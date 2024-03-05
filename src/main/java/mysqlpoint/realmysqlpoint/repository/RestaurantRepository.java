package mysqlpoint.realmysqlpoint.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mysqlpoint.realmysqlpoint.controller.response.RestaurantLocationDTO;
import mysqlpoint.realmysqlpoint.entity.Restaurant;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static mysqlpoint.realmysqlpoint.entity.QItem.*;
import static mysqlpoint.realmysqlpoint.entity.QRestaurant.*;
import static mysqlpoint.realmysqlpoint.entity.QRestaurantStock.*;

@Slf4j
@Repository
@RequiredArgsConstructor
public class RestaurantRepository {

    private final JPAQueryFactory query;

    @Transactional(readOnly = true)
    public List<Restaurant> getRestaurant(
            double neLatitude, double neLongitude, double swLatitude, double swLongitude) {

        String polygon = String.format("POLYGON((%f %f, %f %f, %f %f, %f %f, %f %f))",
                swLongitude, neLatitude,
                neLongitude, neLatitude,
                neLongitude, swLatitude,
                swLongitude, swLatitude,
                swLongitude, neLatitude);

        BooleanExpression inPolygon = Expressions.booleanTemplate(
                "ST_Contains(ST_PolygonFromText({0}), {1})", polygon, restaurant.location);
        BooleanExpression isNotDeleted = restaurant.deletedAt.isNull();

        return query
                .select(restaurant)
                .from(restaurant)
                .join(restaurant.restaurantStocks, restaurantStock).fetchJoin()
                .join(restaurantStock.item, item).fetchJoin()
                .where(inPolygon.and(isNotDeleted))
                .fetch();
    }


}
