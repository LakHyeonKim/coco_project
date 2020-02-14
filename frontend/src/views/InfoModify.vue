<template>
	<div>
		<div id="title">
			<div id="title_text">회원정보수정</div>
		</div>
		<InfoModifyForm />
		<div id="blank"></div>
	</div>
</template>

<script>
import InfoModifyForm from "@/components/InfoModifyForm";

export default {
	name: "InfoModify",
	components: {
		InfoModifyForm
	}
};
</script>

<style>
#title {
	margin: 0 auto;
	padding: 20px;
	font-size: 30px;
	font-weight: 500;
	text-align: center;
	background: white;
	box-shadow: 0.5px 0.5px 3px rgba(0, 0, 0, 0.2);
	position: fixed;
	top: 0;
	width: 100%;
	z-index: 90;
}

@media screen and (max-width: 600px) {
	#title {
		font-size: 20px;
		padding: 10px;
	}
}
</style>
