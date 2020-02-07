<div align="center">
 <img src="https://user-images.githubusercontent.com/48546542/73995432-f22cc400-499b-11ea-9dd2-2125c260121b.png" width=15%>
</div>

# 목차
1. [Overview](#overview)

2. [Before You Begin & Prerequisites](#before-you-begin-&-prerequisites)

3. [Setup](#setup)

4. [Other](#other)

   


# Overview
기존의 웹 기술 프로젝트의 SW 개발 **비서의 기능**과 **웹 + 디자인**의 **SNS**적인 부분을 융합하여 
자신의 포트폴리오를 관리하고 피드백이 가능한 개발자 커뮤니티



# Before You Begin & Prerequisites
local에서 프로젝트 실행을 위해 필요한 tools

* Node.js - [Node.js 홈페이지](http://nodejs.org/) ver. Window
* Vue.js - [Vue.js 공식문서](https://kr.vuejs.org/v2/guide/) 
* Git - [git 다운로드 & 프로젝트 clone](https://git-scm.com/downloads) ver. Window
* Maven - [Maven 다운로드](https://maven.apache.org/download.cgi#) - [설정방법](https://jiwondh.github.io/2018/08/08/maven-install/)



# **Directory** explanation

* backend - IDE환경 Maven, Spring boot 백엔드 서버

* frontend - vue.js를 이용한 프론트 package

  

# Setup

### frontend build

1. node.js 다운로드([설치방법](https://javacpro.tistory.com/62) 완료 후 verson 확인)

```bash
$ npm --verson
```

2. frontend/ 안에서 package 설치

```bash
$ npm install
```

3. vue project package build

```bash
$ npm run build
```

4. dist 폴더안의 내용을 Spring boot 프로젝트 디렉토리에 배포 (src/main/webapp/ 아래)

   ![deploy](https://user-images.githubusercontent.com/48546542/73995659-a3335e80-499c-11ea-8c93-6b8afda5a664.PNG)

   * webapp이 없는 경우 생성

### backend build

* spring boot project package를 모두 묶어(front project 포함) jar, 또는 war로 배포 
* 해당 프로젝트는 .war로 배포 (jar은 context-path를 찾지 못 함배포하면 홈 화면 접속 불가)



1. pom.xml에 package 방법 war 추가

   <img src="https://user-images.githubusercontent.com/48546542/73995995-babf1700-499d-11ea-927b-b52da92f8455.PNG" alt="package" style="zoom: 150%;" />

2. project 폴더 우클릭 -> Run As -> 4. Maven Build

3. target 폴더에 .war 파일 생성



### AWS 배포 방법

**WinSCP설치**

- WinSCP는 마이크로소프트 윈도용으로 개발된 자유 및 오픈 소스 SFTP, SCP 및 FTP 클라이언트

- git bash로 aws ubuntu작업하는 것이 불편하기 때문에 설치

- 다운로드 사이트(클릭시 바로 다운)

  [Downloading WinSCP-5.15.9-Setup.exe :: WinSCP](https://winscp.net/download/WinSCP-5.15.9-Setup.exe)

- 설정 방법

  1. SCP 프로토콜 선택, 호스트 ip입력, 사용자이름에 ubuntu 입력

  ![1](https://user-images.githubusercontent.com/48546542/73997188-25258680-49a1-11ea-924c-c4449129d3ce.png)

  

  2. 고급 선택

     ![2](https://user-images.githubusercontent.com/48546542/73997190-2656b380-49a1-11ea-8857-ceaf8b7e1b9c.png)

     

  3. 인증, 파일 선택 클릭(.pem 파일 넣어야함)

     ![3](https://user-images.githubusercontent.com/48546542/73997194-2951a400-49a1-11ea-82e5-f3f941388a92.png)

     

  4. 모든 개인키 파일 선택

     ![4](https://user-images.githubusercontent.com/48546542/73997199-2a82d100-49a1-11ea-95ef-f1708866c671.png)

     

  5. .pem 파일을 .ppk로 바꾸어 줌 → 확인 클릭

     ![_](https://user-images.githubusercontent.com/48546542/73997353-9402df80-49a1-11ea-9938-4b06f26b09bc.png)

     

  6. 변환된 키 저장하고 cert.ppk 파일 선택

     ![5](https://user-images.githubusercontent.com/48546542/73997202-2bb3fe00-49a1-11ea-877c-607e9a3f66c4.png)

     

  7. 아래 윈도우 창이 뜨면 성공

     ![6](https://user-images.githubusercontent.com/48546542/73997203-2ce52b00-49a1-11ea-9589-831565c303ea.png)

  - 왼쪽이 내로컬 pc, 오른쪽이 원격(aws 서버)
  - home/ubuntu/ 하위 폴더 아래에 .war 배포

**database 설정 Local에 데이터베이스 서버가동(자기 컴터 항상 켜둬야함)**

- aws에 mysql 설치 후 통으로 db server까지 배포 할려고 했으나 권한? 아니면 버전 문제로 mysql 명령어가 하나도 안먹힘..그래서 spring boot war파일만 aws 배포하고 db는 로컬 서버에 둬야함

- db 계정을 하나 파줘야 한다. 외부에서 접속 하려면 모든 아이피로 접속 가능하게 또는 1개 아이피만 접속가능하게 aws에서만 접근 가능하도록 하는 것이 안전

  [MySQL Workbench 사용자 계정 생성/권한 부여 방법](https://mystyle1057.tistory.com/entry/MySQL-Workbench-사용자-계정-생성권한-부여-방법)



### AWS RDB 사용도 있지만 goole cloud platform SQL 서버 이용이 엄청 편하당 mysql wrokbench랑 연동 쉽게 가능

- 1년 300$ 무료

- 구글 계정있으면 그냥 쌉가능

- 구글 클라우드 플랫폼 프로젝트 생성 → sql 콘솔에서 mysql 인스턴스 생성

  [MySQL용 Cloud SQL 빠른 시작 | Google Cloud](https://cloud.google.com/sql/docs/mysql/quickstart?hl=ko)

- 더하기 클릭

  ![7](https://user-images.githubusercontent.com/48546542/73997206-2d7dc180-49a1-11ea-9792-3d82a41800c4.png)

  

- 아래 사진에 표시해둔 순서대로 클릭

  ![8](https://user-images.githubusercontent.com/48546542/73997208-2eaeee80-49a1-11ea-84d1-465b83566e66.png)

  1. 커낵션 이름: 원하는 이름 사용

  2. Hostname GCP(구글 클라우드 플랫폼)에 있는 sql 서버 ip를 호스트 사용 포트는 3306

     ![__](https://user-images.githubusercontent.com/48546542/73997696-9580d780-49a2-11ea-9270-a1e0954f8357.png)

  3. 구글 sql 인스턴스 생성시 root 비밀번호 설정한 것 기입

  4. 테스트 연결 해서 성공하면 완료

  5. ok 누르면  생성

- 연결 클릭

  ![9](https://user-images.githubusercontent.com/48546542/73997209-3078b200-49a1-11ea-87e1-13c4c388ddcb.png)

  

- 구글 mysql 서버는 public ip 사용 함으로 해당 ip로 접근한다는 것을 알려 줘야함

- 새 네트워크로 접속하려는 ip주소 입력 → ipv4 형식의 ip 주소 넣어야함

  ![10](https://user-images.githubusercontent.com/48546542/73997213-31114880-49a1-11ea-83d6-7bea0ff4acce.png)

- 만약 배포했을 때 db서버와 WAS 서버가 다른경우 WAS서버가 배포되고 있는 컴퓨터의 ip를

  구글mysql서버에다가 등록해주어야 함

- aws host 입력하면 됨





# Other

**front vue.js package Spring boot에 배포**

- src/main/ 아래에 webapp 폴더 생성 후 dist파일 안 내용 전부 넣기 (아래 사진처럼)
  - **front + back 전부 war로 묶어서 배포함**

**Spring boot .war로 package**

**주의: .jar으로 배포하게되면 context-path 못 찾음**

​	[spring boot war 배포 및 실행](https://www.leafcats.com/225)

- 위 사이트 그대로 따라하면 됨
  1. 프로젝트 오른쪽 마우스 클릭
  2. run as → 4.maven build → package로 설정하고 빌드 시작
  3. target 폴더에 .jar .war 모두 원격으로 파일 보내야함

**ssh 터미널 종료후에도 Server 가동 하는 방법**

- nohup: 리눅스 명령어 중 백드라운드로 가동하는 방법

- &: 프로세스 pid 확인가능

  ```bash
  $ nohup java -jar '.war 파일명' & 
  $ [1] process PID 
  $ kill pid
  ```

  

  

  

