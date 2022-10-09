package recruitServer.recruitServer.resume.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import recruitServer.recruitServer.resume.domain.Resume;
import recruitServer.recruitServer.resume.dto.ResumeDto;
import recruitServer.recruitServer.resume.repository.ResumeRepository;

@Service
@RequiredArgsConstructor
public class ResumeService {

    private final ResumeRepository resumeRepository;

    @Transactional(readOnly = true)
    public Resume getResume(String user) {
        return resumeRepository.findByUser(user);
    }

    @Transactional
    public void saveResume(ResumeDto resumeDto, String user) {
        resumeDto.setUser(user);
        resumeRepository.save(resumeDto.toEntity());
    }

    @Transactional
    public void updateResume(ResumeDto resumeDto, String user) {
        Resume resume = resumeRepository.findByUser(user);
        resumeDto.setId(resume.getId());
        resumeDto.setUser(user);
        resumeRepository.save(resumeDto.toEntity());
    }
}
