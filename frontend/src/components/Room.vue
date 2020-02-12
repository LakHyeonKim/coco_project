<template>
	<div id="chat">
		<div id="open">
			<div id="roomWrap">
				<input
					type="text"
					v-model="room_name"
					@keyup.enter="createRoom"
				/>
				<button type="button" @click="createRoom">
					채팅방 개설
				</button>
				<div id="roomList">
					<div id="roomHeader">
						채팅 방 목록
					</div>
					<div id="roomSelect">
						<div
							class="roomEl active"
							v-for="item in chatrooms"
							v-bind:key="item.roomId"
							v-on:click="enterRoom(item.roomId)"
						>
							{{ item.name }}
						</div>
					</div>
				</div>
			</div>
			<RoomDetail v-if="isRoomDetail"> </RoomDetail>
		</div>
	</div>
</template>

<script>
import axios from 'axios'
import RoomDetail from '@/components/RoomDetail'

export default {
	name: 'Room',
	data () {
		return {
			isHidden: true,
			isRoomDetail: false,
			room_name: '',
			chatrooms: []
		}
	},
	components: {
		RoomDetail
	},
	created () {
		this.findAllRoom()
	},
	methods: {
		findAllRoom: function () {
			axios.get('http://localhost:8081/chat/rooms').then(response => {
				console.log(response)
				this.chatrooms = response.data
			})
		},
		createRoom: function () {
			if ('' === this.room_name) {
				alert('방 제목을 입력해 주십시요.')
				return
			} else {
				var params = new URLSearchParams()
				params.append('name', this.room_name)
				axios
					.post('http://localhost:8081/chat/room', params)
					.then(response => {
						console.log(response)
						alert(response.data.name + '방 개설에 성공하였습니다.')
						this.room_name = ''
						this.findAllRoom()
					})
					.catch(response => {
						console.log(response)
						alert('채팅방 개설에 실패하였습니다.')
					})
			}
		},
		enterRoom: function (roomId) {
			var sender = prompt('대화명을 입력해 주세요.')
			localStorage.setItem('wschat.sender', sender)
			localStorage.setItem('wschat.roomId', roomId)
			this.isRoomDetail = true
		}
	}
}
</script>

<style>
#roomWrap {
	width: 200px;
}

#chat {
	text-align: left;
	top: 0;
	right: 0;
	z-index: 1;
}

#open {
	background-color: white;
	position: relative;
	left: 50px;
	top: -10px;
	width: 500px;
	height: 700px;
	padding: 10px;
	box-shadow: 0.1px 0.1px 5px 0.15px rgba(0, 0, 0, 0.267);
	border-radius: 15px;
  overflow: auto;
}

#roomList {
	border: 1px solid #0084ff;
	border-radius: 5px;
}

#roomHeader {
	background-color: #0084ff;
	color: rgb(0, 0, 0);
	height: 40px;
	font-size: 18px;
	line-height: 40px;
	text-align: center;
	border-radius: 5px;
}

.roomEl {
	text-align: center;
	border-bottom: 1px solid rgb(228, 16, 217);
	padding: 10px 0px;
	cursor: pointer;
}

.roomEl:hover {
	background: rgb(228, 16, 217);
}

.roomEl.active {
	background: rgb(228, 16, 217);
}
</style>
