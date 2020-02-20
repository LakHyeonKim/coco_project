<template>
	<div id="el" class="canvas">
		<div :id="'totalCounter-' + idPost" class="total-counter">{{ likeCount }}</div>
		<div
			:id="'clap-' + idPost"
			class="clap-container"
			@click="addClap"
			@mouseover="addSonar"
			@mouseout="removeSonar"
		>
			<!-- <span class="clap-icon" :style="this.likeCheck ? { color: '#b4001e' } : { color: 'gray' }"> -->
			<span class="clap-icon" :style="likeCheck ? { color: '#b4001e' } : { color: 'gray' }">
				<i class="fa fa-thumb-tack"></i>
			</span>
		</div>

		<div :id="'clicker-' + idPost" class="click-counter">
			<span class="counter"></span>
		</div>

		<div :id="'sonar-clap-' + idPost" class="clap-container-sonar"></div>

		<div :id="'particles-1-' + idPost" class="particles-container">
			<div class="triangle">
				<div class="square"></div>
			</div>
			<div class="triangle">
				<div class="square"></div>
			</div>
			<div class="triangle">
				<div class="square"></div>
			</div>
			<div class="triangle">
				<div class="square"></div>
			</div>
			<div class="triangle">
				<div class="square"></div>
			</div>
		</div>

		<div :id="'particles-2-' + idPost" class="particles-container">
			<div class="triangle">
				<div class="square"></div>
			</div>
			<div class="triangle">
				<div class="square"></div>
			</div>
			<div class="triangle">
				<div class="square"></div>
			</div>
			<div class="triangle">
				<div class="square"></div>
			</div>
			<div class="triangle">
				<div class="square"></div>
			</div>
		</div>

		<div :id="'particles-3-' + idPost" class="particles-container">
			<div class="triangle">
				<div class="square"></div>
			</div>
			<div class="triangle">
				<div class="square"></div>
			</div>
			<div class="triangle">
				<div class="square"></div>
			</div>
			<div class="triangle">
				<div class="square"></div>
			</div>
			<div class="triangle">
				<div class="square"></div>
			</div>
		</div>
	</div>
</template>

<script>
import http from "../http-common";
export default {
	name: "MediumClap",
	props: {
		idPost: {},
		likeCheck: {},
		likeCount: {},
		likes: {}
	},
	data() {
		return {
			totalCount: 0,
			minDeg: 1,
			maxDeg: 72,
			particlesClasses: [
				{ class: "pop-top" },
				{ class: "pop-top-left" },
				{ class: "pop-top-right" },
				{ class: "pop-bottom-right" },
				{ class: "pop-bottom-left" }
			],

			clap: null,
			sonarClap: null,
			clickCounter: null,

			requestAddress: null
		};
	},
	methods: {
		addClap() {
			const particles = document.getElementById(
				`particles-1-${this.idPost}`
			);
			const particles2 = document.getElementById(
				`particles-2-${this.idPost}`
			);
			const particles3 = document.getElementById(
				`particles-3-${this.idPost}`
			);
			const totalClickCounter = document.getElementById(
				`totalCounter-${this.idPost}`
			);

			if (!this.likeCheck) {
				// if (!this.clap.classList.contains("clicked")) {
				this.requestAddress = "/trc/pushLike/";
				this.$store.state.likeUpdate = 1;
				this.$emit("updateLike", +1);
				// this.likeCheck = 1;
				// document.querySelector(".clap-icon").style.color = "#b4001e";
				this.totalCount++;
				this.clickCounter.children[0].innerText = "+1";

				if (!particles.classList.contains("animating")) {
					this.animateParticles(particles, 700);
				} else if (!particles2.classList.contains("animating")) {
					this.animateParticles(particles2, 700);
				} else if (!particles3.classList.contains("animating")) {
					this.animateParticles(particles3, 700);
				}

				this.runAnimationCycle(this.clap, "scale");
			} else {
				this.requestAddress = "/trc/unLike/";
				this.$store.state.likeUpdate = -1;
				this.$emit("updateLike", -1);
				// this.likeCheck = 0;
				// document.querySelector(".clap-icon").style.color = "gray";
				this.totalCount--;
				this.clickCounter.children[0].innerText = "-1";
			}

			if (this.clickCounter.classList.contains("first-active")) {
				this.runAnimationCycle(this.clickCounter, "active");
			} else {
				this.runAnimationCycle(this.clickCounter, "first-active");
			}
			this.runAnimationCycle(totalClickCounter, "fader");

			http.post(
				this.requestAddress,
				{
					member: {
						idmember: this.$session.get("id")
					},
					post: {
						idpost: this.idPost
					}
				},
				{
					headers: {
						Authorization: this.$session.get("accessToken")
					}
				}
			)
				.then(res => {
					console.log(res);
				})
				.catch(err => {
					console.log(err);
				});
		},

		addSonar() {
			this.sonarClap.classList.add("hover-active");
		},
		removeSonar() {
			this.sonarClap.classList.remove("hover-active");
		},
		runAnimationCycle(el, className, duration) {
			if (el && !el.classList.contains(className)) {
				el.classList.add(className);
			} else {
				el.classList.remove(className);
				void el.offsetWidth; // Trigger a reflow in between removing and adding the class name
				el.classList.add(className);
			}
		},
		runParticleAnimationCycle(el, className, duration) {
			if (el && !el.classList.contains(className)) {
				el.classList.add(className);
				setTimeout(() => {
					el.classList.remove(className);
				}, duration);
			}
		},
		animateParticles(particles, duration) {
			this.addRandomParticlesRotation(
				particles.id,
				this.minDeg,
				this.maxDeg
			);
			for (let i = 0; i < this.particlesClasses.length; i++) {
				this.runParticleAnimationCycle(
					particles.children[i],
					this.particlesClasses[i].class,
					duration
				);
			}
			// Boolean functionality only to activate particles2, particles3 when needed
			particles.classList.add("animating");
			setTimeout(() => {
				particles.classList.remove("animating");
			}, duration);
		},
		getRandomInt(min, max) {
			return Math.floor(Math.random() * (max - min + 1)) + min;
		},

		addRandomParticlesRotation(particlesName, minDeg, maxDeg) {
			const particles = document.getElementById(particlesName);
			const randomRotationAngle =
				this.getRandomInt(minDeg, maxDeg) + "deg";
			particles.style.transform = `rotate(${randomRotationAngle})`;
		}
	},
	mounted() {
		this.clap = document.getElementById(`clap-${this.idPost}`);
		this.sonarClap = document.getElementById(`sonar-clap-${this.idPost}`);
		this.clickCounter = document.getElementById(`clicker-${this.idPost}`);
	},
	watch: {
		likeCount: function(data) {
			this.totalCount = data;
		},
		likeCheck: function(data) {
			if (data) {
				this.clap.classList.add("clicked");
			} else {
				this.clap.classList.remove("clicked");
			}
		}
	}
};
</script>

<style lang="scss" scoped>
$default-clap-color: #b4001e;
.canvas {
	display: flex;
	justify-content: center;
	align-items: center;
	width: 100px;
	height: 100px;
	.total-counter {
		position: relative;
		display: flex;
		justify-content: center;
		align-items: center;
		width: 100%;
		// position: absolute;
		// margin-top: -45px;
		margin-left: 100px;
		color: gray;
		font-family: sans-serif;
		font-size: 16px;
	}
	.total-counter.fader {
		animation: fade-in 1400ms forwards;
	}
	.clap-container {
		display: flex;
		justify-content: center;
		align-items: center;
		text-align: center;
		position: absolute;
		width: 60px;
		height: 60px;
		border: 1px solid rgba(0, 0, 0, 0.15);
		border-radius: 50%;
		z-index: 2;
		background: #fff;
		cursor: pointer;
		.clap-icon {
			// font-size: 30px;
			font-size: 2rem;
			color: $default-clap-color;
		}
	}
	.clap-container:hover {
		border: 1px solid $default-clap-color;
	}
	.clap-container.scale {
		animation: scaleAndBack 700ms forwards;
	}
	.click-counter {
		display: flex;
		justify-content: center;
		align-items: center;
		width: 35px;
		height: 35px;
		position: absolute;
		// top: 132px;
		background-color: $default-clap-color;
		border-radius: 50%;
		z-index: 1;
		.counter {
			font-family: sans-serif;
			font-size: 14px;
			color: #fff;
		}
	}
	.click-counter.first-active {
		animation: first-bump-in 1s forwards;
	}
	.click-counter.active {
		animation: bump-in 1s forwards;
	}
	.clap-container-sonar {
		width: 60px;
		height: 60px;
		background: $default-clap-color;
		border-radius: 50%;
		position: absolute;
		opacity: 0;
		z-index: 0;
	}
	.hover-active {
		animation: sonar-wave 1.5s forwards;
		// animation-name: sonar-wave;
		// -webkit-animation-fill-mode: forwards;
		// animation-duration: 3s;
		animation-iteration-count: infinite;
	}
	.particles-container {
		display: flex;
		justify-content: center;
		align-items: center;
		width: 60px;
		height: 60px;
		position: absolute;
		/* border: 1px solid gray; */
		/* z-index: 3; */
		.triangle {
			border-left: 4px solid transparent;
			border-right: 4px solid transparent;
			border-top: 10px solid red;
			border-bottom: 4px solid transparent;
			position: absolute;
			.square {
				width: 5px;
				height: 5px;
				background: $default-clap-color;
				position: absolute;
				left: -15px;
				top: 0;
			}
		}
		.pop-top {
			animation: pop-top 1s forwards;
		}
		.pop-top-left {
			animation: pop-top-left 1s forwards;
		}
		.pop-top-right {
			animation: pop-top-right 1s forwards;
		}
		.pop-bottom-right {
			animation: pop-bottom-right 1s forwards;
		}
		.pop-bottom-left {
			animation: pop-bottom-left 1s forwards;
		}
	}
}

// * * * Animations * * * //
@keyframes sonar-wave {
	0% {
		opacity: 0.7;
	}
	100% {
		transform: scale(1.4);
		opacity: 0;
	}
}
@keyframes fade-in {
	0% {
		opacity: 0;
	}
	50% {
		opacity: 0;
	}
	100% {
		opacity: 1;
	}
}
// * * * Pop Animations * * * //
@keyframes pop-top {
	0% {
		transform: translate(0, 0) rotate(0);
		opacity: 0.4;
	}
	100% {
		transform: translate(0, -100px) rotate(0);
		opacity: 0;
	}
}
@keyframes pop-top-left {
	0% {
		transform: translate(0, 0) rotate(-55deg);
		opacity: 0.4;
	}
	100% {
		transform: translate(-100px, -50px) rotate(-55deg);
		opacity: 0;
	}
}
@keyframes pop-top-right {
	0% {
		transform: translate(0, 0) rotate(55deg);
		opacity: 0.4;
	}
	100% {
		transform: translate(100px, -50px) rotate(55deg);
		opacity: 0;
	}
}
@keyframes pop-bottom-right {
	0% {
		transform: translate(0, 0) rotate(135deg);
		opacity: 0.4;
	}
	100% {
		transform: translate(70px, 80px) rotate(135deg);
		opacity: 0;
	}
}
@keyframes pop-bottom-left {
	0% {
		transform: translate(0, 0) rotate(-135deg);
		opacity: 0.4;
	}
	100% {
		transform: translate(-70px, 80px) rotate(-135deg);
		opacity: 0;
	}
}
@keyframes first-bump-in {
	0% {
		transform: translateY(-65px);
		opacity: 1;
	}
	50% {
		transform: translateY(-80px);
		opacity: 1;
	}
	100% {
		transform: translateY(-100px);
		opacity: 0;
	}
}

@keyframes bump-in {
	0% {
		transform: translateY(-80px) scale(0.9);
		opacity: 1;
	}
	50% {
		transform: translateY(-80px) scale(1);
		opacity: 1;
	}
	100% {
		transform: translateY(-100px) scale(1);
		opacity: 0;
	}
}
@keyframes scaleAndBack {
	0% {
		transform: scale(1);
	}
	50% {
		transform: scale(1.15);
	}
	100% {
		transform: scale(1);
	}
}
</style>