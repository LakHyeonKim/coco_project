<template>
	<div id="posts">
		<MypageMyMenu :search="getSearchData" />
		<div id="post_list">
			<div id="post_top">
				<v-select
					:items="postSels"
					dense
					item-color="black"
					color="rgba(0, 0, 0, 0.5)"
					@change="chnagePostSel"
					label="정렬조건"
					id="orderSel"
					v-model="orderSel"
				></v-select>
				<div id="search">
					<v-select
						label="검색조건"
						:items="items"
						@change="changeMenuSel"
						color="rgba(0, 0, 0, 0.5)"
						item-color="black"
						id="search_sel"
						v-model="searchSel"
					/>
					<input
						v-model="menu_text"
						type="text"
						id="search_text"
						@keypress.enter="search()"
					/>
					<img
						id="search_img"
						@click="search()"
						src="../assets/icon/search_b.png"
					/>
				</div>
			</div>
			<div
				id="loading"
				:style="loadingTop ? loadingStyleOn : loadingStyleOff"
				v-if="loadingTop"
			>
				<div class="loader"></div>
			</div>
			<div class="post" v-for="i in posts.length" :key="i">
				<MypageMyPostCard
					@like="like"
					@getSearchData="getSearchData"
					:index="i - 1"
					:idpost="posts[i - 1].post.idpost"
					:memberId="posts[i - 1].post.memberId"
					:postTitle="posts[i - 1].post.postTitle"
					:postWriter="posts[i - 1].post.postWriter"
					:dateCreated="posts[i - 1].post.dateCreated"
					:updateCreated="posts[i - 1].post.updateCreated"
					:code="posts[i - 1].post.code"
					:likeCount="posts[i - 1].post.likeCount"
					:views="posts[i - 1].post.views"
					:imagePath="posts[i - 1].post.imagePath"
					:filePath="posts[i - 1].post.filePath"
					:access="posts[i - 1].post.access"
					:likeCheck="posts[i - 1].post.likeCheck"
					:order="posts[i - 1].post.order"
					:tags="posts[i - 1].tags"
					:commentCount="posts[i - 1].commentCount"
					:selTag="selTag"
				></MypageMyPostCard>
				<div class="slash" />
			</div>
		</div>
		<div v-if="noContents" id="noContents">
			해당 포스트가 존재하지 않습니다
		</div>
	</div>
</template>
<script>
import http from "../http-common";
import store from "../store";
import MypageMyMenu from "@/components/MypageMyMenu";
import MypageMyPostCard from "@/components/MypageMyPostCard";

export default {
	name: "MypageMyPost",
	store,
	components: {
		MypageMyMenu,
		MypageMyPostCard
	},
	data() {
		return {
			loadingTop: false,
			loadingStyleOn: {
				display: "grid"
			},
			loadingStyleOff: {
				display: "none"
			},
			dialog: false,
			noContents: false,
			posts: [],
			postSels: [
				{ text: "최신순", value: "4" },
				{ text: "오래된순", value: "3" },
				{ text: "좋아요순", value: "2" }
			],
			items: [
				{ text: "전체", value: "1" },
				{ text: "#", value: "2" },
				{ text: "글제목", value: "3" },
				{ text: "글내용", value: "4" }
			],
			address: "",
			selTag: "",
			menuSel: "",
			menu_text: "",
			searchSel: "",
			orderSel: ""
		};
	},
	methods: {
		search() {
			if (this.menuSel == "") {
				alert("검색조건을 선택해주세요!");
				return;
			}
			if (this.menu_text == "") {
				alert("검색어를 입력해주세요!");
				return;
			}
			this.getSearchData(this.menuSel, this.menu_text);
			this.menu_text = "";
		},
		getSearchData(sel, text) {
			let address = "";
			if (sel == 1) {
				address = "/api/findByAllKeywordMyPosts";
			} else if (sel == 2) {
				address = "/api/findByTagKeywordMyPosts";
			} else if (sel == 3) {
				address = "/api/findByPostTitleKeywordMyPosts";
			} else {
				address = "/api/findByPostCodeKeywordMyPosts";
			}
			this.orderSel = null;
			this.posts = "";
			this.loadingTop = true;
			http.post(
				address,
				{
					keyword: text,
					myIdMember: this.$session.get("id"),
					order: 4,
					youIdMember: this.$route.params.no
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
						http.post(
							"/jwt/getAccessTokenByRefreshToken/",
							this.$session.get("refreshToken") == undefined
								? null
								: this.$session.get("refreshToken")
						)
							.then(ref => {
								if (ref.status == 203) {
									this.$session.destroy();
									alert("로그인 정보가 만료되었습니다.");
									this.$router.push("/");
								} else {
									this.$session.set("accessToken", ref.data);
									window.location.reload(true);
								}
							})
							.catch(error => {
								console.log(error);
							});
					} else {
						if (sel == 2) this.selTag = text;
						else this.selTag = "";
						this.posts = response.data;
						if (response.data == "" || this.posts.length == 0)
							this.noContents = true;
						else this.noContents = false;
					}
				})
				.catch(error => {
					console.log(error);
				})
				.finally(() => (this.loadingTop = false));
		},
		chnagePostSel(idx) {
			http.post(
				"/api/findByMyPosts/",
				{
					myIdMember: this.$session.get("id"),
					order: idx,
					youIdMember: this.$route.params.no
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
						http.post(
							"/jwt/getAccessTokenByRefreshToken/",
							this.$session.get("refreshToken") == undefined
								? null
								: this.$session.get("refreshToken")
						)
							.then(ref => {
								if (ref.status == 203) {
									this.$session.destroy();
									alert("로그인 정보가 만료되었습니다.");
									this.$router.push("/");
								} else {
									this.$session.set("accessToken", ref.data);
									this.chnagePostSel(idx);
								}
							})
							.catch(error => {
								console.log(error);
							});
					} else {
						this.selTag = "";
						this.searchSel = null;
						this.posts = "";
						this.loadingTop = true;

						this.posts = response.data;
						console.log(this.posts);
					}
				})
				.catch(error => {
					console.log(error);
				})
				.finally(() => (this.loadingTop = false));
		},
		changeMenuSel(idx) {
			this.menuSel = idx;
		},
		like(postNum, index) {
			// console.log("글번호 : " + postNum + "| index : " + index);
			// console.log("멤버 ID : " + this.$session.get("id"));
			if (this.posts[index].post.likeCheck == 1) {
				this.address = "/trc/unLike/";
				this.posts[index].post.likeCheck = 0;
				this.posts[index].post.likeCount--;
			} else {
				this.address = "/trc/pushLike/";
				this.posts[index].post.likeCheck = 1;
				this.posts[index].post.likeCount++;
			}
			// console.log(this.address);
			http.post(
				this.address,
				{
					member: {
						idmember: this.$session.get("id")
					},
					post: {
						idpost: postNum
					}
				},
				{ headers: { Authorization: this.$session.get("accessToken") } }
			)
				.then(response => {
					if (response.status == 203) {
						http.post(
							"/jwt/getAccessTokenByRefreshToken/",
							this.$session.get("refreshToken") == undefined
								? null
								: this.$session.get("refreshToken")
						)
							.then(ref => {
								if (ref.status == 203) {
									this.$session.destroy();
									alert("로그인 정보가 만료되었습니다.");
									this.$router.push("/");
								} else {
									this.$session.set("accessToken", ref.data);
									if (this.posts[index].post.likeCheck == 1) {
										this.posts[index].post.likeCheck = 0;
										this.posts[index].post.likeCount--;
									} else {
										this.posts[index].post.likeCheck = 1;
										this.posts[index].post.likeCount++;
									}
									this.like(postNum, index);
								}
							})
							.catch(error => {
								console.log(error);
							});
					} else {
						console.log(response);
					}
				})
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
		this.loadingTop = true;
		// console.log("MypageMyPost : " + this.$route.params.no);
		http.post(
			"/api/findByMyPosts/",
			{
				myIdMember: this.$session.get("id"),
				order: 4,
				youIdMember: this.$route.params.no
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
				this.posts = response.data;
				// console.log(response);
				// console.log(this.posts.length);
				if (response.data == "" || this.posts.length == 0)
					this.noContents = true;
				else this.noContents = false;
			})
			.catch(error => {
				console.log(error);
			})
			.finally(() => (this.loadingTop = false));
	}
};
</script>
<style>
#loading {
	display: none;
	width: 100%;
	margin: 20px auto 20px auto;
	justify-content: center;
}
.loader {
	border: 6px solid #f3f3f3; /* Light grey */
	border-top: 6px solid gray; /* Blue */
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
	float: right;
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
	text-align: left;
}

#post_list {
	width: 80%;
	margin: 0 auto;
	text-align: center;
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

@media screen and (max-width: 600px) {
	#post_list {
		width: 100%;
	}
	#post_top {
		width: 80%;
		justify-content: center;
	}
}
</style>
