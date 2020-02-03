<template>
	<div class="login">
		<h1>Login Page</h1>
		<LoginForm />
		<RegisterForm />
	</div>
</template>

<script>
// @: alias to src
import LoginForm from "@/components/LoginForm";
import RegisterForm from "@/components/RegisterForm";

export default {
	name: "Login",
	components: {
		LoginForm,
		RegisterForm
	}
};
</script>

<style></style>
