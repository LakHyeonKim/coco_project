<template>
	<!-- 모바일 화면에서 width 줄어들 때 구성요소 잘리는 현상 -->
	<!-- 웹 화면에서 구성요소 링크 범위 조절 -->
	<div id="navbar">
		<ul>
			<li>
				<!-- <router-link to="/mypage"> -->
				<img
					@click="getMypage()"
					id="profile"
					src="../assets/user.png"
				/>
				<!-- </router-link> -->
			</li>
			<li class="nav_menu">
				<router-link to="/newsfeed">
					<img class="nav_menu_img" src="../assets/icon/news.png" />
				</router-link>
			</li>
			<li class="nav_menu">
				<router-link to="/newpage">
					<img class="nav_menu_img" src="../assets/icon/plus.png" />
				</router-link>
			</li>
			<li class="nav_menu">
				<router-link to="/search">
					<img class="nav_menu_img" src="../assets/icon/search.png" />
				</router-link>
			</li>
			<li class="nav_menu">
				<router-link to="#">
					<img class="nav_menu_img" src="../assets/icon/alarm.png" />
				</router-link>
			</li>
			<!-- <li class="nav_menu">
				<a @click.prevent="logout" href="#">Logout</a>
			</li> -->
		</ul>
	</div>
</template>

<script>
import router from "../router";

export default {
	name: "NavBar",
	data() {
		return {
			preUrl: ""
		};
	},
	methods: {
		logout() {
			this.$session.destroy();
			router.push("/");
		},
		getMypage() {
			console.log(this.$route.fullPath);
			let location = "/mypage/" + this.$session.get("id");

			if (this.$route.fullPath != location) {
				router.push(location).catch(err => {
					console.log(err);
				});
			} else {
				window.location.reload(true);
			}
		}
	}
};
</script>

<style>
#navbar {
	position: fixed;
	top: 0;
	height: 100%;
	z-index: 100;
	color: white;
	background-color: #7d4879;
	width: 60px;
	font-size: 12px;
	padding-top: 30px;
	box-shadow: 0.1px 0 5px 0.2px black;
}

#navbar > ul {
	list-style: none;
	padding: 0;
}

#profile {
	border-radius: 25px;
	background-color: white;
	margin-bottom: 20px;
	width: 40px;
	cursor: pointer;
}

#navbar > ul > li {
	text-align: center;
}

.nav_menu {
	height: 50px;
}

.nav_menu_img {
	width: 25px;
	opacity: 0.5;
	transition: 0.4s;
}

.nav_menu_img:hover {
	opacity: 1;
}

@media screen and (max-width: 600px) {
	#navbar {
		top: auto;
		bottom: 0;
		height: 17vw;
		width: 100%;
		padding: 0;
	}

	#profile {
		width: 35px;
		margin-bottom: 0;
	}

	#navbar > ul {
		text-align: center;
	}

	#navbar > ul > li {
		display: inline-block;
		margin: 4vw 5vw 0 5vw;
	}

	.nav_menu_img {
		width: 35px;
		padding: 3px;
	}
}
@media screen and (max-width: 330px) {
	#profile {
		cursor: pointer;
		width: 30px;
	}
	.nav_menu_img {
		width: 30px;
		padding: 3px;
	}
	#navbar > ul > li {
		display: inline-block;
		margin-top: 3.5vw;
	}
}
</style>
