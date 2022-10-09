package recruitServer.recruitServer.apply.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import recruitServer.recruitServer.apply.domain.Apply;

@Data
@NoArgsConstructor
public class ApplyDto {

    private Long id;
    private String user;
    private Long jobId;

    public Apply toEntity() {
        return Apply.builder()
                .id(id)
                .user(user)
                .jobId(jobId)
                .build();
    }

    @Builder
    public ApplyDto(Long id, String user, Long jobId) {
        this.id = id;
        this.user = user;
        this.jobId = jobId;
    }
}
