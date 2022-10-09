package recruitServer.recruitServer.apply.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import recruitServer.recruitServer.apply.domain.Apply;

import java.util.List;

public interface ApplyRepository extends JpaRepository<Apply, Long> {

    List<Apply> findByJobId(Long id);

    List<Apply> findByUser(String user);
}
