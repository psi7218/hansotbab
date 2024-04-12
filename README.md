# 내 손안의 공유 냉장고로 마음을 나눠요 : 한솥밥 :yellow_heart:
![메인페이지](https://github.com/blacklabf/joblog/assets/108640460/55a3b5d2-291d-4d75-affd-170b12d9e463)

## 목차
[1. 프로젝트 개요](#1️⃣-프로젝트-개요)<br>
[2. 주요 기능](#two-주요-기능)<br>
[3. 화면 설명](#three-화면-설명)<br>
[4. 기술 소개](#four-기술-소개)<br>
[5. 시스템 아키텍처](#five-시스템-아키텍처)<br>
[6. 파일 구조](#six-파일-구조)<br>
[7. 기술 스택](#seven-기술-스택)<br>
[8. 설계 문서](#eight-설계-문서)<br>
[9. 팀 소개](#nine-팀-소개)<br>
<br><br>


## 1️⃣ 프로젝트 개요
### 프로젝트 소개
> <span style="font-size:125%; font-weight:bold;">"한솥밥 "은 공유냉장고를 통해 시민들 간의 자발적인 음식 나눔을 촉진하고, 음식 낭비를 줄이는 서비스입니다.</span>  

### 프로젝트 진행 기간
<details style="margin-left: 5px;">
<summary><strong>전체 일정 : 2024.02.26 ~ 2024.04.04 (약 6주)</strong></summary>
<div>
    <ul>
        <li>기획: 2024.02.26 ~ 2024.03.03</li>
        <li>설계: 2024.03.04 ~ 2024.03.10</li>
        <li>구현: 2024.03.11 ~ 2024.04.02</li>
        <li>배포: 2024.04.03</li>
        <li>버그 수정: 2024.04.03 ~ 2024.04.04</li>
    </ul>
</div>
</details>
<br><br>

## :two: 주요 기능 
### 💡 음식 기부하기
- 사용자들은 손쉽게 음식을 기부할 수 있습니다.
- 이미지 인식으로 상품명과 대분류가 자동으로 입력됩니다.
- 기부 음식명을 입력할 때 추천 검색어를 통해 원활한 등록이 가능합니다.
### 💡 음식 가져가기
- 공유 냉장고안의 상품 리스트를 확인하고 가져갈 수 있습니다.
- 음식을 가져간 후 다음 사용자를 위해 재고 가능성을 선택한 것과 가져간 인원수를 바탕으로 재고 예측을 해줍니다.
### 💡 감사인사 나누기 및 위시리스트 작성
- 감사인사를 남겨 기부자에게 마음을 전달할 수 있습니다
- 봉사자와 기부자들의 원동력을 제공합니다.
- 사용자들의 접근성을 향상하고 기부를 독려합니다.
### 💡 알림 기능
- 즐겨찾기한 냉장고에 새로운 음식이 기부되었을 때 알림을 받을 수 있습니다.
- 기부한 음식에 리뷰를 받으면 알림을 통해 확인할 수 있습니다.
<br><br>

## :three: 화면 설명
<table>
    <tr height="140px">
        <td align="center" ><img width="300px" src="https://github.com/blacklabf/springboot-study/assets/108640460/1ea043c7-1af7-4f5b-94ad-25614f0ef3e8"/></td>
        <td align="center" ><img width="300px" src="https://github.com/blacklabf/springboot-study/assets/108640460/3f7b1f8a-c147-4a2e-a202-c63fdbaf0a42"/></td>
        <td align="center" ><img width="300px" src="https://github.com/blacklabf/springboot-study/assets/108640460/81e86de9-f34c-4310-9205-bb9286513d47"/></td>
        <td align="center" ><img width="300px" src="https://github.com/blacklabf/joblog/assets/108640460/20e36918-9cea-4458-8570-10fd01d531c2"/></td>
    <tr/>
    <tr>
       <td align="center">🔼 메인페이지</td>
       <td align="center">🔼 냉장고 탭 이동</td>
       <td align="center">🔼 음식 등록시, <br>음식명 자동완성</td>
       <td align="center">🔼 음식등록시, <br>검색어 자동완성</td>
    </tr>
</table>
<br><br>
<table>
    <tr height="140px">
        <td align="center" ><img width="300px" src="https://github.com/blacklabf/springboot-study/assets/108640460/b6a972fb-a294-4456-a223-3e6d08bb97e3"/> </td>
        <td align="center" ><img width="300px" src="https://github.com/blacklabf/springboot-study/assets/108640460/6797396a-6820-4f02-b756-527c0e33a577"/> </td>
        <td align="center" ><img width="300px" src="https://github.com/blacklabf/springboot-study/assets/108640460/2e24e230-5d0d-4d70-871b-6878fc643a46"/> </td>
        <td align="center" ><img width="300px" src="https://github.com/blacklabf/springboot-study/assets/108640460/cd2de631-eda1-410c-81a7-358a3a537617"/> </td>
    <tr/>
    <tr>
       <td align="center">🔼 가져가기 및 감사인사</td>
       <td align="center">🔼 감사알림</td>
       <td align="center">🔼 즐겨찾기 및 <br>음식등록알림</td>
       <td align="center">🔼 감사인사 및 위시리스트</td>
    </tr>
</table>
<br><br>


## :four: 기술 소개
### ⭐ AI
- 이미지 분류 모델(efficientnet b0)을 이용한 상품 이미지 분류
- FastAPI로 모델서빙

### ⭐ 실시간 알림
- 백그라운드, 포그라운드, 절전상태에서도 즉각적인 알림
- 배터리 낭비 최소화 
- firebase Cloud Messaging 으로 구현

### ⭐ Elastic Search & Kibana
- Elastic Search
    - ElasticSearch를 사용하여 음식 빅데이터의  자동완성기능을 더 빠르고 정확하게 구현
    - match 쿼리에 fuzziness 설정을 통해서 검색시 오타보정
- Kibana
    - Elastic Search에 저장된 데이터를 시각화해서 확인
    - console에서 자동완성기능을 위한 index를 생성 및 설정

### ⭐ DevOps
- Jenkins를 활용한 CI/CD 파이프라인 구축
- Gitlab Webhook을 이용한 자동 빌드 환경(CI) 구축
- 파이프라인 코드 및 쉘 스크립트 작성으로 자동 배포 환경(CD) 구축
- SonarQube를 활용한 정적 분석

### ⭐ 빅데이터 분산 및 처리
- Hadoop & Spark를 활용해 이미지와 정보를 저장하고 분산하여 모델 학습 및 냉장고마다 기부수를 통계

### ⭐ FrontEnd
- dart기반 flutter를 통해 프로젝트 진행 
- SharedPreferences를 통해 로컬Storage 관리

<br><br>


## :five: 시스템 아키텍처
![시스템아키텍쳐](https://github.com/blacklabf/joblog/assets/108640460/98d59c19-7e96-4ad8-ba27-bcab2cb5bb30)

<br><br>

## :six: 파일 구조
<details  style="margin-left: 5px;">
<summary><b>server</b></summary>
<div>

```
/home/ubuntu
📦 /home/ubuntu
 ┣ 📂deploy
 ┃ ┣ 📂back
 ┃ ┃ ┣ 📜Dockerfile
 ┃ ┃ ┣ 📜docker-compose.yaml
 ┃ ┃ ┗ 📜hansotbab-0.0.1-SNAPSHOT.jar
 ┃ ┗ 📜deploy.sh
 ┣ 📂env
 ┃ ┣ 📜application.properties
 ┃ ┗ 📜firebase_service_key.json
 ┗ 📂workspace
   ┗ 📂docker
     ┣ 📜docker-compose.yaml
     ┗ 📂sonarqube
       ┗ 📜docker-compose.yaml
```

</div>
</details>
<br>

<details  style="margin-left: 5px;">
<summary><b>back</b></summary>
<div>

```
📦server/hansotbab
 ┣ 📂.gradle
 ┣ 📂.idea
 ┣ 📂gradle
 ┣ 📂src
 ┃ ┣ 📂main
 ┃ ┃ ┣ 📂java
 ┃ ┃ ┃ ┗ 📂com
 ┃ ┃ ┃ ┃ ┗ 📂b209
 ┃ ┃ ┃ ┃ ┃ ┗ 📂hansotbab
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂alarm
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂config
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜FCMConfig.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜AlarmController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂request
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜AlarmRequestDTO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜AlarmUserRequestDTO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂entity
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜FcmToken.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂repository
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜FcmTokenRepository.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜AlarmService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜AlarmServiceImpl.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂food
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜FoodController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜FoodRequestDto.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂entity
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜ElasticSearchItems.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂repository
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜ElasticSearchItemsRepository.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜FoodService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜JsonDataUtils.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂fridge
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜FridgeController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂request
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜ProductCreateRequestDTO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜ProductUpdateRequestDTO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂response
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜FridgeListResponseDTO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜ProductDetailResponseDTO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜ProductListResponseDTO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂entity
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜Fridge.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜FridgeLike.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜Product.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜ProductBring.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜ProductCategory.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜ProductCondition.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂repository
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜FridgeLikeRepository.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜FridgeRepository.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜ProductBringRepository.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜ProductRepository.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜FridgeService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜ProductService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜S3Service.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜WebClientService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂global
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂advice
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜AuthControllerAdvice.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂config
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜AuditConfig.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜ElasticSearchConfig.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜S3Config.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜SwaggerConfig.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂entity
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜BaseEntity.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜BaseTimeEntity.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂review
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜ReviewController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂request
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜ReviewLikeRequestDTO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜ReviewRequestDTO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂response
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜ReviewResponseDTO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂entity
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜Review.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜ReviewLike.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂repository
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜ReviewRepository.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜ReviewService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜ReviewServiceImpl.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂user
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂config
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜RedisConfig.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜RestTemplateConfig.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜SecurityConfig.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜WebConfig.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜UserController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂request
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜AdminReqestDTO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜LoginRequestDTO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜NicknameDTO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜RegisterRequestDTO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜TokenReissueRequestDTO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂response
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜LikedFridgeDTO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜TokenDTO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜UserProfileDTO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜UserResponseDTO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂entity
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜RefreshToken.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜RoleType.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜User.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜UserPrincipal.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂exception
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜AlreadyAuthenticatedException.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜InvalidTokenException.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜NotAuthenticactedException.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜NoUserExistsException.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜RegisterFailedException.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜UnauthorizedException.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂handler
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜CustomAccessDeniedHandler.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜CustomAuthenticationEntryPoint.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜CustomLogoutHandler.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜CustomLogoutSuccessHandler.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂jwt
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜JWTAuthenticationFilter.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜LoginAuthenticationToken.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜TokenProvider.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂repository
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜TokenRepository.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜UserRepository.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜RefreshTokenService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜UserPrincipalService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜UserService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂util
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜AuthUtils.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜CookieUtils.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂wishlist
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂controller
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜WishlistController.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂dto
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂request
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜WishlistLikeRequestDTO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜WishlistRequestDTO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂response
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜WishlistResponseDTO.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂entity
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜Wishlist.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜WishlistLike.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂repository
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜WishlistLikeRepository.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜WishlistRepository.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📜WishlistService.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜WishlistServiceImpl.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜HansotbabApplication.java
 ┃ ┃ ┗ 📂resources
 ┃ ┃ ┃ ┣ 📂files
 ┃ ┃ ┃ ┃ ┣ 📜food_info.json
 ┃ ┃ ┃ ┃ ┗ 📜food_result.json
 ┃ ┃ ┃ ┣ 📂firebase
 ┃ ┃ ┃ ┃ ┣ 📜firebase.json
 ┃ ┃ ┃ ┃ ┗ 📜firebase_service_key.json
 ┃ ┃ ┃ ┣ 📂static
 ┃ ┃ ┃ ┃ ┗ 📜index.html
 ┃ ┃ ┃ ┗ 📜log4j2.xml
 ┃ ┗ 📂test
 ┃ ┃ ┗ 📂java
 ┃ ┃ ┃ ┗ 📂com
 ┃ ┃ ┃ ┃ ┗ 📂b209
 ┃ ┃ ┃ ┃ ┃ ┗ 📂hansotbab
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂fridge
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂repository
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜FridgeRepositoryTest.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜WebClientServiceTest.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂user
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂repository
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜UserRepositoryTest.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂wishlist
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┣ 📂repository
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜WishlistRepositoryTest.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📂service
 ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜WishlistServiceTest.java
 ┃ ┃ ┃ ┃ ┃ ┃ ┗ 📜HansotbabApplicationTests.java
 ┣ 📜.gitignore
 ┣ 📜build.gradle
 ┣ 📜compose.yaml
 ┣ 📜gradlew
 ┣ 📜gradlew.bat
 ┗ 📜settings.gradle
```

</div>
</details>
<br>

<details  style="margin-left: 5px;">
<summary><b>front</b></summary>
<div>

```
📦flutter/hansotbab
 ┣ 📂android
 ┣ 📂assets
 ┣ 📂build
 ┣ 📂lib
 ┃ ┣ 📂class
 ┃ ┣ 📂firebase
 ┃ ┃ ┣ 📜fcmSetting.dart
 ┃ ┃ ┗ 📜firebase_options.dart
 ┃ ┣ 📂pages
 ┃ ┣ 📂providers
 ┃ ┃ ┣ 📜api.dart
 ┃ ┃ ┣ 📜loginState.dart
 ┃ ┃ ┗ 📜userpreferences.dart
 ┃ ┣ 📂widget
 ┃ ┃ ┣ 📜bottomappbar.dart
 ┃ ┃ ┣ 📜button.dart
 ┃ ┃ ┣ 📜carousel.dart
 ┃ ┃ ┣ 📜imagepicker.dart
 ┃ ┃ ┣ 📜nearst.dart
 ┃ ┃ ┗ 📜navermap.dart
 ┣ 📜.gitignore
 ┣ 📜analysis_options.yaml
 ┣ 📜devtools_options.yaml
 ┣ 📜flutter_jank_metrics_01.json
 ┣ 📜flutter_native_splash.yaml
 ┣ 📜pubspec.yaml

```
</div>
</details>
<br>

<details  style="margin-left: 5px;">
<summary><b>fastapi</b></summary>
<div>

```
📦hansotbab
 ┣ 📂.venv
 ┃ ┗ 📂Lib
 ┣ 📂app
 ┃ ┣ 📂configs
 ┃ ┃ ┣ 📜efficientnet_b0_base.yaml
 ┃ ┃ ┗ 📜efficientnet_b0_ours.yaml
 ┃ ┣ 📂dataset
 ┃ ┃ ┣ 📂split
 ┃ ┃ ┃ ┣ 📜test.txt
 ┃ ┃ ┃ ┣ 📜train.txt
 ┃ ┃ ┃ ┗ 📜validation.txt
 ┃ ┃ ┗ 📜error_log.txt
 ┃ ┣ 📂images
 ┃ ┣ 📂models
 ┃ ┃ ┣ 📂base
 ┃ ┃ ┃ ┣ 📂checkpoint
 ┃ ┃ ┃ ┃ ┗ 📜best_top1_validation.pth
 ┃ ┃ ┃ ┣ 📜events.out.tfevents.1615854151.1f2595b872d1.1.0
 ┃ ┃ ┃ ┣ 📜log.log
 ┃ ┃ ┃ ┗ 📜test_log.txt
 ┃ ┃ ┗ 📂ours
 ┃ ┃ ┃ ┣ 📂checkpoint
 ┃ ┃ ┃ ┃ ┗ 📜best_top1_validation.pth
 ┃ ┃ ┃ ┣ 📜events.out.tfevents.1615855075.f254be47ec21.1.0
 ┃ ┃ ┃ ┣ 📜log.log
 ┃ ┃ ┃ ┗ 📜test_log.txt
 ┃ ┣ 📂products
 ┃ ┃ ┗ 📂test
 ┃ ┣ 📂src
 ┃ ┃ ┣ 📂LotteClassification
 ┃ ┃ ┃ ┣ 📂datasets
 ┃ ┃ ┃ ┃ ┗ 📜__init__.py
 ┃ ┃ ┃ ┣ 📂models
 ┃ ┃ ┃ ┃ ┣ 📂LotteNet
 ┃ ┃ ┃ ┃ ┃ ┣ 📜LotteNet.py
 ┃ ┃ ┃ ┃ ┃ ┗ 📜__init__.py
 ┃ ┃ ┃ ┃ ┗ 📜__init__.py
 ┃ ┃ ┃ ┣ 📂utils
 ┃ ┃ ┃ ┃ ┣ 📜config.py
 ┃ ┃ ┃ ┃ ┣ 📜logger.py
 ┃ ┃ ┃ ┃ ┗ 📜__init__.py
 ┃ ┃ ┃ ┣ 📜initialize.py
 ┃ ┃ ┃ ┣ 📜LotteClassification.py
 ┃ ┃ ┃ ┗ 📜__init__.py
 ┃ ┃ ┗ 📜docker_run.py
 ┃ ┣ 📜generate_product_dir.py
 ┃ ┣ 📜main.py
 ┃ ┗ 📜product_meta_info.json
 ┣ 📜Dockerfile
 ┗ 📜requirements.txt
```

</div>
</details>
<br>


## :seven: 기술 스택
<div style="display:flex; flex-direction:column; align-items:flex-start;">
    <p><strong>⚡ Management Tool</stron-g></p>
    <div>
        <img src="https://img.shields.io/badge/jira-0052CC?style=for-the-badge&logo=jira&logoColor=white"> 
        <img src="https://img.shields.io/badge/gitlab-FC6D26?style=for-the-badge&logo=gitlab&logoColor=white">  
        <img src="https://img.shields.io/badge/mattermost-0058CC?style=for-the-badge&logo=mattermost&logoColor=white"> 
        <img src="https://img.shields.io/badge/notion-000000?style=for-the-badge&logo=notion&logoColor=white"> 
       <img src="https://img.shields.io/badge/figma-F24E1E?style=for-the-badge&logo=figma&logoColor=white">
       <img src="https://img.shields.io/badge/termius-000000?style=for-the-badge&logo=termius&logoColor=white">
    </div>
    <br>
    <p><strong>⚡ IDE</strong></p>
    <div>
        <img src="https://img.shields.io/badge/vscode 1.86-007ACC?style=for-the-badge&logo=visualstudiocode&logoColor=white"> 
        <img src="https://img.shields.io/badge/intellij-000000?style=for-the-badge&logo=intellijidea&logoColor=white">  
        <img src="https://img.shields.io/badge/android studio 1.1.28-569A31?style=for-the-badge&logo=redis&logoColor=white">
    </div>
    <br>
    <!-- Server -->
    <p><strong>⚡ Server</strong></p>
    <div>
        <img src="https://img.shields.io/badge/ubuntu 20.04-E95420?style=for-the-badge&logo=ubuntu&logoColor=white">    
        <img src="https://img.shields.io/badge/nginx 1.18.0-009639?style=for-the-badge&logo=nginx&logoColor=white">
        <img src="https://img.shields.io/badge/amazon ec2-FF9900?style=for-the-badge&logo=amazonec2&logoColor=white">
        <img src="https://img.shields.io/badge/amazon s3-569A31?style=for-the-badge&logo=amazons3&logoColor=white">
        <img src="https://img.shields.io/badge/redis 7.2.4-DC382D?style=for-the-badge&logo=redis&logoColor=white">
    </div>
    <br>
    <p><strong>⚡ Infra</strong></p>
    <div>
        <img src="https://img.shields.io/badge/docker 25.0.4-2496ED?style=for-the-badge&logo=docker&logoColor=white">
        <img src="https://img.shields.io/badge/jenkins 2.448-D24939?style=for-the-badge&logo=jenkins&logoColor=white">
        <img src="https://img.shields.io/badge/sonarqube-4E9BCD?style=for-the-badge&logo=sonarqube&logoColor=white">
        <img src="https://img.shields.io/badge/elasticsearch 4.4.1.3373-005571?style=for-the-badge&logo=elasticsearch&logoColor=white">
        <img src="https://img.shields.io/badge/Hadoop 3.3.6-66CCFF?style=for-the-badge&logo=apachehadoop&logoColor=white">
         <img src="https://img.shields.io/badge/Spark 3.5.16-E25A1C?style=for-the-badge&logo=apachespark&logoColor=white">
    </div>
    <br>
    <!-- Frontend -->
    <p><strong>⚡ Frontend</strong></p>
    <div>
        <img src="https://img.shields.io/badge/Flutter 3.86-02569B?style=for-the-badge&logo=flutter&logoColor=white"> 
        <img src="https://img.shields.io/badge/dart 3.86-0175C2?style=for-the-badge&logo=dart&logoColor=white"> 
    </div>
    <br>
    <!-- Backend -->
    <p><strong>⚡ Backend</strong></p>
    <div>
        <img src="https://img.shields.io/badge/Java 17-007396?style=for-the-badge&logo=Java&logoColor=white"> 
        <img src="https://img.shields.io/badge/Spring Boot 3.2.3-6DB33F?style=for-the-badge&logo=spring boot&logoColor=white">
        <img src="https://img.shields.io/badge/gralde 8.5-02303A?style=for-the-badge&logo=gradle&logoColor=white">
       <img src="https://img.shields.io/badge/spring jpa-6DB33F?style=for-the-badge&logo=spring&logoColor=white">
       <img src="https://img.shields.io/badge/spring security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white">
       <img src="https://img.shields.io/badge/jwt-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white">
       <img src="https://img.shields.io/badge/swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=white">
        <img src="https://img.shields.io/badge/fastapi 0.110.0-009688?style=for-the-badge&logo=fastapi&logoColor=white">
    </div>
    <br>
    <p><strong>⚡ Database</strong></p>
    <div>
        <img src="https://img.shields.io/badge/mysql 8.0.35-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
    </div>
    <br>
    <!-- AI -->
    <p><strong>⚡ AI</strong></p>
    <div>
        <img src="https://img.shields.io/badge/torch 2.2.1+cu121-EE4C2C?style=for-the-badge&logo=pytorch&logoColor=white">
        <img src="https://img.shields.io/badge/python 3.11.8-3776AB?style=for-the-badge&logo=python&logoColor=white">
    </div>
    <br>
    <!--Alarm-->
    <p><strong>⚡ Alarm</strong></p>
    <div>
        <img src="https://img.shields.io/badge/firebase-FFCA28?style=for-the-badge&logo=Firebase&logoColor=white">
    </div>
</div>
<br><br>

## :eight: 문서

### 📂ERD
![erd](https://github.com/blacklabf/joblog/assets/108640460/b6e0a9c5-85de-4e78-9aff-0e0f3da25183)

### 📂기능명세서
<img src="https://github.com/blacklabf/joblog/assets/108640460/88931eeb-b185-4ab3-a96d-ad43050a1266" width="400" />
<img src="https://github.com/blacklabf/joblog/assets/108640460/7c235404-27cd-4672-a9e6-f3c78df488e9" width="400" />

### 📂API 명세서
<img src="https://github.com/blacklabf/joblog/assets/108640460/9cd8cb15-b26e-4143-8e66-e2974bab3b87" width="400" />
<img src="https://github.com/blacklabf/joblog/assets/108640460/49d8b624-18f2-438a-be63-b1d1fbf1bd8f" width="400" />

### 📂포팅메뉴얼
:arrow_forward: [포팅 메뉴얼](/exec/포팅 매뉴얼 - 한솥밥ver2.pdf)

## :nine: 팀 소개
|![박성인](https://github.com/blacklabf/joblog/assets/108640460/3c750499-96dd-4c25-9a3a-021f82c8908a)|![박정연](https://github.com/blacklabf/joblog/assets/108640460/8872ad2e-c6b2-4330-9cf9-37c442b3c994)|![방소영](https://github.com/blacklabf/joblog/assets/108640460/dbb7d63a-e372-45cd-a30e-4353f2bddecb)|![이지영](https://github.com/blacklabf/joblog/assets/108640460/1eb0dc41-d0cd-47c0-ad88-656167389117)|![이현영](https://github.com/blacklabf/joblog/assets/108640460/7834e422-ee00-4ba3-8033-abd17550b103)|![주동현](https://github.com/blacklabf/joblog/assets/108640460/a87149c6-376a-46f6-b4b2-c35f815c9f81)|
|:--:|:--:|:--:|:--:|:--:|:--:|
|박성인|박정연|방소영|이지영|이현영|주동현|
|FrontEnd & Nginx|Backend & Hadoop & Spark|Leader & Backend & Alarm|DevOps & AI|BackEnd & ElasticSearch|Frontend|
<br>

