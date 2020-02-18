<template>
	<div id="backBox">
		<div id="header">
			<div id="headInnerBox">
				<img
					src="../assets/CC_Logo.png"
					alt="logo_image"
					id="searchLogo"
				/>
				<div id="codeCoworker">Code Coworker</div>
			</div>
		</div>
		<createPage id="subBox"></createPage>
	</div>
</template>

<script>
import CreatePage from "../components/CreatePage";

export default {
	name: "NewPage",
	components: {
		CreatePage
	},
	data() {
		return {};
	}
};
</script>

<style>
#header {
	position: fixed;
	width: 100%;
	height: 75px;
	z-index: 1;
	background-color: white;
	border-bottom: 1px solid rgba(0, 0, 0, 0.2);
}
#headInnerBox {
	text-align: center;
	padding: 20px 10px 20px 10px;
}
#searchLogo {
	display: inline-block;
	width: 30px;
	height: 30px;
	vertical-align: middle;
}
#codeCoworker {
	display: none;
}

#backBox {
	height: 100%;
}

#subBox {
	height: 100%;
	width: 75vw;
	margin: 0 auto;
	padding: 30px;
	padding-top: 80px;
}
@media screen and (max-width: 600px) {
	#header {
		position: fixed;
		width: 100%;
		z-index: 1;
		border-bottom: 1px solid rgba(0, 0, 0, 0.2);
	}
	#headInnerBox {
		padding: 20px 10px 20px 10px;
	}
	#searchLogo {
		display: inline-block;
		width: 30px;
		height: 30px;
		margin-right: 10px;
	}
	#codeCoworker {
		display: inline-block;
		font-size: 20px;
		margin: 0px;
	}
	#subBox {
		width: 100vw;
	}
}
</style>
