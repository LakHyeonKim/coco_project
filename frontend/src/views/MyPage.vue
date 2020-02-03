<template>
	<div>
		<Banner />
		<MyMenu />
		<MyPost />
		<Footer />
		<div id="blank"></div>
	</div>
</template>

<script>
import Banner from "@/components/Banner";
import MyMenu from "@/components/MyMenu";
import MyPost from "@/components/MyPost";
import Footer from "@/components/Footer";
import store from "../store";
export default {
	name: "myPage",
	store,
	components: {
		Banner,
		MyMenu,
		MyPost,
		Footer
	},
	data() {
		return {};
	}
};
</script>
<style>
@import url("https://fonts.googleapis.com/css?family=Noto+Sans+KR:100,300,400,500,700,900&display=swap");

* {
	font-family: "Noto Sans KR", Courier;
}
@media screen and (max-width: 600px) {
	#blank {
		height: 10vw;
	}
}
</style>
