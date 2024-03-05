package mysqlpoint.realmysqlpoint.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mysqlpoint.realmysqlpoint.controller.request.UserLocationRequest;
import mysqlpoint.realmysqlpoint.controller.request.RestaurantSearchLocationRequest;
import mysqlpoint.realmysqlpoint.controller.response.RestaurantLocationResponse;
import mysqlpoint.realmysqlpoint.entity.Restaurant;
import mysqlpoint.realmysqlpoint.service.RestaurantService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/v1/api")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurants;

    @GetMapping("/restaurant")
    public ResponseEntity<List<RestaurantLocationResponse>> nearby(@RequestBody UserLocationRequest request) {

        List<RestaurantLocationResponse> restaurantSearch = restaurants.getRestaurantSearch(request);

        return ResponseEntity.ok().body(restaurantSearch);
    }

    @ResponseBody
    @PostMapping("/restaurant_list_view")
    public RestaurantSearchLocationRequest getReceiveListViewByZoomLevel(@RequestBody RestaurantSearchLocationRequest userLocationRequest) {

        log.info("현재 사용자의 위치의 목록 보기 = {} ", userLocationRequest.toString());


        return userLocationRequest;
    }

}
