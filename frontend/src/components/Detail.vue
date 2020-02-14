<template>
	<div>
		<div id="blankBox"></div>
		<div id="detailBox">
			<div>
				<h1>{{ postTitle }}</h1>
				<br />
				<div id="post-head">
					<div id="profile-img">
						<v-avatar size="30">
							<img src="imagePath" alt="user-img" v-if="imagePath" />
							<img src="../assets/user.png" alt="default-img" v-else />
							<!-- <v-gravatar :email="postWriter" alt="gravatar" :size="50" /> -->
						</v-avatar>
					</div>

					<div id="profile-info">
						<p id="user-nickname">
							{{ postWriter }}
							<v-btn class="ml-2 follow-btn" color="indigo" height="20px" outlined small>
								<span>팔로우</span>
								<v-icon small>mdi-plus-thick</v-icon>
							</v-btn>
							<v-btn class="ml-2 follow-btn" color="indigo" height="20px" outlined small>
								<span>팔로잉</span>
								<v-icon small>mdi-check-bold</v-icon>
							</v-btn>
						</p>
						<span id="post-info">{{ dateCreated }} | {{ updateCreated }} · {{ views }} &nbsp;</span>
						<span id="post-info" v-if="views > 1">views</span>
						<span id="post-info" v-else>view</span>
					</div>
				</div>
			</div>
			<div>
				<vue-markdown
					class="line-numbers match-braces rainbow-braces show-invisibles"
					:source="code"
					data-download-link
				></vue-markdown>
			</div>

			<div id="additionBox">
				<div id="hash-tags">
					<v-btn class="mr-2" v-for="tag in tags" :key="tag.idtag" small outlined>
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
							<v-icon>fa fa-ellipsis-h</v-icon>
						</button>
					</div>
				</div>

				<div id="divide-line"></div>

				<div id="commentCreateCard">
					<v-card class="mx-auto">
						<v-card-text id="commentCreateInfo">
							<v-avatar size="40">
								<img src="../assets/user.png" alt="default-img" />
							</v-avatar>
							<v-card-title id="commentCreatePlaceholder" v-show="!show" @click="moveinFocus">댓글을 작성해주세요</v-card-title>
							<v-card-subtitle id="commentCreateNickname" v-show="show">{{ $session.get("nickname") }}</v-card-subtitle>
						</v-card-text>

						<v-expand-transition>
							<div v-show="show">
								<v-card-text id="commentCreateExpand">
									<textarea
										id="commentCreateInput"
										style="resize: none;"
										@blur="moveoutFocus"
										v-model="commentContent"
									/>
									<v-btn outlined @click="commenting">댓글 작성</v-btn>
								</v-card-text>
							</div>
						</v-expand-transition>
					</v-card>
				</div>

				<div>
					<div
						id="commentListCard"
						v-for="comment in comments.slice().reverse()"
						:key="comment.idcomment"
					>
						<div id="commentInfo">
							<router-link
								id="commentWriter"
								:to="{ name: 'mypage', params: { no: comment.memberId }}"
							>{{ comment.commentWriter }}</router-link>
							<div id="commentDate">{{ comment.updateCreated }}</div>
						</div>

						<pre id="commentContent">{{ comment.contents }}</pre>
					</div>
				</div>

				<br />
				<br />
				<br />
				<br />
				<br />

				<div>likeCount {{ likeCount }}</div>
				<div>filePath{{ filePath }}</div>
				<div>order{{ order }}</div>
				<div>tags{{ tags }}</div>
				<div>comments{{ comments }}</div>
				<div>likes{{ likes }}</div>
				<div>babyPosts{{ babyPosts }}</div>
				<div>commentCount{{ commentCount }}</div>
				<div>attachments{{ attachments }}</div>
			</div>
		</div>
	</div>
</template>
<script>
import http from "../http-common";
import Prism from "../prism";
import MediumClap from "./MediumClap";
export default {
	name: "Detail",
	components: {
		MediumClap
	},
	props: {
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
		comments: {},
		likes: {},
		babyPosts: {},
		commentCount: {},
		attachments: {}
	},
	data() {
		return {
			show: false,
			commentContent: ""
		};
	},
	methods: {
		updateLike(data) {
			this.$emit("updateLike", data);
		},
		expandCard() {
			if (!this.show) {
				this.show = !this.show;
			}
		},
		async moveinFocus() {
			await this.expandCard();
			document.getElementById("commentCreateInput").focus();
		},
		moveoutFocus() {
			if (!document.getElementById("commentCreateInput").value) {
				this.show = !this.show;
			}
		},
		commenting() {
			if (!this.commentContent) {
				return;
			}
			const userComment = {
				comments: [
					{
						commentWriter: this.$session.get("nickname"),
						contents: this.commentContent,
						memberId: this.$session.get("id"),
						postId: this.idPost
					}
				],
				post: {
					memberId: this.memberId
				}
			};
			http.post("/trc/makeComment/", userComment, {
				headers: {
					Authorization: this.$session.get("accessToken")
				}
			})
				.then(res => {
					console.log(res);
					this.$emit("addComment", userComment.comments[0]);
					this.commentContent = "";
					this.show = !this.show;
				})
				.catch(err => {
					console.log(err);
				});
		}
	},
	updated() {
		Prism.highlightAll();
	},
	mounted() {}
};
</script>

<style scoped>
#post-head {
	display: flex;
	margin: 7px;
	/* style="display: inline-block; line-height: 10px" */
}
#profile-img {
	width: 40px;
	height: 40px;
	margin-right: 10px;
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
	width: 65vw;
	height: 100%;
}
#additionBox {
	margin-top: 40px;
}
#profile-info {
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
#commentCreateCard {
	-webkit-font-smoothing: antialiased;
}
#commentCreateInfo {
	display: flex;
	align-items: center;
}
#commentCreatePlaceholder {
	width: 100%;
	color: rgba(0, 0, 0, 0.54);
}
#commentCreateNickname {
	padding: 16px;
	margin: 0;
	color: gray;
	font-weight: 500;
}
#commentCreateExpand {
	padding: 0 16px 16px;
}
#commentCreateInput {
	width: 100%;
	min-height: 160px;
	overflow: visible;
	outline: none;
}
#commentListCard {
	background: white;
	margin: 20px 0 0;
	padding: 10px 20px 15px;
	border: 1px solid rgba(0, 0, 0, 0.09);
	box-shadow: 0 1px 4px rgba(0, 0, 0, 0.04);
}
#commentInfo {
	display: flex;
	justify-content: space-between;
	margin: 0 0 15px;
	padding: 5px 0 0;
}
#commentWriter {
	text-decoration: none;
	font-size: 16px;
}
#commentDate {
	font-size: 14px;
	color: rgba(0, 0, 0, 0.68);
}
#commentContent {
	overflow: auto;
	white-space: pre-wrap;
}
</style>
