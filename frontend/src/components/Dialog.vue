<template>
	<div class="text-center">
		<!-- <v-dialog v-model="dialog" width="300px" :close="close">
			<template v-slot:activator="{ on }" slot="main">
				<div v-on="on"><slot name="click"></slot></div>
			</template>
			<v-card class="d_container">
				<slot name="header" class="d_header"></slot>
				<slot name="body" class="d_body"></slot>
				<slot name="d_ok"></slot>
				<button class="d_btn" @click="dialog = false">
					취소
				</button>
			</v-card>
		</v-dialog> -->
	</div>
</template>

<script>
export default {
	name: "dialog_view",
	data() {
		return { active: false };
	},
	methods: {
		close() {
			this.active = false;
		}
	}
};
</script>

<style>
.d_btn {
	margin-left: 15px;
	padding: 5px 10px 5px 10px;
	border-radius: 3px;
	background-color: rgba(160, 23, 98, 0.5);
	color: white;
}
.d_btn:hover {
	background-color: rgba(160, 23, 98, 0.8);
}
.d_container {
	margin: 0px auto;
	padding: 20px 26px 20px 25px;
	background-color: #fff;
	border-radius: 2px;
	box-shadow: 0 2px 8px rgba(0, 0, 0, 0.33);
	text-align: center;
}

.d_header {
	margin-top: 0;
	font-size: 20px;
	font-weight: 500;
}

.d_body {
	margin: 20px 0;
}
</style>
