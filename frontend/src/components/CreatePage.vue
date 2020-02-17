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
					<button class="submitButton" @click="posting">
						WRITE
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
									v-model="board.code"
									@keydown="insertTab"
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
					<v-file-input
						name="attachments"
						v-model="board.attachments"
						label="첨부파일"
						color="rgb(0, 0, 0)"
					></v-file-input>
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
			},
			stack: [
				"Java", "Python", "C++", "C", "Go",
				"Spring", "php", "Vue.js", "Javascript", "C#",
				
			]
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
			const token = this.$session.get("accessToken");
			const headers = {
				Authorization: token
			};
			if (this.board.postTitle && this.board.code) {
				this.board.tags = [];
				for (let i = 0; i < this.tags.length; ++i) {
					this.board.tags.push(this.tags[i].value);
				}

				let formData = new FormData(document.forms.namedItem("board"));
				formData.append("tags", this.board.tags);
				console.log("formData ", formData)
				http.post("/trc/makePost/", formData, { headers })
					.then(res => {
						console.log("makePost res ", res);
						alert("글이 성공적으로 작성되었습니다.");
						// this.$session.set("targetId", this.$session.get("id"));
						router.push("/mypage/" + this.$session.get("id"));
					})
					.catch(err => {
						console.log("makePost err ", err);
						alert("글 작성 중 문제가 생겼습니다.");
						router.push("/newpage");
					});
			} else {
				alert("글을 작성해 주세요");
			}
		},
		autocomplete(inp, arr) {
			/*the autocomplete function takes two arguments,
			the text field element and an array of possible autocompleted values:*/
			var currentFocus;
			/*execute a function when someone writes in the text field:*/
			inp.addEventListener("input", function(e) {
				var a,
					b,
					i,
					val = this.value;
				/*close any already open lists of autocompleted values*/
				closeAllLists();
				if (!val) {
					return false;
				}
				currentFocus = -1;
				/*create a DIV element that will contain the items (values):*/
				a = document.createElement("DIV");
				a.setAttribute("id", this.id + "autocomplete-list");
				a.setAttribute("class", "autocomplete-items");
				/*append the DIV element as a child of the autocomplete container:*/
				this.parentNode.appendChild(a);
				/*for each item in the array...*/
				for (i = 0; i < arr.length; i++) {
					/*check if the item starts with the same letters as the text field value:*/
					if (
						arr[i].substr(0, val.length).toUpperCase() ==
						val.toUpperCase()
					) {
						/*create a DIV element for each matching element:*/
						b = document.createElement("DIV");
						/*make the matching letters bold:*/
						b.innerHTML =
							"<strong>" +
							arr[i].substr(0, val.length) +
							"</strong>";
						b.innerHTML += arr[i].substr(val.length);
						/*insert a input field that will hold the current array item's value:*/
						b.innerHTML +=
							"<input type='hidden' value='" + arr[i] + "'>";
						/*execute a function when someone clicks on the item value (DIV element):*/
						b.addEventListener("click", function(e) {
							/*insert the value for the autocomplete text field:*/
							inp.value = this.getElementsByTagName(
								"input"
							)[0].value;
							// console.log(this.getElementsByTagName("input")[0].value)
							// document.getElementsByTagName("input")[1].value.append({key:"", value:this.getElementsByTagName("input")[0].value})
							console.log(
								document.getElementsByTagName("input")[1].value
							);
							/*close the list of autocompleted values,
							(or any other open lists of autocompleted values:*/
							closeAllLists();
						});
						a.appendChild(b);
					}
				}
			});
			/*execute a function presses a key on the keyboard:*/
			inp.addEventListener("keydown", function(e) {
				var x = document.getElementById(this.id + "autocomplete-list");
				if (x) x = x.getElementsByTagName("div");
				if (e.keyCode == 40) {
					/*If the arrow DOWN key is pressed,
					increase the currentFocus variable:*/
					currentFocus++;
					/*and and make the current item more visible:*/
					addActive(x);
				} else if (e.keyCode == 38) {
					//up
					/*If the arrow UP key is pressed,
					decrease the currentFocus variable:*/
					currentFocus--;
					/*and and make the current item more visible:*/
					addActive(x);
				} else if (e.keyCode == 13) {
					/*If the ENTER key is pressed, prevent the form from being submitted,*/
					e.preventDefault();
					if (currentFocus > -1) {
						/*and simulate a click on the "active" item:*/
						if (x) x[currentFocus].click();
					}
				}
			});
			function addActive(x) {
				/*a function to classify an item as "active":*/
				if (!x) return false;
				/*start by removing the "active" class on all items:*/
				removeActive(x);
				if (currentFocus >= x.length) currentFocus = 0;
				if (currentFocus < 0) currentFocus = x.length - 1;
				/*add class "autocomplete-active":*/
				x[currentFocus].classList.add("autocomplete-active");
			}
			function removeActive(x) {
				/*a function to remove the "active" class from all autocomplete items:*/
				for (var i = 0; i < x.length; i++) {
					x[i].classList.remove("autocomplete-active");
				}
			}
			function closeAllLists(elmnt) {
				/*close all autocomplete lists in the document,
				except the one passed as an argument:*/
				var x = document.getElementsByClassName("autocomplete-items");
				for (var i = 0; i < x.length; i++) {
					if (elmnt != x[i] && elmnt != inp) {
						x[i].parentNode.removeChild(x[i]);
					}
				}
			}
			/*execute a function when someone clicks in the document:*/
			document.addEventListener("click", function(e) {
				closeAllLists(e.target);
			});
		}
	},
	mounted() {
		// Prism.plugins.autoloader.use_minified = false;
		// this.board.memberId = this.$session.get("id");
		// console.log("memberId newpage mounted ", this.board.memberId);
		// // 닉네임 재확인 안할방법 찾아보기
		// this.$store.state.token = this.$session.get("accessToken");
		// this.board.postWriter = this.$store.getters.userNickname;
		// console.log("nickname this ", this.board.postWriter);
		// console.log("nickname vuex ", this.$store.getters.userNickname);
		this.autocomplete(
			document.querySelector(
				"#subBox > div > div > div > div > div.tags-input-wrapper-default.tags-input > input[type=text]:nth-child(1)"
			),
			this.stack
		);
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

.autocomplete-items {
	position: absolute;
	border: 1px solid #d4d4d4;
	border-bottom: none;
	border-top: none;
	z-index: 99;
	/*position the autocomplete items to be the same width as the container:*/
	top: 100%;
	left: 0;
	right: 0;
}
.autocomplete-items div {
	padding: 10px;
	cursor: pointer;
	background-color: #fff;
	border-bottom: 1px solid #d4d4d4;
}
.autocomplete-items div:hover {
	/*when hovering an item:*/
	background-color: #e9e9e9;
}
.autocomplete-active {
	/*when navigating through the items using the arrow keys:*/
	background-color: DodgerBlue !important;
	color: #ffffff;
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
