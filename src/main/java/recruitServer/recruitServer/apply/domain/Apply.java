package recruitServer.recruitServer.apply.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Apply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "apply_id")
    private Long id;

    private String user;
    private Long jobId;

    @Builder
    public Apply(Long id, String user, Long jobId) {
        this.id = id;
        this.user = user;
        this.jobId = jobId;
    }
}
