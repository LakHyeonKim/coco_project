<template>
	<v-container class="col-12 col-sm-10 col-md-8">
		<v-row>
			<v-col>
				<v-text-field
					name="postTitle"
					v-model="board.postTitle"
					label="제목"
					append-icon="mdi-page-layout-header"
					counter
					outlined
				></v-text-field>
			</v-col>
		</v-row>
		<v-card>
			<v-tabs background-color="white" color="deep-purple accent-4" right>
				<v-tab>글쓰기</v-tab>
				<v-tab @click="highlighting">미리보기</v-tab>
				<v-tab-item>
					<v-container fluid>
						<v-textarea
							name="post.code"
							id="input"
							v-model="board.code"
							label="Input"
							@keydown.tab="insertTab"
							@keydown="questionCount"
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
								:source="board.code"
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
			</v-col>
		</v-row>
		<form name="board" id="board" enctype="multipart/form-data">
			<v-row>
				<v-col>
					<v-file-input name="attachments" v-model="board.attachments" chips label="첨부파일"></v-file-input>
				</v-col>
			</v-row>
			<v-row>
				<v-col>
					<v-btn @click="posting">글 작성</v-btn>
				</v-col>
			</v-row>
			<input type="hidden" name="postTitle" v-model="board.postTitle" />
			<input type="hidden" name="postWriter" v-model="board.postWriter" />
			<input type="hidden" name="memberId" v-model="board.memberId" />
			<input type="hidden" name="code" v-model="board.code" />
			<!-- <input type="hidden" name="tags" v-model="board.tags" /> -->
		</form>
		<v-row justify="center">
    		<v-dialog v-model="dialog" scrollable overflowed>
				<template v-slot:activator="{ on }">
					<v-btn color="primary" dark v-on="on">Open Dialog</v-btn>
				</template>
				<v-card>
					<v-card-title>
						<span class="headline">Use Google's location service?</span>
					</v-card-title>
					<v-card-text>Lorem ipsum dolor sit amet, semper quis, sapien id natoque elit.</v-card-text>
					<agile ref="carousel" fade :dots="true">
						<div v-for="dict in dictArray" :key="dict.idwordDictionary">
							<p>{{ dict.word }}</p>
							<p>{{ dict.description }}</p>
							<p>{{ dict.link}}</p>
							<p>{{ dict.thumbnailSrc }}</p>
						</div>
						<div class="test1"></div>
						<div class="test2"></div>
						<div class="test3"></div>
						<img class="test4" src="1.jpg" alt="">
						<v-icon slot="prevButton">mdi-chevron-left</v-icon>
						<template slot="prevButton">prev</template>
						<template slot="nextButton">next</template>
						<v-icon slot="nextButton">mdi-chevron-right</v-icon>
					</agile>
					<v-card-actions>
						<v-btn color="green darken-1" text @click="dialog = false">Disagree</v-btn>
					</v-card-actions>
				</v-card>
			</v-dialog>
  		</v-row>
	</v-container>	
</template>

<script>
import router from "../router";
import Prism from "../prism";
import http from "../http-common";

export default {
	name: "NewPage",
	components: {
	},
	data() {
		return {
			dialog: false,
			question: 0,
			dictWord: "",
			dictArray: [],

			tags: [],
			board: {
				code: "",
				memberId: 0,
				postTitle: "",
				postWriter: "",
				tags: [],
				attachments: null
			}
		};
	},
	methods: {
		insertTab: function(event) {
			var kC = event.keyCode
				? event.keyCode
				: event.charCode
				? event.charCode
				: event.which;
			if (kC == 9 && !event.shiftKey && !event.ctrlKey && !event.altKey) {
				var oS = event.target.scrollTop;
				if (event.target.setSelectionRange) {
					var sS = event.target.selectionStart;
					var sE = event.target.selectionEnd;
					event.target.value =
						event.target.value.substring(0, sS) +
						"\t" +
						event.target.value.substr(sE);
					event.target.setSelectionRange(sS + 1, sS + 1);
					event.target.focus();
				} else if (event.target.createTextRange) {
					document.selection.createRange().text = "\t";
					event.returnValue = false;
				}
				event.target.scrollTop = oS;
				if (event.preventDefault) {
					event.preventDefault();
				}
				return false;
			}
			return true;
		},
		findWordDict() {
			http.post(
				"/api/findWordDictionary/",
				{ word: this.dictWord },
				{ headers: { Authorization: this.$session.get("accessToken") } }
			).then(res => {
				console.log(res);
				this.dictArray = res.data;
				this.question = 0;
				this.dictWord = "";
				this.dialog = true;
			});
		},
		questionCount: function(event) {
			console.log(event);
			if (event.key == "?") {
				this.question++;
			} else if (this.question == 2) {
				if (
					(event.keyCode >= 48 && event.keyCode <= 57) ||
					(event.keyCode >= 65 && event.keyCode <= 90) ||
					(event.keyCode >= 96 && event.keyCode <= 111)
				) {
					this.dictWord += event.key;
					console.log(this.dictWord);
				} else if (event.code == "Backspace") {
					this.dictWord = this.dictWord.slice(0, -1);
					console.log(this.dictWord);
				} else if (event.code == "Space") {
					this.findWordDict();
				} else {
					this.question = 0;
					this.dictWord = "";
				}
			} else if (event.key == "Shift" || event.key == "CapsLock") {
				return;
			} else {
				this.question = 0;
				this.dictWord = "";
			}
		},
		highlighting() {
			Prism.highlightAll();
		},
		posting() {
			this.board.tags = [];
			for (let i = 0; i < this.tags.length; ++i) {
				this.board.tags.push(this.tags[i].value);
			}

			let formData = new FormData(document.forms.namedItem("board"));
			formData.append("tags", this.board.tags);

			http.post("/trc/makePost/", formData)
				.then(res => {
					alert("글이 성공적으로 작성되었습니다.");
					this.$session.set("targetId", this.$session.get("id"));
					router.push("/mypage");
				})
				.catch(err => {
					alert("글 작성 중 문제가 생겼습니다.");
				});
		}
	},
	mounted() {
		Prism.plugins.autoloader.use_minified = false;
		this.board.memberId = this.$session.get("id");
		console.log("memberId newpage mounted ", this.board.memberId);
		// 닉네임 재확인 안할방법 찾아보기
		this.$store.state.token = this.$session.get("accessToken");
		this.board.postWriter = this.$store.getters.userNickname;
		console.log("nickname this ", this.board.postWriter);
		console.log("nickname vuex ", this.$store.getters.userNickname);
	},
	updated() {
		Prism.highlightAll();
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
.test1 {
	height: 100px;
	background-color: black;
}
.test2 {
	height: 100px;
	background-color: red;
}
.test3 {
	height: 100px;
	background-color: blue;
}
.agile__actions {
	margin-top: 20px;
}
.agile__actions ul{
	padding: 0;
}
.agile__dots {
	margin: 16px 0;
	padding: 0;
}
.agile__dot {
	margin: 0 10px;
}
.agile__dot button {
	background-color: #eee;
	border: none;
	border-radius: 50%;
	cursor: pointer;
	display: block;
	font-size: 0;
	line-height: 0;
	margin: 0;
	padding: 0;
	transition-duration: .3s;
	height: 10px;
	width: 10px;
}
.agile__dot--current {
	border-radius: 50%;
}
.agile__dot--current button {
	background-color: #888;
}
.agile__dot:hover button {
	background-color: #888;
}
</style>
