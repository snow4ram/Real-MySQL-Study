package mysqlpoint.realmysqlpoint.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mysqlpoint.realmysqlpoint.common.response.PageResponse;
import mysqlpoint.realmysqlpoint.controller.request.RestaurantLocationRequest;
import mysqlpoint.realmysqlpoint.controller.request.UserLocationRequest;
import mysqlpoint.realmysqlpoint.controller.response.RestaurantLocationResponse;
import mysqlpoint.realmysqlpoint.controller.response.RestaurantNearbyResponse;
import mysqlpoint.realmysqlpoint.service.RestaurantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/restaurants")
@Tag(name = "v1-restaurants-controller", description = "레스토랑 관련 API")
public class RestaurantController {

    private final RestaurantService restaurants;

    @GetMapping
    public ResponseEntity<List<RestaurantLocationResponse>> nearby(@RequestBody RestaurantLocationRequest request) {

        List<RestaurantLocationResponse> restaurantSearch = restaurants.getRestaurants(request.getNeLatitude() , request.getNeLongitude() , request.getSwLatitude() ,request.getSwLongitude());

        return ResponseEntity.ok().body(restaurantSearch);
    }

    @GetMapping("/nearby")
    public ResponseEntity<PageResponse<RestaurantNearbyResponse>> getRestaurantsWithinNearby(
            @RequestBody UserLocationRequest locationRequest
    ) {
        PageResponse<RestaurantNearbyResponse> dev = PageResponse.of(restaurants.get(
                locationRequest.getUserLocationLatitude(),
                locationRequest.getUserLocationLongitude(),
                locationRequest.getKeyword(),
                locationRequest.getPage(),
                locationRequest.getSize()));
        return ResponseEntity.ok().body(dev);
    }

}
