package mysqlpoint.realmysqlpoint.repository;

import com.querydsl.jpa.JPQLQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mysqlpoint.realmysqlpoint.controller.response.RestaurantLocationResponse;
import mysqlpoint.realmysqlpoint.entity.Restaurant;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Repository
@RequiredArgsConstructor
public class RestaurantRepositoryImpl implements RestaurantRepository {

    private final JPQLQueryFactory query;

    private final EntityManager entityManager;

    @Override
    public List<RestaurantLocationResponse> findAllItemsAndQuantitiesWithinPolygonForRestaurant(
            double neLatitude, double neLongitude, double swLatitude, double swLongitude , double userLocationLatitude, double userLocationLongitude) {

        String polygon = "ST_PolygonFromText('POLYGON((" +
                swLongitude + " " + neLatitude + ", " +
                neLongitude + " " + neLatitude + ", " +
                neLongitude + " " + swLatitude + ", " +
                swLongitude + " " + swLatitude + ", " +
                swLongitude + " " + neLatitude + "))')";

        String searchRestaurantsInAreaQuery =
                "SELECT "
                        + "r.id,"
                        + "r.member_id,"
                        + "r.category,"
                        + "r.name,"
                        + "r.location,"
                        + "r.address,"
                        + "r.contact,"
                        + "r.menu,"
                        + "r.time,"
                        + "r.provision,"
                        + "r.created_at,"
                        + "r.updated_at,"
                        + "r.deleted_at,"
                        + "ST_DISTANCE_SPHERE(POINT(:userLocationLongitude, :userLocationLatitude), r.location) as distance "
                        + "FROM restaurant r "
                        + "JOIN restaurant_stock rs ON r.id = rs.restaurant_id "
                        + "JOIN item i ON rs.item_id = i.id "
                        + "WHERE ST_Contains(" + polygon + ", r.location) "
                        + "AND r.deleted_at is null ";

        Query query = entityManager.createNativeQuery(searchRestaurantsInAreaQuery, Restaurant.class);
        query.setParameter("userLocationLatitude", userLocationLatitude);
        query.setParameter("userLocationLongitude", userLocationLongitude);

        List<Restaurant> resultList = query.getResultList();

        return resultList.stream()
                .map(RestaurantLocationResponse::of)
                .collect(Collectors.toList());


    }




}