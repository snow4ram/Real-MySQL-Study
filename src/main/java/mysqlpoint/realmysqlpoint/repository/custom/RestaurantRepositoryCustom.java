package mysqlpoint.realmysqlpoint.repository.custom;

import mysqlpoint.realmysqlpoint.controller.response.RestaurantLocationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RestaurantRepositoryCustom {
    Page<RestaurantLocationResponse> getSearchRestaurantsInArea(String keyword,
                                                             double swLatitude,
                                                             double swLongitude,
                                                             double neLatitude,
                                                             double neLongitude,
                                                             double lat,
                                                             double lon,
                                                             Pageable pageable);

}
