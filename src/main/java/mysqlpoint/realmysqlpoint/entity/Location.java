package mysqlpoint.realmysqlpoint.entity;

import lombok.Data;

@Data
public class Location {


    Double latitude;

    Double longitude;

    public Location(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
