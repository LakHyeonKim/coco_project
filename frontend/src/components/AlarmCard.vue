<template>
	<div id="alarmCard">
		<div id="userImgDiv" @click="test()">
			<img id="userImg" src="../assets/CC_Logo.png" alt="" />
		</div>
		<div id="content">
			<pre>
                누가 뭐를 했다
                if 팔로우, 누가 나를 팔로우했다
                if 좋아요, 누가 내 글을 좋아요했다: 글 제목
                3줄 제한 걸기
				asdfasdf
				asdfasdfsad
				fas
				
            </pre>
		</div>
		<div id="isRead">
			<button id="readButton">
				<a href="" id="readText">READ</a>
			</button>
		</div>
	</div>
</template>

<script>
import http from "../http-common";

export default {
	name: "AlarmList",
	methods: {
		test() {
			console.log(this.$session.get("accessToken"));
			const headers = {
				Authorization: this.$session.get("accessToken")
			};
			http.get("/api/findAllMember", {
				headers
			})
				.then(res => {
					console.log(res);
				})
				.catch(err => {
					console.log(err);
				});
		}
	}
};
</script>

<style scoped>
#alarmCard {
	height: 100%;
	border: 1px solid red;
}
#userImgDiv {
	display: inline-block;
}
#userImg {
	width: 60px;
}
#userImg {
}
#content {
	display: inline-block;
}
#isRead {
	display: inline-block;
}
#readButton {
	/* margin: 25px auto 25px auto;
	padding-right: 7vw; */
	width: 75px;
	height: 30px;
	background-color: rgba(160, 23, 98, 0.5);
	border-radius: 5px;
}
#readText {
	/* width: 100px;
	height: 50px; */
	font-size: 20px;
	color: white;

	text-decoration: none;
}
</style>
