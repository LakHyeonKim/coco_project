<template>
	<div class="backBox">
		<div id="header">
			<div id="headInnerBox">
				<img
					src="../assets/CC_Logo.png"
					alt="logo_image"
					id="searchLogo"
				/>
				<div id="codeCoworker">Code Coworker</div>
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
	mounted() {
		const token = this.$session.get("accessToken");
		const headers = {
			Authorization: token
		};
		console.log(headers);
		http.post(
			"/api/findAlarm",
			{ memberReceiver: this.$session.get("id") },
			{ headers }
		)
			.then(res => {
				console.log("findalarm res ", res);
				this.alarms = res.data;
			})
			.catch(err => {
				console.log("findalarm err ", err);
			});
	}
};
</script>

<style>
.backBox {
	height: 100%;
	background-color: none;
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
