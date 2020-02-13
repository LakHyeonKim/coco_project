<template>
	<div>
		<div class="backBox">
			<div class="tagInput">
				<tags-input
					v-model="tags"
					element-id="tags"
					placeholder="해시태그"
					add-tags-on-space
					add-tags-on-blur
				></tags-input>
			</div>
			<form name="board" enctype="multipart/form-data">
				<div class="submitInput">
					<button class="submitButton" @click="posting">
						글 작성
					</button>
				</div>
				<div class="title">
					<input
						type="text"
						placeholder="제목"
						class="titleInput"
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
									id="input"
									v-model="board.code"
									@keydown="insertTab"
									auto-grow
									rounded
									placeholder="내용"
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
					<v-file-input
						name="attachments"
						v-model="board.attachments"
						label="첨부파일"
						color="rgb(0, 0, 0)"
					></v-file-input>
				</div>
				<div class="footerBox"></div>
				<input type="hidden" name="postTitle" v-model="board.postTitle" />
				<input type="hidden" name="postWriter" v-model="board.postWriter" />
				<input type="hidden" name="memberId" v-model="board.memberId" />
				<input type="hidden" name="code" v-model="board.code" />
				<!-- <input type="hidden" name="tags" v-model="board.tags" /> -->
			</form>
		</div>
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
					router.push("/mypage/" + this.$session.get("id"));
				})
				.catch(err => {
					alert("글 작성 중 문제가 생겼습니다.");
					router.push("/newpage");
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
.backBox {
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
</style>
