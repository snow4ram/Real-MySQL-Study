package mysqlpoint.realmysqlpoint.repository;


import mysqlpoint.realmysqlpoint.entity.Restaurant;


import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;


public interface JpaRestaurantRepository extends JpaRepository<Restaurant, Long> {

    @Query(value =
            "SELECT * FROM Restaurant r WHERE MBRContains(" +
            "LineString(" +
            "Point(:longitude - (:radius / 111.32), :latitude - (:radius / (111.32 * COS(RADIANS(:latitude))))), " +
            "Point(:longitude + (:radius / 111.32), :latitude + (:radius / (111.32 * COS(RADIANS(:latitude)))))), r.location) " +
            "AND ST_Distance_Sphere(r.location, Point(:longitude, :latitude)) <= :radius", nativeQuery = true)
    List<Restaurant> findAllWithinPoint(@Param("latitude") double latitude, @Param("longitude") double longitude, @Param("radius") double radius);


    @Query(value = "SELECT r FROM Restaurant r WHERE ST_Distance_Sphere(r.location , :pointText) <= :radius")
    List<Restaurant> findAllWithinPoints(@Param("pointText") Point pointText, @Param("radius") double radius);


}

