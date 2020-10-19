import Vue from "vue";
import VueRouter from "vue-router";
import { isDev, checkUserSession } from "@/framework/Context";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Index",
    component: () =>
      import(/* webpackChunkName: "index" */ "../views/index/Index.vue")
  },
  {
    path: "/login",
    name: "Login",
    component: () =>
      import(/* webpackChunkName: "login" */ "../views/auth/Login.vue")
  },
  {
    path: "/tenant",
    name: "Tenant",
    component: () =>
      import(/* webpackChunkName: "tenant" */ "../views/tenant/Index.vue")
  },
  {
    path: "*",
    name: "404",
    component: () =>
      import(/* webpackChunkName: "error" */ "../views/error/404.vue")
  }
];

const router = new VueRouter({
  mode: "history",
  base: isDev() ? process.env.BASE_URL : "/admin",
  routes
});

// 路由变更，验证用户权限
router.beforeEach((to, from, next) => {
  if (to.name == "Login") {
    next();
  } else {
    checkUserSession()
      .then(() => {
        next();
      })
      .catch(() => {
        next("/login");
      });
  }
});

export default router;
