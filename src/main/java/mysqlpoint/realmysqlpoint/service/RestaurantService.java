package mysqlpoint.realmysqlpoint.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import mysqlpoint.realmysqlpoint.controller.request.UserLocationRequest;
import mysqlpoint.realmysqlpoint.controller.request.RestaurantSearchLocationRequest;
import mysqlpoint.realmysqlpoint.controller.response.RestaurantLocationResponse;
import mysqlpoint.realmysqlpoint.controller.response.RestaurantNameResponse;
import mysqlpoint.realmysqlpoint.entity.Restaurant;
import mysqlpoint.realmysqlpoint.repository.JpaRestaurantRepository;
import mysqlpoint.realmysqlpoint.util.RestaurantMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


import java.awt.print.Pageable;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final JpaRestaurantRepository restaurantRepository;

    private final RestaurantMapper restaurantNameResponseMapper;

    public List<RestaurantLocationResponse> getRestaurantSearch(UserLocationRequest boundsRequest) {

        double lat = 37.551779;
        double lon = 126.843123;

        List<Restaurant> allWithinPolygon = restaurantRepository.findAllWithinPoint(lat,lon, boundsRequest.getNeLatitude(), boundsRequest.getNeLongitude(), boundsRequest.getSwLatitude(), boundsRequest.getSwLongitude());

        return allWithinPolygon.stream().map(RestaurantLocationResponse::of).collect(Collectors.toList());
    }


    public void getSearch(int page , int pageSize , double neLatitude, double neLongitude, double swLatitude, double swLongitude) {

        PageRequest pageable = PageRequest.of(page - 1, pageSize);

        int start = (int) pageable.getOffset();

        int end = Math.min((start + pageable.getPageSize()), 15);

        log.info("현제 페이지 = {}" , start);
        log.info("마지막 페이지 = {}" , end);

    }



    public void getRestaurantNameResponses(RestaurantSearchLocationRequest locationAndZoomRequest) {
//        List<Restaurant> everythingWithinA17PointRadius =
//                restaurantRepository.findAllWithinPoint(locationAndZoomRequest.getLatitude(), locationAndZoomRequest.getLongitude(), locationAndZoomRequest.getRadius());
//
//        return everythingWithinA17PointRadius.stream()
//                .map(restaurantNameResponseMapper)
//                .collect(Collectors.toList());
    }


}
