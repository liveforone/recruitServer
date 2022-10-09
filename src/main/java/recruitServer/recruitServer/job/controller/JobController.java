package recruitServer.recruitServer.job.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recruitServer.recruitServer.job.domain.Job;
import recruitServer.recruitServer.job.dto.JobDto;
import recruitServer.recruitServer.job.service.JobService;

import java.net.URI;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class JobController {

    private final JobService jobService;

    @GetMapping("/job")
    public ResponseEntity<Page<Job>> jobHome(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Job> jobList = jobService.getJobList(pageable);

        return new ResponseEntity<>(jobList, HttpStatus.OK);
    }

    @GetMapping("/job/post")
    public ResponseEntity<?> postPage() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/job/post")
    public ResponseEntity<?> jobPost(
            @RequestBody JobDto jobDto,
            Principal principal
    ) {
        String writer = principal.getName();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("/job"));

        jobService.saveJob(jobDto, writer);
        log.info("채용 공고 작성 성공");

        return new ResponseEntity<>(httpHeaders, HttpStatus.MOVED_PERMANENTLY);
    }

    /*
    현재 유저를 함께 보냄.
    뷰단에서 받은 job json데이터의 writer와 currentUser가 같은지 판별하여
    수정 버튼 보여주기.
    또한 writer와 currentUser가 같은지 판별하여 구직 신청자 리스트 보여주기.
    여기에서 apply, 즉 구직버튼을 클릭함.
     */
    @GetMapping("/job/{id}")
    public ResponseEntity<Map<String, Object>> jobDetail(
            @PathVariable("id") Long id,
            Principal principal
    ) {
        Map<String, Object> map = new HashMap<>();
        String currentUser = principal.getName();
        Job job = jobService.getOneJob(id);

        map.put("currentUser", currentUser);
        map.put("job", job);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    /*
    수정시 기존의 작성한 것들을 그대로 보여주고 필요한 것들만 수정하라고
    기존 작성물들을 body로 보내줌
     */
    @GetMapping("/job/edit/{id}")
    public ResponseEntity<?> editPage(@PathVariable("id") Long id) {
        Job job = jobService.getOneJob(id);

        return new ResponseEntity<>(job, HttpStatus.OK);
    }

    /*
    기존 값들을 json으로 보내주었기 때문에 dto로 입력받음.
     */
    @PostMapping("/job/edit/{id}")
    public ResponseEntity<?> editJob(
            @PathVariable("id") Long id,
            @RequestBody JobDto jobDto
    ) {
        String url = "/job/edit/" + id;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create(url));

        jobService.editJob(jobDto, id);
        log.info("채용 공고 수정 완료!!");

        return new ResponseEntity<>(httpHeaders, HttpStatus.MOVED_PERMANENTLY);
    }

    @GetMapping("/job/search")
    public ResponseEntity<Page<Job>> searchJob(
            @PageableDefault(page = 0, size = 10)
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "volunteer", direction = Sort.Direction.DESC),
                    @SortDefault(sort = "id", direction = Sort.Direction.DESC)
            }) Pageable pageable,
            @RequestParam("keyword") String keyword
    ) {
        Page<Job> searchList = jobService.jobSearchList(keyword, pageable);

        return new ResponseEntity<>(searchList, HttpStatus.OK);
    }
}
