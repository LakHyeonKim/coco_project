<template>
	<div id="searchMain">
		<searchBar @searchwords="searchwords"></searchBar>
		<div id="compo">
			<searchFavTag @favtag="searchwords"></searchFavTag>
			<div>
				<div v-for="i in searches.length" :key="i">
					<SearchCard
						:idPost="searches[i - 1].post.idPost"
						:memberId="searches[i - 1].post.memberId"
						:postTitle="searches[i - 1].post.postTitle"
						:postWriter="searches[i - 1].post.postWriter"
						:dateCreated="searches[i - 1].post.dateCreated"
						:updateCreated="searches[i - 1].post.updateCreated"
						:code="searches[i - 1].post.code"
						:likeCount="searches[i - 1].likeCount"
						:views="searches[i - 1].post.views"
						:access="searches[i - 1].post.access"
						:likeCheck="searches[i - 1].post.likeCheck"
						:order="searches[i - 1].post.order"
						:tags="searches[i - 1].tags"
						:commentCount="searches[i - 1].commentCount"
						id="searches"
						@searchtag="searchwords"
					></SearchCard>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
import SearchBar from "../components/SearchBar";
import SearchFavTag from "../components/SearchFavTag";
import SearchCard from "../components/SearchCard";
import http from "../http-common";

export default {
	name: "SearchPage",
	data() {
		return {
			searches: [],
			keyword: "",
			idMember: 0
		};
	},
	components: {
		SearchBar,
		SearchFavTag,
		SearchCard
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
				const searchword = {
					idMember: this.idMember,
					keyWord: word
				};
				console.log(searchword);
				http.post("/api/findByAllKeyword/", searchword)
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
		this.idMember = this.$session.get("id");
		console.log("mounted", this.idMember);
		const tagforsearch = this.$store.state.searchtag;
		if (tagforsearch) {
			const requestForm = {
				idMember: this.idMember,
				keyWord: tagforsearch
			};
			console.log(requestForm);
			http.post("/api/findByAllKeyword/", requestForm)
				.then(res => {
					console.log("searchtags then ", res);
					this.searches = res.data;
					this.$store.state.searchtag = "";
					console.log("tagforsearch then res change", tagforsearch);
					return;
				})
				.catch(err => {
					console.log("searchtags catch ", err);
				});
		} else {
			http.post("/api/findByAllDefaultSearch/", this.idMember)
				.then(res => {
					console.log("search mounted then", res);
					this.searches = res.data;
				})
				.catch(err => {
					console.log("search mounted catch", err);
				});
		}
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
