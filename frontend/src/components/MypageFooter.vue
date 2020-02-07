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
							<a class="link" href="#footer">SEESAW</a> (
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
							(주)멀티캠퍼스 서울특별시 강남구 언주로 508
							10-17층<br />(역삼동, 서울상록빌딩)
						</td>
					</tr>
					<tr>
						<td class="info_img">
							<img src="../assets/logo/phone.png" width="20px" />
						</td>
						<td>010-2228-6223</td>
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
		<div id="footer_bottom">© All right Reversed.</div>
	</div>
</template>

<script>
export default { name: "MypageFooter" };
</script>

<style>
@import url("https://fonts.googleapis.com/css?family=Noto+Sans+KR:100,300,400,500,700,900&display=swap&subset=korean");
#footer {
	font-family: "Noto Sans KR", sans-serif;
	background-color: rgba(125, 72, 121, 0.85);
	height: 300px;
	text-align: center;
	color: white;
	width: 100%;
	font-weight: 300;
	font-size: 12px;
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
	position: absolute;
	bottom: 0;
	text-align: center;
	width: 100%;
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

@media screen and (max-width: 900px) {
	#tech,
	#info {
		float: none;
		width: 400px;
		margin: 0 auto;
	}

	#tech {
		margin-top: 30px;
	}
}
</style>
