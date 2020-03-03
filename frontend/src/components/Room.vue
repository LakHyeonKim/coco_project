<template>
	<div class="openRoom">
		<div id="roomWrap">
			<div id="roomList">
				<div id="roomHeader">
					<span>CHAT ROOM</span>
					<img
						id="room_exit_btn"
						@click="clossChatRoom"
						src="../assets/icon/close_w.png"
					/>
					<img
						id="room_refresh_btn"
						@click="findAllRoom"
						src="../assets/icon/refresh.png"
					/>
				</div>
				<div id="chat_input_div">
					<div style="float: left;">
						<input
							id="chat_text"
							type="text"
							v-model="room_name"
							@keyup.enter="createRoom"
							placeholder="채팅방명"
							maxlength="15"
						/>
						<img
							id="makeRoom_btn"
							@click="createRoom"
							src="../assets/icon/plus.png"
						/>
					</div>
					<div style="float: right;">
						<input
							id="chat_text"
							type="text"
							v-model="search_room_name"
							@keyup="searchRoom"
						/>
						<button id="chatSearch_btn" @click="searchRoom">
							search
						</button>
					</div>
				</div>
				<div id="roomSelect">
					<div
						class="roomEl active"
						v-for="item in chatrooms"
						v-bind:key="item.idroom"
						@click="enterRoom(item)"
					>
						<div id="room_chat_date">
							{{ item.dateCreated }}
						</div>
						<div id="room_chat">
							<div v-if="item.idroom != 0">
								<div id="room_chat_num">
									{{ item.idroom }}
								</div>
								<div id="room_chat_title">
									{{ item.roomName }}
								</div>
							</div>
							<div id="room_chat_btn">
								<!-- <button id="buttonStyle">
									채팅방 입장
								</button> -->
								<!-- <button
									id="chat_delete"
									@click="deleteRoom(item.idroom)"
									v-if="item.memberId == senderStatic"
								>
									X
								</button> -->
								<img
									id="chat_delete"
									@click="deleteRoom(item.idroom)"
									v-if="item.memberId == senderStatic"
									src="../assets/icon/cross.png"
								/>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
import alarmHttp from "../http-alarm";

export default {
	name: "Room",
	props: ["toChild"],
	data() {
		return {
			senderStatic: 0,
			isHidden: this.toChild.isHiddenDetail,
			room_name: "",
			search_room_name: "",
			chatrooms: []
		};
	},
	created() {
		this.findAllRoom();
		this.senderStatic = this.$session.get("id");
	},
	mounted() {
		// var standardWidth = window.innerWidth / 2;
		// var standardHeight = window.innerHeight / 2;
		// if (
		// 	standardWidth > this.toChild.left &&
		// 	standardHeight > this.toChild.top
		// ) {
		// 	document.getElementsByClassName("openRoom")[0].style.left =
		// 		this.toChild.left + "px";
		// 	document.getElementsByClassName("openRoom")[0].style.top =
		// 		this.toChild.top - 270 + "px";
		// } else if (
		// 	standardWidth <= this.toChild.left &&
		// 	standardHeight <= this.toChild.top
		// ) {
		// 	document.getElementsByClassName("openRoom")[0].style.left =
		// 		this.toChild.left - 500 + "px";
		// 	document.getElementsByClassName("openRoom")[0].style.top =
		// 		this.toChild.top - 670 + "px";
		// } else if (
		// 	standardWidth <= this.toChild.left &&
		// 	standardHeight > this.toChild.top
		// ) {
		// 	document.getElementsByClassName("openRoom")[0].style.left =
		// 		this.toChild.left - 500 + "px";
		// 	document.getElementsByClassName("openRoom")[0].style.top =
		// 		this.toChild.top - 270 + "px";
		// } else if (
		// 	standardWidth > this.toChild.left &&
		// 	standardHeight <= this.toChild.top
		// ) {
		// 	document.getElementsByClassName("openRoom")[0].style.left =
		// 		this.toChild.left + "px";
		// 	document.getElementsByClassName("openRoom")[0].style.top =
		// 		this.toChild.top - 670 + "px";
		// }
	},
	methods: {
		clossChatRoom: function() {
			this.$emit("updateIsHiddenDetail", { flag: true, value: true });
		},
		findAllRoom: function() {
			alarmHttp.get("/chat/rooms").then(response => {
				console.log("findAllRoom");
				console.log(response);
				this.chatrooms = response.data;
			});
		},
		searchRoom: function() {
			if (this.search_room_name == "") {
				this.findAllRoom();
			} else {
				alarmHttp
					.get("/chat/search/" + encodeURI(this.search_room_name))
					.then(response => {
						console.log(response);
						this.chatrooms = response.data;
					});
			}
		},
		createRoom: function() {
			if ("" === this.room_name) {
				alert("방 제목을 입력해 주십시요.");
				return;
			} else {
				alarmHttp
					.post("/chat/room", {
						roomName: this.room_name,
						memberId: this.$session.get("id")
					})
					.then(response => {
						console.log(response);
						alert(
							response.data.roomName + "방 개설에 성공하였습니다."
						);
						this.room_name = "";
						this.findAllRoom();
					})
					.catch(response => {
						console.log(response);
						alert("채팅방 개설에 실패하였습니다.");
					});
			}
		},
		deleteRoom: function(idroom) {
			alarmHttp
				.delete("/chat/room/" + idroom)
				.then(response => {
					console.log(response);
					alert("방 삭제를 성공하였습니다.");
					this.findAllRoom();
					this.$emit("updateIsHiddenDetail", {
						flag: false,
						value: false
					});
				})
				.catch(response => {
					console.log(response);
					alert("채팅방 삭제를 실패하였습니다.");
				});
		},
		enterRoom: function(item) {
			localStorage.setItem("wschat.member_id", this.senderStatic);
			localStorage.setItem("wschat.idroom", item.idroom);
			localStorage.setItem("wschat.memberCount", item.memberCount);
			//console.log(this.isHidden)
			this.$emit("updateIsHiddenDetail", {
				flag: false,
				value: !this.isHidden
			});
			// console.log(this.isHidden)
			this.$emit("updateIsHiddenDetail", { flag: false, value: false });
			setTimeout(() => {
				this.$emit("updateIsHiddenDetail", {
					flag: false,
					value: true
				});
			}, 1);
		}
	}
};
</script>

<style>
#roomWrap {
	width: 100%;
}

#roomHeader {
	background-color: rgba(160, 23, 98, 0.5);
	color: rgb(255, 255, 255);
	height: 40px;
	font-size: 18px;
	line-height: 40px;
	text-align: center;
	border-radius: 5px;
}

#room_refresh_btn {
	position: absolute;
	margin: 10px 0 10px 5px;
	/* right: 40px; */
	width: 20px;
	height: 20px;
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

#room_refresh_btn:hover {
	-webkit-transform: scale(1.1);
	-moz-transform: scale(1.1);
	-ms-transform: scale(1.1);
	-o-transform: scale(1.1);
	transform: scale(1.1);
}

#room_exit_btn {
	position: absolute;
	margin: 8px;
	right: 10px;
	width: 25px;
	height: 25px;
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

#room_exit_btn:hover {
	-webkit-transform: scale(1.1);
	-moz-transform: scale(1.1);
	-ms-transform: scale(1.1);
	-o-transform: scale(1.1);
	transform: scale(1.1);
}
.openRoom {
	padding: 10px;
	float: left;
	background-color: white;
	position: relative;
	left: 50px;
	top: -10px;
	width: 500px;
	height: 800%;
	padding: 10px;
	box-shadow: 0.1px 0.1px 5px 0.15px rgba(0, 0, 0, 0.267);
	border-radius: 15px;
	overflow: auto;
	color: black;
}

@media screen and (max-width: 400px) {
	#room_exit_btn,
	#room_refresh_btn {
		transition: none;
	}

	#room_exit_btn:hover,
	#room_refresh_btn:hover {
		transform: none;
	}
}

#chat_input_div {
	margin: 10px;
	display: inline-block;
	width: 95%;
	text-align: right;
}

#chat_text {
	color: black;
	border-bottom: 1.45px solid gray;
	outline: none;
	margin: 3px;
	height: 25px;
	width: 15vw;
	-webkit-transition: width 0.4s ease-in-out;
	transition: width 0.4s ease-in-out;
}

#chat_text:focus {
	outline: none;
	width: 30vw;
}

#chat_text::placeholder {
	font-size: 15px;
}

#makeRoom_btn {
	position: absolute;
	cursor: pointer;
	color: white;
	background-color: rgba(160, 23, 98, 0.9);
	width: 25px;
	padding: 5px;
	border-radius: 5px;
	margin-left: 3px;
	margin-top: 2px;
}

#makeRoom_btn:hover {
	background-color: rgba(160, 23, 98, 0.2);
}

#chatSearch_btn {
	background-color: rgba(160, 23, 98, 0.9);
	color: white;
	padding: 0 5px 0 5px;
	height: 25px;
	font-size: 15px;
	border-radius: 5px;
}

#chatSearch_btn:hover {
	background-color: rgba(160, 23, 98, 0.2);
}

.roomEl {
	text-align: center;
	border-bottom: 1px solid #ddd;
	padding: 10px 0px;
	cursor: pointer;
	width: 100%;
	padding: 10px;
}

.roomEl:hover {
	box-shadow: 2px 2px 5px rgba(0, 0, 0, 0.2);
}

#room_chat {
	display: inline-block;
	width: 100%;
}

#room_chat_num {
	color: white;
	background-color: rgba(160, 23, 98, 0.5);
	float: left;
	width: 25px;
	height: 25px;
	padding: 4px;
	border-radius: 50%;
	margin-right: 10px;
}

#room_chat_title {
	float: left;
	font-size: 15px;
	white-space: nowrap;
	overflow: hidden;
	text-overflow: ellipsis;
	width: 80%;
	line-height: 25px;
}

#chat_delete {
	width: 15px;
}

#room_chat_date {
	margin: 5px;
	color: gray;
}

#room_chat_btn {
	float: right;
	display: block;
	margin: 5px;
}

#roomList {
	border-radius: 10px;
}

#roomSelect {
	border-radius: 10px;
}
</style>
