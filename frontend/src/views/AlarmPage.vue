<template>
	<div class="backBox">
		<div class="blankBox"></div>
		<div class="alarmSubBox">
			<div class="alarmBox">
				<alarmCard></alarmCard>
			</div>
		</div>
	</div>
</template>

<script>
import AlarmCard from "../components/AlarmCard";

export default {
	name: "AlarmPage",
	components: {
		AlarmCard
	}
};
</script>

<style>
.backBox {
	height: 100%;
	background-color: none;
}
.blankBox {
	height: 100px;
}
.alarmSubBox {
	display: grid;
	justify-content: center;
	background-color: darkolivegreen;
	/* width: 80vw; */
}
.alarmBox {
	margin-left: 5vw;
	width: 70vw;
	background-color: antiquewhite;
}
</style>
