<template>
	<div
		style="background-color:rgba(0, 0, 0,0.0);"
		class="postBox"
		@click.prevent="goDetail()"
	>
		<div id="cardBox">
			<div id="contentBox">
				<div id="cardHash">
					<button v-for="tag in tags" :key="`${tag}`">
						<a id="hashTag" @click.stop="goSearchTag(`${tag}`)">
							{{ tag }}
						</a>
					</button>
				</div>

				<div id="cardTitle">
					<div class="line-clamp-title">
						<b>{{ postTitle }}</b>
					</div>
				</div>

				<div id="cardHead">
					<img
						:src="
							userImg == undefined || userImg == ''
								? '../img/icons/user.png'
								: userImg
						"
						id="userImg"
					/>
					<div id="userId" @click.stop="goYourPage(memberId)">
						{{ postWriter }}
					</div>
					<div id="date">{{ dateCreated }}</div>
				</div>

				<div id="cardBody">
					<vue-markdown
						class="line-numbers match-braces rainbow-braces show-invisibles line-clamp-body"
						:source="code"
						data-download-link
						id="mark"
						:style="
							temp_width < 600
								? 'maxHeight: 75px; width:100vw'
								: 'maxHeight: 195px'
						"
					></vue-markdown>
				</div>

				<div id="cardFooter">
					<img
						:id="idPost"
						class="like_img"
						:src="
							likeCheck == 1
								? './img/icons/tack_full.png'
								: './img/icons/tack_empty.png'
						"
						width="35px"
						@click.stop="likeEmit(idPost, postIdx)"
					/>
					<div id="likeCount">
						{{ likeCount }}
					</div>
					<img src="../assets/icon/chat.png" class="comment_img" />
					<div id="commentCount">
						{{ commentCount }}
					</div>
				</div>
			</div>
			<!-- <div id="imgBox" v-if="imagePath">
				<v-img :src="imagePath" height="220px" width="220px"></v-img>
				<div id="stackImg">
					<div v-for="tag in tags" :key="`${tag.idtag}`">
						{{ tag.imagePath }}
					</div>
					<img src="../assets/css.png" alt="" class="stackImgs" />
					<img src="../assets/JS.png" alt="" class="stackImgs" />
					<img src="../assets/vue.png" alt="" class="stackImgs" />
				</div>
			</div>
			<div id="noStackImg" v-else>
				<img src="../assets/css.png" alt="" class="stackImgs" />
				<img src="../assets/JS.png" alt="" class="stackImgs" />
				<img src="../assets/vue.png" alt="" class="stackImgs" />
			</div> -->
		</div>
	</div>
</template>

<script>
import router from "../router";
import http from "../http-common";
import Prism from "../prism";

export default {
	name: "SearchList",
	props: {
		// imagePath: {},
		postIdx: {},
		idPost: {},
		memberId: {},
		postTitle: {},
		postWriter: {},
		dateCreated: {},
		updateCreated: {},
		code: {},
		likeCount: {},
		views: {},
		access: {},
		likeCheck: {},
		order: {},
		tags: {},
		commentCount: {}
	},
	data() {
		return {
			tagforsearch: "",
			userImg: "",
			temp_width: 0,
			now_width: 0
		};
	},
	methods: {
		goDetail() {
			if (this.memberId != this.$session.get("id")) {
				// console.log("alsdkfjlaskdfj", this.idPost);
				// store.dispatch("saveIdPost", this.idPost);
				// console.log("idPOst", store.state.idPost);
				const token = this.$session.get("accessToken");
				const headers = {
					Authorization: token
				};
				const requestForm = {
					member: {
						idmember: this.$session.get("id")
					},
					post: {
						idpost: this.idPost
					}
				};
				console.log("goDetail requestForm ", requestForm);
				http.post("/trc/postClick/", requestForm, { headers })
					.then(res => {
						console.log("postclick then ", res);
					})
					.catch(err => {
						console.log("postclick catch ", err);
					});
				router.push("/detail/" + this.idPost);
			} else {
				router.push("/detail/" + this.idPost);
				console.log("justgo");
			}
		},
		goSearchTag(tag) {
			// console.log(word);
			// store.dispatch("saveSearchTag", tag);
			// router.push("/search");
			console.log(tag);
			this.$emit("searchtag", tag);
		},
		goYourPage(memberId) {
			// this.$session.set("targetId", memberId);
			router.push("/mypage/" + memberId);
		},
		likeEmit(postNum, index) {
			// const form = {
			// 	postId: postNum,
			// 	postIdx: index
			// };
			this.$emit("like", postNum, index);
		}
	},
	mounted() {
		const token = this.$session.get("accessToken");
		const headers = {
			Authorization: token
		};
		http.post("/api/findMember", { idmember: this.memberId }, { headers })
			.then(res => {
				console.log("findMember res ", res);
				this.userImg = res.data[0].imageUrl;
			})
			.catch(err => {
				console.log("findMember err ", err);
			});
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

<style scoped>
@import "../prism.css";

.postBox {
	border: 1px solid rgba(0, 0, 0, 0.05);
	/* box-shadow: 2px 2px 5px rgba(0, 0, 0, 0.2); */
	/* height: 400px; */
	height: 100%;
}
.postBox:hover {
	box-shadow: 2px 2px 5px rgba(0, 0, 0, 0.2);
	cursor: pointer;
}
#cardBox {
	margin: 13px 13px 13px 13px;
	display: flex;
}
#contentBox {
	flex: 1;
	display: grid;
}
#cardHead {
	display: inline-block;
	margin-bottom: 7px;
	height: 30px;
}
#imgDiv {
	width: 20px;
	height: 20px;
	/* margin-right: 10px; */
}
#userImg {
	float: left;
	border-radius: 50%;
	width: 20px;
	height: 20px;
	margin-top: 4px;
	border: 1px solid rgba(0, 0, 0, 0.5);
	margin-right: 3px;
}
#userId {
	float: left;
	color: black;
	font-size: 13px;
	line-height: 30px;
	margin-right: 7px;
}
#date {
	float: left;
	font-size: 11px;
	line-height: 30px;
	color: gray;
}
#cardTitle {
	padding-top: 5px;
	padding-bottom: 5px;
	/* margin-left: 10px; */
}
.line-clamp-title {
	font-size: 20px;
	font-weight: 400;
	overflow: hidden;
	display: -webkit-box;
	-webkit-line-clamp: 1;
	-webkit-box-orient: vertical;
}
#cardHash {
	/* display: flex; */
	margin-bottom: 7px;
}
#hashTag {
	float: left;
	margin-right: 6px;
	font-size: 13px;
	border-radius: 8px;
	padding-left: 5px;
	padding-right: 5px;
	color: white;
	background-color: rgba(160, 23, 98, 0.5);
	white-space: nowrap;
}
#hashTag:hover {
	background-color: rgba(160, 23, 98, 1);
}
#cardBody {
	/* min-height: 75px; */
	margin-bottom: 5px;
	/* margin-left: 10px; */
}
.line-clamp-body {
	color: rgb(27, 27, 27);
	overflow: hidden;
	display: -webkit-box;
	-webkit-line-clamp: 8;
	-webkit-box-orient: vertical;
}
#cardFooter {
	display: inline-block;
}
.like_img {
	float: left;
	width: 35px;
	border-radius: 50%;
	transition: all ease-in-out 0.3s;
}
#likeCount {
	float: left;
	font-weight: 400;
	margin-top: 10px;
	font-size: 15px;
}
.comment_img {
	float: left;
	width: 30px;
	margin: 7px 3px 0 10px;
}
#commentCount {
	float: left;
	font-weight: 400;
	margin-top: 10px;
	font-size: 15px;
}
#imgBox {
	margin-left: 10px;
	width: 200px;
}
#stackImg {
	position: relative;
	text-align: end;
	transform: translateY(-100%);
}
.stackImgs {
	width: 35px;
	height: 35px;
	border-radius: 50%;
	background-color: white;
}
#noStackImg {
	position: relative;
	/* justify-content: center; */
	/* align-items: center; */
	/* text-align: end; */
	/* transform: translateY(410%); */
	/* right: 0;
	margin-right: 11vw; */
}
@media screen and (max-width: 600px) {
	.postBox {
		/* margin: 0px; */
		border: none;
		box-shadow: none;
		border-bottom: 0.75px solid rgba(0, 0, 0, 0.2);
		/* border-top: 0.25px solid rgba(0, 0, 0, 0.2); */
		/* height: 100%; */
		/* display: block; */
	}
	#cardBox {
		margin: 10px 10px 5px 10px;
		display: block;
	}
	#contentBox {
		display: grid;
	}
	#cardHead {
		/* display: inline-block; */
		margin-bottom: 3px;
		/* height: 30px; */
	}
	#imgDiv {
		width: 20px;
		height: 20px;
		/* margin-right: 10px; */
	}
	#userImg {
		/* float: left;
		border-radius: 50%;
		width: 20px;
		margin-top: 4px;
		border: 1px solid rgba(0, 0, 0, 0.5);
		margin-right: 3px; */
	}
	#userId {
		/* float: left;
		color: black;
		font-size: 13px;
		line-height: 30px;
		margin-right: 7px; */
	}
	#date {
		/* float: left;
		font-size: 11px;
		line-height: 30px;
		color: gray; */
	}
	#cardTitle {
		padding-top: 2px;
		padding-bottom: 2px;
		/* margin-left: 10px; */
	}
	.line-clamp-title {
		/* font-size: 20px;
		font-weight: 400;
		overflow: hidden;
		display: -webkit-box;
		-webkit-line-clamp: 1;
		-webkit-box-orient: vertical; */
	}
	#cardHash {
		/* display: flex; */
		margin-bottom: 4px;
	}
	#hashTag {
		/* float: left;
		margin-right: 6px;
		font-size: 13px;
		border-radius: 8px;
		padding-left: 5px;
		padding-right: 5px;
		color: white;
		background-color: rgba(160, 23, 98, 0.5); */
	}
	#cardBody {
		display: block;
		/* min-height: 75px; */
		margin-bottom: 5px;
		/* margin-left: 10px; */
	}
	.line-clamp-body {
		/* color: rgb(27, 27, 27);
		overflow: hidden;
		display: -webkit-box;
		-webkit-line-clamp: 3;
		-webkit-box-orient: vertical; */
	}
	#cardFooter {
		display: block;
		position: unset;
	}
	.like_img {
		/* float: left;
		width: 35px;
		border-radius: 50%;
		transition: all ease-in-out 0.3s; */
	}
	#likeCount {
		/* float: left;
		font-weight: 400;
		margin-top: 10px;
		font-size: 15px; */
	}
	.comment_img {
		/* float: left;
		width: 30px;
		margin: 7px 3px 0 10px; */
	}
	#commentCount {
		/* float: left;
		font-weight: 400;
		margin-top: 10px;
		font-size: 15px; */
	}
	#imgBox {
		/* margin-left: 10px;
		width: 200px; */
	}
	#stackImg {
		/* position: relative;
		text-align: end;
		transform: translateY(-100%); */
	}
	.stackImgs {
		/* width: 35px;
		height: 35px;
		border-radius: 50%;
		background-color: white; */
	}
	#noStackImg {
		/* position: relative; */
		/* justify-content: center; */
		/* align-items: center; */
		/* text-align: end; */
		/* transform: translateY(410%); */
		/* right: 0;
		margin-right: 11vw; */
	}
}
</style>
