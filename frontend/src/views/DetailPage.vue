<template>
	<!-- store를 통해 받아온 post number로 // post에 대한 모든 정보가 다 필요함 -->
	<!-- 로딩된 정보를 넘겨 받아서 바로 띄워주면? -->
	<div>
		<div id="detailMain">
			<detail
				:isFollow="this.detail.isFollow"
				:idPost="this.detail.post.idpost"
				:memberId="this.detail.post.memberId"
				:postTitle="this.detail.post.postTitle"
				:postWriter="this.detail.post.postWriter"
				:dateCreated="this.detail.post.dateCreated"
				:updateCreated="this.detail.post.updateCreated"
				:code="this.detail.post.code"
				:likeCount="this.detail.post.likeCount"
				:views="this.detail.post.views"
				:imagePath="this.detail.post.imagePath"
				:filePath="this.detail.post.filePath"
				:access="this.detail.post.access"
				:likeCheck="this.detail.post.likeCheck"
				:order="this.detail.post.order"
				:tags="this.detail.tags"
				:commentInfos="this.detail.commentInfos"
				:likes="this.detail.likes"
				:babyPosts="this.detail.babyPosts"
				:commentCount="this.detail.commentCount"
				:postWriterProfileImage="this.detail.postWriterProfileImage"
				@updateLike="updateLike"
				@addComment="addComment"
				@updateFollow="updateFollow"
			></detail>
		</div>
		<!-- <div id="detailBaby">
			<detail
				v-for="babyPost in this.detail.babyPosts"
				:key="babyPost.idPost"
				:isFollow="babyPost.isFollow"
				:idPost="babyPost.post.idpost"
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
				:comments="babyPost.comments"
				:likes="babyPost.likes"
				:babyPosts="babyPost.babyPosts"
				:commentCount="babyPost.commentCount"
				@updateLike="updateLike"
				@addComment="addComment"
				@updateFollow="updateFollow"
			></detail>
		</div>-->
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
		updateLike(like) {
			if (like == 1) {
				this.detail.post.likeCheck = 1;
			} else {
				this.detail.post.likeCheck = 0;
			}
			this.detail.post.likeCount += like;
		},
		addComment(comment) {
			comment.dateCreated = "방금 전";
			comment.updateCreated = "방금 전";
			comment.idcomment = 0;
			this.detail.commentInfos.push(comment);
		},
		updateFollow() {
			this.isFollow = !this.isFollow;
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
				for (let babyPost in this.detail.babyPosts) {
					http.post(
						"/api/findByBoardDetailPostId",
						{
							idMember: this.$session.get("id"),
							idPost: babyPost
						},
						{
							headers: {
								Authorization: this.$session.get("accessToken")
							}
						}
					)
						.then(res => {
							console.log(res);
							this.babyPosts.append(res);
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
	background-color: bisque;
	height: 100%;
	width: 100%;
}
</style>
