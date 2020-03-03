import Vue from 'vue'
import App from './App.vue'
import $ from "jquery"
import Notifications from 'vue-notification'
window.jQuery = window.$ = $
require("bootstrap/dist/css/bootstrap.min.css")
require("bootstrap/dist/js/bootstrap.min.js")
import router from "./router";

Vue.config.productionTip = false
Vue.use(Notifications)

new Vue({
  router,
  render: h => h(App),
}).$mount('#app')
