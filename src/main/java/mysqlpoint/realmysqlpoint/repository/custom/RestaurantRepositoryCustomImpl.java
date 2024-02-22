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

    @Override
    public Page<RestaurantLocationResponse> searchRestaurantsInArea(
            String keyword,
            double swLatitude, double swLongitude,
            double neLatitude, double neLongitude,
            Pageable pageable) {

        //현제 위치
        String polygonText = "ST_PolygonFromText('POLYGON((" +
                swLongitude + " " + neLatitude + ", " +
                neLongitude + " " + neLatitude + ", " +
                neLongitude + " " + swLatitude + ", " +
                swLongitude + " " + swLatitude + ", " +
                swLongitude + " " + neLatitude + "))')";

        //현제 위치에서 가게를 찾는다 폴리곤라인으로
        String baseQuery = "SELECT r.* FROM restaurant r WHERE ST_Contains(" + polygonText + ", r.location)";

        //제약 조건 추가 부분
        if (keyword != null) {
            baseQuery += " AND (r.name LIKE :keyword OR r.address LIKE :keyword)";
        }

        //Query 에 추가
        Query query = entityManager.createNativeQuery(baseQuery, Restaurant.class);

        if (keyword != null && !keyword.isEmpty()) {
            query.setParameter("keyword", "%" + keyword + "%");
        }

        List<Restaurant> resultList = query.getResultList();

        if (resultList.isEmpty()) {
            return search(keyword, pageable);
        }

        List<RestaurantLocationResponse> responseList = resultList.stream()
                .map(RestaurantLocationResponse::of)
                .collect(Collectors.toList());

        int start = (int) pageable.getOffset();

        int end = Math.min((start + pageable.getPageSize()), resultList.size());

        return new PageImpl<>(responseList.subList(start, end), pageable, responseList.size());

    }


    public Page<RestaurantLocationResponse> search(String newSearch, Pageable pageable) {

        String baseQuery = "SELECT r.* FROM restaurant r";

        if (newSearch != null && !newSearch.isEmpty()) {
            baseQuery += " WHERE (r.name LIKE :keyword OR r.address LIKE :keyword)";
        }
        // 쿼리 실행 부분
        Query query = entityManager.createNativeQuery(baseQuery, Restaurant.class);

        if (newSearch != null && !newSearch.isEmpty()) {
            query.setParameter("keyword", "%" + newSearch + "%");
        }

        List<Restaurant> resultList = query.getResultList();

        List<RestaurantLocationResponse> responseList = resultList.stream()
                .map(RestaurantLocationResponse::of)
                .toList();

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), resultList.size());

        PageImpl<RestaurantLocationResponse> restaurantLocationResponses = new PageImpl<>(responseList.subList(start, end), pageable, responseList.size());

        for (RestaurantLocationResponse restaurantLocationRespons : restaurantLocationResponses) {
            log.info("가게 정보 = {}", restaurantLocationRespons.toString());
        }
        return restaurantLocationResponses;
    }
}