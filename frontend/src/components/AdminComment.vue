<template>
	<div id="Table">
		<table>
			<tr>
				<th>idcomment</th>
				<th>
					<input type="text" v-model="commentUpdate.idcomment" />
				</th>
			</tr>
			<tr>
				<th>memberId</th>
				<th>
					<input type="text" v-model="commentUpdate.memberId" />
				</th>
			</tr>
			<tr>
				<th>postId</th>
				<th>
					<input type="text" v-model="commentUpdate.postId" />
				</th>
			</tr>
			<tr>
				<th>commentWriter</th>
				<th>
					<input type="text" v-model="commentUpdate.commentWriter" />
				</th>
			</tr>
			<tr>
				<th>contents</th>
				<th>
					<textarea
						cols="30"
						rows="10"
						v-model="commentUpdate.contents"
					></textarea>
				</th>
			</tr>
			<tr>
				<th>dateCreated</th>
				<th>
					<input type="text" v-model="commentUpdate.dateCreated" />
				</th>
			</tr>
			<tr>
				<th>updateCreated</th>
				<th>
					<input type="text" v-model="commentUpdate.updateCreated" />
				</th>
			</tr>
			<tr>
				<th>access</th>
				<th>
					<input type="text" v-model="commentUpdate.access" />
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
	name: "AdminMember",
	data() {
		return {
			requestForm: {
				headers: {
					Authorization: ""
				}
			},
			commentUpdate: {
				idcomment: 0,
				memberId: "",
				postId: "",
				commentWriter: "",
				contents: "",
				dateCreated: "",
				updateCreated: 0,
				access: ""
			}
		};
	},
	methods: {
		Update() {
			const requestForm = {
				idcomment: this.commentUpdate.idcomment,
				memberId: this.commentUpdate.memberId,
				postId: this.commentUpdate.postId,
				commentWriter: this.commentUpdate.commentWriter,
				contents: this.commentUpdate.contents,
				access: this.commentUpdate.access
			};
			http.put("/api/updateComment", requestForm, this.requestForm)
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
		const commentInfo = {
			idcomment: this.$route.params.no
		};
		// console.log(postInfo);
		http.post("/api/findComment", commentInfo, this.requestForm)
			.then(res => {
				console.log("get comment res ", res);
				this.commentUpdate.idcomment = res.data[0].idcomment;
				this.commentUpdate.memberId = res.data[0].memberId;
				this.commentUpdate.postId = res.data[0].postId;
				this.commentUpdate.commentWriter = res.data[0].commentWriter;
				this.commentUpdate.contents = res.data[0].contents;
				this.commentUpdate.dateCreated = res.data[0].dateCreated;
				this.commentUpdate.updateCreated = res.data[0].updateCreated;
				this.commentUpdate.access = res.data[0].access;
			})
			.catch(err => {
				console.log("get comment err ", err);
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
#postTable {
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
