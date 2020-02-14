<template>
	<div id="modify">
		<validation-observer ref="form">
			<form
				@submit.prevent="register"
				id="formData"
				enctype="multipart/form-data"
			>
				<div id="img_div">
					<div id="back_img">
						<img-inputer
							name="file"
							v-model="signUpMember.file"
							placeholder="Drop file here or click"
							bottomText="Drop file here or click"
							exceedSizeText="사진의 크기가 초과하였습니다"
							:maxSize="10240"
							ref="profile"
							@onExceed="exceedHandler"
							img-src="../img/icons/close_w.png"
							size="small"
						/>
						<br />배너 이미지
					</div>

					<div id="profile_img">
						<img-inputer
							name="file"
							v-model="signUpMember.file"
							placeholder="Drop file here or click"
							bottomText="Drop file here or click"
							exceedSizeText="사진의 크기가 초과하였습니다"
							:maxSize="10240"
							ref="profile"
							@onExceed="exceedHandler"
							img-src=""
							size="small"
						/>
						<br />프로필 이미지
					</div>
				</div>

				<div class="info_div">
					<v-text-field
						name="id"
						label="아이디"
						v-model="temp_id"
						readonly
						color="gray"
					></v-text-field>
				</div>

				<div id="nick_div" class="info_div">
					<div id="nick_text">
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
							></v-text-field>
						</validation-provider>
					</div>
					<div id="duplicate_btn" @click="nickCheck">
						중복확인
					</div>
				</div>
				<!-- <div>
					<div v-if="duplicate">
						{{ duplicate }}
					</div>
				</div> -->

				<div class="info_div">
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
				</div>

				<div class="info_div">
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
				</div>

				<div class="info_div">
					<v-text-field
						v-model="signUpMember.gitUrl"
						label="Git url(선택)"
						color="gray"
					></v-text-field>
				</div>

				<div class="info_div">
					<v-text-field
						v-model="signUpMember.kakaoUrl"
						label="Kakao url(선택)"
						color="gray"
					></v-text-field>
				</div>

				<div class="info_div">
					<v-text-field
						v-model="signUpMember.instagramUrl"
						label="Instagram url(선택)"
						color="gray"
					></v-text-field>
				</div>

				<v-btn class="mr-4" type="submit">수정하기</v-btn>
				<v-btn @click="clear">초기화</v-btn>
			</form>
		</validation-observer>
		<div id="blank"></div>
	</div>
</template>

<script>
import http from "../http-common";
import router from "../router";
import {
	ValidationProvider,
	ValidationObserver,
	extend,
	validate
} from "vee-validate";

export default {
	name: "InfoModifyForm",
	components: {
		ValidationProvider,
		ValidationObserver
	},
	data: () => ({
		temp_id: "temp@naver.com",
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
		duplicate: "",
		idcheck: false
	}),

	methods: {
		register() {
			let formData = new FormData(document.getElementById("formData"));
			formData.set("file", this.$refs.profile.file);

			if (this.onSubmit() && this.idcheck) {
				this.$store.dispatch("startLoading");
				console.log("REGISTER beforeaxios ", formData);
				// http.post("/trc/signUp/", formData)
				// 	.then(res => {
				// 		console.log("REGISTER then ", res);

				// 		http.post("/jwt/login/", {
				// 			id: this.signUpMember.id,
				// 			password: this.signUpMember.password
				// 		},
				// { headers: { Authorization: this.$session.get("accessToken") } })
				// 			.then(res => {
				// 				console.log(res);
				// 				if (res.status != "204") {
				// 					this.$session.start();
				// 					this.$session.set(
				// 						"accessToken",
				// 						res.data.accessToken
				// 					);
				// 					this.$session.set(
				// 						"refreshToken",
				// 						res.data.refreshToken
				// 					);
				// 					this.$store.state.token =
				// 						res.data.accessToken;
				// 					this.$session.set(
				// 						"id",
				// 						Number(this.$store.getters.userId)
				// 					);
				// 					this.$session.set("targetId", 10);
				// 					this.loading = false;
				// 					router.push("/newsfeed");
				// 					console.log("LOGIN then ", res);
				// 				} else {
				// 					router.push("/").catch(err => {
				// 						err;
				// 					});
				// 					alert(
				// 						"아이디와 비밀번호를 확인해 주십시오."
				// 					);
				// 					this.loading = false;
				// 				}
				// 			})
				// 			.catch(err => {
				// 				this.loading = false;
				// 				// this.$store.dispatch("endLoading");
				// 				console.log("LOGIN err ", err);
				// 			});

				// 		this.$store.dispatch("endLoading");
				// 		alert("회원가입이 성공적으로 완료되었습니다.");
				// 		router.push("/");
				// 	})
				// 	.catch(err => {
				// 		this.$store.dispatch("endLoading");
				// 		console.log("REGISTER catch ", err);
				// 	});
				// http.post("/jwt/sendEmailkey/", formData);
			} else {
				console.log("REGISTER ", "검증 실패");
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
				if (!success) {
					alert("제출양식에 맞지 않습니다.");
					return false;
				}
			});
			return true;
		},
		clear() {
			this.$refs.form.reset();
		},
		valid() {
			this.$refs.form.validate();
		},
		nickCheck() {
			if (this.signUpMember.id) {
				this.duplicate = "";
				console.log("DUPLICATE ", this.signUpMember.id);
				http.post("/api/check", {
					id: this.signUpMember.id
				})
					.then(res => {
						console.log("DUPLICATE then ", this.signUpMember.id);
						console.log("DUPLICATE then ", res);
						if (res.data) {
							this.duplicate = "사용하실수 있는 아이디입니다.";
							this.idcheck = true;
						} else {
							this.duplicate = "아이디가 중복되었습니다.";
							this.idcheck = false;
						}
					})
					.catch(err => {
						console.log("DUPLICATE catch ", err);
					});
			} else {
				this.duplicate = "닉네임을 입력해 주십시오.";
			}
			alert(this.duplicate);
		},
		exceedHandler(file) {
			console.log(this.$refs.profile);
			console.warn("onExceed -> file", file);
			// this.$refs.profile.reset();
		}
	},
	mounted() {
		// this.localize("ko", this.dictionary)
		// if (this.$store.state.isCheck == 0) {
		// 	alert("잘못된 접근입니다!");
		// 	this.$router.push("/mypage/" + this.$session.get("id"));
		// } else {
		// 	this.$store.state.isCheck = 0;
		// }
	},
	computed: {
		loading: function() {
			return this.$store.state.loading;
		}
	}
};
</script>

<style>
#modify {
	width: 400px;
	position: relative;
	margin: 0 auto;
	margin-top: 100px;
	z-index: 1;

	-webkit-font-smoothing: subpixel-antialiased;
	-webkit-touch-callout: none;
	-webkit-user-select: none;
	-khtml-user-select: none;
	-ms-user-select: none;
	-moz-user-select: none;
	user-select: none;
}

#img_div {
	display: inline-block;
	padding: 15px;
	text-align: center;
}

#back_img {
	float: left;
}

#profile_img {
	float: left;
	margin-left: 40px;
}

#nick_div {
	display: inline-block;
	width: 100%;
}

#nick_text {
	float: left;
	width: 80%;
}

#duplicate_btn {
	float: right;
	font-size: 15px;
	width: 18%;
	color: white;
	background-color: rgba(160, 23, 98, 0.5);
	padding: 8px 5px 8px 5px;
	border-radius: 10px;
	margin-top: 10px;
	box-shadow: 0.1px 0.1px 5px 0.15px rgba(0, 0, 0, 0.267);
	transition: all 0.3s ease;
	text-align: center;
	cursor: pointer;
}

#duplicate_btn:hover {
	background-color: rgba(160, 23, 98, 0.7);
}

.info_div {
	height: 80px;
	overflow: hidden;
}

@media screen and (max-width: 600px) {
	#blank {
		height: 20vw;
		width: 100%;
	}
}

@media screen and (max-width: 500px) {
	#modify {
		width: 80%;
		margin-top: 50px;
	}
}
</style>
