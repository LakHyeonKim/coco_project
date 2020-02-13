<template>
	<div class="mainBox">
		<div class="blankBox"></div>
		<div class="compo">
			<RegisterForm />
		</div>
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

<style>
.mainBox {
	display: grid;
	justify-items: center;
}
/* .blankBox {
	height: 50px;
} */
.compo {
	width: 450px;
	height: 100%;
	/* background-color: white; */
	/* border: 1px solid rgba(187, 187, 187, 0.5); */
}
</style>
