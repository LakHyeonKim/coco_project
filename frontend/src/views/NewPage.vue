<template>
	<v-container>
		<v-row style="justify-content: center">
			<v-col class="col-12 col-sm-9 col-md-9">
				<v-text-field
					v-model="board.post.postTitle"
					label="제목"
					append-icon="mdi-page-layout-header"
					counter
					filled
					outlined
				></v-text-field>
				<v-textarea
					id="content"
					v-model="board.post.code"
					label="내용"
					hint="더블클릭하여 에디터에서 수정"
					rows="10"
					append-icon="mdi-page-layout-body"
					counter
					filled
					auto-grow
					outlined
					@dblclick="runStackEdit"
				></v-textarea>
				<tags-input
					v-model="tags"
					element-id="tags"
					placeholder="해시태그"
					add-tags-on-space
					add-tags-on-blur
				></tags-input>
				<v-btn @click="test">test</v-btn>
			</v-col>
		</v-row>
	</v-container>
</template>

<script>
import Stackedit from "stackedit-js";
import http from "../http-common";
import router from "../router";

export default {
	name: "NewPage",
	components: {},
	data() {
		return {
			el: "",
			stackedit: "",
			source: "",
			result: "",

			tags: [],
			board: {
				post: {
					access: 0,
					code: "",
					idpost: 0,
					imagePath: "string",
					likeCount: 0,
					memberId: 7,
					postTitle: "",
					postWriter: "tester",
					views: 0
				},
				tags: []
			}
		};
	},
	methods: {
		test() {
			for (let i = 0; i < this.tags.length; ++i) {
				this.board.tags.push({
					tagName: this.tags[i].value
				});
			}
			console.log(this.board);

			http.post("/makeTagsFromPost/", this.board).then(res => {
				console.log(res);
				router.push("/");
			});
		},
		runStackEdit() {
			// Open the iframe
			this.stackedit.openFile({
				name: "Filename", // with an optional filename
				content: {
					text: this.el.value // and the Markdown content.
					// text: this.board.post.code
				}
			});

			// Listen to StackEdit events and apply the changes to the textarea.
			this.stackedit.on("fileChange", file => {
				// this.el.value = file.content.text;
				this.board.post.code = file.content.text;
				// this.p.innerHTML = file.content.html;
			});
		}
	},
	mounted() {
		this.el = document.querySelector("#content");
		this.stackedit = new Stackedit();
	}
};
</script>

<style>
@import "~@voerro/vue-tagsinput/dist/style.css";
@import url("https://fonts.googleapis.com/css?family=Noto+Sans+KR:100,300,400,500,700,900&display=swap");
* {
	font-family: "Noto Sans KR", Courier;
}
.tags-input-wrapper-default {
	background: white;
	line-height: 2;
}
.tags-input-wrapper-default input::placeholder {
	color: gray;
}

.tags-input-badge {
	font-size: 100%;
}

.tags-input-remove::before,
.tags-input-remove::after {
	background: white;
}

.tags-input-badge-selected-default {
	color: white;
	font-weight: 400;
	background: rgba(160, 23, 98, 0.5);
}
</style>
