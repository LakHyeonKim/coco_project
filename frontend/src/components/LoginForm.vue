<template>
	<div class="container boxform">
		<div id="innerbox">
			<div class="login-div">
				<div
					id="loading"
					:style="loadingTop ? loadingStyleOn : loadingStyleOff"
					v-if="loadingTop"
				>
					<div class="loader"></div>
				</div>
				<form v-else class="loginform" @submit.prevent="login">
					<img
						src="../assets/CC_Logo.png"
						width="200px"
						style="margin-top:40px; margin-bottom:20px;"
					/>
					<div>
						<input
							type="text"
							class="inputform"
							id="id"
							placeholder="  아이디"
							style="margin-bottom:5px;"
							v-model="credentials.id"
						/>
					</div>
					<div>
						<input
							type="password"
							class="inputform"
							id="password"
							placeholder="  비밀번호"
							style="margin-bottom:10px;"
							v-model="credentials.password"
						/>
					</div>
					<button type="submit" class="loginbutton">
						<p id="logintext">로그인</p>
					</button>
				</form>
				<div id="divideLine">
					<hr style="width:30%; display:inline-block;" />
					<p style="display:inline;">또는</p>
					<hr style="width:30%; display:inline-block;" />
				</div>
			</div>
			<div id="logos">
				<div style="display:inline; margin-right:5px;">
					<button v-on:click="loginWithGoogle">
						<img src="../assets/google_logo.png" class="logos" />
					</button>
				</div>
				<!-- <div style="display:inline-block">
					<LoginFormForKakao></LoginFormForKakao>
				</div>-->
				<a @click.prevent="getCode" target="_blank">
					<img src="../assets/kakao_logo.png" class="logos" />
				</a>
			</div>
			<div v-if="errors.length" id="loginError" style="display:inline;">
				<div v-for="(error, idx) in errors" :key="idx">{{ error }}</div>
			</div>
			<div id="forgotpwd">
				<findPassword>
					<div slot="click">
						<a id="findpwd" slot="click">비밀번호를 잊으셨나요?</a>
					</div>
				</findPassword>
			</div>
		</div>
		<div id="downBox">
			<div id="regiform">
				계정이 없으신가요?
				<router-link to="/register">
					<button style="color:blue;">가입하기</button>
				</router-link>
			</div>
			<div></div>
		</div>
	</div>
</template>

<script>
import FindPassword from "../views/FindPassword";
import http from "../http-common";
import router from "../router";
import FirebaseService from "@/services/FirebaseService";
// import axios from "axios";

export default {
	name: "LoginForm",
	components: {
		FindPassword
	},
	data() {
		return {
			credentials: {
				id: "",
				password: ""
			},
			errors: [],
			loadingTop: false,
			loadingStyleOn: {
				display: "grid"
			},
			loadingStyleOff: {
				display: "none"
			}
		};
	},
	methods: {
		login() {
			if (this.checkForm()) {
				this.loadingTop = true;
				console.log("login form ", this.credentials);
				http.post("/jwt/login/", this.credentials)
					.then(res => {
						console.log("login res", res);
						if (res.status != "204") {
							this.$session.start();
							this.$session.set(
								"accessToken",
								res.data.accessToken
							);
							this.$session.set(
								"refreshToken",
								res.data.refreshToken
							);
							this.$store.state.token = res.data.accessToken;

							// decode
							let decode = this.$store.getters.decode;
							console.log("decode :: ");
							console.log(decode);
							this.$session.set("id", Number(decode.idmember));
							this.$session.set("nickName", decode.nickname);
							this.$session.set("rankId", decode.rankId);
							this.$session.set("isDelete", decode.isDelete);
							this.$session.set("imageUrl", decode.imageUrl);
							this.$session.set("grade", decode.grade);
							this.$session.set("email", decode.id);
							this.$session.set("isManager", decode.isManager);
							console.log(
								"asdfasdf",
								typeof this.$session.get("isManager")
							);
							if (this.$session.get("isManager") == 0) {
								this.$session.clear();
								alert("이메일 인증을 해주세요");
								this.loadingTop = false;
								document.location.reload();
							} else if (this.$session.get("isManager") == 7) {
								// alert("관리자");
								this.loadingTop = false;
								router.push("/admin");
								document.location.reload();
							} else {
								// alert("잠깐만");
								this.loadingTop = false;
								router.push("/newsfeed");
							}
							this.loadingTop = false;
							// router.push("/newsfeed");
							console.log("LOGIN then ", res);
						} else {
							router.push("/").catch(err => {
								err;
							});
							alert("아이디와 비밀번호를 확인해 주십시오.");
							this.loadingTop = false;
						}
					})
					.catch(err => {
						this.loadingTop = false;
						// this.$store.dispatch("endLoading");
						console.log("LOGIN err ", err);
					});
			} else {
				console.log("LOGIN ", "fail");
				this.loadingTop = false;
			}
		},
		checkForm() {
			this.errors = [];
			// 사용자가 ID를 입력하지 않은 경우
			if (!this.credentials.id) {
				this.errors.push("아이디를 확인해주세요");
			}
			// password가 8글자 미만인 경우
			if (this.credentials.password.length < 1) {
				this.errors.push("비밀번호를 재확인 해주세요");
			}
			// 둘다 잘 입력한 경우
			if (this.errors.length === 0) {
				return true;
			}
		},
		findPwd() {
			router.push("/findpwd");
		},
		async loginWithGoogle() {
			const result = await FirebaseService.loginWithGoogle();
			console.log(result);
			const useremail = result.user.email;
			console.log(useremail);
			http.post("/jwt/snsLogin/", useremail)
				.then(res => {
					this.loadingTop = true;
					console.log("google login res ", res);
					if (res.data.accessToken) {
						console.log("google login if ", res);
						this.$session.set("accessToken", res.data.accessToken);
						this.$session.set(
							"refreshToken",
							res.data.refreshToken
						);
						this.$store.state.token = res.data.accessToken;
						let decode = this.$store.getters.decode;
						console.log("decode :: ");
						console.log(decode);
						this.$session.set("id", Number(decode.idmember));
						this.$session.set("nickName", decode.nickname);
						this.$session.set("rankId", decode.rankId);
						this.$session.set("isDelete", decode.isDelete);
						this.$session.set("imageUrl", decode.imageUrl);
						this.$session.set("grade", decode.grade);
						this.$session.set("email", decode.id);
						this.$session.set("isManager", decode.isManager);
						console.log(
							"asdfasdf",
							typeof this.$session.get("isManager")
						);
						if (this.$session.get("isManager") == 0) {
							this.$session.clear();
							alert("이메일 인증을 해주세요");
							this.loadingTop = false;
							document.location.reload();
						} else if (this.$session.get("isManager") == 7) {
							// alert("관리자");
							this.loadingTop = false;
							router.push("/admin");
							document.location.reload();
						} else {
							// alert("잠깐만");
							this.loadingTop = false;
							router.push("/newsfeed");
						}
						this.loadingTop = false;
						// router.push("/newsfeed");
						console.log("LOGIN then ", res);
					} else {
						console.log("google res else", res);
						this.$session.set("useremail", useremail);
						router.push("/register");
					}
				})
				.catch(err => {
					console.log("google login err ", err);
				});
			// this.$session.start();
			// this.$session.set("accessToken", result.credential.accessToken);
			// router.push("/newsfeed");
		},
		getCode() {
			window.location.href =
				"https://kauth.kakao.com/oauth/authorize?client_id=716ea071847daf5fdddd8ecac5cd2796&redirect_uri=http://52.79.118.55:8888&response_type=code";
		}
	},
	mounted() {
		const code = document.location.href.split("code");
		if (code[1]) {
			const sendCode = code[1].slice(1);
			//alert(sendCode);
			http.get("/jwt/kakaologin", { params: { code: sendCode } })
				.then(res => {
					this.loadingTop = true;
					console.log("kakao res", res.data);
					console.log("kakao res", res.data.accessToken);
					if (res.data.accessToken) {
						this.$session.start();
						this.$session.set("accessToken", res.data.accessToken);
						this.$session.set(
							"refreshToken",
							res.data.refreshToken
						);
						this.$store.state.token = res.data.accessToken;
						let decode = this.$store.getters.decode;
						console.log("decode :: ");
						console.log(decode);
						this.$session.set("id", Number(decode.idmember));
						this.$session.set("nickName", decode.nickname);
						this.$session.set("rankId", decode.rankId);
						this.$session.set("isDelete", decode.isDelete);
						this.$session.set("imageUrl", decode.imageUrl);
						this.$session.set("grade", decode.grade);
						this.$session.set("email", decode.id);
						this.$session.set("isManager", decode.isManager);
						console.log(
							"asdfasdf",
							typeof this.$session.get("isManager")
						);
						if (this.$session.get("isManager") == 0) {
							this.$session.clear();
							alert("이메일 인증을 해주세요");
							this.loadingTop = false;
							document.location.reload();
						} else if (this.$session.get("isManager") == 7) {
							// alert("관리자");
							this.loadingTop = false;
							router.push("/admin");
							document.location.reload();
						} else {
							this.loadingTop = false;
							router.push("/newsfeed");
						}
						this.loadingTop = false;
						// router.push("/newsfeed");
						console.log("LOGIN then ", res);
					} else {
						console.log("kakao res else", res);
						this.$session.set("useremail", res.data.id);
						router.push("/register");
					}
				})
				.catch(err => {
					console.log("kakao err", err);
				});
		}
	}
};
</script>

<style scoped>
#loading {
	display: none;
	width: 100%;
	margin: 20px auto 20px auto;
	/* display: grid; */
	justify-content: center;
}
.loader {
	/* margin: 20px auto 20px auto; */
	border: 6px solid #f3f3f3; /* Light grey */
	border-top: 6px solid #3498db; /* Blue */
	border-radius: 50%;
	width: 60px;
	height: 60px;
	animation: spin 2s linear infinite;
}
.boxform {
	min-height: 450px;
	width: 350px;
}
#innerbox {
	width: 100%;
	background-color: white;
	border: 1px solid rgba(187, 187, 187, 0.5);
	margin-bottom: 10px;
	align-content: center;
	justify-content: center;
}
.logos {
	height: 40px;
	width: 40px;
	background-color: #bbb;
	border-radius: 50%;
	margin-bottom: 10px;
}
#logos {
	display: block;
	text-align: center;
}
.inputform {
	border: 1px black;
	border-radius: 5px;
	width: 80%;
	align-content: center;
	justify-content: center;
	background-color: #e9cde7;
	font-size: 20px;
	height: 35px;
}
input::placeholder {
	color: white;
	font-size: 18px;
}
.loginform {
	display: block;
	text-align: center;
	align-content: center;
	justify-content: center;
	font-size: 27px;
}
.loginbutton {
	border: 1px black;
	border-radius: 5px;
	width: 80%;
	align-content: center;
	justify-content: center;
	font-size: 20px;
	background-color: #7d4879;
	color: white;
}
#logintext {
	font-size: 15px;
	margin-top: 0.5em;
	margin-bottom: 0.5em;
}
#divideLine {
	text-align: center;
	margin-top: 15px;
	margin-bottom: 15px;
}
#forgotpwd {
	font-size: 11px;
	text-align: center;
	margin-bottom: 20px;
}
#findpwd {
	text-decoration: none;
	color: black;
	font-size: 12px;
}
#downBox {
	display: grid;
	width: 100%;
	background-color: white;
	border: 1px solid rgba(187, 187, 187, 0.5);
}
#regiform {
	text-align: center;
	align-content: center;
	margin-top: 10px;
	margin-bottom: 10px;
}
#loginError {
	color: red;
	width: auto;
	text-align: center;
	margin-bottom: 10px;
}
@media screen and (max-width: 450px) {
	.boxform {
		min-height: 450px;
		width: 350px;
	}
	#innerbox {
		width: 100%;
		background-color: rgba(0, 0, 0, 0);
		border: none;
		margin-bottom: 10px;
		align-content: center;
		justify-content: center;
	}
	.logos {
		height: 40px;
		width: 40px;
		background-color: none;
		border-radius: 50%;
		margin-bottom: 10px;
	}
	#logos {
		display: block;
		text-align: center;
	}
	.inputform {
		border: 1px black;
		border-radius: 5px;
		width: 80%;
		align-content: center;
		justify-content: center;
		background-color: #e9cde7;
		font-size: 20px;
		height: 35px;
	}
	input::placeholder {
		color: white;
		font-size: 18px;
	}
	.loginform {
		display: block;
		text-align: center;
		align-content: center;
		justify-content: center;
		font-size: 27px;
	}
	.loginbutton {
		border: 1px black;
		border-radius: 5px;
		width: 80%;
		align-content: center;
		justify-content: center;
		font-size: 20px;
		background-color: #7d4879;
		color: white;
	}
	#logintext {
		font-size: 15px;
		margin-top: 0.5em;
		margin-bottom: 0.5em;
	}
	#divideLine {
		text-align: center;
		margin-top: 15px;
		margin-bottom: 15px;
	}
	#forgotpwd {
		font-size: 11px;
		text-align: center;
	}
	#downBox {
		display: grid;
		width: 100%;
		background-color: rgba(0, 0, 0, 0);
		border: none;
	}
	#regiform {
		text-align: center;
		align-content: center;
		margin-top: 10px;
		margin-bottom: 10px;
	}
	#loginError {
		color: red;
		width: auto;
		text-align: center;
		margin-bottom: 10px;
	}
}
</style>
