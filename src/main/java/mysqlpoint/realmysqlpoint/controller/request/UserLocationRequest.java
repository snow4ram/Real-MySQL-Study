package mysqlpoint.realmysqlpoint.controller.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class UserLocationRequest {
    private double neLatitude;
    private double neLongitude;
    private double swLatitude;
    private double swLongitude;


    public UserLocationRequest(double neLatitude, double neLongitude, double swLatitude, double swLongitude) {
        this.neLatitude = neLatitude;
        this.neLongitude = neLongitude;
        this.swLatitude = swLatitude;
        this.swLongitude = swLongitude;
    }
}
