package mysqlpoint.realmysqlpoint.controller.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@NoArgsConstructor
public class UserLocationToZoomRequest {

    private Long zoomLevel;

    private Double latitude;

    private Double longitude;


    public UserLocationToZoomRequest(Long zoomLevel, Double latitude, Double longitude) {
        this.zoomLevel = zoomLevel;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
