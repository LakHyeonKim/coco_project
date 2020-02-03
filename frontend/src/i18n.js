import Vue from "vue";
import VueI18n from "vue-i18n";
import ko from "vee-validate/dist/locale/ko.json";

Vue.use(VueI18n);

const i18n = new VueI18n({
	locale: "ko",
	messages: {
		ko: {
			validation: ko.messages
		}
	},
	silentTranslationWarn: true
});

export {
	i18n
};