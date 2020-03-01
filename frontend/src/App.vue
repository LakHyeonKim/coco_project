<template>
	<v-app>
		<!-- Nav -->
		<div v-if="isAuthenticated">
			<NavBar
				:userProfile="userProfile"
				:current="current"
				@updateUserProfile="updateUserProfile"
			/>
		</div>
		<v-content>
			<router-view
				:key="$route.fullPath"
				@updateUserProfile="updateUserProfile"
				@updateCurrent="updateCurrent"
			/>
		</v-content>
	</v-app>
</template>

<script>
import NavBar from "@/components/NavBar";

export default {
	name: "App",
	components: {
		NavBar
	},
	data() {
		return {
			isAuthenticated: this.$session.get("accessToken"),
			userProfile: "../img/icons/user.png",
			current: 0
		};
	},
	methods: {
		updateUserProfile(temp) {
			this.userProfile = temp;
			console.log("업데이트할고야ㅑㅑㅑ" + temp);
		},
		updateCurrent(num) {
			this.current = num;
		}
	},
	updated() {
		this.isAuthenticated = this.$session.get("accessToken");
	},
	mounted() {
		this.userProfile = this.$session.get("imageUrl");
		console.log("??????????????????" + this.$session.get("imageUrl"));
		this.current = this.$session.get("current");
	}
};
</script>
