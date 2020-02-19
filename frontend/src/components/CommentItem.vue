<template>
	<div>
		<div id="commentInfo">
			<router-link
				id="commentWriter"
				:to="{ name: 'mypage', params: { no: commentInfo.comment.memberId }}"
			>
				<!-- <v-avatar id="commentWriterImg" size="40">
					<img :src="commentInfo.postWriterProfileImage" alt="../assets/user.png" />
				</v-avatar>-->
				<img id="commentWriterImg" :src="commentInfo.postWriterProfileImage" alt="../assets/user.png" />
				<p id="commentWriterNickname">{{ commentInfo.comment.commentWriter }}</p>
			</router-link>
			<v-icon
				id="commentDelete"
				v-if="commentInfo.comment.memberId == $session.get('id')"
				@click="commentDelete"
			>mdi-trash-can-outline</v-icon>
			<div id="commentDate" class="ml-auto">{{ commentInfo.comment.updateCreated }}</div>
		</div>

		<pre id="commentContent">{{ commentInfo.comment.contents }}</pre>
	</div>
</template>

<script>
import http from "../http-common";
export default {
	name: "CommentItem",
	props: {
		commentInfo: {},
		receiver: {}
	},
	methods: {
		commentDelete() {
			http.post(
				"/trc/deleteComment",
				{
					postId: this.commentInfo.comment.postId,
					caller: this.commentInfo.comment.memberId,
					commentId: this.commentInfo.comment.idcomment,
					receiver: this.receiver
				},
				{ headers: { Authorization: this.$session.get("accessToken") } }
			)
				.then(res => {
					this.$emit("commentDelete");
				})
				.catch(err => {
					console.log(err);
				});
		}
	}
};
</script>

<style>
#commentInfo {
	display: flex;
	align-items: center;
	/* justify-content: space-between; */
	margin: 0 0 15px;
	padding: 5px 0 0;
}
#commentWriter {
	display: flex;
	align-items: center;
	text-decoration: none;
	font-size: 16px;
}
#commentWriterImg {
	width: 40px;
	height: 40px;
	border: 1px solid silver;
	border-radius: 50%;
	margin: 0 10px 0 0;
}
#commentWriterNickname {
	margin-bottom: 0;
}
#commentDelete {
	margin-left: 10px;
}
#commentDelete:hover {
	color: black;
}
#commentDate {
	font-size: 14px;
	color: rgba(0, 0, 0, 0.68);
}
#commentContent {
	overflow: auto;
	white-space: pre-wrap;
}
</style>