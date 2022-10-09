# 구직 사이트
> linked in 과 유사한 형태의 사이트
> rest-api 기반의 서버

## 설계
* 마이페이지에 이력서 등록. 없다면 생성창으로 redirect.
* 마이페이지에의 지원리스트에서는 내가 지원한 회사와 직무가 뜸
* 채용공고에는 회사들의 공고가 뜸.
* 한 채용공고에 지원을 하게 되면 해당 공고의 지원자 리스트에 지원을 클릭한 사람들이 뜸.
* 또한 지원시 지원한 사람의 수(volunteer)가 1개씩 늘어난다.

## 엔티티 설계
#### Job - 채용 공고
* title, content, position, writer, company, duty, volunteer
#### Resume - mypage
* intro(소개), location(위치), user, skill
* 작성자 이름으로 들고와서 없으면 생성, 있으면 json 뿌리기.
#### Apply - mypage, applyList in job-detail
* user, jobId
* 마이페이지에서는 지원자 이름으로 mypage에 뿌려줌
* 마이페이지에서는 회사, 직무(duty)를 보여줌
* applyList 에서는 jobId로 뿌려줌
* 지원을 하게 되면 지원자와 jobId 저장됨.
* 지원자 수(volunteer) 업데이트.

## api 설계(user 생략)
#### job
* /job - get, 페이징
* /job/post - get/post
* /job/{id} - get, map으로 현재 유저도 같이 json으로 보냄, 뷰에서 currentUser와 writer판별함(위임)
* /job/edit/{id} - get/post
* /job/search - get, 페이징, 파라미터로 키워드 입력받음, 지원자 수 순으로 정렬
#### resume
* mypage - get, 이력서가 존재하지 않는다면 이력서 생성 창으로 리다이렉트, 아니면 이력서 json send
* /resume/post - get/post
* /resume/edit - get/post
#### apply
* /applylist/{id} - get
* /mypage/applylist - get, 리스트에서 jobId파싱해 job의 회사와 직무를 map으로 뿌림.
* /apply/{id} - post

## json test body
#### users - signup
<pre>
{
    "email" : "yc1234@gmail.com",
    "password" : "1234"
}
</pre>
#### job - post
<pre>
{
    "title" : "test1",
    "content" : "this is test",
    "position" : "신입",
    "company" : "naver",
    "duty" : "백엔드"
}
</pre>
#### resume - post/edit
<pre>
- post -
{
    "intro" : "resume",
    "location" : "seoul",
    "skill" : "backend, jpa"
}
- edit -
{
    "intro" : "updated resume",
    "location" : "seoul",
    "skill" : "backend, jpa"
}
</pre>

## 마이페이지의 나의 구직 신청 리스트
* 해당 작업을 할 때, 한 채용공고의 지원자 리스트와 달리 여기서는 직무와 회사가 같이 떠야했다.
* 문제는 회사도, 직무도 여러개가 될 수 있다.
* (당연하게도 구직 신청을 한 회사만 하지 않는다. 또 한 회사에 한 직무로만 하지 않는다.)
* 따라서 map을 사용하기로 하였다.
* 리스트로 구직 신청(apply)를 받고, 
* for 문으로 리스트의 크기만큼 돌려서 
* 한개의 apply에 jobId를 통해 job 한개를 가져왔다.
* 그리고 "회사" + i, "직무" + i 와 같은 형식으로 map에 집어넣었다.
```
Map<String, Object> map = new HashMap<>();
String user = principal.getName();
List<Apply> applyList = applyService.getApplyListForMyPage(user);

for (int i=0; i<applyList.size(); i++) {
    Job job = jobService.getOneJob(applyList.get(i).getJobId());
    map.put("회사" + i, job.getCompany());
    map.put("직무" + i, job.getDuty());
}
```