package mysqlpoint.realmysqlpoint.repository;


import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mysqlpoint.realmysqlpoint.controller.request.RestaurantLocationRequest;
import mysqlpoint.realmysqlpoint.controller.response.RestaurantLocationResponse;
import mysqlpoint.realmysqlpoint.entity.Restaurant;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static mysqlpoint.realmysqlpoint.entity.QRestaurant.*;

@Slf4j
@Repository
@RequiredArgsConstructor
public class RestaurantLocationSearchRepository {

    private final JPQLQueryFactory query;

    @Transactional(readOnly = true)
    public RestaurantLocationResponse search(RestaurantLocationRequest locationRequest) {

        final GeometryFactory geometryFactory = new GeometryFactory();

        final Point point = geometryFactory.createPoint(new Coordinate(locationRequest.getLongitude(), locationRequest.getLatitude()));

        Restaurant findRestaurant = query.selectFrom(restaurant)
                .where(Objects.requireNonNull(
                        isRestaurantName(locationRequest.getMessage()))
                        .and(isRestaurantLocation(point))).fetchOne();

        return RestaurantLocationResponse.of(findRestaurant);
    }

    private BooleanExpression isRestaurantName(String restaurantName) {
        return restaurantName.isEmpty() ? null : restaurant.name.eq(restaurantName);
    }

    private BooleanExpression isRestaurantLocation(Point latitude) {
        return latitude.isEmpty() ? null : restaurant.location.eq(latitude);
    }

}
