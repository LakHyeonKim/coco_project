<template>
	<div class="back">
		<v-dialog v-model="dialog" width="350">
			<template v-slot:activator="{ on }">
				<div v-on="on"><slot name="click"></slot></div>
			</template>

			<v-card class="d_container">
				<div class="fPwdBody">
					<div id="fPwdText">
						비밀번호 찾기
					</div>
					<div class="inner">
						<input id="findPwdInput" type="text" placeholder="   아이디를 입력해주세요" v-model="id" />
					</div>
				</div>
				<div class="fPwdFooter">
					<button class="d_btn" @click="check()">
						전송
					</button>
					<button class="d_btn" @click="cancle()">
						취소
					</button>
				</div>
			</v-card>
		</v-dialog>
	</div>
</template>

<script>
import http from "../http-common";

export default {
	name: "FindPassword",
	data() {
		return {
			id: "",
			dialog: false
		};
	},
	methods: {
		check() {
			console.log(this.id);
			console.log(this.nick);
			const requestForm = {
				id: this.id,
			};
			console.log(requestForm);
			http.post("/jwt/getTemporaryPassword/", requestForm)
				.then(res => {
					console.log("findPwd then ", res);
					if (res.status == "200") {
						alert("해당 이메일로 임시 비밀번호가 전송되었습니다.")
						this.dialog = false
						this.id = ""
					} else {
						alert("아이디를 확인해 주세요")
						this.dialog = true
					}
				})
				.catch(err => {
					console.log("findPwd catch ", err);
				});
		},
		cancle() {
			this.dialog = false;
			this.id = ""
		}
	}
};
</script>

<style scoped>
.fPwdBody {
	height: 100%;
	margin-bottom: 15px;
}
#fPwdText {
	font-size: 22px;
	margin-bottom: 15px;
}
#findPwdInput {
	border: 1px black;
	border-radius: 5px;
	background-color: #e9cde7;
	font-size: 17px;
	height: 35px;
	width: 100%;
}
::placeholder {
	color: white;
	font-size: 17px;
}
.fPwdFooter {

}
</style>
