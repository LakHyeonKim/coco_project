<template>
	<v-app>
		<!-- Nav -->
		<div v-if="isAuthenticated">
			<NavBar></NavBar>
		</div>
		<v-content>
			<router-view />
		</v-content>
	</v-app>
</template>

<script>
// import store from "./store";
import NavBar from "./components/NavBar";

export default {
	name: "App",
	// store,
	components: {
		NavBar
	},
	data() {
		return {
			isAuthenticated: this.$session.has("jwt")
		};
	},
	updated() {
		this.isAuthenticated = this.$session.has("jwt");
	}
	// computed: {
	// 	isLoggedIn: function() {
	// 		console.log("isLoggedIn ", this.$store.getters.isLoggedIn);
	// 		return this.$store.getters.isLoggedIn;
	// 	}
	// }
};
</script>
