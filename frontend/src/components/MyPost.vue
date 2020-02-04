<template>
	<div id="posts">
		<div id="post_list">
			<div class="post" v-for="item in posts" :key="item.post.idpost">
				<div style="margin: 10px;">
					<div>
						<span class="post_tag">#java</span>
						<span class="post_tag">#javascript</span>
						<span class="post_tag">#eclipse</span>
					</div>
					<div class="post_title">{{ item.post.postTitle }}</div>
					<div class="post_create">
						<img class="post_profile" src="../assets/user.png" />
						<div class="post_nickname">{{ item.post.postWriter }}</div>
						<div class="post_date">{{ item.post.dateCreated }}</div>
					</div>
					<div class="post_code">{{ item.post.code }}</div>
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
	name: "mypost",
	store,
	data() {
		return {
			posts: "",
			postTags: ""
		};
	},
	methods: {
		getPostTag(idx) {}
	},
	mounted() {
		alert(this.$session.get("jwt"));
		http.post("/api/findByMyPosts/", this.$session.get("id"))
			.then(response => {
				this.posts = response.data;
				console.log(this.posts);
			})
			.catch(error => {
				console.log(error);
			})
			.finally(() => (this.loading = false));
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
}
</style>
