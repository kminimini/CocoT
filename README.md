# 🚉 열차 에약 서비스 프로젝트
  ## 해당 프로젝트는 사용자가 기차여행을 즐기기 위해 커뮤니티를 제공하여 정보를 공유하고 더불어 조회와 결제를 동시에 진행할 수 있게 만들어진 서비스입니다.
# 📆 작업 기간
## 2023-12-18 ~ 2024-1-2 (16일)
# 👷 팀원 구성
### 팀장:[곽기민](https://github.com/kminimini) - 열차조회, 결제
### 팀원:[김양현](https://github.com/rladidgus) - 회원관리, 공지사항
### 팀원:[조해준](https://github.com/ChoHaeJun) - 게시판
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
![1-2 도시코드 기반 기차역 API조회](https://github.com/kminimini/CocoT/assets/138873285/a0836db8-bf24-4a0c-a20e-0cd5e9cb798c)</br>
## 🚧 OPEN API를 활용해 고유의 도시 코드에 대한 기차역을 조회하여 선택할 수 있도록 하였습니다.
![2 기차표 검색](https://github.com/kminimini/CocoT/assets/138873285/2a5b19d5-8bee-48af-9017-537967540b33)
![2-3 기차표 검색 콘솔화면 ](https://github.com/kminimini/CocoT/assets/138873285/46623928-c136-4095-b801-bcd42ea63590)</br>
## 🚧 OPEN API를 활용해 조회한 기차역에 대한 기차표 정보 목록을 과거의 데이터 정보를 불러오는 것이 아닌 실제 코레일에 존재하는 기차표 목록을 보여줍니다.
![3 기차표 목록 페이지 이동](https://github.com/kminimini/CocoT/assets/138873285/e156e420-dff3-4511-8720-33bff3961710)
## 🚧
![4 결제하기 화면](https://github.com/kminimini/CocoT/assets/138873285/51806383-79ef-4dce-9a80-249d98695e3c)
![4-2 결제하기 진행](https://github.com/kminimini/CocoT/assets/138873285/c84d1038-522b-44e0-9045-06d9b558c077)
## 🚧
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

