package mysqlpoint.realmysqlpoint.service;

import lombok.extern.slf4j.Slf4j;
import mysqlpoint.realmysqlpoint.controller.response.RestaurantNameResponse;
import mysqlpoint.realmysqlpoint.entity.Restaurant;
import mysqlpoint.realmysqlpoint.repository.JpaRestaurantRepository;
import mysqlpoint.realmysqlpoint.util.RestaurantMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@SpringBootTest
@Transactional
@ExtendWith(SpringExtension.class)
class UserRadiusInKilometersRestaurantServiceTest {


    @Autowired
    private JpaRestaurantRepository jpaRestaurantRepository;

    @Autowired
    private RestaurantMapper restaurantMapper;

    private static final Integer ZOOM_LEVEL_18_RADIUS = 500;
    private static final Integer ZOOM_LEVEL_19_RADIUS = 250;


    @Test
    @DisplayName("반지름 250m 이내의 레스토랑 정보 찾기")
    public void Restaurant_information_within_a_250m_radius() {

        //give : 현재 250M 위치
        double latitude = 35.8386548; //위도
        double longitude = 128.753059; //경도


        //when
        List<Restaurant> repositoryAllWithinPoint = jpaRestaurantRepository.findAllWithinPoint(latitude, longitude, ZOOM_LEVEL_19_RADIUS);


        //then
        Assertions.assertThat(repositoryAllWithinPoint).isNotEmpty();
        Assertions.assertThat(repositoryAllWithinPoint.size()).isEqualTo(6);

    }


    @Test
    @DisplayName("현재 위치에서 250m 이외의 가게를 찾는 테스트 코드")
    public void Restaurant_information_outside_a_250m_radius() {

        //give
        double latitude = 35.837872; //위도
        double longitude = 128.752106; //경도

        String getNameOfRestaurant= "커피플레이스 카페";

        // 반복 당 이동할 거리 🛫 - - - - - - - 🛬
        double movePerIterationLat = 0.0012;
        double movePerIterationLon = 0.0012;


        //when
        double moveLatitude = latitude + movePerIterationLat;
        double moveLongitude = movePerIterationLon + longitude;


        List<Restaurant> repositoryAllWithinPoint = jpaRestaurantRepository.findAllWithinPoint(moveLatitude, moveLongitude, ZOOM_LEVEL_19_RADIUS);

        for (Restaurant restaurant : repositoryAllWithinPoint) {
            log.info("가게 정보 = {}" , restaurant);
        }

        List<RestaurantNameResponse> collect = repositoryAllWithinPoint
                .stream()
                .filter(restaurant -> restaurant.getName().equals(getNameOfRestaurant))
                .map(restaurantMapper)
                .collect(Collectors.toList());


        //then
        Assertions.assertThat(collect).isNotEmpty();
        Assertions.assertThat(collect.get(0).getLatitude()).isEqualTo(35.841194);
        Assertions.assertThat(collect.get(0).getLongitude()).isEqualTo(128.753973);


    }

}