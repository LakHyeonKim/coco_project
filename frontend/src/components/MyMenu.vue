<template>
	<div id="menu">
		<img
			id="imgMenu"
			@click="isHidden = !isHidden"
			:src="
				isHidden ? './img/icons/menu_w.png' : './img/icons/close_w.png'
			"
			width="25px"
		/>
		<div id="open" v-if="!isHidden">
			<ul id="list">
				<li>#java</li>
				<li>#vue</li>
				<li>#안녕하세요안녕하세요안녕안녕하세요안녕하세요안녕</li>
				<li>#java</li>
				<li>#vue</li>
				<li>#안녕하세요안녕하세요안녕안녕하세요안녕하세요안녕</li>
				<li>#java</li>
				<li>#vue</li>
				<li>#안녕하세요안녕하세요안녕안녕하세요안녕하세요안녕</li>
				<li>#java</li>
				<li>#vue</li>
				<li>#안녕하세요안녕하세요안녕안녕하세요안녕하세요안녕</li>
				<li>#java</li>
				<li>#vue</li>
				<li>#안녕하세요안녕하세요안녕안녕하세요안녕하세요안녕</li>
				<li>#java</li>
				<li>#vue</li>
				<li>#안녕하세요안녕하세요안녕안녕하세요안녕하세요안녕</li>
				<li>#java</li>
				<li>#vue</li>
				<li>#안녕하세요안녕하세요안녕안녕하세요안녕하세요안녕</li>
			</ul>
			<div id="search">
				<v-select
					label="검색조건"
					:items="items"
					color="rgba(0, 0, 0, 0.5)"
					item-color="black"
					id="search_sel"
					style="float: left; width: 80px; font-size: 15px;"
				/>
				<input type="text" id="search_text" />
				<img id="search_img" src="../assets/icon/search_b.png" />
			</div>
		</div>
		<div></div>
	</div>
</template>
<script>
import store from "../store";
export default {
	name: "mymenu",
	store,
	data() {
		return {
			items: ["전체", "#", "글제목", "글내용"],
			isHidden: true
		};
	}
};
</script>

<style>
#search {
	position: relative;
	top: -20px;
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
	margin-top: 15px;
	float: left;
	width: 25px;
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
	width: 280px;
	height: 250px;
}
#list > li {
	display: inline-block;
	margin: 5px;
	padding: 4px 8px 4px 8px;
	background-color: rgb(146, 79, 163);
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
