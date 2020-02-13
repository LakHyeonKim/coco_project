<template>
	<div class="text-center">
		<v-dialog v-model="dialog" width="300">
			<template v-slot:activator="{ on }">
				<div v-on="on"><slot name="click"></slot></div>
			</template>

			<v-card class="d_container">
				<div class="d_header">
					비밀번호 확인
				</div>
				<div class="d_body">
					<v-text-field
						:append-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
						:rules="[rules.required, rules.min]"
						:type="show ? 'text' : 'password'"
						hint="At least 8 characters"
						class="input-group--focused"
						@click:append="show = !show"
						:counter="counterEn ? counter : false"
						solo
						color="rgba(160, 23, 98, 0.5)"
						v-model="input_pw"
					></v-text-field>
				</div>
				<div class="d_footer">
					<button class="d_btn" @click="pwCheck()">
						확인
					</button>
					<button class="d_btn" @click="dialog = false">
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
	data() {
		return {
			dialog: false,
			clearable: true,
			counterEn: true,
			counter: 16,
			show: false,
			password: "Password",
			rules: {
				required: value => !!value || "Required.",
				min: v => v.length >= 8 || "Min 8 characters",
				emailMatch: () =>
					"The email and password you entered don't match"
			},
			input_pw: ""
		};
	},
	methods: {
		pwCheck() {
			console.log(this.input_pw);

			http.post("/api/checkPwd/", {
				idmember: this.$session.get("id"),
				password: this.input_pw
			})
				.then(response => {
					console.log(response.status);
					if (response.status == 204) {
						alert("비밀번호가 일치하지 않습니다!");
					} else {
						this.dialog = false;
						this.$store.state.isCheck = 1;
						this.$router.push("/infoModify/");
					}
				})
				.catch(error => {
					console.log(error);
				});
			this.input_pw = "";
		}
	}
};
</script>
