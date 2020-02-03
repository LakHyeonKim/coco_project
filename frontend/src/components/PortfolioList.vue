<template>
	<v-layout mt-5 wrap>
		<v-flex
			v-for="i in portfolios.length > limits ? limits : portfolios.length"
			:key="i"
			class="portList"
		>
			<Portfolio
				class="ma-3"
				:postWriter="portfolios[i - 1].post.postWriter"
				:dateCreated="portfolios[i - 1].post.dateCreated"
				:postTitle="portfolios[i - 1].post.postTitle"
				:code="portfolios[i - 1].post.code"
				:likeCount="portfolios[i - 1].likeCount"
				:commentCount="portfolios[i - 1].commentCount"
				:tags="portfolios[i - 1].tags"
				id="portfolio"
			></Portfolio>
			<MobilePortfolio
				class="ma-3"
				:postWriter="portfolios[i - 1].post.postWriter"
				:dateCreated="portfolios[i - 1].post.dateCreated"
				:postTitle="portfolios[i - 1].post.postTitle"
				:code="portfolios[i - 1].post.code"
				:likeCount="portfolios[i - 1].likeCount"
				:commentCount="portfolios[i - 1].commentCount"
				:tags="portfolios[i - 1].tags"
				id="mobilePortfolio"
			>
				<!-- :imagePath="portfolios[i - 1].post.imagePath" -->
			</MobilePortfolio>
		</v-flex>

		<v-flex xs12 text-xs-center round my-5 v-if="loadMore">
			<v-btn color="info" dark v-on:click="loadMorePortfolios">
				<v-icon size="25" class="mr-2">fa-plus</v-icon>더 보기
			</v-btn>
		</v-flex>
	</v-layout>
</template>
<script>
import Portfolio from "@/components/Portfolio";
import MobilePortfolio from "@/components/MobilePortfolio";
import http from "../http-common";

export default {
	name: "PortfoliosList",
	props: {
		limits: { type: Number },
		loadMore: { type: Boolean, default: false }
	},
	data() {
		return {
			portfolios: []
		};
	},
	components: {
		Portfolio,
		MobilePortfolio
	},
	mounted() {
		http.post("/findByAllNewsfeed/", 5)
			.then(res => {
				console.log("getport then ", res.data);
				this.portfolios = res.data;
				console.log("getport then ", this.portfolios);
				// console.log("getport then ", this.portfolios.tags);
			})
			.catch(err => {
				console.log("getport catch ", err);
			});
	},
	methods: {
		loadMorePortfolios() {}
	}
};
</script>

<style>
#mobilePortfolio {
	display: none;
}
@media screen and (max-width: 600px) {
	#portfolio {
		display: none;
	}
	#mobilePortfolio {
		display: block;
	}
}
</style>
