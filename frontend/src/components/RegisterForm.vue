<template>
	<div style="display: flex ; align-itmes: center; justify-content: center;">
		<validation-observer ref="form">
			<form
				@submit.prevent="register"
				id="formData"
				enctype="multipart/form-data"
			>
				<!-- 안되는 코드 -->
				<!-- <img-inputer
					name="file"
					v-model="singUpMember.file"
					size="middle"
					placeholder="Drop file here or click"
					bottomText="Drop file here or click"
				/> -->
				<div>
					<div>
						<label>Profile Image</label>
						<input
							type="file"
							@change="previewImage"
							accept="image/*"
							name="file"
							class="w3-input w3-border"
						/>
					</div>
					<div class="image-preview" v-if="singUpMember.file.length > 0">
						<img class="preview" :src="singUpMember.file" />
					</div>
				</div>
				<!-- <v-gravatar :email="email" alt="gravatar" :size="50" /> -->
				<input
					type="hidden"
					name="rankId"
					:value="this.singUpMember.rankId"
				/>
				<input
					type="hidden"
					name="gitUrl"
					:value="this.singUpMember.gitUrl"
				/>
				<input
					type="hidden"
					name="grade"
					:value="this.singUpMember.grade"
				/>
				<input
					type="hidden"
					name="idmember"
					:value="this.singUpMember.idmember"
				/>
				<input
					type="hidden"
					name="instargramUrl"
					:value="this.singUpMember.instargramUrl"
				/>
				<input
					type="hidden"
					name="isDelete"
					:value="this.singUpMember.isDelete"
				/>
				<input
					type="hidden"
					name="isManager"
					:value="this.singUpMember.isManager"
				/>
				<input
					type="hidden"
					name="kakaoUrl"
					:value="this.singUpMember.kakaoUrl"
				/>
				<validation-provider
					name="닉네임 "
					rules="required|alpha"
					v-slot="{ errors }"
				>
					<v-text-field
						name="nickname"
						v-model="singUpMember.nickname"
						:counter="10"
						label="닉네임"
						:error-messages="errors[0] ? errors[0] : []"
					></v-text-field>
				</validation-provider>
				<validation-provider
					name="이름 "
					rules="required|alpha"
					v-slot="{ errors }"
				>
					<v-text-field
						name="id"
						v-model="singUpMember.id"
						:counter="10"
						label="이름"
						:error-messages="errors[0] ? errors[0] : []"
					></v-text-field>
				</validation-provider>
				<validation-provider
					name="이메일 "
					rules="required|email"
					v-slot="{ errors }"
				>
					<v-text-field
						name="email"
						v-model="singUpMember.email"
						label="이메일"
						:error-messages="errors[0] ? errors[0] : []"
					></v-text-field>
				</validation-provider>
				<validation-provider
					name="비밀번호 "
					vid="confirmation"
					rules="required|min:8"
					v-slot="{ errors }"
				>
					<v-text-field
						name="password"
						v-model="singUpMember.password"
						label="비밀번호"
						:type="pwd1 ? 'text' : 'password'"
						:error-messages="errors[0] ? errors[0] : []"
						:append-icon="pwd1 ? 'mdi-eye' : 'mdi-eye-off'"
						@click:append="pwd1 = !pwd1"
					></v-text-field>
				</validation-provider>
				<validation-provider
					name="비밀번호 확인 "
					rules="confirmed:confirmation"
					v-slot="{ errors }"
				>
					<v-text-field
						v-model="passwordConfirm"
						label="비밀번호 확인"
						:type="pwd2 ? 'text' : 'password'"
						:error-messages="errors[0] ? errors[0] : []"
						:append-icon="pwd2 ? 'mdi-eye' : 'mdi-eye-off'"
						@click:append="pwd2 = !pwd2"
					></v-text-field>
				</validation-provider>

				<v-btn class="mr-4" type="submit">회원가입</v-btn>
				<v-btn @click="clear">초기화</v-btn>
			</form>
			<div>
				<button v-on:click="idCheck">중복확인</button>
				<div v-if="duplicate">
					<div v-for="(message, idx) in duplicate" :key="idx">
						{{ message }}
					</div>
				</div>
			</div>
		</validation-observer>
	</div>
</template>

<script>
import http from '../http-common'
import router from '../router'
import { ValidationProvider, ValidationObserver } from 'vee-validate'

export default {
	name: 'RegisterForm',
	components: {
		ValidationProvider,
		ValidationObserver
	},
	data: () => ({
		singUpMember: {
			file: '',
			idmember: 0,
			rankId: 1,
			isManager: 0,
			isDelete: 0,
			nickname: '',
			id: '',
			password: '',
			email: '',
			gitUrl: '',
			kakaoUrl: '',
			instagramUrl: '',
			grade: ''
		},
		pwd1: false,
		pwd2: false,
		passwordConfirm: '',
		duplicate: [],
		idcheck: false,
		loading: false
	}),

	methods: {
		register () {
			let formData = new FormData(document.getElementById('formData'))
			window.console.log(this.singUpMember.file)
			// console.log(this.onSubmit());
			if (this.onSubmit() && this.idcheck) {
				this.$store.dispatch('startLoading')
				console.log('REGISTER beforeaxios ', formData)
				http
					.post('http://localhost:8888/api/signUp/', formData)
					.then(res => {
						console.log('REGISTER then ', res)
						this.$store.dispatch('endLoading')
						alert('회원가입이 성공적으로 완료되었습니다.')
						router.push('/')
					})
					.catch(err => {
						this.$store.dispatch('endLoading')
						console.log('REGISTER catch ', err)
					})
			} else {
				console.log('REGISTER ', '검증 실패')
			}
		},
		previewImage: function (event) {
			var input = event.target
			if (input.files && input.files[0]) {
				var reader = new FileReader()
				reader.onload = e => {
					this.singUpMember.file = e.target.result
				}
				reader.readAsDataURL(input.files[0])
			}
		},
		onSubmit () {
			this.$refs.form.validate().then(success => {
				if (!success) {
					alert('제출양식에 맞지 않습니다.')
					return false
				}
			})
			return true
		},
		clear () {
			this.singUpMember.nickname = ''
			this.singUpMember.name = ''
			this.singUpMember.email = ''
			this.singUpMember.password = ''
			this.passwordConfirm = ''
			// this.$validator.reset();
		},
		valid () {
			this.$refs.form.validate()
		},
		idCheck () {
			if (this.singUpMember.id) {
				this.duplicate = []
				console.log('DUPLICATE ', this.singUpMember.id)
				http.post('/check/', {
					id: this.singUpMember.id
				})
					.then(res => {
						console.log('DUPLICATE then ', this.singUpMember.id)
						console.log('DUPLICATE then ', res)
						if (res.data) {
							this.duplicate.push('사용하실수 있는 아이디입니다.')
							this.idcheck = true
						} else {
							this.duplicate.push('아이디가 중복되었습니다.')
						}
					})
					.catch(err => {
						console.log('DUPLICATE catch ', err)
					})
			} else {
				this.duplicate.push('아이디를 입력해 주십시오.')
			}
		}
	},
	mounted () {
		// this.localize("ko", this.dictionary)
	},
	computed: {
		loading: function () {
			return this.$store.state.loading
		}
	}
}
</script>

<style scoped>
#regiform {
	display: flex;
	/* align-items: center; */
	justify-content: center;
	vertical-align: middle;
}
#nickname {
	background-color: #e9cde7;
	border: 1px black;
	border-radius: 5px;
}
#id {
	background-color: #e9cde7;
	border: 1px black;
	border-radius: 5px;
}
#email {
	background-color: #e9cde7;
	border: 1px black;
	border-radius: 5px;
}
#password {
	background-color: #e9cde7;
	border: 1px black;
	border-radius: 5px;
}
#pwdcheck {
	background-color: #e9cde7;
	border: 1px black;
	border-radius: 5px;
}
img.preview {
  width: 200px;
  background-color: white;
  border: 1px solid #ddd;
  padding: 5px;
}
</style>
