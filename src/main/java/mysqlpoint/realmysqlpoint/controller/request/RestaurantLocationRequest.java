package mysqlpoint.realmysqlpoint.controller.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class RestaurantLocationRequest {

    private String restaurantName;

    private Double latitude;

    private Double longitude;

    @Builder
    public RestaurantLocationRequest(String message, double latitude, double longitude) {
        this.restaurantName = message;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
