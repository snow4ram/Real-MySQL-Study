package mysqlpoint.realmysqlpoint.repository;


import mysqlpoint.realmysqlpoint.controller.response.RestaurantNearbyResponse;
import mysqlpoint.realmysqlpoint.entity.Member;
import mysqlpoint.realmysqlpoint.entity.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
public interface RestaurantRepositoryCustom {

    List<Restaurant> getRestaurant(double neLatitude, double neLongitude, double swLatitude, double swLongitude);
    Page<RestaurantNearbyResponse> getRestaurantSellingProducts(double userLocationLatitude, double userLocationLongitude, String keyword, Pageable pageable);
}
