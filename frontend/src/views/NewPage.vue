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
							@keydown="insertTab"
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
					<v-file-input
						name="attachments"
						v-model="board.attachments"
						chips
						label="첨부파일"
					></v-file-input>
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
	</v-container>
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
					router.push("/mypage/" + this.$session.get("id"));
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
</style>
