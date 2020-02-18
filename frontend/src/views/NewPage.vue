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
	},
	methods: {
		scrollEvent() {
			if (
				document.body.scrollTop > 50 ||
				document.documentElement.scrollTop > 50
			) {
				document.getElementById("header").style.height = "50px";
				document.getElementById("headInnerBox").style.padding = "10px";
				document.getElementById("CC").style.display = "inline-block";
				document.getElementById("serachBox").style.display = "none";
			} else {
				document.getElementById("header").style.height = "75px";
				document.getElementById("headInnerBox").style.padding =
					"20px 10px 20px 10px";
				document.getElementById("CC").style.display = "none";
				document.getElementById("serachBox").style.display =
					"inline-block";
			}
		}
	},
	created: function() {
		console.log("크리에이트는 언제 찍힐까");
		window.addEventListener("scroll", this.scrollEvent);
	},
	beforeDestroy: function() {
		console.log("destroy kasjdfhkasjdfhlkajsdfhlkajsdfhlkajsdfhakl");
		window.removeEventListener("scroll", this.scrollEvent);
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
	transition: 0.2s;
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
