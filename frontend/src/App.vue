<template>
	<v-app>
		<!-- Nav -->
		<div v-if="isAuthenticated">
			<NavBar :userProfile="userProfile" />
		</div>
		<v-content>
			<router-view
				:key="$route.fullPath"
				@updateUserProfile="updateUserProfile"
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
			userProfile: "../img/icons/user.png"
		};
	},
	methods: {
		updateUserProfile(temp) {
			this.userProfile = temp;
			console.log("업데이트할고야ㅑㅑㅑ" + temp);
		}
	},
	updated() {
		this.isAuthenticated = this.$session.get("accessToken");
	},
	mounted() {
		this.userProfile = this.$session.get("imageUrl");
	}
};
</script>
