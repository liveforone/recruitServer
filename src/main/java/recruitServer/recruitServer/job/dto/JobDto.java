package recruitServer.recruitServer.job.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import recruitServer.recruitServer.job.domain.Job;


@Data
@NoArgsConstructor
public class JobDto {

    private Long id;
    private String title;
    private String content;
    private String position;
    private String writer;
    private String company;
    private String duty;
    private int volunteer;

    public Job toEntity() {
        return Job.builder()
                .id(id)
                .title(title)
                .content(content)
                .position(position)
                .writer(writer)
                .company(company)
                .duty(duty)
                .volunteer(volunteer)
                .build();
    }
}
