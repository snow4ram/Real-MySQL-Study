package mysqlpoint.realmysqlpoint.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import mysqlpoint.realmysqlpoint.controller.request.UserLocationRequest;
import mysqlpoint.realmysqlpoint.controller.response.RestaurantLocationResponse;
import mysqlpoint.realmysqlpoint.entity.Restaurant;
import mysqlpoint.realmysqlpoint.service.RestaurantService;
import mysqlpoint.realmysqlpoint.util.DayInfo;
import mysqlpoint.realmysqlpoint.util.Provision;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


import java.math.BigDecimal;
import java.time.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@Slf4j
@SpringBootTest
@ExtendWith(SpringExtension.class)
class RestaurantControllerTest {

    @Autowired
    private RestaurantService restaurants;
    String openTime;
    String closedTime;
    String breakTime;

    double neLatitude = 37.5567635;
    double neLongitude = 126.8529193;
    double swLatitude = 37.5482577;
    double swLongitude = 126.8421905;

    @Test
    @DisplayName("가게 이름 , 가게의 위치 정보를 통해서 특정 가게정보 찾기")
    public void getRestaurantDetailsController() throws Exception {


    }

    @Test
    public void queryTest() {

        UserLocationRequest locationPolygonBounds = new UserLocationRequest(neLatitude, neLongitude, swLatitude, swLongitude);


        List<RestaurantLocationResponse> restaurantSearch = restaurants.getRestaurantSearch(locationPolygonBounds);

        LocalTime time = LocalTime.now();

        LocalTime userTime = LocalTime.of(16, 30);

        for (RestaurantLocationResponse search : restaurantSearch) {
            log.info("가게정보 = {}" , search);
        }

    }


    private Map<String, Object> createMenu() {
        return Map.of(
                "kimchi", Map.of("price", BigDecimal.valueOf(5)),
                "bibimbap", Map.of("price", BigDecimal.valueOf(10))
        );
    }
    private Map<String, Object> createTime() {
        return Map.of(
                DayInfo.MONDAY.name(), Map.of("open", LocalTime.of(9, 0), "close", LocalTime.of(21, 0))
        );
    }
    private Map<String, Object> createProvision() {
        return Map.of(
                Provision.WIFI.name(), true,
                Provision.PET.name(), true
        );
    }
    private Restaurant createRestaurant(double latitude, double longitude, String name, String address) {
        GeometryFactory geometryFactory = new GeometryFactory();
        Point point = geometryFactory.createPoint(new Coordinate(longitude, latitude));
        return Restaurant.builder()
                .category("Korean")
                .name(name)
                .address(address)
                .location(point)
                .contact(10323000001L)
                .menu(createMenu())
                .time(createTime())
                .provision(createProvision())
                .build();
    }




}