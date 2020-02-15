<template>
	<!-- 모바일 화면에서 width 줄어들 때 구성요소 잘리는 현상 -->
	<!-- 웹 화면에서 구성요소 링크 범위 조절 -->
	<div id="navbar">
		<notifications group="foo" />
		<ul>
			<li>
				<!-- <router-link to="/mypage"> -->
				<img
					@click="getMypage()"
					id="profile"
					src="../assets/user.png"
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
			<li class="nav_menu">
				<router-link to="/alarm">
					<img class="nav_menu_img" src="../assets/icon/alarm.png" />
				</router-link>
			</li>
			<li class="nav_menu">
				<img
					class="nav_menu_img"
					src="../assets/kakao_logo.png"
					@click="toggleMenu()"
				/>
				<Room v-if="!isHidden" v-bind:isHiddenDetail="isHiddenDetail" v-on:updateIsHiddenDetail="updateIsHiddenDetail"></Room>
				<RoomDetail v-bind:isHiddenDetail="isHiddenDetail" v-if="!isHidden&&isHiddenDetail" v-on:updateIsHiddenDetail="updateIsHiddenDetail"></RoomDetail>
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
			isHidden: true,
			isHiddenDetail: false,
			preUrl: '',
			isfirst: true,
			timerID: 0,
			latest_alarm_id: 0,
			soloconnected: false,
			solo_send_message: '',
		}
	},
	methods: {
		updateIsHiddenDetail(value){
			this.isHiddenDetail = value;
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
      		this.socket = new SockJS("http://localhost:8081/gs-guide-websocket");
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
                				this.$notify({
                  					group: "foo",
                  					title: "Important message",
                  					text:
                    					JSON.parse(tick.body).memberCaller +
                    					"님에게서 알림이 도착했어요~"
                				});
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
	destroyed(){
		this.disconnect()
	}
}
</script>

<style>
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
	background-color: white;
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
