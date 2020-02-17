<template>
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
							:img-src="member.bannerImgUrl"
							size="small"
							class="img"
						/>
						<br />배너 이미지
						<span class="deleteImg">삭제</span>
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
						/>
						<br />프로필 이미지
						<span class="deleteImg">삭제</span>
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
							></v-text-field>
						</validation-provider>
					</div>
					<div id="duplicate_btn" @click="nickCheck">중복확인</div>
				</div>

				<div class="info_div">
					<v-text-field
						name="bannerText"
						label="마이페이지 타이틀"
						color="gray"
						v-model="member.bannerText"
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
						:add-on-key="[13, 32, 9, ':', ';']"
						add-on-blur
						allow-edit-tags
						:max-tags="10"
						id="tagsInput"
						placeholder
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
						></v-text-field>
					</validation-provider>
				</div>

				<div class="info_div">
					<v-text-field
						v-model="member.gitUrl"
						label="Git url(선택)"
						color="gray"
						name="gitUrl"
					></v-text-field>
				</div>

				<div class="info_div">
					<v-text-field
						v-model="member.kakaoUrl"
						label="Kakao url(선택)"
						color="gray"
						name="kakaoUrl"
					></v-text-field>
				</div>

				<div class="info_div">
					<v-text-field
						v-model="member.instagramUrl"
						label="Instagram url(선택)"
						color="gray"
						name="instagramUrl"
					></v-text-field>
				</div>

				<v-btn class="my-2" @click="updateForm">수정하기</v-btn>
				<!-- <button class="btn" @click="clear">초기화</button> -->
			</form>
		</validation-observer>
		<div id="blank"></div>
	</div>
</template>
<script>
import http from "../http-common";
import { ValidationProvider, ValidationObserver } from "vee-validate";
import VueTagsInput from "@johmun/vue-tags-input";

export default {
	name: "InfoModifyForm",
	components: {
		ValidationProvider,
		ValidationObserver,
		VueTagsInput
	},
	data() {
		return {
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
		updateForm() {
			// let formData = new FormData(document.getElementById("formData"));
			// formData.set("file", this.$refs.profile.file);
			if (!this.duplicate) {
				alert("닉네임 중복확인이 필요합니다!");
				return;
			}
			if (this.onSubmit()) {
				// this.$store.dispatch("startLoading");

				this.t_tags = [];
				for (let i = 0; i < this.tags.length; ++i) {
					this.t_tags.push(this.tags[i].text);
				}

				let formData = new FormData(
					document.getElementById("updateForm")
				);
				formData.append("tags", this.t_tags);
				formData.append("tags", "");
				formData.append("idmember", this.$session.get("id"));
				console.log(this.$refs.bannerImage.file);
				formData.set("bannerImage", this.$refs.bannerImage.file);
				formData.set("profileImage", this.$refs.profileImage.file);

				console.log("UPDATE beforeaxios");
				for (var pair of formData.entries()) {
					console.log(pair[0] + ", " + pair[1]);
				}
				http.post("/trc/updateMemeberInfo/", formData, {
					headers: { Authorization: this.$session.get("accessToken") }
				})
					.then(res => {
						console.log("UPDATE then ", res);
						alert("회원정보가 수정되었습니다.");
					})
					.catch(err => {
						this.$store.dispatch("endLoading");
						console.log("UPDATE catch ", err);
					});
			} else {
				console.log("UPDATE ", "검증 실패");
			}
		},
		// previewImage: function(event) {
		//    var input = event.target;
		//    if (input.files && input.files[0]) {
		//       var reader = new FileReader();
		//       reader.onload = e => {
		//          this.singUpMember.file = e.target.result;
		//       };
		//       reader.readAsDataURL(input.files[0]);
		//    }
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
			if (this.nickName_duplication == this.member.nickName) return;
			if (this.member.nickName) {
				http.post("/jwt/checkNickName", { nickname: this.nickName })
					.then(res => {
						console.log("DUPLICATE then ", res);
						if (res.status == "204") {
							alert("사용하실수 있는 닉네임입니다.");
							this.duplicate = true;
						} else {
							alert("닉네임이 중복되었습니다.");
							this.duplicate = false;
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
		// if (this.$store.state.isCheck == 0) {
		//    alert("잘못된 접근입니다!");
		//    this.$router.push("/mypage/" + this.$session.get("id"));
		// } else {
		//    this.$store.state.isCheck = 0;
		// }

		http.post("/api/findByMemberHomePageModify", this.$session.get("id"), {
			headers: { Authorization: this.$session.get("accessToken") }
		})
			.then(res => {
				console.log("findByMemberHomepageModify");
				console.log(res.data);
				this.member = res.data;
				this.nickName_duplication = this.member.nickName;
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

#blank {
	height: 50px;
}

@media screen and (max-width: 600px) {
	#blank {
		height: 20vw;
		width: 100%;
	}

	#back_img > div {
		width: 140px;
	}

	#profile_img > div {
		width: 140px;
	}

	#img_div {
		padding: 30px;
	}
}

@media screen and (max-width: 500px) {
	#modify {
		width: 80%;
		margin-top: 50px;
	}
}
</style>
