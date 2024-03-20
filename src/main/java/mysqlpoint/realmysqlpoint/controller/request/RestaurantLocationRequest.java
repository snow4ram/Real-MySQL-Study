package mysqlpoint.realmysqlpoint.controller.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class RestaurantLocationRequest {

    private double neLatitude;
    private double neLongitude;
    private double swLatitude;
    private double swLongitude;


    public RestaurantLocationRequest(double neLatitude, double neLongitude, double swLatitude, double swLongitude) {
        this.neLatitude = neLatitude;
        this.neLongitude = neLongitude;
        this.swLatitude = swLatitude;
        this.swLongitude = swLongitude;
    }
}
