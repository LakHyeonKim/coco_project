<template>
	<div id="Table">
		<table>
			<tr>
				<th>idtag</th>
				<th>
					<input type="text" v-model="tagUpdate.idtag" />
				</th>
			</tr>
			<tr>
				<th>tagName</th>
				<th>
					<input type="text" v-model="tagUpdate.tagName" />
				</th>
			</tr>
			<tr>
				<th>searchedNumber</th>
				<th>
					<input type="text" v-model="tagUpdate.searchedNumber" />
				</th>
			</tr>
			<tr>
				<th>includedNumber</th>
				<th>
					<input type="text" v-model="tagUpdate.includedNumber" />
				</th>
			</tr>
			<tr>
				<th>firstTimeCreated</th>
				<th>
					{{ tagUpdate.firstTimeCreated }}
				</th>
			</tr>
			<tr>
				<th>mostRecentlyMentionedTime</th>
				<th>
					{{ tagUpdate.mostRecentlyMentionedTime }}
				</th>
			</tr>
			<tr>
				<th>imagePath</th>
				<th>
					<input type="text" v-model="tagUpdate.imagePath" />
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
			tagUpdate: {
				idtag: 0,
				tagName: 0,
				searchedNumber: 0,
				includedNumber: 0,
				firstTimeCreated: "",
				mostRecentlyMentionedTime: "",
				imagePath: ""
			}
		};
	},
	methods: {
		Update() {
			const requestForm = {
				// idtag: this.tagUpdate.idtag,
				// tagName: this.tagUpdate.tagName,
				searchedNumber: this.tagUpdate.searchedNumber,
				includedNumber: this.tagUpdate.includedNumber,
				imagePath: this.tagUpdate.imagePath
			};
			http.put("/api/updateTag", requestForm, this.requestForm)
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
		const tagInfo = {
			idtag: this.$route.params.no
		};
		// console.log(tagInfo);
		http.post("/api/findTag", tagInfo, this.requestForm)
			.then(res => {
				// console.log("get tag res ", res);
				this.tagUpdate.idtag = res.data[0].idtag;
				this.tagUpdate.tagName = res.data[0].tagName;
				this.tagUpdate.searchedNumber = res.data[0].searchedNumber;
				this.tagUpdate.includedNumber = res.data[0].includedNumber;
				this.tagUpdate.firstTimeCreated = res.data[0].firstTimeCreated;
				this.tagUpdate.mostRecentlyMentionedTime =
					res.data[0].mostRecentlyMentionedTime;
				this.tagUpdate.imagePath = res.data[0].imagePath;
			})
			.catch(err => {
				console.log("get tag err ", err);
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
