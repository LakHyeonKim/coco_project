<template>
	<!-- store를 통해 받아온 post number로 // post에 대한 모든 정보가 다 필요함 -->
	<!-- 로딩된 정보를 넘겨 받아서 바로 띄워주면? -->
	<div id="detailMain">
		<detail
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
			:comments="this.detail.comments"
			:likes="this.detail.likes"
			:babyPosts="this.detail.babyPosts"
			:commentCount="this.detail.commentCount"
			:attachments="this.detail.attachments"
			id="compo"
		></detail>
	</div>
</template>

<script>
import Detail from "../components/Detail";
import store from "../store";
import http from "../http-common";

export default {
	name: "DetailPage",
	props: ["no"],
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
				comments: [],
				likes: [],
				babyPosts: [],
				commentCount: 0
				// attachments: {}
			}
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
	methods: {},
	mounted() {
		const requestForm = {
			idMember: this.$session.get("id"),
			idPost: this.$route.params.idPost
		};
		http.post("/api/findByBoardDetailPostId/", requestForm)
			.then(res => {
				console.log("detail res ", res);
				this.detail = res.data;
			})
			.catch(err => {
				console.log("detail err ", err);
			});
	}
};
</script>

<style>
#detailMain {
	background-color: bisque;
	height: 100%;
	width: 100%;
	/* text-align: center; */
}
#compo {
	display: grid;
	justify-content: center;
}
</style>
