<template>
	<div id="open">
		<div id="roomWrap">
			<div id="roomList">
				<div id="roomHeader">
					채팅 방 목록
				</div>
				<div id="roomSelect">
					<div
						class="roomEl active"
						v-for="item in chatrooms"
						v-bind:key="item.idroom"
						v-on:click="enterRoom(item)"
					>
						<p v-if="item.idroom != 0">
							방번호::{{ item.idroom }} - 방제목::
							{{ item.nameName }} - 방장:: {{ item.memberId }}
						</p>
						<!-- <button
							id="buttonStyle"
							type="button"
							@click="deleteRoom"
						>
							채팅방 삭제
						</button> -->
					</div>
				</div>
			</div>
		</div>
		<input
			id="inputStyle"
			type="text"
			v-model="room_name"
			@keyup.enter="createRoom"
		/>
		<button id="buttonStyle" type="button" @click="createRoom">
			채팅방 개설
		</button>
		<RoomDetail v-if="isRoomDetail"> </RoomDetail>
	</div>
</template>

<script>
import axios from 'axios'
import RoomDetail from '@/components/RoomDetail'

export default {
	name: 'Room',
	data () {
		return {
			senderStatic: '',
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
				axios
					.post('http://localhost:8081/chat/room', {
						roomName: this.room_name,
						memberId: this.$session.get('id')
					})
					.then(response => {
						console.log(response)
						alert(
							response.data.roomName + '방 개설에 성공하였습니다.'
						)
						this.room_name = ''
						this.findAllRoom()
					})
					.catch(response => {
						console.log(response)
						alert('채팅방 개설에 실패하였습니다.')
					})
			}
		},
		deleteRoom: function () {},
		enterRoom: function (item) {
			//var sender = prompt('대화명을 입력해 주세요.')
			this.senderStatic = this.$session.get('id')
			localStorage.setItem('wschat.member_id', this.senderStatic)
			localStorage.setItem('wschat.idroom', item.idroom)
			this.isRoomDetail = true
			this.isRoomDetail = false
			setTimeout(() => {
				this.isRoomDetail = true
			}, 1)
		}
	}
}
</script>

<style>
#roomWrap {
	width: 100%;
}

input {
	border-block-color: black;
}

#open {
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
}

#inputStyle {
	color: black;
	border: 1px solid black;
}

#buttonStyle {
	border: 1px solid black;
	background: #7d4879;
}

#roomList {
	border: 1px solid rgb(221, 187, 210);
	border-radius: 5px;
}

#roomSelect {
	border: 1px solid rgb(221, 187, 210);
	border-radius: 5px;
}

#roomHeader {
	background-color: #7d4879;
	color: rgb(0, 0, 0);
	height: 40px;
	font-size: 18px;
	line-height: 40px;
	text-align: center;
	border-radius: 5px;
}

.roomEl {
	text-align: center;
	border-bottom: 1px solid #ddd;
	padding: 10px 0px;
	cursor: pointer;
}

.roomEl:hover {
	background: #7d4879;
}

.roomEl.active {
	background: #7d4879;
}
</style>
