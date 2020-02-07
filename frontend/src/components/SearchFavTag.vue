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
			tag: []
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
		http.post("/api/findByAllDefaultSearch/", this.$session.get("id"))
			.then(res => {
				console.log("findByAlldefault for tag ", res);
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
				}
				// console.log(this.tag);
			})
			.catch(err => {
				console.log(err);
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
	margin-right: 10px;
}
</style>
