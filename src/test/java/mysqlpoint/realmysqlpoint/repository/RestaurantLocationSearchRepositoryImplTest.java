package mysqlpoint.realmysqlpoint.repository;

import lombok.extern.slf4j.Slf4j;

import mysqlpoint.realmysqlpoint.controller.response.RestaurantLocationResponse;
import mysqlpoint.realmysqlpoint.controller.response.RestaurantNearbyLocationResponse;
import mysqlpoint.realmysqlpoint.entity.Item;
import mysqlpoint.realmysqlpoint.entity.Member;
import mysqlpoint.realmysqlpoint.entity.Restaurant;
import mysqlpoint.realmysqlpoint.entity.RestaurantStock;
import mysqlpoint.realmysqlpoint.enumerated.MemberRole;
import mysqlpoint.realmysqlpoint.enumerated.ProviderType;
import mysqlpoint.realmysqlpoint.util.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@SpringBootTest
@ExtendWith(SpringExtension.class)
class RestaurantLocationSearchRepositoryImplTest {

    @Autowired
    private JpaMemberRepository memberRepository;

    @Autowired
    private JpaRestaurantRepository restaurantRepository;

    @Autowired
    private JpaItemRepository itemRepository;

    @Autowired
    private JpaRestaurantStockRepository restaurantStockRepository;

    @Autowired
    private RestaurantRepositoryImpl restaurantRepositoryImpl;

    @Autowired
    private RestaurantPolygonRepository repository;

    double neLatitude = 37.5567635;
    double neLongitude = 126.8529193;
    double swLatitude = 37.5482577;
    double swLongitude = 126.8421905;

    double lat = 37.552201;
    double lon = 126.845541;


    @Test
    public void search() {

//        List<RestaurantLocationResponse> allItemsAndQuantitiesWithinPolygonForRestaurant = restaurantRepositoryImpl
//                .findAllItemsAndQuantitiesWithinPolygonForRestaurant(neLatitude, neLongitude, swLatitude, swLongitude, lat, lon);
//
//        for (RestaurantLocationResponse restaurantLocationResponse : allItemsAndQuantitiesWithinPolygonForRestaurant) {
//            log.info("조회 결과 = {}" , restaurantLocationResponse);
//        }

        List<RestaurantNearbyLocationResponse> allItemsAndQuantitiesWithinPolygonForRestaurant = repository.findAllItemsAndQuantitiesWithinPolygonForRestaurant(neLatitude, neLongitude, swLatitude, swLongitude);
        for (RestaurantNearbyLocationResponse restaurant : allItemsAndQuantitiesWithinPolygonForRestaurant) {
            log.info("레스토랑 정보 ={} " , restaurant);
        }
    }


    @Test
    public void init() {

        restaurantRepository.findAllItemsAndQuantitiesWithinPolygonForRestaurant(neLatitude, neLongitude, swLatitude, swLongitude);



    }

    final GeometryFactory geometryFactory = new GeometryFactory();

    private Map<String, Object> getMenuTest() {
        Map<String, Object> frame = new LinkedHashMap<>();
        frame.put("비빔밥", 8000);
        frame.put("돌솥 비빔밥", 5000);
        frame.put("불고기", 12000);
        return frame;
    }

    @Test
    public void inti() {
        Map<String, Object> timeTest = getTimeTest();

        log.info("영업 시간 = {}" , timeTest);
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

        System.out.println(result.getEndTime());

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
    @Commit
    void beforeEach() {
        Member member = Member.builder()
                .email("toss1@example.com")
                .provider(ProviderType.KAKAO)
                .name("toss_1")
                .nickname("toss_1")
                .role(MemberRole.MEMBER)
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


        Item item1 = Item.builder()
                .type(ItemType.PROMOTION)
                .name("item 1")
                .price(BigDecimal.valueOf(20000))
                .info("item 1 info")
                .build();
        Item item2 = Item.builder()
                .type(ItemType.PROMOTION)
                .name("item_2")
                .price(BigDecimal.valueOf(20000))
                .info("item_2_info")
                .build();

        itemRepository.save(item1);
        itemRepository.save(item2);

        final Coordinate coordinate = new Coordinate(126.842299, 37.549636);
        Point restaurant_location = geometryFactory.createPoint(coordinate);
        Restaurant restaurant = Restaurant.builder()
                .members(member)
                .category("학식")
                .name("우정산 폴리텍대학")
                .address("우정산 서울강서 캠퍼스")
                .location(restaurant_location)
                .contact(1012345678L)
                .menu(getMenuTest())
                .time(getTimeTest())
                .provision(getProvisionTest())
                .createdAt(LocalDateTime.now())
                .build();

        restaurantRepository.save(restaurant);

        RestaurantStock stock1 = RestaurantStock.builder()
                .item(item1)
                .restaurant(restaurant)
                .quantity(100L) // 예시로 100을 설정했습니다. 필요한 수량에 맞게 조정하세요.
                .build();

        RestaurantStock stock2 = RestaurantStock.builder()
                .item(item2)
                .restaurant(restaurant)
                .quantity(150L) // 예시로 150을 설정했습니다. 필요한 수량에 맞게 조정하세요.
                .build();

        stock1.addItem(item1);
        stock1.addItem(item2);

        stock1.addRestaurant(restaurant);

        // RestaurantStock 인스턴스 저장
        restaurantStockRepository.save(stock1);
        restaurantStockRepository.save(stock2);


    }
}