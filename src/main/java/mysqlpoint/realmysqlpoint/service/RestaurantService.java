package mysqlpoint.realmysqlpoint.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import mysqlpoint.realmysqlpoint.controller.request.UserLocationRequest;
import mysqlpoint.realmysqlpoint.controller.request.RestaurantSearchLocationRequest;
import mysqlpoint.realmysqlpoint.controller.response.RestaurantLocationResponse;
import mysqlpoint.realmysqlpoint.entity.Restaurant;
import mysqlpoint.realmysqlpoint.repository.JpaRestaurantRepository;
import mysqlpoint.realmysqlpoint.repository.custom.RestaurantRepositoryCustomImpl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final JpaRestaurantRepository restaurantRepository;

    private final RestaurantRepositoryCustomImpl restaurantRepositoryCustom;

    public List<RestaurantLocationResponse> getRestaurantSearch(UserLocationRequest boundsRequest) {

        double lat = 37.551779;
        double lon = 126.843123;

        List<Restaurant> allWithinPolygon = restaurantRepository.findAllWithinPolygon(boundsRequest.getNeLatitude(), boundsRequest.getNeLongitude(), boundsRequest.getSwLatitude(), boundsRequest.getSwLongitude());

        return allWithinPolygon.stream().map(RestaurantLocationResponse::of).collect(Collectors.toList());
    }


    public Page<RestaurantLocationResponse> getSearch(String keyword , double neLatitude, double neLongitude, double swLatitude, double swLongitude , double lat , double lon ,int page  , int pageSize) {

        PageRequest pages = PageRequest.of(page , pageSize);

        return restaurantRepositoryCustom.getSearchRestaurantsInArea(
                keyword, swLatitude, swLongitude, neLatitude, neLongitude, lat , lon, pages);
    }

}
