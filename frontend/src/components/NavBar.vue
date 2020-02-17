<template>
	<!-- 모바일 화면에서 width 줄어들 때 구성요소 잘리는 현상 -->
	<!-- 웹 화면에서 구성요소 링크 범위 조절 -->
	<div id="navbar">
		<notifications  group="foo" />
		<ul>
			<li>
				<!-- <router-link to="/mypage"> -->
				<img
					@click="getMypage()"
					id="profile"
					:src="
						$session.get('imageUrl') == undefined
							? './img/icons/user.png'
							: $session.get('imageUrl')
					"
				/>
				<!-- </router-link> -->
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
					<img v-if="alarmIcon" class="nav_menu_img" src="../assets/icon/alarm.png" />
					<img v-if="followIcon" class="nav_menu_img image blinking" src="../assets/icon/follow.png" />
					<img v-if="likeIcon" class="nav_menu_img image blinking" src="../assets/icon/like.png" />
					<img v-if="commentIcon" class="nav_menu_img image blinking" src="../assets/icon/comment.png" />
				</router-link>
			</li>
			<li class="nav_menu">
				<img
					class="nav_menu_drag"
					src="../assets/kakao_logo.png"
					@dblclick="toggleMenu()"
					@mousedown="startDrag($event)"
				/>
				<Room id="openRoom" v-if="!isHidden" v-bind:toChild="toChild" v-on:updateIsHiddenDetail="updateIsHiddenDetail"></Room>
				<RoomDetail id="openDetail" v-bind:toChild="toChild" v-if="!isHidden&&toChild.isHiddenDetail" v-on:updateIsHiddenDetail="updateIsHiddenDetail"></RoomDetail>
			</li>
			<!-- <li class="nav_menu">
				<a @click.prevent="logout" href="#">Logout</a>
			</li> -->
		</ul>
	</div>
</template>

<script>
import router from '../router'
import Room from '@/components/Room'
import RoomDetail from '@/components/RoomDetail'
import SockJS from 'sockjs-client'
import Stomp from 'webstomp-client'

export default {
	name: 'NavBar',
	components: {
		Room,
		RoomDetail
	},
	data () {
		return {
			toChild:{
				isHiddenDetail: false,
				left: 0,
				top: 0
			},
			isHidden: true,
			preUrl: '',
			isfirst: true,
			timerID: 0,
			latest_alarm_id: 0,
			soloconnected: false,
			solo_send_message: '',
			div_L: 0,
			div_T: 0,
			targetObj: null,
			alarmIcon: true,
			followIcon: false,
			likeIcon: false,
			commentIcon: false
		}
	},
	methods: {
		alarmIconReset(){
			this.alarmIcon = true
			this.followIcon = false
			this.likeIcon = false
			this.commentIcon = false
		},
		getLeft(){
			return parseInt(this.targetObj[0].style.left.replace("px",""));
		},
		getTop(){
			return parseInt(this.targetObj[0].style.top.replace("px",""));
		},
		moveDrag(e){
			var e_obj = window.event? window.event : e;
			this.div_L = e_obj.clientX;
			this.div_T = e_obj.clientY;
			document.getElementsByClassName("nav_menu_drag")[0].style.left = e_obj.clientX + "px";
			document.getElementsByClassName("nav_menu_drag")[0].style.top = e_obj.clientY + "px";
     		return false;
		},
		stopDrag(){
			document.onmousemove = null;
			document.onmouseup = null;
		},
		startDrag(e){
			this.targetObj = document.getElementsByClassName("nav_menu_drag");
			var e_obj = window.event? window.event : e;
			this.div_L = e_obj.clientX;
			this.div_T = e_obj.clientY;
			this.toChild.left = e_obj.clientX;
			this.toChild.top = e_obj.clientY;
			document.onmousemove = this.moveDrag;
			document.onmouseup = this.stopDrag;
			if(e_obj.preventDefault) e_obj.preventDefault();
		},
		updateIsHiddenDetail(value){
			this.toChild.isHiddenDetail = value;
		},
		alarm () {
      		setInterval(this.solosend, 5000)
		},
		solosend () {
      		console.log("Send message:" + this.solo_send_message);
      		if (this.stompClient && this.stompClient.connected) {
        		const msg = { memberId: this.solo_send_message }
        		console.log(JSON.stringify(msg));
        		this.stompClient.send('/app/info', JSON.stringify(msg), {})
      		}
		},
		soloconnect() {
      		this.socket = new SockJS("http://192.168.100.57:8081/gs-guide-websocket");
      		this.stompClient = Stomp.over(this.socket);
      		this.stompClient.connect(
        		{},
        		frame => {
          			this.soloconnected = true;
          			console.log(frame);
          			this.stompClient.subscribe("/user/queue/info", tick => {
            			console.log(JSON.parse(tick.body).idalarm);
            			if (this.latest_alarm_id < JSON.parse(tick.body).idalarm) {
              				if (!this.isfirst) {
								if(JSON.parse(tick.body).postId > 0 && JSON.parse(tick.body).likeId > 0 && JSON.parse(tick.body).followId == 0){
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
								}
                				else if(JSON.parse(tick.body).postId > 0 && JSON.parse(tick.body).likeId == 0 && JSON.parse(tick.body).followId == 0){
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
								}
								else if(JSON.parse(tick.body).postId == 0 && JSON.parse(tick.body).likeId == 0 && JSON.parse(tick.body).followId > 0){
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
		toggleMenu () {
			this.isHidden = !this.isHidden
		},
		getMypage() {
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
	mounted(){
		this.solo_send_message = this.$session.get("id");
		this.soloconnect()
		this.alarm()
	},
	beforeDestroy(){
		this.disconnect()
	}
}
</script>

<style>

.blinking{
	-webkit-animation:blink 0.5s ease-in-out infinite alternate;
    -moz-animation:blink 0.5s ease-in-out infinite alternate;
    animation:blink 0.5s ease-in-out infinite alternate;
}
@-webkit-keyframes blink{
    0% {opacity:0;}
    100% {opacity:1;}
}
@-moz-keyframes blink{
    0% {opacity:0;}
    100% {opacity:1;}
}
@keyframes blink{
    0% {opacity:0;}
    100% {opacity:1;}
}

.vue-notification {
	background-color: #7d4879;
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
	cursor: pointer;
}

#navbar > ul > li {
	text-align: center;
}

.nav_menu {
	height: 50px;
}

.nav_menu_drag{
	height: 50px;
	position:absolute; 
	left:100px;
	top:100px; 
	cursor:pointer;
	cursor:hand;
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

}
@media screen and (max-width: 330px) {
	#profile {
		cursor: pointer;
		width: 30px;
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
