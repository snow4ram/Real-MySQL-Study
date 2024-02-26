package mysqlpoint.realmysqlpoint.repository.custom;

import mysqlpoint.realmysqlpoint.controller.response.RestaurantLocationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RestaurantRepositoryCustom {
    Page<RestaurantLocationResponse> getSearchRestaurantsInArea(String keyword,
                                                             double neLatitude,
                                                             double neLongitude,
                                                             double swLatitude,
                                                             double swLongitude,
                                                             double userLocationLatitude,
                                                             double userLocationLongitude,
                                                             Pageable pageable);

}
