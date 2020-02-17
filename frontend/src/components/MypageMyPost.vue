<template>
	<div id="posts">
		<MypageMyMenu @search="search" />
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
					<input v-model="menu_text" type="text" id="search_text" />
					<img
						id="search_img"
						@click="search()"
						src="../assets/icon/search_b.png"
					/>
				</div>
			</div>
			<div
				class="post"
				v-for="(item, index) in posts"
				:key="item.post.idpost"
			>
				<div style="margin: 10px;">
					<div @click.prevent="goDetail(item.post.idpost)">
						<div
							v-for="tag in item.tags"
							:key="tag.idtag"
							style="display: inline-block;"
						>
							<span
								class="post_tag"
								:style="selTag == tag ? selStyle : tagStyle"
								@click="getSearchData(2, tag)"
							>
								{{ tag }}
							</span>
						</div>
						<div class="post_title">
							{{ item.post.postTitle }}
						</div>
						<div class="post_create">
							<img
								class="post_profile"
								src="../assets/user.png"
							/>
							<div class="post_nickname">
								{{ item.post.postWriter }}
							</div>
							<div class="post_date">
								{{ item.post.dateCreated }}
							</div>
						</div>
						<div class="post_code">
							{{ item.post.code }}
						</div>
					</div>
					<div class="like_comment">
						<img
							:id="item.post.idpost"
							class="like_img"
							:src="
								item.post.likeCheck == 1
									? '../img/icons/tack_full.png'
									: '../img/icons/tack_empty.png'
							"
							width="35px"
							@click.stop="like(item.post.idpost, index)"
						/>
						<MemberList class="counting_click">
							<div slot="click">
								<div class="like_text">
									{{ item.post.likeCount }}
								</div>
							</div>
						</MemberList>
						<!-- <div class="like_text">{{ item.post.likeCount }}</div> -->
						<img
							src="../assets/icon/chat.png"
							class="comment_img"
						/>
						<div class="comment_text">{{ item.commentCount }}</div>
					</div>
				</div>
				<div class="line" />
			</div>
		</div>
		<div v-if="noContents" id="noContents">
			검색한 내용의 포스트가 존재하지 않습니다
		</div>
	</div>
</template>
<script>
import http from "../http-common";
import store from "../store";
import MypageMyMenu from "@/components/MypageMyMenu";
import MemberList from "@/components/MemberList";
export default {
	name: "MypageMyPost",
	store,
	components: {
		MypageMyMenu,
		MemberList
	},
	data() {
		return {
			dialog: false,
			noContents: false,
			posts: "",
			postTags: "",
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
			selStyle: {
				float: "left",
				marginRight: "6px",
				fontSize: "13px",
				borderRadius: "8px",
				paddingLeft: "5px",
				paddingRight: "5px",
				color: "white",
				backgroundColor: "#7d4879"
			},
			tagStyle: {
				float: "left",
				marginRight: "6px",
				fontSize: "13px",
				borderRadius: "8px",
				paddingLeft: "5px",
				paddingRight: "5px",
				color: "white",
				backgroundColor: "rgba(160, 23, 98, 0.5)"
			},
			menuSel: "",
			menu_text: "",
			searchSel: "",
			orderSel: ""
		};
	},
	methods: {
		test() {},
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
		},
		goDetail(detail) {
			this.$router.push({ name: "detail", params: { idPost: detail } });
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
			http.post(
				address,
				{
					keyword: text,
					myIdMember: this.$session.get("id"),
					order: 4,
					youIdMember: this.$route.params.no
				},
				{ headers: { Authorization: this.$session.get("accessToken") } }
			)
				.then(response => {
					if (sel == 2) this.selTag = text;
					else this.selTag = "";
					this.posts = response.data;
					console.log(this.posts.length);
					if (this.posts.length == 0) this.noContents = true;
					else this.noContents = false;
				})
				.catch(error => {
					console.log(error);
				});
		},
		chnagePostSel(idx) {
			console.log(idx);
			this.selTag = "";
			// console.log(document.getElementById("search_sel").value);
			// document.getElementById("search_sel").selected = undefined;
			// document.getElementById("search_sel").items = this.items;
			this.searchSel = null;
			http.post(
				"/api/findByMyPosts/",
				{
					myIdMember: this.$session.get("id"),
					order: idx,
					youIdMember: this.$route.params.no
				},
				{ headers: { Authorization: this.$session.get("accessToken") } }
			)
				.then(response => {
					this.posts = response.data;
					console.log(this.posts);
				})
				.catch(error => {
					console.log(error);
				});
		},
		changeMenuSel(idx) {
			this.menuSel = idx;
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
				.then(res => {
					console.log(res);
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
		console.log("MypageMyPost : " + this.$route.params.no);
		http.post(
			"/api/findByMyPosts/",
			{
				myIdMember: this.$session.get("id"),
				order: 4,
				youIdMember: this.$route.params.no
			},
			{ headers: { Authorization: this.$session.get("accessToken") } }
		)
			.then(response => {
				this.posts = response.data;
				console.log(response);
			})
			.catch(error => {
				console.log(error);
			});
		// .finally(() => (this.loading = false));
	}
};
</script>
<style>
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
	width: 300px;
	height: 30px;
	border-bottom: 0.9px solid rgba(0, 0, 0, 0.4);
}
#search_text:focus {
	outline: none;
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
		width: 90px;
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
		width: 130px;
	}
}

#post_top {
	display: inline-block;
	width: 100%;
}

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
	/* margin-right: 6px;
	font-size: 13px;
	border-radius: 8px;
	padding-left: 5px;
	padding-right: 5px;
	color: white;
	background-color: rgba(160, 23, 98, 0.5); */
	cursor: pointer;
}
.post_tag:hover {
	background-color: #7d4879;
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
