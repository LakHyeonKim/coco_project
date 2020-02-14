<template>
	<div id="tagBox">
		<div id="blankBox"></div>
		<div id="favTag">
			<div v-for="ta in this.tag" :key="ta" id="tag">
				<button v-on:click.prevent="favtag(ta)">#{{ ta }}</button>
			</div>
		</div>
	</div>
</template>

<script>
// import axios from "axios";
import http from "../http-common";

export default {
	name: "SearchFavTag",
	data() {
		return {
			tags: {},
			tag: [],
			imgs: [
				{ tag: "java", path: "../assets/vue.png" },
				{ tag: "javascript", path: "../assets/JS.png" }
			]
		};
	},
	methods: {
		favtag(ta) {
			// console.log(ta);
			this.$emit("favtag", ta);
			// this.tagforsearch = "";
		},
		imgPath(tag) {
			if (tag == "java") {
				return "../assets/vue.png";
			} else if (tag == "javascript") {
				return "../assets/JS.png";
			}
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
				// console.log(items.slice(0, 5));

				for (let i = 0; i < 5; ++i) {
					// console.log(i);
					// console.log(items[i]);
					this.tag.push(items[i][0]);
					// this.tag.push({
					// 	tag: items[i][0],
					// 	path: this.imgPath(items[i][0])
					// });
				}
				// console.log(this.tag);
			})
			.catch(err => {
				console.log("findByAlldefault for tag err ", err);
			});
	}
};
</script>

<style scoped>
#tagBox {
	width: 80vw;
	/* background-color: white; */
}
#blankBox {
	height: 100px;
}
#favTag {
	border: 5px solid black;
	height: 100px;
}
#tag {
	display: inline-block;
	font-size: 25px;
	border-radius: 15px;
	margin: 10px;
	padding-left: 5px;
	padding-right: 5px;
	color: white;
	background-color: rgba(160, 23, 98, 0.5);
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
	}
	#tag {
		display: inline-block;
		font-size: 25px;
		border-radius: 15px;
		margin: 10px;
		padding-left: 5px;
		padding-right: 5px;
		color: white;
		background-color: rgba(160, 23, 98, 0.5);
	}
}
</style>
