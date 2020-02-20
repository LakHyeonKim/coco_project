<template>
	<div>
		<div class="templatePage">
			<form name="board" enctype="multipart/form-data">
				<div id="tagInput_div">
					<span style="color: gray; font-size: 12px;"
						>마이페이지 태그 (10개 제한)</span
					>
					<VueTagsInput
						v-model="tag"
						:tags="tags"
						@tags-changed="newTags => (tags = newTags)"
						:add-on-key="[13, 32, 9, ':', ';']"
						add-on-blur
						allow-edit-tags
						:max-tags="10"
						placeholder
						id="tagInput"
						:disabled="disabled"
					/>
				</div>
				<div id="write_title">
					<v-text-field
						label="제목"
						color="gray"
						v-model="board.postTitle"
						:disabled="disabled"
					/>
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
									:disabled="disabled"
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
										:disabled="disabled"
									></vue-markdown>
								</div>
							</v-container>
						</v-tab-item>
					</v-tabs>
				</div>
				<div class="attachInput">
					<v-file-input
						name="attachments"
						v-model="board.attachments"
						label="첨부파일"
						color="rgb(0, 0, 0)"
						:disabled="disabled"
					></v-file-input>
				</div>

				<div id="create_top">
					<button
						id="write_btn"
						@click.prevent="posting"
						:disabled="disabled"
					>
						WRITE
					</button>
				</div>

				<div class="footerBox"></div>
				<input
					type="hidden"
					name="postTitle"
					v-model="board.postTitle"
				/>
				<input
					type="hidden"
					name="postWriter"
					v-model="board.postWriter"
				/>
				<input type="hidden" name="memberId" v-model="board.memberId" />
				<input type="hidden" name="code" v-model="board.code" />
				<!-- <input type="hidden" name="tags" v-model="board.tags" /> -->
			</form>
		</div>
		<v-row justify="center">
			<v-dialog
				v-model="dialog"
				scrollable
				overflowed
				@keydown.enter="insertDescription"
				:disabled="disabled"
			>
				<v-card>
					<v-card-actions class="d-flex justify-end">
						<v-icon @click="dialog = false"
							>mdi-close-circle-outline</v-icon
						>
					</v-card-actions>
					<agile ref="carousel" fade :dots="true">
						<div
							v-for="dict in dictArray"
							:key="dict.idwordDictionary"
						>
							<v-card-title>
								<span class="headline">
									<v-icon
										>mdi-file-document-box-search-outline</v-icon
									>
									{{ dict.word }}
								</span>
							</v-card-title>
							<v-card-text class="d-flex">
								<v-img
									:src="dict.thumbnailSrc"
									v-show="dict.thumbnailSrc"
									width="100"
								></v-img>
								<div class="ml-4 space-between">
									<h3>{{ dict.title }}</h3>
									<br />
									<p>
										{{ dict.description }}
										<a
											:href="dict.link"
											target="_blank"
											style="color: rgba(125, 72, 121, 0.85)"
											>자세히 보기</a
										>
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
import VueTagsInput from "@johmun/vue-tags-input";

export default {
	name: "NewPage",
	components: { VueTagsInput },
	data() {
		return {
			disabled: false,
			dialog: false,
			question: 0,
			dictWord: "",
			dictArray: [],
			tag: "",
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
				this.disabled = true;
				this.board.tags = [];

				for (let i = 0; i < this.tags.length; ++i) {
					this.board.tags.push(this.tags[i].text);
				}

				let formData = new FormData(document.forms.namedItem("board"));
				formData.append("tags", this.board.tags);
				let requestAddress = "/trc/makePost/";
				console.log("asdasda", this.$store.state.parent);
				if (this.$store.state.parent != null) {
					requestAddress = "/trc/makeBabyPost/";
					formData.append(
						"parentIdPost",
						this.$store.state.parent.parentIdPost
					);
					formData.append(
						"parentIdMember",
						this.$store.state.parent.parentIdMember
					);
				}
				this.$store.state.parent = null;

				http.post(requestAddress, formData, {
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
						// router.push("/newpage");
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
		this.board.postWriter = this.$session.get("nickName");
		console.log("nickname : ", this.$session.get("nickName"));
	},
	updated() {
		Prism.highlightAll();
	},
	destroyed() {
		this.$store.state.parent = null;
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
/* tag input */
#tagInput_div {
	width: 100%;
}

#tagInput > div {
	border-radius: 5px;
}

.vue-tags-input[data-v-61d92e31] {
	max-width: none;
	width: 100%;
}

.vue-tags-input .ti-tag {
	position: relative;
	background: rgba(160, 23, 98, 0.5);
	color: white;
}

.vue-tags-input .ti-new-tag-input.ti-invalid {
	color: silver;
}

.vue-tags-input .ti-tag.ti-valid.ti-deletion-mark {
	background: rgba(160, 23, 98, 1);
}

/* dic */
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

/* etc */
.templatePage {
	background-color: white;
	height: 100%;
	padding: 10px;
}
#create_top {
	display: inline-block;
	width: 100%;
	margin-bottom: 10px;
}

#write_btn {
	float: right;
	border: 1px solid rgba(160, 23, 98, 0.5);
	color: rgba(160, 23, 98, 0.5);
	width: 100%;
	margin-top: 30px;
	font-size: 25px;
	padding: 20px;
	transition: all 0.3s ease;
	outline: none;
}

#write_btn:hover {
	background-color: rgba(160, 23, 98, 0.5);
	color: white;
}

#write_btn:active {
	background-color: rgba(160, 23, 98, 0.7);
	border: 1px solid rgba(160, 23, 98, 0.7);
	color: white;
}

#subBox
	> div.templatePage
	> form
	> div.codeInput
	> div
	> div.v-window.v-item-group.theme--light.v-tabs-items
	> div
	> div.v-window-item.v-window-item--active
	> div
	> div
	> div
	> div.v-input__slot,
#subBox
	> div.templatePage
	> form
	> div.codeInput
	> div
	> div.v-window.v-item-group.theme--light.v-tabs-items
	> div
	> div.v-window-item.v-window-item--active
	> div {
	padding: 0;
}

.codeInput {
	margin-bottom: 20px;
}

.footerBox {
	height: 200px;
}
</style>
