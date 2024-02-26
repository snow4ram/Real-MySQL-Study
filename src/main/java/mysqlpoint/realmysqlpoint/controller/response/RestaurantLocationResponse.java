package mysqlpoint.realmysqlpoint.controller.response;


import lombok.*;
import mysqlpoint.realmysqlpoint.entity.Restaurant;

import java.util.Map;


@Getter
@ToString
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class RestaurantLocationResponse {

    private Long id;

    private Long memberId;

    private String category;

    private String name;

    private String address;

    private Double latitude;

    private Double longitude;

    private Long contact;

    private Map<String, Object> menu;

    private Map<String, Object> time;

    private Map<String, Object> provision;

    private String status;

    public void setBusinessStatus(String status) {
        this.status = status;
    }

    public static RestaurantLocationResponse of(Restaurant restaurant) {
        return RestaurantLocationResponse.builder()
                .id(restaurant.getId())
                .memberId(restaurant.getMembers().getId())
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
