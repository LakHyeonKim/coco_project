<template>
	<div>
		<searchBar @searchwords="searchwords"></searchBar>
		<div id="searchMain">
			<div id="compo">
				<searchFavTag id="tags" @favtag="searchwords"></searchFavTag>
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
		<div class="footerBlank"></div>
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
			const token = this.$session.get("accessToken");
			const headers = {
				Authorization: token
			};
			console.log("search words headers ", headers);
			if (word) {
				const searchword = {
					idMember: this.idMember,
					keyWord: word
				};
				console.log(searchword);
				if (value == 1) {
					http.post("/api/findByPostTitleKeyword/", searchword, {
						headers
					})
						.then(res => {
							console.log("search TITLE words then ", res);
							this.searches = res.data;
						})
						.catch(err => {
							console.log("search TITLE words catch ", err);
						});
				} else if (value == 2) {
					http.post("api/findByPostCodeKeyword/", searchword, {
						headers
					})
						.then(res => {
							console.log("search CODE words then ", res);
							this.searches = res.data;
						})
						.catch(err => {
							console.log("search CODE words catch ", err);
						});
				} else if (value == 3) {
					http.post("api/findByPostWriterKeyword/", searchword, {
						headers
					})
						.then(res => {
							console.log("search WRITER words then ", res);
							this.searches = res.data;
						})
						.catch(err => {
							console.log("search WRITER words catch ", err);
						});
				} else if (value == 4) {
					http.post("api/findByTagKeyword/", searchword, { headers })
						.then(res => {
							console.log("search TAG words then ", res);
							this.searches = res.data;
						})
						.catch(err => {
							console.log("search TAG words catch ", err);
						});
				} else {
					http.post("/api/findByAllKeyword/", searchword, { headers })
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
			const token = this.$session.get("accessToken");
			const headers = {
				Authorization: token
			};
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
			http.post(
				this.address,
				{
					member: {
						idmember: this.$session.get("id")
					},
					post: {
						idpost: postNum
					}
				},
				{ headers }
			)
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
		const token = this.$session.get("accessToken");
		const headers = {
			Authorization: token
		};
		console.log(headers);
		if (tagforsearch) {
			const requestForm = {
				idMember: this.idMember,
				keyWord: tagforsearch
			};
			console.log(requestForm);
			http.post("/api/findByAllKeyword/", requestForm, { headers })
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
			http.post("/api/findByAllDefaultSearch/", this.idMember, {
				headers
			})
				.then(res => {
					console.log("search default mounted then", res);
					this.searches = res.data;
				})
				.catch(err => {
					console.log("search default mounted catch", err);
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
	padding-left: 40px;
}
#compo {
	justify-content: center;
	width: 80vw;
}
@media screen and (max-width: 600px) {
	#searchMain {
		padding-left: 0px;
	}
	#compo {
		width: 100vw;
	}
	#tags {
		width: 100vw;
	}
	#searches {
		width: 100vw;
	}
	.footerBlank {
		display: block;
		top: auto;
		bottom: 0;
		height: 17vw;
		width: 100%;
		padding: 0;
	}
}
</style>
