<template>
	<div>
		<div id="blankBox"></div>
		<div id="detailBox">
			<div>
				<h1>{{ postTitle }}</h1>
				<br />
				<div id="post-info">
					<div id="profile-img">
						<v-avatar size="40">
							<img src="imagePath" alt="user-img" v-if="imagePath" />
							<img src="../assets/user.png" alt="default-img" v-else />
							<!-- <v-gravatar :email="postWriter" alt="gravatar" :size="50" /> -->
						</v-avatar>
					</div>

					<div id="profile-info">
						<p id="user-nickname">
							{{ postWriter }}
							<v-btn class="ml-2" color="indigo" height="20px" outlined small>팔로우</v-btn>
						</p>
						<p id="post-date">{{ dateCreated }} | {{ updateCreated }}</p>
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

			<div id="hash-tags">
				<v-btn class="mr-2" v-for="tag in tags" :key="tag.idtag" small outlined>
					<v-icon left>mdi-music-accidental-sharp</v-icon>
					{{ tag.tagName }}
				</v-btn>
			</div>

			<div id="action-bar">
				<MediumClap :likeCheck="likeCheck" :likeCount="likeCount"></MediumClap>
			</div>

			<div id="divide-line"></div>

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
import MediumClap from "./MediumClap";
export default {
	name: "Detail",
	components: {
		MediumClap
	},
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
	methods: {
		test() {
			console.log(document.getElementById("tests").src);
		}
	},
	updated() {
		Prism.highlightAll();
	}
};
</script>

<style scoped>
#post-info {
	display: flex;
	margin: 7px;
	/* style="display: inline-block; line-height: 10px" */
}
#profile-img {
	width: 40px;
	height: 40px;
	margin-right: 10px;
}
#user-nickname {
	font-size: 15px;
	margin-bottom: 5px;
	height: 17px;
}
#post-date {
	color: gray;
	margin-top: 5px;
	font-size: 12px;
}
#blankBox {
	height: 50px;
}
#detailBox {
	background-color: aliceblue;
	width: 80vw;
	height: 100%;
}
#profile-info {
}
#action-bar {
	display: flex;
	justify-content: space-between;
}
#more-btn {
	width: 30px;
	height: 30px;
}
#divide-line {
	margin: 20px 0;
	border-top: 2px solid rgba(0, 0, 0, 0.1);
}
</style>
