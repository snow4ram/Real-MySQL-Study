package mysqlpoint.realmysqlpoint.controller.response;


import lombok.*;
import mysqlpoint.realmysqlpoint.entity.Restaurant;
import mysqlpoint.realmysqlpoint.entity.RestaurantLocation;
import org.locationtech.jts.geom.Point;

import java.math.BigDecimal;
import java.util.Map;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RestaurantNearbyLocationResponse {

    private Long id;

    private Long memberId;

    private String category;

    private String name;

    private String address;

    private Double latitude;

    private Double longitude;

    private Long contact;

    private Map<String, Object> menu;

    private Map<String, Object> time;

    private Map<String, Object> provision;

    private Long itemId;

    private String itemName;

    private BigDecimal itemPrice;

    private String itemInfo;


    private String status;

    public void setBusinessStatus(String status) {
        this.status = status;
    }


    public RestaurantNearbyLocationResponse(Long id, Long memberId, String category, String name, String address, Point location, Long contact, Map<String, Object> menu, Map<String, Object> time, Map<String, Object> provision, Long itemId, String itemName, BigDecimal itemPrice, String itemInfo) {
        this.id = id;
        this.memberId = memberId;
        this.category = category;
        this.name = name;
        this.address = address;
        this.latitude = location.getX();
        this.longitude = location.getY();
        this.contact = contact;
        this.menu = menu;
        this.time = time;
        this.provision = provision;
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemInfo = itemInfo;
    }

}
