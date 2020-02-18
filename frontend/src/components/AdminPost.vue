<template>
	<div id="Table">
		<table>
			<tr>
				<th>idPost</th>
				<th>
					<input type="text" v-model="postUpdate.idpost" />
				</th>
			</tr>
			<tr>
				<th>memberId</th>
				<th>
					<input type="text" v-model="postUpdate.memberId" />
				</th>
			</tr>
			<tr>
				<th>postTitle</th>
				<th>
					<input type="text" v-model="postUpdate.postTitle" />
				</th>
			</tr>
			<tr>
				<th>postWriter</th>
				<th>
					<input type="text" v-model="postUpdate.postWriter" />
				</th>
			</tr>
			<tr>
				<th>dateCreated</th>
				<th>
					{{ postUpdate.dateCreated }}
				</th>
			</tr>
			<tr>
				<th>code</th>
				<th>
					<textarea rows="20" cols="50" v-model="postUpdate.code" />
				</th>
			</tr>
			<tr>
				<th>likeCount</th>
				<th>
					<input type="text" v-model="postUpdate.likeCount" />
				</th>
			</tr>
			<tr>
				<th>views</th>
				<th>
					<input type="text" v-model="postUpdate.views" />
				</th>
			</tr>
			<tr>
				<th>imagePath</th>
				<th>
					<input type="text" v-model="postUpdate.imagePath" />
				</th>
			</tr>
			<tr>
				<th>filePath</th>
				<th>
					<input type="text" v-model="postUpdate.filePath" />
				</th>
			</tr>
			<tr>
				<th>access</th>
				<th>
					<input type="text" v-model="postUpdate.access" />
				</th>
			</tr>
			<tr>
				<th>likeCheck</th>
				<th>
					<input type="text" v-model="postUpdate.likeCheck" />
				</th>
			</tr>
			<tr>
				<th>order</th>
				<th>
					<input type="text" v-model="postUpdate.order" />
				</th>
			</tr>
		</table>

		<button @click="Update()">수정</button>
		<button @click="goBack()">취소</button>
	</div>
</template>

<script>
import http from "../http-common";
import router from "../router";

export default {
	name: "AdminPost",
	data() {
		return {
			requestForm: {
				headers: {
					Authorization: ""
				}
			},
			postUpdate: {
				memberId: 0,
				access: 0,
				idpost: 0,
				postTitle: "",
				postWriter: "",
				code: "",
				filePath: "",
				imagePath: "",
				likeCheck: 0,
				likeCount: 0,
				views: 0,
				order: 0,
				dateCreated: ""
			}
		};
	},
	methods: {
		Update() {
			const requestForm = {
				// memberId: this.postUpdate.memberId,
				// access: this.postUpdate.access,
				idpost: this.postUpdate.idpost,
				postTitle: this.postUpdate.postTitle,
				postWriter: this.postUpdate.postWriter,
				code: this.postUpdate.code,
				filePath: this.postUpdate.filePath,
				imagePath: this.postUpdate.imagePath
				// likeCheck: this.postUpdate.likeCheck,
				// likeCount: this.postUpdate.likeCount,
				// views: this.postUpdate.views,
				// order: this.postUpdate.order
			};
			console.log(requestForm);
			http.put("/api/updatePost", requestForm, this.requestForm)
				.then(res => {
					console.log(res);
					document.location.reload();
					alert("수정완료");
				})
				.catch(err => {
					console.log(err);
					alert("에러");
				});
		},
		goBack() {
			router.push("/admin");
		}
	},
	mounted() {
		this.requestForm.headers.Authorization = this.$session.get(
			"accessToken"
		);
		document.querySelector("#navbar").setAttribute("style", "display:none");
		const postInfo = {
			idpost: this.$route.params.no
		};
		// console.log(postInfo);
		http.post("/api/findPost", postInfo, this.requestForm)
			.then(res => {
				console.log("get post res ", res);
				this.postUpdate.access = res.data[0].access;
				this.postUpdate.code = res.data[0].code;
				this.postUpdate.filePath = res.data[0].filePath;
				this.postUpdate.imagePath = res.data[0].imagePath;
				this.postUpdate.likeCheck = res.data[0].likeCheck;
				this.postUpdate.likeCount = res.data[0].likeCount;
				this.postUpdate.order = res.data[0].order;
				this.postUpdate.postTitle = res.data[0].postTitle;
				this.postUpdate.postWriter = res.data[0].postWriter;
				this.postUpdate.views = res.data[0].views;
				this.postUpdate.idpost = res.data[0].idpost;
				this.postUpdate.memberId = res.data[0].memberId;
				this.postUpdate.dateCreated = res.data[0].dateCreated;
			})
			.catch(err => {
				console.log("get post err ", err);
			});
	},
	destroyed() {
		document
			.querySelector("#navbar")
			.setAttribute("style", "display:fixed");
	}
};
</script>

<style scoped>
#Table {
	display: grid;
	justify-content: center;
	width: 100%;
}
table {
	border-collapse: collapse;
	border-spacing: 0;
	width: 100%;
	border: 1px solid #ddd;
}
th,
td {
	text-align: left;
	padding: 16px;
}

tr:nth-child(even) {
	background-color: #f2f2f2;
}
::placeholder {
	font-size: 15px;
}
</style>
