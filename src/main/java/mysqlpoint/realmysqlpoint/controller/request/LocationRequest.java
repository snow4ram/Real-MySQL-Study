package mysqlpoint.realmysqlpoint.controller.request;

import lombok.Data;


@Data
public class LocationRequest {

    private double latitude;

    private double longitude;

    private String message;

}
