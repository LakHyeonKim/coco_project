<template>
	<div class="searchBox">
		<!-- html 기본속성 방지를 위해 .prevent 사용 -->
		<div id="header">
			<div id="headInnerBox">
				<label for="searchBox">
					<img
						src="../assets/CC_Logo.png"
						alt="logo_image"
						id="searchLogo"
					/>
					<div id="CC">
						CODE COWORKERS
					</div>
				</label>
				<!-- <p id="codeCoworker">Code Coworker</p> -->
				<form v-on:submit.prevent="searchwords" id="serachBox">
					<v-select
						id="options"
						:items="searchOption"
						@change="selectOption"
						dense
						item-color="black"
						color="rgba(0, 0, 0, 0.5)"
						placeholder="검색조건"
						height="28px"
						style="width: 100px; float: left; font-size: 15px; transform: translateY(-13%); "
					></v-select>
					<input
						type="text"
						v-model="words"
						id="searchInput"
						placeholder="Search"
					/>
					<button type="submit" id="searchButton">
						<img
							class="searchIcon"
							src="../assets/icon/search_b.png"
						/>
					</button>
				</form>
			</div>
		</div>
	</div>
</template>

<script>
export default {
	name: "SearchBar",
	data() {
		return {
			words: "",
			searchOption: [
				{ text: "제목", value: 1 },
				{ text: "내용", value: 2 },
				{ text: "글쓴이", value: 3 },
				{ text: "태그", value: 4 }
			],
			value: 0
		};
	},
	methods: {
		searchwords() {
			// console.log("emit value ", this.value);
			this.$emit("searchwords", this.words, this.value);
			// this.words = "";
		},
		selectOption(values) {
			// console.log(value);
			this.value = values;
		},
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
		// console.log("크리에이트는 언제 찍힐까");
		window.addEventListener("scroll", this.scrollEvent);
	},
	beforeDestroy: function() {
		// console.log("destroy kasjdfhkasjdfhlkajsdfhlkajsdfhlkajsdfhakl");
		window.removeEventListener("scroll", this.scrollEvent);
	}
};
</script>

<style scoped>
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
	/* transition: 0.2s; */
}
#searchLogo {
	display: inline-block;
	width: 30px;
	height: 30px;
	vertical-align: middle;
	margin-right: 10px;
}
#CC {
	display: none;
}
#serachBox {
	display: inline-block;
}
#searchInput {
	display: inline-block;
	vertical-align: middle;
	border-bottom: 0.25px solid gray;
	margin-left: 5px;
	margin-right: 10px;
	width: 130px;
	-webkit-transition: width 0.4s ease-in-out;
	transition: width 0.4s ease-in-out;
	outline: none;
}
#searchInput:focus {
	width: 25vw;
}
::placeholder {
	color: black;
	font-size: 18px;
}
#searchButton {
	display: inline-block;
	vertical-align: middle;
	height: 25px;
}
.searchIcon {
	width: 25px;
	color: black;
}
#codeCoworker {
	display: none;
}
@media screen and (max-width: 600px) {
	#header {
		height: 65px;
	}
	#headInnerBox {
		text-align: center;
		padding: 0px;
		padding-top: 15px;
	}
	#searchLogo {
		display: inline-block;
		width: 30px;
		height: 30px;
		vertical-align: middle;
		margin-right: 10px;
	}
	#serachBox {
		display: inline-block;
	}
	#searchInput {
		display: inline-block;
		vertical-align: middle;
		border-bottom: 1px solid black;
		width: 25vw;
		margin-left: 5px;
		margin-right: 10px;
	}
	#searchInput:focus {
		width: 40vw;
	}
	::placeholder {
		color: black;
		font-size: 20px;
	}
	#searchButton {
		display: inline-block;
		vertical-align: middle;
		height: 25px;
	}
	.searchIcon {
		width: 25px;
		color: black;
	}
	#codeCoworker {
		display: none;
	}
}
</style>
