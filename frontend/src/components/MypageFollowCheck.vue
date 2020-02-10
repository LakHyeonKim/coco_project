<template>
	<div>
		<v-dialog v-model="dialog" width="500">
			<template v-slot:activator="{ on }">
				<div v-on="on"><slot name="click"></slot></div>
			</template>

			<v-card class="d_container">
				<div class="d_body">
					<div v-if="userInfo.isFollew == 1">
						정말
						{{ userInfo.member.nickname }} 님을<br />
						팔로우 취소하시겠습니까?
					</div>
					<div v-else>
						{{ userInfo.member.nickname }}
						님을<br />팔로우 하시겠습니까?
					</div>
				</div>
				<div class="d_footer">
					<button class="d_btn" @click="follow()">
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
	props: {
		userInfo: {}
	},
	data() {
		return {
			dialog: false
		};
	},
	methods: {
		follow() {
			this.dialog = false;
			console.log(this.$route.params.no);

			let address = "";
			if (this.userInfo.isFollew == 1) {
				address = "/trc/makeUnFollow/";
				this.userInfo.isFollew = 0;
				this.f_current = "팔로우";
			} else {
				address = "/trc/makeFollow/";
				this.userInfo.isFollew = 1;
				this.f_current = "팔로잉";
			}
			this.showModal_f = false;

			http.post(address, {
				memberFollower: this.$session.get("id"),
				// memberFollowing: this.$session.get("targetId")
				memberFollowing: this.$route.params.no
			})
				.then(response => {
					console.log(response);
				})
				.catch(error => {
					console.log(error);
					if (this.userInfo.isFollew == 1) {
						this.userInfo.isFollew = 0;
						this.f_current = "팔로우";
					} else {
						this.userInfo.isFollew = 1;
						this.f_current = "팔로잉";
					}
				});
		}
	}
};
</script>

<style lang="scss" scoped></style>
