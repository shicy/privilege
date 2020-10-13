<!-- 组件 @scyui/vue-base/ListView 的扩展 -->

<template>
  <ListView
    :columns="columns"
    :searchs="searchs"
    :buttons="buttons"
    :head-height="40"
  />
</template>

<script>
import { ListView } from "@scyui/vue-base";

export default {
  components: { ListView },
  props: {
    columns: Array,
    searchs: Array,
    buttons: Array
  }
};
</script>

<style lang="scss">
.sui-vue-listview {
  .sui-vue-searchform {
    .search-btns {
      margin-left: 20px;

      .ivu-btn {
        min-width: 80px;
      }
    }
  }

  .sui-vue-mytable {
    .ivu-table th {
      height: 40px;
    }
  }
}
</style>
