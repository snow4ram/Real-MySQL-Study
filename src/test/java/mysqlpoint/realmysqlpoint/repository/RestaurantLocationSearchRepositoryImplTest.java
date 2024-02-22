package mysqlpoint.realmysqlpoint.repository;

import lombok.extern.slf4j.Slf4j;
import mysqlpoint.realmysqlpoint.controller.request.UserLocationRequest;

import mysqlpoint.realmysqlpoint.controller.response.RestaurantLocationResponse;
import mysqlpoint.realmysqlpoint.repository.custom.RestaurantRepositoryCustomImpl;
import mysqlpoint.realmysqlpoint.service.RestaurantService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@Slf4j
@SpringBootTest
@ExtendWith(SpringExtension.class)
class RestaurantLocationSearchRepositoryImplTest {


    @Autowired
    private RestaurantSearchRepository searchRepository;

    @Autowired
    private RestaurantRepositoryCustomImpl restaurantRepositoryCustom;

    @Test
    public void queryTest() {


        double neLatitude = 37.5567635;
        double neLongitude = 126.8529193;
        double swLatitude = 37.5482577;
        double swLongitude = 126.8421905;

        UserLocationRequest locationPolygonBounds = new UserLocationRequest(
                neLatitude,
                neLongitude,
                swLatitude,
                swLongitude
        );

        Pageable pageable = PageRequest.of(0 ,20);

        Page<RestaurantLocationResponse> restaurantLocationResponses = restaurantRepositoryCustom.searchRestaurantsInArea("우리집" , neLatitude, neLongitude, swLatitude, swLongitude, pageable);

        for (RestaurantLocationResponse restaurantLocationRespons : restaurantLocationResponses) {
            log.info("가게정보 = {}" , restaurantLocationRespons);
        }
    }
}