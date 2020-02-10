<template>
	<div style="background-color:rgba(0, 0, 0,0.0);" class="postBox">
		<div id="cardBox">
			<div id="contentBox">
				<div id="cardHead">
					<div id="imgDiv">
						<img src="../assets/user.png" id="userImg" />
					</div>
					<div id="userTitle">
						<p id="userId" @click.prevent="goYourPage(memberId)">
							{{ postWriter }}
						</p>
						<p id="date">{{ dateCreated }}</p>
					</div>
				</div>
				<div id="cardTitle">
					<div class="line-clamp-title" @click.prevent="goDetail()">
						<b>{{ postTitle }}</b>
					</div>
				</div>
				<div id="cardHash">
					<div v-for="tag in tags" :key="`${tag}`">
						<a id="hashTag" @click.prevent="goSearch(`${tag}`)">
							#{{ tag }}
						</a>
					</div>
				</div>
				<div id="cardBody">
					<span class="line-clamp-body">
						{{ code }}
					</span>
				</div>
				<div id="cardFooter">
					<div id="like">좋아요 {{ likeCount }}개</div>
					<div id="comment">댓글 {{ commentCount }}개</div>
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
import store from "../store";

export default {
	name: "Post",
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
		commentCount: {}
	},
	data() {
		return {
			//
		};
	},
	methods: {
		goDetail() {
			// console.log("alsdkfjlaskdfj", this.idPost);
			// store.dispatch("saveIdPost", this.idPost);
			// console.log("idPOst", store.state.idPost);
			router.push("/detail/" + this.idPost);
		},
		goSearch(tag) {
			// console.log(word);
			store.dispatch("saveSearchTag", tag);
			router.push("/search");
		},
		goYourPage(memberId) {
			this.$session.set("targetId", memberId);
			router.push("/mypage/" + memberId);
		}
	}
};
</script>

<style scoped>
.postBox {
	border: 1px solid rgba(0, 0, 0, 0.2);
	box-shadow: 2px 2px 5px rgba(0, 0, 0, 0.2);
	height: 250px;
}
#cardBox {
	margin: 13px 13px 13px 13px;
	display: flex;
}
#contentBox {
	flex: 1;
}
#cardHead {
	display: flex;
	margin-bottom: 7px;
}
#imgDiv {
	width: 40px;
	height: 40px;
	margin-right: 10px;
}
#userImg {
	width: 40px;
	height: 40px;
	border-radius: 25px;
	background-color: black;
}
#userId {
	color: black;
	font-size: 15px;
	margin-bottom: 2px;
	height: 17px;
}
#date {
	color: gray;
	font-size: 12px;
	margin: 0;
	height: 17px;
}
#cardTitle {
	margin-bottom: 7px;
	/* margin-left: 10px; */
}
.line-clamp-title {
	font-size: 20px;
	overflow: hidden;
	display: -webkit-box;
	-webkit-line-clamp: 1;
	-webkit-box-orient: vertical;
}
#cardHash {
	display: flex;
	margin-bottom: 7px;
}
#hashTag {
	font-size: 16px;
	color: blue;
	text-decoration: none;
	margin-right: 5px;
}
#cardBody {
	min-height: 75px;
	margin-bottom: 10px;
	/* margin-left: 10px; */
}
.line-clamp-body {
	color: rgb(27, 27, 27);
	overflow: hidden;
	display: -webkit-box;
	-webkit-line-clamp: 3;
	-webkit-box-orient: vertical;
}
#like {
	display: inline-block;
	/* margin-left: 20px; */
	margin-right: 10px;
}
#comment {
	display: inline-block;
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
</style>
