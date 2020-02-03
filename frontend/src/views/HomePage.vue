<template>
	<div>
		<ImgBanner imgSrc="bg_home.jpg">
			<div style="line-height:1.2em;" slot="text" class="mb-12 pb-12">
				It's like LALALAND
			</div>
		</ImgBanner>
		<v-container>
			<!-- About Me -->
			<v-layout my-5>
				<v-flex class="myDesc text-color">
					<h2 class="headline mb-3">About Me</h2>
					<p class="mr-4">
						City of stars. Are you shining just for me? City of
						stars There's so much that I can't see Who knows? I felt
						it from the first embrace I shared with you.
						<br />That now our dreams may finally come true.
						<br />City of stars. Just one thing everybody wants.
						There in the bars. And through the smokescreen of the
						crowded restaurants. It's love. Yes, all we're looking
						for is love from someone else <br />A rush, A glance, A
						touch, A dance, <br />A look in somebody's eyes. To
						light up the skies. To open the world and send it
						reeling. A voice that says, I'll be here. And you'll be
						alright. I don't care if I know. Just where I will go.
						'Cause all that I need's this crazy feeling. A
						rat-tat-tat on my heart. <br />Think I want it to stay.
						City of stars. Are you shining just for me? City of
						stars... <br />You never shined so brightly
					</p>
				</v-flex>
				<v-flex class="myImg text-color">
					<v-img
						:src="getImgUrl('profile_sebs.jpg')"
						aspect-ratio="1.5"
					/>
				</v-flex>
			</v-layout>

			<!-- Portfolio -->
			<v-layout my-5>
				<v-flex xs12>
					<h2 class="headline my-5 text-xs-center text-color">
						Portfolio
					</h2>
					<PortfolioList></PortfolioList>
				</v-flex>
			</v-layout>

			<!-- Post -->
			<v-layout my-5>
				<v-flex xs12>
					<h2 class="headline my-5 text-xs-center text-color">
						Post
					</h2>
					<PostList></PostList>
				</v-flex>
			</v-layout>

			<!-- Github -->
			<v-layout my-5>
				<v-flex xs12>
					<h2 class="headline my-5 text-xs-center text-color">
						Project
					</h2>
					<RepositoryList></RepositoryList>
				</v-flex>
			</v-layout>
		</v-container>
		<Footer />
	</div>
</template>

<script>
import ImgBanner from "../components/ImgBanner";
import PortfolioList from "../components/PortfolioList";
import PostList from "../components/PostList";
import RepositoryList from "../components/RepositoryList";
import Footer from "../components/Footer";

export default {
	name: "HomePage",
	components: {
		ImgBanner,
		PortfolioList,
		PostList,
		RepositoryList,
		Footer
	},
	methods: {
		getImgUrl(img) {
			return require("../assets/" + img);
		}
	}
};
</script>

<style>
.text-color {
	color: white;
}
.myImg {
	flex: 33%;
	max-width: 33%;
}
.myDesc {
	flex: 67%;
	max-width: 67%;
}
@media screen and (max-width: 600px) {
	.myImg {
		display: none;
	}
	.myDesc {
		flex: 100%;
		max-width: 100%;
		text-align: center;
	}
}
</style>
