package recruitServer.recruitServer.resume.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import recruitServer.recruitServer.resume.domain.Resume;

public interface ResumeRepository extends JpaRepository<Resume, Long> {

    Resume findByUser(String user);
}
