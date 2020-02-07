<template>
	<div style="background-color:white;" class="portfolioBox">
		<div id="cardBox">
			<div id="contentBox">
				<div id="cardHead">
					<div id="imgDiv">
						<img src="../assets/user.png" id="userImg" />
					</div>
					<div id="userTitle">
						<p id="userId">{{ postWriter }}</p>
						<p id="date">{{ dateCreated }}</p>
					</div>
				</div>
				<div id="cardTitle">
					<div class="line-clamp-title">
						<b>{{ postTitle }}</b>
					</div>
				</div>
				<div id="cardHash">
					<div v-for="tag in tags" :key="`${tag}`">
						<a href="#" id="hashTag"> #{{ tag }} </a>
					</div>
				</div>
				<!-- <div id="imgBox" v-if="imagePath">
					<v-img :src="imagePath" height="150px" width="100%"></v-img>
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
		</div>
	</div>
</template>

<script>
export default {
	name: "MobilePortfolio",
	props: {
		dateCreated: {},
		postTitle: {},
		code: {},
		// imagePath: {},
		postWriter: {},
		likeCount: {},
		commentCount: {},
		tags: {},
		idPost: {},
		memberId: {},
		updateCreated: {},
		views: {},
		access: {},
		order: {}
	},
	data() {
		return {
			//
		};
	}
};
</script>

<style scoped>
.portfolioBox {
	border-bottom: 1px solid rgba(0, 0, 0, 0.2);
	border-top: 1px solid rgba(0, 0, 0, 0.2);
	/* box-shadow: 2px 2px 5px rgba(0, 0, 0, 0.2); */
	/* height: 250px; */
}
#cardBox {
	margin: 10px;
	/* display: flex; */
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
	/* margin-left: 10px; */
	height: 160px;
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
