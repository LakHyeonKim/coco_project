<template>
	<v-card>
		<v-app-bar
			color="rgba(0, 0, 0, 0.4)"
			dense
			dark
			fixed
			elevation="10"
			inverted-scroll
			class="d-none d-sm-flex"
		>
			<router-link to="/" style="color:white; text-decoration:none;">
				<v-btn title>
					<i class="material-icons">home</i>
					Sloth
				</v-btn>
			</router-link>

			<v-spacer></v-spacer>

			<router-link to="/post" style="color:white; text-decoration:none;">
				<v-btn text>Post</v-btn>
			</router-link>

			<router-link
				to="/portfolio"
				style="color:white; text-decoration:none;"
			>
				<v-btn text>Portfolio</v-btn>
			</router-link>
			<router-link to="/login" style="color:white; text-decoration:none;">
				<v-btn text>Login</v-btn>
			</router-link>
			<router-link
				to="/register"
				style="color:white; text-decoration:none;"
			>
				<v-btn text>Register</v-btn>
			</router-link>
		</v-app-bar>

		<v-app-bar
			collapse
			class="hidden-sm-and-up"
			color="rgba(0, 0, 0, 0)"
			dense
			dark
			fixed
			top
		>
			<v-btn @click.stop="drawer = !drawer">
				<i class="material-icons">list</i>
			</v-btn>
		</v-app-bar>

		<v-navigation-drawer
			v-model="drawer"
			fixed
			left
			temporary
			class="hidden-sm-and-up"
			width="150px"
			dark
			:src="require('../assets/bg_drawer.jpg')"
		>
			<v-list nav dense>
				<v-list-item-group>
					<router-link
						to="/"
						style="color:white; text-decoration:none;"
					>
						<v-btn text>
							<i class="material-icons">home</i>
							Home
						</v-btn>
					</router-link>

					<router-link
						to="/post"
						style="color:white; text-decoration:none;"
					>
						<v-btn text>
							<i class="material-icons">library_books</i>
							Post
						</v-btn>
					</router-link>

					<router-link
						to="/portfolio"
						style="color:white; text-decoration:none;"
					>
						<v-btn text>
							<i class="material-icons">recent_actors</i>
							Portfolio
						</v-btn>
					</router-link>
				</v-list-item-group>
			</v-list>
		</v-navigation-drawer>
	</v-card>
</template>

<script>
export default {
	name: "Header",
	data() {
		return {
			drawer: false
		};
	}
};
</script>

<style></style>
