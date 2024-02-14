package mysqlpoint.realmysqlpoint.entity;


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


    @Type(JsonType.class)
    @Comment("메뉴")
    @Column(name = "menu", columnDefinition ="json")
    private Map<String, Object> menu = new HashMap<>();

    @Type(JsonType.class)
    @Comment("영업시간")
    @Column(name = "time", columnDefinition ="json")
    private Map<String, Object> time = new HashMap<>();

    @Type(JsonType.class)
    @Comment("레스토랑의 편의시설")
    @Column(name = "provision", columnDefinition ="json")
    private Map<String , Object> provision = new HashMap<>();

}