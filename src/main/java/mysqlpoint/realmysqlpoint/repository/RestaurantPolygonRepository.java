package mysqlpoint.realmysqlpoint.repository;

import com.querydsl.core.Query;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPQLQueryFactory;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mysqlpoint.realmysqlpoint.controller.response.RestaurantLocationResponse;
import mysqlpoint.realmysqlpoint.controller.response.RestaurantNearbyLocationResponse;
import mysqlpoint.realmysqlpoint.entity.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static mysqlpoint.realmysqlpoint.entity.QItem.*;
import static mysqlpoint.realmysqlpoint.entity.QRestaurant.*;
import static mysqlpoint.realmysqlpoint.entity.QRestaurantStock.*;

@Slf4j
@Repository
@RequiredArgsConstructor
public class RestaurantPolygonRepository {

    private final JPAQueryFactory query;

    @Transactional(readOnly = true)
    public List<RestaurantNearbyLocationResponse> findAllItemsAndQuantitiesWithinPolygonForRestaurant(
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

        // QueryDSL 쿼리 구성
        return query
                .select(Projections.constructor(RestaurantNearbyLocationResponse.class ,
                        restaurant.id,
                        restaurant.members.id,
                        restaurant.category,
                        restaurant.name,
                        restaurant.address,
                        restaurant.location,
                        restaurant.contact,
                        restaurant.menu,
                        restaurant.time,
                        restaurant.provision,
                        item.id,
                        item.name,
                        item.price,
                        item.info
                        ))
                .from(restaurant)
                .join(restaurant.restaurantStocks, restaurantStock)
                .join(restaurantStock.item , item)
                .where(inPolygon.and(isNotDeleted)).fetch();

    }

}
