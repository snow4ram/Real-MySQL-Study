package mysqlpoint.realmysqlpoint.map;


import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.Type;
import org.locationtech.jts.geom.Point;

import java.util.HashMap;
import java.util.Map;

@Getter
@Entity
@ToString
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    @Comment("레스토랑 분류")
    private String category;

    @Column(length = 200)
    @Comment("레스토랑 이름")
    private String name;

    @Column(length = 200)
    @Comment("레스토랑 주소")
    private String address;

    @Comment("위도, 경도")
    private Point location;

    @Comment("가게 연락처")
    private Long contact;

}
