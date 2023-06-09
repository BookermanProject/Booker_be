## 01 - 프로젝트 <BOOKER> 소개

# [대용량 도서 처리 시스템 _ BOOKER]
![KakaoTalk_Photo_2023-06-21-20-03-40](https://github.com/BookermanProject/Booker_be/assets/40461588/0f32896e-8bc3-40b9-a6e1-ba96e42497db)


# 프로젝트 소개

## **프로젝트 기획의도 : 책 읽는 사람이 점점 줄고 있는 시대에 도서량을 증진시키기 위한 도서관, 또는 서점의 책 무료나눔 이벤트**

### 프로젝트 시나리오

<aside>
💡 전국도서관에 보관중인 책들에 대해서, 공공포털을 이용해 일주일에 한번 무료나눔 이벤트를 진행합니다. 
이때 유저들은 도서관 검색 시스템을 통해 무료나눔 받고싶은 책을 추천해서 리스트를 만드는데 기여합니다.

- 만들어진 책 데이터에 대해 검색할 수 있고, 회원가입한 회원에 한해 원하는 책을 나눔 받을 수 있도록 이벤트 신청을 할 수 있습니다.
- 관리자는 현재 책 데이터의 좋아요 TOP 10 리스트를 가지고, 이 책들을 이벤트로 등록 시킬 수 있습니다.
- 이 리스트에 속해있는 모든 책들이 무조건적으로 나눔 받을 수 있는것은 아니며, 사정상 특정 책들은 나눔을 못 받을 수 있습니다. (책 분량을 확보 못했거나 등)
- 관리자는 해당 리스트를 검열하여 부정 도서가 있는지 확인하고 정해진 시간에 이벤트를 발생시킬 수 있도록 검증하는 역할을 담당합니다.
</aside>

# 주요 기능

- 1000만건 이상의 도서 데이터를 빠르게 검색할 수 있습니다.
- 무료나눔 책을 신청하고, 마이 페이지에서 신청현황을 확인 할 수 있습니다.

# 프로젝트 목표

### 대용량 데이터 , 트래픽 처리

- 데이터 수집 : 도서마루 서울 모든 도서관 데이터 + yes24 책 데이터 크롤링으로 1000만건 이상의 데이터 확보
- 트래픽 처리 : 최소 10만건 이상의 API 요청이 동시에 오더라도 데이터 손실 없이 이벤트 처리가 가능하도록 분산 처리 구조 확립
- 검색 성능 :  1000만건 데이터를 5000명의 동시접속자가 평균적인 검색 요청(10초에 한번씩, 총 10번)을 보낼 때 견딜 수 있는 검색 구조 확립

### 효율적인 인프라 구축

- Docker : Local용 테스트 서버(3대), AWS 배포서버(3대) , 개인 개발 서버(4대)등 총 10대 이상에 달하는 개발환경/서버에 일관성 있는 환경을 효율적으로 구축
- Github_Action : 배포서버에 코드 기능이 업데이트 될때마다 자동으로 배포 함으로 효율적인 배포시스템 구축

# 아키텍처

## ELASTIC
![엘라스틱 구조도기술구조도2](https://github.com/BookermanProject/Booker_be/assets/40461588/36eb5c06-f3ad-4b1c-b2e2-dfbbd87ba9e0)


## KAFKA
![카프카 기술단카프카기술구조도완완완](https://github.com/BookermanProject/Booker_be/assets/40461588/b86b60f0-4480-4559-9e4f-3f2895b3929f)

# 서버 구조 
![인프라서버 구축 2222](https://github.com/BookermanProject/Booker_be/assets/40461588/0690570a-6f27-4fa6-9fb4-2fb6f1fcfaeb)

## 👬팀원
<table>
  <tbody>
    <tr>
      <td align="center"><a href="https://github.com/kanteluv"><img src="https://avatars.githubusercontent.com/u/105421031?v=4" width="100px;" alt=""/><br /><sub><b>리더 : 정성윤</b></sub></a><br /></td>
      <td align="center"><a href="https://github.com/jwodn123"><img src="https://avatars.githubusercontent.com/u/68779402?v=4" width="100px;" alt=""/><br /><sub><b>BE 팀원 : 전재우</b></sub></a><br /></td>
        <td align="center"><a href="https://github.com/kkj5158"><img src="https://avatars.githubusercontent.com/u/40461588?v=4" width="100px;" alt=""/><br /><sub><b>BE 팀원 : 김지승</b></sub></a><br /></td>
        <td align="center"><a href="https://github.com/YooMyeonggeun"><img src="https://avatars.githubusercontent.com/u/129927506?v=4" width="100px;" alt=""/><br /><sub><b>BE 팀원 : 유명근</b></sub></a><br /></td>
    </tr>
  </tbody>
</table>
