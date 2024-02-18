package mysqlpoint.realmysqlpoint.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mysqlpoint.realmysqlpoint.controller.request.RestaurantLocationRequest;
import mysqlpoint.realmysqlpoint.controller.request.UserLocationToZoomRequest;
import mysqlpoint.realmysqlpoint.controller.request.UserLocationRequest;
import mysqlpoint.realmysqlpoint.controller.response.RestaurantLocationResponse;
import mysqlpoint.realmysqlpoint.controller.response.RestaurantNameResponse;
import mysqlpoint.realmysqlpoint.repository.RestaurantLocationSearchRepository;
import mysqlpoint.realmysqlpoint.service.RestaurantService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/v1/api")
@RequiredArgsConstructor
public class LocationController {

    private final RestaurantService restaurants;

    private final RestaurantLocationSearchRepository restaurantLocationSearchRepository;

    @GetMapping("/nearby_restaurant")
    public String map (){
        return "html/go";
    }

    @PostMapping("/nearby_restaurant")
    @ResponseBody
    public ResponseEntity<List<RestaurantNameResponse>> getNearbyRestaurants(@RequestBody UserLocationRequest userLocationRequest) {

        List<RestaurantNameResponse> getRestaurantName = restaurants.locationCheck(userLocationRequest);

        return ResponseEntity.ok().body(getRestaurantName);
    }


    @PostMapping("/restaurant_details")
    @ResponseBody
    public ResponseEntity<RestaurantLocationResponse> getRestaurantDetails(@RequestBody RestaurantLocationRequest userLocationRequest) {

        RestaurantLocationResponse search = restaurantLocationSearchRepository.search(userLocationRequest);

        return ResponseEntity.ok().body(search);
    }

    @PostMapping("/restaurant_list_view")
    @ResponseBody
    public UserLocationToZoomRequest getReceiveListViewByZoomLevel(@RequestBody UserLocationToZoomRequest userLocationRequest) {

        log.info("현재 사용자의 위치의 목록 보기 = {} " , userLocationRequest.toString());

        return userLocationRequest;
    }



    @PostMapping("/zoom_level")
    @ResponseBody
    public ResponseEntity<List<RestaurantNameResponse>> getRestaurantsByZoomLevel(@RequestBody UserLocationToZoomRequest locationAndZoomRequest) {

        List<RestaurantNameResponse> zoomLevelRestaurant = restaurants.getZoomLevelRestaurant(locationAndZoomRequest);

        for (RestaurantNameResponse response : zoomLevelRestaurant) {
            log.info("줌 레벨에 따라 보이는가게의 정보 = {} " ,response);
        }

        return ResponseEntity.ok().body(zoomLevelRestaurant);
    }

}
