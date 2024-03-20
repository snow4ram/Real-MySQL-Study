package mysqlpoint.realmysqlpoint.repository;

import mysqlpoint.realmysqlpoint.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member , Long> {
}
