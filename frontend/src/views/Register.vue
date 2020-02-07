<template>
	<div>
		<br />
		<h1 class="text-center">회원가입</h1>
		<br />
		<RegisterForm />
	</div>
</template>

<script>
import RegisterForm from "../components/RegisterForm";

export default {
	name: "Register",
	components: {
		RegisterForm
	}
};
</script>
