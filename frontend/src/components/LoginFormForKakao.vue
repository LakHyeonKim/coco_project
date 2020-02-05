<template>
	<div style="inline">
		<a id="kakao-login-btn" @click="kakaoLogin()">
			<img src="../assets/kakao_logo.png" class="logos" />
		</a>
	</div>
</template>

<script>
import Kakao from "./kakao.js";
import http from "../http-common";
import router from "../router";
import store from "../store";

export default {
	name: "LoginFormForKakao",
	data: function() {
		return {
			test: "test"
		};
	},
	computed: {
		init: function() {
			return store.state.init;
		},
		memberemail: function() {
			return store.state.memberemail;
		}
	},
	mounted() {
		if (this.init) {
			Kakao.init("8f50c0ec5069771884b4057173b83cde");
			store.dispatch("saveInit");
			console.log(this.init);
		}
	},
	methods: {
		kakaoLogin() {
			// console.log("kakao login");
			console.log("15165496846513135", this.test);
			// 로그인 창을 띄웁니다.
			Kakao.Auth.loginForm({
				success: function(authObj) {
					console.log("카카오 로그인 성공! access token 받아옴!");
					// console.log(JSON.stringify(authObj));
					// console.log(authObj);
					console.log("testestestestes", this.test);
					store.dispatch("saveMemberEmail", "asdfasdf");
					console.log("memberemail", store.state.memberemail);
					// console.log(authObj.access_token);
					// console.log(authObj.refresh_token);
					Kakao.API.request({
						url: "/v2/user/me",
						success: function(res) {
							// console.log("res success ", JSON.stringify(res));
							console.log("asjkdfhkajsdhfkajsdf", this.test);
							// console.log("res success ", res);
							// console.log(
							// 	"res success email",
							// 	res.kakao_account.email
							// );
							const email = res.kakao_account.email;
							http.post("/jwt/snsLogin/", email)
								.then(res => {
									console.log(res);
									if (res.status == "200") {
										// alert(res.data);
										console.log(res.data);
										const acessToken = res.data.acessToken;
										const refreshToken =
											res.data.refreshToken;
										store.dispatch(
											"saveTokens",
											acessToken,
											refreshToken
										);
										console.log(
											"vuex",
											store.state.acessToken,
											store.state.refreshToken
										);
										router.push("/newsfeed");
										console.log("SNSLOGIN then ", res);
									} else {
										store.dispatch(
											"saveMemberEmail",
											email
										);
										console.log(store.state.memberemail);
										router.push("/register");
									}
								})
								.catch(err => {
									console.log(err);
								});
						},
						fail: function(error) {
							console.log("error fail ", JSON.stringify(error));
						}
					});
				},
				fail: function(err) {
					alert(JSON.stringify(err));
				}
			});
		}
	}
};
</script>
