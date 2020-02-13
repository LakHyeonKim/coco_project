<template>
	<div id="menu">
		<img
			id="imgMenu"
			@click="toggleMenu()"
			:src="
				isHidden
					? '../img/icons/menu_g.png'
					: '../img/icons/close_g.png'
			"
			width="25px"
		/>
		<!-- blur 생각해보기 -->
		<div id="open" v-if="!isHidden">
			<div id="search">
				<v-select
					label="검색조건"
					:items="items"
					@change="changeMenuSel"
					color="rgba(0, 0, 0, 0.5)"
					item-color="black"
					id="search_sel"
					style="float: left; width: 90px; font-size: 15px;"
				/>
				<input v-model="menu_text" type="text" id="search_text" />
				<img
					id="search_img"
					@click="searchMenu()"
					src="../assets/icon/search_b.png"
				/>
			</div>
			<ul id="list" v-for="item in tags" :key="item">
				<li>#{{ item }}</li>
			</ul>
		</div>
		<div></div>
	</div>
</template>
<script>
import http from "../http-common";
import store from "../store";
export default {
	name: "MypageMyMenu",
	store,
	props: {
		posts: null
	},
	data() {
		return {
			items: [
				{ text: "전체", value: "1" },
				{ text: "#", value: "2" },
				{ text: "글제목", value: "3" },
				{ text: "글내용", value: "4" }
			],
			isHidden: true,
			tags: "",
			menuSel: "",
			menu_text: ""
		};
	},
	methods: {
		toggleMenu() {
			this.isHidden = !this.isHidden;
			this.tags = store.state.tags;
			console.log(this.tags);
		},
		changeMenuSel(idx) {
			this.menuSel = idx;
		},
		searchMenu() {
			if (this.menuSel == "") {
				alert("검색조건을 선택해주세요!");
				return;
			}
			if (this.menu_text == "") {
				alert("검색어를 입력해주세요!");
				return;
			}
			let address = "";
			if (this.menuSel == 1) {
				address = "/api/findByAllKeywordMyPosts";
			} else if (this.menuSel == 2) {
				address = "/api/findByTagKeywordMyPosts";
			} else if (this.menuSel == 3) {
				address = "/api/findByPostTitleKeywordMyPosts";
			} else {
				address = "/api/findByPostCodeKeywordMyPosts";
			}
			console.log(this.menu_text);
			console.log(address);
			http.post(
				address,
				{
					keyword: this.menu_text,
					myIdMember: this.$session.get("id"),
					order: 4,
					youIdMember: this.$route.params.no
				},
				{ headers: { Authorization: this.$session.get("accessToken") } }
			)
				.then(response => {
					// this.posts = response.data;
					this.$emit("setPosts", response.data);
					console.log(this.posts);
				})
				.catch(error => {
					console.log(error);
				});

			this.isHidden = true;
			this.menu_text = "";
		}
	}
};
</script>

<style>
#search {
	margin-left: 10px;
	display: inline-block;
	width: 300px;
}
#search_text {
	float: left;
	/* margin-left: 5px; */
	margin-top: 19px;
	width: 150px;
	height: 30px;
	border-bottom: 0.9px solid rgba(0, 0, 0, 0.4);
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
#menu {
	position: fixed;
	text-align: right;
	top: 0;
	right: 0;
	z-index: 1;
}
#imgMenu {
	right: 0;
	margin: 15px;

	-webkit-transform: scale(1);
	-moz-transform: scale(1);
	-ms-transform: scale(1);
	-o-transform: scale(1);
	transform: scale(1);
	-webkit-transition: 0.2s;
	-moz-transition: 0.2s;
	-ms-transition: 0.2s;
	-o-transition: 0.2s;
	transition: 0.2s;
}
#imgMenu:hover {
	cursor: pointer;

	-webkit-transform: scale(1.1);
	-moz-transform: scale(1.1);
	-ms-transform: scale(1.1);
	-o-transform: scale(1.1);
	transform: scale(1.1);
}
#open {
	background-color: white;
	position: relative;
	right: 20px;
	top: -10px;
	width: 300px;
	height: 300px;
	padding: 10px;
	box-shadow: 0.1px 0.1px 5px 0.15px rgba(0, 0, 0, 0.267);
	border-radius: 15px;
}
#list {
	background-color: white;
	text-align: left;
	padding: 0 5px 0px 5px;
	list-style: none;
	text-overflow: ellipsis;
	overflow: scroll;
	float: left;
}
#list > li {
	padding: 4px 8px 4px 8px;
	background-color: rgba(160, 23, 98, 0.5);
	border-radius: 10px;
	color: white;
}

#list::-webkit-scrollbar {
	width: 5.2px;
}
#list::-webkit-scrollbar-track {
	background: none;
}
#list::-webkit-scrollbar-thumb {
	background: rgba(0, 0, 0, 0.2);
}
#list::-webkit-scrollbar-thumb:hover {
	background: rgba(0, 0, 0, 0.5);
}
#list::-webkit-scrollbar-thumb:active {
	background: rgba(0, 0, 0, 0.5);
}
</style>
