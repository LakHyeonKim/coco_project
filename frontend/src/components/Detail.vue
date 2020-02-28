<template>
	<div id="compo">
		<div id="blankBox"></div>
		<div id="detailBox">
			<div>
				<div id="hash-tags">
					<v-btn
						class="mr-2"
						v-for="tag in tags"
						:key="tag.idtag"
						@click="searchHashtag(tag)"
						small
						outlined
						style="padding: 5px; color: gray; border-radius: 5px; border: 1px solid rgba(0, 0, 0, 0.1);"
					>
						<v-icon left>mdi-music-accidental-sharp</v-icon>
						{{ tag.tagName }}
					</v-btn>
				</div>
				<div id="post-title">
					{{ postTitle }}
					<v-icon id="postEdit" v-if="memberId == $session.get('id')" @click="postEdit">mdi-pen</v-icon>
					<div id="post-info">
						<span v-if="dateCreated == updateCreated">
							{{ dateCreated }} ·
							{{ views }} &nbsp;
						</span>
						<span v-else>
							{{ updateCreated }}(수정됨) ·
							{{ views }} &nbsp;
						</span>
						<span v-if="views > 1">views</span>
						<span v-else>view</span>
					</div>
				</div>

				<div id="post-head">
					<div id="profile-info">
						<img
							id="profile-img"
							:src="
								postWriterProfileImage == null ||
								postWriterProfileImage == ''
									? '../img/icons/user.png'
									: postWriterProfileImage
							"
							@click="goMypage()"
						/>
						<div style="float: left;">
							<div id="user-nickname" @click="goMypage()">{{ postWriter }}</div>
							<!-- <span id="post-info">{{ dateCreated }} | {{ updateCreated }} · {{ views }} &nbsp;</span> -->
							<!-- <div id="post-info">
								<span v-if="dateCreated == updateCreated">
									{{ dateCreated }} ·
									{{ views }} &nbsp;
								</span>
								<span v-else>
									{{ updateCreated }}(수정됨) ·
									{{ views }} &nbsp;
								</span>
								<span v-if="views > 1">views</span>
								<span v-else>view</span>
							</div> -->
						</div>
					</div>
					<button id="follow-btn" v-show="!isUser()" @click="follow">
						<span v-show="!isFollow">팔로우</span>
						<span v-show="isFollow">팔로잉</span>
						<v-icon v-show="!isFollow" small>mdi-plus-thick</v-icon>
						<v-icon v-show="isFollow" small>mdi-check-bold</v-icon>
					</button>
				</div>
			</div>
			<div id="divide-line"></div>
			<div id="fileBox" v-if="filePath">
				<v-icon>mdi-file-download-outline</v-icon>
				<a @click="fileDownload">{{ filePath.substring(38) }}</a>
			</div>

			<div id="postBox">
				<vue-markdown
					class="line-numbers match-braces rainbow-braces show-invisibles"
					:source="code"
					id="mark"
					data-download-link
				></vue-markdown>
			</div>

			<div id="additionBox">
				<div id="action-bar">
					<MediumClap
						:idPost="idPost"
						:likeCheck="likeCheck"
						:likeCount="likeCount"
						:likes="likes"
						@updateLike="updateLike"
					></MediumClap>
					<div id="addition-action" class="ma-4">
						<button>
							<v-icon>fa fa-ellipsis-h</v-icon>
						</button>
					</div>
				</div>

				<div id="divide-line"></div>
				<button id="babyPostCreate" @click="babyPostCreate">WRITE BABY POST ( ღ'ᴗ'ღ )</button>
				<comment-create
					:idPost="idPost"
					:memberId="memberId"
					:commentCount="commentCount"
					@addComment="addComment"
				></comment-create>

				<comment-list :commentInfos="commentInfos" :receiver="memberId" @commentDelete="commentDelete"></comment-list>
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
		postEdit() {
			this.$store.state.postData = {
				code: this.code,
				memberId: this.memberId,
				idPost: this.idPost,
				postTitle: this.postTitle,
				postWriter: this.postWriter,
				tags: this.tags,
				attachments: this.filePath
			};
			this.$router.push({ name: "newpage" });
		},
		goMypage() {
			this.$router.push("/mypage/" + this.memberId);
		},
		babyPostCreate() {
			this.$store.state.parent = {
				parentIdPost: this.idPost,
				parentIdMember: this.memberId
			};
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
		updateLike(like) {
			this.$emit("updateLike", like);
		},
		addComment(comment) {
			console.log("Detail.vue", comment);
			this.$emit("addComment", comment);
		},
		commentDelete(idx) {
			this.$emit("commentDelete", idx);
		},
		searchHashtag(hashtag) {
			console.log(hashtag);
			this.$store.state.searchtag = hashtag.tagName;
			this.$router.push({ name: "search" });
			console.log(hashtag);
		},
		fileDownload() {
			http.get(`/test/download/${this.idPost}`, { responseType: "blob" })
				.then(res => {
					const url = window.URL.createObjectURL(
						new Blob([res.data], {
							type: res.data.type
						})
					);
					const link = document.createElement("a");
					link.href = url;
					link.setAttribute("download", this.filePath.substring(38));
					document.body.appendChild(link);
					link.click();
					document.body.removeChild(link);
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
	width: 80%;
	margin: 0 auto;
}
#hash-tags {
	margin-bottom: 20px;
}
#post-title {
	font-size: 22px;
	font-weight: 500;
}
#post-head {
	/* display: flex; */
	margin-top: 32px;
	/* style="display: inline-block; line-height: 10px" */
}

#profile-info {
	display: inline-block;
}

#profile-img {
	width: 40px;
	height: 40px;
	margin-left: 10px;
	margin-right: 10px;
	border-radius: 50%;
	border: 1px solid silver;
	float: left;
	cursor: pointer;
}
#user-nickname {
	font-size: 15px;
	margin-top: 10px;
	height: 17px;
	cursor: pointer;
}
#post-info {
	color: gray;
	margin-top: 5px;
	font-size: 12px;
	/* margin-left: 5px; */
}

#follow-btn {
	margin: 5px;
	margin-right: 10px;
	height: 20x;
	border: 1px solid silver;
	padding: 3px 8px 3px 8px;
	float: right;
	transition: all 0.3s ease;
}

#follow-btn:hover {
	background-color: rgba(192, 192, 192, 0.363);
}

#blankBox {
	height: 50px;
}
#detailBox {
	/* background-color: aliceblue; */
	width: 100%;
	height: 100%;
}
#fileBox {
	/* display: flex; */
	/* justify-content: flex-end; */
	text-align: right;
	font-size: 12px;
}
#postBox {
	padding: 10px;
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
	border-top: 1px solid rgba(0, 0, 0, 0.1);
}

#babyPostCreate {
	width: 100%;
	padding: 10px;
	background-color: rgba(160, 23, 98, 0.5);
	color: white;
	margin-bottom: 10px;
	box-shadow: 1px 1px 4px rgba(0, 0, 0, 0.5);
}

#babyPostCreate:hover {
	background-color: rgba(161, 52, 112, 0.8);
}

@media screen and (max-width: 600px) {
	#compo {
		width: 90%;
	}
	#profile-img {
		margin-left: 5px;
		margin-right: 5px;
	}
	#follow-btn {
		margin: 5px;
		/* margin-right: 10px; */
		padding: 3px 5px 3px 5px;
	}
}
</style>
