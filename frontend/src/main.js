import Vue from "vue";
import Vuetify from "vuetify/lib";
import "vuetify/dist/vuetify.min.css";
import VueSimplemde from "vue-simplemde";
import "simplemde/dist/simplemde.min.css";
import "font-awesome/css/font-awesome.min.css";
import Gravatar from "vue-gravatar";
import ImgInputer from "vue-img-inputer";
import "vue-img-inputer/dist/index.css";
import VueSession from "vue-session";

import App from "./App.vue";
import router from "./router";
import store from "./store";
import "./registerServiceWorker";
import { i18n } from "./i18n";
import "./vee-validate";
import VueMarkdown from "vue-markdown";
import VoerroTagsInput from "@voerro/vue-tagsinput";

Vue.config.productionTip = false;

Vue.use(VueSimplemde);
Vue.use(Vuetify);
Vue.use(VueSession);

Vue.component("v-gravatar", Gravatar);
Vue.component("ImgInputer", ImgInputer);
Vue.component("VueMarkdown", VueMarkdown);
Vue.component("tags-input", VoerroTagsInput);

new Vue({
	router,
	store,
	i18n,
	vuetify: new Vuetify({
		iconfont: "fa",
		theme: {
			primary: "#3f51b5",
			secondary: "#b0bec5",
			accent: "#8c9eff",
			error: "#b71c1c"
		},
		lang: {
			t: (key, ...params) => i18n.t(key, params)
		}
	}),
	render: h => h(App)
}).$mount("#app");
