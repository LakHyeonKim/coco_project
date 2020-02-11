<template>
	<div style="display: flex ; align-itmes: center; justify-content: center;">
		<validation-observer ref="form">
			<form
				@submit.prevent="register"
				id="formData"
				enctype="multipart/form-data"
			>
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

				<!-- <v-gravatar :email="email" alt="gravatar" :size="50" /> -->

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
					></v-text-field>
				</validation-provider>
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
					></v-text-field>
				</validation-provider>
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
					></v-text-field>
				</validation-provider>
				<v-text-field
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
				></v-text-field>

				<v-btn class="mr-4" type="submit">회원가입</v-btn>
				<v-btn @click="clear">초기화</v-btn>
			</form>
			<div>
				<button v-on:click="idCheck">중복확인</button>
				<div v-if="duplicate">
					<div v-for="(message, idx) in duplicate" :key="idx">
						{{ message }}
					</div>
				</div>
			</div>
		</validation-observer>
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
		duplicate: [],
		idcheck: false
	}),

	methods: {
		register() {
			let formData = new FormData(document.getElementById("formData"));
			formData.set("file", this.$refs.profile.file);

			if (this.onSubmit() && this.idcheck) {
				this.$store.dispatch("startLoading");
				console.log("REGISTER beforeaxios ", formData);
				http.post("/trc/signUp/", formData)
					.then(res => {
						console.log("REGISTER then ", res);

						http.post("/jwt/login/", {
							id: this.signUpMember.id,
							password: this.signUpMember.password
						})
							.then(res => {
								console.log(res);
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
									this.$session.set(
										"id",
										Number(this.$store.getters.userId)
									);
									this.$session.set("targetId", 10);
									this.loading = false;
									router.push("/newsfeed");
									console.log("LOGIN then ", res);
								} else {
									router.push("/").catch(err => {
										err;
									});
									alert(
										"아이디와 비밀번호를 확인해 주십시오."
									);
									this.loading = false;
								}
							})
							.catch(err => {
								this.loading = false;
								// this.$store.dispatch("endLoading");
								console.log("LOGIN err ", err);
							});

						this.$store.dispatch("endLoading");
						alert("회원가입이 성공적으로 완료되었습니다.");
						const credentials = {
							id: this.signUpMember.id,
							password: this.signUpMember.password
						};
						http.post("/jwt/login/", credentials)
							.then(res => {
								console.log(res);
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
									this.$session.set(
										"id",
										Number(this.$store.getters.userId)
									);
									this.loading = false;
									router.push("/newsfeed");
									console.log("LOGIN then ", res);
								} else {
									router.push("/").catch(err => {
										err;
									});
									alert(
										"아이디와 비밀번호를 확인해 주십시오."
									);
									this.loading = false;
								}
							})
							.catch(err => {
								this.loading = false;
								// this.$store.dispatch("endLoading");
								console.log("LOGIN err ", err);
							});
					})
					.catch(err => {
						this.$store.dispatch("endLoading");
						console.log("REGISTER catch ", err);
					});
				// http.post("/jwt/sendEmailkey/", formData);
			} else {
				console.log("REGISTER ", "검증 실패");
			}
		},
		previewImage: function(event) {
			var input = event.target;
			if (input.files && input.files[0]) {
				var reader = new FileReader();
				reader.onload = e => {
					this.singUpMember.file = e.target.result;
				};
				reader.readAsDataURL(input.files[0]);
			}
		},
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
		idCheck() {
			if (this.signUpMember.id) {
				this.duplicate = [];
				console.log("DUPLICATE ", this.signUpMember.id);
				http.post("/api/check", {
					id: this.signUpMember.id
				})
					.then(res => {
						console.log("DUPLICATE then ", this.signUpMember.id);
						console.log("DUPLICATE then ", res);
						if (res.data) {
							this.duplicate.push(
								"사용하실수 있는 아이디입니다."
							);
							this.idcheck = true;
						} else {
							this.duplicate.push("아이디가 중복되었습니다.");
							this.idcheck = false;
						}
					})
					.catch(err => {
						console.log("DUPLICATE catch ", err);
					});
			} else {
				this.duplicate.push("아이디를 입력해 주십시오.");
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
	},
	computed: {
		loading: function() {
			return this.$store.state.loading;
		}
	}
};
</script>

<style scoped></style>
