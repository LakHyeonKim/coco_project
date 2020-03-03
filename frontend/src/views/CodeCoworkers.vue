<template>
	<div>
		<div class="about-section">
			<h1 style="font-size:60px">SEE-SAW</h1>
			<p style="font-size:25px">빛이 나는 CoCo</p>
		</div>

		<div class="row">
			<div class="column">
				<div class="card">
					<img
						src="../assets/logo_square.png"
						alt="logo"
						id="ourImg"
					/>
					<div class="container">
						<h2>SEA-SAW</h2>
						<p class="title">OUR TEAM</p>
						<p style="font-size: 14px">
							<br />
						</p>
						<p id="context">
							목표는 전국 1등, 50만 마일리지
						</p>
						<p>
							<a
								href="https://lab.ssafy.com/webmobile1-sub3/s02p13d105"
								target="#"
								class="button"
							>
								Contact
							</a>
						</p>
					</div>
				</div>
			</div>

			<div class="column">
				<div class="card">
					<img src="../assets/CC/JINHO.jpg" alt="진호" id="ourImg" />
					<div class="container">
						<h2>이진호</h2>
						<p class="title">CEO & PM</p>
						<p>a13975@naver.com</p>
						<p id="context1">
							서울시 강동구 천호동 강동병원 출생. 현재 여자친구와
							7년째 열애중에 있으며, 슬하에 2살
							강아지(수호:푸들)를 두고있다.
						</p>
						<p>
							<a
								href="https://github.com/Ogochim"
								target="#"
								class="button"
							>
								Contact
							</a>
						</p>
					</div>
				</div>
			</div>

			<div class="column">
				<div class="card">
					<img
						src="../assets/CC/LAKHYEON.jpg"
						alt="락현"
						id="ourImg"
					/>
					<div class="container">
						<h2>김락현</h2>
						<p class="title">CTO & DB</p>
						<p>krh715@hanmail.net</p>
						<p id="context2">
							ㄴr는 ㄱr끔 눈물을 흘린ㄷr
						</p>
						<p>
							<a
								href="https://github.com/LakHyeonKim"
								target="#"
								class="button"
							>
								Contact
							</a>
						</p>
					</div>
				</div>
			</div>

			<div class="column">
				<div class="card">
					<img
						src="../assets/CC/SEOHYUN.jpg"
						alt="서현"
						id="ourImg"
					/>
					<div class="container">
						<h2>백서현</h2>
						<p class="title">CIO & KILLER FUNCTION</p>
						<p>hyunny0463@naver.com</p>
						<p id="context3">
							코로나19 조심하세요
						</p>
						<p>
							<a
								href="https://github.com/hyunny0463"
								target="#"
								class="button"
							>
								Contact
							</a>
						</p>
					</div>
				</div>
			</div>

			<div class="column">
				<div class="card">
					<img src="../assets/CC/JIHUN.jpg" alt="지훈" id="ourImg" />
					<div class="container">
						<h2>이지훈</h2>
						<p class="title">CQO & POST LIST</p>
						<p>happiest@kakao.com</p>
						<p id="context4">
							내 꿈은 언더아머 단속반
						</p>
						<p>
							<a
								href="https://github.com/hopehappy92"
								target="#"
								class="button"
							>
								Contact
							</a>
						</p>
					</div>
				</div>
			</div>

			<div class="column">
				<div class="card">
					<img
						src="../assets/CC/YOUNGEUN.jpg"
						alt="영은"
						id="ourImg"
					/>
					<div class="container">
						<h2>황영은</h2>
						<p class="title">CDO & Header Art Director</p>
						<p>ktt7566@naver.com</p>
						<p id="context5">
							탈 싸피는 취업뿐
						</p>
						<p>
							<a
								href="https://github.com/euni95"
								target="#"
								class="button"
							>
								Contact
							</a>
						</p>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
export default {
	naem: "CodeCoworkers",
	data() {
		return {};
	},
	mounted() {
		document.querySelector("#navbar").setAttribute("style", "display:none");
	},
	destroyed() {
		document
			.querySelector("#navbar")
			.setAttribute("style", "display:fixed");
	}
};
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css?family=Black+And+White+Picture|Black+Han+Sans|Cute+Font|Dokdo|Hi+Melody|Song+Myung&display=swap");
#ourImg {
	width: 100%;
	/* height: 400px; */
	overflow: hidden;
}
#context {
	height: 100px;
	font-family: "Black Han Sans", sans-serif;
	font-size: 28px;
}
#context1 {
	height: 100px;
	font-family: "Cute Font", cursive;
	font-size: 30px;
}
#context2 {
	height: 100px;
	font-family: "Dokdo", cursive;
	font-size: 40px;
}
#context3 {
	height: 100px;
	font-family: "Hi Melody", cursive;
	font-size: 35px;
}
#context4 {
	height: 100px;
	font-family: "Black And White Picture", sans-serif;
	font-size: 35px;
}
#context5 {
	height: 100px;
	font-family: "Song Myung", serif;
	font-size: 45px;
}
body {
	font-family: Arial, Helvetica, sans-serif;
	margin: 0;
}

html {
	box-sizing: border-box;
}

*,
*:before,
*:after {
	box-sizing: inherit;
}

.column {
	float: left;
	width: 33.3%;
	margin-bottom: 16px;
	padding: 0 8px;
}

.card {
	box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);
	margin: 8px;
}

.about-section {
	padding: 50px;
	text-align: center;
	background-image: url("../assets/back.jpg");
	color: white;
	background-size: cover;
}

.container {
	padding: 0 16px;
}

.container::after,
.row::after {
	content: "";
	clear: both;
	display: table;
}

.title {
	color: grey;
	padding-left: 0px;
	padding-right: 0px;
	margin-bottom: 5px;
}

.button {
	border: none;
	outline: 0;
	display: inline-block;
	padding: 8px;
	color: white;
	background-color: rgba(160, 23, 98, 0.4);
	text-align: center;
	cursor: pointer;
	width: 100%;
	text-decoration: none;
}

.button:hover {
	background-color: rgba(160, 23, 98, 0.7);
}

@media screen and (max-width: 650px) {
	.column {
		width: 100%;
		display: block;
	}
}
</style>
