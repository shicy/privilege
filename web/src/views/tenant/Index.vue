<!-- 租户管理 -->

<template>
  <div class="m-tenant">
    <MainFrame fix>
      <ListView />
    </MainFrame>
  </div>
</template>

<script>
import MainFrame from "@/framework/main/MainFrame.vue";
// import ListView from "@/temp/vue-base/components/list/ListView.vue";
import { ListView } from "@scyui/vue-base";

export default {
  components: { MainFrame, ListView }
};
</script>

<style lang="scss">
.m-tenant {
  height: 100%;
}
</style>
