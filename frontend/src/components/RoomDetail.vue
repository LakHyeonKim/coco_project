<template>
	<div id="chatWrap">
		<div id="chatHeader">{{ room.roomName }}</div>
		<div id="chatRoom">
			<div id="chatLog" v-for="message in messages" :key="message">
				<div class="anotherMsg" v-if="message.member_id != member_id">
					<span class="anotherName">{{ message.member_id }}</span>
					<span class="msg">{{ message.context }}</span>
				</div>
				<div class="myMsg" v-if="message.member_id == member_id">
					<span class="anotherName">{{ message.member_id }}</span>
					<span class="msg">{{ message.context }}</span>
				</div>
			</div>
		</div>
		<input
			type="text"
			autocomplete="off"
			size="30"
			id="message"
			v-model="message"
			@keyup.enter="sendMessage"
			placeholder="메시지를 입력하세요"
		/>
		<button id="buttonStyle" type="button" @click="sendMessage">
			보내기
		</button>
	</div>
</template>

<script>
import sock from 'sockjs-client'
import ws from 'webstomp-client'
import axios from 'axios'

export default {
	name: 'RoomDetail',
	data () {
		return {
			roomId: 0,
			roomNumber:'',
			room: {},
			member_id: 0,
			message: '',
			messages: []
		}
	},
	created () {
		this.roomId = localStorage.getItem('wschat.idroom')
		this.member_id = localStorage.getItem('wschat.member_id')
		this.roomNumber = localStorage.getItem('wschat.roomNumber')
		this.findRoom()
		this.connect()
	},
	methods: {
		findRoom: function () {
			axios
				.get('http://localhost:8081/chat/room/' + this.roomId)
				.then(response => {
					this.room = response.data
					console.log(this.room)
				})
			axios
				.get('http://localhost:8081/chat/messages/' + this.roomId)
				.then(response => {
					this.messages = response.data
					console.log(this.messages)
				})
		},
		sendMessage: function () {
			this.stompClient.send(
				'/app/chat/message',
				JSON.stringify(
					{
						type: 'TALK',
						roomId: this.roomId,
						memberId: this.member_id,
						context: this.message
					},
					{}
				)
			)
			this.message = ''
		},
		recvMessage: function (recv) {
			this.messages.push({
				type: recv.type,
				memberId: recv.type == 'ENTER' ? '[알림]' : recv.memberId,
				context: recv.context,
				dateCreated: recv.dateCreated
			})
		},
		connect: function () {
			this.socket = new sock('http://localhost:8081/gs-guide-websocket')
			this.stompClient = ws.over(this.socket)
			this.stompClient.connect(
				{},
				frame => {
					console.log(frame)
					this.stompClient.subscribe(
						'/sub/chat/room/' + this.$data.idroom,
						message => {
							var recv = JSON.parse(message.body)
							this.recvMessage(recv)
						}
					)
					this.stompClient.send(
						'/app/chat/message',
						JSON.stringify(
							{
								type: 'ENTER',
								roomId: this.$data.roomId,
								memberId: this.$data.member_id
							},
							{}
						)
					)
				},
				error => {
					alert('error ' + error)
				}
			)
		}
	}
}
</script>

<style>
#chatWrap {
	width: 100%;
	border: 1px solid #ddd;
}

#chatHeader {
	height: 60px;
	color: black;
	text-align: center;
	line-height: 60px;
	font-size: 25px;
	font-weight: 900;
	border-bottom: 1px solid #ddd;
}

#chatLog {
	height: 100px;
	overflow-y: auto;
	padding: 10px;
}

.myMsg {
	text-align: right;
}

#buttonStyle {
	border: 1px solid black;
	background: #7d4879;
}

.anotherMsg {
	text-align: left;
	margin-bottom: 5px;
}

.msg {
	display: inline-block;
	border-radius: 15px;
	padding: 7px 15px;
	margin-bottom: 10px;
	margin-top: 5px;
}

.anotherMsg > .msg {
	background-color: #f1f0f0;
}

.myMsg > .msg {
	background-color: #0084ff;
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
	width: 85%;
	height: calc(100% - 1px);
	border: none;
	padding-bottom: 0;
	color: #000;
	border: 1px solid black;
}

#message:focus {
	outline: none;
}

#chatForm > input[type='submit'] {
	outline: none;
	border: none;
	background: none;
	color: #0084ff;
	font-size: 17px;
}

#chatRoom {
	overflow: auto;
	height: 350px;
	color: #000;
}
</style>
