<template>
	<div class="text-center">
		<v-dialog v-model="dialog" width="350">
			<template v-slot:activator="{ on }">
				<div v-on="on"><slot name="click"></slot></div>
			</template>

			<v-card class="d_container">
				<div
					v-for="(follow, index) in followList"
					:key="follow.member.idmember"
				>
					<div class="f_div">
						<img
							class="f_img"
							:src="
								follow.member.imgUrl == null
									? '../img/icons/user.png'
									: follow.member.imgUrl
							"
						/>
						<div class="f_nick_div">
							<span>{{ follow.member.nickname }}</span>
						</div>

						<div class="f_btn_div">
							<button
								class="f_button"
								:style="
									follow.isFollow == 1
										? following_btn
										: follow_btn
								"
							>
								<img
									:src="
										follow.isFollow == 1
											? '../img/icons/check_g.png'
											: '../img/icons/plus_w.png'
									"
									width="13px"
								/>
							</button>
						</div>
					</div>
				</div>
			</v-card>
		</v-dialog>
	</div>
</template>

<script>
export default {
	props: {
		userInfo: {},
		follow: {},
		followList: {}
		// following_btn: {},
		// follow_btn: {}
	},
	data() {
		return {
			dialog: false,
			following_btn: {
				marginLeft: "10px",
				backgroundColor: "white",
				borderRadius: "5px",
				color: "gray",
				padding: "3px 10px 3px 10px",
				// boxShadow: "0.5px 0.5px 7px rgba(0, 0, 0, 0.3)"
				border: "1px solid silver"
			},
			follow_btn: {
				marginLeft: "10px",
				backgroundColor: "rgb(192, 110, 155)",
				borderRadius: "5px",
				color: "white",
				padding: "3px 10px 3px 10px"
				// boxShadow: "0.5px 0.5px 5px rgba(0, 0, 0, 0.2)"
			}
		};
	},
	methods: {}
};
</script>
<style>
.f_img {
	width: 40px;
	border: 1px solid gray;
	border-radius: 50%;
	float: left;
}
.f_nick_div {
	float: left;
	padding: 8px;
}
.f_div {
	display: inline-block;
	width: 100%;
	/* background-color: silver; */
	margin: 2px 0 2px 0;
}
.f_btn_div {
	padding: 5px;
	float: right;
}
::-webkit-scrollbar {
	width: 5.2px;
}
::-webkit-scrollbar-track {
	background: white;
}
::-webkit-scrollbar-thumb {
	background: rgba(0, 0, 0, 0.3);
}
::-webkit-scrollbar-thumb:hover {
	background: rgba(0, 0, 0, 0.5);
}
::-webkit-scrollbar-thumb:active {
	background: rgba(0, 0, 0, 0.7);
}
@media screen and (max-width: 400px) {
	.f_nick_div {
		font-size: 15px;
	}
}
</style>
