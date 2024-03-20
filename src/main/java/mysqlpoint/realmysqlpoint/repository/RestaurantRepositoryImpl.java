package mysqlpoint.realmysqlpoint.repository;


import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import mysqlpoint.realmysqlpoint.controller.response.RestaurantNearbyResponse;
import mysqlpoint.realmysqlpoint.entity.Member;
import mysqlpoint.realmysqlpoint.entity.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

import static mysqlpoint.realmysqlpoint.entity.QProduct.product;
import static mysqlpoint.realmysqlpoint.entity.QRestaurant.restaurant;
import static mysqlpoint.realmysqlpoint.entity.QRestaurantStock.restaurantStock;


@RequiredArgsConstructor
public class RestaurantRepositoryImpl implements RestaurantRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<Restaurant> getRestaurant(double neLatitude, double neLongitude, double swLatitude, double swLongitude) {
        String polygon = String.format("POLYGON((%f %f, %f %f, %f %f, %f %f, %f %f))",
                swLongitude, neLatitude,
                neLongitude, neLatitude,
                neLongitude, swLatitude,
                swLongitude, swLatitude,
                swLongitude, neLatitude);

        BooleanExpression inPolygon = Expressions.booleanTemplate(
                "ST_Contains(ST_PolygonFromText({0}), {1})", polygon, restaurant.location);
        BooleanExpression isNotDeleted = restaurant.deletedAt.isNull();

        return jpaQueryFactory
                .select(restaurant)
                .from(restaurant)
                .leftJoin(restaurant.restaurantStocks, restaurantStock).fetchJoin()
                .leftJoin(restaurantStock.product, product).fetchJoin()
                .where(inPolygon.and(isNotDeleted))
                .fetch();
    }

    @Override
    public Page<RestaurantNearbyResponse> getRestaurantSellingProducts(double userLocationLatitude, double userLocationLongitude, String keyword, Pageable pageable) {

        BooleanExpression isMeasurement = isRestaurantWithinRadius(userLocationLongitude, userLocationLatitude, 5000);

        OrderSpecifier<Double> closestStoreDistanceFromUser = getClosestStoreDistanceFromUser(userLocationLatitude, userLocationLongitude);

        List<RestaurantNearbyResponse> restaurants = jpaQueryFactory
                .select(Projections.constructor(
                        RestaurantNearbyResponse.class,
                        restaurant.id,
                        restaurant.name,
                        restaurant.address,
                        product.name,
                        Expressions.numberTemplate(Double.class, "ST_Distance_Sphere(point({0}, {1}), restaurant.location)", userLocationLongitude, userLocationLatitude).as("distance")
                        ))
                .from(restaurant)
                .leftJoin(restaurant.restaurantStocks, restaurantStock)
                .leftJoin(restaurantStock.product, product)
                .where(isMeasurement
                        .and(isMatchesProductName(keyword))
                        .and(isNullDeleted())
                        .and(isProductOutOfStock())
                ).orderBy(closestStoreDistanceFromUser)
                .fetch();

        JPAQuery<Long> total = jpaQueryFactory
                .select(restaurant.count())
                .from(restaurant);

        return PageableExecutionUtils.getPage(restaurants, pageable, total::fetchOne);
    }
    public BooleanExpression isMatchesProductName(String keyword) {
        return !StringUtils.hasText(keyword) ? null : product.name.eq(keyword);
    }
    public OrderSpecifier<Double> getClosestStoreDistanceFromUser(double userLocationLatitude, double userLocationLongitude) {
        return Expressions.numberTemplate(Double.class,
                "ST_Distance_Sphere(point({0}, {1}), restaurant.location)",
                userLocationLongitude, userLocationLatitude).asc();
    }
    public BooleanExpression isRestaurantWithinRadius(double userLocationLongitude, double userLocationLatitude, double radius) {
        return Expressions.booleanTemplate(
                "ST_Distance_Sphere(point({0}, {1}), restaurant.location) <= {2}",
                userLocationLongitude, userLocationLatitude, radius);
    }
    public BooleanExpression isProductOutOfStock() {
        return product.quantity.isNotNull().or(product.quantity.loe(0));
    }
    public BooleanExpression isNullDeleted() {
        return restaurant.deletedAt.isNull();
    }
}
