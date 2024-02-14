package mysqlpoint.realmysqlpoint.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mysqlpoint.realmysqlpoint.controller.request.UserLocationRequest;
import mysqlpoint.realmysqlpoint.controller.response.RestaurantLocationResponse;
import mysqlpoint.realmysqlpoint.controller.response.RestaurantNameResponse;
import mysqlpoint.realmysqlpoint.service.RestaurantLocationSearchRepository;
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
    public ResponseEntity<List<RestaurantNameResponse>> receiveLocationData(@RequestBody UserLocationRequest locationData) {

        List<RestaurantNameResponse> getRestaurantName = restaurants.locationCheck(locationData);

        for (RestaurantNameResponse locations : getRestaurantName) {
            log.info("주변 찾은 가게 = {}" , locations.toString());
        }

        return ResponseEntity.ok().body(getRestaurantName);
    }


    @PostMapping("/sendRestaurantName")
    @ResponseBody
    public ResponseEntity<RestaurantLocationResponse> restaurantName(@RequestBody UserLocationRequest locationData) {

        log.info("가게 위치 정보 = {}" , locationData.getMessage());
        log.info("가게 위치 정보 = {}" , locationData.getLatitude());
        log.info("가게 위치 정보 = {}" , locationData.getLongitude());

        RestaurantLocationResponse search = restaurantLocationSearchRepository.search(locationData);

        log.info("찾은 가게 정보 = {}" , search.toString());

        return ResponseEntity.ok().body(search);
    }

}
