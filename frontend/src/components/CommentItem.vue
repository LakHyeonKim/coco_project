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
				id="commentEdit"
				v-if="commentInfo.comment.memberId == $session.get('id')"
				@click="commentEditForm"
			>mdi-pen</v-icon>
			<v-icon
				id="commentDelete"
				v-if="commentInfo.comment.memberId == $session.get('id')"
				@click="commentDelete"
			>mdi-trash-can-outline</v-icon>
			<div id="commentDate" class="ml-auto">{{ commentInfo.comment.updateCreated }}</div>
		</div>

		<div id="commentContent" v-show="!show">{{ commentInfo.comment.contents }}</div>
		<v-card-text :id="'commentUpdateExpand-' + commentInfo.comment.idcomment" v-show="show">
			<textarea :id="'commentUpdateInput-' + commentInfo.comment.idcomment" v-model="contents" />
			<v-btn @click="commentEdit" outlined>댓글 수정</v-btn>
			<v-btn class="ml-2" @click="show = !show" outlined>취소</v-btn>
		</v-card-text>
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
	data() {
		return {
			show: false,
			contents: ""
		}
	},
	methods: {
		commentEditForm() {
			this.show = !this.show;
			this.contents = this.commentInfo.comment.contents;
			document.getElementById(`commentUpdateInput-${this.commentInfo.comment.idcomment}`).focus();

		},
		commentEdit() {
			if ( !this.contents) {
				alert("최소 한글자는 적어야 합니다")
			} else if (this.contents == this.commentInfo.comment.contents) {
				this.show = !this.show;
			} else {
				http.put(
					"/api/updateComment",
					{
						contents: this.contents,
						idcomment: this.commentInfo.comment.idcomment,

					},
					{ headers: { Authorization: this.$session.get("accessToken") } }
				)
					.then(res => {
						this.$emit("commentEdit");
						this.commentInfo.comment.contents = this.contents;
						this.show = !this.show;
					})
					.catch(err => {
						console.log(err);
					})
			}
		},
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
	padding: 16px;
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
#commentEdit {
	margin-left:5px;
}
#commentEdit:hover {
	color: black;
}
#commentDelete {
	margin-left:5px;
}
#commentDelete:hover {
	color: black;
}
#commentDate {
	font-size: 14px;
	color: rgba(0, 0, 0, 0.68);
}
#commentContent {
	padding: 0 16px 16px;
	width: 100%;
	word-break: keep-all;
	word-wrap: break-word;
	/* overflow: auto;
	white-space: pre-wrap; */
}
#commentUpdateExpand {
	padding: 0 16px 16px;
}
/* #commentUpdateInput { */
textarea[id^="commentUpdateInput"] {
	width: 100%;
	min-height: 160px;
	overflow: visible;
	outline: none;
	resize: none;
}
</style>