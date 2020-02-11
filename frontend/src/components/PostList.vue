<template>
	<v-layout wrap>
		<v-flex v-for="i in posts.length" :key="i" class="postList">
			<post
				@like="like"
				:postIdx="i - 1"
				:idPost="posts[i - 1][1].post.idpost"
				:memberId="posts[i - 1][1].post.memberId"
				:postTitle="posts[i - 1][1].post.postTitle"
				:postWriter="posts[i - 1][1].post.postWriter"
				:dateCreated="posts[i - 1][1].post.dateCreated"
				:updateCreated="posts[i - 1][1].post.updateCreated"
				:code="posts[i - 1][1].post.code"
				:likeCount="posts[i - 1][1].post.likeCount"
				:views="posts[i - 1][1].post.views"
				:imagePath="posts[i - 1][1].post.imagePath"
				:filePath="posts[i - 1][1].post.filePath"
				:access="posts[i - 1][1].post.access"
				:likeCheck="posts[i - 1][1].post.likeCheck"
				:order="posts[i - 1][1].post.order"
				:tags="posts[i - 1][1].tags"
				:commentCount="posts[i - 1][1].commentCount"
				id="post"
			></post>
			<!-- <PostMobile
				class="ma-3"
				:postIdx="i - 1"
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
			</PostMobile> -->
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
			posts: [],
			mapPosts: new Map(),
			scrollY: 0,
			timer: null,
			flag: true
		};
	},
	components: {
		Post,
		PostMobile
	},
	methods: {
		like(postNum, index) {
			console.log("글번호 : " + postNum + "| index : " + index);
			console.log("멤버 ID : " + this.$session.get("id"));
			if (this.posts[index][1].post.likeCheck == 1) {
				this.address = "/trc/unLike/";
				this.posts[index][1].post.likeCheck = 0;
				this.posts[index][1].post.likeCount--;
			} else {
				this.address = "/trc/pushLike/";
				this.posts[index][1].post.likeCheck = 1;
				this.posts[index][1].post.likeCount++;
			}
			console.log(this.address);
			http.post(this.address, {
				member: {
					idmember: this.$session.get("id")
				},
				post: {
					idpost: postNum
				}
			})
				.then(res => {
					console.log(res);
				})
				.catch(error => {
					console.log(error);
					if (this.posts[index][1].post.likeCheck == 1) {
						this.posts[index][1].post.likeCheck = 0;
						this.posts[index][1].post.likeCount--;
					} else {
						this.posts[index][1].post.likeCheck = 1;
						this.posts[index][1].post.likeCount++;
					}
				});
		},
		scrollEvent: function() {
			console.log(window.scrollY);

			if (window.scrollY == 0) {
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
						this.mapPosts = new Map();
						this.posts = [];
						for (let i = 0; i < res.data.length; ++i) {
							this.mapPosts.set(
								res.data[i].post.idpost,
								res.data[i]
							);
						}
						console.log("getpost then 3", this.mapPosts);
						this.posts = [...this.mapPosts];
						console.log(this.posts);
					})
					.catch(err => {
						console.log("getpost catch ", err);
					});
			}

			console.log(
				window.scrollY >=
					document.body.offsetHeight - window.innerHeight - 150
			);
			if (
				window.scrollY >=
					document.body.offsetHeight - window.innerHeight - 150 &&
				this.flag == true
			) {
				this.flag = false;
				const requestForm = {
					member: {
						idmember: this.$session.get("id")
					},
					post: {
						idpost: this.posts[this.posts.length - 1][1].post.idpost
					}
				};
				console.log("reqeustForm ", requestForm);
				const token = this.$session.get("accessToken");
				const headers = {
					Authorization: token
				};
				http.post("/api/findByAllNewsfeedScrollDown/", requestForm, {
					headers
				})
					.then(res => {
						console.log("getpost then 1", res.data);
						console.log("getpost then 2", res.data[0].post.idpost);
						for (let i = 0; i < res.data.length; ++i) {
							this.mapPosts.set(
								res.data[i].post.idpost,
								res.data[i]
							);
						}
						console.log("getpost then 3", this.mapPosts);
						this.posts = [...this.mapPosts];
						console.log(this.posts);
						this.flag = true;
					})
					.catch(err => {
						console.log("getpost catch ", err);
					});
			}
		}
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
				for (let i = 0; i < res.data.length; ++i) {
					this.mapPosts.set(res.data[i].post.idpost, res.data[i]);
				}
				console.log("getpost then 3", this.mapPosts);
				this.posts = [...this.mapPosts];
				console.log(this.posts);
			})
			.catch(err => {
				console.log("getpost catch ", err);
			});
	},
	created: function() {
		window.addEventListener("scroll", this.scrollEvent);
	},
	beforeDestroy: function() {
		window.removeEventListener("scroll", this.scrollEvent);
	}
};
</script>

<style>
#post {
	margin: 12px;
}
@media screen and (max-width: 600px) {
	.postList {
		/* display: block; */
		/* flex: none; */
		width: 100%;
	}
	#post {
		/* display: block;
		flex: none;
		width: 100%; */
		margin: 0px;
	}
}
</style>
