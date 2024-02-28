package mysqlpoint.realmysqlpoint.repository;

import mysqlpoint.realmysqlpoint.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaItemRepository extends JpaRepository<Item, Long> , RestaurantRepository {
}
