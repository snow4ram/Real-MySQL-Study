package mysqlpoint.realmysqlpoint.controller.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class RestaurantSearchLocationRequest {

    private Integer radius;

    private Double latitude;

    private Double longitude;

    @Builder
    public RestaurantSearchLocationRequest(Integer radius, Double latitude, Double longitude) {
        this.radius = radius;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
