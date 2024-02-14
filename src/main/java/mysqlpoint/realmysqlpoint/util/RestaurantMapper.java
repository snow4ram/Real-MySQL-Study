package mysqlpoint.realmysqlpoint.util;

import mysqlpoint.realmysqlpoint.controller.response.RestaurantNameResponse;
import mysqlpoint.realmysqlpoint.entity.Restaurant;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class RestaurantMapper implements Function<Restaurant, RestaurantNameResponse> {

    @Override
    public RestaurantNameResponse apply(Restaurant restaurant) {
        return RestaurantNameResponse.builder()
                .name(restaurant.getName())
                .longitude(restaurant.getLocation().getX()) //경도
                .latitude(restaurant.getLocation().getY()) //위도
                .build();

    }
}
