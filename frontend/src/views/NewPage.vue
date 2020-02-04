<template>
	<v-container class="col-12 col-sm-10 col-md-8">
		<v-row>
			<v-col>
				<v-text-field
					v-model="board.post.postTitle"
					label="제목"
					append-icon="mdi-page-layout-header"
					counter
					filled
					outlined
				></v-text-field>
			</v-col>
		</v-row>
		<v-card>
			<v-tabs background-color="white" color="deep-purple accent-4" right>
				<v-tab>글쓰기</v-tab>
				<v-tab>미리보기</v-tab>

				<v-tab-item>
					<v-container fluid>
						<v-textarea
							v-model="source"
							label="Input"
							auto-grow
							outlined
						/>
					</v-container>
				</v-tab-item>
				<v-tab-item>
					<v-container fluid>
						<div class="result">
							<vue-markdown
								class="line-numbers match-braces rainbow-braces show-invisibles"
								:source="source"
								data-download-link
							></vue-markdown>
						</div>
					</v-container>
				</v-tab-item>
			</v-tabs>
		</v-card>
		<v-row>
			<v-col>
				<tags-input
					v-model="tags"
					element-id="tags"
					placeholder="해시태그"
					add-tags-on-space
					add-tags-on-blur
				></tags-input>
				<v-btn @click="test">test</v-btn>
				<v-btn @click="testt">testt</v-btn>
			</v-col>
		</v-row>
	</v-container>
</template>

<script>
import axios from "axios";
import router from "../router";
import Prism from "../prism";

export default {
	name: "NewPage",
	components: {},
	data() {
		return {
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
	watch: {
		source: function() {
			Prism.highlightAll();
		}
	},
	methods: {
		test() {
			for (let i = 0; i < this.tags.length; ++i) {
				this.board.tags.push({
					tagName: this.tags[i].value
				});
			}
			console.log(this.board);

			axios
				.post(
					"http://192.168.100.95:8888/api/makeTagsFromPost/",
					this.board
				)
				.then(res => {
					console.log(res);
					router.push("/");
				});
		},
		testt() {
			Prism.highlightAll();
		}
	},
	mounted() {
		Prism.plugins.autoloader.use_minified = false;
	}
};
</script>

<style>
@import "~@voerro/vue-tagsinput/dist/style.css";
@import "../prism.css";

.result {
	border: 1.5px solid #ccc;
	border-radius: 5px;
}

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
