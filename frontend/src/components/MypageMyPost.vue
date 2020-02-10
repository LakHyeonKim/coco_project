<template>
	<div id="posts">
		<div id="post_list">
			<div style="height: 50px; margin-top: 15px;">
				<img
					src="../assets/icon/sort_b.png"
					width="25px"
					style="float: left; margin-top: 7px; margin-right: 10px; opacity: 0.5;"
				/>
				<v-select
					:items="postSels"
					dense
					item-color="black"
					color="rgba(0, 0, 0, 0.5)"
					@change="chnagePostSel"
					placeholder="정렬조건"
					style="width: 100px; float: left; font-size: 15px;"
				></v-select>
			</div>
			<div
				class="post"
				v-for="(item, index) in posts"
				:key="item.post.idpost"
			>
				<div style="margin: 10px;">
					<div
						v-for="tag in item.tags"
						:key="tag.idtag"
						style="display: inline-block;"
					>
						<span class="post_tag">{{ tag }}</span>
					</div>
					<div class="post_title">{{ item.post.postTitle }}</div>
					<div class="post_create">
						<img class="post_profile" src="../assets/user.png" />
						<div class="post_nickname">
							{{ item.post.postWriter }}
						</div>
						<div class="post_date">{{ item.post.dateCreated }}</div>
					</div>
					<div class="post_code">{{ item.post.code }}</div>
					<div class="like_comment">
						<img
							:id="item.post.idpost"
							class="like_img"
							:src="
								item.post.likeCheck == 1
									? './img/icons/tack_full.png'
									: './img/icons/tack_empty.png'
							"
							width="35px"
							@click="like(item.post.idpost, index)"
						/>
						<div class="like_text">
							{{ item.post.likeCount }}
						</div>
						<img
							src="../assets/icon/chat.png"
							class="comment_img"
						/>
						<div class="comment_text">
							{{ item.commentCount }}
						</div>
					</div>
				</div>
				<div class="line" />
			</div>
		</div>
	</div>
</template>
<script>
import http from "../http-common";
import store from "../store";
export default {
	name: "MypageMyPost",
	store,
	data() {
		return {
			posts: "",
			postTags: "",
			postSels: [
				{ text: "최신순", value: "4" },
				{ text: "오래된순", value: "3" },
				{ text: "좋아요순", value: "2" }
			],
			address: ""
		};
	},
	methods: {
		chnagePostSel(idx) {
			console.log(idx);
			http.post("/api/findByMyPosts/", {
				myIdMember: this.$session.get("id"),
				order: idx,
				youIdMember: this.$session.get("targetId")
			})
				.then(response => {
					this.posts = response.data;
					console.log(this.posts);
				})
				.catch(error => {
					console.log(error);
				});
		},
		like(postNum, index) {
			console.log("글번호 : " + postNum + "| index : " + index);
			console.log("멤버 ID : " + this.$session.get("id"));
			if (this.posts[index].post.likeCheck == 1) {
				this.address = "/trc/unLike/";
				this.posts[index].post.likeCheck = 0;
				this.posts[index].post.likeCount--;
			} else {
				this.address = "/trc/pushLike/";
				this.posts[index].post.likeCheck = 1;
				this.posts[index].post.likeCount++;
			}
			console.log(this.address);
			http.post(this.address, {
				member: {
					idmember: this.$session.get("id")
				},
				post: {
					idpost: postNum
				}
			})
				.then(response => {})
				.catch(error => {
					console.log(error);
					if (this.posts[index].post.likeCheck == 1) {
						this.posts[index].post.likeCheck = 0;
						this.posts[index].post.likeCount--;
					} else {
						this.posts[index].post.likeCheck = 1;
						this.posts[index].post.likeCount++;
					}
				});
		}
	},
	mounted() {
		http.post("/api/findByMyPosts/", {
			myIdMember: this.$session.get("id"),
			order: 4,
			youIdMember: this.$session.get("targetId")
		})
			.then(response => {
				this.posts = response.data;
				// console.log(response);
			})
			.catch(error => {
				console.log(error);
			});
		// .finally(() => (this.loading = false));
	}
};
</script>
<style>
.line {
	margin-top: 20px;
	margin-bottom: 20px;
	border-bottom: 1.5px solid rgba(0, 0, 0, 0.06);
}

.post {
	margin-top: 20px;
	font-weight: 300;
}
#post_list {
	width: 80%;
	margin: 0 auto;
}
.post_title {
	font-size: 20px;
	font-weight: 400;
	padding-top: 5px;
	padding-bottom: 5px;
	text-overflow: ellipsis;
	white-space: nowrap;
}
.post_tag {
	float: left;
	margin-right: 6px;
	font-size: 13px;
	border-radius: 8px;
	padding-left: 5px;
	padding-right: 5px;
	color: white;
	background-color: rgba(160, 23, 98, 0.5);
}
.post_create {
	display: inline-block;
	height: 30px;
}
.post_profile {
	float: left;
	border-radius: 50%;
	width: 20px;
	margin-top: 4px;
	border: 1px solid rgba(0, 0, 0, 0.5);
	margin-right: 3px;
}
.post_nickname {
	float: left;
	font-size: 13px;
	line-height: 30px;
	margin-right: 7px;
}
.post_date {
	float: left;
	font-size: 11px;
	line-height: 30px;
	color: gray;
}
.post_code {
	overflow: hidden;
	display: -webkit-box;
	-webkit-line-clamp: 3;
	-webkit-box-orient: vertical;
	margin-bottom: 5px;
}
.like_comment {
	display: inline-block;
}
.like_text,
.like_img,
.comment_text,
.comment_img {
	float: left;
}

.like_img {
	width: 35px;
	border-radius: 50%;
	transition: all ease-in-out 0.3s;
}

.like_img:hover {
	background-color: rgba(128, 128, 128, 0.2);
	transition: all ease-in 0.2s;
}
.like_img:active {
	background-color: rgba(128, 128, 128, 0.5);
}
.comment_img {
	width: 30px;
	margin: 7px 3px 0 10px;
}

.comment_text,
.like_text {
	font-weight: 400;
	margin-top: 10px;
	font-size: 15px;
}
</style>
