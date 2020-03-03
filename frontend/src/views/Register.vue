<template>
	<div class="mainBox">
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
.compo {
	width: 450px;
	height: 100%;
	/* background-color: white; */
	/* border: 1px solid rgba(187, 187, 187, 0.5); */
}
@media screen and (max-width: 450px) {
	.compo {
		width: 100%;
	}
}
</style>
