<template>
	<div>
		<searchBar @searchwords="searchwords"></searchBar>
		<div id="searchMain">
			<div id="compo">
				<searchFavTag @favtag="searchwords"></searchFavTag>
				<div>
					<div v-for="i in searches.length" :key="i">
						<SearchCard
							@like="like"
							@searchtag="searchwords"
							:postIdx="i - 1"
							:idPost="searches[i - 1].post.idpost"
							:memberId="searches[i - 1].post.memberId"
							:postTitle="searches[i - 1].post.postTitle"
							:postWriter="searches[i - 1].post.postWriter"
							:dateCreated="searches[i - 1].post.dateCreated"
							:updateCreated="searches[i - 1].post.updateCreated"
							:code="searches[i - 1].post.code"
							:likeCount="searches[i - 1].post.likeCount"
							:views="searches[i - 1].post.views"
							:access="searches[i - 1].post.access"
							:likeCheck="searches[i - 1].post.likeCheck"
							:order="searches[i - 1].post.order"
							:tags="searches[i - 1].tags"
							:commentCount="searches[i - 1].commentCount"
							id="searches"
						></SearchCard>
					</div>
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
		searchwords(word, value) {
			// alert("넘어왔다");
			console.log("word $ value ", word, value);
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
				if (value == 1) {
					http.post("/api/findByPostTitleKeyword/", searchword)
						.then(res => {
							console.log("search TITLE words then ", res);
							this.searches = res.data;
						})
						.catch(err => {
							console.log("search TITLE words catch ", err);
						});
				} else if (value == 2) {
					http.post("api/findByPostCodeKeyword/", searchword)
						.then(res => {
							console.log("search CODE words then ", res);
							this.searches = res.data;
						})
						.catch(err => {
							console.log("search CODE words catch ", err);
						});
				} else if (value == 3) {
					http.post("api/findByPostWriterKeyword/", searchword)
						.then(res => {
							console.log("search WRITER words then ", res);
							this.searches = res.data;
						})
						.catch(err => {
							console.log("search WRITER words catch ", err);
						});
				} else if (value == 4) {
					http.post("api/findByTagKeyword/", searchword)
						.then(res => {
							console.log("search TAG words then ", res);
							this.searches = res.data;
						})
						.catch(err => {
							console.log("search TAG words catch ", err);
						});
				} else {
					http.post("/api/findByAllKeyword/", searchword)
						.then(res => {
							console.log("searchwords then ", res);
							this.searches = res.data;
						})
						.catch(err => {
							console.log("searchwords catch ", err);
						});
				}
			} else {
				alert("검색어를 입력해 주십시오");
			}
		},
		like(postNum, index) {
			console.log("글번호 : " + postNum + "| index : " + index);
			console.log("멤버 ID : " + this.$session.get("id"));
			if (this.searches[index].post.likeCheck == 1) {
				this.address = "/trc/unLike/";
				this.searches[index].post.likeCheck = 0;
				this.searches[index].post.likeCount--;
			} else {
				this.address = "/trc/pushLike/";
				this.searches[index].post.likeCheck = 1;
				this.searches[index].post.likeCount++;
			}
			console.log(this.address);
			http.post(this.address, {
				member: {
					idmember: this.$session.get("id")
				},
				post: {
					idpost: postNum
				}
			})
				.then(res => {
					console.log(res);
				})
				.catch(error => {
					console.log(error);
					if (this.searches[index].post.likeCheck == 1) {
						this.searches[index].post.likeCheck = 0;
						this.searches[index].post.likeCount--;
					} else {
						this.searches[index].post.likeCheck = 1;
						this.searches[index].post.likeCount++;
					}
				});
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
	display: grid;
	height: 100%;
	width: 100%;
	justify-content: center;
}
#compo {
	justify-content: center;
	width: 80vw;
}
/* @media screen and (max-width: 600px) {
	#searches {
		display: none;
	}
} */
</style>
