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
import AlarmPage from "./views/AlarmPage";
import CodeCoworkers from "./views/CodeCoworkers";
import Admin from "./views/Admin";
import AdminPost from "./components/AdminPost";
import AdminMember from "./components/AdminMember";
import AdminTag from "./components/AdminTag";
import AdminComment from "./components/AdminComment";
import AdminFollow from "./components/AdminFollow";
import EmailMessage from "./views/EmailMessage";

Vue.use(VueRouter);

const routes = [
	{
		path: "/",
		name: "home",
		component: Home
	},

	{
		path: "/register",
		name: "register",
		component: Register
	},

	{
		path: "/newsfeed",
		name: "newsfeed",
		component: NewsFeed

	},
	{
		path: "/mypage/:no",
		name: "mypage",
		component: MyPage,
		props: true
	
	},
	{
		path: "/newpage",
		name: "newpage",
		component: NewPage
		
	},
	{
		path: "/search",
		name: "search",
		component: SearchPage
		
	},
	{
		path: "/detail/:idPost",
		name: "detail",
		component: DetailPage,
		props: true
		
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
		path: "/alarm",
		name: "alarm",
		component: AlarmPage
	},
	{
		path: "/cc",
		name: "codecoworkers",
		component: CodeCoworkers
	},
	{
		path: "/admin",
		name: "admin",
		component: Admin
	},
	{
		path: "/admin/post/:no",
		name: "adminpost",
		component: AdminPost,
		props: true
	},
	{
		path: "/admin/member/:no",
		name: "adminmember",
		component: AdminMember,
		props: true
	},
	{
		path: "/admin/tag/:no",
		name: "admintag",
		component: AdminTag,
		props: true
	},
	{
		path: "/admin/comment/:no",
		name: "admincomment",
		component: AdminComment,
		props: true
	},
	{
		path: "/admin/follow/:no",
		name: "adminfollow",
		component: AdminFollow,
		props: true
	},
	{
		path: "*",
		name: "notfound",
		component: NewsFeed
	},
	{
		path: "/email/:key",
		name: "emailMessage",
		component: EmailMessage,
		props: true
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
