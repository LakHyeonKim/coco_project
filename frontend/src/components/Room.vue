<template>
	<div class="openRoom">
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
					>
						<p v-if="item.idroom != 0">
							방번호::{{ item.idroom }}___방제목::
							{{ item.roomName }}___방장:: {{ item.memberId }}___생성일::{{ item.dateCreated }}
						</p>
						<button
							id="buttonStyle"
							type="button"
							@click="enterRoom(item)"
						>
							채팅방 입장
						</button>
						<button
							id="buttonStyle"
							type="button"
							@click="deleteRoom(item.idroom)"
							v-if="item.memberId == senderStatic"
						>
							채팅방 삭제
						</button>
					</div>
				</div>
			</div>
		</div>
		<div>
			<input
				id="inputStyle"
				type="text"
				v-model="room_name"
				@keyup.enter="createRoom"
			/>
			<button id="buttonStyle" type="button" @click="createRoom">
				채팅방 개설
			</button>
			<input
				id="inputStyle"
				type="text"
				v-model="search_room_name"
				@keyup.enter="searchRoom"
			/>
			<button id="buttonStyle" type="button" @click="searchRoom">
				채팅방 검색
			</button>
		</div>
	</div>
</template>

<script>
import alarmHttp from "../http-alarm";

export default {
	name: 'Room',
	props:["toChild"],
	data () {
		return {
			senderStatic: 0,
			isHidden: this.toChild.isHiddenDetail,
			room_name: '',
			search_room_name:'',
			chatrooms: []
		}
	},
	created () {
		this.findAllRoom()
		this.senderStatic = this.$session.get('id')
	},
	mounted(){
		var standardWidth = window.innerWidth / 2;
		var standardHeight = window.innerHeight / 2;
		if(standardWidth > this.toChild.left && standardHeight > this.toChild.top){
			document.getElementsByClassName("openRoom")[0].style.left = (this.toChild.left) + "px";
			document.getElementsByClassName("openRoom")[0].style.top = (this.toChild.top-270) + "px";
		}else if(standardWidth <= this.toChild.left && standardHeight <= this.toChild.top){
			document.getElementsByClassName("openRoom")[0].style.left = (this.toChild.left-500) + "px";
			document.getElementsByClassName("openRoom")[0].style.top = (this.toChild.top-670) + "px";
		}else if(standardWidth <= this.toChild.left && standardHeight > this.toChild.top){
			document.getElementsByClassName("openRoom")[0].style.left = (this.toChild.left-500) + "px";
			document.getElementsByClassName("openRoom")[0].style.top = (this.toChild.top-270) + "px";
		}else if(standardWidth > this.toChild.left && standardHeight <= this.toChild.top){
			document.getElementsByClassName("openRoom")[0].style.left = this.toChild.left + "px";
			document.getElementsByClassName("openRoom")[0].style.top = (this.toChild.top-670) + "px";
		}
	},
	methods: {
		findAllRoom: function () {
			alarmHttp.get('/chat/rooms').then(response => {
				console.log(response)
				this.chatrooms = response.data
			})
		},
		searchRoom: function(){
			alarmHttp.get('/chat/search/' + encodeURI(this.search_room_name)).then(response => {
				console.log(response)
				this.chatrooms = response.data
			})
		},
		createRoom: function () {
			if ('' === this.room_name) {
				alert('방 제목을 입력해 주십시요.')
				return
			} else {
				alarmHttp
					.post('/chat/room', {
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
		deleteRoom: function (idroom) {
			alarmHttp
				.delete('/chat/room/' + idroom)
				.then(response => {
					console.log(response)
					alert(
						'방 삭제를 성공하였습니다.'
					)
					this.findAllRoom()
					this.$emit("updateIsHiddenDetail",false)
				})
				.catch(response =>{
					console.log(response)
					alert('채팅방 삭제를 실패하였습니다.')
				})
		},
		enterRoom: function (item) {
			localStorage.setItem('wschat.member_id', this.senderStatic)
			localStorage.setItem('wschat.idroom', item.idroom)
			//console.log(this.isHidden)
			this.$emit("updateIsHiddenDetail",!this.isHidden)
			// console.log(this.isHidden)
			this.$emit("updateIsHiddenDetail",false)
			setTimeout(() => {
				this.$emit("updateIsHiddenDetail",true)
			}, 1)
		}
	}
}
</script>

<style>
#roomWrap {
	width: 100%;
}

.openRoom {
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
}

input {
	border-block-color: black;
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
	border-radius: 10px;
}

#roomSelect {
	
	border-radius: 10px;
}

#roomHeader {
	background-color: #a794a5;
	color: rgb(255, 255, 255);
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
