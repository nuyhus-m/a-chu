# 👶A-Chu

<img src ="https://i.imgur.com/DGSnGWf.png" width="100%" alt="배너">

<div align="center">

#### SSAFY 12기 특화 프로젝트

#### 🗓️2025.02.24 ~ 2025.04.11. (7주)

</div>

## 👶주요 기능

#### 중고 거래

- 육아용품은 사용주기가 짧은 경우가 많아, 사용하지않는 물품이 집 안에 쌓이는 일이 흔합니다. 하지만 고가의 제품도 많아 단기간 사용 후 처분하기에는 아까운 경우가 많으며,
  다른 가정에서 충분히 다시 사용할 수 있는 자원입니다. 따라서 판매자는 사용하지 않는 육아용품을 판매하거나 무료 나눔할 수 있으며, 구매자는 필요한 물건을 카테고리별로 찾거나 검색을 통해 손쉽게 거래할 수 있습니다.

#### 실시간 채팅

- 중고 거래 특성 상 판매자와 구매자의 원활한 소통은 중요합니다. 사용자들은 거래 관련 문의나 일정 조율을 실시간 채팅을 통해 빠르게 처리할 수 있습니다.  

#### 아기 맞춤 상품 추천

- `협업 필터링(CF)`과 `컨텐츠 기반 필터링(CB)`을 결합한 하이브리드 방식인 `LightFM` 라이브러리를 사용하여 추천 상품을 제공합니다. 부모의 활동 데이터(상품 조회, 찜, 구매)와 더불어 자녀의 특징(성별, 개월수)도 학습하여 현재 우리 아이에게 필요한 상품을 더 정교하게 추천해줍니다. 

#### 추억 기록

- 아이들은 성장 시기마다 필요한 물품이 달라지며 사용한 물건들에는 아이들과의 추억이 담깁니다. 처분하기 아까운 물건들을 거래하기 전에 해당 물건에 담긴 자녀와의 소중한 추억을 글과 사진으로 기록해보세요.

## 👶기술 스택

### Back-End

![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![](https://img.shields.io/badge/Spring%20Boot-6DB33F.svg?style=for-the-badge&logo=springboot&logoColor=white)
![](https://img.shields.io/badge/Spring%20Security-6DB33F.svg?style=for-the-badge&logo=springsecurity&logoColor=white)
![](https://img.shields.io/badge/mysql-%234479A1.svg?&style=for-the-badge&logo=mysql&logoColor=white)
![](https://img.shields.io/badge/firebase-%23FFCA28.svg?&style=for-the-badge&logo=firebase&logoColor=black)
![Amazon EC2](https://img.shields.io/badge/Amazon%20EC2-FF9900?style=for-the-badge&logo=amazonec2&logoColor=white)
![Amazon S3](https://img.shields.io/badge/Amazon%20S3-FF9900?style=for-the-badge&logo=amazons3&logoColor=white)

![FastAPI](https://img.shields.io/badge/FastAPI-005571?style=for-the-badge&logo=fastapi)
![Selenium](https://img.shields.io/badge/-selenium-%43B02A?style=for-the-badge&logo=selenium&logoColor=white)
![Pandas](https://img.shields.io/badge/pandas-%23150458.svg?style=for-the-badge&logo=pandas&logoColor=white)
![NumPy](https://img.shields.io/badge/numpy-%23013243.svg?style=for-the-badge&logo=numpy&logoColor=white)
![Anaconda](https://img.shields.io/badge/Anaconda-%2344A833.svg?style=for-the-badge&logo=anaconda&logoColor=white)

### Android

![AOS](https://img.shields.io/badge/android%20studio-%233DDC84.svg?&style=for-the-badge&logo=android%20studio&logoColor=white)
![JetpackCompose](https://img.shields.io/badge/JETPACK_COMPOSE-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)
![Kotlin](https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white)

### CI/CD


![kubernetes](https://img.shields.io/badge/kubernetes-%23326CE5.svg?&style=for-the-badge&logo=kubernetes&logoColor=white)
![helm](https://img.shields.io/badge/helm-%230F1689.svg?&style=for-the-badge&logo=helm&logoColor=white)
![jenkins](https://img.shields.io/badge/jenkins-%23D24939.svg?&style=for-the-badge&logo=jenkins&logoColor=white)
![argo](https://img.shields.io/badge/ARGO-EF7B4D?style=for-the-badge&logo=argo&logoColor=white)
![SonarQube](https://img.shields.io/badge/SonarQube-black?style=for-the-badge&logo=sonarqube&logoColor=4E9BCD)

### Co-Work

![gitlab](https://img.shields.io/badge/gitlab-%23FCA121.svg?&style=for-the-badge&logo=gitlab&logoColor=orange)
![jira](https://img.shields.io/badge/jira-%230052CC.svg?&style=for-the-badge&logo=jira&logoColor=white)
![notion](https://img.shields.io/badge/notion-%23000000.svg?&style=for-the-badge&logo=notion&logoColor=white)
![mattermost](https://img.shields.io/badge/mattermost-%230072C6.svg?&style=for-the-badge&logo=mattermost&logoColor=white)
![figma](https://img.shields.io/badge/figma-%23F24E1E.svg?&style=for-the-badge&logo=figma&logoColor=white)

## 👶Backend 모듈 구조

```
backend
|-- auth-user
|-- build
|-- chat
|-- clients
|   `-- recommend
|-- core
|   |-- core-api
|   `-- core-domain
|-- event
|   `-- event-core
|-- notification
|-- storage
|   |-- db-core
|   `-- file
|-- support
|   |-- logging
|   |-- monitoring
|   `-- restdocs
`-- tests
    `-- api-docs
```

## 👶Android 패키지 구조

```
├─core
│  ├─components
│  │  ├─dialog
│  │  └─textfield
│  ├─navigation
│  ├─theme
│  └─util
├─data
│  ├─database
│  ├─model
│  │  ├─auth
│  │  ├─baby
│  │  ├─chat
│  │  ├─memory
│  │  └─product
│  ├─network
│  └─repository
└─ui
    ├─auth
    │  ├─findaccount
    │  ├─signin
    │  └─signup
    ├─chat
    │  ├─chatdetail
    │  └─chatlist
    ├─home
    │  └─homecomponents
    ├─memory
    │  ├─memorydetail
    │  └─memoryupload
    ├─mypage
    │  ├─baby
    │  ├─likelist
    │  ├─recommendlist
    │  ├─tradelist
    │  └─userinfo
    └─product
        ├─productdetail
        ├─productlist
        └─uploadproduct
```
<!-- 
## 👶시스템 아키텍쳐

## 👶화면 구성

#### 📌회원가입

#### 📌로그인 및 로그아웃

#### 📌중고 거래

<img src="https://github-production-user-asset-6210df.s3.amazonaws.com/175380781/434640753-937ea968-78ef-45f3-9235-6ca7940fc5f2.gif?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAVCODYLSA53PQK4ZA%2F20250417%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20250417T050320Z&X-Amz-Expires=300&X-Amz-Signature=edc94a1a332c820e0cce9cd8200a46e2d9ed30826ca89320940563937fef2601&X-Amz-SignedHeaders=host" alt="중고거래" style="zoom:70%;" />

#### 📌실시간 채팅

<img src ="https://github-production-user-asset-6210df.s3.amazonaws.com/175380781/434667348-6a2a2b88-c728-4840-9fb6-f8618325448a.gif?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAVCODYLSA53PQK4ZA%2F20250417%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20250417T063823Z&X-Amz-Expires=300&X-Amz-Signature=a79adabf627442f284e431ae4a1b7da92ecff83bd5c15f23f015995b93a85d75&X-Amz-SignedHeaders=host" alt="채팅" style="zoom:70%;" />

#### 📌상품 추천

<img src = "https://github-production-user-asset-6210df.s3.amazonaws.com/175380781/434647866-dfebacb8-2285-4c6f-9fd6-8ba82d09b63d.gif?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAVCODYLSA53PQK4ZA%2F20250417%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20250417T053459Z&X-Amz-Expires=300&X-Amz-Signature=d2a7f2a962e34531709c00c4a7f9de6c44f7660cb9dfc6d570dc498295f7c32f&X-Amz-SignedHeaders=host" alt="추천" style="zoom:70%;"/>

#### 📌추억 기록

<img src = "https://github-production-user-asset-6210df.s3.amazonaws.com/175380781/434645875-4928ba9c-dccc-4c10-b90f-e5d31d22acb4.gif?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAVCODYLSA53PQK4ZA%2F20250417%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20250417T052700Z&X-Amz-Expires=300&X-Amz-Signature=ef349494e521c1a9e62f66d8170ec3726781f38e0ef8bb7c07506556327b7f53&X-Amz-SignedHeaders=host" alt="추억" style="zoom:70%;" /> -->

## 👶팀원 소개

<table style="width: 100%; table-layout: fixed;">
  <tr>
    <td align="center" valign="middle">
      <img src="https://i.imgur.com/ZeEnafw.png" alt="Image" style="width: 500%; height: 500%; object-fit: cover;">
    </td>
    <td align="center" valign="middle">
      <img src="https://i.imgur.com/pi9C3JW.png" alt="Image" style="width: 100%; height: 100%; object-fit: cover;">
    </td>
    <td align="center" valign="middle">
      <img src="https://i.imgur.com/09Gk1Ze.png" alt="Image" style="width: 100%; height: 100%; object-fit: cover;">
    </td>
    <td align="center" valign="middle">
      <img src="https://i.imgur.com/Wd22IyX.png" alt="Image" style="width: 100%; height: 100%; object-fit: cover;">
    </td>
    <td align="center" valign="middle">
      <img src="https://i.imgur.com/ZPAe6lv.png" alt="Image" style="width: 100%; height: 100%; object-fit: cover;">
    </td>
    <td align="center" valign="middle">
      <img src="https://i.imgur.com/3OhwCKE.png" alt="Image" style="width: 100%; height: 100%; object-fit: cover;">
    </td>
  </tr>
  <tr style="background: #f5f5f5;">
    <td align="center" valign="middle">Back-End</td>
    <td align="center" valign="middle">Back-End</td>
    <td align="center" valign="middle">Back-End</td>
    <td align="center" valign="middle">Back-End</td>
    <td align="center" valign="middle">Android</td>
    <td align="center" valign="middle">Android</td>
  </tr>
  <tr style="background: #f5f5f5;">
    <td align="center" valign="middle">
      <ul>
        <li>채팅 시스템 구현</li>
      </ul>
      <ul>
        <li>이미지 처리 구현</li>
      </ul>
      <ul>
        <li>인증/인가 구현</li>
      </ul>
      <ul>
        <li>CI/CD</li>
      </ul>
    </td>
    <td align="center" valign="middle">
      <ul>
        <li>중고거래 시스템 구현</li>
      </ul>
      <ul>
        <li>알림 시스템 구현</li>
      </ul>
      <ul>
        <li>API 문서 작성</li>
      </ul>
      <ul>
        <li>영상 제작</li>
      </ul>
    </td>
    <td align="center" valign="middle">
      <ul>
        <li>중고거래 시스템 구현</li>
      </ul>
      <ul>
        <li>ERD 설계</li>
      </ul>
      <ul>
        <li>API 문서 작성</li>
      </ul>
      <ul>
        <li>최종 발표</li>
      </ul>
    </td>
    <td align="center" valign="middle">
      <ul>
        <li>추천 시스템 구현</li>
      </ul>
      <ul>
        <li>데이터 크롤링</li>
      </ul>
      <ul>
        <li>데이터 전처리</li>
      </ul>
      <ul>
        <li>API 문서 작성</li>
      </ul>
    </td>
    <td align="center" valign="middle">
      <ul>
        <li>UI/UX 디자인</li>
      </ul>
      <ul>
        <li>추억, 추천 기능 구현</li>
      </ul>
      <ul>
        <li>홈, 마이페이지 구현</li>
      </ul>
      <ul>
        <li>이미지 처리</li>
      </ul>
      <ul>
        <li>알림 기능 구현</li>
      </ul>
    </td>
    <td align="center" valign="middle">
      <ul>
        <li>프로젝트 아키텍쳐 설계 및 세팅</li>
      </ul>
      <ul>
        <li>로그인/회원가입 구현</li>
      </ul>
      <ul>
        <li>중고거래 기능 구현</li>
      </ul>
      <ul>
        <li>채팅 기능 구현</li>
      </ul>
    </td>
  </tr>
  <tr>
    <td align="center" valign="middle">김덕윤👑</td>
    <td align="center" valign="middle">박재영</td>
    <td align="center" valign="middle">임지혜</td>
    <td align="center" valign="middle">채용수</td>
    <td align="center" valign="middle">안주현</td>
    <td align="center" valign="middle">민수현</td>
  </tr>
</table>