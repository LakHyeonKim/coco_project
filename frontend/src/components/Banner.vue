<template>
	<div id="banner">
		<!-- <ImgBanner imgSrc="bg_home.jpg" /> -->
		<div id="imgBannerBox">
			<img id="imgBanner" src="../assets/back.jpg" />
		</div>
		<div id="infoBox">
			<div id="today">
				today {{ userInfo.mypage.todayVisitedCount }} · total
				{{ userInfo.mypage.totalVisitedCount }}
			</div>
			<div id="info_title">
				{{ userInfo.mypage.bannerText }}
			</div>
			<div id="info_desc">
				<img id="imgUser" :src="userInfo.member.imageUrl" />
				<div id="info_desc_bottom">
					<div id="nickname">
						{{ userInfo.member.nickname }}
						<img
							v-if="isUser"
							src="../assets/icon/settings.png"
							id="settings"
							@click="showModal = true"
						/>
					</div>
					<div id="counting">
						팔로잉 {{ userInfo.followingCount }} · 팔로워
						{{ userInfo.followerCount }} · 게시글
						{{ userInfo.totalPostCount }}
					</div>
				</div>
			</div>
		</div>
		<PWCheck v-if="showModal" @close="showModal = false"> </PWCheck>
	</div>
</template>

<script>
import http from "../http-common";
import store from "../store";
import PWCheck from "./PWCheck.vue";
export default {
	name: "banner",
	components: { PWCheck },
	store,
	data() {
		return {
			showModal: false,
			isUser: true,
			userInfo: ""
		};
	},
	mounted() {
		http.post("/api/findByMemberHomePageUserID/", this.$session.get("id"))
			.then(response => {
				this.userInfo = response.data;
				store.state.tags = this.userInfo.tags;
				console.log("Banner mounted()");
				console.log(response);
			})
			.catch(error => {
				console.log(error);
			});
	}
};
</script>

<style>
#settings {
	width: 17px;
	opacity: 0.6;
	cursor: pointer;
}
#imgBannerBox {
	height: 35vw;
	overflow: hidden;
}
#imgBanner {
	width: 100%;
}
#today {
	font-size: 15px;
	font-weight: 300;
}
#banner {
	width: 100%;
	margin: 0 auto;
	text-align: center;
	height: 35vw;
}
#infoBox {
	position: relative;
	display: inline-block;
	z-index: 1;
	top: -160px;
	/* background-color: rgba(0, 0, 0, 0.3); */
	width: 80%;
	height: 130px;
	padding: 10px;
	text-align: left;
}
#imgUser {
	float: left;
	width: 55px;
	border-radius: 50%;
	background-color: white;
}
#info_title {
	font-size: 25px;
	margin-bottom: 10px;
}
#info_title > a {
	font-weight: 500;
	color: black;
	text-decoration: none;
}
#info_desc_bottom {
	float: left;
	margin-left: 10px;
}
#nickname {
	font-size: 20px;
}
#counting {
	font-size: 15px;
	font-weight: 300;
}
@media screen and (max-width: 600px) {
	#settings {
		width: 15px;
	}
	#imgBannerBox {
		height: 50vw;
	}
	#imgUser {
		width: 50px;
	}
	#info_title {
		font-size: 20px;
	}
	#nickname {
		font-size: 17px;
	}
	#counting {
		font-size: 13px;
	}
	#info_desc_bottom {
		margin-top: 5px;
	}
	#today {
		font-size: 12px;
	}
	#infoBox {
		top: -140px;
	}
	#banner {
		height: 50vw;
	}
}
@media screen and (max-width: 320px) {
	#nickname {
		font-size: 15px;
	}
	#counting {
		font-size: 11px;
	}
	#banner {
		height: 50vw;
	}
}
</style>
