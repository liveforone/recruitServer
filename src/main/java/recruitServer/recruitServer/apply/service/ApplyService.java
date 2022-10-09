package recruitServer.recruitServer.apply.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import recruitServer.recruitServer.apply.domain.Apply;
import recruitServer.recruitServer.apply.dto.ApplyDto;
import recruitServer.recruitServer.apply.repository.ApplyRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplyService {

    private final ApplyRepository applyRepository;

    @Transactional(readOnly = true)
    public List<Apply> getApplyList(Long id) {
        return applyRepository.findByJobId(id);
    }

    @Transactional(readOnly = true)
    public List<Apply> getApplyListForMyPage(String user) {
        return applyRepository.findByUser(user);
    }

    @Transactional
    public void applyJob(Long id, String user) {
        ApplyDto applyDto = ApplyDto.builder()
                .jobId(id)
                .user(user)
                .build();
        applyRepository.save(applyDto.toEntity());
    }
}
