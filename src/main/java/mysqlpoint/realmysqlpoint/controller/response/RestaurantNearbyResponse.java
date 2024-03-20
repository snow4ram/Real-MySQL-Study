package mysqlpoint.realmysqlpoint.controller.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(description = "레스토랑의 주변에 제품을 취급에 대한 응답")
public class RestaurantNearbyResponse {

    @Schema(description = "레스토랑 고유아이디")
    private Long restaurantId;

    @Schema(description = "매장 이름")
    private String restaurantName;

    @Schema(description = "매장 주소")
    private String address;

    @Schema(description = "제품의 이름")
    private String productName;

    @Schema(description = "사용자 위치로부터의 거리 (미터 단위)")
    private Double distance;

    public RestaurantNearbyResponse(Long restaurantId, String restaurantName, String address, String productName, Double distance) {
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.address = address;
        this.productName = productName;
        this.distance = distance;
    }
}
