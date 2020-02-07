<template>
	<div class="mainform">
		<div id="blankBox"></div>
		<div id="mainBox">
			<div id="imgBoxDesk">
				<div id="macDiv">
					<img
						src="../assets/mac.png"
						alt="macbook_image"
						id="macImg"
					/>
				</div>
				<div id="macBackImg">
					<div class="macCarousel">
						<img src="../assets/bg_home.jpg" id="macBackImg" />
						<img src="../assets/bg_drawer.jpg" id="macBackImg" />
						<img src="../assets/bg_portfolio.jpg" id="macBackImg" />
						<img src="../assets/bg_post.jpg" id="macBackImg" />
					</div>
				</div>
			</div>
			<div id="imgBoxMobile">
				<div id="mobileDiv">
					<img
						src="../assets/phone.png"
						alt="iphone_image"
						id="mobileImg"
					/>
				</div>
				<div id="mobileBackImg">
					<div class="mobileCarousel">
						<img src="../assets/bg_drawer.jpg" id="mobileBackImg" />
						<img src="../assets/bg_post.jpg" id="mobileBackImg" />
						<img src="../assets/bg_home.jpg" id="mobileBackImg" />
						<img
							src="../assets/bg_portfolio.jpg"
							id="mobileBackImg"
						/>
					</div>
				</div>
			</div>

			<!-- <RegisterForm id="regiForm"></RegisterForm> -->
			<LoginForm id="loginForm"></LoginForm>
		</div>
		<MobileLoginForm id="mobileloginForm"></MobileLoginForm>
	</div>
</template>

<script>
import LoginForm from "../components/LoginForm";
import MobileLoginForm from "../components/MobileLoginForm";
import router from "../router";

export default {
	name: "home",
	components: {
		LoginForm,
		MobileLoginForm
	},
	methods: {
		checkLoggedIn() {
			if (this.$session.has("jwt")) {
				router.push("/newsfeed").catch(err => {
					err;
				});
			}
		}
	},
	mounted() {
		this.checkLoggedIn();
	}
};
</script>

<style>
.mainform {
	display: grid;
	justify-content: center;
	height: 100%;
}
#imgBoxDesk {
	display: inline-block;
	position: absolute;
	margin-top: 110px;
}
/* #regiForm {
	display: inline-block;
	margin-left: 100px;
} */
#loginForm {
	display: inline-block;
	position: relative;
	margin-left: 576px;
}
#imgBoxMobile {
	display: none;
}
#macDiv {
	width: 575px;
	position: absolute;
}
#macImg {
	position: relative;
	width: 575px;
	height: 316px;
}
#macBackImg {
	position: relative;
	overflow: hidden;
	width: 432px;
	height: 273px;
	transform: translate(17.5%, 3%);
}
.macCarousel {
	width: 500%;
	-webkit-animation: macCarousell 20s linear infinite;
	-moz-animation: macCarousell 20s linear infinite;
	-ms-animation: macCarousel 20s linear infinite;
	animation: macCarousel 20s linear infinite;
}
.macCarousel .macCarouselImg {
	display: inline-block;
}
.macCarousel img:not(:first-child) {
	float: left;
}
@-webkit-keyframes macCarousel {
	0%,
	23% {
		margin-left: -18%;
	}
	25%,
	48% {
		margin-left: -118%;
	}
	50%,
	73% {
		margin-left: -218%;
	}
	75%,
	98% {
		margin-left: -318%;
	}
	100% {
		margin-left: -18%;
	}
}
@-moz-keyframes macCarousel {
	0%,
	23% {
		margin-left: -18%;
	}
	25%,
	48% {
		margin-left: -118%;
	}
	50%,
	73% {
		margin-left: -218%;
	}
	75%,
	98% {
		margin-left: -318%;
	}
	100% {
		margin-left: -18%;
	}
}
@-ms-keyframes macCarousel {
	0%,
	23% {
		margin-left: -18%;
	}
	25%,
	48% {
		margin-left: -118%;
	}
	50%,
	73% {
		margin-left: -218%;
	}
	75%,
	98% {
		margin-left: -318%;
	}
	100% {
		margin-left: -18%;
	}
}
@keyframes macCarousel {
	0%,
	23% {
		margin-left: -18%;
	}
	25%,
	48% {
		margin-left: -118%;
	}
	50%,
	73% {
		margin-left: -218%;
	}
	75%,
	98% {
		margin-left: -318%;
	}
	100% {
		margin-left: -18%;
	}
}
#mobileloginForm {
	display: none;
}
@media screen and (max-width: 1200px) {
	#imgBoxDesk {
		display: none;
	}
	#loginForm {
		display: inline-block;
		position: relative;
		margin-left: 280px;
	}
	#imgBoxMobile {
		display: inline-block;
		position: absolute;
	}
	#mobileDiv {
		position: absolute;
	}
	#mobileImg {
		width: 270px;
		position: relative;
		z-index: 500;
	}
	#mobileBackImg {
		position: relative;
		overflow: hidden;
		width: 260px;
		height: 530px;
		margin-top: 4px;
		margin-left: 4px;
		border-radius: 12%;
	}
	.mobileCarousel {
		width: 500%;
		-webkit-animation: mobileCarousel 20s linear infinite;
		-moz-animation: mobileCarousel 20s linear infinite;
		-ms-animation: mobileCarousel 20s linear infinite;
		animation: mobileCarousel 20s linear infinite;
	}
	.mobileCarousel .mobileCarousel {
		display: inline-block;
	}
	.mobileCarousel img:not(:first-child) {
		float: left;
	}
	@-webkit-keyframes mobileCarousel {
		0%,
		23% {
			margin-left: -3%;
		}
		25%,
		48% {
			margin-left: -103%;
		}
		50%,
		73% {
			margin-left: -203%;
		}
		75%,
		98% {
			margin-left: -303%;
		}
		100% {
			margin-left: -3%;
		}
	}
	@-moz-keyframes mobileCarousel {
		0%,
		23% {
			margin-left: -3%;
		}
		25%,
		48% {
			margin-left: -103%;
		}
		50%,
		73% {
			margin-left: -203%;
		}
		75%,
		98% {
			margin-left: -303%;
		}
		100% {
			margin-left: -3%;
		}
	}
	@-ms-keyframes mobileCarousel {
		0%,
		23% {
			margin-left: -3%;
		}
		25%,
		48% {
			margin-left: -103%;
		}
		50%,
		73% {
			margin-left: -203%;
		}
		75%,
		98% {
			margin-left: -303%;
		}
		100% {
			margin-left: -3%;
		}
	}
	@keyframes mobileCarousel {
		0%,
		23% {
			margin-left: -3%;
		}
		25%,
		48% {
			margin-left: -103%;
		}
		50%,
		73% {
			margin-left: -203%;
		}
		75%,
		98% {
			margin-left: -303%;
		}
		100% {
			margin-left: -3%;
		}
	}
}
@media screen and (max-width: 875px) {
	#imgBoxMobile {
		display: none;
	}
	#loginForm {
		display: inline-block;
		justify-content: center;
		margin: 10px;
		padding: 5px;
	}
}
@media screen and (max-width: 450px) {
	#mainBox {
		display: none;
	}
	#blankBox {
		display: none;
	}
	#mobileloginForm {
		display: block;
	}
}
</style>
