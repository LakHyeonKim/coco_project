<template>
	<v-card style="background-color:rgba(0,0,0,0.3);" class="postBox">
		<v-card-title primary-title>
			<v-flex>
				<div class="caption color-data">{{ formatedDate }}</div>
				<h2
					class="color-title headline font-weight-light line-clamp-title"
				>
					<b>{{ title }}</b>
				</h2>
				<p
					class="mb-1 color-body font-weight-light subheading line-clamp-body"
				>
					{{ body }}
				</p>
			</v-flex>
		</v-card-title>
	</v-card>
</template>

<script>
export default {
	name: "Post",
	props: {
		date: { type: Date },
		title: { type: String },
		body: { type: String }
	},
	computed: {
		formatedDate() {
			return `${this.date.getFullYear()}년 ${this.date.getMonth() +
				1}월 ${this.date.getDate()}일`;
		}
	}
};
</script>
<style>
.color-data {
	color: white;
}
.color-title {
	color: white;
}
.color-body {
	color: white;
}
.line-clamp-title {
	overflow: hidden;
	display: -webkit-box;
	-webkit-line-clamp: 1;
	-webkit-box-orient: vertical;
}
.line-clamp-body {
	overflow: hidden;
	display: -webkit-box;
	-webkit-line-clamp: 4;
	-webkit-box-orient: vertical;
}
.postBox {
	border-style: solid;
	border-width: 1px;
	border-color: white;
	height: 210px;
}
</style>
