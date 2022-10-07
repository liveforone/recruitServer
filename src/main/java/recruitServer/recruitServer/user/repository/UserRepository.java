package recruitServer.recruitServer.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import recruitServer.recruitServer.user.domain.Users;

public interface UserRepository extends JpaRepository<Users, Long> {

    Users findByEmail(String email);
}
