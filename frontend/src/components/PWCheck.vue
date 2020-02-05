<template>
	<transition name="modal" id="modal-template">
		<div class="modal-mask">
			<div class="modal-wrapper">
				<div class="modal-container">
					<div class="modal-header">
						비밀번호 확인
					</div>

					<div class="modal-body">
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

					<div class="modal-footer">
						<!-- default footer -->
						<button class="modal-default-button" @click="pwCheck()">
							확인
						</button>
						<button
							class="modal-default-button"
							@click="$emit('close')"
						>
							취소
						</button>
					</div>
				</div>
			</div>
		</div>
	</transition>
</template>
<script>
export default {
	data: () => ({
		clearable: true,
		counterEn: true,
		counter: 16,
		show: false,
		password: "Password",
		rules: {
			required: value => !!value || "Required.",
			min: v => v.length >= 8 || "Min 8 characters",
			emailMatch: () => "The email and password you entered don't match"
		},
		input_pw: ""
	}),
	methods: {
		pwCheck() {
			alert(this.input_pw);
			this.$router.push("/infoModify");
			// http.post("/api/findByMyPosts/", {
			// 	idMember: this.$session.get("id"),
			// 	order: idx
			// })
			// 	.then(response => {
			// 		this.posts = response.data;
			// 		console.log(this.posts);
			// 	})
			// 	.catch(error => {
			// 		console.log(error);
			// 	});
		}
	}
};
</script>
<style>
/* 모달 관련 css */
.modal-mask {
	position: fixed;
	z-index: 9998;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	background-color: rgba(0, 0, 0, 0.5);
	display: table;
	transition: opacity 0.3s ease;
}

.modal-wrapper {
	display: table-cell;
	vertical-align: middle;
}

.modal-container {
	width: 350px;
	height: 220px;
	margin: 0px auto;
	padding: 20px 40px 20px 40px;
	background-color: #fff;
	border-radius: 2px;
	box-shadow: 0 2px 8px rgba(0, 0, 0, 0.33);
	transition: all 0.3s ease;
	/* font-family: Helvetica, Arial, sans-serif; */
}

.modal-header {
	margin-top: 0;
	font-size: 20px;
	font-weight: 500;
}

.modal-body {
	margin: 20px 0;
	height: 80px;
}
.modal-default-button {
	margin-left: 15px;
	padding: 5px 10px 5px 10px;
	border-radius: 3px;
	background-color: rgba(160, 23, 98, 0.5);
	color: white;
}
.modal-default-button:hover {
	background-color: rgba(160, 23, 98, 0.8);
}

/*
 * The following styles are auto-applied to elements with
 * transition="modal" when their visibility is toggled
 * by Vue.js.
 *
 * You can easily play with the modal transition by editing
 * these styles.
 */

.modal-enter {
	opacity: 0;
}

.modal-leave-active {
	opacity: 0;
}

.modal-enter .modal-container,
.modal-leave-active .modal-container {
	-webkit-transform: scale(1.1);
	transform: scale(1.1);
}
</style>
