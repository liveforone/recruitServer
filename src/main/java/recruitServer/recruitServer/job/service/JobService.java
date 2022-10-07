package recruitServer.recruitServer.job.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import recruitServer.recruitServer.job.domain.Job;
import recruitServer.recruitServer.job.dto.JobDto;
import recruitServer.recruitServer.job.repository.JobRepository;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;

    @Transactional(readOnly = true)
    public Page<Job> getJobList(Pageable pageable) {
        return jobRepository.findAll(pageable);
    }

    @Transactional
    public void saveJob(JobDto jobDto, String writer) {
        jobDto.setWriter(writer);
        jobRepository.save(jobDto.toEntity());
    }

    @Transactional(readOnly = true)
    public Job getOneJob(Long id) {
        return jobRepository.findOneById(id);
    }

    @Transactional(readOnly = true)
    public Page<Job> jobSearchList(String keyword, Pageable pageable) {
        return jobRepository.findByTitleContaining(keyword, pageable);
    }

    @Transactional
    public void editJob(JobDto jobDto, Long id) {
        Job job = jobRepository.findOneById(id);
        jobDto.setId(id);  //dto에 유일하게 set되지않은 id와 volunteer 값을 넣어준다.
        jobDto.setVolunteer(job.getVolunteer());
        jobRepository.save(jobDto.toEntity());
    }

    @Transactional
    public void updateVolunteer(Long id) {
        jobRepository.updateVolunteer(id);
    }
}
