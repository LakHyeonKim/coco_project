<template>
	<div>
		<searchBar @searchwords="searchwords"></searchBar>
		<div id="searchMain">
			<div id="compo">
				<searchFavTag id="tags" @favtag="searchwords" />
				<div
					id="loading"
					:style="loadingTop ? loadingStyleOn : loadingStyleOff"
				>
					<div v-if="loadingTop" class="loader"></div>
				</div>
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
				<div id="loading" :style="loading ? loadingStyleOn : loadingStyleOff">
					<div v-if="loading" class="loader"></div>
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
			idMember: 0,
			loading: false,
			loadingTop: false,
			loadingStyleOn: {
				display: "grid",
				height: "100%"
			},
			loadingStyleOff: {
				display: "none"
			},
			flag: true,
			loadingflag: true,
			searchcheck: true
			// adress: ""
		};
	},
	components: {
		SearchBar,
		SearchFavTag,
		SearchCard
	},
	methods: {
		find(adress, searchword, headers, value) {
			http.post(adress, searchword, {
				headers
			})
				.then(res => {
					if (res.status == 203) {
						// console.log("refresh token -> server");
						http.post(
							"/jwt/getAccessTokenByRefreshToken/",
							this.$session.get("refreshToken") == undefined
								? null
								: this.$session.get("refreshToken")
						)
							.then(ref => {
								// console.log(ref);

								if (ref.status == 203) {
									this.$session.destroy();
									alert("로그인 정보가 만료되었습니다.");
									this.$router.push("/");
								} else {
									this.$session.set("accessToken", ref.data);
									this.find(
										adress,
										searchword,
										headers,
										value
									);
								}
							})
							.catch(err => {
								// console.log(err);
							});
					} else {
						// console.log("search words then ", value, res);
						this.searches = res.data;
						this.loadingTop = false;
					}
				})
				.catch(err => {
					// console.log("search words catch ", err);
					this.loadingTop = false;
				});
		},
		searchwords(word, value) {
			this.searchcheck = false
			this.loadingTop = true;
			// alert("넘어왔다");
			// console.log("word $ value ", word, value);
			const token = this.$session.get("accessToken");
			const headers = {
				Authorization: token
			};
			// console.log("search words headers ", headers);
			if (word) {
				const searchword = {
					idMember: this.idMember,
					keyWord: word
				};
				let adress = "";
				// console.log(searchword);
				if (value == 1) {
					adress = "/api/findByPostTitleKeyword/";
					// console.log(adress, searchword, headers, value);
					this.find(adress, searchword, headers, value);
				} else if (value == 2) {
					adress = "api/findByPostCodeKeyword/";
					// console.log(adress, searchword, headers, value);
					this.find(adress, searchword, headers, value);
				} else if (value == 3) {
					adress = "api/findByPostWriterKeyword/";
					// console.log(adress, searchword, headers, value);
					this.find(adress, searchword, headers, value);
				} else if (value == 4) {
					adress = "api/findByTagKeyword/";
					// console.log(adress, searchword, headers, value);
					this.find(adress, searchword, headers, value);
				} else {
					adress = "/api/findByAllKeyword/";
					// console.log(adress, searchword, headers, value);
					this.find(adress, searchword, headers, value);
				}
			} else {
				alert("검색어를 입력해 주십시오");
			}
		},
		like(postNum, index) {
			// console.log("글번호 : " + postNum + "| index : " + index);
			// console.log("멤버 ID : " + this.$session.get("id"));
			if (this.searches[index].post.likeCheck == 1) {
				this.address = "/trc/unLike/";
				this.searches[index].post.likeCheck = 0;
				this.searches[index].post.likeCount--;
			} else {
				this.address = "/trc/pushLike/";
				this.searches[index].post.likeCheck = 1;
				this.searches[index].post.likeCount++;
			}
			// console.log(this.address);
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
				{ headers: { Authorization: this.$session.get("accessToken") } }
			)
				.then(response => {
					if (response.status == 203) {
						// console.log("refresh token -> server");
						http.post(
							"/jwt/getAccessTokenByRefreshToken/",
							this.$session.get("refreshToken") == undefined
								? null
								: this.$session.get("refreshToken")
						)
							.then(ref => {
								// console.log(ref);

								if (ref.status == 203) {
									this.$session.destroy();
									alert("로그인 정보가 만료되었습니다.");
									this.$router.push("/");
								} else {
									this.$session.set("accessToken", ref.data);
									if (
										this.searches[index].post.likeCheck == 1
									) {
										this.searches[index].post.likeCheck = 0;
										this.searches[index].post.likeCount--;
									} else {
										this.searches[index].post.likeCheck = 1;
										this.searches[index].post.likeCount++;
									}
									this.like(postNum, index);
								}
							})
							.catch(error => {
								// console.log(error);
							});
					} else {
						// console.log(response);
					}
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
		},
		scrollEvent: function() {
			// console.log(window.scrollY);
			const token = this.$session.get("accessToken");
			const headers = {
				Authorization: token
			};
			// console.log("scroll headers event ", headers);
			if (window.scrollY == 0 && this.searchcheck == true) {
				this.loadingTop = true;
				http.post("/api/findByAllDefaultSearch/", this.idMember, {
					headers
				})
					.then(res => {
						if (res.status == 203) {
							// console.log("refresh token -> server");
							http.post(
								"/jwt/getAccessTokenByRefreshToken/",
								this.$session.get("refreshToken") == undefined
									? null
									: this.$session.get("refreshToken")
							)
								.then(ref => {
									// console.log(ref);

									if (ref.status == 203) {
										this.$session.destroy();
										alert("로그인 정보가 만료되었습니다.");
										this.$router.push("/");
									} else {
										this.$session.set(
											"accessToken",
											ref.data
										);
										this.scrollEvent;
									}
								})
								.catch(err => {
									console.log(err);
								});
						} else {
							// console.log("getpost then ", res.data);
							this.searches = res.data;
							this.loadingTop = false;
							this.loadingflag = true;
						}
					})
					.catch(err => {
						console.log("getpost catch ", err);
						// this.loadingTop = false;
					});
			}

			// console.log(
			// 	window.scrollY >=
			// 		document.body.offsetHeight - window.innerHeight - 150
			// );
			if (
				window.scrollY >=
					document.body.offsetHeight - window.innerHeight - 150 &&
				this.flag == true &&
				this.loadingflag == true &&
				this.searchcheck == true
			) {
				// console.log(
				// 	"flagflagflagflag asdjflkasjdlfkajsdlfkajsldfkjalsdkfjalskdfj"
				// );
				// console.log(this.searches)
				this.flag = false;
				this.loading = true;
				const requestForm = {
					member: {
						idmember: this.idMember
					},
					post: {
						idpost: this.searches[this.searches.length - 1].post.idpost
					}
				};
				// console.log("down scroll reqeustForm ", requestForm);
				// console.log("scroll headers event ", headers);
				http.post("/api/findByAllDefaultSearchScrollDown/", requestForm, {
					headers
				})
					.then(res => {
						if (res.status == 203) {
							// console.log("refresh token -> server");
							http.post(
								"/jwt/getAccessTokenByRefreshToken/",
								this.$session.get("refreshToken") == undefined
									? null
									: this.$session.get("refreshToken")
							)
								.then(ref => {
									// console.log(ref);

									if (ref.status == 203) {
										this.$session.destroy();
										alert("로그인 정보가 만료되었습니다.");
										this.$router.push("/");
									} else {
										this.$session.set(
											"accessToken",
											ref.data
										);
										this.scrollEvent;
									}
								})
								.catch(err => {
									console.log(err);
								});
						} else {
							// console.log("getpost then ", res.data);
							console.log("getpost then 2 ", res.data[0].post.idpost)
							for (let i = 0; i < res.data.length; ++i) {
								this.searches.push(res.data[i])
							}
							// console.log(this.searches)
							this.flag = true;
							this.loading = false;
						}
					})
					.catch(err => {
						console.log("getpost catch ", err);
						this.flag = true;
						this.loading = false;
						this.loadingflag = false;
					});
			}
		},
		mount(tagforsearch) {
			const token = this.$session.get("accessToken");
			const headers = {
				Authorization: token
			};
			// console.log(headers);
			if (tagforsearch) {
				this.searchcheck = false;
				const requestForm = {
					idMember: this.idMember,
					keyWord: tagforsearch
				};
				// console.log(requestForm);
				http.post("/api/findByAllKeyword/", requestForm, { headers })
					.then(res => {
						if (res.status == 203) {
							// console.log("refresh token -> server");
							http.post(
								"/jwt/getAccessTokenByRefreshToken/",
								this.$session.get("refreshToken") == undefined
									? null
									: this.$session.get("refreshToken")
							)
								.then(ref => {
									// console.log(ref);

									if (ref.status == 203) {
										this.$session.destroy();
										alert("로그인 정보가 만료되었습니다.");
										this.$router.push("/");
									} else {
										this.$session.set(
											"accessToken",
											ref.data
										);
										this.mount(tagforsearch);
									}
								})
								.catch(err => {
									console.log(err);
								});
						} else {
							// console.log("searchtags then ", res);
							this.searches = res.data;
							this.$store.state.searchtag = "";
							// console.log(
							// 	"tagforsearch then res change",
							// 	tagforsearch
							// );
							this.loadingTop = false;
							return;
						}
					})
					.catch(err => {
						console.log("searchtags catch ", err);
						this.loadingTop = false;
					});
			} else {
				http.post("/api/findByAllDefaultSearch/", this.idMember, {
					headers
				})
					.then(res => {
						if (res.status == 203) {
							// console.log("refresh token -> server");
							http.post(
								"/jwt/getAccessTokenByRefreshToken/",
								this.$session.get("refreshToken") == undefined
									? null
									: this.$session.get("refreshToken")
							)
								.then(ref => {
									// console.log(ref);

									if (ref.status == 203) {
										this.$session.destroy();
										alert("로그인 정보가 만료되었습니다.");
										this.$router.push("/");
									} else {
										this.$session.set(
											"accessToken",
											ref.data
										);
										this.mount(tagforsearch);
									}
								})
								.catch(err => {
									console.log(err);
								});
						} else {
							// console.log("search default mounted then", res);
							this.searches = res.data;
							this.loadingTop = false;
							window.addEventListener("scroll", this.scrollEvent);
						}
					})
					.catch(err => {
						console.log("search default mounted catch", err);
						this.loadingTop = false;
					});
			}
		}
	},
	mounted() {
		this.loadingTop = true;
		this.idMember = this.$session.get("id");
		// console.log("mounted", this.idMember);
		const tagforsearch = this.$store.state.searchtag;
		this.mount(tagforsearch);
	},
	beforeDestroy: function() {
		// console.log("destroy kasjdfhkasjdfhlkajsdfhlkajsdfhlkajsdfhakl");
		window.removeEventListener("scroll", this.scrollEvent);
		this.$session.set("current", 2);
		this.$emit("updateCurrent", 2);
	}
};
</script>

<style>
#loading {
	display: none;
	width: 100%;
	margin: 20px auto 20px auto;
	display: grid;
	justify-content: center;
}
.loader {
	/* margin: 20px auto 20px auto; */
	border: 6px solid #f3f3f3; /* Light grey */
	border-top: 6px solid #3498db; /* Blue */
	border-radius: 50%;
	width: 60px;
	height: 60px;
	animation: spin 2s linear infinite;
}
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
		padding: 5px 5px;
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
