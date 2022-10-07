package recruitServer.recruitServer.job.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import recruitServer.recruitServer.job.domain.Job;

public interface JobRepository extends JpaRepository<Job, Long> {

    Job findOneById(Long id);

    Page<Job> findByTitleContaining(String keyword, Pageable pageable);

    @Modifying
    @Query("update Job j set j.volunteer = j.volunteer + 1 where j.id = :id")
    void updateVolunteer(@Param("id") Long id);
}
