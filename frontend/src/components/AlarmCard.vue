<template>
	<div>
		<div id="alarmCard">
			<div id="userImgDiv">
				<img
					id="userImg"
					:src="
						userImg == null || userImg == ''
							? '../img/icons/user.png'
							: userImg
					"
					alt="userImg"
					@click.prevent="goYourPage()"
				/>
			</div>
			<div id="content">
				<p v-if="this.followId" class="line-clamp-content">
					<a @click.prevent="goYourPage()"
						><b style="color:black">{{ userNickname }}</b></a
					>가 나를 팔로우 하였습니다.
					<br />
				</p>
				<p v-else class="line-clamp-content">
					<a @click.prevent="goYourPage()"
						><b style="color:black">{{ userNickname }}</b></a
					>가 내 글을 좋아요 하였습니다:
					<a @click.prevent="goDetail()"
						><b style="color:black">{{ postTitle }}</b></a
					>
					<br />
				</p>
				<div style="font-size:12px">{{ dateCreated }}</div>
			</div>
		</div>
	</div>
</template>

<script>
import router from "../router";
import http from "../http-common";

export default {
	name: "AlarmList",
	props: {
		access: {},
		dateCreated: {},
		followId: {},
		idalarm: {},
		isRead: {},
		likeId: {},
		memberCaller: {},
		memberReceiver: {},
		postId: {}
	},
	data() {
		return {
			userNickname: "",
			userImg: "",
			postTitle: ""
		};
	},
	methods: {
		goYourPage() {
			router.push("/mypage/" + this.memberCaller);
		},
		goDetail() {
			router.push("/detail/" + this.postId);
		}
	},
	mounted() {
		const token = this.$session.get("accessToken");
		const headers = {
			Authorization: token
		};
		http.post(
			"/api/findMember",
			{ idmember: this.memberCaller },
			{ headers }
		)
			.then(res => {
				console.log("findMember res ", res);
				this.userNickname = res.data[0].nickname;
				this.userImg = res.data[0].imageUrl;
			})
			.catch(err => {
				console.log("findMember err ", err);
			});
		if (this.postId) {
			http.post("/api/findPost", { idpost: this.postId }, { headers })
				.then(res => {
					console.log("findPost res ", res);
					this.postTitle = res.data[0].postTitle;
				})
				.catch(err => {
					console.log("findPost err ", err);
				});
		}
	}
};
</script>

<style scoped>
.Effect {
	filter: grayscale(100%);
	-webkit-filter: grayscale(100%);
	color: rgba(0, 0, 0, 0.5);
}
#alarmCard {
	height: 100%;
	/* border-top: 1px solid red; */
	border-bottom: 0.75px solid rgba(0, 0, 0, 0.2);
	display: flex;
	margin: 5px auto 5px auto;
}
#userImgDiv {
	display: inline-block;
	margin: 10px;
	height: 50px;
}
#userImg {
	height: 50px;
	width: 50px;
	border-radius: 50%;
}
#content {
	display: inline-block;
	margin-top: 10px;
	margin-bottom: 10px;
	width: 100%;
}
#content > p {
	margin: 0px;
	font-size: 15px;
}
.line-clamp-content {
	overflow: hidden;
	display: -webkit-box;
	-webkit-line-clamp: 2;
	-webkit-box-orient: vertical;
}
#isRead {
	display: inline-block;
	margin: auto 10px auto 10px;
}
#readButton {
	/* margin: 25px auto 25px auto;
	padding-right: 7vw; */
	width: 60px;
	height: 25px;
	background-color: rgba(160, 23, 98, 0.5);
	border-radius: 5px;
}
#readText {
	/* width: 100px;
	height: 50px; */
	font-size: 17px;
	color: white;
	text-decoration: none;
}
@media screen and (max-width: 600px) {
	#alarmCard {
		margin: 3px auto 3px auto;
	}
	#userImgDiv {
		height: 40px;
	}
	#userImg {
		width: 40px;
	}
	#content {
		display: inline-block;
		margin-top: 10px;
		margin-bottom: 10px;
	}
	#content > p {
		font-size: 13px;
	}
	#readButton {
		width: 40px;
		height: 24px;
	}
	#readText {
		font-size: 13px;
	}
}
</style>
