package recruitServer.recruitServer.resume.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resume_id")
    private Long id;

    private String intro;
    private String location;
    private String user;

    @Column(columnDefinition = "TEXT")
    private String skill;

    @Builder
    public Resume(Long id, String intro, String location, String user, String skill) {
        this.id = id;
        this.intro = intro;
        this.location = location;
        this.user = user;
        this.skill = skill;
    }
}
