<template>
	<div id="banner">
		<!-- <ImgBanner imgSrc="bg_home.jpg" /> -->
		<div id="imgBannerBox">
			<img id="imgBanner" src="../assets/back.jpg" />
		</div>
		<div id="infoBox">
			<div id="today">today 30 · total 300</div>
			<div id="info_title">
				TITLE
			</div>
			<div id="info_desc">
				<img id="imgUser" src="../assets/user.png" />
				<div id="info_desc_bottom">
					<div id="nickname">
						닉네임
						<img
							v-if="isUser"
							src="../assets/icon/settings.png"
							id="settings"
							@click="showModal = true"
						/>
					</div>
					<div id="counting">팔로잉 101 · 팔로워 22 · 게시글 333</div>
				</div>
			</div>
		</div>
		<PWCheck v-if="showModal" @close="showModal = false"> </PWCheck>
	</div>
</template>

<script>
import store from "../store";
import PWCheck from "./PWCheck.vue";
export default {
	name: "banner",
	components: { PWCheck },
	store,
	data() {
		return {
			showModal: false,
			isUser: true
		};
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
