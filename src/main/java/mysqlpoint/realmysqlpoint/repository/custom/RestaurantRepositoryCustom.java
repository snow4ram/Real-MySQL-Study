package mysqlpoint.realmysqlpoint.repository.custom;

import mysqlpoint.realmysqlpoint.controller.response.RestaurantLocationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RestaurantRepositoryCustom {
    Page<RestaurantLocationResponse> searchRestaurantsInArea(String keyword, double swLatitude, double swLongitude, double neLatitude, double neLongitude, Pageable pageable);

}
