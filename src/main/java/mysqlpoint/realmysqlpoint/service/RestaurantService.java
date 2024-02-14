package mysqlpoint.realmysqlpoint.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mysqlpoint.realmysqlpoint.controller.request.UserLocationRequest;
import mysqlpoint.realmysqlpoint.controller.response.RestaurantNameResponse;
import mysqlpoint.realmysqlpoint.entity.Restaurant;
import mysqlpoint.realmysqlpoint.repository.JpaRestaurantRepository;
import mysqlpoint.realmysqlpoint.util.RestaurantMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final JpaRestaurantRepository restaurantRepository;

    private final RestaurantMapper restaurantMapper;

    private static final String RESTAURANT_SEARCH = "음식점";

    private static final Integer RADIUS = 1000;

    /*
     todo
        1. ✅ 사용자의 위치 계속 확인하는 방향이 아니라 사용자 재검색을 누르면 근처 가게의 정보를 출력하는 방식
        2. 사용자 기준 점에서 반경 몇 km 까지 검색할껀지
            - Zoom Level 에서 몇개의 가게 의 정보를 보여줄지.
            - Zoom Level 에서 몇 km 까지에 있는 가게 정보를 가져올지.
        3. 가게의 이름으로 가게의 정보를 가져오는 코드를 작성.
        4. 가게의 정보 이름 받아 와서 가게 대한 정보를 찾고 그 찾고있는 가게 대한 정보를 QueryDSL 로 전달.
    * */

    @Transactional(readOnly = true)
    public List<RestaurantNameResponse> locationCheck(UserLocationRequest locationRequest) {

        List<Restaurant> restaurants = restaurantRepository.findAllWithinPoint(locationRequest.getLatitude() , locationRequest.getLongitude() , RADIUS);

        return restaurants.stream().map(restaurantMapper).collect(Collectors.toList());

    }


    





}
