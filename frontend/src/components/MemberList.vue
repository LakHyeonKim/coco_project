<template>
	<div class="text-center">
		<v-dialog v-model="dialog" width="350">
			<template v-slot:activator="{ on }">
				<div v-on="on"><slot name="click"></slot></div>
			</template>

			<v-card class="d_container">
				<div v-if="followList != null && followList.length == 0">
					해당 정보가 없습니다 ㅠㅠ
				</div>

				<div v-for="(f, idx) in followList" :key="f.member.idmember">
					<div class="f_div">
						<div
							class="f_info_div"
							@click="moveMypage(f.member.idmember)"
						>
							<img
								class="f_img"
								:src="
									f.member.imageUrl == null ||
									f.member.imageUrl == ''
										? '../img/icons/user.png'
										: f.member.imageUrl
								"
							/>
							<div class="f_nick_div">
								<span>
									{{ f.member.nickname }}
								</span>
							</div>
						</div>
						<div class="f_btn_div">
							<MypageFollowCheck
								v-if="f.member.idmember != $session.get('id')"
								:nickname="f.member.nickname"
								:isFollow="f.isFollow"
								:follow="otherFollow"
							>
								<div slot="click" class="isFollow">
									<button
										class="f_button"
										:style="
											f.isFollow == 1
												? following_btn
												: follow_btn
										"
										@click="otherIdx = idx"
									>
										<img
											:src="
												f.isFollow == 1
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
				</div>
			</v-card>
		</v-dialog>
	</div>
</template>

<script>
import http from "../http-common";
import MypageFollowCheck from "./MypageFollowCheck.vue";
export default {
	components: { MypageFollowCheck },
	props: {
		userInfo: {},
		followList: {}
	},
	data() {
		return {
			isNone: false,
			dialog: false,
			following_btn: {
				backgroundColor: "white",
				borderRadius: "5px",
				color: "gray",
				padding: "3px 10px 3px 10px",
				border: "1px solid silver"
			},
			follow_btn: {
				backgroundColor: "rgb(192, 110, 155)",
				borderRadius: "5px",
				color: "white",
				padding: "3px 10px 3px 10px"
			},
			otherIdx: {}
		};
	},
	methods: {
		moveMypage(num) {
			this.$router.push("/mypage/" + num);
		},
		otherFollow() {
			let address = "";
			console.log(this.followList[this.otherIdx]);
			let isFollow = this.followList[this.otherIdx].isFollow;
			if (isFollow == 1) {
				address = "/trc/makeUnFollow/";
				this.followList[this.otherIdx].isFollow = 0;
			} else {
				address = "/trc/makeFollow/";
				this.followList[this.otherIdx].isFollow = 1;
			}

			http.post(
				address,
				{
					memberFollower: this.$session.get("id"),
					memberFollowing: this.followList[this.otherIdx].member
						.idmember
				},
				{
					headers: {
						Authorization:
							this.$session.get("accessToken") == undefined
								? null
								: this.$session.get("accessToken")
					}
				}
			)
				.then(response => {
					console.log(response);
					if (response.status == 203) {
						console.log("refresh token -> server");
						http.post("/jwt/getAccessTokenByRefreshToken/", {
							refToken:
								this.$session.get("refreshToken") == undefined
									? null
									: this.$session.get("refreshToken")
						})
							.then(ref => {
								console.log(ref);

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
					}
				})
				.catch(error => {
					console.log(error);
					isFollow = this.followList[this.otherIdx].isFollow;
					if (isFollow == 1) {
						this.followList[this.otherIdx].isFollow = 0;
					} else {
						this.followList[this.otherIdx].isFollow = 1;
					}
				});
		}
	}
};
</script>
<style>
.f_img {
	width: 40px;
	height: 40px;
	overflow: hidden;
	border: 1px solid silver;
	border-radius: 50%;
	float: left;
}
.f_nick_div {
	float: left;
	padding: 8px;
}
.f_div {
	display: flex;
	justify-content: space-between;
	/* width: 100%; */
	/* background-color: silver; */
	/* margin: 2px 0 2px 0; */
	margin: 10px 0 10px 0;
	padding: 5px;
	border-radius: 10px;
}
.f_div:hover {
	box-shadow: 0.5px 0.5px 5px rgba(0, 0, 0, 0.3);
	cursor: pointer;
}
.f_info_div {
	width: 80%;
	height: 40px;
	margin-right: 0px;
}
.f_btn_div {
	width: 15%;
	padding: 5px;
	display: flex;
	flex-direction: row-reverse;
	float: right;
}
::-webkit-scrollbar {
	width: 5.2px;
}
::-webkit-scrollbar-track {
	background: white;
}
::-webkit-scrollbar-thumb {
	background: rgba(0, 0, 0, 0.3);
}
::-webkit-scrollbar-thumb:hover {
	background: rgba(0, 0, 0, 0.5);
}
::-webkit-scrollbar-thumb:active {
	background: rgba(0, 0, 0, 0.7);
}
@media screen and (max-width: 400px) {
	.f_nick_div {
		font-size: 15px;
	}
}
</style>
