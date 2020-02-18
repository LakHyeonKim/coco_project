<template>
	<div>
		<div class="templatePage">
			<div class="tagInput" autocomplete="off">
				<div class="autocomplete">
					<tags-input
						v-model="tags"
						element-id="tags"
						placeholder="해시태그"
						add-tags-on-space
					></tags-input>
				</div>
			</div>
			<form name="board" enctype="multipart/form-data">
				<div class="submitInput">
					<button class="submitButton" @click.prevent="posting">WRITE</button>
				</div>
				<div class="title">
					<input type="text" placeholder="제목" class="titleInput" v-model="board.postTitle" />
				</div>
				<div class="codeInput">
					<v-tabs right color="rgba(0, 0, 0, 0.5)" hide-slider>
						<v-tab>글쓰기</v-tab>
						<v-tab @click="highlighting">미리보기</v-tab>
						<v-tab-item>
							<v-container>
								<v-textarea
									name="post.code"
									v-model="board.code"
									@keydown.tab="insertTab"
									@keydown="questionCount"
									auto-grow
									rounded
									placeholder="내용"
									class="code"
								/>
							</v-container>
						</v-tab-item>
						<v-tab-item>
							<v-container>
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
				</div>
				<div class="attachInput">
					<v-file-input name="attachments" v-model="board.attachments" label="첨부파일" color="rgb(0, 0, 0)"></v-file-input>
				</div>
				<div class="footerBox"></div>
				<input type="hidden" name="postTitle" v-model="board.postTitle" />
				<input type="hidden" name="postWriter" v-model="board.postWriter" />
				<input type="hidden" name="memberId" v-model="board.memberId" />
				<input type="hidden" name="code" v-model="board.code" />
				<!-- <input type="hidden" name="tags" v-model="board.tags" /> -->
			</form>
		</div>
		<v-row justify="center">
			<v-dialog v-model="dialog" scrollable overflowed @keydown.enter="insertDescription">
				<v-card>
					<v-card-actions class="d-flex justify-end">
						<v-icon @click="dialog = false">mdi-close-circle-outline</v-icon>
					</v-card-actions>
					<agile ref="carousel" fade :dots="true">
						<div v-for="dict in dictArray" :key="dict.idwordDictionary">
							<v-card-title>
								<span class="headline">
									<v-icon>mdi-file-document-box-search-outline</v-icon>
									{{ dict.word }}
								</span>
							</v-card-title>
							<v-card-text class="d-flex">
								<v-img :src="dict.thumbnailSrc" v-show="dict.thumbnailSrc" width="100"></v-img>
								<div class="ml-4 space-between">
									<h3>{{ dict.title }}</h3>
									<br />
									<p>
										{{ dict.description }}
										<a
											:href="dict.link"
											target="_blank"
											style="color: rgba(125, 72, 121, 0.85)"
										>자세히 보기</a>
									</p>
								</div>
							</v-card-text>
						</div>
						<v-icon slot="prevButton">mdi-chevron-left</v-icon>
						<template slot="prevButton">prev</template>
						<template slot="nextButton">next</template>
						<v-icon slot="nextButton">mdi-chevron-right</v-icon>
					</agile>
				</v-card>
			</v-dialog>
		</v-row>
	</div>
</template>

<script>
import router from "../router";
import Prism from "../prism";
import http from "../http-common";

export default {
	name: "NewPage",
	components: {},
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
			)
				.then(res => {
					console.log(res);
					this.dictArray = [];
					this.dictArray = res.data;
					this.question = 0;
					this.dictWord = "";
					this.dialog = true;
				})
				.catch(err => {
					console.log(err);
				});
		},
		questionCount: function(event) {
			if (event.key == "?") {
				this.question++;
			} else if (this.question == 2) {
				if (
					(event.keyCode >= 48 && event.keyCode <= 57) ||
					(event.keyCode >= 65 && event.keyCode <= 90) ||
					(event.keyCode >= 96 && event.keyCode <= 111)
				) {
					this.dictWord += event.key;
				} else if (event.code == "Backspace") {
					this.dictWord = this.dictWord.slice(0, -1);
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
		insertDescription() {
			let index = this.$refs.carousel.getCurrentSlide();
			this.board.code += this.dictArray[index].description;
			this.dialog = false;
		},
		highlighting() {
			Prism.highlightAll();
		},
		posting() {
			if (this.board.postTitle && this.board.code) {
				this.board.tags = [];
				for (let i = 0; i < this.tags.length; ++i) {
					this.board.tags.push(this.tags[i].value);
				}

				let formData = new FormData(document.forms.namedItem("board"));
				formData.append("tags", this.board.tags);
				http.post("/trc/makePost/", formData, {
					headers: { Authorization: this.$session.get("accessToken") }
				})
					.then(res => {
						console.log("makePost res ", res);
						alert("글이 성공적으로 작성되었습니다.");
						// this.$session.set("targetId", this.$session.get("id"));
						router.push({
							name: "mypage",
							params: { no: this.$session.get("id") }
						});
					})
					.catch(err => {
						console.log("makePost err ", err);
						alert("글 작성 중 문제가 생겼습니다.");
						console.log(err);
						router.push("/newpage");
					});
			} else {
				alert("글을 작성해 주세요");
			}
		}
	},
	mounted() {
		Prism.plugins.autoloader.use_minified = false;
		this.board.memberId = this.$session.get("id");
		console.log("memberId newpage mounted ", this.board.memberId);
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
@import url("https://fonts.googleapis.com/css?family=Noto+Sans+KR:100,300,400,500,700,900&display=swap");
* {
	font-family: "Noto Sans KR", Courier;
}
.tags-input-wrapper-default {
	background: white;
	line-height: 2;
	border: none;
	border: 0.5px solid rgba(128, 128, 128, 0.5);
	border-radius: 8px;
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
.templatePage {
	height: 100%;
	background-color: white;
}
.tagInput {
	float: left;
	width: 68%;
	margin: 25px auto 25px auto;
	padding: 0px 0px 0px 10vw;
}
.submitInput {
	/* display:grid;
	justify-content: end; */
	float: right;
	margin: 25px auto 25px auto;
	padding-right: 7vw;
}
.submitButton {
	width: 100px;
	height: 50px;
	font-size: 20px;
	color: white;
	background-color: rgba(160, 23, 98, 0.5);
	border-radius: 5px;
}
.title {
	width: 80%;
	margin: 0px auto 0px auto;
	padding: 150px 40px 20px 40px;
	border-bottom: 0.5px solid rgba(128, 128, 128, 0.5);
}
.titleInput {
	width: 100%;
	height: 60px;
	font-size: 30px;
}
.title > ::placeholder {
	color: gray;
	font-size: 30px;
}
.codeInput {
	width: 80%;
	margin: 0px auto 0px auto;
}
.attachInput {
	/* display:inline-block; */
	width: 82%;
	margin: 0px auto 0px auto;
	padding: 0px 40px 0px 40px;
}
.footerBox {
	background-color: white;
	height: 200px;
}
.agile__actions {
	margin-top: 20px;
}
.agile__actions ul {
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
	transition-duration: 0.3s;
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
@media screen and (max-width: 600px) {
	.templatePage {
		height: 100%;
		background-color: white;
	}
	.tagInput {
		float: left;
		width: 68%;
		margin: 25px auto 25px auto;
		padding: 0px 0px 0px 5vw;
	}
	.submitInput {
		/* display:grid;
		justify-content: end; */
		float: right;
		margin: 25px auto 25px auto;
		padding-right: 5vw;
	}
	.submitButton {
		width: 70px;
		height: 45px;
		font-size: 15px;
		color: white;
		background-color: rgba(160, 23, 98, 0.5);
		border-radius: 5px;
	}
	.title {
		width: 90%;
		margin: 0px 5vw 0px 5vw;
		padding: 150px 0px 10px 0px;
		border-bottom: 0.5px solid rgba(128, 128, 128, 0.5);
	}
	.titleInput {
		width: 100%;
		height: 50px;
		font-size: 20px;
	}
	.title > ::placeholder {
		color: gray;
		font-size: 20px;
	}
	#subBox
		> div
		> form
		> div.codeInput
		> div
		> div.v-window.v-item-group.theme--light.v-tabs-items
		> div
		> div.v-window-item.v-window-item--active
		> div {
		padding: 0px;
	}
	#subBox
		> div
		> form
		> div.codeInput
		> div
		> div.v-window.v-item-group.theme--light.v-tabs-items
		> div
		> div.v-window-item.v-window-item--active
		> div
		> div {
		padding-top: 0px;
	}
	#subBox
		> div
		> form
		> div.codeInput
		> div
		> div.v-window.v-item-group.theme--light.v-tabs-items
		> div
		> div.v-window-item.v-window-item--active
		> div
		> div
		> div
		> div.v-input__slot {
		width: 100%;
		padding: 0px;
	}
	.codeInput {
		width: 90%;
		margin: 0px 5vw 0px 5vw;
	}
	.attachInput {
		/* display:inline-block; */
		width: 90%;
		margin: 0px 5vw 0px 5vw;
		padding: 0px;
	}
	.footerBox {
		/* background-color: white; */
		height: 0px;
	}
}
</style>
