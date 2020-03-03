import Vue from "vue";
import Vuex from "vuex";
import jwtDecode from "jwt-decode";

Vue.use(Vuex);

export default new Vuex.Store({
	state: {
		token: null,
		idmember: 0,
		id: "",
		imgUrl: "",
		grade: "",
		nickname: "",
		isManager: 0,
		isDelete: 0,
		rankId: 0,
		loading: false,
		postDetail: [],
		idPost: 0,
		searchtag: false,
		tags: null,
		parent: null,
		isCheck: 0,
		targetImgUrl: "../img/icons/user.png",
		commentContent: {},
		commentDelIdx: 0,
		likeUpdate: 0,
		postData: null
	},
	getters: {
		decode: function(state) {
			return jwtDecode(state.token);
		},
		isLoggedIn: function(state) {
			return state.token ? true : false;
		},
		requestHeader: function(state) {
			return {
				headers: {
					Authorization: "JWT " + state.token
				}
			};
		}
	},
	mutations: {
		setToken: function(state, token) {
			state.token = token;
		},
		setLoading: function(state, status) {
			state.loading = status;
		},
		setPostDetail: function(state, data) {
			state.postDetail = [];
			state.postDetail = data;
		},
		setIdPost: function(state, data) {
			state.idPost = data;
		},
		setSearchTag: function(state, data) {
			state.searchtag = data;
		}
		// // 아래로 카카오 로그인 kakao API에 사용
		// setInit: function(state, status) {
		// 	state.init = status;
		// },
		// setMemberEmail: function(state, data) {
		// 	state.memberemail = data;
		// },
		// setTokens: function(state, data) {
		// 	state.accessToken = data.accessToken;
		// 	state.refreshToken = data.refreshToken;
		// this.$session.start();
		// this.$session.set("accessToken", state.accessToken);
		// this.$session.set("refreshToken", state.refreshToken);
		// this.$session.start();
		// this.$session.set("acessToken", acess);
		// this.$session.set("refreshToken", refresh);
		// }
	},
	actions: {
		login: function(context, token) {
			context.commit("setToken", token);
		},
		logout: function(context) {
			context.commit("setToken", null);
		},
		startLoading: function(context) {
			context.commit("setLoading", true);
		},
		endLoading: function(context) {
			context.commit("setLoading", false);
		},
		savePostDetail: function(context, data) {
			context.commit("setPostDetail", data);
		},
		saveIdPost: function(context, data) {
			context.commit("setIdPost", data);
		},
		saveSearchTag: function(context, data) {
			context.commit("setSearchTag", data);
		}
	}
});
