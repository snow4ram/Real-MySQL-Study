package mysqlpoint.realmysqlpoint.controller.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
public class RestaurantNameResponse {

    private String name;

    private double latitude;

    private double longitude;

    @Builder
    public RestaurantNameResponse(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
