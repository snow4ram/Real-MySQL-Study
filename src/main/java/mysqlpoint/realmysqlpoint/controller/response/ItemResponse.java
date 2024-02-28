package mysqlpoint.realmysqlpoint.controller.response;

import jakarta.persistence.Column;
import lombok.*;
import mysqlpoint.realmysqlpoint.entity.Item;
import mysqlpoint.realmysqlpoint.util.ItemType;
import org.hibernate.annotations.Comment;

import java.math.BigDecimal;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemResponse {

    private Long id;

    private String name;

    private BigDecimal price;

    private String info;


    public static ItemResponse of(Item item) {
        return ItemResponse.builder()
                .id(item.getId())
                .name(item.getName())
                .price(item.getPrice())
                .info(item.getInfo()).build();

    }

}
