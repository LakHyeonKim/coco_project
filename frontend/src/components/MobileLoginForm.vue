<template>
	<div class="container boxform">
		<div class="login-div">
			<div v-if="loading" class="spinner-border" role="status">
				<span class="sr-only">Loading...</span>
			</div>
			<form v-else class="loginform" @submit.prevent="login">
				<img
					src="../assets/logoo.png"
					width="200px"
					style="margin-top:20px; margin-bottom:20px;"
				/>
				<div>
					<input
						type="text"
						class="inputform"
						id="id"
						placeholder="  아이디"
						style="margin-bottom:5px;"
						v-model="credentials.username"
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
					<p id="logintext">
						로그인
					</p>
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
			<div style="display:inline;">
				<button v-on:click="loginWithGoogle">
					<img src="../assets/kakao_logo.png" class="logos" />
				</button>
			</div>
		</div>
		<div v-if="errors.length" id="loginError">
			<div v-for="(error, idx) in errors" :key="idx">
				{{ error }}
			</div>
		</div>
		<div id="forgotpwd">
			<button>
				<p>비밀번호를 잊으셨나요?</p>
			</button>
		</div>
		<div id="regiform">
			계정이 없으신가요?
			<router-link to="/register">
				<button style="color:blue;">
					가입하기
				</button>
			</router-link>
		</div>
		<div></div>
	</div>
</template>

<script>
import http from "../http-common";
import router from "../router";
import FirebaseService from "@/services/FirebaseService";

export default {
	name: "MobileLoginForm",
	data() {
		return {
			credentials: {
				id: "",
				password: ""
			},
			loading: false,
			errors: []
		};
	},
	// computed: {
	// 	loading: function() {
	// 		return this.$store.state.loading;
	// 	}
	// },
	methods: {
		login() {
			if (this.checkForm()) {
				this.loading = true;
				// this.$store.dispatch("startLoading");
				http.post("/jwt/login/", this.credentials)
					.then(res => {
						console.log("before", res);
						if (res.status != "204") {
							// alert(res.data);
							this.$session.start();
							this.$session.set(
								"accessToken",
								res.data.accessToken
							);
							this.$session.set(
								"refreshToken",
								res.data.refreshToken
							);
							this.loading = false;
							router.push("/newsfeed");
							console.log("LOGIN then ", res);
						} else {
							router.push("/").catch(err => {
								err;
							});
							alert("아이디와 비밀번호를 확인해 주십시오.");
							this.loading = false;
						}
					})
					.catch(err => {
						this.loading = false;
						// this.$store.dispatch("endLoading");
						console.log("LOGIN err ", err);
					});
			} else {
				console.log("LOGIN ", "fail");
			}
		},
		checkForm() {
			this.errors = [];
			// 사용자가 ID를 입력하지 않은 경우
			if (!this.credentials.username) {
				this.errors.push("아이디를 입력해주세요");
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
		async loginWithGoogle() {
			const result = await FirebaseService.loginWithGoogle();
			this.$store.state.token = result.credential.accessToken;
			this.$store.state.user = result.user;
		}
	}
	// mounted() {
	// 	console.log(this.$store.state);
	// }
};
</script>

<style>
.boxform {
	min-height: 450px;
	width: 350px;
}
#innerbox {
	width: 100%;
	background-color: white;
	border: 1px solid gray;
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
}
#downBox {
	display: grid;
	width: 100%;
	background-color: white;
	border: 1px solid gray;
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
</style>
