package recruitServer.recruitServer.resume.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import recruitServer.recruitServer.resume.domain.Resume;
import recruitServer.recruitServer.resume.dto.ResumeDto;
import recruitServer.recruitServer.resume.service.ResumeService;

import java.net.URI;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ResumeController {  // == mypageController

    private final ResumeService resumeService;

    @GetMapping("/mypage")
    public ResponseEntity<Resume> myPage(Principal principal) {
        String user = principal.getName();
        Resume resume = resumeService.getResume(user);

        if (resume == null) { //이력서 없을 시, 이력서 생성 url로 이동
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(URI.create("/resume/post"));

            return new ResponseEntity<>(httpHeaders, HttpStatus.MOVED_PERMANENTLY);
        }

        return new ResponseEntity<>(resume, HttpStatus.OK);
    }

    @GetMapping("/resume/post")
    public ResponseEntity<?> resumePostPage() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/resume/post")
    public ResponseEntity<?> resumePost(
            @RequestBody ResumeDto resumeDto,
            Principal principal
    ) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("/mypage"));
        String user = principal.getName();

        resumeService.saveResume(resumeDto, user);
        log.info("이력서 저장 완료 !!");

        return new ResponseEntity<>(httpHeaders, HttpStatus.MOVED_PERMANENTLY);
    }

    @GetMapping("/resume/edit")
    public ResponseEntity<Resume> resumeEditPage(Principal principal) {
        String user = principal.getName();
        Resume resume = resumeService.getResume(user);

        return new ResponseEntity<>(resume, HttpStatus.OK);
    }

    @PostMapping("/resume/edit")
    public ResponseEntity<?> resumeEdit(
            @RequestBody ResumeDto resumeDto,
            Principal principal
    ) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("/mypage"));
        String user = principal.getName();

        resumeService.updateResume(resumeDto, user);
        log.info("이력서 수정 완료 !!");

        return new ResponseEntity<>(httpHeaders, HttpStatus.MOVED_PERMANENTLY);
    }
}
