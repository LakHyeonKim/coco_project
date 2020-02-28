<template>
	<div id="backBox">
		<div id="header">
			<div id="headInnerBox">
				<label for="searchBox">
					<img
						src="../assets/CC_Logo.png"
						alt="logo_image"
						id="searchLogo"
					/>
				</label>
				<div id="CC">Code Coworker</div>
			</div>
		</div>
		<div id="blankBox"></div>
		<div id="mainBox">
			<PostList id="subBox"></PostList>
		</div>
		<div class="footerBlank"></div>
	</div>
</template>

<script>
import PostList from "../components/PostList";

export default {
	name: "NewsFeed",
	data() {
		return {};
	},
	components: {
		PostList
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
			} else {
				document.getElementById("header").style.height = "75px";
				document.getElementById("headInnerBox").style.padding =
					"20px 10px 20px 10px";
				document.getElementById("CC").style.display = "none";
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

<style scoped>
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
#blankBox {
	height: 100px;
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
	padding-top: 0;
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
	#CC {
		display: inline-block;
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
	#subBox {
		width: 100%;
		padding-left: 0;
		padding-right: 0;
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
