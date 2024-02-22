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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@Slf4j
@AutoConfigureRestDocs
@WebMvcTest(RestaurantController.class)
@ExtendWith(SpringExtension.class)
class RestaurantControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RestaurantService restaurants;

    @MockBean
    private RestaurantLocationSearchRepository restaurantLocationSearchRepository;

    double neLatitude = 37.5567635;
    double neLongitude = 126.8529193;
    double swLatitude = 37.5482577;
    double swLongitude = 126.8421905;

    @Test
    @DisplayName("가게 이름 , 가게의 위치 정보를 통해서 특정 가게정보 찾기")
    public void getRestaurantDetailsController() throws Exception {


    }

    @Test
    public void queryTest() throws Exception {



        Restaurant restaurant30 = createRestaurant(37.549636, 126.842299, "우정산 폴리텍대학", "우정산 서울강서 캠퍼스");
        Restaurant restaurant31 = createRestaurant(37.550256, 126.846277, "우장산 앞 카페코나 퀸즈 강서점", "우장산 앞 카페코나 퀸즈 강서점");

        List<RestaurantLocationResponse> mockResponseList = List.of(
                RestaurantLocationResponse.of(restaurant30),
                RestaurantLocationResponse.of(restaurant31)
        );

        UserLocationRequest locationPolygonBounds = new UserLocationRequest(neLatitude, neLongitude, swLatitude, swLongitude);


        given(restaurants.getRestaurantSearch(any(UserLocationRequest.class)))
                .willReturn(mockResponseList);


        mvc.perform(get("/v1/api/nearby-restaurant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(locationPolygonBounds)))
                .andExpect(jsonPath("$[0].name").value("우정산 폴리텍대학"))
                .andExpect(jsonPath("$[1].name").value("우장산 앞 카페코나 퀸즈 강서점"))
                .andExpect(status().isOk())
                .andDo(print());
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