<template>
	<div>
		<div class="about-section">
			<h1>SEE-SAW</h1>
			<p>빛이 나는 Solo</p>
		</div>

		<div class="row">
			<div class="column">
				<div class="card">
					<img
						src="../assets/bg_home.jpg"
						alt="Jane"
						style="width:100%"
					/>
					<div class="container">
						<h2>이진호</h2>
						<p class="title">CEO & PM</p>
						<p>
							오늘부터 난난난나나 빛이 나는 솔로
						</p>
						<p>a13975@naver.com</p>
						<p><button class="button">Contact</button></p>
					</div>
				</div>
			</div>

			<div class="column">
				<div class="card">
					<img
						src="../assets/bg_post.jpg"
						alt="Mike"
						style="width:100%"
					/>
					<div class="container">
						<h2>김락현</h2>
						<p class="title">CTO & DB</p>
						<p>
							만남 설렘 감동 뒤엔 이별 눈물 후회
						</p>
						<p>mike@example.com</p>
						<p><button class="button">Contact</button></p>
					</div>
				</div>
			</div>

			<div class="column">
				<div class="card">
					<img
						src="../assets/bg_portfolio.jpg"
						alt="John"
						style="width:100%"
					/>
					<div class="container">
						<h2>백서현</h2>
						<p class="title">CIO & KILLER FUNCTION</p>
						<p>
							넌 뭔데 자꾸 생각나 자존심 다 어쩌고 저쩌고
						</p>
						<p>hyunny0463@naver.com</p>
						<p><button class="button">Contact</button></p>
					</div>
				</div>
			</div>

			<div class="column">
				<div class="card">
					<img
						src="../assets/bg_portfolio.jpg"
						alt="John"
						style="width:100%"
					/>
					<div class="container">
						<h2>이지훈</h2>
						<p class="title">CQO & POST LIST</p>
						<p>
							거짓말 처럼 키스해줘 내가 너에게 마지막 사라인것
							처럼
						</p>
						<p>happiest@kakao.com</p>
						<p><button class="button">Contact</button></p>
					</div>
				</div>
			</div>

			<div class="column">
				<div class="card">
					<img
						src="../assets/bg_portfolio.jpg"
						alt="John"
						style="width:100%"
					/>
					<div class="container">
						<h2>황영은</h2>
						<p class="title">CDO & Header Art Director</p>
						<p>
							내가 너에게 마지막 사랑인것 처럼 마지막처럼 마마마
							마지막처럼
						</p>
						<p>ktt7566@naver.com</p>
						<p><button class="button">Contact</button></p>
					</div>
				</div>
			</div>

			<div class="column">
				<div class="card">
					<img
						src="../assets/bg_portfolio.jpg"
						alt="John"
						style="width:100%"
					/>
					<div class="container">
						<h2>SEA-SAW</h2>
						<p class="title">OUR TEAM</p>
						<p>
							저와다르게 출장은 안가시기 때문에, 누가 우와야
							ㅎㅎㅎ, 우와스럽게
						</p>
						<p>git URL</p>
						<p><button class="button">Contact</button></p>
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

<style>
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
	background-color: #474e5d;
	color: white;
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
}

.button {
	border: none;
	outline: 0;
	display: inline-block;
	padding: 8px;
	color: white;
	background-color: #000;
	text-align: center;
	cursor: pointer;
	width: 100%;
}

.button:hover {
	background-color: #555;
}

@media screen and (max-width: 650px) {
	.column {
		width: 100%;
		display: block;
	}
}
</style>
