<template>
	<div id="tagBox">
		<div id="blankBox"></div>
		<div id="favTag">
			<div v-for="(img, idx) in tag" :key="idx" id="tag">
				<button v-on:click.prevent="favtag(img.tag)">
					<img id="stackImg" :src="img.path" alt="" />
				</button>
			</div>
			<div v-if="showArray.length" style="display: inline-block">
				<div v-for="(img, idx) in showArray" :key="idx" id="tag">
					<button v-on:click.prevent="favtag(img[0].tag)">
						<img id="stackImg" :src="img[0].path" alt="" />
					</button>
				</div>
			</div>
			<!-- <div id="tag" v-for="i in 20" :key="i">
				<button>
					<img id="stackImg" src="../assets/stacks/C.png" alt="" />
				</button>
			</div> -->
		</div>
	</div>
</template>

<script>
import http from "../http-common";

export default {
	name: "SearchFavTag",
	data() {
		return {
			tags: {},
			tag: [],
			imgs: [
				{ tag: "java", path: "../img/stacks/java.png" },
				{ tag: "javascript", path: "../img/stacks/JS.png" },
				{ tag: "C", path: "../img/stacks/C.png" },
				{ tag: "c#", path: "../img/stacks/C#.png" },
				{ tag: "cpp", path: "../img/stacks/Cpp.png" },
				{ tag: "css", path: "../img/stacks/css.png" },
				{ tag: "git", path: "../img/stacks/git.png" },
				{ tag: "html", path: "../img/stacks/html.png" },
				{ tag: "mariadb", path: "../img/stacks/mariadb.png" },
				{ tag: "mysql", path: "../img/stacks/mysql.png" },
				{ tag: "php", path: "../img/stacks/php.png" },
				{ tag: "python", path: "../img/stacks/python.png" },
				{ tag: "react", path: "../img/stacks/react.png" },
				{ tag: "spring", path: "../img/stacks/spring.png" },
				{ tag: "swagger", path: "../img/stacks/swagger.png" },
				{ tag: "R", path: "../img/stacks/R.png" },
				{ tag: "kotlin", path: "../img/stacks/kotlin.png" },
				{ tag: "rubyonRails", path: "../img/stacks/rubyRail.png" },
				{ tag: "objective_C", path: "../img/stacks/objC.png" },
				{ tag: "ruby", path: "../img/stacks/ruby.png" },
				{ tag: "matlab", path: "../img/stacks/matlab.png" },
				{ tag: "go", path: "../img/stacks/go.png" }
			],
			cnt: 0,
			tmpMap: new Map(),
			showArray: []
		};
	},
	methods: {
		favtag(ta) {
			// console.log(ta);
			this.$emit("favtag", ta);
			// this.tagforsearch = "";
		}
	},
	mounted() {
		const token = this.$session.get("accessToken");
		const headers = {
			Authorization: token
		};
		console.log("favHeaders", headers);
		http.post("/api/findByAllDefaultSearch/", this.$session.get("id"), {
			headers
		})
			.then(res => {
				console.log("findByAlldefault for tag res ", res);
				// console.log(res.data);
				for (let i = 0; i < res.data.length; ++i) {
					// console.log(res.data[i].tags);
					// console.log(res.data[i].tags.length);
					for (let j = 0; j < res.data[i].tags.length; ++j) {
						// console.log(res.data[i].tags[j]);
						if (res.data[i].tags[j] in this.tags) {
							this.tags[`${res.data[i].tags[j]}`] += 1;
						} else {
							this.tags[`${res.data[i].tags[j]}`] = 1;
						}
					}
				}
				// console.log(this.tags);
				var dict = this.tags;
				var items = Object.keys(dict).map(function(key) {
					return [key, dict[key]];
				});
				// Sort the array based on the second element
				items.sort(function(first, second) {
					return second[1] - first[1];
				});
				// Create a new array with only the first 5 items
				// console.log(items);

				for (let i = 0; i < items.length; ++i) {
					// this.tag.push(items[i][0]);
					for (let j = 0; j < this.imgs.length; ++j) {
						if (this.imgs[j].tag == items[i][0]) {
							this.tag.push(this.imgs[j]);
							this.cnt += 1;
						}
					}
				}
				console.log(this.tag);
				// console.log("akjsdfhkajsdhkasjdfh", this.imgs)
				if (this.cnt <= 2) {
					for (let i = 0; i < 5; ++i) {
						this.tmpMap.set(
							this.imgs[
								Math.floor(Math.random() * this.imgs.length)
							]
						);
						// console.log(Math.floor(Math.random() * this.imgs.length) + 1)
					}
				}
				// console.log("asdfasdfasdfasdfasdf", [...this.tmpMap][0][0].tag)
				this.showArray = [...this.tmpMap];
			})
			.catch(err => {
				console.log("findByAlldefault for tag err ", err);
			});

		let diff = 0;
		let ticking = false;
		// const wheelEvent =
		// 	"onwheel" in document.createElement("div") ? "wheel" : "mousewheel";
		const list = document.querySelector("#favTag");
		function doSomething(diff) {
			list.scrollLeft += diff;
		}
		list.addEventListener(
			"wheel",
			function(e) {
				diff = e.deltaY;
				if (!ticking) {
					window.requestAnimationFrame(function() {
						doSomething(diff);
						ticking = false;
					});
				}
				ticking = true;
			},
			{ passive: true }
		);
	}
};
</script>

<style scoped>
#tagBox {
	width: 80vw;
}
#blankBox {
	height: 100px;
}
#favTag {
	border: 2px solid rgba(160, 23, 98, 0.3);
	height: 100%;
	width: 80vw;
	margin-bottom: 10px;
	/* overflow: auto; */
	white-space: nowrap;
	/* overflow-y: hidden; */
	overflow-x: scroll;
	overscroll-behavior: contain;
}
#favTag::-webkit-scrollbar {
	width: 11px;
	height: 8px;
}
#favTag::-webkit-scrollbar-track {
	background: none;
}
#favTag::-webkit-scrollbar-thumb {
	background: rgba(160, 23, 98, 0.3);
}
#favTag::-webkit-scrollbar-thumb:hover {
	background: rgba(160, 23, 98, 0.3);
}
#favTag::-webkit-scrollbar-thumb:active {
	background: rgba(160, 23, 98, 0.3);
}
#tag {
	display: inline-block;
	margin: 12px 6px;
}
#stackImg {
	width: 60px;
	height: 60px;
	background-color: white;
	border-radius: 50%;
}
@media screen and (max-width: 600px) {
	#tagBox {
		width: 100%;
		/* background-color: white; */
	}
	#blankBox {
		height: 80px;
	}
	#favTag {
		border: none;
		border-bottom: 0.75px solid rgba(0, 0, 0, 0.2);
		height: 100%;
		width: 100vw;
	}
	#tag {
		display: inline-block;
		/* font-size: 25px;
		border-radius: 15px;
		margin: 10px;
		padding-left: 5px;
		padding-right: 5px;
		color: white;
		background-color: rgba(160, 23, 98, 0.5); */
	}
}
</style>
