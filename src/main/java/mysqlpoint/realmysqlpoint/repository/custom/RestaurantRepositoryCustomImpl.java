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

    //무한 스크롤 default values = 20 -> N 까지
    //첫번쨰는 현제 위치의 폴리곤 라인안에 있는 가게의 정보를 모두 보여주는게 1차 목표, 이 영역안에 있는 가게를 찾는거죠 가 , 나 , 사  이 영역안에 있는 가게만 찾는걸 목표로 했고
    //두번째로는 검색을 했는데 이 영역안에 이 가게가 없으면 -> 밖에 라인으로 검색 을 하는거죠
    public Page<RestaurantLocationResponse> getSearchRestaurantsInArea(
            String shopName,  double neLatitude, double neLongitude, double swLatitude, double swLongitude , double userLocationLatitude, double userLocationLongitude, Pageable pageable) {

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
                            + "JOIN member m on r.member_id = m.id "
                            + "WHERE ST_Contains(" + polygon + ", r.location) "
                            + "AND r.deleted_at is null ";

        if (shopName != null) {
            searchRestaurantsInAreaQuery += "AND (r.name LIKE :shopName) ";
            //searchRestaurantsInAreaQuery += "MATCH (r.name) AGAINST(:shopName IN BOOLEAN MODE) ";
        }

        searchRestaurantsInAreaQuery += "ORDER BY distance ASC";

        Query query = entityManager.createNativeQuery(searchRestaurantsInAreaQuery, Restaurant.class);
        query.setParameter("userLocationLatitude", userLocationLatitude);
        query.setParameter("userLocationLongitude", userLocationLongitude);

        //이상 ,,,, 너무 한방 쿼리,,
        if (shopName != null) {
            query.setParameter("shopName", "%" + shopName + "%");
        }

        //
        List<Restaurant> restaurantList = query.getResultList();

        if (restaurantList.isEmpty()) {
            return getSearchRestaurantsNotInArea(shopName, pageable , userLocationLatitude , userLocationLongitude);
        }

        return getRestaurantLocationResponses(pageable, restaurantList);
    }

    private Page<RestaurantLocationResponse> getSearchRestaurantsNotInArea(String shopName, Pageable pageable , double userLocationLatitude, double userLocationLongitude) {

        String searchRestaurantsNotInAreaQuery =
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
                            + "JOIN member m on r.member_id = m.id "
                            + "WHERE (r.name LIKE :shopName) "
                            + "AND r.deleted_at is null ";

        searchRestaurantsNotInAreaQuery += "ORDER BY distance ASC";


        Query query = entityManager.createNativeQuery(searchRestaurantsNotInAreaQuery, Restaurant.class);
        query.setParameter("userLocationLatitude", userLocationLatitude);
        query.setParameter("userLocationLongitude", userLocationLongitude);

        query.setParameter("shopName", "%" + shopName + "%");


        List<Restaurant> restaurantList = query.getResultList();

        return getRestaurantLocationResponses(pageable, restaurantList);

    }


    private static PageImpl<RestaurantLocationResponse> getRestaurantLocationResponses(Pageable pageable, List<Restaurant> restaurantList) {
        List<RestaurantLocationResponse> responseList = restaurantList.stream()
                .map(RestaurantLocationResponse::of)
                .collect(Collectors.toList());

        int start = (int) pageable.getOffset();

        int end = Math.min((start + pageable.getPageSize()), restaurantList.size());

        return new PageImpl<>(responseList.subList(start, end), pageable, responseList.size());
    }
}