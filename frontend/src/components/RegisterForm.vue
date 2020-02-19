<template>
	<div id="backBox">
		<div id="nameBox">
			<img id="logo" src="../assets/CC_Logo.png" alt="Logo" />
			<p id="codeCoworkers">
				CODE COWORKERS <br />
				기록, 공유 그리고 성장
			</p>
		</div>
		<hr style="width:100%; display:inline-block;" />
		<div
			id="loading"
			:style="loadingTop ? loadingStyleOn : loadingStyleOff"
			v-if="loadingTop"
		>
			<div class="loader"></div>
		</div>
		<validation-observer v-else ref="form">
			<form
				@submit.prevent="register"
				id="formData"
				enctype="multipart/form-data"
			>
				<div id="imgBox">
					<validation-provider name="프로필 이미지 ">
						<img-inputer
							name="file"
							v-model="signUpMember.file"
							size="middle"
							placeholder="Drop file here or click"
							bottomText="Drop file here or click"
							exceedSizeText="사진의 크기가 초과하였습니다"
							:maxSize="10240"
							ref="profile"
							@onExceed="exceedHandler"
						/>
					</validation-provider>
				</div>
				<!-- <div>
					<v-gravatar :email="signUpMember.id" alt="gravatar" :size="50" />
				</div> -->
				<div id="idBox">
					<validation-provider
						name="아이디 "
						rules="required|email"
						v-slot="{ errors }"
					>
						<v-text-field
							name="id"
							v-model="signUpMember.id"
							label="아이디"
							:error-messages="errors[0] ? errors[0] : []"
							color="gray"
							@keypress="isIdModi()"
							:disabled="disabled"
						></v-text-field>
					</validation-provider>
				</div>
				<div id="idCheck" v-on:click="idCheck">
					<p id="checkText">중복확인</p>
				</div>
				<div id="idBox">
					<validation-provider
						name="닉네임 "
						rules="required|max:10"
						v-slot="{ errors }"
					>
						<v-text-field
							name="nickname"
							v-model="signUpMember.nickname"
							:counter="10"
							label="닉네임"
							:error-messages="errors[0] ? errors[0] : []"
							color="gray"
							@keypress="isNickModi()"
						></v-text-field>
					</validation-provider>
				</div>
				<div id="idCheck" v-on:click="nicknameCheck">
					<p id="checkText">중복확인</p>
				</div>
				<validation-provider
					name="비밀번호 "
					vid="confirmation"
					rules="required|min:8|max:16|password"
					v-slot="{ errors }"
				>
					<v-text-field
						name="password"
						v-model="signUpMember.password"
						label="비밀번호"
						:counter="16"
						:type="pwd1 ? 'text' : 'password'"
						:error-messages="errors[0] ? errors[0] : []"
						:append-icon="pwd1 ? 'mdi-eye' : 'mdi-eye-off'"
						@click:append="pwd1 = !pwd1"
						color="gray"
					></v-text-field>
				</validation-provider>
				<validation-provider
					name="비밀번호 확인 "
					rules="required|confirmed:confirmation"
					v-slot="{ errors }"
				>
					<v-text-field
						v-model="passwordConfirm"
						label="비밀번호 확인"
						:type="pwd2 ? 'text' : 'password'"
						:error-messages="errors[0] ? errors[0] : []"
						:append-icon="pwd2 ? 'mdi-eye' : 'mdi-eye-off'"
						@click:append="pwd2 = !pwd2"
						color="gray"
					></v-text-field>
				</validation-provider>
				<!-- <v-text-field
					v-model="signUpMember.gitUrl"
					label="Git url(선택)"
				></v-text-field>
				<v-text-field
					v-model="signUpMember.kakaoUrl"
					label="Kakao url(선택)"
				></v-text-field>
				<v-text-field
					v-model="signUpMember.instagramUrl"
					label="Instagram url(선택)"
				></v-text-field> -->
				<button id="submitButton">회원가입</button>
			</form>
		</validation-observer>
	</div>
</template>

<script>
import http from "../http-common";
import router from "../router";
import {
	ValidationProvider,
	ValidationObserver
	// extend,
	// validate
} from "vee-validate";

export default {
	name: "RegisterForm",
	components: {
		ValidationProvider,
		ValidationObserver
	},
	data: () => ({
		signUpMember: {
			file: "",
			id: "",
			nickname: "",
			password: "",
			email: "",
			gitUrl: "",
			kakaoUrl: "",
			instagramUrl: ""
		},
		pwd1: false,
		pwd2: false,
		passwordConfirm: "",
		duplicate_id: [],
		duplicate_nickname: [],
		idcheck: false,
		nicknamecheck: false,
		loadingTop: false,
		loadingStyleOn: {
			display: "grid"
		},
		loadingStyleOff: {
			display: "none"
		},
		disabled: false
	}),

	methods: {
		isIdModi() {
			this.idcheck = false;
		},
		isNickModi() {
			this.nicknamecheck = false;
		},
		register() {
			let formData = new FormData(document.getElementById("formData"));
			formData.set("file", this.$refs.profile.file);

			if (this.onSubmit() && this.idcheck && this.nicknamecheck) {
				this.loadingTop = true;
				// this.$store.dispatch("startLoading");
				console.log("REGISTER beforeaxios ", formData);
				http.post("/jwt/signUp/", formData)
					.then(res => {
						console.log("REGISTER then ", res);
						// this.$store.dispatch("endLoading");
						alert("회원가입이 성공적으로 완료되었습니다.");
						const credentials = {
							id: this.signUpMember.id,
							password: this.signUpMember.password
						};
						console.log("for login credentials ", credentials);
						http.post("/jwt/login/", credentials)
							.then(res => {
								console.log("login ", res);
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
									this.$store.state.token =
										res.data.accessToken;

									// decode
									let decode = this.$store.getters.decode;
									console.log("decode :: ");
									console.log(decode);
									this.$session.set(
										"id",
										Number(decode.idmember)
									);
									this.$session.set(
										"nickName",
										decode.nickname
									);
									this.$session.set("rankId", decode.rankId);
									this.$session.set(
										"isDelete",
										decode.isDelete
									);
									this.$session.set(
										"imageUrl",
										decode.imageUrl
									);
									this.$session.set("grade", decode.grade);
									this.$session.set("email", decode.id);
									this.$session.set(
										"isManager",
										decode.isManager
									);
									console.log(
										"asdfasdf",
										typeof this.$session.get("isManager")
									);
									if (this.$session.get("isManager") == 0) {
										this.$session.clear();
										alert("이메일 인증을 해주세요");
										this.loadingTop = false;
										document.location.reload();
									} else if (
										this.$session.get("isManager") == 7
									) {
										alert("관리자");
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
									router.push("/").catch(err => {
										err;
									});
									alert(
										"아이디와 비밀번호를 확인해 주십시오."
									);
									this.loadingTop = false;
								}
							})
							.catch(err => {
								this.loadingTop = false;
								// this.$store.dispatch("endLoading");
								console.log("LOGIN err ", err);
							});
					})
					.catch(err => {
						// this.$store.dispatch("endLoading");
						this.loadingTop = false;
						console.log("REGISTER catch ", err);
					});
				// http.post("/jwt/sendEmailkey/", formData);
			} else {
				if (!this.idcheck || !this.nicknameCheck)
					alert("중복확인을 해주세요");
				console.log("REGISTER ", "검증 실패");

				this.loadingTop = false;
			}
		},
		// previewImage: function(event) {
		// 	var input = event.target;
		// 	if (input.files && input.files[0]) {
		// 		var reader = new FileReader();
		// 		reader.onload = e => {
		// 			this.singUpMember.file = e.target.result;
		// 		};
		// 		reader.readAsDataURL(input.files[0]);
		// 	}
		// },
		onSubmit() {
			this.$refs.form.validate().then(success => {
				console.log("onSubmit");
				console.log(success);
				if (!success) {
					alert("제출양식에 맞지 않습니다.");
					return false;
				}
			});
			return true;
		},
		valid() {
			this.$refs.form.validate();
		},
		idCheck() {
			this.duplicate_id = [];
			if (this.signUpMember.id) {
				console.log("DUPLICATE ", this.signUpMember.id);
				http.post("/jwt/check", {
					id: this.signUpMember.id
				})
					.then(res => {
						console.log("DUPLICATE then ", this.signUpMember.id);
						console.log("DUPLICATE then ", res);
						if (res.data) {
							alert("사용하실수 있는 아이디입니다.");
							this.idcheck = true;
						} else {
							alert("아이디가 중복되었습니다.");
							this.idcheck = false;
						}
					})
					.catch(err => {
						console.log("DUPLICATE catch ", err);
					});
			} else {
				alert("아이디를 입력해 주십시오.");
			}
		},
		nicknameCheck() {
			this.duplicate_nickname = [];
			if (this.signUpMember.nickname) {
				const requestForm = {
					nickname: this.signUpMember.nickname
				};
				console.log("DUPLICATE ", requestForm);
				http.post("/jwt/checkNickName", requestForm)
					.then(res => {
						console.log(
							"DUPLICATE then ",
							this.signUpMember.nickname
						);
						console.log("DUPLICATE then ", res);
						if (res.status == "204") {
							alert("사용하실수 있는 닉네임입니다.");
							this.nicknamecheck = true;
						} else {
							alert("닉네임이 중복되었습니다.");
							this.nicknamecheck = false;
						}
					})
					.catch(err => {
						console.log("DUPLICATE catch ", err);
					});
			} else {
				alert("닉네임을 입력해 주십시오.");
			}
		},
		exceedHandler(file) {
			console.log(this.$refs.profile);
			console.warn("onExceed -> file", file);
			// this.$refs.profile.reset();
		}
	},
	mounted() {
		// this.localize("ko", this.dictionary)
		if (this.$session.has("useremail")) {
			this.signUpMember.id = this.$session.get("useremail");
			this.disabled = true;
			this.idcheck = true;
			this.idCheck();
			this.$session.destroy();
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
#backBox {
	display: grid;
	justify-content: center;
	width: 450px;
	/* background-color: aquamarine; */
}
#logo {
	width: 75px;
	height: 75px;
}
#nameBox {
	text-align: center;
	margin-top: 25px;
	margin-bottom: 15px;
}
#codeCoworkers {
	font-size: 30px;
	margin-bottom: 0px;
}
#formData {
	width: 350px;
}
#imgBox > span > div {
	width: 200px;
	height: 200px;
}
#imgBox {
	display: grid;
	justify-content: center;
	margin: 20px auto 20px auto;
}
#idBox {
	display: inline-block;
	width: 80%;
	justify-content: left;
}
#idCheck {
	width: 65px;
	float: right;
	font-size: 15px;
	height: 35px;
	text-align: center;
	margin-top: 13px;
	color: white;
	background-color: rgba(160, 23, 98, 0.5);
	border-radius: 5px;
	transform: translateY(-10%);
	cursor: pointer;
}

#idCheck:hover {
	background-color: rgba(160, 23, 98, 0.8);
	box-shadow: 1px 1px 3px rgba(0, 0, 0, 0.2);
}

#checkText {
	margin-top: 5px;
	margin-bottom: 5px;
}
#submitButton {
	width: 100%;
	height: 50px;
	font-size: 20px;
	color: white;
	background-color: #7d4879;
	border-radius: 5px;
	margin-bottom: 50px;
	outline: none;
}
#submitButton:hover {
	color: #7d4879;
	background-color: white;
	box-shadow: 1px 1px 3px rgba(0, 0, 0, 0.2);
}
#check {
	color: rgba(160, 23, 98, 0.5);
}
</style>
