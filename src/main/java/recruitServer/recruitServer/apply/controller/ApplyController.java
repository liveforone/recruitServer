package recruitServer.recruitServer.apply.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import recruitServer.recruitServer.apply.domain.Apply;
import recruitServer.recruitServer.apply.service.ApplyService;
import recruitServer.recruitServer.job.domain.Job;
import recruitServer.recruitServer.job.service.JobService;

import java.net.URI;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ApplyController {

    private final ApplyService applyService;
    private final JobService jobService;

    /*
    job detail에서 확인 할 수 있는 구직 신청 리스트
     */
    @GetMapping("/applylist/{id}")  //여기서 id는 jobId임, applyId가 아니라
    public ResponseEntity<List<Apply>> applyList(@PathVariable("id") Long jobId) {
        List<Apply> apply = applyService.getApplyList(jobId);

        return new ResponseEntity<>(apply, HttpStatus.OK);
    }

    /*
    mypage에서 버튼 클릭으로 볼 수 있는 내가 구직 신청한 리스트
    회사와 직무를 보여주어야함.
     */
    @GetMapping("/mypage/applylist")
    public ResponseEntity<Map<String, Object>> getApplyListForMyPage(Principal principal) {
        Map<String, Object> map = new HashMap<>();
        String user = principal.getName();
        List<Apply> applyList = applyService.getApplyListForMyPage(user);

        for (int i=0; i<applyList.size(); i++) {
            Job job = jobService.getOneJob(applyList.get(i).getJobId());
            map.put("회사" + i, job.getCompany());
            map.put("직무" + i, job.getDuty());
        }

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/apply/{id}")  //여기서 id는 jobId임, applyId가 아니라
    public ResponseEntity<?> applyJob(
            @PathVariable("id") Long jobId,
            Principal principal
    ) {
        String url = "/job/" + jobId;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create(url));

        String user = principal.getName();

        applyService.applyJob(jobId, user);
        jobService.updateVolunteer(jobId);
        log.info("구직 신청 성공!!");

        return new ResponseEntity<>(httpHeaders, HttpStatus.MOVED_PERMANENTLY);
    }
}
