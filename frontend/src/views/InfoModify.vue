<template>
	<div>
		<div id="title">
			<div id="title_text">회원정보수정</div>
		</div>
		<div id="modify">
			<validation-observer ref="form">
				<form
					@submit.prevent="updateForm"
					name="updateForm"
					id="updateForm"
					enctype="multipart/form-data"
				>
					<div class="info_div">
						<v-text-field
							label="아이디"
							v-model="member.id"
							readonly
							color="gray"
							filled
						></v-text-field>
					</div>

					<div id="img_div">
						<div id="back_img">
							<img-inputer
								name="bannerImage"
								v-model="bannerImage"
								placeholder="Drop file here or click"
								bottomText="Drop file here or click"
								exceedSizeText="사진의 크기가 초과하였습니다"
								:maxSize="10240"
								ref="bannerImage"
								@onExceed="exceedHandler"
								:img-src="member.bannerImageUrl"
								size="small"
								class="img"
								:disabled="disabled"
							/>
							<br />배너 이미지
							<span
								class="deleteImg"
								@click="disabled ? '' : deleteImg(true)"
							>
								삭제
							</span>
						</div>

						<div id="profile_img">
							<img-inputer
								name="profileImage"
								v-model="profileImage"
								placeholder="Drop file here or click"
								bottomText="Drop file here or click"
								exceedSizeText="사진의 크기가 초과하였습니다"
								:maxSize="10240"
								ref="profileImage"
								@onExceed="exceedHandler"
								:img-src="member.profileImageUrl"
								size="small"
								class="img"
								:disabled="disabled"
							/>
							<br />프로필 이미지
							<span
								class="deleteImg"
								@click="disabled ? '' : deleteImg(false)"
							>
								삭제
							</span>
						</div>
					</div>

					<div id="nick_div" class="info_div">
						<div id="nick_text">
							<validation-provider
								name="닉네임 "
								rules="required|max:10"
								v-slot="{ errors }"
							>
								<v-text-field
									name="nickName"
									v-model="member.nickName"
									:counter="10"
									label="닉네임"
									:error-messages="errors[0] ? errors[0] : []"
									color="gray"
									:disabled="disabled"
								></v-text-field>
							</validation-provider>
						</div>
						<div id="duplicate_btn" @click="nickCheck">
							중복확인
						</div>
					</div>

					<div class="info_div">
						<v-text-field
							name="bannerText"
							label="마이페이지 타이틀"
							color="gray"
							v-model="member.bannerText"
							:disabled="disabled"
						></v-text-field>
					</div>

					<div style="margin-bottom: 20px;">
						<span style="color: gray; font-size: 12px;">
							마이페이지 태그 (10개 제한)
						</span>
						<VueTagsInput
							v-model="tag"
							:tags="tags"
							@tags-changed="newTags => (tags = newTags)"
							:add-on-key="[13, 32, 9, ':', ';', ' ']"
							add-on-blur
							allow-edit-tags
							:max-tags="10"
							id="tagsInput"
							placeholder
							:disabled="disabled"
						/>
					</div>

					<div class="info_div">
						<validation-provider
							name="비밀번호 "
							vid="confirmation"
							rules="min:8|max:16|password"
							v-slot="{ errors }"
						>
							<v-text-field
								name="password"
								v-model="pwd"
								label="비밀번호"
								:counter="16"
								:type="pwd1 ? 'text' : 'password'"
								:error-messages="errors[0] ? errors[0] : []"
								:append-icon="pwd1 ? 'mdi-eye' : 'mdi-eye-off'"
								@click:append="pwd1 = !pwd1"
								color="gray"
								:disabled="disabled"
							></v-text-field>
						</validation-provider>
					</div>

					<div class="info_div">
						<validation-provider
							name="비밀번호 확인 "
							rules="confirmed:confirmation"
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
								:disabled="disabled"
							></v-text-field>
						</validation-provider>
					</div>

					<div class="info_div">
						<v-text-field
							v-model="member.gitUrl"
							label="Git url(선택)"
							color="gray"
							name="gitUrl"
							:disabled="disabled"
						></v-text-field>
					</div>

					<div class="info_div">
						<v-text-field
							v-model="member.kakaoUrl"
							label="Kakao url(선택)"
							color="gray"
							name="kakaoUrl"
							:disabled="disabled"
						></v-text-field>
					</div>

					<div class="info_div">
						<v-text-field
							v-model="member.instagramUrl"
							label="Instagram url(선택)"
							color="gray"
							name="instagramUrl"
							:disabled="disabled"
						></v-text-field>
					</div>

					<v-btn
						class="my-2"
						@click="updateForm"
						id="modify_btn"
						:disabled="disabled"
					>
						수정하기
					</v-btn>
					<v-dialog v-model="dialog" width="350" :disabled="disabled">
						<template v-slot:activator="{ on }">
							<v-btn v-on="on" class="my-2" :disabled="disabled">
								탈퇴하기
							</v-btn>
						</template>

						<v-card class="d_container">
							<div
								style="font-family: '궁서체'; font-size: 25px; font-weight: bold; color: red; margin-bottom: 3px"
							>
								정말 탈퇴하시겠습니까?
							</div>
							<div style="display: inline-block;">
								<img
									src="../assets/icon/error.png"
									width="30px"
									style="margin-bottom: 5px; float: left;"
								/>
								<div style="float: left; margin-top: 3px;">
									삭제한 정보는 복구가 불가능합니다!!!
								</div>
							</div>
							<button class="d_btn" @click="deleteMember()">
								네
							</button>
							<button class="d_btn" @click="dialog = false">
								아니오
							</button>
						</v-card>
					</v-dialog>
				</form>
			</validation-observer>
			<div id="blank"></div>
		</div>
	</div>
</template>
<script>
import http from "../http-common";
import { ValidationProvider, ValidationObserver } from "vee-validate";
import { VueTagsInput, createTags } from "@johmun/vue-tags-input";

export default {
	name: "InfoModify",
	components: {
		ValidationProvider,
		ValidationObserver,
		VueTagsInput
	},
	data() {
		return {
			disabled: false,
			dialog: false,
			member: {
				id: "",
				profileImageUrl: "",
				bannerImageUrl: "",
				nickName: "",
				gitUrl: "",
				kakaoUrl: "",
				instagramUrl: "",
				password: "",
				bannerText: ""
			},
			pwd: "",
			profileImage: "",
			bannerImage: "",
			pwd1: false,
			pwd2: false,
			passwordConfirm: "",
			duplicate: true,
			nickName_duplication: "",
			tag: "",
			tags: []
		};
	},

	methods: {
		deleteMember() {},
		deleteImg(flag) {
			if (!window.confirm("정말 삭제하시겠습니까?")) return;

			let address = "";
			if (flag) {
				address = "/trc/deleteMemberBannerImage";
			} else {
				address = "/trc/deleteMemberProfile";
			}

			http.post(address, Number(this.$session.get("id")), {
				headers: {
					Authorization:
						this.$session.get("accessToken") == undefined
							? null
							: this.$session.get("accessToken")
				}
			})
				.then(response => {
					if (response.status == 203) {
						// console.log("refresh token -> server");
						http.post(
							"/jwt/getAccessTokenByRefreshToken/",
							this.$session.get("refreshToken") == undefined
								? null
								: this.$session.get("refreshToken")
						)
							.then(ref => {
								// console.log("ref");
								// console.log(ref);
								// console.log(
								// 	this.$session.get("refreshToken") ==
								// 		undefined
								// 		? null
								// 		: this.$session.get("refreshToken")
								// );
								if (ref.status == 203) {
									this.$session.destroy();
									alert("로그인 정보가 만료되었습니다.");
									this.$router.push("/");
								} else {
									this.$session.set("accessToken", ref.data);
									window.location.reload(true);
								}
							})
							.catch(error => {
								console.log(error);
							});
					} else {
						// console.log("DELETE then ", response);
						if (flag) {
							this.member.bannerImageUrl = "";
							this.bannerImage = null;
						} else {
							this.member.profileImageUrl = "";
							this.profileImage = null;
							this.$session.remove("imageUrl");
						}
						alert("삭제되었습니다 ~");
					}
				})
				.catch(err => {
					this.$store.dispatch("endLoading");
					// console.log("DELETE catch ", err);
				});
		},
		updateForm() {
			if (!this.duplicate) {
				alert("닉네임 중복확인이 필요합니다!");
				return;
			}

			if (this.onSubmit() && window.confirm("정말 수정하시겠습니까?")) {
				this.disabled = true;
				// this.$store.dispatch("startLoading");

				this.t_tags = [];

				// console.log(this.tags);
				for (let i = 0; i < this.tags.length; ++i) {
					this.t_tags.push(this.tags[i].text);
				}

				let formData = new FormData(
					document.getElementById("updateForm")
				);
				formData.append("tags", this.t_tags);
				formData.append("idmember", this.$session.get("id"));

				if (this.$refs.bannerImage.file) {
					// console.log("bannerImg file exist");
					formData.set("bannerImage", this.$refs.bannerImage.file);
				}

				console.log(this.$refs.profileImage.file);
				if (this.$refs.profileImage.file) {
					// console.log("profile file exist");
					formData.set("profileImage", this.$refs.profileImage.file);
				}

				// console.log("UPDATE beforeaxios");
				// for (var pair of formData.entries()) {
				// 	console.log(pair[0] + ", " + pair[1]);
				// }
				http.post("/trc/updateMemeberInfo/", formData, {
					headers: {
						Authorization:
							this.$session.get("accessToken") == undefined
								? null
								: this.$session.get("accessToken")
					}
				})
					.then(response => {
						if (response.status == 203) {
							http.post(
								"/jwt/getAccessTokenByRefreshToken/",
								this.$session.get("refreshToken") == undefined
									? null
									: this.$session.get("refreshToken")
							)
								.then(ref => {
									if (ref.status == 203) {
										this.$session.destroy();
										alert("로그인 정보가 만료되었습니다.");
										this.$router.push("/");
									} else {
										this.$session.set(
											"accessToken",
											ref.data
										);
										window.location.reload(true);
									}
								})
								.catch(error => {
									console.log(error);
								});
						} else {
							this.$session.set("nickName", this.member.nickName);

							if (this.$refs.profileImage.file) {
								console.log(
									"http://52.79.118.55:8888/userprofile/" +
										this.$session.get("id") +
										"_" +
										this.profileImage.name
								);
								this.$session.set(
									"imageUrl",
									"http://52.79.118.55:8888/userprofile/" +
										this.$session.get("id") +
										"_" +
										this.profileImage.name
								);
								this.$emit(
									"updateUserProfile",
									"http://52.79.118.55:8888/userprofile/" +
										this.$session.get("id") +
										"_" +
										this.profileImage.name
								);
							}
							alert("회원정보가 수정되었습니다.");
							this.$router.push(
								"/mypage/" + this.$session.get("id")
							);
						}
					})
					.catch(err => {
						this.$store.dispatch("endLoading");
					});
			} else {
				console.log("UPDATE ", "검증 실패");
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
		nickCheck() {
			if (this.nickName_duplication == this.member.nickName) return;
			if (this.member.nickName) {
				http.post("/jwt/checkNickName", {
					nickname: this.member.nickName
				})
					.then(res => {
						if (res.status == "204") {
							alert("사용하실수 있는 닉네임입니다.");
							this.duplicate = true;
						} else {
							alert("닉네임이 중복되었습니다.");
							this.duplicate = false;
						}
					})
					.catch(err => {});
			} else {
				alert("닉네임을 입력해 주십시오.");
			}
		},
		exceedHandler(file) {
			// console.log(this.$refs.profile);
			// console.warn("onExceed -> file", file);
			// this.$refs.profile.reset();
		}
	},
	mounted() {
		// this.localize("ko", this.dictionary);
		if (this.$store.state.isCheck == 0) {
			alert("잘못된 접근입니다!");
			this.$router.push("/mypage/" + this.$session.get("id"));
		} else {
			this.$store.state.isCheck = 0;
		}

		http.post("/api/findByMemberHomePageModify", this.$session.get("id"), {
			headers: {
				Authorization:
					this.$session.get("accessToken") == undefined
						? null
						: this.$session.get("accessToken")
			}
		})
			.then(response => {
				if (response.status == 203) {
					http.post(
						"/jwt/getAccessTokenByRefreshToken/",
						this.$session.get("refreshToken") == undefined
							? null
							: this.$session.get("refreshToken")
					)
						.then(ref => {
							if (ref.status == 203) {
								this.$session.destroy();
								alert("로그인 정보가 만료되었습니다.");
								this.$router.push("/");
							} else {
								this.$session.set("accessToken", ref.data);
								window.location.reload(true);
							}
						})
						.catch(error => {
							console.log(error);
						});
				} else {
					this.member = response.data;
					this.nickName_duplication = this.member.nickName;
					this.tags = createTags(this.member.tags, []);
				}
			})
			.catch(error => {
				console.log(error);
			});
	},
	computed: {
		loading: function() {
			return this.$store.state.loading;
		}
	}
};
</script>

<style>
#modify_btn {
	margin-right: 10px;
}
.img {
	margin-bottom: 10px;
}
.deleteImg {
	padding: 3px;
	text-decoration: underline;
	font-size: 13px;
	cursor: pointer;
	color: gray;
}
.deleteImg:hover {
	color: silver;
	font-style: italic;
}
#tagsInput {
	background: none;
}
#tagsInput > div {
	border-radius: 5px;
	border: 0.5px solid silver;
}

#tagsInput > div > ul > li.ti-tag.ti-valid {
	background-color: rgba(160, 23, 98, 0.5);
}

#tagsInput > div > ul > li.ti-tag.ti-valid.ti-deletion-mark {
	background-color: rgba(160, 23, 98, 0.9);
}

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
	padding: 30px;
	text-align: center;
	width: 100%;
}

#back_img {
	float: left;
}

#back_img > div {
	height: 120px;
}

#profile_img {
	float: right;
	margin-left: 40px;
}

#profile_img > div {
	width: 120px;
	height: 120px;
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

#blank {
	height: 50px;
}

@media screen and (max-width: 500px) {
	#blank {
		height: 20vw;
		width: 100%;
	}

	#img_div {
		padding: 15px;
	}

	#back_img > div {
		height: 100px;
		width: 120px;
	}

	#profile_img {
		margin-left: 20px;
	}

	#profile_img > div {
		width: 100px;
		height: 100px;
	}

	#nick_text {
		width: 75%;
	}

	#duplicate_btn {
		width: 23%;
		font-size: 13px;
	}
}

@media screen and (max-width: 500px) {
	#modify {
		width: 80%;
		margin-top: 70px;
	}
}

#title {
	margin: 0 auto;
	padding: 20px;
	font-size: 30px;
	font-weight: 500;
	text-align: center;
	background: white;
	box-shadow: 0.5px 0.5px 3px rgba(0, 0, 0, 0.2);
	position: fixed;
	top: 0;
	width: 100%;
	z-index: 90;
}

@media screen and (max-width: 600px) {
	#title {
		font-size: 20px;
		padding: 10px;
	}
}
</style>
