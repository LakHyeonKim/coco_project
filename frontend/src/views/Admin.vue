<template>
	<div>
		<table>
			<tr>
				<th id="admintitle" @click="allMembers()">MEMBER</th>
				<th id="admintitle" @click="allPosts()">POST</th>
				<th id="admintitle" @click="allTags()">TAG</th>
				<th id="admintitle" @click="allComments()">COMMENT</th>
				<th id="admintitle" @click="allFollows()">FOLLOW</th>
			</tr>
		</table>

		<table v-if="membercheck">
			<tr>
				<th>idmember</th>
				<th>id</th>
				<th>dateCreated</th>
			</tr>
			<tr v-for="member in members" :key="member.idmember">
				<th>{{ member.idmember }}</th>
				<th>
					<a @click="goMemberDetail(member.idmember)">{{
						member.id
					}}</a>
				</th>
				<th>{{ member.nickname }}</th>
				<th>{{ member.dateCreated }}</th>
			</tr>
		</table>

		<table v-if="postcheck">
			<tr>
				<th>idpost</th>
				<th>TITLE</th>
				<th>VIEWS</th>
				<th>dateCreated</th>
			</tr>
			<tr v-for="post in posts" :key="post.idpost">
				<th>{{ post.idpost }}</th>
				<th>
					<a @click="goPostDetail(post.idpost)">{{
						post.postTitle
					}}</a>
				</th>
				<th>{{ post.views }}</th>
				<th>{{ post.dateCreated }}</th>
			</tr>
		</table>

		<table v-if="tagcheck">
			<tr>
				<th>idTag</th>
				<th>TagName</th>
				<th>FirstTimeCreated</th>
			</tr>
			<tr v-for="tag in tags" :key="tag.idtag">
				<th>{{ tag.idtag }}</th>
				<th>
					<a @click="goTagDetail(tag.idtag)">{{ tag.tagName }}</a>
				</th>
				<th>{{ tag.firstTimeCreated }}</th>
			</tr>
		</table>

		<table v-if="commentcheck">
			<tr>
				<th>idComment</th>
				<th>memberId</th>
				<th>Contents</th>
				<th>dateCreated</th>
			</tr>
			<tr v-for="comment in comments" :key="comment.idcomment">
				<th>{{ comment.idcomment }}</th>
				<th>{{ comment.memberId }}</th>
				<th>
					<a @click="goCommentDetail(comment.idcomment)">{{
						comment.contents
					}}</a>
				</th>
				<th>{{ comment.dateCreated }}</th>
			</tr>
		</table>

		<table v-if="followcheck">
			<tr>
				<th>idfollow</th>
				<th>memberFollower</th>
				<th>memberFollowing</th>
			</tr>
			<tr v-for="follow in follows" :key="follow.idcomment">
				<th>
					<a @click="goFollowDetail(follow.idfollow)">
						{{ follow.idfollow }}
					</a>
				</th>
				<th>{{ follow.memberFollower }}</th>
				<th>
					{{ follow.memberFollowing }}
				</th>
			</tr>
		</table>

		<!-- <div id="temp">
			<div id="ttt" @click="test()">
				test
			</div>
		</div> -->
	</div>
</template>

<script>
import http from "../http-common";
import router from "../router";

export default {
	name: "Admin",
	components: {},
	data() {
		return {
			requestForm: {
				headers: {
					Authorization: ""
				}
			},
			requestForm2: {
				headers: {
					Authorization: ""
				}
			},
			membercheck: 0,
			members: [],
			postcheck: 0,
			posts: [],
			tagcheck: 0,
			tags: [],
			commentcheck: 0,
			comments: [],
			followcheck: 0,
			follows: []
		};
	},
	methods: {
		// test() {
		// 	http.get("/test/download/94", { responseType: "blob" })
		// 		.then(res => {
		// 			const url = window.URL.createObjectURL(
		// 				new Blob([res.data], { type: res.data.type })
		// 			);
		// 			const link = document.createElement("a");
		// 			link.href = url;

		// 			link.setAttribute("download", "sdsd");
		// 			document.body.appendChild(link);
		// 			link.click();
		// 			console.log(link);
		// 			console.log(res);
		// 		})
		// 		.catch(err => {
		// 			console.log(err);
		// 		});
		// },
		// const url = window.URL.createObjectURL(
		// 	new Blob([res.data])
		// );
		// const link = document.createElement("a");
		// link.href = url;
		// link.setAttribute("download", "CalculRanking.pdf");
		// document.body.appendChild(link); //이부분을 원하는 부분에 붙이고 싶다.
		// link.click();
		// link.parentNode.removeChild(link);
		allMembers() {
			console.log(this.requestForm);
			if (!this.membercheck) {
				this.membercheck = 1;
				http.get("/api/findAllMember", this.requestForm)
					.then(res => {
						console.log("all member res ", res);
						this.members = res.data;
					})
					.catch(err => {
						console.log("all member err ", err);
					});
			} else {
				this.membercheck = 0;
			}
		},
		goMemberDetail(memberpk) {
			router.push("/admin/member/" + memberpk);
		},
		allPosts() {
			console.log(this.requestForm);
			console.log("asdfasdf", this.requestForm2);
			if (!this.postcheck) {
				this.postcheck = 1;
				http.get("/api/findAllPost", this.requestForm2, {
					crossDomain: true
				})
					.then(res => {
						console.log("all post res ", res);
						this.posts = res.data;
					})
					.catch(err => {
						console.log("all post err ", err);
					});
			} else {
				this.postcheck = 0;
			}
		},
		goPostDetail(postpk) {
			router.push("/admin/post/" + postpk);
		},
		allTags() {
			console.log(this.requestForm);
			if (!this.tagcheck) {
				this.tagcheck = 1;
				http.get("/api/findAllTag", this.requestForm)
					.then(res => {
						console.log("all tag res ", res);
						this.tags = res.data;
					})
					.catch(err => {
						console.log("all tag err ", err);
					});
			} else {
				this.tagcheck = 0;
			}
		},
		goTagDetail(tagpk) {
			router.push("/admin/tag/" + tagpk);
		},
		allComments() {
			console.log(this.requestForm);
			if (!this.commentcheck) {
				this.commentcheck = 1;
				http.get("/api/findAllComment", this.requestForm)
					.then(res => {
						console.log("all comment res ", res);
						this.comments = res.data;
					})
					.catch(err => {
						console.log("all comment err ", err);
					});
			} else {
				this.commentcheck = 0;
			}
		},
		goCommentDetail(commentpk) {
			router.push("/admin/comment/" + commentpk);
		},
		allFollows() {
			console.log(this.requestForm);
			if (!this.followcheck) {
				this.followcheck = 1;
				http.get("/api/findAllFollow", this.requestForm)
					.then(res => {
						console.log("all follow res ", res);
						this.follows = res.data;
					})
					.catch(err => {
						console.log("all follow err ", err);
					});
			} else {
				this.followcheck = 0;
			}
		},
		goFollowDetail(followpk) {
			router.push("/admin/follow/" + followpk);
		}
	},
	mounted() {
		document.querySelector("#navbar").setAttribute("style", "display:none");
		this.requestForm.headers.Authorization = this.$session.get(
			"accessToken"
		);
		this.requestForm2.headers.Authorization = this.$session.get(
			"accessToken"
		);
	},
	destroyed() {
		document
			.querySelector("#navbar")
			.setAttribute("style", "display:fixed");
	}
};
</script>

<style>
#temp {
	margin-left: 100px;
}
#admintitle {
	font-size: 30px;
	background-color: gray;
	text-align: center;
	color: white;
}
table {
	border-collapse: collapse;
	border-spacing: 0;
	width: 100%;
	/* border: 1px solid #ddd; */
}
th,
td {
	text-align: left;
	padding: 16px;
}

tr:nth-child(even) {
	/* background-color: #f2f2f2; */
}
::placeholder {
	font-size: 15px;
}
</style>
