package mysqlpoint.realmysqlpoint.repository;


import mysqlpoint.realmysqlpoint.entity.RestaurantStock;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RestaurantStockRepository extends JpaRepository<RestaurantStock, Long> {

}

