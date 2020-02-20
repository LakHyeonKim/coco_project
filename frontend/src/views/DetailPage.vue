<template>
	<!-- store를 통해 받아온 post number로 // post에 대한 모든 정보가 다 필요함 -->
	<!-- 로딩된 정보를 넘겨 받아서 바로 띄워주면? -->
	<div>
		<div id="detailMain">
			<detail
				:idPost="detail.post.idpost"
				:isFollow="detail.isFollow"
				:memberId="detail.post.memberId"
				:postTitle="detail.post.postTitle"
				:postWriter="detail.post.postWriter"
				:dateCreated="detail.post.dateCreated"
				:updateCreated="detail.post.updateCreated"
				:code="detail.post.code"
				:likeCount="detail.post.likeCount"
				:views="detail.post.views"
				:imagePath="detail.post.imagePath"
				:filePath="detail.post.filePath"
				:access="detail.post.access"
				:likeCheck="detail.post.likeCheck"
				:order="detail.post.order"
				:tags="detail.tags"
				:commentInfos="detail.commentInfos"
				:likes="detail.likes"
				:babyPosts="detail.babyPosts"
				:commentCount="detail.commentCount"
				:postWriterProfileImage="detail.postWriterProfileImage"
				@updateLike="updateLike(detail)"
				@addComment="addComment(detail)"
				@updateFollow="updateFollow(detail)"
				@commentDelete="commentDelete(detail)"
			></detail>
		</div>
		<div
			id="detailBaby"
			v-for="babyPost in babyPosts"
			:key="babyPost.post.idpost"
		>
			<detail
				:idPost="babyPost.post.idpost"
				:isFollow="babyPost.isFollow"
				:memberId="babyPost.post.memberId"
				:postTitle="babyPost.post.postTitle"
				:postWriter="babyPost.post.postWriter"
				:dateCreated="babyPost.post.dateCreated"
				:updateCreated="babyPost.post.updateCreated"
				:code="babyPost.post.code"
				:likeCount="babyPost.post.likeCount"
				:views="babyPost.post.views"
				:imagePath="babyPost.post.imagePath"
				:filePath="babyPost.post.filePath"
				:access="babyPost.post.access"
				:likeCheck="babyPost.post.likeCheck"
				:order="babyPost.post.order"
				:tags="babyPost.tags"
				:commentInfos="babyPost.commentInfos"
				:likes="babyPost.likes"
				:babyPosts="babyPost.babyPosts"
				:commentCount="babyPost.commentCount"
				:postWriterProfileImage="babyPost.postWriterProfileImage"
				@updateLike="updateLike(babyPost)"
				@addComment="addComment(babyPost)"
				@updateFollow="updateFollow(babyPost)"
				@commentDelete="commentDelete(babyPost)"
			></detail>
		</div>
	</div>
</template>

<script>
import Detail from "../components/Detail";
import store from "../store";
import http from "../http-common";

export default {
	name: "DetailPage",
	data() {
		return {
			likeCheck: 0,
			detail: {
				post: {
					idPost: 0,
					memberId: 0,
					postTitle: "",
					postWriter: "",
					dateCreated: {
						type: Date,
						default: new Date("2020-02-06")
					},
					updateCreated: {
						type: Date,
						default: new Date("2020-02-06")
					},
					code: "",
					likeCount: 0,
					views: 0,
					imagePath: "",
					filePath: "",
					access: 0,
					likeCheck: 0,
					order: 0
				},
				tags: [],
				commentInfos: [],
				likes: [],
				babyPosts: [],
				commentCount: 0,
				postWriterProfileImage: "",
				isFollow: 0
			},
			babyPosts: []
		};
	},
	components: {
		Detail
	},
	computed: {
		idPost: function() {
			return store.state.idPost;
		}
	},
	methods: {
		updateLike(post) {
			if (this.$store.state.likeUpdate == 1) {
				post.post.likeCheck = 1;
			} else {
				post.post.likeCheck = 0;
			}
			post.post.likeCount += this.$store.state.likeUpdate;
		},
		addComment(post) {
			store.state.commentContent.dateCreated = "방금 전";
			store.state.commentContent.updateCreated = "방금 전";
			store.state.commentContent.access = 0;
			post.commentInfos.push({
				comment: store.state.commentContent,
				isFollow: 0,
				postWriterProfileImage: this.$session.get("imageUrl")
			});
			store.state.commentContent = {};
		},
		updateFollow(post) {
			post.isFollow = !post.isFollow;
		},
		commentDelete(post) {
			console.log("1", post.commentInfos);
			post.commentInfos.splice(
				post.commentInfos.length - 1 - this.$store.state.commentDelIdx,
				1
			);
			console.log("2", post.commentInfos);
		}
	},
	created() {
		const requestForm = {
			idMember: this.$session.get("id"),
			idPost: this.$route.params.idPost
		};
		const headers = {
			Authorization: this.$session.get("accessToken")
		};

		http.post("/api/findByBoardDetailPostId/", requestForm, { headers })
			.then(res => {
				console.log("detail res ", res);
				this.detail = res.data;
				this.babyPosts = [];
				// for (let babyPost in res.data.babyPosts) {
				for (let idx = 0; idx < res.data.babyPosts.length; idx++) {
					http.post(
						"/api/findByBoardDetailPostId",
						{
							idMember: this.$session.get("id"),
							idPost: res.data.babyPosts[idx].idpost
						},
						{
							headers: {
								Authorization: this.$session.get("accessToken")
							}
						}
					)
						.then(res => {
							this.babyPosts.push(res.data);
							console.log(this.babyPosts);
						})
						.catch(err => {
							console.log(err);
						});
				}
			})
			.catch(err => {
				console.log("detail err ", err);
			});
	},
	mounted() {}
};
</script>

<style>
#detailMain {
	height: 100%;
	width: 100%;
}
#detailBaby {
	position: relative;
	left: 50px;
	height: 100%;
	width: 100%;
}
</style>
