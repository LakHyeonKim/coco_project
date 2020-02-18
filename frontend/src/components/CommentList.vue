<template>
	<div>
		<div
			id="commentListCard"
			v-for="commentInfo in commentInfos.slice().reverse()"
			:key="commentInfo.comment.idcomment"
		>
			<comment-item :commentInfo="commentInfo"></comment-item>
		</div>
	</div>
</template>

<script>
import CommentItem from "./CommentItem";
export default {
	name: "CommentList",
	components: {
		CommentItem
	},
	props: {
		commentInfos: {}
	}
};
</script>

<style>
#commentListCard {
	background: white;
	margin: 20px 0 0;
	padding: 10px 20px 15px;
	border: 1px solid rgba(0, 0, 0, 0.09);
	box-shadow: 0 1px 4px rgba(0, 0, 0, 0.04);
}
</style>