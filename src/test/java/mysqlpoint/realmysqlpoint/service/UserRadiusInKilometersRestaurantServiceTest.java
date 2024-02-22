package mysqlpoint.realmysqlpoint.service;

import lombok.extern.slf4j.Slf4j;
import mysqlpoint.realmysqlpoint.controller.request.RestaurantSearchLocationRequest;
import mysqlpoint.realmysqlpoint.controller.request.UserLocationRequest;
import mysqlpoint.realmysqlpoint.controller.response.RestaurantLocationResponse;
import mysqlpoint.realmysqlpoint.controller.response.RestaurantNameResponse;
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
    private RestaurantService restaurants;

    double neLatitude = 37.5567635;
    double neLongitude = 126.8529193;
    double swLatitude = 37.5482577;
    double swLongitude = 126.8421905;


    @Test
    @DisplayName("반지름 250m 이내의 레스토랑 정보 찾기")
    public void restaurantInformationWithin250mRadius() {

        //give : 현재 250M 위치
        double latitude = 35.8386548; //위도
        double longitude = 128.753059; //경도
        Integer zoomLevel = 250;

        //when
//        List<RestaurantNameResponse> zoomLevelRestaurant = restaurants.getRestaurantNameResponses(new RestaurantSearchLocationRequest(zoomLevel, latitude, longitude));

        List<RestaurantLocationResponse> restaurantSearch = restaurants.getRestaurantSearch(new UserLocationRequest(neLatitude, neLongitude, swLatitude, swLongitude));

        for (RestaurantLocationResponse response : restaurantSearch) {
            log.info("가게정보 = {}" , response);
        }
        //then

    }


}