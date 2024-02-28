package mysqlpoint.realmysqlpoint.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mysqlpoint.realmysqlpoint.controller.request.UserLocationRequest;
import mysqlpoint.realmysqlpoint.controller.request.RestaurantSearchLocationRequest;
import mysqlpoint.realmysqlpoint.controller.response.RestaurantLocationResponse;
import mysqlpoint.realmysqlpoint.controller.response.RestaurantNearbyLocationResponse;
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
    public ResponseEntity<List<RestaurantNearbyLocationResponse>> nearby(@RequestBody UserLocationRequest request) {
        List<RestaurantNearbyLocationResponse> restaurantSearch = restaurants.getRestaurantSearch(request);
        return ResponseEntity.ok().body(restaurantSearch);
    }


    @ResponseBody
    @PostMapping("/restaurant_list_view")
    public RestaurantSearchLocationRequest getReceiveListViewByZoomLevel(@RequestBody RestaurantSearchLocationRequest userLocationRequest) {

        log.info("현재 사용자의 위치의 목록 보기 = {} ", userLocationRequest.toString());

        return userLocationRequest;
    }


    @GetMapping("/search")
    public String searchRestaurantName(
            @RequestParam(name = "keyword" ,required = false) String keyword,
            @RequestParam(name = "page", defaultValue = "0") int page, // 수정된 부분
            @RequestParam(name = "size", defaultValue = "10") int pageSize, // 수정된 부분
            Model model) {

        // 로깅 정보
        log.info("검색 키워드 = {}", keyword);
        log.info("페이지 번호 = {}", page);
        log.info("페이지 크기 = {}", pageSize);

        double neLatitude = 37.5567635;
        double neLongitude = 126.8529193;
        double swLatitude = 37.5482577;
        double swLongitude = 126.8421905;

        double lat = 37.552201;
        double lon = 126.845541;



        return "/html/search";
    }

}
