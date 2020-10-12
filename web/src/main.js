import Vue from "vue";
import iView from "view-design";

Vue.config.productionTip = false;

Vue.use(iView);

import App from "./App.vue";
import router from "./router";

import "view-design/dist/styles/iview.css";
import "./framework/css/base.scss";
import "./framework/css/iview.scss";

new Vue({
  router,
  render: h => h(App)
}).$mount("#app");
