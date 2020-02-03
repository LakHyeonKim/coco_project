<template>
	<div>
		<v-img :src="require('../assets/' + imgSrc)" aspect-ratio="3">
			<v-layout align-center justify-center row fill-height>
				<v-flex text-xs-center class="imgBannerText">
					<span class="text-shadow font-weight-light">
						<slot name="text" />
					</span>
				</v-flex>
			</v-layout>
		</v-img>
	</div>
</template>

<script>
export default {
	name: "ImgBanner",
	props: {
		imgSrc: { type: String },
		text: { type: String }
	},
	methods: {}
};
</script>
<style>
.text-shadow {
	text-shadow: 0 0 15px rgb(255, 255, 255);
}
@import url("https://fonts.googleapis.com/css?family=Ibarra+Real+Nova&display=swap");
.imgBannerText {
	line-height: 1.2em;
	font-size: 8vw;
	text-align: center;
	font-family: "Ibarra Real Nova", serif;
}
</style>
