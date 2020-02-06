<template>
	<div id="banner">
		<!-- <ImgBanner imgSrc="bg_home.jpg" /> -->
		<div id="imgBannerBox">
			<img id="imgBanner" src="../assets/back.jpg" />
		</div>
		<div id="infoBox">
			<div id="today">
				today {{ userInfo.mypage.todayVisitedCount }} · total
				{{ userInfo.mypage.totalVisitedCount }}
			</div>
			<div id="info_title">
				{{ userInfo.mypage.bannerText }}
			</div>
			<div id="info_desc">
				<img id="imgUser" :src="userInfo.member.imageUrl" />
				<div id="info_desc_mid">
					<div id="" style="display: inline-block;">
						<div id="nickname">
							{{ userInfo.member.nickname }}
						</div>
						<img
							v-if="isUser"
							src="../assets/icon/settings.png"
							id="settings"
							@click="showModal = true"
						/>
						<div id="isFollow" v-if="!isUser">
							<button
								id="f_button"
								:style="
									userInfo.isFollew == 1
										? following_btn
										: follow_btn
								"
								@click="
									userInfo.isFollew == 1
										? (showModal_f = true)
										: follow()
								"
							>
								{{ f_current }}
								<img
									:src="
										userInfo.isFollew == 1
											? './img/icons/check_g.png'
											: './img/icons/plus_w.png'
									"
									width="13px"
								/>
							</button>
						</div>
					</div>
				</div>
				<div id="counting">
					팔로잉 {{ userInfo.followingCount }} · 팔로워
					{{ userInfo.followerCount }} · 게시글
					{{ userInfo.totalPostCount }}
				</div>
			</div>
		</div>
		<Modal v-if="showModal" @close="showModal = false">
			<div slot="header">비밀번호 확인</div>
			<div slot="body">
				<v-text-field
					:append-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
					:rules="[rules.required, rules.min]"
					:type="show ? 'text' : 'password'"
					hint="At least 8 characters"
					class="input-group--focused"
					@click:append="show = !show"
					:counter="counterEn ? counter : false"
					solo
					color="rgba(160, 23, 98, 0.5)"
					v-model="input_pw"
				></v-text-field>
			</div>
			<div slot="footer">
				<button class="modal-default-button" @click="pwCheck()">
					확인
				</button>
				<button class="modal-default-button" @click="showModal = false">
					취소
				</button>
			</div>
		</Modal>
		<Modal v-if="showModal_f" @close="showModal_f = false">
			<div slot="body">
				<span id="message">
					정말 팔로우 취소하시겠습니까?
				</span>
			</div>
			<div slot="footer">
				<button class="modal-default-button" @click="pwCheck()">
					확인
				</button>
				<button
					class="modal-default-button"
					@click="showModal_f = false"
				>
					취소
				</button>
			</div>
		</Modal>
	</div>
</template>

<script>
import http from "../http-common";
import store from "../store";
import Modal from "./Modal.vue";
export default {
	name: "banner",
	components: { Modal },
	store,
	data() {
		return {
			checkFollow: false,
			showModal: false,
			showModal_f: false,
			isUser: false,
			userInfo: {
				mypage: {
					todayVisitedCount: 0,
					totalVisitedCount: 0,
					bannerText: ""
				},
				member: {
					imageUrl: "",
					nickname: ""
				},
				followingCount: 0,
				followerCount: 0,
				totalPostCount: 0
			},
			f_current: "",
			following_btn: {
				marginLeft: "10px",
				backgroundColor: "white",
				borderRadius: "5px",
				color: "gray",
				padding: "3px 10px 3px 10px",
				boxShadow: "0.5px 0.5px 7px rgba(0, 0, 0, 0.3)"
			},
			follow_btn: {
				marginLeft: "10px",
				backgroundColor: "rgb(192, 110, 155)",
				borderRadius: "5px",
				color: "white",
				padding: "3px 10px 3px 10px",
				boxShadow: "0.5px 0.5px 5px rgba(0, 0, 0, 0.2)"
			},
			clearable: true,
			counterEn: true,
			counter: 16,
			show: false,
			password: "Password",
			rules: {
				required: value => !!value || "Required.",
				min: v => v.length >= 8 || "Min 8 characters",
				emailMatch: () =>
					"The email and password you entered don't match"
			},
			input_pw: ""
		};
	},
	methods: {
		clickFollow() {
			this.checkFollow = !this.checkFollow;
			if (this.userInfo.isFollew == 1) {
				// confirm("정말 팔로우를 취소하시겠습니까?");
			} else {
				console.log("");
			}
		},
		pwCheck() {
			alert(this.input_pw);
			this.$router.push("/infoModify");
			// http.post("/api/findByMyPosts/", {
			// 	idMember: this.$session.get("id"),
			// 	order: idx
			// })
			// 	.then(response => {
			// 		this.posts = response.data;
			// 		console.log(this.posts);
			// 	})
			// 	.catch(error => {
			// 		console.log(error);
			// 	});
		},
		follow() {
			alert("왓다리");
		}
	},
	mounted() {
		if (this.$session.get("id") == this.$session.get("targetId")) {
			this.isUser = true;
		}
		http.post("/api/findByMemberHomePageUserID/", {
			myIdMemeber: this.$session.get("id"),
			youIdMember: this.$session.get("targetId")
		})

			.then(response => {
				this.userInfo = response.data;
				store.state.tags = this.userInfo.tags;
				console.log("Banner mounted()");
				console.log(response);
				if (this.userInfo.isFollew == 1) {
					this.f_current = "팔로잉";
				} else {
					this.f_current = "팔로우";
				}
			})
			.catch(error => {
				console.log(error);
			});
	}
};
</script>

<style>
#message {
	font-size: 20px;
	font-weight: 500;
}
#settings {
	width: 17px;
	opacity: 0.5;
	cursor: pointer;
	float: left;
	margin: 8px 5px 0 3px;
}
#imgBannerBox {
	height: 35vw;
	overflow: hidden;
}
#imgBanner {
	width: 100%;
	filter: brightness(95%);
}
#today {
	font-size: 15px;
	font-weight: 300;
	text-shadow: 0.8px 0.8px 7px rgba(0, 0, 0, 0.5);
	color: white;
}
#banner {
	width: 100%;
	margin: 0 auto;
	text-align: center;
	height: 35vw;
	/* text-shadow: 0.8px 0.8px 7px rgba(0, 0, 0, 0.5); */
}
#infoBox {
	position: relative;
	display: inline-block;
	z-index: 1;
	top: -160px;
	/* background-color: rgba(0, 0, 0, 0.3); */
	width: 80%;
	height: 130px;
	padding: 10px;
	text-align: left;
}
#imgUser {
	float: left;
	width: 55px;
	border-radius: 50%;
	background-color: white;
}
#info_title {
	font-size: 25px;
	margin-bottom: 10px;
	text-shadow: 0.8px 0.8px 7px rgba(0, 0, 0, 0.5);
	color: white;
}
#info_title > a {
	font-weight: 500;
	color: black;
	text-decoration: none;
}
#nickname {
	font-size: 20px;
	float: left;
	text-shadow: 0.8px 0.8px 7px rgba(0, 0, 0, 0.5);
	color: white;
}
#isFollow {
	float: left;
	position: relative;
}
#counting {
	font-size: 15px;
	font-weight: 300;
	float: left;
	text-shadow: 0.8px 0.8px 7px rgba(0, 0, 0, 0.5);
	color: white;
}
#checkFollow {
	text-shadow: 0 0;
	margin: 0 auto;
	z-index: 1;
	width: 100px;
	height: 100px;
	background-color: rgba(255, 255, 255);
	color: black;
	border-radius: 5px;
	box-shadow: 0.5px 0.5px 7px rgba(0, 0, 0, 0.3);
}
@media screen and (max-width: 600px) {
	#settings {
		width: 15px;
	}
	#imgBannerBox {
		height: 50vw;
	}
	#imgUser {
		width: 50px;
	}
	#infoBox {
		top: -160px;
	}
	#banner {
		height: 50vw;
	}
}
@media screen and (max-width: 320px) {
	#settings {
		width: 15px;
		margin-top: 5px;
	}
	#info_title {
		font-size: 20px;
		margin-bottom: 3px;
	}
	#nickname {
		font-size: 17px;
	}
	#counting {
		font-size: 13px;
	}
	#today {
		font-size: 12px;
	}
	#infoBox {
		top: -125px;
	}
	#banner {
		height: 50vw;
	}
}
</style>
