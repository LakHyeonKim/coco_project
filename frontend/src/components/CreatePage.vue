<template>
	<div>
		<div class="templatePage">
			<div
				id="loading"
				:style="loadingTop ? loadingStyleOn : loadingStyleOff"
				v-if="loadingTop"
			>
				<div class="loader"></div>
			</div>
			<form v-else name="board" enctype="multipart/form-data">
				<div id="tagInput_div">
					<span style="color: gray; font-size: 12px;">
						마이페이지 태그 (10개 제한)
					</span>
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
						:separators="[' ', ',']"
					/>
				</div>
				<div id="write_title">
					<v-text-field
						label="제목"
						color="gray"
						v-model="board.postTitle"
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
									@keyup="questionCount"
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
					<v-text-field
						id="uploaded"
						v-model="uploaded"
						v-if="uploaded"
						prepend-icon="mdi-paperclip"
						readonly
						clearable
					></v-text-field>
					<v-file-input
						id="attachments"
						name="attachments"
						v-show="!uploaded"
						v-model="board.attachments"
						label="첨부파일"
						color="rgb(0, 0, 0)"
					></v-file-input>
				</div>

				<div id="create_top">
					<button id="write_btn" @click.prevent="posting">
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

		<div id="dic">
			<v-dialog
				v-model="dialog"
				scrollable
				overflowed
				@keydown.enter="insertDescription"
				width="500"
			>
				<v-card class="dic_container">
					<div id="dic_close_btn">
						<v-icon @click="closeDict">
							mdi-close-circle-outline
						</v-icon>
					</div>

					<div v-if="dictArray != null">
						<div id="dic_title">
							<v-icon>
								mdi-file-document-box-search-outline
							</v-icon>
							{{ dictArray[0].word }}
						</div>
						<agile
							ref="carousel"
							fade
							:dots="false"
							:arrows="false"
							v-if="dictArray != null"
						>
							<div
								v-for="dict in dictArray"
								:key="dict.idwordDictionary"
								id="dic_body"
							>
								<div id="dic_img_div">
									<img
										v-if="dict.thumbnailSrc != null"
										:src="dict.thumbnailSrc"
										v-show="dict.thumbnailSrc"
										id="dic_img"
									/>
								</div>
								<div
									id="dic_detail"
									:class="
										dict.thumbnailSrc != null
											? 'dic_detail_ximg'
											: 'dic_detail_img'
									"
								>
									<div id="dic_subTitle">
										{{ dict.title }}
									</div>
									<div id="dic_desc">
										{{ dict.description }}
										<br />
										<a
											:href="dict.link"
											target="_blank"
											style="font-size: 13px; float: right; color: rgba(125, 72, 121, 0.85)"
											>자세히 보기
										</a>
									</div>
								</div>
							</div>
							<v-icon slot="prevButton">mdi-chevron-left</v-icon>
							<template slot="prevButton">prev</template>
							<template slot="nextButton">next</template>
							<v-icon slot="nextButton">mdi-chevron-right</v-icon>
						</agile>
					</div>
				</v-card>
			</v-dialog>
		</div>
		<!-- <v-row justify="center">
			<v-dialog
				v-model="dialog"
				scrollable
				overflowed
				@keydown.enter="insertDescription"
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
		</v-row> -->
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
			dialog: false,
			question: 0,
			dictWord: "",
			dictArray: null,
			tag: "",
			tags: [],
			uploaded: "",
			board: {
				code: "",
				memberId: 0,
				postTitle: "",
				postWriter: "",
				tags: [],
				attachments: null
			},
			loadingTop: false
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
			http.post("/wdc/findWordDictionary/", this.dictWord, {
				headers: { Authorization: this.$session.get("accessToken") }
			})
				.then(res => {
					console.log("findWordDict");
					console.log(res);
					this.dictArray = res.data;
					this.question = 0;
					this.dictWord = "";
					this.dialog = true;
				})
				.catch(err => {
					console.log(err);
				});
		},
		closeDict() {
			this.dictArray = null;
			this.dialog = false;
		},
		questionCount: function(event) {
			//keyCode _ 모바일: 229, ? : 191, shift: 16, capslock: 20, backspace: 8, space: 32

			let kCode = event.keyCode;
			var str = String(event.target.value).split(" ");

			if (kCode == 229) {
				let temp = event.target.value
					.charAt(event.target.selectionStart - 1)
					.charCodeAt();

				if (temp == 63) kCode = 191;
				if (temp == 32) kCode = 32;
			}

			if (kCode == 191) {
				this.question++;
			} else if (this.question == 2) {
				if (kCode == 32) {
					this.question = 0;
					this.dictWord = str[str.length - 2];
					this.dictWord = String(this.dictWord).substring(
						2,
						this.dictWord.length
					);
					this.dictArray = null;
					this.findWordDict();
				}
			} else if (kCode == 32) {
				this.question = 0;
			}

			// if (kCode == 191) {
			// 	this.question++;
			// } else if (this.question == 2) {
			// 	if (
			// 		(kCode >= 48 && kCode <= 57) ||
			// 		(kCode >= 65 && kCode <= 90) ||
			// 		(kCode >= 96 && kCode <= 111)
			// 	) {
			// 		this.dictWord += event.key;
			// 	} else if (kCode == 8) {
			// 		this.dictWord = this.dictWord.slice(0, -1);
			// 	} else if (kCode == 32) {
			// 		this.findWordDict();
			// 	} else {
			// 		this.question = 0;
			// 		this.dictWord = "";
			// 	}
			// } else if (kCode == 16 || kCode == 20) {
			// 	return;
			// } else {
			// 	// ex) 112
			// 	this.question = 0;
			// 	this.dictWord = "";
			// }
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
				this.loadingTop = true;
				this.board.tags = [];

				for (let i = 0; i < this.tags.length; ++i) {
					this.board.tags.push(this.tags[i].text);
				}

				let formData = new FormData(document.forms.namedItem("board"));
				formData.append("tags", this.board.tags);
				let requestAddress = "/trc/makePost/";
				// console.log("Parent Data: ", this.$store.state.parent);
				// console.log("Edit Data: ", this.$store.state.postData);
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
				} else if (this.$store.state.postData) {
					requestAddress = "/trc/updatePost/";
					formData.append(
						"idpost",
						this.$store.state.postData.idPost
					);
					if (!this.uploaded && !this.board.attachments) {
						http.post(
							"/api/deleteFile",
							this.$store.state.postData.idPost,
							{
								headers: {
									Authorization: this.$session.get(
										"accessToken"
									)
								}
							}
						)
							.then(res => {
								console.log("File Delete Request: ", res);
							})
							.catch(err => {
								console.log("File Delete Error: ", err);
							});
					}
				}
				this.$store.state.parent = null;
				this.$store.state.postData = null;

				// console.log("Before axios: ", this.board);
				http.post(requestAddress, formData, {
					headers: { Authorization: this.$session.get("accessToken") }
				})
					.then(res => {
						// console.log("makePost res ", res);
						alert("글이 성공적으로 작성되었습니다.");
						this.loadingTop = false;
						// this.$session.set("targetId", this.$session.get("id"));
						router.push({
							name: "mypage",
							params: { no: this.$session.get("id") }
						});
					})
					.catch(err => {
						// console.log("makePost err ", err);
						alert("글 작성 중 문제가 생겼습니다.");
						this.loadingTop = false;
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
		// console.log("memberId newpage mounted ", this.board.memberId);
		this.$store.state.token = this.$session.get("accessToken");
		this.board.postWriter = this.$session.get("nickName");
		// console.log("nickname : ", this.$session.get("nickName"));

		if (this.$store.state.postData != null) {
			this.board.code = this.$store.state.postData.code;
			this.board.memberId = this.$store.state.postData.memberId;
			this.board.postTitle = this.$store.state.postData.postTitle;
			this.board.postWriter = this.$store.state.postData.postWriter;
			this.uploaded = this.$store.state.postData.attachments.substring(
				36
			);
			this.$store.state.postData.tags.forEach(element => {
				this.tags.push({
					text: element.tagName,
					tiClasses: ["ti-valid"],
					style:
						"color: rgba(160, 23, 98, 0.5); background-color: transparent; border: 1px solid rgba(160, 23, 98, 0.5)"
				});
			});
		}
	},
	updated() {
		Prism.highlightAll();
	},
	destroyed() {
		this.$store.state.parent = null;
		this.$store.state.postData = null;
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
,
#loading {
	display: none;
	width: 100%;
	margin: 20px auto 20px auto;
	/* display: grid; */
	justify-content: center;
}
.loader {
	/* margin: 20px auto 20px auto; */
	border: 6px solid #f3f3f3; /* Light grey */
	border-top: 6px solid #3498db; /* Blue */
	border-radius: 50%;
	width: 60px;
	height: 60px;
	animation: spin 2s linear infinite;
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

#app
	> div.v-dialog__content.v-dialog__content--active
	> div
	> div
	> div:nth-child(2)
	> div.agile.agile--fade
	> div.agile__list
	> div
	> div {
	width: 100%;
}

.dic_container {
	width: 500px;
	padding: 20px;
}

#dic_close_btn {
	width: 100%;
	text-align: right;
}

#dic_title {
	font-size: 20px;
	margin-left: 5px;
}

#dic_body {
	display: inline-block;
	padding: 5px;
}

#dic_img_div {
	float: left;
	width: 15%;
	margin-right: 10px;
}
#dic_img {
	width: 100%;
}

#dic_detail_img {
	float: right;
	width: 80%;
	padding: 10px;
}

#dic_detail_ximg {
	float: right;
	width: 80%;
	padding: 10px;
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
