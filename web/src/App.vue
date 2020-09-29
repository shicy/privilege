<template>
  <div id="app">
    <router-view />
    <div v-if="!beInit" class="loading">正在努力加载中...</div>
    <!-- <div id="nav">
      <router-link to="/">Home</router-link> |
      <router-link to="/about">About</router-link>
    </div>
    <router-view /> -->
  </div>
</template>

<script>
import PageLoading from "@/temp/vue-base/tool/PageLoading.vue";

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
