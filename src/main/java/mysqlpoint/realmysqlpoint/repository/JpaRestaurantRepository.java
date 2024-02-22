package mysqlpoint.realmysqlpoint.repository;


import jakarta.persistence.EntityManager;
import mysqlpoint.realmysqlpoint.entity.Restaurant;


import mysqlpoint.realmysqlpoint.repository.custom.RestaurantRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;


public interface JpaRestaurantRepository extends JpaRepository<Restaurant, Long> , RestaurantRepositoryCustom {

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


//    @Query(nativeQuery = true, value = """
//                SELECT R.*, ST_DISTANCE_SPHERE(POINT(:longitude, :latitude), R.location) AS distance
//                FROM (
//                    SELECT r.*
//                    FROM Restaurant r
//                    WHERE MBRContains(
//                        LineString(
//                            Point(
//                                :longitude - (:radius / 111.32),
//                                :latitude - (:radius / (111.32 * COS(RADIANS(:latitude))))
//                            ),
//                            Point(
//                                :longitude + (:radius / 111.32),
//                                :latitude + (:radius / (111.32 * COS(RADIANS(:latitude))))
//                            )
//                        ), r.location
//                    )
//                ) AS r
//                WHERE ST_DISTANCE_SPHERE(POINT(:longitude, :latitude), R.location) <= :radius
//                ORDER BY distance ASC;
//            """)
//    List<Restaurant> findAllWithinPoint(
//            @Param("latitude") double latitude,
//            @Param("longitude") double longitude,
//            @Param("radius") double radius);

    @Query(nativeQuery = true, value = """
                SELECT r.*, ST_DISTANCE_SPHERE(POINT(:longitude, :latitude), r.location) AS distance
                FROM (
                    SELECT *
                    FROM Restaurant r 
                    WHERE ST_Contains(
                                    Polygon(LineString(
                                    Point(:swLongitude, :neLatitude),  
                                    Point(:neLongitude, :neLatitude),  
                                    Point(:neLongitude, :swLatitude),  
                                    Point(:swLongitude, :swLatitude),  
                                    Point(:swLongitude, :neLatitude))), r.location), 
                                    r.location
                    )
                ) AS r
                ORDER BY distance ASC;
            """)
    List<Restaurant> findAllWithinPoint(
            @Param("latitude") double latitude,
            @Param("longitude") double longitude,
            @Param("neLatitude") double neLatitude,
            @Param("neLongitude") double neLongitude,
            @Param("swLatitude") double swLatitude,
            @Param("swLongitude") double swLongitude);

}

