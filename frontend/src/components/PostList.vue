<template>
	<v-layout mt-5 wrap>
		<v-flex v-for="i in posts.length" :key="i" class="portList">
			<post
				@like="like"
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
			scrollY: 0,
			timer: null
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
			if (this.posts[index].post.likeCheck == 1) {
				this.address = "/trc/unLike/";
				this.posts[index].post.likeCheck = 0;
				this.posts[index].post.likeCount--;
			} else {
				this.address = "/trc/pushLike/";
				this.posts[index].post.likeCheck = 1;
				this.posts[index].post.likeCount++;
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
					if (this.posts[index].post.likeCheck == 1) {
						this.posts[index].post.likeCheck = 0;
						this.posts[index].post.likeCount--;
					} else {
						this.posts[index].post.likeCheck = 1;
						this.posts[index].post.likeCount++;
					}
				});
		},
		// infinityScroll() {
		// 	console.log(window.scrollY);
		// 	window.onscroll = function(e) {
		// 		//추가되는 임시 콘텐츠
		// 		if (
		// 			window.innerHeight + window.scrollY >=
		// 			document.body.offsetHeight
		// 		) {
		// 			const token = this.$session.get("accessToken");
		// 			const headers = {
		// 				Authorization: token
		// 			};
		// 			http.post(
		// 				"/api/findByAllNewsfeed/",
		// 				this.$session.get("id"),
		// 				{
		// 					headers
		// 				}
		// 			)
		// 				.then(res => {
		// 					console.log("getpost then 1", res.data);
		// 					console.log(
		// 						"getpost then 2",
		// 						res.data[0].post.idpost
		// 					);
		// 					this.posts.push(res.data);
		// 					console.log("getpost then 3", this.posts);
		// 				})
		// 				.catch(err => {
		// 					console.log("getpost catch ", err);
		// 				});
		// 		}
		// 	};
		// },
		handleScroll: function() {
			console.log(this.scrollY);
			console.log(window.scrollY);
			console.log(
				window.scrollY ==
					document.body.offsetHeight - window.innerHeight + 20
			);
			if (
				window.scrollY ==
				document.body.offsetHeight - window.innerHeight + 20
			) {
				const requestForm = {
					memberId: this.$session.get("id"),
					postId: this.posts[this.posts.length - 1].post.idpost
				};
				console.log("reqeustForm ", requestForm);
				console.log("laskjdflaksjdflkajsdlfkajsldkfj");
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
							this.posts.push(res.data[i]);
						}
						console.log("getpost then 3", this.posts);
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
				this.posts = res.data;
				console.log("getpost then 3", this.posts);
			})
			.catch(err => {
				console.log("getpost catch ", err);
			});
	},
	created: function() {
		// 핸들러 등록하기
		window.addEventListener("scroll", this.handleScroll);
	},
	beforeDestroy: function() {
		// 핸들러 제거하기(컴포넌트 또는 SPA의 경우 절대 잊지 말아 주세요!)
		window.removeEventListener("scroll", this.handleScroll);
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
