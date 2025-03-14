# 🚉 열차 예약 서비스 프로젝트
  ## 해당 프로젝트는 사용자가 기차여행을 즐길 수 있도록 커뮤니티를 제공하여 정보를 공유함과 더불어 조회와 결제를 동시에 진행할 수 있도록 만들어진 서비스입니다.
# 📆 작업 기간
## 2023-12-18 ~ 2024-1-2 (16일)
# 👷 팀원 구성
### 팀장:[곽기민](https://github.com/kminimini) - 열차조회, 결제
### 팀원:[김양현](https://github.com/rladidgus) - 회원관리, 공지사항
### 팀원:[조해준](https://github.com/ChoHaeJun) - 게시판, 결제 서포트
# 🔧 기술 스택
## Front 
<div>
 <img alt="JavaScript" src="https://img.shields.io/badge/javascript-%23323330.svg?style=for-the-badge&logo=javascript&logoColor=%23F7DF1E"/>
 <img alt="Bootstrap" src="https://img.shields.io/badge/bootstrap-%23563D7C.svg?style=for-the-badge&logo=bootstrap&logoColor=white"/>
 <img alt="jQuery" src="https://img.shields.io/badge/jquery-%230769AD.svg?style=for-the-badge&logo=jquery&logoColor=white"/>
 <img alt="HTML5" src="https://img.shields.io/badge/html5-%23E34F26.svg?style=for-the-badge&logo=html5&logoColor=white"/>
 <img alt="CSS3" src="https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge&logo=CSS3&logoColor=white"/>
</div>

## Back
<div>
 <img alt="Java" src="https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white"/>
 <img alt="Spring" src="https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white"/>
 <img alt="SpringBoot" src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"/>
 <img alt="JUnit5" src="https://img.shields.io/badge/JUnit5-25A162?style=for-the-badge&logo=mysql&logoColor=white"/>
 <img alt="oracle" src="https://img.shields.io/badge/oracle-4479A1?style=for-the-badge&logo=oracle&logoColor=red"/>
</div>
 
## Communication
<div>
 <img alt="GitHub" src="https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white"/>   
 <img alt="Discord" src="https://img.shields.io/badge/Discord-%237289DA.svg?style=for-the-badge&logo=discord&logoColor=white"/>
</div>

# 🔐 데이터베이스 명세
![ERD](https://github.com/kminimini/CocoT/assets/138873285/564e0dac-d911-4fa5-842e-59f0066c34a4)
# 📃 API 사용 내역

### 1. 국토교통부_(TAGO)_열차정보 (공공데이터포털)

### 2. 결제 (토스페이먼츠)

### 3. 주소 검색 (다음API)

# 🔍 화면 흐름도
![화면 흐름도-수정](https://github.com/kminimini/CocoT/assets/138873285/c5c4fe94-9462-4b65-a337-afe79b69ee2f)
# 📑 기능 구현 내용
## 1. 메인페이지 및 도시 기반 기차역 조회
  * 도시별 기차역 조회
  * 검색한 기차역에 대한 기차표 목록
  * 페이징 처리
  * 결제하기 화면
  * 결제하기 진행
![1 메인 도시 기차역 선택](https://github.com/kminimini/CocoT/assets/138873285/648024d7-731d-4c40-9add-533d55f710a0)
<!--![1-2 도시코드 기반 기차역 API조회](https://github.com/kminimini/CocoT/assets/138873285/a0836db8-bf24-4a0c-a20e-0cd5e9cb798c)</br> -->
## 🚧 OPEN API를 활용해 고유의 도시 코드에 대한 </br>기차역을 조회하여 선택할 수 있도록 하였습니다.
![2 기차표 검색](https://github.com/kminimini/CocoT/assets/138873285/2a5b19d5-8bee-48af-9017-537967540b33)
<!--![2-3 기차표 검색 콘솔화면 ](https://github.com/kminimini/CocoT/assets/138873285/46623928-c136-4095-b801-bcd42ea63590)</br> -->
## 🚧 OPEN API를 활용해 조회한 기차역에 대한 기차표 정보 목록을 과거의 데이터 정보를 </br>불러오는 것이 아닌 실제 코레일에 존재하는 기차표 목록을 보여줍니다.
![3 기차표 목록 페이지 이동](https://github.com/kminimini/CocoT/assets/138873285/e156e420-dff3-4511-8720-33bff3961710)
<!--![3-2  기차표 목록 페이지 이동 콘솔화면](https://github.com/kminimini/CocoT/assets/138873285/1f750ba0-9c61-4aa8-9218-e7b4962a08ab) -->
## 🚧 기차표 목록에서 페이징 처리 내용입니다. </br>조회를 하여 기차표 목록을 데이터베이스에 저장하는 과정에서 데이터가 </br>쌓이게 되는 문제점이 생겼습니다.</br>이에 대한 해결 방안은 페이지이동 처리가 되면 저장했던 기차표 데이터를 </br>전부 삭제하고 새롭게 API조회한 기차표 데이터를 저장할수 있는 트리거를 추가 하였습니다.
![4 결제하기 화면](https://github.com/kminimini/CocoT/assets/138873285/51806383-79ef-4dce-9a80-249d98695e3c)
![4-2 결제하기 진행](https://github.com/kminimini/CocoT/assets/138873285/c84d1038-522b-44e0-9045-06d9b558c077)
## 🚧결제하기 내용입니다. </br>기차표 목록에서 선택한 기차표에 대한 결제하기 화면으로 이동하였습니다.</br> 사용자가 기차표를 선택하면 선택한 기차표에 대한 데이터를 데이터베이스에 임시 저장하게 되고, 임시로 저장된 데이터에 대한 결제를 진행합니다.
## 2. 회원관리
  * 회원가입
  * 비밀번호 변경 및 회원탈퇴
  * 비밀번호 찾기와 이메일 인증
![5  회원가입](https://github.com/kminimini/CocoT/assets/138873285/175da7f2-f6e8-45a0-916c-14bee224c953)
![5-2 비밀번호 변경 및 회원 탈퇴](https://github.com/kminimini/CocoT/assets/138873285/7cf2900b-0f89-4bd3-b2df-260a4bedd5c8)
![5-3  비밀번호 찾기 이메일 인증](https://github.com/kminimini/CocoT/assets/138873285/d08daf9b-1589-41df-b58f-8a2974ae7f20)
![5-3  아이디 비밀번호 찾기](https://github.com/kminimini/CocoT/assets/138873285/e6fd8bc1-b8ad-41a4-8136-1f94a06baff5)
## 3. 게시판 및 QnA
  * 게시판 및 공지사항 댓글 + 비밀글
![6  게시판 공지사항 댓글 + 비밀글](https://github.com/kminimini/CocoT/assets/138873285/82f5d1f8-604d-4167-88df-623134ec801f)
![6-2  게시판 댓글 대댓글](https://github.com/kminimini/CocoT/assets/138873285/ea41d8ee-8686-49ca-83ed-2c6b17c01715)

# 😃 마치며 - 곽기민
  ## 프로젝트를 기획하고 설계하는 과정에서 우리가 원하는 기능을 구현하기 위해서 무엇이 필요한지 조사하였고, 조사하던 과정에서 수업시간에 배우지 못한 OPEN API를 활용하는 부분에서 처음 벽을 느꼈습니다.</br> 그리고 API를 활용하는 과정에서 어떻게 하면 조금 더 효율적으로 실시간 정보를 가져와서 보여줄 수 있을까를 제일 많이 고민하였고 개발하는 과정에서 코드를 많이 망가트려 보기도 하고 혹은, 여러 가지 방법을 사용하여 데이터를 가져오고 보여줄 수 있게 설계하는 시행착오를 겪으며 버전 이슈의 중요성과 설계, 로직의 중요성을 깨닫는 계기가 되었습니다.</br> 마지막으로 국비과정 동안 Spring Boot에 대하여 조사하였고 알고 있었지만, 해당 프로젝트를 진행하면서 Spring Boot에 대하여 심도 있게 공부하게 되는 계기가 되었고 아직은 미숙하지만 MVC패턴과 JPA, Oracle, 버전 관리 등에 대해 완벽하게 이해하게 되었습니다.</br> 또 깃을 통한 협업의 중요성과 디스코드의 화면 공유와 회의를 통해 글을 통해 소통하는 것보다 더욱 효과적으로 진정정 있게 회의를 진행할 수 있게 되었고 동시에 나도 미처 생각지도 못한 방법을 회의를 통해 깨닫게 될 수 있는 기회가 되어 팀원들과 소통이 가장 중요하다는 것을 많이 느꼈습니다.</br> 부족하지만 끝까지 읽어주셔서 감사합니다.


# 마치며 - 김양현
 ## 처음 프로젝트를 시작할 때는 기획과 설계를 기반으로 사용자의 입장에서 이 웹사이트를 접했을 때 필요한 것이 무엇인지, 어떻게 하면 더 빠르고 간단하게 접근할 수 있을지에 대해 많인 생각했습니다.</br> API를 설계할 때 데이터의 흐름을 명확하게 해야 했고, 보안성을 고려하여 비밀번호를 암호화하는 과정에서 적절한 암호화 방식과 인증 방법 등을 고민하는 과정에서 많은 어려움이 있었지만 기술 활용 자료나 책을 찾아보면서 기술에 대한 정보를 완벽히 습득하게 되는 계기가 되었습니다.</br> Git과 Discord를 통해 각자가 맡은 역할을 수행하면서도 끊임없이 의견을 공유하고, 문제가 발생할 때 마다 함께 해결 방법을 고민하다보면 해결책을 찾을 수 있다는 점을 몸소 경험할 수 있었습니다. 이번 프로젝트를 통해 단순한 기능 구현을 넘어, 문제를 해결하는 능력과 협업의 중요성을 배울 수 있었던 소중한 경험이 되었고 지속적으로 공부하면서 더 나은 서비스를 개발할 수 있도록 노력해야겠다는 다짐을 하게 되었습니다.
