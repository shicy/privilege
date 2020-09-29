import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Home",
    component: Home
  },
  {
    path: "/login",
    name: "Login",
    component: () =>
      import(/* webpackChunkName: "login" */ "../views/auth/Login.vue")
  }
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes
});

// 路由变更，验证用户权限
router.beforeEach((to, from, next) => {
  if (to.name == "Login") {
    next();
  } else {
    setTimeout(() => {
      next("/login");
    }, 1000);
  }
});

export default router;
