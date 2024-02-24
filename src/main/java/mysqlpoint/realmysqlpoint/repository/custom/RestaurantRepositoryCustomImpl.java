package mysqlpoint.realmysqlpoint.repository.custom;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mysqlpoint.realmysqlpoint.controller.response.RestaurantLocationResponse;
import mysqlpoint.realmysqlpoint.entity.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Repository
@RequiredArgsConstructor
public class RestaurantRepositoryCustomImpl implements RestaurantRepositoryCustom {

    private final EntityManager entityManager;

    public Page<RestaurantLocationResponse> getSearchRestaurantsInArea(
            String shopName, double swLatitude, double swLongitude, double neLatitude, double neLongitude, double userLocationLatitude, double userLocationLongitude, Pageable pageable) {

        String polygonText = "ST_PolygonFromText('POLYGON((" +
                swLongitude + " " + neLatitude + ", " +
                neLongitude + " " + neLatitude + ", " +
                neLongitude + " " + swLatitude + ", " +
                swLongitude + " " + swLatitude + ", " +
                swLongitude + " " + neLatitude + "))')";

        String nativeQuery = "SELECT " +
                        "r.id, " +
                        "r.category, " +
                        "r.name, " +
                        "r.location, " +
                        "r.address, " +
                        "r.contact, " +
                        "r.menu, " +
                        "r.time, " +
                        "r.provision, " +
                        "ST_DISTANCE_SPHERE(POINT(:userLocationLatitude, :userLocationLongitude), r.location) as distance " +
                        "FROM restaurant r " +
                        "WHERE ST_Contains(" + polygonText + ", r.location) " +
                        "AND r.deleted_at is null ";

        if (shopName != null) {
            nativeQuery += "AND (r.name LIKE :keyword) ";
        }

        nativeQuery += "ORDER BY distance DESC";

        Query query = entityManager.createNativeQuery(nativeQuery, Restaurant.class);
        query.setParameter("userLocationLatitude", userLocationLatitude);
        query.setParameter("luserLocationLongitudeon", userLocationLongitude);

        if (shopName != null) {
            query.setParameter("keyword", "%" + shopName + "%");
        }

        List<Restaurant> restaurantList = query.getResultList();

        if (restaurantList.isEmpty()) {
            return getSearchRestaurantsNotInArea(shopName, pageable , userLocationLatitude , userLocationLongitude);
        }

        return getRestaurantLocationResponses(pageable, restaurantList);
    }

    public Page<RestaurantLocationResponse> getSearchRestaurantsNotInArea(String shopName, Pageable pageable , double userLocationLatitude, double userLocationLongitude) {

        String nativeQuery = "SELECT " +
                    "r.id, " +
                    "r.category, " +
                    "r.name, " +
                    "r.location, " +
                    "r.address, " +
                    "r.contact, " +
                    "r.menu, " +
                    "r.time, " +
                    "r.provision, " +
                    "ST_DISTANCE_SPHERE(POINT(:userLocationLatitude, :userLocationLongitude), r.location) as distance " +
                    "FROM restaurant r ";


        if (shopName != null) {
            nativeQuery += "WHERE (r.name LIKE :newSearch) ";
        }

        nativeQuery += "AND r.deleted_at is null ";

        nativeQuery += "ORDER BY distance DESC";


        Query query = entityManager.createNativeQuery(nativeQuery, Restaurant.class);
        query.setParameter("userLocationLatitude", userLocationLatitude);
        query.setParameter("userLocationLongitude", userLocationLongitude);

        if (shopName != null) {
            query.setParameter("newSearch", "%" + shopName + "%");
        }

        List<Restaurant> restaurantList = query.getResultList();

        return getRestaurantLocationResponses(pageable, restaurantList);

    }


    private static PageImpl<RestaurantLocationResponse> getRestaurantLocationResponses(Pageable pageable, List<Restaurant> restaurantList) {
        List<RestaurantLocationResponse> responseList = restaurantList.stream()
                .map(RestaurantLocationResponse::of)
                .collect(Collectors.toList());

        int start = (int) pageable.getOffset();

        int end = Math.min((start + pageable.getPageSize()), restaurantList.size());

        return new PageImpl<>(responseList.subList(start, end), pageable, responseList.size());
    }
}