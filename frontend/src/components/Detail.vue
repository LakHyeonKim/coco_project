<template>
	<div>
		<div id="blankBox"></div>
		<div id="detailBox">
			<div>
				<h1>{{ postTitle }}</h1>
				<br />
				<div style="display: inline-block; line-height: 10px">
					<div id="imgDiv" style="float: left">
						<v-avatar>
							<img src="imagePath" alt="user-img" v-if="imagePath" />
							<img src="../assets/user.png" alt="default-img" v-else />
							<!-- <v-gravatar :email="postWriter" alt="gravatar" :size="50" /> -->
						</v-avatar>
					</div>

					<div id="profile-info" style="float: left; font-size: 13px">
						<p>
							{{ postWriter }}
							<v-btn class="ml-2" color="indigo" height="20px" outlined small>팔로우</v-btn>
						</p>
						<p>{{ dateCreated }} | {{ updateCreated }}</p>
					</div>
				</div>
			</div>

			<div>
				<vue-markdown
					class="line-numbers match-braces rainbow-braces show-invisibles"
					:source="code"
					data-download-link
				></vue-markdown>
			</div>

			<div>
				<ul>
					<li v-for="tag in tags" :key="tag.idtag">{{ tag.tagName }}</li>
				</ul>
			</div>

			<div>likeCount{{ likeCount }}</div>
			<div>views{{ views }}</div>
			<div>filePath{{ filePath }}</div>
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
import Prism from "../prism";
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
	},
	updated() {
		Prism.highlightAll();
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
#profile-info p {
	margin: 5px;
	height: 20px;
}
</style>
