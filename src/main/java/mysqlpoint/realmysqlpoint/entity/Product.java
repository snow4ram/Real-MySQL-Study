package mysqlpoint.realmysqlpoint.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import mysqlpoint.realmysqlpoint.util.BaseEntity;
import org.hibernate.annotations.Comment;

import java.math.BigDecimal;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product")
public class Product extends BaseEntity {
    @Comment("제품 이름")
    @Column(name = "name", columnDefinition = "VARCHAR(50)")
    private String name;

    @Comment("제품 원가")
    @Column(name = "price", columnDefinition = "DECIMAL(64, 3)")
    private BigDecimal price;

    @Comment("재고 수량")
    @Column(name = "quantity", columnDefinition = "BIGINT")
    private Long quantity;

    @Comment("술 도수")
    @Column(name = "alcohol", columnDefinition = "DOUBLE")
    private Double alcohol;


}
