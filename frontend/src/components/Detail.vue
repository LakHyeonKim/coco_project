<template>
	<div>
		<div id="blankBox"></div>
		<div id="detailBox">
			<div>
				<h1>{{ postTitle }}</h1>
				<br />
				<div id="post-head">
					<div id="profile-img">
						<v-avatar size="40">
							<img src="imagePath" alt="user-img" v-if="imagePath" />
							<img src="../assets/user.png" alt="default-img" v-else />
							<!-- <v-gravatar :email="postWriter" alt="gravatar" :size="50" /> -->
						</v-avatar>
					</div>

					<div id="profile-info">
						<p id="user-nickname">
							{{ postWriter }}
							<v-btn class="ml-2 follow-btn" color="indigo" height="20px" @click="follow" outlined small>
								<span v-show="!isFollow">팔로우</span>
								<span v-show="isFollow">팔로잉</span>
								<v-icon v-show="!isFollow" small>mdi-plus-thick</v-icon>
								<v-icon v-show="isFollow" small>mdi-check-bold</v-icon>
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
							<v-icon>fa fa-ellipsis-h</v-icon>
						</button>
					</div>
				</div>

				<div id="divide-line"></div>

				<comment-create :idPost="idPost" :memberId="memberId" @addComment="addComment"></comment-create>

				<comment-list :comments="comments"></comment-list>
			</div>
		</div>
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
		comments: {},
		likes: {},
		babyPosts: {},
		commentCount: {},
		attachments: {}
	},
	data() {
		return {};
	},
	methods: {
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
					memberFollower: this.memberId,
					memberFollowing: this.$session.get("id")
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
