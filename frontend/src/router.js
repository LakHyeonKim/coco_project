import Vue from "vue";
import VueRouter from "vue-router";
// import HomePage from "./views/HomePage.vue";
import PostPage from "./views/PostPage.vue";
import PortfolioPage from "./views/PortfolioPage.vue";
// import LoginPage from "./views/LoginPage.vue";
import Login from "./views/Login.vue";
import Register from "./views/Register";
import Start from "./views/Home.vue";
import NewPage from "./views/NewPage";
import NewsFeed from "./views/NewsFeed.vue";
import Home from "./views/Home.vue";
import MyPage from "./views/MyPage.vue";

Vue.use(VueRouter);

const routes = [
	{
		path: "/",
		name: "home",
		component: Home
	},
	{
		path: "/post",
		name: "post",
		component: PostPage
	},
	{
		path: "/portfolio",
		name: "portfolio",
		component: PortfolioPage
	},
	{
		path: "/login",
		name: "login",
		component: Login
	},
	{
		path: "/register",
		name: "register",
		component: Register
	},
	{
		path: "/start",
		name: "start",
		component: Start
	},
	{
		path: "/newsfeed",
		name: "newsfeed",
		component: NewsFeed
	},
	{
		path: "/myPage",
		name: "myPage",
		component: MyPage
	},
	{
		path: "/newpage",
		name: "newpage",
		component: NewPage
	},
	{
		path: "*",
		name: "notfound",
		component: Home
	}
];

const router = new VueRouter({
	// vue-router를 설치할 때 설정했던 History 모드
	mode: "history",
	base: process.env.BASE_URL,
	routes
});

export default router;
