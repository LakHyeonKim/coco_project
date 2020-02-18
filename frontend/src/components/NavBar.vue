<template>
	<!-- 모바일 화면에서 width 줄어들 때 구성요소 잘리는 현상 -->
	<!-- 웹 화면에서 구성요소 링크 범위 조절 -->
	<div id="navbar">
		<ul>
			<!-- <li>
				<img
					@click="getMypage()"
					id="profile"
					:src="
						$session.get('imageUrl') == undefined
							? '../img/icons/user.png'
							: $session.get('imageUrl')
					"
				/>
			</li> -->
			<li id="profile_menu">
				<img
					@click="isBlock = !isBlock"
					id="profile"
					:src="
						$session.get('imageUrl') == undefined
							? '../img/icons/user.png'
							: $session.get('imageUrl')
					"
				/>
				<ul
					id="profile_sub"
					:style="isBlock ? 'display: block' : 'display: none'"
				>
					<li id="mypage_li" @click="getMypage()">마이페이지</li>
					<li @click="logout()">로그아웃</li>
				</ul>
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
				<router-link to="/alarm">
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
			preUrl: "",
			isBlock: false
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
	margin-bottom: 20px;
	width: 40px;
	height: 40px;
	cursor: pointer;
}

#navbar li ul {
	list-style: none;
	display: none;
	background-color: rgb(243, 230, 250);
	position: fixed;
	color: rgba(0, 0, 0, 0.7);
	margin: 0px;
	padding: 0px;
	z-index: 200;
	left: 55px;
	top: 30px;
	/* border-radius: 10px; */
	font-size: 15px;
	box-shadow: 0.2px 0.2px 3px 0.5px rgba(0, 0, 0, 0.3);
}
#navbar li ul li {
	padding: 10px;
	cursor: pointer;
}
#navbar li ul li:hover {
	background-color: rgb(168, 136, 185);
	color: white;
}
#mypage_li {
	border-bottom: 1px solid silver;
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
		height: 35px;
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
		height: 30px;
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
