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
		<div id="blankBox"></div>
		<div id="mainBox">
			<createPage id="subBox"></createPage>
		</div>
		<div class="footerBlank"></div>
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
#backBox {
	height: 100%;
}
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
#searchBox {
	display: inline-block;
	vertical-align: middle;
	border: 1px solid black;
	width: 20vw;
}
::placeholder {
	color: black;
	font-size: 20px;
}
#codeCoworker {
	display: none;
}
#blankBox {
	height: 75px;
}
#mainBox {
	display: grid;
	align-content: center;
	justify-content: center;
	/* background-color: blueviolet; */
}
#subBox {
	height: 100%;
	width: 80vw;
	/* background-color: red; */
	/* align-content: center; */
	justify-content: center;
	padding-left: 40px;
}
@media screen and (max-width: 600px) {
	#header {
		position: fixed;
		width: 100%;
		z-index: 1;
		/* background-color: white; */
		border-bottom: 1px solid rgba(0, 0, 0, 0.2);
	}
	#headInnerBox {
		padding: 20px 10px 20px 10px;
		/* vertical-align: middle; */
		/* align-content: center;
		justify-content: center; */
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
		/* padding-top: 10px; */
	}
	#searchBox {
		display: none;
	}
	#blankBox {
		display: block;
		height: 75px;
	}
	#mainBox {
		/* display: block; */
		/* align-content: center;
		justify-content: center; */
		/* background-color: blueviolet; */
	}
	#subBox {
		width: 100%;
		padding-left: 0;
	}
	.footerBlank {
		display: block;
		top: auto;
		bottom: 0;
		height: 17vw;
		width: 100%;
		padding: 0;
	}
}
</style>
