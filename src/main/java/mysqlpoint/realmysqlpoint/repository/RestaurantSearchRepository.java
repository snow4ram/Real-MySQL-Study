package mysqlpoint.realmysqlpoint.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mysqlpoint.realmysqlpoint.controller.response.RestaurantLocationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;


@Slf4j
@Repository
@RequiredArgsConstructor
public class RestaurantSearchRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void searchRestaurants(
            String keyword,
            double neLatitude,
            double neLongitude,
            double swLatitude,
            double swLongitude
    ) {
        // 기본 쿼리
        String nativeQuery = "SELECT *"
                + "FROM Restaurant r "
                + "WHERE (:keyword IS NULL OR r.name LIKE :keyword) "
                + "AND ST_Contains(" +
                "Polygon(LineString(" +
                "Point(:swLongitude, :neLatitude), " + // 북서(NW) 꼭짓점
                "Point(:neLongitude, :neLatitude), " + // 북동(NE) 꼭짓점
                "Point(:neLongitude, :swLatitude), " + // 남동(SE) 꼭짓점
                "Point(:swLongitude, :swLatitude), " + // 남서(SW) 꼭짓점
                "Point(:swLongitude, :neLatitude))), r.location)";

        // Ensure this condition is properly appended to the nativeQuery
//        String withinRangeCondition = "AND ST_Contains(ST_MakeEnvelope("
//                + "ST_Point(:swLongitude, :neLatitude), "
//                + "ST_Point(:neLongitude, :swLatitude)), r.location) ";

        // Combine the queries
        String finalQuery = nativeQuery;

        // Use createNativeQuery for a native SQL query
        Query query = entityManager.createNativeQuery(finalQuery, RestaurantLocationResponse.class)
                .setParameter("keyword", keyword != null ? "%" + keyword + "%" : null)
                .setParameter("neLatitude", neLatitude)
                .setParameter("neLongitude", neLongitude)
                .setParameter("swLatitude", swLatitude)
                .setParameter("swLongitude", swLongitude);


        log.info("Final query: {}",query );
    }
}