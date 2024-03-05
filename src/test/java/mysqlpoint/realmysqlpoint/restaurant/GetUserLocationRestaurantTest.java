package mysqlpoint.realmysqlpoint.restaurant;

import lombok.extern.slf4j.Slf4j;
import mysqlpoint.realmysqlpoint.entity.*;
import mysqlpoint.realmysqlpoint.enumerated.MemberRole;
import mysqlpoint.realmysqlpoint.enumerated.ProviderType;
import mysqlpoint.realmysqlpoint.repository.JpaMemberRepository;
import mysqlpoint.realmysqlpoint.repository.JpaRestaurantRepository;
import mysqlpoint.realmysqlpoint.repository.JpaRestaurantStockRepository;
import mysqlpoint.realmysqlpoint.repository.JpaItemRepository;
import mysqlpoint.realmysqlpoint.util.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.Map;


@Slf4j
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class GetUserLocationRestaurantTest {
    @Autowired
    private JpaRestaurantRepository repository;
    @Autowired
    private JpaMemberRepository memberRepository;
    @Autowired
    private JpaItemRepository itemRepository;
    @Autowired
    private JpaRestaurantStockRepository restaurantStockRepository;
    final GeometryFactory geometryFactory = new GeometryFactory();

    private String shopName = "학식";
    private final String email = "test4@example.com";
    private final String provider = ProviderType.KAKAO.getProviderName();
    private final String memberName = "test_4";
    private final String nickname = "test_4";
    private final String itemName1 = " item_name_test_7";
    private final String itemInfo1 = " item_info_test_7";
    private final String itemName2 = " item_name_test_8";
    private final String itemInfo2 = " item_info_test_8";
    private final String address = "서울시 강남구";
    private final String role = MemberRole.MEMBER.getRole();
    private final LocalDate certifyAt = LocalDate.now();
    private final boolean agreedToServiceUse = true;
    private final boolean agreedToServicePolicy = true;
    private final boolean agreedToServicePolicyUse = true;
    private final LocalDateTime memberCreatedAt = LocalDateTime.now();
    double lat = 37.551597;
    double lon = 126.846507;
    @Test
    @Commit
    public void main() {

        Member build = Member.builder()
                .email(email)
                .provider(ProviderType.ofProvider(provider))
                .name(memberName)
                .nickname(nickname)
                .role(MemberRole.ofRole(role))
                .phone(1012345678L)
                .certifyAt(certifyAt)
                .agreedToServiceUse(agreedToServiceUse)
                .agreedToServicePolicy(agreedToServicePolicy)
                .agreedToServicePolicyUse(agreedToServicePolicyUse)
                .createdAt(memberCreatedAt)
                .build();

        memberRepository.save(build);


        Item item1 = Item.builder()
                .type(ItemType.PROMOTION)
                .name(itemName1)
                .price(BigDecimal.valueOf(20000))
                .info(itemInfo1)
                .build();
        Item item2 = Item.builder()
                .type(ItemType.PROMOTION)
                .name(itemName2)
                .price(BigDecimal.valueOf(20000))
                .info(itemInfo2)
                .build();

        itemRepository.save(item1);
        itemRepository.save(item2);

        Restaurant restaurant = Restaurant.builder()
                .members(build)
                .category("Korean")
                .name(shopName)
                .address(address)
                .location(Restaurant.genPoint(lon, lat))
                .contact(10323000001L)
                .menu(getMenuTest())
                .time(getTimeTest())
                .provision(getProvisionTest())
                .createdAt(LocalDateTime.now())
                .build();


        repository.save(restaurant);


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
        stock1.addRestaurant(restaurant);

        stock2.addItem(item2);
        stock2.addRestaurant(restaurant);

// RestaurantStock 인스턴스 저장
        restaurantStockRepository.save(stock1);
        restaurantStockRepository.save(stock2);
    }


    private Map<String, Object> getMenuTest() {
        Map<String, Object> frame = new LinkedHashMap<>();
        frame.put("비빔밥", 8000);
        frame.put("불고기", 12000);
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




}
