이 포스트에서는 Vue CLI을 이용한 개발 환경 구축을 목표로 한다.

Vue CLI는 기본적으로 vue만 포함한다. vue를 더욱 잘 이용하기 위해 vue router와 vuex, vue계의 부트스트랩과 같은 vuetify를 사용해본다.

만약, vue cli로 프로젝트 만들기를 하지 않았다면, 이전 글을 참고할 수 있다.

# Vue Router
[Vue Router 홈페이지](https://router.vuejs.org/kr/installation.html)

## Vue Router 란?
Vue.js의 공식 라우터.

Vue.js를 사용한 싱글 페이지 앱을 쉽게 만들 수 있도록 Vue.js의 코어와 긴밀히 통합.

<center>장고로 따지자면 urls.py의 역할을 하는 부분이라고 생각된다.</center>
## Vue Router설치

vue router의 설치는 비교적 간단하다.

```shell
vue add router
```

cmd에 입력한 후, y 한번만 입력하면 설치가 완료된다.


<center><img src="https://sweetlog.netlify.com/static/0c37871be65e1f7a645b84fbdbf190a7/65fcc/1562587550343.png" ></center>
Vuex

[Vuex 홈페이지](https://vuex.vuejs.org/kr/)

## Vuex 란?

Vue.js 애플리케이션에 대한 <b>상태 관리 패턴 + 라이브러리.</b> 애플리케이션의 모든 컴포넌트에 대한 중앙 집중식 저장소 역할을 하며, 예측 가능한 방식으로 상태를 변경할 수 있다.

<center><img src="https://vuex.vuejs.org/vuex.png" ></center>
## Vuex 설치

```bash
vue add vuex
```


<center><img src="https://sweetlog.netlify.com/static/0c37871be65e1f7a645b84fbdbf190a7/65fcc/1562587550343.png" ></center>
위의 두 플러그인을 설치한 후, `npm run serve` 명령어로 서버를 켜면 다음과 같은 화면이 나타난다.


<center><img src="https://sweetlog.netlify.com/static/e29c54ce4daaf41274fd1347ecb4088d/05c6f/1562588074034.png" ></center>