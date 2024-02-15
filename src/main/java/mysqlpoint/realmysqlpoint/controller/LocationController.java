package mysqlpoint.realmysqlpoint.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mysqlpoint.realmysqlpoint.controller.request.UserLocationAndZoomRequest;
import mysqlpoint.realmysqlpoint.controller.request.RestaurantLocationRequest;
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

    @GetMapping("/restaurant")
    public String map (){
        return "html/go";
    }

    @PostMapping("/restaurant")
    @ResponseBody
    public ResponseEntity<List<RestaurantNameResponse>> receiveLocationData(@RequestBody UserLocationRequest userLocationRequest) {

        List<RestaurantNameResponse> getRestaurantName = restaurants.locationCheck(userLocationRequest);

        for (RestaurantNameResponse locations : getRestaurantName) {
            log.info("주변 찾은 가게 = {}" , locations.toString());
        }

        return ResponseEntity.ok().body(getRestaurantName);
    }


    @PostMapping("/sendRestaurantName")
    @ResponseBody
    public ResponseEntity<RestaurantLocationResponse> restaurantName(@RequestBody RestaurantLocationRequest userLocationRequest) {

        log.info("가게 위치 정보 = {}" , userLocationRequest.getMessage());
        log.info("가게 위치 정보 = {}" , userLocationRequest.getLatitude());
        log.info("가게 위치 정보 = {}" , userLocationRequest.getLongitude());

        RestaurantLocationResponse search = restaurantLocationSearchRepository.search(userLocationRequest);

        log.info("찾은 가게 정보 = {}" , search.toString());

        return ResponseEntity.ok().body(search);
    }


    @PostMapping("/zoomLevel")
    @ResponseBody
    public ResponseEntity<List<RestaurantNameResponse>> zoomLevel(@RequestBody UserLocationAndZoomRequest locationAndZoomRequest) {
        log.info("현제 줌 값 = {}" , locationAndZoomRequest.getZoomLevel());

        List<RestaurantNameResponse> zoomLevelRestaurant = restaurants.getZoomLevelRestaurant(locationAndZoomRequest);

        return ResponseEntity.ok().body(zoomLevelRestaurant);
    }

}
