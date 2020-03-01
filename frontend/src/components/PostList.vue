<template>
	<v-layout wrap>
		<div
			id="loading"
			:style="loadingTop ? loadingStyleOn : loadingStyleOff"
		>
			<div v-if="loadingTop" class="loader"></div>
		</div>
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
		</v-flex>
		<div id="loading" :style="loading ? loadingStyleOn : loadingStyleOff">
			<div v-if="loading" class="loader"></div>
		</div>
	</v-layout>
</template>
<script>
import Post from "@/components/Post";
import http from "../http-common";

export default {
	name: "PostList",
	data() {
		return {
			posts: [],
			mapPosts: new Map(),
			scrollY: 0,
			// timer: null,
			flag: true,
			loading: false,
			loadingTop: false,
			loadingStyleOn: {
				display: "grid"
			},
			loadingStyleOff: {
				display: "none"
			},
			loadingflag: true
		};
	},
	components: {
		Post
	},
	methods: {
		like(postNum, index) {
			// console.log("글번호 : " + postNum + "| index : " + index);
			// console.log("멤버 ID : " + this.$session.get("id"));
			if (this.posts[index][1].post.likeCheck == 1) {
				this.address = "/trc/unLike/";
				this.posts[index][1].post.likeCheck = 0;
				this.posts[index][1].post.likeCount--;
			} else {
				this.address = "/trc/pushLike/";
				this.posts[index][1].post.likeCheck = 1;
				this.posts[index][1].post.likeCount++;
			}
			// console.log(this.address);
			http.post(
				this.address,
				{
					member: {
						idmember: this.$session.get("id")
					},
					post: {
						idpost: postNum
					}
				},
				{ headers: { Authorization: this.$session.get("accessToken") } }
			)
				.then(response => {
					if (response.status == 203) {
						// console.log("refresh token -> server");
						http.post(
							"/jwt/getAccessTokenByRefreshToken/",
							this.$session.get("refreshToken") == undefined
								? null
								: this.$session.get("refreshToken")
						)
							.then(ref => {
								// console.log(ref);

								if (ref.status == 203) {
									this.$session.destroy();
									alert("로그인 정보가 만료되었습니다.");
									this.$router.push("/");
								} else {
									this.$session.set("accessToken", ref.data);
									if (
										this.posts[index][1].post.likeCheck == 1
									) {
										this.posts[index][1].post.likeCheck = 0;
										this.posts[index][1].post.likeCount--;
									} else {
										this.posts[index][1].post.likeCheck = 1;
										this.posts[index][1].post.likeCount++;
									}
									this.like(postNum, index);
								}
							})
							.catch(error => {
								// console.log(error);
							});
					} else {
						// console.log(response);
					}
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
			// console.log(window.scrollY);
			const token = this.$session.get("accessToken");
			const headers = {
				Authorization: token
			};
			// console.log("scroll headers event ", headers);
			if (window.scrollY == 0) {
				this.loadingTop = true;
				http.post("/api/findByAllNewsfeed/", this.$session.get("id"), {
					headers
				})
					.then(res => {
						if (res.status == 203) {
							// console.log("refresh token -> server");
							http.post(
								"/jwt/getAccessTokenByRefreshToken/",
								this.$session.get("refreshToken") == undefined
									? null
									: this.$session.get("refreshToken")
							)
								.then(ref => {
									// console.log(ref);

									if (ref.status == 203) {
										this.$session.destroy();
										alert("로그인 정보가 만료되었습니다.");
										this.$router.push("/");
									} else {
										this.$session.set(
											"accessToken",
											ref.data
										);
										this.scrollEvent;
									}
								})
								.catch(err => {
									console.log(err);
								});
						} else {
							// console.log("getpost then 1", res.data);
							// console.log(
							// 	"getpost then 2",
							// 	res.data[0].post.idpost
							// );
							this.mapPosts = new Map();
							this.posts = [];
							for (let i = 0; i < res.data.length; ++i) {
								this.mapPosts.set(
									res.data[i].post.idpost,
									res.data[i]
								);
							}
							// console.log("getpost then 3", this.mapPosts);
							this.posts = [...this.mapPosts];
							// console.log(this.posts);
							this.loadingTop = false;
							this.loadingflag = true;
						}
					})
					.catch(err => {
						console.log("getpost catch ", err);
						// this.loadingTop = false;
					});
			}

			// console.log(
			// 	window.scrollY >=
			// 		document.body.offsetHeight - window.innerHeight - 150
			// );
			if (
				window.scrollY >=
					document.body.offsetHeight - window.innerHeight - 150 &&
				this.flag == true &&
				this.loadingflag == true
			) {
				// console.log(
				// 	"flagflagflagflag asdjflkasjdlfkajsdlfkajsldfkjalsdkfjalskdfj"
				// );
				this.flag = false;
				this.loading = true;
				const requestForm = {
					member: {
						idmember: this.$session.get("id")
					},
					post: {
						idpost: this.posts[this.posts.length - 1][1].post.idpost
					}
				};
				// console.log("down scroll reqeustForm ", requestForm);
				// console.log("scroll headers event ", headers);
				http.post("/api/findByAllNewsfeedScrollDown/", requestForm, {
					headers
				})
					.then(res => {
						if (res.status == 203) {
							// console.log("refresh token -> server");
							http.post(
								"/jwt/getAccessTokenByRefreshToken/",
								this.$session.get("refreshToken") == undefined
									? null
									: this.$session.get("refreshToken")
							)
								.then(ref => {
									// console.log(ref);

									if (ref.status == 203) {
										this.$session.destroy();
										alert("로그인 정보가 만료되었습니다.");
										this.$router.push("/");
									} else {
										this.$session.set(
											"accessToken",
											ref.data
										);
										this.scrollEvent;
									}
								})
								.catch(err => {
									console.log(err);
								});
						} else {
							// console.log("getpost then 1", res.data);
							console.log(
								"getpost then 2",
								res.data[0].post.idpost
							);
							for (let i = 0; i < res.data.length; ++i) {
								this.mapPosts.set(
									res.data[i].post.idpost,
									res.data[i]
								);
							}
							// console.log("getpost then 3", this.mapPosts);
							this.posts = [...this.mapPosts];
							// console.log(this.posts);
							this.flag = true;
							this.loading = false;
						}
					})
					.catch(err => {
						console.log("getpost catch ", err);
						this.flag = true;
						this.loading = false;
						this.loadingflag = false;
					});
			}
		},
		mount() {
			const token = this.$session.get("accessToken");
			const headers = {
				Authorization: token
			};
			// console.log("lakjsdfkjasdf", this.$session.get("id"));
			// console.log("lakjsdfkjasdf", headers);
			http.post("/api/findByAllNewsfeed", this.$session.get("id"), {
				headers
			})
				.then(res => {
					if (res.status == 203) {
						// console.log("refresh token -> server");
						http.post(
							"/jwt/getAccessTokenByRefreshToken/",
							this.$session.get("refreshToken") == undefined
								? null
								: this.$session.get("refreshToken")
						)
							.then(ref => {
								// console.log(ref);

								if (ref.status == 203) {
									this.$session.destroy();
									alert("로그인 정보가 만료되었습니다.");
									this.$router.push("/");
								} else {
									this.$session.set("accessToken", ref.data);
									this.mount();
								}
							})
							.catch(err => {
								console.log(err);
							});
					} else {
						// console.log("getpost then 1", res.data);
						// console.log("getpost then 2", res.data[0].post.idpost);
						for (let i = 0; i < res.data.length; ++i) {
							this.mapPosts.set(
								res.data[i].post.idpost,
								res.data[i]
							);
						}
						// console.log("getpost then 3", this.mapPosts);
						this.posts = [...this.mapPosts];
						// console.log(this.posts);
						this.loadingTop = false;
						window.addEventListener("scroll", this.scrollEvent);
					}
				})
				.catch(err => {
					// console.log("getpost catch ", err);
					this.loadingTop = false;
				});
		}
	},
	mounted() {
		this.loadingTop = true;
		this.mount();
	},
	beforeDestroy: function() {
		// console.log("destroy kasjdfhkasjdfhlkajsdfhlkajsdfhlkajsdfhakl");
		window.removeEventListener("scroll", this.scrollEvent);
	}
};
</script>

<style>
#post {
	margin: 12px;
}
#loading {
	display: none;
	width: 100%;
	margin: 20px auto 100px auto;
	/* display: grid; */
	justify-content: center;
}
.loader {
	/* margin: 20px auto 20px auto; */
	border: 6px solid #f3f3f3; /* Light grey */
	border-top: 6px solid gray; /* Blue */
	border-radius: 50%;
	width: 60px;
	height: 60px;
	animation: spin 2s linear infinite;
}
@keyframes spin {
	0% {
		transform: rotate(0deg);
	}
	100% {
		transform: rotate(360deg);
	}
}
@media screen and (max-width: 600px) {
	.postList {
		/* display: block; */
		/* flex: none; */
		width: 100%;
		border-bottom: 0.75px solid rgba(0, 0, 0, 0.2);
	}
	#post {
		/* display: block;
		flex: none;
		width: 100%; */
		margin: 0px;
	}
}
</style>
