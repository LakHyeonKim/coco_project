<template>
	<div class="openRoom">
		<div id="roomWrap">
			<div id="roomList">
				<div id="roomHeader">
					CHAT ROOM
				</div>
				<div id="chat_input_div">
					<input
						id="inputStyle"
						type="text"
						v-model="room_name"
						@keyup.enter="createRoom"
					/>
					<button id="room_btn" @click="createRoom">
						채팅방 개설
					</button>
					<input
						id="inputStyle"
						type="text"
						v-model="search_room_name"
						@keyup.enter="searchRoom"
					/>
					<button id="room_btn" @click="searchRoom">
						채팅방 검색
					</button>
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
								<button
									id="buttonStyle"
									@click="deleteRoom(item.idroom)"
									v-if="item.memberId == senderStatic"
									style="margin-left: 5px;"
								>
									삭제
								</button>
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
		var standardWidth = window.innerWidth / 2;
		var standardHeight = window.innerHeight / 2;
		if (
			standardWidth > this.toChild.left &&
			standardHeight > this.toChild.top
		) {
			document.getElementsByClassName("openRoom")[0].style.left =
				this.toChild.left + "px";
			document.getElementsByClassName("openRoom")[0].style.top =
				this.toChild.top - 270 + "px";
		} else if (
			standardWidth <= this.toChild.left &&
			standardHeight <= this.toChild.top
		) {
			document.getElementsByClassName("openRoom")[0].style.left =
				this.toChild.left - 500 + "px";
			document.getElementsByClassName("openRoom")[0].style.top =
				this.toChild.top - 670 + "px";
		} else if (
			standardWidth <= this.toChild.left &&
			standardHeight > this.toChild.top
		) {
			document.getElementsByClassName("openRoom")[0].style.left =
				this.toChild.left - 500 + "px";
			document.getElementsByClassName("openRoom")[0].style.top =
				this.toChild.top - 270 + "px";
		} else if (
			standardWidth > this.toChild.left &&
			standardHeight <= this.toChild.top
		) {
			document.getElementsByClassName("openRoom")[0].style.left =
				this.toChild.left + "px";
			document.getElementsByClassName("openRoom")[0].style.top =
				this.toChild.top - 670 + "px";
		}
	},
	methods: {
		findAllRoom: function() {
			alarmHttp.get("/chat/rooms").then(response => {
				console.log("findAllRoom");
				console.log(response);
				this.chatrooms = response.data;
			});
		},
		searchRoom: function() {},
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
					this.$emit("updateIsHiddenDetail", false);
				})
				.catch(response => {
					console.log(response);
					alert("채팅방 삭제를 실패하였습니다.");
				});
		},
		enterRoom: function(item) {
			localStorage.setItem("wschat.member_id", this.senderStatic);
			localStorage.setItem("wschat.idroom", item.idroom);
			//console.log(this.isHidden)
			this.$emit("updateIsHiddenDetail", !this.isHidden);
			// console.log(this.isHidden)
			this.$emit("updateIsHiddenDetail", false);
			setTimeout(() => {
				this.$emit("updateIsHiddenDetail", true);
			}, 1);
		}
	}
};
</script>

<style>
#roomWrap {
	width: 100%;
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

#chat_input_div {
	margin: 10px;
}

#inputStyle {
	color: black;
	border: 1px solid silver;
	outline: none;
	margin: 3px;
}

#room_btn {
	border: 1px solid rgba(160, 23, 98, 0.9);
	color: rgba(160, 23, 98, 0.9);
	padding: 0 3px 0 3px;
	border-radius: 5px;
}
#room_btn:hover {
	background-color: rgba(160, 23, 98, 0.7);
	color: white;
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

.roomEl.active {
	/* background: #7d4879; */
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

#roomHeader {
	background-color: rgba(160, 23, 98, 0.5);
	color: rgb(255, 255, 255);
	height: 40px;
	font-size: 18px;
	line-height: 40px;
	text-align: center;
	border-radius: 5px;
}
</style>
