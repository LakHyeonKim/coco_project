<template>
	<v-app>
		<!-- Nav -->
		<div v-if="isAuthenticated">
			<NavBar></NavBar>
		</div>
		<v-content>
			<router-view :key="$route.fullPath" />
		</v-content>
	</v-app>
</template>

<script>
// import store from "./store";
import NavBar from "@/components/NavBar";

export default {
	name: "App",
	// store,
	components: {
		NavBar
	},
	data() {
		return {
			isAuthenticated: this.$session.get("accessToken")
		};
	},
	updated() {
		this.isAuthenticated = this.$session.get("accessToken");
	}
};
</script>
