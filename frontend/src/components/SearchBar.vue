<template>
	<div class="searchBox">
		<!-- html 기본속성 방지를 위해 .prevent 사용 -->
		<div id="header">
			<div id="headInnerBox">
				<label for="searchBox">
					<img
						src="../assets/logoo.png"
						alt="logo_image"
						id="searchLogo"
					/>
				</label>
				<form v-on:submit.prevent="searchwords" id="serachBox">
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
				<p id="codeCoworker">Code Coworker</p>
			</div>
		</div>
	</div>
</template>

<script>
export default {
	name: "SearchBar",
	data() {
		return {
			words: ""
		};
	},
	methods: {
		searchwords() {
			this.$emit("searchwords", this.words);
			// this.words = "";
		}
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
#serachBox {
	display: inline-block;
}
#searchInput {
	display: inline-block;
	vertical-align: middle;
	border: 1px solid black;
	width: 20vw;
	margin-left: 10px;
	margin-right: 10px;
}
::placeholder {
	color: black;
	font-size: 30px;
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
</style>
