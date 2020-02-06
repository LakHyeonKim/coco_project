import Vue from "vue";
import Vuex from "vuex";
import jwtDecode from "jwt-decode";

Vue.use(Vuex);

export default new Vuex.Store({
	state: {
		token: null,
		user: "",
		loading: false,
		postDetail: [],
		idPost: 0,
		searchword: ""
		// init: true,
		// memberemail: "",
		// accessToken: "",
		// refreshToken: "",
	},
	getters: {
		isLoggedIn: function(state) {
			return state.token ? true : false;
		},
		requestHeader: function(state) {
			return {
				headers: {
					Authorization: "JWT " + state.token
				}
			};
		},
		userId: function(state) {
			return jwtDecode(state.token).idmember;
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
		setSearchWord: function(state, data) {
			state.searchword = data;
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
		saveSearchWord: function(context, data) {
			context.commit("setSearchWord", data);
		}
		// saveInit: function(context) {
		// 	context.commit("setInit", false);
		// },
		// saveMemberEmail: function(context, data) {
		// 	context.commit("setMemberEmail", data);
		// },
		// saveTokens: function(context, data) {
		// 	context.commit("setTokens", data);
		// },
	}
});
