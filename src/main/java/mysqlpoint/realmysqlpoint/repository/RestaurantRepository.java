package mysqlpoint.realmysqlpoint.repository;


import mysqlpoint.realmysqlpoint.entity.Restaurant;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;


public interface RestaurantRepository extends JpaRepository<Restaurant, Long> ,RestaurantRepositoryCustom{

    @Query(value = "SELECT * FROM Restaurant r WHERE ST_Contains(" +
            "Polygon(LineString(" +
            "Point(:swLongitude, :neLatitude), " + // 북서(NW) 꼭짓점
            "Point(:neLongitude, :neLatitude), " + // 북동(NE) 꼭짓점
            "Point(:neLongitude, :swLatitude), " + // 남동(SE) 꼭짓점
            "Point(:swLongitude, :swLatitude), " + // 남서(SW) 꼭짓점
            "Point(:swLongitude, :neLatitude))), r.location)", // 폴리곤을 닫기 위해 북서(NW) 꼭짓점 반복
            nativeQuery = true)
    List<Restaurant> findAllWithinPolygon(
            @Param("neLatitude") double neLatitude,
            @Param("neLongitude") double neLongitude,
            @Param("swLatitude") double swLatitude,
            @Param("swLongitude") double swLongitude);


    @Query(value =
            "SELECT r.*, i.*" +
            "FROM restaurant_stock rs " +
            "JOIN restaurant r ON r.id = rs.restaurant_id " +
            "JOIN item i ON i.id = rs.item_id " +
                "WHERE ST_Contains(" +
                "Polygon(LineString(" +
                "Point(:swLongitude, :neLatitude), " +
                "Point(:neLongitude, :neLatitude), " +
                "Point(:neLongitude, :swLatitude), " +
                "Point(:swLongitude, :swLatitude), " +
                "Point(:swLongitude, :neLatitude))), r.location) ", nativeQuery = true)
    List<Object[]> findAllItemsAndQuantitiesWithinPolygonForRestaurant(
            @Param("neLatitude") double neLatitude,
            @Param("neLongitude") double neLongitude,
            @Param("swLatitude") double swLatitude,
            @Param("swLongitude") double swLongitude);


}

