import Vue from "vue";

import iView from "view-design";
import "view-design/dist/styles/iview.css";

import App from "./App.vue";
import router from "./router";

import "./framework/css/base.scss";
import "./framework/css/iview.scss";

Vue.config.productionTip = false;

Vue.use(iView);

new Vue({
  router,
  render: h => h(App)
}).$mount("#app");
