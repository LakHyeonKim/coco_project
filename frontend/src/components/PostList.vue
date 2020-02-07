<template>
	<v-layout mt-5 wrap>
		<v-flex v-for="i in posts.length" :key="i" class="portList">
			<post
				class="ma-3"
				:idPost="posts[i - 1].post.idpost"
				:memberId="posts[i - 1].post.memberId"
				:postTitle="posts[i - 1].post.postTitle"
				:postWriter="posts[i - 1].post.postWriter"
				:dateCreated="posts[i - 1].post.dateCreated"
				:updateCreated="posts[i - 1].post.updateCreated"
				:code="posts[i - 1].post.code"
				:likeCount="posts[i - 1].post.likeCount"
				:views="posts[i - 1].post.views"
				:imagePath="posts[i - 1].post.imagePath"
				:filePath="posts[i - 1].post.filePath"
				:access="posts[i - 1].post.access"
				:likeCheck="posts[i - 1].post.likeCheck"
				:order="posts[i - 1].post.order"
				:tags="posts[i - 1].tags"
				:commentCount="posts[i - 1].commentCount"
				id="post"
			></post>
			<PostMobile
				class="ma-3"
				:idPost="posts[i - 1].post.idpost"
				:memberId="posts[i - 1].post.memberId"
				:postTitle="posts[i - 1].post.postTitle"
				:postWriter="posts[i - 1].post.postWriter"
				:dateCreated="posts[i - 1].post.dateCreated"
				:updateCreated="posts[i - 1].post.updateCreated"
				:code="posts[i - 1].post.code"
				:likeCount="posts[i - 1].post.likeCount"
				:views="posts[i - 1].post.views"
				:imagePath="posts[i - 1].post.imagePath"
				:filePath="posts[i - 1].post.filePath"
				:access="posts[i - 1].post.access"
				:likeCheck="posts[i - 1].post.likeCheck"
				:order="posts[i - 1].post.order"
				:tags="posts[i - 1].tags"
				:commentCount="posts[i - 1].commentCount"
				id="postmobile"
			>
			</PostMobile>
		</v-flex>
	</v-layout>
</template>
<script>
import Post from "@/components/Post";
import PostMobile from "@/components/PostMobile";
import http from "../http-common";

export default {
	name: "PostList",
	data() {
		return {
			posts: []
		};
	},
	components: {
		Post,
		PostMobile
	},
	mounted() {
		const token = this.$session.get("accessToken");
		const headers = {
			Authorization: token
		};
		http.post("/api/findByAllNewsfeed/", this.$session.get("id"), {
			headers
		})
			.then(res => {
				console.log("getpost then 1", res.data);
				console.log("getpost then 2", res.data[0].post.idpost);
				this.posts = res.data;
				console.log("getpost then 3", this.posts);
			})
			.catch(err => {
				console.log("getpost catch ", err);
			});
	}
};
</script>

<style>
#postmobile {
	display: none;
}
@media screen and (max-width: 600px) {
	#post {
		display: none;
	}
	#postmobile {
		display: block;
	}
}
</style>
