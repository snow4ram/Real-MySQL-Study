package mysqlpoint.realmysqlpoint.repository;

import mysqlpoint.realmysqlpoint.map.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaRestaurantRepository extends JpaRepository<Restaurant, Long> {
}
