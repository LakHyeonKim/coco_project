<template>
	<div id="footer">
		<div id="footer_top">
			<div id="info">
				<table style="height:120px">
					<tr>
						<td class="info_img">
							<img src="../assets/logo/team.png" width="20px" />
						</td>
						<td>
							<!-- 소개 페이지 연결 -->
							<button @click="goUs()" class="link">SEESAW</button>
							(
							<a
								class="link"
								href="https://lab.ssafy.com/webmobile1-sub2/s02p12d105"
								>gitlab</a
							>)
						</td>
					</tr>
					<tr>
						<td class="info_img">
							<img src="../assets/logo/pin.png" width="20px" />
						</td>
						<td>
							경상북도 구미시 임수동 94-1 창의동 304호 SSAFY<br />
						</td>
					</tr>
					<tr>
						<td class="info_img">
							<img src="../assets/logo/phone.png" width="20px" />
						</td>
						<td>
							054) 여기는-싸피싸피
						</td>
					</tr>
				</table>
			</div>
			<div id="tech">
				<img class="logo" src="../assets/logo/vue.png" />
				<img class="logo" src="../assets/logo/mysql.png" />
				<img class="logo" src="../assets/logo/springboot.png" />
				<img class="logo" src="../assets/logo/mybatis.png" />
			</div>
		</div>
		<div style="clear: both;"></div>
		<div id="footer_bottom">© All right Reversed.</div>
		<!-- <div id="goAdmin" @click="goAdmin()"></div> -->
	</div>
</template>

<script>
import router from "../router";

export default {
	name: "MypageFooter",
	methods: {
		goUs() {
			router.push("/cc");
		},
		goAdmin() {
			router.push("/admin");
		}
	}
};
</script>

<style>
@import url("https://fonts.googleapis.com/css?family=Noto+Sans+KR:100,300,400,500,700,900&display=swap&subset=korean");
#footer {
	font-family: "Noto Sans KR", sans-serif;
	background-color: rgba(125, 72, 121, 0.85);
	/* height: 200px; */
	text-align: center;
	color: white;
	width: 100%;
	font-weight: 300;
	font-size: 12px;
	/* position: relative; */
}

#footer_top {
	margin: 0 auto;
	padding-top: 30px;
}

#info > table > tr > td {
	padding: 5px;
}

.link {
	color: white !important;
	text-decoration: none;
}

.link:hover {
	color: rgba(255, 255, 255, 0.5) !important;
}

#footer_bottom {
	/* position: absolute; */
	/* bottom: 0; */
	text-align: center;
	width: 100%;
	margin-top: 50px;
	padding-bottom: 20px;
}

#info {
	float: left;
	text-align: left;
	margin-left: 100px;
}

#tech {
	float: right;
	margin-top: 35px;
	margin-bottom: 35px;
	margin-right: 100px;
}

.logo {
	width: 50px;
	margin-left: 10px;
	margin-right: 10px;
}
#goAdmin {
	width: 80px;
	height: 80px;
	position: absolute;
	background-color: rgba(0, 0, 0, 0);
	right: 0;
	bottom: 0;
}
@media screen and (max-width: 900px) {
	#tech,
	#info {
		float: none;
		width: 90vw;
		margin: 0 5vw;
	}

	#tech {
		margin-top: 30px;
	}
}
</style>
