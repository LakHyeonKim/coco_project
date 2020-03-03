<template>
	<div>
		<v-dialog v-model="dialog" width="300">
			<template v-slot:activator="{ on }">
				<div v-on="on"><slot name="click"></slot></div>
			</template>

			<v-card class="d_container">
				<div class="d_body">
					<div v-if="isFollow == 1">
						정말
						{{ nickname }} 님을<br />
						팔로우 취소하시겠습니까?
					</div>
					<div v-else>
						{{ nickname }}
						님을<br />팔로우 하시겠습니까?
					</div>
				</div>
				<div class="d_footer">
					<button class="d_btn" @click="ok()">
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
export default {
	props: {
		follow: {},
		nickname: {},
		isFollow: {}
	},
	data() {
		return {
			dialog: false
		};
	},
	methods: {
		ok() {
			this.follow();
			this.dialog = false;
		}
	}
};
</script>
