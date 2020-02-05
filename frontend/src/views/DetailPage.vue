<template>
	<!-- store를 통해 받아온 post number로 // post에 대한 모든 정보가 다 필요함 -->
	<!-- 로딩된 정보를 넘겨 받아서 바로 띄워주면? -->
	<div id="detailMain">
		asdfasdfasdfasdfasdf
		<div id="compo">
			<detail></detail>
		</div>
	</div>
</template>

<script>
import Detail from "../components/Detail";

export default {
	name: "DetailPage",
	components: {
		Detail
	}
};
</script>

<style>
#detailMain {
	background-color: bisque;
	height: 100%;
	width: 100%;
	text-align: center;
}
#compo {
	display: grid;
	justify-content: center;
}
</style>
