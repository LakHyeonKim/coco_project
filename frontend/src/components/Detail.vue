<template>
	<div>
		<div id="blankBox"></div>
		<div id="detailBox">
			<div>idPost{{ idPost }}</div>
			<div>memberId{{ memberId }}</div>
			<div>postTitle{{ postTitle }}</div>
			<div>postWriter{{ postWriter }}</div>
			<div>dateCreated{{ dateCreated }}</div>
			<div>updateCreated{{ updateCreated }}</div>
			<div>code{{ code }}</div>
			<div>likeCount{{ likeCount }}</div>
			<div>views{{ views }}</div>
			<div>imagePath{{ imagePath }}</div>
			<div>filePath{{ filePath }}</div>
			<div>access{{ access }}</div>
			<div>likeCheck{{ likeCheck }}</div>
			<div>order{{ order }}</div>
			<div>tags{{ tags }}</div>
			<div>comments{{ comments }}</div>
			<div>likes{{ likes }}</div>
			<div>babyPosts{{ babyPosts }}</div>
			<div>commentCount{{ commentCount }}</div>
			<div>attachments{{ attachments }}</div>
		</div>
	</div>
</template>
<script>
export default {
	name: "Detail",
	props: {
		idPost: {},
		memberId: {},
		postTitle: {},
		postWriter: {},
		dateCreated: {},
		updateCreated: {},
		code: {},
		likeCount: {},
		views: {},
		imagePath: {},
		filePath: {},
		access: {},
		likeCheck: {},
		order: {},
		tags: {},
		comments: {},
		likes: {},
		babyPosts: {},
		commentCount: {},
		attachments: {}
	}
	// ,
	// mounted() {
	// 	alert(this.no);
	// }
};
</script>

<style scoped>
#blankBox {
	height: 50px;
}
#detailBox {
	background-color: aliceblue;
	width: 80vw;
	height: 100%;
}
</style>
