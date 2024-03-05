package mysqlpoint.realmysqlpoint.controller.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import mysqlpoint.realmysqlpoint.entity.Restaurant;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Getter
@ToString
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class RestaurantLocationResponse {

    @Schema(description = "레스토랑 고유아이디")
    private Long id;

    @Schema(description = "회원 고유아이디")
    private Long memberId;

    @Schema(description = "매장 카테고리")
    private String category;

    @Schema(description = "매장 이름")
    private String name;

    @Schema(description = "매장 주소")
    private String address;

//    @Schema(description = "매장 위치(위도 , 경도)")
//    private Point location;
    private Double lon;

    private Double lat;

    @Schema(description = "매장 연락처")
    private Long contact;

    @Schema(description = "매장 목록")
    private Map<String, Object> menu;

    @Schema(description = "영업 시간")
    private Map<String, Object> time;

    @Schema(description = "편의시설 목록")
    private Map<String, Object> provision;

    private List<RestaurantStockResponse> stockResponses;

    @Schema(description = "레스토랑 영업여부")
    private String status;

    public void setRestaurantStatus(String string) {
        this.status = string;

    }

    public static RestaurantLocationResponse of(Restaurant restaurant) {
        List<RestaurantStockResponse> collect = restaurant
                .getRestaurantStocks()
                .stream()
                .map(RestaurantStockResponse::of)
                .collect(Collectors.toList());

        return RestaurantLocationResponse.builder()
                .id(restaurant.getId())
                .memberId(restaurant.getId())
                .category(restaurant.getCategory())
                .name(restaurant.getName())
                .address(restaurant.getAddress())
                .lon(restaurant.getLocation().getY())
                .lat(restaurant.getLocation().getX())
                .contact(restaurant.getContact())
                .menu(restaurant.getMenu())
                .time(restaurant.getTime())
                .stockResponses(collect)
                .build();
    }

}
