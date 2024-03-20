package mysqlpoint.realmysqlpoint.controller.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserLocationRequest {

    private double userLocationLatitude;

    private double userLocationLongitude;

    private String keyword;

    private int page;

    private int size;

    public UserLocationRequest(double userLocationLatitude, double userLocationLongitude, String keyword, int page, int size) {
        this.userLocationLatitude = userLocationLatitude;
        this.userLocationLongitude = userLocationLongitude;
        this.keyword = keyword;
        this.page = page;
        this.size = size;
    }
}
