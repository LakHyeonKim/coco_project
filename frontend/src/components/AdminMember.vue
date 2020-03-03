<template>
	<div id="Table">
		<table>
			<tr>
				<th>idmember</th>
				<th>
					<input type="text" v-model="memberUpdate.idmember" />
				</th>
			</tr>
			<tr>
				<th>id</th>
				<th>
					<input type="text" v-model="memberUpdate.id" />
				</th>
			</tr>
			<tr>
				<th>email</th>
				<th>
					<input type="text" v-model="memberUpdate.email" />
				</th>
			</tr>
			<tr>
				<th>nickname</th>
				<th>
					<input type="text" v-model="memberUpdate.nickname" />
				</th>
			</tr>
			<tr>
				<th>password</th>
				<th>
					<input type="text" v-model="memberUpdate.password" />
				</th>
			</tr>
			<tr>
				<th>grade</th>
				<th>
					<input type="text" v-model="memberUpdate.grade" />
				</th>
			</tr>
			<tr>
				<th>rankId</th>
				<th>
					<input type="text" v-model="memberUpdate.rankId" />
				</th>
			</tr>
			<tr>
				<th>gitUrl</th>
				<th>
					<input type="text" v-model="memberUpdate.gitUrl" />
				</th>
			</tr>
			<tr>
				<th>instagramUrl</th>
				<th>
					<input type="text" v-model="memberUpdate.instagramUrl" />
				</th>
			</tr>
			<tr>
				<th>kakaoUrl</th>
				<th>
					<input type="text" v-model="memberUpdate.kakaoUrl" />
				</th>
			</tr>
			<tr>
				<th>imageUrl</th>
				<th>
					<input type="text" v-model="memberUpdate.imageUrl" />
				</th>
			</tr>
			<tr>
				<th>isManager</th>
				<th>
					<input type="text" v-model="memberUpdate.isManager" />
				</th>
			</tr>
			<tr>
				<th>isDelete</th>
				<th>
					<input type="text" v-model="memberUpdate.isDelete" />
				</th>
			</tr>
			<tr>
				<th>refreshToken</th>
				<th>
					<input type="text" v-model="memberUpdate.refreshToken" />
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
			memberUpdate: {
				idmember: 0,
				id: "",
				email: "",
				nickname: "",
				password: "",
				grade: "",
				rankId: 0,
				gitUrl: "",
				instagramUrl: "",
				kakaoUrl: "",
				imageUrl: "",
				isManager: 0,
				isDelete: 0,
				refreshToken: ""
			}
		};
	},
	methods: {
		Update() {
			// console.log(this.requestForm);
			const requestForm = {
				// idmember: this.memberUpdate.idmember,
				// id: this.memberUpdate.id,
				email: this.memberUpdate.email,
				nickname: this.memberUpdate.nickname,
				password: this.memberUpdate.password,
				// grade: this.memberUpdate.grade,
				rankId: this.memberUpdate.rankId,
				gitUrl: this.memberUpdate.gitUrl,
				instagramUrl: this.memberUpdate.instagramUrl,
				kakaoUrl: this.memberUpdate.kakaoUrl,
				imageUrl: this.memberUpdate.imageUrl,
				isManager: this.memberUpdate.isManager,
				isDelete: this.memberUpdate.isDelete
			};
			http.put("/api/updateMember", requestForm, this.requestForm)
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
		console.log(this.requestForm);
		document.querySelector("#navbar").setAttribute("style", "display:none");
		const memberInfo = {
			idmember: this.$route.params.no
		};
		// console.log(postInfo);
		http.post("/api/findMember", memberInfo, this.requestForm)
			.then(res => {
				// console.log("get member res ", res);
				this.memberUpdate.email = res.data[0].email;
				this.memberUpdate.gitUrl = res.data[0].gitUrl;
				this.memberUpdate.grade = res.data[0].grade;
				this.memberUpdate.id = res.data[0].id;
				this.memberUpdate.idmember = res.data[0].idmember;
				this.memberUpdate.imageUrl = res.data[0].imageUrl;
				this.memberUpdate.instagramUrl = res.data[0].instagramUrl;
				this.memberUpdate.isDelete = res.data[0].isDelete;
				this.memberUpdate.isManager = res.data[0].isManager;
				this.memberUpdate.kakaoUrl = res.data[0].kakaoUrl;
				this.memberUpdate.nickname = res.data[0].nickname;
				this.memberUpdate.password = res.data[0].password;
				this.memberUpdate.rankId = res.data[0].rankId;
				this.memberUpdate.refreshToken = res.data[0].refreshToken;
			})
			.catch(err => {
				console.log("get member err ", err);
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
