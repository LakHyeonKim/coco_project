<template>
	<v-layout mt-5 wrap>
		<v-flex v-for="i in portfolios.length" :key="i" class="portList">
			<Portfolio
				class="ma-3"
				:idPost="portfolios[i - 1].post.idpost"
				:memberId="portfolios[i - 1].post.memberId"
				:postTitle="portfolios[i - 1].post.postTitle"
				:postWriter="portfolios[i - 1].post.postWriter"
				:dateCreated="portfolios[i - 1].post.dateCreated"
				:updateCreated="portfolios[i - 1].post.updateCreated"
				:code="portfolios[i - 1].post.code"
				:likeCount="portfolios[i - 1].post.likeCount"
				:views="portfolios[i - 1].post.views"
				:imagePath="portfolios[i - 1].post.imagePath"
				:filePath="portfolios[i - 1].post.filePath"
				:access="portfolios[i - 1].post.access"
				:likeCheck="portfolios[i - 1].post.likeCheck"
				:order="portfolios[i - 1].post.order"
				:tags="portfolios[i - 1].tags"
				:commentCount="portfolios[i - 1].commentCount"
				id="portfolio"
			></Portfolio>
			<MobilePortfolio
				class="ma-3"
				:idPost="portfolios[i - 1].post.idpost"
				:memberId="portfolios[i - 1].post.memberId"
				:postTitle="portfolios[i - 1].post.postTitle"
				:postWriter="portfolios[i - 1].post.postWriter"
				:dateCreated="portfolios[i - 1].post.dateCreated"
				:updateCreated="portfolios[i - 1].post.updateCreated"
				:code="portfolios[i - 1].post.code"
				:likeCount="portfolios[i - 1].post.likeCount"
				:views="portfolios[i - 1].post.views"
				:imagePath="portfolios[i - 1].post.imagePath"
				:filePath="portfolios[i - 1].post.filePath"
				:access="portfolios[i - 1].post.access"
				:likeCheck="portfolios[i - 1].post.likeCheck"
				:order="portfolios[i - 1].post.order"
				:tags="portfolios[i - 1].tags"
				:commentCount="portfolios[i - 1].commentCount"
				id="mobilePortfolio"
			>
			</MobilePortfolio>
		</v-flex>
	</v-layout>
</template>
<script>
import Portfolio from "@/components/Portfolio";
import MobilePortfolio from "@/components/MobilePortfolio";
import http from "../http-common";

export default {
	name: "PortfoliosList",
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
		const token = this.$session.get("accessToken");
		const headers = {
			Authorization: token
		};
		const idMember = this.$session.get("id");
		// console.log(idMember);
		http.post("/api/findByAllNewsfeed/", idMember, { headers })
			.then(res => {
				console.log("getport then 1", res.data);
				console.log("getport then 2", res.data[0].post.idpost);
				this.portfolios = res.data;
				console.log("getport then 3", this.portfolios);
				// console.log("getport then ", this.portfolios.tags);
			})
			.catch(err => {
				console.log("getport catch ", err);
			});
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
