<template>
	<div id="commentCreateCard">
		<v-card class="mx-auto">
			<v-card-text id="commentCreateInfo">
				<v-avatar size="40">
					<img
						:src="$session.get('imageUrl')"
						alt="../assets/user.png"
					/>
				</v-avatar>
				<!-- <img id="commentCreaterImg" :src="$session.get('imgUrl')" alt="../assets/user.png" /> -->
				<v-card-title
					id="commentCreatePlaceholder"
					v-show="!show"
					v-if="commentCount"
					@click="moveinFocus"
					>댓글을 작성해주세요</v-card-title
				>
				<v-card-title
					id="commentCreatePlaceholder"
					v-show="!show"
					v-else
					@click="moveinFocus"
					>첫번째 댓글을 작성하세요</v-card-title
				>
				<v-card-subtitle id="commentCreateNickname" v-show="show">{{
					$session.get("nickName")
				}}</v-card-subtitle>
			</v-card-text>

			<v-expand-transition>
				<div v-show="show">
					<v-card-text id="commentCreateExpand">
						<textarea
							:id="'commentCreateInput-' + idPost"
							@blur="moveoutFocus"
							v-model="commentContent"
						/>
						<v-btn outlined @click="commenting">댓글 작성</v-btn>
					</v-card-text>
				</div>
			</v-expand-transition>
		</v-card>
	</div>
</template>

<script>
import http from "../http-common";
export default {
	name: "CommentCreate",
	props: {
		idPost: {},
		memberId: {},
		commentCount: {}
	},
	data() {
		return {
			show: false,
			commentContent: ""
		};
	},
	methods: {
		expandCard() {
			if (!this.show) {
				this.show = !this.show;
			}
		},
		async moveinFocus() {
			await this.expandCard();
			document
				.getElementById(`commentCreateInput-${this.idPost}`)
				.focus();
		},
		moveoutFocus() {
			if (
				!document.getElementById(`commentCreateInput-${this.idPost}`)
					.value
			) {
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
						commentWriter: this.$session.get("nickName"),
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
					userComment.comments[0].idcomment = res.data;
					this.$store.state.commentContent = userComment.comments[0];
					this.$emit("addComment", userComment.comments[0]);
					this.commentContent = "";
					this.show = !this.show;
				})
				.catch(err => {
					console.log(err);
				});
		}
	}
};
</script>

<style>
#commentCreateCard {
	-webkit-font-smoothing: antialiased;
}
#commentCreateInfo {
	display: flex;
	align-items: center;
}
/* #commentCreaterImg {
	width: 40px;
	border-radius: 50%;
} */
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
/* #commentCreateInput {
	width: 100%;
	min-height: 160px;
	overflow: visible;
	outline: none;
} */
textarea[id^="commentCreateInput"] {
	width: 100%;
	min-height: 160px;
	overflow: visible;
	outline: none;
	resize: none;
}
</style>
