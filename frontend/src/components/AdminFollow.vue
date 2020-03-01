<template>
	<div id="Table">
		<table>
			<tr>
				<th>idfollow</th>
				<th>
					<input type="text" v-model="followUpdate.idfollow" />
				</th>
			</tr>
			<tr>
				<th>memberFollower</th>
				<th>
					<input type="text" v-model="followUpdate.memberFollower" />
				</th>
			</tr>
			<tr>
				<th>memberFollowing</th>
				<th>
					<input type="text" v-model="followUpdate.memberFollowing" />
				</th>
			</tr>
			<tr>
				<th>access</th>
				<th>
					<input type="text" v-model="followUpdate.access" />
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
	name: "AdminTag",
	data() {
		return {
			requestForm: {
				headers: {
					Authorization: ""
				}
			},
			followUpdate: {
				idfollow: 0,
				memberFollower: 0,
				memberFollowing: 0,
				access: 0
			}
		};
	},
	methods: {
		Update() {
			const requestForm = {
				idfollow: this.followUpdate.idfollow,
				memberFollower: this.followUpdate.memberFollower,
				memberFollowing: this.followUpdate.memberFollowing
				// access: this.followUpdate.access
			};
			http.put("/api/updateFollow", requestForm, this.requestForm)
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
		const followInfo = {
			idfollow: this.$route.params.no
		};
		// console.log(tagInfo);
		http.post("/api/findFollow", followInfo, this.requestForm)
			.then(res => {
				// console.log("get follow res ", res);
				this.followUpdate.idfollow = res.data[0].idfollow;
				this.followUpdate.memberFollower = res.data[0].memberFollower;
				this.followUpdate.memberFollowing = res.data[0].memberFollowing;
				this.followUpdate.access = res.data[0].access;
			})
			.catch(err => {
				console.log("get follow err ", err);
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
