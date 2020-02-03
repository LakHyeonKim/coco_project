import Vue from "vue";
import Vuex from "vuex";
import jwtDecode from "jwt-decode";

Vue.use(Vuex);

export default new Vuex.Store({
	state: {
		token: null,
		user: "",
		loading: false
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
			return state.token ? jwtDecode(state.token).user_id : null;
		}
	},
	mutations: {
		setToken: function(state, token) {
			state.token = token;
		},
		setLoading: function(state, status) {
			state.loading = status;
		}
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
		}
	}
});
