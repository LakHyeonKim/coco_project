<template>
	<div id="banner">
		<div id="imgBannerBox">
			<img
				id="imgBanner"
				:src="
					userInfo.mypage.bannerImagePath == ''
						? '../img/back.jpg'
						: userInfo.mypage.bannerImagePath
				"
			/>
		</div>
		<div id="infoBox">
			<div id="today">
				today {{ userInfo.mypage.todayVisitedCount }} · total
				{{ userInfo.mypage.totalVisitedCount }}
			</div>
			<div id="info_title">{{ userInfo.mypage.bannerText }}</div>
			<div id="info_desc">
				<img
					id="imgUser"
					:src="
						userInfo.member.imageUrl == ''
							? '../img/icons/user.png'
							: userInfo.member.imageUrl
					"
				/>
				<!-- <img id="imgUser" :src="'../img/icons/user.png'" /> -->
				<div id="info_desc_mid">
					<div id style="display: inline-block;">
						<div id="nickname">{{ userInfo.member.nickname }}</div>

						<MypagePWCheck v-if="isUser" style="float: left;">
							<div slot="click">
								<img
									slot="click"
									src="../assets/icon/settings.png"
									id="settings"
								/>
							</div>
						</MypagePWCheck>
						<MypageFollowCheck
							v-if="!isUser"
							:nickname="userInfo.member.nickname"
							:isFollow="userInfo.isFollew"
							:follow="follow"
							style="float: left;"
						>
							<div slot="click" class="isFollow">
								<button
									class="f_button"
									:style="
										userInfo.isFollew == 1
											? following_btn
											: follow_btn
									"
								>
									{{ f_current }}
									<img
										:src="
											userInfo.isFollew == 1
												? '../img/icons/check_g.png'
												: '../img/icons/plus_w.png'
										"
										width="13px"
									/>
								</button>
							</div>
						</MypageFollowCheck>
					</div>
				</div>

				<div id="counting">
					<MemberList
						:userInfo="userInfo"
						:followList="followList"
						class="counting_click"
					>
						<div slot="click">
							<span @click="getFollow(false)"
								>팔로잉 {{ userInfo.followingCount }}</span
							>
						</div>
					</MemberList>
					<span class="counting_sub">·</span>
					<MemberList
						:userInfo="userInfo"
						:followList="followList"
						class="counting_click"
					>
						<div slot="click">
							<span @click="getFollow(true)"
								>팔로워 {{ userInfo.followerCount }}</span
							>
						</div>
					</MemberList>
					<span class="counting_sub">·</span>
					<span class="counting_sub"
						>게시글 {{ userInfo.totalPostCount }}</span
					>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
import http from "../http-common";
import store from "../store";
import MypagePWCheck from "./MypagePWCheck.vue";
import MypageFollowCheck from "./MypageFollowCheck.vue";
import MemberList from "./MemberList.vue";
import "../assets/styles/check_btn.css";
export default {
	name: "MypageBanner",
	components: {
		MypagePWCheck,
		MypageFollowCheck,
		MemberList
	},
	store,
	data() {
		return {
			isFollowing: false,
			isUser: false,
			userInfo: {
				mypage: {
					todayVisitedCount: 0,
					totalVisitedCount: 0,
					bannerText: "",
					bannerImagePath: ""
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
			followList: null,
			memberFollower: null,
			memberFollowing: null
		};
	},
	methods: {
		pwCheck() {
			alert(this.input_pw);
			this.$router.push("/infoModify");
		},
		follow() {
			this.dialog = false;
			console.log(this.$route.params.no);

			let address = "";
			if (this.userInfo.isFollew == 1) {
				address = "/trc/makeUnFollow/";
				this.userInfo.isFollew = 0;
				this.f_current = "팔로우";
			} else {
				address = "/trc/makeFollow/";
				this.userInfo.isFollew = 1;
				this.f_current = "팔로잉";
			}
			this.showModal_f = false;

			http.post(
				address,
				{
					memberFollower: this.$session.get("id"),
					memberFollowing: this.$route.params.no
				},
				{ headers: { Authorization: this.$session.get("accessToken") } }
			)
				.then(response => {
					console.log(response);
				})
				.catch(error => {
					console.log(error);
					if (this.userInfo.isFollew == 1) {
						this.userInfo.isFollew = 0;
						this.f_current = "팔로우";
					} else {
						this.userInfo.isFollew = 1;
						this.f_current = "팔로잉";
					}
				});
		},
		getFollow(flag) {
			this.followList = null;
			let address = "";
			if (flag) address = "/api/findByFollowerListMember/";
			else address = "/api/findByFollowingListMember/";

			http.post(
				address,
				{
					myIdMemeber: this.$session.get("id"),
					youIdMember: this.$route.params.no
				},
				{ headers: { Authorization: this.$session.get("accessToken") } }
			)
				.then(response => {
					console.log("Banner getFollow()");
					this.followList = response.data;
					console.log(this.followList);
				})
				.catch(error => {
					console.log(error);
				});
		}
	},
	mounted() {
		console.log("MypageBanner : " + this.$route.params.no);
		const token = this.$session.get("accessToken");
		const headers = {
			Authorization: token
		};
		console.log("myPage banner headers", headers);
		if (this.$session.get("id") == this.$route.params.no) {
			this.isUser = true;
		}
		console.log("isUser : " + this.isUser);
		http.post(
			"/api/findByMemberHomePageUserID/",
			{
				myIdMemeber: this.$session.get("id"),
				youIdMember: this.$route.params.no
			},
			{ headers: { Authorization: this.$session.get("accessToken") } }
		)
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
.counting_sub,
.counting_click {
	float: left;
}
.counting_click {
	cursor: pointer;
}
.counting_click:hover {
	opacity: 0.3;
}
.f_button {
	outline: 0;
	border: 0;
}
.f_button:hover {
	filter: brightness(95%);
}
.f_button:active {
	filter: brightness(85%);
}
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
}
#infoBox {
	position: relative;
	display: inline-block;
	z-index: 1;
	top: -160px;
	width: 80%;
	height: 130px;
	padding: 10px;
	text-align: left;
}
#imgUser {
	float: left;
	width: 55px;
	height: 55px;
	margin-right: 10px;
	margin-top: 3px;
	border-radius: 50%;
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
.isFollow {
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

@media screen and (max-width: 600px) {
	#settings {
		width: 15px;
	}
	#imgBannerBox {
		height: 50vw;
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
