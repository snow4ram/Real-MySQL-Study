package mysqlpoint.realmysqlpoint.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import mysqlpoint.realmysqlpoint.controller.request.UserLocationRequest;
import mysqlpoint.realmysqlpoint.controller.response.RestaurantLocationDTO;
import mysqlpoint.realmysqlpoint.controller.response.RestaurantLocationResponse;
import mysqlpoint.realmysqlpoint.entity.Restaurant;

import mysqlpoint.realmysqlpoint.repository.RestaurantRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantService {


    private final RestaurantRepository polygonRepository;


    public List<RestaurantLocationResponse> getRestaurantSearch(UserLocationRequest boundsRequest) {

        List<Restaurant> restaurant = polygonRepository.getRestaurant(boundsRequest.getNeLatitude(), boundsRequest.getNeLongitude(), boundsRequest.getSwLatitude(), boundsRequest.getSwLongitude());

        List<RestaurantLocationResponse> restaurantLocationResponses = restaurant.stream().map(RestaurantLocationResponse::of).collect(Collectors.toList());

        checkRestaurantStatus(restaurantLocationResponses);

        List<Long> itemId = restaurantLocationResponses
                .stream()
                .flatMap(restaurants -> restaurants.getStockResponses().stream().map(stock -> stock.getItem().getId()))
                .toList();
        
        return restaurantLocationResponses;
    }

    public void checkRestaurantStatus(List<RestaurantLocationResponse> restaurantSearch) {

        LocalTime userTime = LocalTime.now();

        for (RestaurantLocationResponse search : restaurantSearch) {

            StringBuilder stringBuilder = new StringBuilder();

            DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();

            Object dayTimeData = search.getTime().get(dayOfWeek.toString());

            if (dayTimeData instanceof Map) {

                Map<String, List<Integer>> todayBusinessHours = (Map<String, List<Integer>>) dayTimeData;

                List<Integer> startTime = todayBusinessHours.get("startTime");
                List<Integer> endTime = todayBusinessHours.get("endTime");
                List<Integer> breakStartTime = todayBusinessHours.get("breakStartTime");
                List<Integer> breakEndTime = todayBusinessHours.get("breakEndTime");

                LocalTime todayStartTime = LocalTime.of(startTime.get(0), startTime.get(1));
                LocalTime todayClose = LocalTime.of(endTime.get(0), endTime.get(1));
                LocalTime todayBreakStartTime = LocalTime.of(breakStartTime.get(0), breakStartTime.get(1));
                LocalTime todayBreakEndTime = LocalTime.of(breakEndTime.get(0), breakEndTime.get(1));

                if (userTime.isAfter(todayStartTime) && userTime.isBefore(todayClose)) {
                    if (!(userTime.isAfter(todayBreakStartTime) && userTime.isBefore(todayBreakEndTime))) {
                        stringBuilder.append("영업중");
                    } else {
                        stringBuilder.append("브레이크 타임");
                    }
                } else if (userTime.isAfter(todayClose) || userTime.isBefore(todayStartTime)) {
                    stringBuilder.append("영업 종료");
                }

                search.setRestaurantStatus(stringBuilder.toString());
            }
        }
    }
}
