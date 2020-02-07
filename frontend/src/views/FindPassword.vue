<template>
	<div class="back">
		<h1>비밀번호 찾기</h1>
		<div class="inner">
			<input type="text" placeholder="아이디입력" v-model="id" />
			<input type="text" placeholder="닉네임입력" v-model="nick" />
			<button type="submit" @click="check()">SUBMIT</button>
		</div>
	</div>
</template>

<script>
import http from "../http-common";

export default {
	name: "FindPassword",
	data() {
		return {
			id: "",
			nick: ""
		};
	},
	methods: {
		check() {
			console.log(this.id);
			console.log(this.nick);
			const requestForm = {
				id: this.id,
				nickname: this.nick
			};
			console.log(requestForm);
			http.post("/test/mailTest/", requestForm)
				.then(res => {
					console.log(res);
				})
				.catch(err => {
					console.log(err);
				});
		}
	}
};
</script>

<style scoped>
::placeholder {
	color: black;
}
</style>
