package mysqlpoint.realmysqlpoint.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mysqlpoint.realmysqlpoint.controller.request.UserLocationToZoomRequest;

import mysqlpoint.realmysqlpoint.controller.request.UserLocationRequest;
import mysqlpoint.realmysqlpoint.controller.response.RestaurantNameResponse;
import mysqlpoint.realmysqlpoint.entity.Restaurant;
import mysqlpoint.realmysqlpoint.repository.JpaRestaurantRepository;
import mysqlpoint.realmysqlpoint.util.RestaurantMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final JpaRestaurantRepository restaurantRepository;

    private final RestaurantMapper restaurantNameResponseMapper;

    private static final Integer DEFAULT_RADIUS = 1000;
    //반경 미정.
    private static final Integer ZOOM_LEVEL_17_RADIUS = 750;
    private static final Integer ZOOM_LEVEL_18_RADIUS = 500;
    private static final Integer ZOOM_LEVEL_19_RADIUS = 250;

    private static final Long ZOOM_LEVEL_16 = 16L;

    private static final Long ZOOM_LEVEL_17 = 17L;

    private static final Long ZOOM_LEVEL_18 = 18L;
    private static final Long ZOOM_LEVEL_19 = 19L;


    /*
     todo
        1. ✅ 사용자의 위치 계속 확인하는 방향이 아니라 사용자 재검색을 누르면 근처 가게의 정보를 출력하는 방식
        2. 사용자 기준 점에서 반경 몇 km 까지 검색할껀지
            - Zoom Level 에서 몇개의 가게 의 정보를 보여줄지.
            - Zoom Level 에서 몇 km 까지에 있는 가게 정보를 가져올지.
        3. ✅ 가게의 이름, 위치 정보로 가게의 정보를 찾는 코드를 작성.
        4. ✅ 가게의 정보 이름 받아 와서 가게 대한 정보를 찾고 그 찾고있는 가게 대한 정보를 QueryDSL 로 전달.
    * */

    @Transactional(readOnly = true)
    public List<RestaurantNameResponse> locationCheck(UserLocationRequest locationRequest) {

        List<Restaurant> restaurants = restaurantRepository.findAllWithinPoint(locationRequest.getLatitude() , locationRequest.getLongitude() , DEFAULT_RADIUS);

        return restaurants.stream()
                .map(restaurantNameResponseMapper)
                .collect(Collectors.toList());

    }
    
    public List<RestaurantNameResponse> getZoomLevelRestaurant(UserLocationToZoomRequest locationAndZoomRequest) {

        final Long zoomLevel = locationAndZoomRequest.getZoomLevel();

        if (Objects.equals(zoomLevel, ZOOM_LEVEL_16)) {
            return getRestaurantNameResponses(locationAndZoomRequest , DEFAULT_RADIUS);
        }

        if (Objects.equals(zoomLevel, ZOOM_LEVEL_17)) {
            return getRestaurantNameResponses(locationAndZoomRequest , ZOOM_LEVEL_17_RADIUS);
        }

        if (Objects.equals(zoomLevel, ZOOM_LEVEL_18)) {
            return getRestaurantNameResponses(locationAndZoomRequest , ZOOM_LEVEL_18_RADIUS);
        }

        if (Objects.equals(zoomLevel, ZOOM_LEVEL_19)) {
            return getRestaurantNameResponses(locationAndZoomRequest , ZOOM_LEVEL_19_RADIUS);
        }

        //임시로 16 ~ 19 사이로 지정.
        throw new IllegalArgumentException("Invalid zoom level: " + zoomLevel + ". Valid zoom levels are between 16 and 19.");
    }

    private List<RestaurantNameResponse> getRestaurantNameResponses(UserLocationToZoomRequest locationAndZoomRequest , Integer zoomLeveValue) {
        List<Restaurant> everythingWithinA17PointRadius = restaurantRepository.findAllWithinPoint(locationAndZoomRequest.getLatitude(), locationAndZoomRequest.getLongitude(), zoomLeveValue);

        return everythingWithinA17PointRadius.stream()
                .map(restaurantNameResponseMapper).collect(Collectors.toList());
    }


}
