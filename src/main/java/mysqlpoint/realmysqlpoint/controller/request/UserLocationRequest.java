package mysqlpoint.realmysqlpoint.controller.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class UserLocationRequest {

    private Double latitude;

    private Double longitude;

    @Builder
    public UserLocationRequest( double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
