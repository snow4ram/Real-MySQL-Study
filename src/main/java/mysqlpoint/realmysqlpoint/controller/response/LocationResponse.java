package mysqlpoint.realmysqlpoint.controller.response;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.Type;
import org.locationtech.jts.geom.Point;

import java.util.HashMap;
import java.util.Map;

@Getter
@ToString
public class LocationResponse {

    private final Long id;

    private final String category;

    private final String name;

    private final String address;

    private final Point location;

    private final Long contact;

    private Map<String, Object> menu = new HashMap<>();

    private Map<String, Object> time = new HashMap<>();

    private Map<String , Object> provision = new HashMap<>();

    @Builder
    public LocationResponse(Long id, String category, String name, String address, Point location, Long contact, Map<String, Object> menu, Map<String, Object> time, Map<String, Object> provision) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.address = address;
        this.location = location;
        this.contact = contact;
        this.menu = menu;
        this.time = time;
        this.provision = provision;
    }
}
