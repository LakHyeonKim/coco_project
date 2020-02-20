<template>
	<div style="margin: 10px;" class="post_click">
		<div @click.prevent="goDetail(idpost)">
			<div
				v-for="tag in tags"
				:key="tag.idtag"
				style="display: inline-block;"
			>
				<span
					:class="
						selTag == tag
							? { post_tag_deep: true }
							: { post_tag: true }
					"
					@click.stop="getSearchDataEmit(2, tag)"
				>
					{{ tag }}
				</span>
			</div>
			<div class="post_title">
				{{ postTitle }}
			</div>
			<div class="post_create">
				<img class="post_profile" :src="$store.state.targetImgUrl" />
				<div class="post_nickname">
					{{ postWriter }}
				</div>
				<div class="post_date">
					{{ dateCreated }}
				</div>
			</div>
			<div class="post_code">
				<vue-markdown
					class="line-numbers match-braces rainbow-braces show-invisibles line-clamp-body"
					:source="code"
					data-download-link
					id="mark"
					:style="
						temp_width < 600
							? 'maxHeight: 75px; width:100%'
							: 'maxHeight: 195px'
					"
				></vue-markdown>
			</div>
		</div>
		<div class="like_comment">
			<img
				:id="idpost"
				class="like_img"
				:src="
					likeCheck == 1
						? '../img/icons/tack_full.png'
						: '../img/icons/tack_empty.png'
				"
				width="35px"
				@click.stop="likeEmit(idpost, index)"
			/>
			<MemberList class="counting_click" :followList="likeList">
				<div slot="click">
					<div class="like_text" @click="getLike(idpost)">
						{{ likeCount }}
					</div>
				</div>
			</MemberList>
			<!-- <div class="like_text">{{  likeCount }}</div> -->
			<img src="../assets/icon/chat.png" class="comment_img" />
			<div class="comment_text">{{ commentCount }}</div>
		</div>
	</div>
</template>
<script>
import http from "../http-common";
import store from "../store";
import MemberList from "@/components/MemberList";
import Prism from "../prism";

export default {
	name: "testtt",
	store,
	components: {
		MemberList
	},
	props: {
		index: {},
		idpost: {},
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
		commentCount: {}
	},
	data() {
		return {
			dialog: false,
			selTag: "",
			post_tag_deep: {
				float: "left",
				marginRight: "6px",
				fontSize: "13px",
				borderRadius: "8px",
				paddingLeft: "5px",
				paddingRight: "5px",
				color: "white",
				backgroundColor: "#7d4879"
			},
			post_tag: {
				float: "left",
				marginRight: "6px",
				fontSize: "13px",
				borderRadius: "8px",
				paddingLeft: "5px",
				paddingRight: "5px",
				color: "white",
				backgroundColor: "rgba(160, 23, 98, 0.5)"
			},
			likeList: {},
			temp_width: 0,
			now_width: 0
		};
	},
	methods: {
		getLike(idx) {
			http.post(
				"/api/findWhoPressedTheLikeButton",
				{
					member: {
						idmember: this.$session.get("id")
					},
					post: {
						idpost: idx
					}
				},
				{
					headers: {
						Authorization:
							this.$session.get("accessToken") == undefined
								? null
								: this.$session.get("accessToken")
					}
				}
			)
				.then(response => {
					if (response.status == 203) {
						console.log("refresh token -> server");
						http.post(
							"/jwt/getAccessTokenByRefreshToken/",
							this.$session.get("refreshToken") == undefined
								? null
								: this.$session.get("refreshToken")
						)
							.then(ref => {
								console.log(ref);

								if (ref.status == 203) {
									this.$session.destroy();
									alert("로그인 정보가 만료되었습니다.");
									this.$router.push("/");
								} else {
									this.$session.set("accessToken", ref.data);
									this.getLike(idx);
								}
							})
							.catch(error => {
								console.log(error);
							});
					} else {
						console.log("getLike()");
						console.log(response.data);
						this.likeList = response.data;
					}
				})
				.catch(error => {
					console.log(error);
				});
		},
		goDetail(detail) {
			this.$router.push({ name: "detail", params: { idPost: detail } });
		},
		getSearchDataEmit(sel, text) {
			this.$emit("getSearchData", sel, text);
		},
		likeEmit(postNum, index) {
			this.$emit("like", postNum, index);
		},
		onResize() {
			this.temp_width = window.innerWidth;
		}
	},
	mounted() {
		this.$nextTick(() => {
			window.addEventListener("resize", this.onResize);
		});
	},
	created() {
		Prism.highlightAll();
	},
	watch: {
		temp_width(newWidth, oldWidth) {
			this.now_width = `it changed to ${newWidth} from ${oldWidth}`;
		}
	},
	beforeDestroy() {
		window.removeEventListener("resize", this.onResize);
	}
};
</script>
<style>
@import "../prism.css";

#loading {
	display: none;
	width: 100%;
	margin: 20px auto 20px auto;
	/* display: grid; */
	justify-content: center;
}
.loader {
	/* margin: 20px auto 20px auto; */
	border: 6px solid #f3f3f3; /* Light grey */
	border-top: 6px solid #3498db; /* Blue */
	border-radius: 50%;
	width: 60px;
	height: 60px;
	animation: spin 2s linear infinite;
	margin-top: 100px;
	margin-bottom: 200px;
}
#noContents {
	text-align: center;
	padding-top: 100px;
	padding-bottom: 100px;
	font-size: 20px;
	font-weight: 500;
}
#search {
	margin-left: 15px;
	display: inline-block;
	padding: 0;
	/* float: right; */
}
#search_text {
	float: left;
	margin-top: 19px;
	padding: 0;
	width: 15vw;
	height: 30px;
	border-bottom: 0.9px solid rgba(0, 0, 0, 0.4);
	-webkit-transition: width 0.4s ease-in-out;
	transition: width 0.4s ease-in-out;
}

#search_text:focus {
	outline: none;
	width: 30vw;
}
#search_img {
	margin-top: 20px;
	float: left;
	width: 20px;
	cursor: pointer;
}
#search_img:hover {
	opacity: 0.5;
}
/* id : search_sel */
#search > div {
	float: left;
	width: 90px;
}
#post_top {
	margin-top: 10px;
}
/* 라벨 */
#post_top
	> div.v-input.v-input--dense.theme--light.v-text-field.v-text-field--is-booted.v-select
	> div
	> div.v-input__slot
	> div.v-select__slot
	> label {
	font-size: 13px;
}

/* id : orderSel */
#post_top
	> div.v-input.v-input--dense.theme--light.v-text-field.v-text-field--is-booted.v-select {
	width: 100px;
	float: left;
	margin-top: 17px;
}

/* 라벨 */
#search > div > div > div.v-input__slot > div.v-select__slot > label {
	font-size: 13px;
}

@media screen and (max-width: 660px) {
	#noContents {
		font-size: 15px;
	}

	/* id : search_sel */
	#search > div {
		width: 70px;
	}

	#search
		> div
		> div
		> div.v-input__slot
		> div.v-select__slot
		> div.v-select__selections
		> div {
		font-size: 13px;
	}

	#search > div > div > div.v-input__slot > div.v-select__slot > label {
		font-size: 12px;
	}

	/* id : orderSel */
	#post_top
		> div.v-input.v-input--dense.theme--light.v-text-field.v-text-field--is-booted.v-select {
		width: 70px;
	}

	#post_top
		> div.v-input.v-input--is-label-active.v-input--is-dirty.v-input--dense.theme--light.v-text-field.v-text-field--is-booted.v-select
		> div
		> div.v-input__slot
		> div.v-select__slot
		> div.v-select__selections
		> div {
		font-size: 13px;
	}

	#post_top
		> div.v-input.v-input--dense.theme--light.v-text-field.v-text-field--is-booted.v-select
		> div
		> div.v-input__slot
		> div.v-select__slot
		> label {
		font-size: 12px;
	}

	#search_text {
		width: 15vw;
	}
	#search_text:focus {
		width: 25vw;
	}
}

#post_top {
	display: inline-block;
	width: 100%;
}

.slash {
	margin-top: 20px;
	margin-bottom: 20px;
	border-bottom: 1.5px solid rgba(0, 0, 0, 0.06);
}

.post_click {
	padding: 10px;
	border-radius: 10px;
}

.post_click:hover {
	box-shadow: 0.5px 0.5px 5px rgba(0, 0, 0, 0.3);
	cursor: pointer;
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
	overflow: hidden;
}
.post_tag_deep {
	float: left;
	cursor: pointer;
	margin-right: 6px;
	font-size: 13px;
	border-radius: 8px;
	padding-left: 5px;
	padding-right: 5px;
	color: white;
	background-color: #7d4879;
}

.post_tag:hover {
	background-color: #7d4879;
}

.post_tag {
	float: left;
	cursor: pointer;
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
	height: 20px;
	margin-top: 4px;
	border: 1px solid rgba(0, 0, 0, 0.2);
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
	/* overflow: hidden;
	display: -webkit-box;
	-webkit-line-clamp: 3;
	-webkit-box-orient: vertical; */
	margin-bottom: 5px;
}
.line-clamp-body {
	color: rgb(27, 27, 27);
	overflow: hidden;
	display: -webkit-box;
	-webkit-line-clamp: 8;
	-webkit-box-orient: vertical;
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
