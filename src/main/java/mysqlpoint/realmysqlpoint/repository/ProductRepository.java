package mysqlpoint.realmysqlpoint.repository;

import mysqlpoint.realmysqlpoint.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
