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
			<ul id="list" v-for="item in tags" :key="item">
				<li>#{{ item }}</li>
			</ul>
		</div>
		<div></div>
	</div>
</template>
<script>
import store from "../store";
export default {
	name: "MypageMyMenu",
	store,
	data() {
		return {
			items: [
				{ text: "전체", value: "1" },
				{ text: "#", value: "2" },
				{ text: "글제목", value: "3" },
				{ text: "글내용", value: "4" }
			],
			isHidden: true,
			tags: ""
		};
	},
	methods: {
		toggleMenu() {
			this.isHidden = !this.isHidden;
			this.tags = store.state.tags;
			console.log(this.tags);
		}
	}
};
</script>

<style>
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
