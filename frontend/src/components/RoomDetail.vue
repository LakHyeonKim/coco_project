<template>
	<div class="openDetail">
		<div id="chatWrap">
			<div id="chatHeader">
				<span id="chat_detail_title">
					{{ room.roomName }}
					<span id="chat_detail_cnt">({{ memberCount }})</span>
				</span>
				<img
					id="chat_exit_btn"
					@click="outChatRoom"
					src="../assets/icon/close_b.png"
				/>
			</div>
			<div id="chatRoom">
				<div
					id="loading"
					:style="loadingTop ? loadingStyleOn : loadingStyleOff"
					v-if="loadingTop"
				>
					<div class="loader"></div>
				</div>
				<div v-if="!loadingTop">
					<div id="chatLog" v-for="(message, i) in messages" :key="i">
						<div class="noticMsg" v-if="message.type == 'ENTER'">
							<!-- {{ message.dateCreated }} -->
							<span class="msg">{{ message.context }}</span>
						</div>
						<div class="noticMsg" v-if="message.type == 'OUT'">
							<!-- {{ message.dateCreated }} -->
							<span class="msg">{{ message.context }}</span>
						</div>
						<div
							class="anotherMsg"
							v-if="
								message.memberId != memberId &&
									message.type == 'TALK'
							"
						>
							<span class="anotherName">{{
								message.nickName
							}}</span>
							<span class="msg">{{ message.context }}</span>
							{{ message.dateCreated }}
						</div>
						<div
							class="myMsg"
							v-if="
								message.memberId == memberId &&
									message.type == 'TALK'
							"
						>
							<span class="anotherName">{{
								message.nickName
							}}</span>
							{{ message.dateCreated }}
							<span class="msg">{{ message.context }}</span>
						</div>
					</div>
				</div>
			</div>
			<div id="send_div">
				<input
					type="text"
					autocomplete="off"
					id="message"
					v-model="message"
					@keyup.enter="sendMessage"
					placeholder="메시지를 입력하세요"
				/>
				<button id="chat_send_btn" @click="sendMessage">
					SEND
				</button>
			</div>
		</div>
	</div>
</template>

<script>
import sock from "sockjs-client";
import ws from "webstomp-client";
import alarmHttp from "../http-alarm";

export default {
	name: "RoomDetail",
	props: ["toChild"],
	data() {
		return {
			roomId: 0,
			memberId: 0,
			nickName: "",
			memberCount: 0,
			room: {},
			message: "",
			messages: [],
			isHidden: this.toChild.isHiddenDetail,
			loadingTop: false,
			loadingStyleOn: {
				display: "grid"
			},
			loadingStyleOff: {
				display: "none"
			}
		};
	},
	created() {
		this.roomId = localStorage.getItem("wschat.idroom");
		this.memberId = localStorage.getItem("wschat.member_id");
		this.nickName = this.$session.get("nickName");
		this.findRoom();
	},
	mounted() {
		this.loadingTop = true;
		// var standardWidth = window.innerWidth / 2;
		// var standardHeight = window.innerHeight / 2;
		// if (
		// 	standardWidth > this.toChild.left &&
		// 	standardHeight > this.toChild.top
		// ) {
		// 	document.getElementsByClassName("openDetail")[0].style.left =
		// 		this.toChild.left + 510 + "px";
		// 	document.getElementsByClassName("openDetail")[0].style.top =
		// 		this.toChild.top - 670 + "px";
		// } else if (
		// 	standardWidth <= this.toChild.left &&
		// 	standardHeight <= this.toChild.top
		// ) {
		// 	document.getElementsByClassName("openDetail")[0].style.left =
		// 		this.toChild.left - 910 + "px";
		// 	document.getElementsByClassName("openDetail")[0].style.top =
		// 		this.toChild.top - 1070 + "px";
		// } else if (
		// 	standardWidth <= this.toChild.left &&
		// 	standardHeight > this.toChild.top
		// ) {
		// 	document.getElementsByClassName("openDetail")[0].style.left =
		// 		this.toChild.left - 910 + "px";
		// 	document.getElementsByClassName("openDetail")[0].style.top =
		// 		this.toChild.top - 670 + "px";
		// } else if (
		// 	standardWidth > this.toChild.left &&
		// 	standardHeight <= this.toChild.top
		// ) {
		// 	document.getElementsByClassName("openDetail")[0].style.left =
		// 		this.toChild.left + 510 + "px";
		// 	document.getElementsByClassName("openDetail")[0].style.top =
		// 		this.toChild.top - 1070 + "px";
		// }
	},
	methods: {
		outChatRoom: function() {
			alarmHttp
				.post("/chat/out", {
					roomId: this.roomId,
					memberId: this.memberId,
					nickName: this.nickName
				})
				.then(response => {
					this.messages = response.data;
					console.log(this.messages);
					this.messages = [];
					this.$emit("updateIsHiddenDetail", !this.isHidden);
					this.outMessage();
				});
		},
		enterList: function() {},
		findRoom: function() {
			alarmHttp.get("/chat/room/" + this.roomId).then(response => {
				this.room = response.data;
				console.log(this.room);
				alarmHttp
					.post("/chat/messages", {
						roomId: this.roomId,
						memberId: this.memberId,
						nickName: this.nickName
					})
					.then(response => {
						this.messages = response.data;
						console.log(this.messages);
						this.connect();
					});
			});
		},
		outMessage: function() {
			this.stompClient.send(
				"/app/chat/message",
				JSON.stringify(
					{
						type: "OUT",
						memberId: this.$data.memberId,
						roomId: this.$data.roomId,
						nickName: this.nickName
					},
					{}
				)
			);
			this.message = "";
		},
		sendMessage: function() {
			if(this.message == "") return;
			this.stompClient.send(
				"/app/chat/message",
				JSON.stringify(
					{
						type: "TALK",
						roomId: this.roomId,
						memberId: this.memberId,
						nickName: this.nickName,
						context: this.message
					},
					{}
				)
			);
			this.message = "";
		},
		recvMessage: function(recv) {
			this.messages.push({
				idmessage: recv.idmessage,
				roomId: recv.roomId,
				memberId: recv.type == "ENTER" ? "[알림]" : recv.memberId,
				nickName: recv.nickName,
				dateCreated: recv.dateCreated,
				context: recv.context,
				type: recv.type
			});
			this.memberCount = recv.memberCount;
		},
		connect: function() {
			this.socket = new sock(
				"http://52.79.118.55:8081/gs-guide-websocket"
			);
			this.stompClient = ws.over(this.socket);
			this.stompClient.connect(
				{},
				frame => {
					console.log(frame);
					this.stompClient.subscribe(
						"/sub/chat/room/" + this.$data.roomId,
						message => {
							var recv = JSON.parse(message.body);
							this.recvMessage(recv);
							setTimeout(this.chatOnScroll, 250);
							this.loadingTop = false;
						}
					);
					this.stompClient.send(
						"/app/chat/message",
						JSON.stringify(
							{
								type: "ENTER",
								memberId: this.$data.memberId,
								roomId: this.$data.roomId,
								nickName: this.nickName
							},
							{}
						)
					);
				},
				error => {
					alert("error " + error);
				}
			);
		},
		chatOnScroll: function() {
			var objDiv = document.getElementById("chatRoom");
			objDiv.scrollTop = objDiv.scrollHeight;
			this.loadingTop = false;
		}
	}
};
</script>

<style>
#loading {
	display: none;
	width: 100%;
	margin: 20px auto 100px auto;
	/* display: grid; */
	justify-content: center;
}
.loader {
	/* margin: 20px auto 20px auto; */
	border: 6px solid #f3f3f3; /* Light grey */
	border-top: 6px solid gray; /* Blue */
	border-radius: 50%;
	width: 60px;
	height: 60px;
	animation: spin 2s linear infinite;
}

.openDetail {
	/* float: left; */
	background-color: white;
	/* position: relative; */
	/* left: 560px; */
	/* bottom: 410px; */
	/* width: 400px; */
	/* height: 930%; */
	height: 100%;
	width: 100%;
	padding: 10px;
	box-shadow: 0.1px 0.1px 5px 0.15px rgba(0, 0, 0, 0.267);
	border-radius: 15px;
	overflow: auto;
	color: black;
}

#chatWrap {
	width: 100%;
	height: 100%;
	border: 1px solid #ddd;
	overflow: hidden;
}

#chatHeader {
	width: 100%;
	text-align: center;
	line-height: 40px;
	border-bottom: 1px solid #ddd;
	display: inline-block;
	padding: 10px;
}

#chat_detail_title {
	float: left;
	font-size: 20px;
	font-weight: 400;
	text-align: left;
	width: 90%;
}

#chat_detail_cnt {
	font-size: 15px;
}

#chat_exit_btn {
	float: right;
	width: 25px;
	margin: 7px 0 7px 0;
	cursor: pointer;

	-webkit-transform: scale(1);
	-moz-transform: scale(1);
	-ms-transform: scale(1);
	-o-transform: scale(1);
	transform: scale(1);
	-webkit-transition: 0.2s;
	-moz-transition: 0.2s;
	-ms-transition: 0.2s;
	-o-transition: 0.2s;
	transition: 0.2s;
}

#chat_exit_btn:hover {
	-webkit-transform: scale(1.1);
	-moz-transform: scale(1.1);
	-ms-transform: scale(1.1);
	-o-transform: scale(1.1);
	transform: scale(1.1);
}

#chatLog {
	overflow-y: auto;
}

.myMsg {
	text-align: right;
	padding-right: 10px;
	margin-left: 5px;
}

.anotherMsg {
	text-align: left;
	padding-left: 10px;
	margin-bottom: 5px;
	margin-right: 5px;
}

.noticMsg {
	text-align: center;
	color: gray;
}

.msg {
	display: inline-block;
	border-radius: 15px;
	padding: 7px 15px;
	margin-bottom: 10px;
	margin-top: 5px;
	font-size: 13px;
}

.anotherMsg > .msg {
	background-color: #f1f0f0;
}

.myMsg > .msg {
	background-color: rgba(160, 23, 98, 0.7);
	color: #fff;
}

.anotherName {
	font-size: 12px;
	display: block;
}

#chatForm {
	display: block;
	width: 100%;
	height: 50px;
	border-top: 2px solid #f0f0f0;
}

#message {
	margin: 3px;
	color: #000;
	font-size: 13px;
	font-weight: 400;
	background-color: white;
	width: calc(100% - 100px);
	height: 25px;
}

#message:focus {
	outline: none;
}

#send_div {
	position: absolute;
	bottom: 8px;
	width: calc(100% - 20px);
	display: inline-block;
	padding: 3px;
}

#chat_send_btn {
	float: right;
	background-color: rgb(173, 95, 138);
	color: white;
	padding: 2px 7px 2px 7px;
	border-radius: 3px;
	font-size: 15px;
	margin-right: 3px;
}

#chat_send_btn:hover {
	background-color: rgba(160, 23, 98, 0.7);
}

#chatForm > input[type="submit"] {
	outline: none;
	border: none;
	background: none;
	color: #0084ff;
	font-size: 17px;
}

#chatRoom {
	overflow: auto;
	/* height: 350px; */
	height: 80%;
	color: #000;
}

@media screen and (max-width: 600px) {
	#message {
		width: calc(100% - 60px);
		height: 20px;
	}
	#chat_send_btn {
		font-size: 12px;
	}
}
</style>
