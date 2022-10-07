# 구직 사이트
> linked in 과 유사한 형태의 사이트
> rest-api 기반의 서버

## 설계
* 마이페이지에 이력서 등록. 마이페이지 들어가면 body에 json으로 뿌리기
* 마이페이지에는 내가 지원한 회사 리스트가 존재
* 채용공고에는 회사들의 공고가 뜸.
* 제목(회사명, 직무), 내용(회사소개, 직무 소개, 위치, 급여 등), 직급, 올린사람, 회사명, 직무, 지원자 수
* 지원을 하게 되면 지원자 리스트에 지원을 클릭한 사람들이 뜸.

## 엔티티 설계
#### Job - 채용 공고
* title, content, position, writer, company, duty, volunteer
#### Resume - mypage
* intro(소개), location(위치), user, skill
* 작성자 이름으로 들고와서 id없으면 생성, 있으면 수정하기.
#### Apply - mypage, job-detail
* user, jobId
* 마이페이지에서는 지원자 이름으로 mypage에 뿌려줌
* job-detail에서는 jobId로 뿌려줌
* 회사, 직무(duty) 는 jobId로 가져옴
* 지원을 하게 되면 지원자와 해당 게시글의 id 저장됨.
* 지원자 수(volunteer) 업데이트.

## api 설계

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

url은 apply/게시글 번호 와 같은 형식으로 한다.