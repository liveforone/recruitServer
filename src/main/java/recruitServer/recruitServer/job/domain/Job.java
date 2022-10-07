package recruitServer.recruitServer.job.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Job {  //채용 공고

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String position;  //직급
    private String writer;  //채용 공고 작성자
    private String company;
    private String duty;  //직무
    private int volunteer;  //지원자 수

    @Builder
    public Job(Long id, String title, String content, String position, String writer, String company, String duty, int volunteer) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.position = position;
        this.writer = writer;
        this.company = company;
        this.duty = duty;
        this.volunteer = volunteer;
    }
}
