<template>
	<div>
		<div
			id="commentListCard"
			v-for="(commentInfo, idx) in commentInfos.slice().reverse()"
			:key="idx"
		>
			<comment-item
				:commentInfo="commentInfo"
				:receiver="receiver"
				@commentDelete="commentDelete(idx)"
			></comment-item>
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
		commentInfos: {},
		receiver: {}
	},
	methods: {
		commentDelete(idx) {
			this.$store.state.commentDelIdx = idx;
			this.$emit("commentDelete", idx);
		}
	}
};
</script>

<style>
#commentListCard {
	background: white;
	margin: 20px 0 0;
	border: 1px solid rgba(0, 0, 0, 0.09);
	box-shadow: 0 1px 4px rgba(0, 0, 0, 0.04);
}
</style>
