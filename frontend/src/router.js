import Vue from "vue";
import VueRouter from "vue-router";
// import HomePage from "./views/HomePage.vue";
// import PostPage from "./views/PostPage.vue";
// import PortfolioPage from "./views/PortfolioPage.vue";
// import LoginPage from "./views/LoginPage.vue";
// import Login from "./views/Login.vue";
import Register from "./views/Register";
// import Start from "./views/Home.vue";
import NewPage from "./views/NewPage";
import NewsFeed from "./views/NewsFeed.vue";
import Home from "./views/Home.vue";
import MyPage from "./views/MyPage.vue";
import SearchPage from "./views/SearchPage.vue";
import DetailPage from "./views/DetailPage.vue";
import FindPassword from "./views/FindPassword.vue";
import InfoModify from "./views/InfoModify";

Vue.use(VueRouter);

const routes = [
	{
		path: "/",
		name: "home",
		component: Home
	},
	// {
	// 	path: "/post",
	// 	name: "post",
	// 	component: PostPage
	// },
	// {
	// 	path: "/portfolio",
	// 	name: "portfolio",
	// 	component: PortfolioPage
	// },
	// {
	// 	path: "/login",
	// 	name: "login",
	// 	component: Login
	// },
	{
		path: "/register",
		name: "register",
		component: Register
	},
	// {
	// 	path: "/start",
	// 	name: "start",
	// 	component: Start
	// },
	{
		path: "/newsfeed",
		name: "newsfeed",
		component: NewsFeed
		// meta: {
		// 	authRequired: true
		// }
	},
	{
		path: "/mypage/:no",
		name: "mypage",
		component: MyPage,
		props: true
		// meta: {
		// 	authRequired: true
		// }
	},
	{
		path: "/newpage",
		name: "newpage",
		component: NewPage
		// meta: {
		// 	authRequired: true
		// }
	},
	{
		path: "/search",
		name: "search",
		component: SearchPage
		// meta: {
		// 	authRequired: true
		// }
	},
	{
		path: "/detail/:idPost",
		name: "detail",
		component: DetailPage,
		props: true
		// meta: {
		// 	authRequired: true
		// }
	},
	{
		path: "/findpwd",
		name: "findpwd",
		component: FindPassword
	},
	{
		path: "/infoModify",
		name: "infoModify",
		component: InfoModify
	},
	{
		path: "*",
		name: "notfound",
		component: NewsFeed
	}
];

const router = new VueRouter({
	// vue-router를 설치할 때 설정했던 History 모드
	mode: "history",
	base: process.env.BASE_URL,
	routes
});
router.beforeEach(function(to, from, next) {
	// to: 이동할 url에 해당하는 라우팅 객체
	if (
		to.matched.some(function(routeInfo) {
			return routeInfo.meta.authRequired;
		})
	) {
		// 이동할 페이지에 인증 정보가 필요하면 경고 창을 띄우고 페이지 전환은 하지 않음
		alert("Login Please!");
		// if (this.$session.has("acessToken")) {
		// 	next();
		// }
		next("/");
	} else {
		console.log("routing success : '" + to.path + "'");
		next(); // 페이지 전환
	}
});
export default router;
