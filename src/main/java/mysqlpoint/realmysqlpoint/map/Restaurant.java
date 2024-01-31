package mysqlpoint.realmysqlpoint.map;


import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Point;


@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(columnDefinition = "POINT")
    private Point coordinates;

    public Restaurant(Point coordinates , String name) {
        this.coordinates = coordinates;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                '}';
    }
}
