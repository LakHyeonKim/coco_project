<template>
	<div>
		<MypageBanner />
		<MypageMyMenu />
		<MypageMyPost />
		<MypageFooter />
		<div id="blank"></div>
	</div>
</template>

<script>
import MypageBanner from "@/components/MypageBanner";
import MypageMyMenu from "@/components/MypageMyMenu";
import MypageMyPost from "@/components/MypageMyPost";
import MypageFooter from "@/components/MypageFooter";
import store from "../store";
export default {
	name: "myPage",
	store,
	props: ["no"],
	components: {
		MypageBanner,
		MypageMyMenu,
		MypageMyPost,
		MypageFooter
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
