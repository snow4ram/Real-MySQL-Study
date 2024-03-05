package mysqlpoint.realmysqlpoint.controller.response;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.*;
import mysqlpoint.realmysqlpoint.entity.Member;
import mysqlpoint.realmysqlpoint.entity.Restaurant;
import mysqlpoint.realmysqlpoint.entity.RestaurantLocation;
import mysqlpoint.realmysqlpoint.entity.RestaurantStock;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.SQLSelect;
import org.hibernate.annotations.Type;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Getter
@ToString
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseEntityMapper{

    private Long id;
    private Long memberId;
    private String category;
    private String name;
    private String address;
    private Double lon; // 경도
    private Double lat; // 위도
    private Long contact;
    private Map<String, Object> menu;
    private Map<String, Object> time;
    private Map<String, Object> provision;
    private List<ItemResponse> item; // 추가된 아이템 목록


    public static ResponseEntityMapper of(Restaurant restaurant) {
        return ResponseEntityMapper.builder()
                .id(restaurant.getId())
                .memberId(restaurant.getMembers().getId())
                .category(restaurant.getCategory())
                .memberId(restaurant.getId())
                .name(restaurant.getName())
                .address(restaurant.getAddress())
                .lon(restaurant.getLocation().getY())
                .lat(restaurant.getLocation().getX())
                .contact(restaurant.getContact())
                .menu(restaurant.getMenu())
                .time(restaurant.getTime())
                .provision(restaurant.getProvision())
                .item(null)
                .build();
    }

}
