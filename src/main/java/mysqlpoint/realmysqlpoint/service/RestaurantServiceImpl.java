package mysqlpoint.realmysqlpoint.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mysqlpoint.realmysqlpoint.common.response.HttpResponse;
import mysqlpoint.realmysqlpoint.controller.response.RestaurantLocationResponse;
import mysqlpoint.realmysqlpoint.controller.response.RestaurantNearbyResponse;
import mysqlpoint.realmysqlpoint.entity.Restaurant;
import mysqlpoint.realmysqlpoint.exception.BusinessException;
import mysqlpoint.realmysqlpoint.repository.RestaurantRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static mysqlpoint.realmysqlpoint.util.RestaurantTimeVerification.getRestaurantBusinessStatus;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Override
    public List<RestaurantLocationResponse> getRestaurants(double neLatitude, double neLongitude, double swLatitude, double swLongitude) {

        List<Restaurant> get = restaurantRepository.getRestaurant(neLatitude, neLongitude, swLatitude, swLongitude);

        List<RestaurantLocationResponse> restaurant = get.stream()
                .map(RestaurantLocationResponse::of)
                .collect(Collectors.toList());

        getRestaurantBusinessStatus(restaurant , LocalTime.now());

        return restaurant;
    }

    @Override
    public Page<RestaurantNearbyResponse> get(double userLocationLatitude, double userLocationLongitude, String keyword, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);

        Page<RestaurantNearbyResponse> search = restaurantRepository.getRestaurantSellingProducts(userLocationLatitude, userLocationLongitude , keyword, pageable);

        if (search.isEmpty()) throw BusinessException.builder().response(HttpResponse.Fail.NOT_FOUND_RESTAURANT).build();

        return search;

    }
}
