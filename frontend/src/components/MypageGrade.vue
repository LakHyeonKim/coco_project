<template>
	<div class="text-center">
		<v-dialog v-model="dialog" width="500">
			<template v-slot:activator="{ on }">
				<div v-on="on"><slot name="click"></slot></div>
			</template>

			<v-card class="d_container">
				<div>
					<p style="font-size: 20px">
						당신의
						<b
							style="color:rgba(160, 23, 98, 0.7); font-size: 30px"
						>
							등급 </b
						>은?
					</p>
				</div>
				<div>
					<div id="gradeBox">
						<img
							id="gradeImg"
							src="../assets/rank/iron.png"
							alt="iron"
							:style="iron ? big : small"
						/>
					</div>
					<div id="gradeBox">
						<img
							id="gradeImg"
							src="../assets/rank/bronze.png"
							alt="bronze"
							:style="bronze ? big : small"
						/>
					</div>
					<div id="gradeBox">
						<img
							id="gradeImg"
							src="../assets/rank/silver.png"
							alt="silver"
							:style="silver ? big : small"
						/>
					</div>
					<div id="gradeBox">
						<img
							id="gradeImg"
							src="../assets/rank/gold.png"
							alt="gold"
							:style="gold ? big : small"
						/>
					</div>
					<div id="gradeBox">
						<img
							id="gradeImg"
							src="../assets/rank/platinum.png"
							alt="platinum"
							:style="platinum ? big : small"
						/>
					</div>
					<div id="gradeBox">
						<img
							id="gradeImg"
							src="../assets/rank/diamond.png"
							alt="dia"
							:style="dia ? big : small"
						/>
					</div>
					<div id="gradeBox">
						<img
							id="gradeImg"
							src="../assets/rank/master.png"
							alt="master"
							:style="master ? big : small"
						/>
					</div>
					<div id="gradeBox">
						<img
							id="gradeImg"
							src="../assets/rank/challenger.png"
							alt="challenger"
							:style="challenger ? big : small"
						/>
					</div>
				</div>
			</v-card>
		</v-dialog>
	</div>
</template>

<script>
export default {
	name: "Mypagegrade",
	props: {
		grade: {}
	},
	data() {
		return {
			dialog: false,
			iron: false,
			bronze: false,
			silver: false,
			gold: false,
			platinum: false,
			dia: false,
			master: false,
			challenger: false,
			big: {
				width: "100px",
				height: "100px"
			},
			small: {
				width: "50px",
				height: "50px"
			}
		};
	},
	mounted() {
		console.log("나의 등급은?", this.grade);
	},
	watch: {
		grade: function() {
			if (this.grade == "아이언") {
				this.iron = true;
			} else if (this.grade == "브론즈") {
				this.bronze = true;
			} else if (this.grade == "실버") {
				this.silver = true;
			} else if (this.grade == "골드") {
				this.gold = true;
			} else if (this.grade == "플래티넘") {
				this.platinum = true;
			} else if (this.grade == "다이아") {
				this.dia = true;
			} else if (this.grade == "마스터") {
				this.master = true;
			} else if (this.grade == "챌린저") {
				this.challenger = true;
			}
		}
	}
};
</script>

<style>
#gradeBox {
	display: inline-block;
}
#gradeImg {
	width: 50px;
	height: 50px;
	border-radius: 50%;
}
</style>
