package mysqlpoint.realmysqlpoint.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mysqlpoint.realmysqlpoint.entity.RestaurantStock;
import org.locationtech.jts.geom.Point;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Slf4j
@Getter
@Schema(description = "레스토랑 상세 조회하는 요청")
public class RestaurantLocationDTO {

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

    @Schema(description = "매장 위치(위도 , 경도)")
    private Point location;

    @Schema(description = "매장 연락처")
    private Long contact;

    @Schema(description = "매장 목록")
    private Map<String, Object> menu;

    @Schema(description = "영업 시간")
    private Map<String, Object> time;


    private List<RestaurantStockResponse> restaurantStocks;

    public List<RestaurantStockResponse>  of(List<RestaurantStock> restaurantStocks) {
        return restaurantStocks.stream().map(RestaurantStockResponse::of).collect(Collectors.toList());
    }

    public RestaurantLocationDTO(Long id, Long memberId, String category, String name, String address, Point location, Long contact, Map<String, Object> menu, Map<String, Object> time, List<RestaurantStock> restaurantStocks) {
        this.id = id;
        this.memberId = memberId;
        this.category = category;
        this.name = name;
        this.address = address;
        this.location = location;
        this.contact = contact;
        this.menu = menu;
        this.time = time;
        this.restaurantStocks = of(restaurantStocks);
    }
}
