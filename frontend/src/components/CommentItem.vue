<template>
	<div>
		<div id="commentInfo">
			<router-link
				id="commentWriter"
				:to="{ name: 'mypage', params: { no: comment.memberId }}"
			>{{ comment.commentWriter }}</router-link>
			<div id="commentDate">{{ comment.updateCreated }}</div>
		</div>

		<pre id="commentContent">{{ comment.contents }}</pre>
	</div>
</template>

<script>
export default {
	name: "CommentItem",
	props: {
		comment: {}
	}
};
</script>

<style>
#commentInfo {
	display: flex;
	justify-content: space-between;
	margin: 0 0 15px;
	padding: 5px 0 0;
}
#commentWriter {
	text-decoration: none;
	font-size: 16px;
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