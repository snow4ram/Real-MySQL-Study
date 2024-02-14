package mysqlpoint.realmysqlpoint.restaurant;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import mysqlpoint.realmysqlpoint.entity.Location;
import mysqlpoint.realmysqlpoint.entity.Restaurant;
import mysqlpoint.realmysqlpoint.repository.JpaRestaurantRepository;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.swing.*;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class GetUserLocationRestaurantTest {

    @Autowired
    private JpaRestaurantRepository repository;

    @Autowired
    private RestaurantService solution;

    @Test
    @Commit
    public void main() {
        //35.840526  128.700646
        //35.8267887 128.7163574
        Location location = new Location(35.844847 ,  128.695932);

        // JTS 라이브러리의 GeometryFactory를 사용하여 Point 객체 생성
        GeometryFactory geometryFactory = new GeometryFactory();
        Point point = geometryFactory.createPoint(new org.locationtech.jts.geom.Coordinate(location.getLongitude(), location.getLatitude()));

        // 메뉴 객체 생성 및 초기화
        Map<String, Object> menu = new HashMap<>();
        menu.put("kimchi", Map.of("price", BigDecimal.valueOf(5)));
        menu.put("bibimbap", Map.of("price", BigDecimal.valueOf(10)));

        // 영업시간 객체 생성 및 초기화
        Map<String, Object> time = new HashMap<>();
        time.put(DayInfo.MONDAY.name(), Map.of("open", LocalTime.of(9, 0), "close", LocalTime.of(21, 0)));

        // 편의시설 객체 생성 및 초기화
        Map<String, Object> provision = new HashMap<>();
        provision.put(Provision.WIFI.name(), true);
        provision.put(Provision.PET.name(), true);

        // Restaurant 인스턴스 생성
        Restaurant restaurant = Restaurant.builder()
                .category("Korean")
                .name("매호 BBQ 치킨")
                .address("매호 BBQ 치킨")
                .location(point)
                .contact(10323000001L)
                .menu(menu)
                .time(time)
                .provision(provision)
                .build();

        // Restaurant 인스턴스 출력 (예시를 위한 toString() 사용)
        repository.save(restaurant);
    }

    @Test
    @DisplayName("NativeQuery 사용")
    public void point_find() {

        double latitude = 35.8393357; //위도
        double longitude = 128.7210818; //경도
        double r = 1000;

        final GeometryFactory geometryFactory = new GeometryFactory();
        Point point = geometryFactory.createPoint(new Coordinate(longitude, latitude));

        List<Restaurant> restaurants = repository.findAllWithinPoint(latitude , longitude , r);

        for (Restaurant restaurant : restaurants) {
            log.info("결과값 = {}", restaurant);
        }

    }

    @Test
    @DisplayName("스프링 JPA Query 사용")
    public void point() {

        double latitude = 35.8393357; //위도

        double longitude = 128.7210818; //경도

        double r = 1000;

        final GeometryFactory geometryFactory = new GeometryFactory();
        Point point = geometryFactory.createPoint(new Coordinate(longitude, latitude));

        List<Restaurant> restaurants = repository.findAllWithinPoints(point , r);

        for (Restaurant restaurant : restaurants) {
            log.info("결과값 = {}", restaurant);
        }



    }
}
