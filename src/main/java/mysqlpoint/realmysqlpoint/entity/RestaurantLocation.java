package mysqlpoint.realmysqlpoint.entity;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.Type;
import org.locationtech.jts.geom.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantLocation {
    private Long id;

    private Member members;

    private String category;

    private String name;

    private String address;

    private Point location;

    private Long contact;

    private Map<String, Object> menu = new HashMap<>();

    private Map<String, Object> time = new HashMap<>();

    private Map<String , Object> provision = new HashMap<>();

    private List<RestaurantStock> restaurantStocks = new ArrayList<>();

}
