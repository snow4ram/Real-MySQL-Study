package mysqlpoint.realmysqlpoint.repository;


import mysqlpoint.realmysqlpoint.entity.RestaurantStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface JpaRestaurantStockRepository extends JpaRepository<RestaurantStock, Long> {

}

