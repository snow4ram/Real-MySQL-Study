package mysqlpoint.realmysqlpoint.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import mysqlpoint.realmysqlpoint.util.ItemType;
import org.hibernate.annotations.Comment;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "item")
public class Item extends BaseEntity {
    @Column(name = "type", columnDefinition = "VARCHAR(20)")
    @Comment("상품 유형")
    @Enumerated(EnumType.STRING)
    private ItemType type;

    @Column(name = "name", columnDefinition = "VARCHAR(200)")
    @Comment("상품 이름")
    private String name;

    @Column(name = "price", columnDefinition = "DECIMAL(64, 3)")
    @Comment("상품 가격")
    private BigDecimal price;

    @Column(name = "info", columnDefinition = "MEDIUMTEXT")
    @Comment("상품 설명")
    private String info;

    @OneToMany(mappedBy = "item")
    @Builder.Default
    private List<RestaurantStock> restaurantStocks = new ArrayList<>();


}
