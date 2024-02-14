package mysqlpoint.realmysqlpoint.controller.response;




import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import mysqlpoint.realmysqlpoint.entity.Restaurant;
import java.util.HashMap;
import java.util.Map;


@Getter
@Builder
@ToString
public class RestaurantLocationResponse {

    private Long id;

    private String category;

    private String name;

    private String address;

    private Double latitude;

    private Double longitude;

    private Long contact;

    private Map<String, Object> menu = new HashMap<>();

    private Map<String, Object> time = new HashMap<>();

    private Map<String , Object> provision = new HashMap<>();

    //point x : 경도  , y : 위도
    //Double latitude : 위도
    //Double longitude : 경도
    public static RestaurantLocationResponse of (Restaurant restaurant) {
        return RestaurantLocationResponse.builder()
                .id(restaurant.getId())
                .category(restaurant.getCategory())
                .name(restaurant.getName())
                .address(restaurant.getAddress())
                .longitude(restaurant.getLocation().getX())
                .latitude(restaurant.getLocation().getY())
                .contact(restaurant.getContact())
                .menu(restaurant.getMenu())
                .time(restaurant.getTime())
                .provision(restaurant.getProvision())
                .build();

    }
}
