package recruitServer.recruitServer.resume.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import recruitServer.recruitServer.resume.domain.Resume;

@Data
@NoArgsConstructor
public class ResumeDto {

    private Long id;
    private String intro;
    private String location;
    private String user;
    private String skill;

    public Resume toEntity() {
        return Resume.builder()
                .id(id)
                .intro(intro)
                .location(location)
                .user(user)
                .skill(skill)
                .build();
    }
}
