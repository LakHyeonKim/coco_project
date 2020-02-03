<template>
	<div id="banner">
		<!-- <ImgBanner imgSrc="bg_home.jpg" /> -->
		<div id="imgBannerBox">
			<img id="imgBanner" src="../assets/back.jpg" />
		</div>
		<div id="infoBox">
			<div id="today">today 30 · total 300</div>
			<div id="info_title">
				<a href="#">TITLE</a>
			</div>
			<div id="info_desc">
				<img id="imgUser" src="../assets/user.png" />
				<div id="info_desc_bottom">
					<div id="nickname">닉네임</div>
					<div id="counting">팔로잉 101 · 팔로워 22 · 게시글 333</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
import store from "../store";
export default {
	name: "banner",
	store,
	data() {
		return {};
	}
};
</script>

<style>
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
}
@media screen and (max-width: 320px) {
	#nickname {
		font-size: 15px;
	}
	#counting {
		font-size: 11px;
	}
}
</style>
