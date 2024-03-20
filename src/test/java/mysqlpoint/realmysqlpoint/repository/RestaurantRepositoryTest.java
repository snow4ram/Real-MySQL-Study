package mysqlpoint.realmysqlpoint.repository;


import mysqlpoint.realmysqlpoint.common.enumerated.DayInfo;
import mysqlpoint.realmysqlpoint.common.enumerated.Provision;
import mysqlpoint.realmysqlpoint.common.enumerated.TimeOption;
import mysqlpoint.realmysqlpoint.controller.response.RestaurantLocationResponse;
import mysqlpoint.realmysqlpoint.controller.response.RestaurantNearbyResponse;
import mysqlpoint.realmysqlpoint.entity.Member;
import mysqlpoint.realmysqlpoint.entity.Product;
import mysqlpoint.realmysqlpoint.entity.Restaurant;
import mysqlpoint.realmysqlpoint.entity.RestaurantStock;
import mysqlpoint.realmysqlpoint.service.RestaurantService;
import mysqlpoint.realmysqlpoint.util.TimeData;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.data.domain.Page;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class RestaurantRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RestaurantStockRepository restaurantStockRepository;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private ProductRepository productRepository;

    private final GeometryFactory geometryFactory = new GeometryFactory();
    private final double restaurantLatitude = 35.835011;
    private final double restaurantLongitude = 128.728191;
    private final double userLocationLatitude = 37.552096;
    private final double userLocationLongitude = 126.845166;
    private final Integer page = 0;
    private final Integer size = 5;
    private final Double distance = 372.4291590383813;
    private final String restaurantName = "배터지게 먹소";
    private final String dongdongju = "동동주";
    private final String takju = "탁주";
    private final String restaurantCategory = "정평역 배터지게 먹소";
    private final String restaurantAddress = "정평역 배터지게 먹소";
    private final BigDecimal productPrice1 = BigDecimal.valueOf(30000);
    private final BigDecimal productPrice2 = BigDecimal.valueOf(20000);

    @Test
    @Commit
    void beforeEach() {
        Member member = Member.builder()
                .email("toss2@example.com")
                .name("toss_2")
                .nickname("toss_2")
                .phone(1012345678L)
                .certifyAt(null)
                .agreedToServiceUse(true)
                .agreedToServicePolicy(true)
                .agreedToServicePolicyUse(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(null)
                .deletedAt(null)
                .build();

        memberRepository.save(member);

        Product product1 = Product.builder()
                .name(dongdongju)
                .price(productPrice1)
                .quantity(25L)
                .alcohol(100D)
                .build();

        Product product2 = Product.builder()
                .name(takju)
                .price(productPrice2)
                .quantity(10L)
                .alcohol(100D)
                .build();

        productRepository.save(product1);
        productRepository.save(product2);

        final Coordinate coordinate = new Coordinate(restaurantLongitude, restaurantLatitude);
        Point restaurant_location = geometryFactory.createPoint(coordinate);

        Restaurant restaurant = Restaurant.builder()
                .members(member)
                .category(restaurantCategory)
                .name(restaurantName)
                .address(restaurantAddress)
                .location(restaurant_location)
                .contact(1012345678L)
                .menu(getMenuTest())
                .time(getTimeTest())
                .provision(getProvisionTest())
                .createdAt(LocalDateTime.now())
                .build();

        restaurantRepository.save(restaurant);

        RestaurantStock stock1 = RestaurantStock.builder()
                .product(product1)
                .restaurant(restaurant)
                .quantity(100L)
                .build();

        RestaurantStock stock2 = RestaurantStock.builder()
                .product(product2)
                .restaurant(restaurant)
                .quantity(150L)
                .build();

        stock1.addRestaurant(restaurant);
        stock2.addRestaurant(restaurant);

        restaurantStockRepository.save(stock1);
        restaurantStockRepository.save(stock2);
    }

    private Map<String, Object> getMenuTest() {
        Map<String, Object> frame = new LinkedHashMap<>();
        frame.put("비빔밥", 8000);
        frame.put("돌솥 비빔밥", 5000);
        frame.put("불고기", 12000);
        frame.put("백반", 8000);
        return frame;
    }

    private Map<String, Object> getTimeTest() {
        Map<String, Object> allDayTime = new LinkedHashMap<>();

        allDayTime.put(TimeOption.HOLIDAY.toString(), true);
        allDayTime.put(TimeOption.ETC.toString(), "여름/겨울 방학 휴업");

        TimeData timeData = TimeData.builder()
                .businessStatus(true)
                .startTime(LocalTime.of(9, 0))
                .endTime(LocalTime.of(22, 0))
                .breakBusinessStatus(true)
                .breakStartTime(LocalTime.of(15, 0))
                .breakEndTime(LocalTime.of(17, 0))
                .build();

        TimeData result = null;

        for (DayInfo value : DayInfo.values()) {
            allDayTime.put(value.toString(), timeData);
            result = (TimeData) allDayTime.get(value);
        }

        return allDayTime;
    }

    private Map<String, Object> getProvisionTest() {
        Map<String, Object> frame = new LinkedHashMap<>();

        for (Provision value : Provision.values()) {
            frame.put(value.toString(), true);
        }
        return frame;
    }


    @Test
    void afterEach() {
        memberRepository.deleteAll();
        restaurantRepository.deleteAll();
        restaurantStockRepository.deleteAll();
        productRepository.deleteAll();
    }
}
