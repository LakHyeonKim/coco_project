<template>
	<div id="searchMain">
		<searchBar @searchwords="searchwords"></searchBar>
		<div id="compo">
			<searchFavTag @favtag="searchwords"></searchFavTag>
			<div>
				<div v-for="i in searches.length" :key="i">
					<SearchList
						:postWriter="searches[i - 1].post.postWriter"
						:dateCreated="searches[i - 1].post.dateCreated"
						:postTitle="searches[i - 1].post.postTitle"
						:code="searches[i - 1].post.code"
						:likeCount="searches[i - 1].likeCount"
						:commentCount="searches[i - 1].commentCount"
						:tags="searches[i - 1].tags"
						id="searches"
						@searchtag="searchwords"
					></SearchList>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
import SearchBar from "../components/SearchBar";
import SearchFavTag from "../components/SearchFavTag";
import SearchList from "../components/SearchList";
import http from "../http-common";

export default {
	name: "Search",
	data() {
		return {
			searches: [],
			keyword: ""
		};
	},
	components: {
		SearchBar,
		SearchFavTag,
		SearchList
	},
	methods: {
		searchwords(word) {
			// alert("넘어왔다");
			console.log(word);
			// const token = this.$session.get("jwt");
			// const headers = {
			// 	Authorization: token
			// };
			if (word) {
				http.post("/api/findByAllKeyword/", word)
					.then(res => {
						console.log("searchwords then ", res);
						this.searches = res.data;
					})
					.catch(err => {
						console.log("searchwords catch ", err);
					});
			} else {
				alert("검색어를 입력해 주십시오");
			}
		}
	},
	mounted() {
		http.post("/api/findByAllDefaultSearch/", 7)
			.then(res => {
				console.log("search mounted then", res);
				this.searches = res.data;
			})
			.catch(err => {
				console.log("search mounted catch", err);
			});
	}
};
</script>

<style>
#searchMain {
	height: 100%;
	width: 100%;
	background-color: bisque;
	text-align: center;
}
#compo {
	display: grid;
	justify-content: center;
}
/* @media screen and (max-width: 600px) {
	#searches {
		display: none;
	}
} */
</style>
