package mysqlpoint.realmysqlpoint.repository;

import mysqlpoint.realmysqlpoint.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaMemberRepository extends JpaRepository<Member , Long> {
}
