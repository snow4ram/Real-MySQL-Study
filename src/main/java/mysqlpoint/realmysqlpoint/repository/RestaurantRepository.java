package mysqlpoint.realmysqlpoint.repository;

import mysqlpoint.realmysqlpoint.controller.response.RestaurantLocationResponse;

import java.util.List;

public interface RestaurantRepository {
    List<RestaurantLocationResponse> findAllItemsAndQuantitiesWithinPolygonForRestaurant(
            double neLatitude, double neLongitude, double swLatitude, double swLongitude , double userLocationLatitude, double userLocationLongitude);
}
