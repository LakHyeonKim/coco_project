<template>
	<div>
		<div id="temp">
			<div @click="allMembers()">
				멤버 다가져오기
				<div v-if="membercheck">
					<div v-for="member in members" :key="member.idmember">
						{{ member.idmember }}, {{ member.id }}
						<div @click.stop="goMemberDetail(member.idmember)">
							특정 멤버 가져오기
						</div>
						<br /><br /><br />
					</div>
				</div>
			</div>
			<div @click="allPosts()">
				포스트 다가져오기
				<div v-if="postcheck">
					<div v-for="post in posts" :key="post.idpost">
						{{ post.idpost }}, {{ post.postTitle }},
						{{ post.dateCreated }}, {{ post.likeCount }},
						{{ post.views }}
						<div @click.stop="goPostDetail(post.idpost)">
							특정 포스트 가져오기
						</div>
						<br /><br /><br />
					</div>
				</div>
			</div>
			<div id="ttt" @click="test()">
				test
			</div>
		</div>
	</div>
</template>

<script>
import http from "../http-common";
import router from "../router";

export default {
	name: "Admin",
	components: {},
	data() {
		return {
			membercheck: 0,
			members: [],
			postcheck: 0,
			posts: []
		};
	},
	methods: {
		test() {
			http.get("/test/download", { responseType: "blob" })
				.then(res => {
					const url = window.URL.createObjectURL(
						new Blob([res.data], { type: res.data.type })
					);
					const link = document.createElement("a");
					link.href = url;

					link.setAttribute("download", "sdsd");
					document.body.appendChild(link);
					link.click();
					console.log(link);
					console.log(res);
				})
				.catch(err => {
					console.log(err);
				});
		},
		allMembers() {
			if (!this.membercheck) {
				this.membercheck = 1;
				const headers = {
					Authorization: this.$session.get("accessToken")
				};
				http.get("/api/findAllMember", { headers })
					.then(res => {
						console.log("all member res ", res);
						const url = window.URL.createObjectURL(
							new Blob([res.data])
						);
						const link = document.createElement("a");
						link.href = url;
						link.setAttribute("download", "CalculRanking.pdf");
						document.body.appendChild(link); //이부분을 원하는 부분에 붙이고 싶다.
						link.click();
						link.parentNode.removeChild(link);
						this.members = res.data;
					})
					.catch(err => {
						console.log("all member err ", err);
					});
			} else {
				this.membercheck = 0;
			}
		},
		goMemberDetail(memberpk) {
			router.push("/admin/member/" + memberpk);
		},
		allPosts() {
			if (!this.postcheck) {
				this.postcheck = 1;
				http.get("/api/findAllPost")
					.then(res => {
						console.log("all member res ", res);
						this.posts = res.data;
					})
					.catch(err => {
						console.log("all member err ", err);
					});
			} else {
				this.postcheck = 0;
			}
		},
		goPostDetail(postpk) {
			router.push("/admin/post/" + postpk);
		}
	},
	mounted() {
		document.querySelector("#navbar").setAttribute("style", "display:none");
	},
	destroyed() {
		document
			.querySelector("#navbar")
			.setAttribute("style", "display:fixed");
	}
};
</script>

<style>
#temp {
	margin-left: 100px;
}
</style>
