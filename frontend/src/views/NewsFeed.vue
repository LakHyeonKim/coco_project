<template>
	<!-- 웹 화면에서 사이즈가 줄어들 경우 좋아요, 댓글 튀어나감 (해쉬태그가 2줄이 되는 경우에) -->
	<!-- header 구성요소 -->
	<!-- 스택 로고 위치, 공간차지 -->
	<div id="backBox">
		<div id="header">
			<div id="headInnerBox">
				<label for="searchBox">
					<img
						src="../assets/logoo.png"
						alt="logo_image"
						id="searchLogo"
					/>
				</label>
				<input
					type="text"
					v-on:change="onInput"
					id="searchBox"
					placeholder="없으면 허전해"
				/>
				<p id="codeCoworker">Code Coworker</p>
			</div>
		</div>
		<div id="blankBox"></div>
		<div id="mainBox">
			<PostList id="subBox"></PostList>
		</div>
	</div>
</template>

<script>
import PostList from "../components/PostList";

export default {
	name: "NewsFeed",
	components: {
		PostList
	},
	methods: {
		onInput(e) {
			console.log(e.target.value);
		}
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
}
@media screen and (max-width: 600px) {
	#header {
		position: fixed;
		width: 100%;
		height: 50px;
		z-index: 1;
		/* background-color: white; */
		border-bottom: 1px solid rgba(0, 0, 0, 0.2);
	}
	#headInnerBox {
		padding: 10px;
	}
	#searchLogo {
		display: inline-block;
		width: 30px;
		height: 30px;
		margin-right: 10px;
	}
	#codeCoworker {
		display: inline-block;
	}
	#searchBox {
		display: none;
	}
	#blankBox {
		height: 30px;
	}
	#mainBox {
		align-content: center;
		justify-content: center;
		/* background-color: blueviolet; */
	}
	#subBox {
		width: 100%;
		padding-left: 0;
	}
}
</style>
