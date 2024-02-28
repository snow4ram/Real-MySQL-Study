package mysqlpoint.realmysqlpoint.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import mysqlpoint.realmysqlpoint.controller.response.RestaurantLocationResponse;
import mysqlpoint.realmysqlpoint.entity.Member;
import mysqlpoint.realmysqlpoint.entity.Restaurant;
import mysqlpoint.realmysqlpoint.enumerated.MemberRole;
import mysqlpoint.realmysqlpoint.enumerated.ProviderType;
import mysqlpoint.realmysqlpoint.service.RestaurantService;
import mysqlpoint.realmysqlpoint.util.DayInfo;
import mysqlpoint.realmysqlpoint.util.Provision;
import mysqlpoint.realmysqlpoint.util.TimeData;
import mysqlpoint.realmysqlpoint.util.TimeOption;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.GeometryFactory;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
class RestaurantRepositoryCustomImplTest {

    @InjectMocks
    private RestaurantRepositoryImpl restaurantRepositoryCustom;
    @Mock
    private RestaurantService restaurantService;
    @Mock
    private EntityManager entityManager;
    @Mock
    private Query query;

    final GeometryFactory geometryFactory = new GeometryFactory();

    private String shopName = "맛있는 한식당";
    private int page = 0;
    private int size = 10;
    double neLatitude = 37.5567635;
    double neLongitude = 126.8529193;
    double swLatitude = 37.5482577;
    double swLongitude = 126.8421905;

    double lat = 37.552201;
    double lon = 126.845541;

    private final Long memberId = 1L;
    private final String email = "test20@example.com";
    private final String provider = ProviderType.KAKAO.getProviderName();
    private final String memberName = "test_20";
    private final String nickname = "test_20";
    private final String role = MemberRole.MEMBER.getRole();
    private final Long phone = 1012345678L;
    private final LocalDate certifyAt = LocalDate.now();
    private final boolean agreedToServiceUse = true;
    private final boolean agreedToServicePolicy = true;
    private final boolean agreedToServicePolicyUse = true;
    private final LocalDateTime memberCreatedAt = LocalDateTime.now();

    private final Long id = 1L;
    private final String category = "한식";
    private final String name = "맛있는 한식당";
    private final String address = "서울시 강남구";
    private final Double longitude = 126.845541;
    private final Double latitude = 37.552201;
    private final Long contact = 1012345678L;

    private Map<String, Object> getMenuTest() {
        Map<String, Object> frame = new LinkedHashMap<>();
        frame.put("비빔밥", 8000);
        frame.put("불고기", 12000);
        return frame;
    }

    private Map<String, Object> getTimeTest() {
        Map<String, Object> allDayTime = new LinkedHashMap<>();

        allDayTime.put(TimeOption.HOLIDAY.toString(), true);
        allDayTime.put(TimeOption.ETC.toString(), "명절 당일만 휴업");

        TimeData timeData = TimeData.builder()
                .businessStatus(true)
                .startTime(LocalTime.of(9, 0))
                .endTime(LocalTime.of(22, 0))
                .breakBusinessStatus(true)
                .breakStartTime(LocalTime.of(15, 0))
                .breakEndTime(LocalTime.of(17, 0))
                .build();

        for (DayInfo value : DayInfo.values()) {
            allDayTime.put(value.toString(), timeData);
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

    private final Map<String, Object> menu = getMenuTest();
    private final Map<String, Object> time = getTimeTest();
    private final Map<String, Object> provision = getProvisionTest();
    private final LocalDateTime createdAt = LocalDateTime.now();

    @BeforeEach
    void setUp() {
        Query mockedQuery = mock(Query.class);

        when(mockedQuery.getResultList()).thenReturn(Collections.emptyList());

        when(entityManager.createNativeQuery(anyString(), eq(Restaurant.class))).thenReturn(mockedQuery);

        when(mockedQuery.setParameter(anyString(), any())).thenReturn(mockedQuery);
    }

    @Test
    public void getSearchRestaurant() {

        PageRequest pageRequest = PageRequest.of(page, size);

        Page<RestaurantLocationResponse> restaurants = this.getRestaurants();

        Restaurant restaurantData = getRestaurantData();

        //List<Restaurant> restaurants = this.getRestaurants();



    }

    private Page<RestaurantLocationResponse> getRestaurants() {
        List<RestaurantLocationResponse> list = List.of(RestaurantLocationResponse.of(this.getRestaurantData()));
        Pageable pageable = PageRequest.of(page, size);
        return new PageImpl<RestaurantLocationResponse>(list, pageable, list.size());
    }

//    private List<Restaurant> getRestaurants() {
//        return List.of(this.getRestaurantData());
//        Pageable pageable = PageRequest.of(page, size);
//        return new PageImpl<Restaurant>(list, pageable, list.size());
//    }



    private Restaurant getRestaurantData() {
        Member member = getMemberData();

        return Restaurant.builder()
                .id(id)
                .members(member)
                .category(category)
                .name(name)
                .address(address)
                .location(Restaurant.genPoint(longitude, latitude))
                .contact(contact)
                .menu(menu)
                .time(time)
                .provision(provision)
                .createdAt(createdAt)
                .build();
    }





    private Member getMemberData() {
        return Member.builder()
                .id(memberId)
                .email(email)
                .provider(ProviderType.ofProvider(provider))
                .name(memberName)
                .nickname(nickname)
                .role(MemberRole.ofRole(role))
                .phone(phone)
                .certifyAt(certifyAt)
                .agreedToServiceUse(agreedToServiceUse)
                .agreedToServicePolicy(agreedToServicePolicy)
                .agreedToServicePolicyUse(agreedToServicePolicyUse)
                .createdAt(memberCreatedAt)
                .build();
    }


}