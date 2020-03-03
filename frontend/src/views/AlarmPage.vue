<template>
	<div class="backBox">
		<div id="header">
			<div id="headInnerBox">
				<img
					src="../assets/CC_Logo.png"
					alt="logo_image"
					id="searchLogo"
				/>
				<div id="CC">Code Coworker</div>
			</div>
		</div>
		<div class="blankBox"></div>
		<div class="alarmSubBox">
			<div class="alarmBox" v-for="alarm in alarms" :key="alarm.idalarm">
				<alarmCard
					:access="alarm.access"
					:dateCreated="alarm.dateCreated"
					:followId="alarm.followId"
					:idalarm="alarm.idalarm"
					:isRead="alarm.isRead"
					:likeId="alarm.likeId"
					:memberCaller="alarm.memberCaller"
					:memberReceiver="alarm.memberReceiver"
					:postId="alarm.postId"
				></alarmCard>
			</div>
		</div>
	</div>
</template>

<script>
import http from "../http-common";
import AlarmCard from "../components/AlarmCard";

export default {
	name: "AlarmPage",
	components: {
		AlarmCard
	},
	data() {
		return {
			alarms: []
		};
	},
	methods: {
		scrollEvent() {
			if (
				document.body.scrollTop > 50 ||
				document.documentElement.scrollTop > 50
			) {
				document.getElementById("header").style.height = "50px";
				document.getElementById("headInnerBox").style.padding = "10px";
				document.getElementById("CC").style.display = "inline-block";
			} else {
				document.getElementById("header").style.height = "75px";
				document.getElementById("headInnerBox").style.padding =
					"20px 10px 20px 10px";
				document.getElementById("CC").style.display = "none";
			}
		},
		mount() {
			const token = this.$session.get("accessToken");
			const headers = {
				Authorization: token
			};
			// console.log(headers);
			http.post(
				"/api/findAlarm",
				{ memberReceiver: this.$session.get("id") },
				{ headers }
			)
				.then(res => {
					if (res.status == 203) {
						// console.log("refresh token -> server");
						http.post(
							"/jwt/getAccessTokenByRefreshToken/",
							this.$session.get("refreshToken") == undefined
								? null
								: this.$session.get("refreshToken")
						)
							.then(ref => {
								// console.log(ref);

								if (ref.status == 203) {
									this.$session.destroy();
									alert("로그인 정보가 만료되었습니다.");
									this.$router.push("/");
								} else {
									this.$session.set("accessToken", ref.data);
									this.mount();
								}
							})
							.catch(err => {
								console.log(err);
							});
					} else {
						// console.log("findalarm res ", res);
						this.alarms = res.data;
					}
				})
				.catch(err => {
					console.log("findalarm err ", err);
				});
		}
	},
	mounted() {
		this.mount();
		this.$session.set("current", 3);
		this.$emit("updateCurrent", 3);
	},
	created: function() {
		// console.log("크리에이트는 언제 찍힐까");
		window.addEventListener("scroll", this.scrollEvent);
	},
	beforeDestroy: function() {
		// console.log("destroy kasjdfhkasjdfhlkajsdfhlkajsdfhlkajsdfhakl");
		window.removeEventListener("scroll", this.scrollEvent);
	}
};
</script>

<style>
.backBox {
	height: 100%;
	background-color: none;
}
#header {
	transition: 0.2s;
}
.blankBox {
	height: 75px;
}
.alarmSubBox {
	display: grid;
	justify-content: center;
	/* width: 80vw; */
	/* height: 100%; */
}
.alarmBox {
	margin-left: 5vw;
	width: 70vw;
	background-color: white;
	/* height: 100%; */
}
@media screen and (max-width: 600px) {
	.backBox {
		background-color: white;
	}
	.alarmBox {
		width: 100vw;
		margin-left: 0px;
	}
}
</style>
