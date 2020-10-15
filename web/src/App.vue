<template>
  <div id="app">
    <router-view />
    <div v-if="!beInit" class="loading">正在努力加载中...</div>
  </div>
</template>

<script>
import { has, PageLoading, beforeRequest } from "@scyui/vue-base";
import { getAccessToken } from "@/framework/Context";

const accessTokenIgnores = [
  "/account/login",
  "/account/logout",
  "/account/valid",
  "/account/access/token",
  "/valid/code"
];

export default {
  data() {
    return {
      beInit: false
    };
  },

  mounted() {
    PageLoading.start();

    this.$router.beforeEach((to, from, next) => {
      PageLoading.start();
      next();
    });

    this.$router.afterEach(() => {
      PageLoading.finish();
      this.beInit = true;
    });

    beforeRequest(options => {
      // console.log("===>", options.url);
      let beIgnore = has(accessTokenIgnores, tmp => {
        return options.url.indexOf(tmp) >= 0;
      });
      if (!beIgnore) {
        return getAccessToken().then(token => {
          if (token) {
            options.headers = { "X-Access-Token": token };
          }
        });
      }
    });
  }
};
</script>

<style lang="scss">
#app .loading {
  padding-top: 40px;
  color: #999;
  font-size: 12px;
  text-align: center;
}
</style>
