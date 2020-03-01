<template>
	<!-- 모바일 화면에서 width 줄어들 때 구성요소 잘리는 현상 -->
	<!-- 웹 화면에서 구성요소 링크 범위 조절 -->
	<div id="navbar">
		<notifications group="foo" />
		<ul>
			<li id="profile_menu">
				<img
					@click="isBlock = !isBlock"
					id="profile"
					:src="userProfile"
				/>
				<ul
					id="profile_sub"
					:style="isBlock ? 'display: block' : 'display: none'"
				>
					<li id="mypage_li" @click="getMypage()">마이페이지</li>
					<li @click="logout()">로그아웃</li>
				</ul>
			</li>
			<li class="nav_menu">
				<router-link to="/newsfeed">
					<img class="nav_menu_img" src="../assets/icon/news.png" />
				</router-link>
			</li>
			<li class="nav_menu">
				<router-link to="/newpage">
					<img class="nav_menu_img" src="../assets/icon/plus.png" />
				</router-link>
			</li>
			<li class="nav_menu">
				<router-link to="/search">
					<img class="nav_menu_img" src="../assets/icon/search.png" />
				</router-link>
			</li>
			<li class="nav_menu" @click="alarmIconReset">
				<router-link to="/alarm">
					<img
						v-if="alarmIcon"
						class="nav_menu_img"
						src="../assets/icon/alarm.png"
						@click="goAlarm()"
					/>
					<img
						v-if="followIcon"
						class="nav_menu_img image blinking"
						src="../assets/icon/follow.png"
						@click="goAlarm()"
					/>
					<img
						v-if="likeIcon"
						class="nav_menu_img image blinking"
						src="../assets/icon/like.png"
						@click="goAlarm()"
					/>
					<img
						v-if="commentIcon"
						class="nav_menu_img image blinking"
						src="../assets/icon/comment.png"
						@click="goAlarm()"
					/>
				</router-link>
			</li>
		</ul>

		<div id="chat_btn"><span @click="toggleMenu">Go to Chat !</span></div>
		<Room
			id="openRoom"
			v-if="!isHidden"
			v-bind:toChild="toChild"
			v-on:updateIsHiddenDetail="updateIsHiddenDetail"
		></Room>
		<RoomDetail
			id="openDetail"
			v-bind:toChild="toChild"
			v-if="toChild.isHiddenDetail"
			v-on:updateIsHiddenDetail="updateIsHiddenDetail"
		></RoomDetail>
	</div>
</template>

<script>
import router from "../router";
import Room from "@/components/Room";
import RoomDetail from "@/components/RoomDetail";
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";

export default {
	name: "NavBar",
	components: {
		Room,
		RoomDetail
	},
	props: {
		userProfile: {}
	},
	data() {
		return {
			toChild: {
				isHiddenDetail: false,
				left: 0,
				top: 0
			},
			isRoom: false,
			isHidden: true,
			preUrl: "",
			isfirst: true,
			timerID: 0,
			latest_alarm_id: 0,
			soloconnected: false,
			solo_send_message: "",
			div_L: 0,
			div_T: 0,
			targetObj: null,
			alarmIcon: true,
			followIcon: false,
			likeIcon: false,
			commentIcon: false,
			isBlock: false
		};
	},

	methods: {
		goAlarm() {
			let location = "/alarm";

			if (this.$route.fullPath != location) {
				router.push(location).catch(err => {
					console.log(err);
				});
			} else {
				window.location.reload(true);
			}
		},
		alarmIconReset() {
			this.alarmIcon = true;
			this.followIcon = false;
			this.likeIcon = false;
			this.commentIcon = false;
		},
		logout() {
			this.isBlock = false;
			this.$session.destroy();
			router.push("/");
		},
		getLeft() {
			return parseInt(this.targetObj[0].style.left.replace("px", ""));
		},
		getTop() {
			return parseInt(this.targetObj[0].style.top.replace("px", ""));
		},
		// moveDrag(e) {
		// 	var e_obj = window.event ? window.event : e;
		// 	this.div_L = e_obj.clientX;
		// 	this.div_T = e_obj.clientY;
		// 	if (window.innerWidth - 50 < e_obj.clientX)
		// 		e_obj.clientX = window.innerWidth - 50;
		// 	if (window.innerHeight - 50 < e_obj.clientY)
		// 		e_obj.clientY = window.innerHeight - 50;
		// 	if (e_obj.clientY < 0) e_obj.clientY = 0;
		// 	document.getElementsByClassName("nav_menu_drag")[0].style.left =
		// 		e_obj.clientX + "px";
		// 	document.getElementsByClassName("nav_menu_drag")[0].style.top =
		// 		e_obj.clientY + "px";
		// 	return false;
		// },
		// stopDrag() {
		// 	document.onmousemove = null;
		// 	document.onmouseup = null;
		// },
		// startDrag(e) {
		// 	this.targetObj = document.getElementsByClassName("nav_menu_drag");
		// 	var e_obj = window.event ? window.event : e;
		// 	this.div_L = e_obj.clientX;
		// 	this.div_T = e_obj.clientY;
		// 	this.toChild.left = e_obj.clientX;
		// 	this.toChild.top = e_obj.clientY;
		// 	document.onmousemove = this.moveDrag;
		// 	document.onmouseup = this.stopDrag;
		// 	if (e_obj.preventDefault) e_obj.preventDefault();
		// },
		updateIsHiddenDetail(value) {
			if (value.flag) {
				this.isHidden = value.value;
			} else {
				this.toChild.isHiddenDetail = value.value;
			}
		},
		alarm() {
			setInterval(this.solosend, 5000);
		},
		solosend() {
			console.log("Send message:" + this.solo_send_message);
			if (this.stompClient && this.stompClient.connected) {
				const msg = { memberId: this.solo_send_message };
				console.log(JSON.stringify(msg));
				this.stompClient.send("/app/info", JSON.stringify(msg), {});
			}
		},
		soloconnect() {
			this.socket = new SockJS(
				"http://52.79.118.55:8081/gs-guide-websocket"
			);
			this.stompClient = Stomp.over(this.socket);
			this.stompClient.connect(
				{},
				frame => {
					this.soloconnected = true;
					console.log(frame);
					this.stompClient.subscribe("/user/queue/info", tick => {
						console.log(JSON.parse(tick.body).idalarm);
						if (
							this.latest_alarm_id <
								JSON.parse(tick.body).idalarm &&
							JSON.parse(tick.body).memberReceiver !=
								JSON.parse(tick.body).memberCaller
						) {
							if (!this.isfirst) {
								if (
									JSON.parse(tick.body).postId > 0 &&
									JSON.parse(tick.body).likeId > 0 &&
									JSON.parse(tick.body).followId == 0
								) {
									this.$notify({
										group: "foo",
										title: "Important message",
										text:
											JSON.parse(tick.body).nickname +
											" 님이 포스트에 좋아요를 눌렀어요~"
									});
									this.alarmIcon = false;
									this.likeIcon = true;
									this.followIcon = false;
									this.commentIcon = false;
								} else if (
									JSON.parse(tick.body).postId > 0 &&
									JSON.parse(tick.body).likeId == 0 &&
									JSON.parse(tick.body).followId == 0
								) {
									this.$notify({
										group: "foo",
										title: "Important message",
										text:
											JSON.parse(tick.body).nickname +
											" 님이 포스트에 댓글을 달았어요~"
									});
									this.alarmIcon = false;
									this.likeIcon = false;
									this.followIcon = false;
									this.commentIcon = true;
								} else if (
									JSON.parse(tick.body).postId == 0 &&
									JSON.parse(tick.body).likeId == 0 &&
									JSON.parse(tick.body).followId > 0
								) {
									this.$notify({
										group: "foo",
										title: "Important message",
										text:
											JSON.parse(tick.body).nickname +
											" 님이 팔로우를 했어요~"
									});
									this.alarmIcon = false;
									this.likeIcon = false;
									this.followIcon = true;
									this.commentIcon = false;
								}
								//this.solo_received_messages.push(JSON.parse(tick.body));
							}
							this.isfirst = false;
						}
						this.latest_alarm_id = JSON.parse(tick.body).idalarm;
					});
				},
				error => {
					console.log(error);
					this.soloconnected = false;
				}
			);
		},
		disconnect() {
			if (this.stompClient) {
				this.stompClient.disconnect();
			}
			clearInterval(this.timerID);
			this.soloconnected = false;
		},
		tickleConnection() {
			this.soloconnected ? this.disconnect() : this.connect();
		},
		toggleMenu() {
			this.isHidden = !this.isHidden;
		},
		getMypage() {
			this.isBlock = false;
			console.log(this.$route.fullPath);
			let location = "/mypage/" + this.$session.get("id");

			if (this.$route.fullPath != location) {
				router.push(location).catch(err => {
					console.log(err);
				});
			} else {
				window.location.reload(true);
			}
		}
	},
	watch: {
		userProfile: function() {
			console.log("힝구" + this.userProfile);
		}
	},
	mounted() {
		this.solo_send_message = this.$session.get("id");
		this.soloconnect();
		this.alarm();

		// let sessionProfile = this.$session.get("imageUrl");

		// sessionProfile == undefined || sessionProfile == ""
		// 	? (this.userProfile = "../img/icons/user.png")
		// 	: (this.userProfile = sessionProfile);

		// console.log("프으로필" + sessionProfile);
	},

	destroyed() {
		this.disconnect();
	}
};
</script>

<style>
@import url(https://fonts.googleapis.com/css?family=Roboto:300);
@import url("https://fonts.googleapis.com/css?family=Fugaz+One&display=swap");

.back {
	margin: 1em auto;
	/* font-family: "Roboto"; */
}
#chat_btn {
	cursor: pointer;
	font-family: "Fugaz One";
	position: fixed;
	top: 100px;
	right: -125px;
	padding: 0 10px 0 10px;
	line-height: 50px;
	font-size: 20px;
	text-align: center;
	border-radius: 10px 0 0 10px;
	background-color: rgba(160, 23, 98, 0.7);
	transition: all 0.25s;
}

#chat_btn > span {
	font-family: "Fugaz One";
}

#chat_btn:hover {
	right: 0;
}

.nav_menu_drag {
	height: 50px;
	width: 50px;
	position: fixed;
	left: 100px;
	top: 100px;
	cursor: hand;
	padding: 8px;
	border-radius: 50%;
	background: rgba(160, 23, 98, 0.7);
	box-shadow: 2px 2px 5px rgba(0, 0, 0, 0.3);
	animation: jumb 2s infinite;
}

.vue-notification {
	background-color: #7d4879;
}

.openRoom {
	position: fixed;
	margin: 0 auto;
	/* left: 0; */
	right: 0;
	/* top: 100px; */
	height: 100%;
	width: calc(100% - 60px);
	margin: 10px;
}
.openDetail {
	z-index: 200;
	position: fixed;
	top: 0;
	right: 0;
	height: 100%;
	width: calc(100% - 60px);
}
#navbar {
	position: fixed;
	top: 0;
	height: 100%;
	z-index: 100;
	color: white;
	background-color: #7d4879;
	width: 60px;
	font-size: 12px;
	padding-top: 30px;
	box-shadow: 0.1px 0 5px 0.2px black;
}

#navbar > ul {
	list-style: none;
	padding: 0;
}

#profile {
	border-radius: 25px;
	margin-bottom: 20px;
	width: 40px;
	height: 40px;
	cursor: pointer;
}

#navbar li ul {
	list-style: none;
	display: none;
	background-color: rgb(243, 230, 250);
	position: fixed;
	color: rgba(0, 0, 0, 0.7);
	margin: 0px;
	padding: 0px;
	z-index: 200;
	left: 55px;
	top: 30px;
	/* border-radius: 10px; */
	font-size: 15px;
	box-shadow: 0.2px 0.2px 3px 0.5px rgba(0, 0, 0, 0.3);
}
#navbar li ul li {
	padding: 10px;
	cursor: pointer;
}
#navbar li ul li:hover {
	background-color: rgb(168, 136, 185);
	color: white;
}
#mypage_li {
	border-bottom: 1px solid silver;
}

#navbar > ul > li {
	text-align: center;
}

.nav_menu {
	height: 50px;
}

.nav_menu_img {
	width: 25px;
	opacity: 0.5;
	transition: 0.4s;
}

.nav_menu_img:hover {
	opacity: 1;
}

@media screen and (max-width: 600px) {
	#navbar {
		top: auto;
		bottom: 0;
		height: 17vw;
		width: 100%;
		padding: 0;
	}

	#profile {
		width: 35px;
		height: 35px;
		margin-bottom: 0;
	}

	#navbar > ul {
		text-align: center;
	}

	#navbar > ul > li {
		display: inline-block;
		margin: 4vw 5vw 0 5vw;
	}

	.nav_menu_img {
		width: 35px;
		padding: 3px;
	}
	#navbar li ul {
		top: auto;
		bottom: 17vw;
		left: 10vw;
	}

	.openRoom {
		width: 100%;
		left: -10px;
		height: calc(100% - 17vw);
	}

	.openDetail {
		width: 100%;
		height: calc(100% - 17vw);
	}
}
@media screen and (max-width: 330px) {
	#profile {
		cursor: pointer;
		width: 30px;
		height: 30px;
	}
	.nav_menu_img {
		width: 30px;
		padding: 3px;
	}
	#navbar > ul > li {
		display: inline-block;
		margin-top: 3.5vw;
	}
}
</style>
