<template>
	<div id="compo">
		<div id="blankBox"></div>
		<div id="detailBox">
			<div>
				<p id="post-title">{{ postTitle }}</p>
				<v-btn id="babyPostCreate" @click="babyPostCreate"
					>babypost</v-btn
				>
				<div id="post-head">
					<div id="profile-img">
						<v-avatar size="40">
							<img
								:src="postWriterProfileImage"
								alt="../assets/icon/user.png"
							/>
						</v-avatar>
					</div>

					<div id="profile-info">
						<p id="user-nickname">
							{{ postWriter }}
							<v-btn
								class="ml-2 follow-btn"
								color="indigo"
								height="20px"
								v-show="!isUser()"
								@click="follow"
								outlined
								small
							>
								<span v-show="!isFollow">팔로우</span>
								<span v-show="isFollow">팔로잉</span>
								<v-icon v-show="!isFollow" small
									>mdi-plus-thick</v-icon
								>
								<v-icon v-show="isFollow" small
									>mdi-check-bold</v-icon
								>
							</v-btn>
						</p>
						<!-- <span id="post-info">{{ dateCreated }} | {{ updateCreated }} · {{ views }} &nbsp;</span> -->
						<div id="post-info">
							<span v-if="dateCreated == updateCreated"
								>{{ dateCreated }} · {{ views }} &nbsp;</span
							>
							<span v-else
								>{{ updateCreated }}(수정됨) ·
								{{ views }} &nbsp;</span
							>
							<span v-if="views > 1">views</span>
							<span v-else>view</span>
						</div>
					</div>
				</div>
			</div>
			<div id="divide-line"></div>
			<div id="fileBox">
				<p>파일첨부</p>
				<a @click="test">{{ filePath }}</a>
			</div>
			<div id="postBox">
				<vue-markdown
					class="line-numbers match-braces rainbow-braces show-invisibles"
					:source="code"
					data-download-link
				></vue-markdown>
			</div>

			<div id="additionBox">
				<div id="hash-tags">
					<v-btn
						class="mr-2"
						v-for="tag in tags"
						:key="tag.idtag"
						@click="searchHashtag(tag)"
						small
						outlined
					>
						<v-icon left>mdi-music-accidental-sharp</v-icon>
						{{ tag.tagName }}
					</v-btn>
				</div>

				<div id="action-bar">
					<MediumClap
						:idPost="idPost"
						:likeCheck="likeCheck"
						:likeCount="likeCount"
						@updateLike="updateLike"
					></MediumClap>
					<div id="addition-action" class="ma-4">
						<button>
							<v-icon @click="test">fa fa-ellipsis-h</v-icon>
						</button>
					</div>
				</div>

				<div id="divide-line"></div>

				<comment-create
					:idPost="idPost"
					:memberId="memberId"
					:commentCount="commentCount"
					@addComment="addComment"
				></comment-create>

				<comment-list
					:commentInfos="commentInfos"
					:receiver="memberId"
				></comment-list>
			</div>
		</div>
		<div id="blankBox"></div>
	</div>
</template>
<script>
import http from "../http-common";
import Prism from "../prism";
import MediumClap from "./MediumClap";
import CommentCreate from "./CommentCreate";
import CommentList from "./CommentList";
export default {
	name: "Detail",
	components: {
		MediumClap,
		CommentCreate,
		CommentList
	},
	props: {
		isFollow: {},
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
		commentInfos: {},
		likes: {},
		babyPosts: {},
		commentCount: {},
		postWriterProfileImage: {}
	},
	data() {
		return {};
	},
	methods: {
		babyPostCreate() {
			let parent = new FormData();
			parent.set("idpost", this.idPost);
			parent.set("memberId", this.memberId);
			parent.set("postTitle", this.postTitle);
			parent.set("postWriter", this.postWriter);
			parent.set("code", this.code);
			parent.set("likeCount", this.likeCount);
			parent.set("views", this.views);
			parent.set("imagePath", this.imagePath);
			parent.set("filePath", this.filePath);
			parent.set("access", this.access);
			parent.set("likeCheck", this.likeCheck);
			parent.set("order", this.order);
			this.$store.state.parent = parent;

			// this.$store.state.parent = {
			// 	idpost: this.idpost,
			// 	memberId: this.memberId,
			// 	postTitle: this.postTitle,
			// 	postWriter: this.postWriter,
			// 	code: this.code,
			// 	likeCount: this.likeCount,
			// 	views: this.views,
			// 	imagePath: this.imagePath,
			// 	filePath: this.filePath,
			// 	access: this.access,
			// 	likeCheck: this.likeCheck,
			// 	order: this.order
			// };
			this.$router.push({ name: "newpage" });
		},
		isUser() {
			if (this.$session.get("id") != this.memberId) {
				return false;
			} else {
				return true;
			}
		},
		follow() {
			let requestAddress = "";
			if (this.isFollow) {
				requestAddress = "/trc/makeUnFollow/";
			} else {
				requestAddress = "/trc/makeFollow/";
			}

			http.post(
				requestAddress,
				{
					memberFollower: this.$session.get("id"),
					memberFollowing: this.memberId
				},
				{ headers: { Authorization: this.$session.get("accessToken") } }
			)
				.then(response => {
					console.log(response);
					this.$emit("updateFollow");
				})
				.catch(error => {
					console.log(error);
				});
		},
		updateLike(data) {
			this.$emit("updateLike", data);
		},
		addComment(comment) {
			this.$emit("addComment", comment);
		},
		searchHashtag(hashtag) {
			console.log(hashtag);
			this.$store.state.searchtag = hashtag.tagName;
			this.$router.push({ name: "search" });
			console.log(hashtag);
		},
		test() {
			http.get("/test/download", { responseType: "blob" })
				.then(res => {
					const url = window.URL.createObjectURL(
						new Blob([res.data], { type: res.data.type })
					);
					const link = document.createElement("a");
					link.href = url;
					link.setAttribute("download", "sdsd");
					document.body.appendChild(link);
					link.click();
					console.log(link);
					console.log(res);
				})
				.catch(err => {
					console.log(err);
				});
		}
	},
	updated() {
		Prism.highlightAll();
	},
	mounted() {},
	created() {}
};
</script>

<style scoped>
#compo {
	/* display: grid;
	justify-content: center; */
	width: 80%;
	margin: 0 auto;
}
#post-title {
	font-size: 32px;
}
#post-head {
	display: flex;
	margin-top: 32px;
	/* style="display: inline-block; line-height: 10px" */
}
#profile-img {
	width: 40px;
	height: 40px;
	margin-left: 10px;
	margin-right: 10px;
	border: 1px solid silver;
	border-radius: 50%;
}
#user-nickname {
	font-size: 15px;
	margin-bottom: 5px;
	height: 17px;
}
#post-info {
	color: gray;
	margin-top: 5px;
	font-size: 12px;
}
#blankBox {
	height: 50px;
}
#detailBox {
	background-color: aliceblue;
	width: 100%;
	height: 100%;
}
#fileBox {
	display: flex;
	justify-content: flex-end;
}
#postBox {
	margin-top: 2em;
}
#additionBox {
	margin-top: 40px;
}
#action-bar {
	display: flex;
	justify-content: space-between;
}
#addition-action {
	display: flex;
	justify-content: center;
	align-items: center;
}
#divide-line {
	margin: 20px 0;
	border-top: 2px solid rgba(0, 0, 0, 0.1);
}
</style>
