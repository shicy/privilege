<!-- 组件 @scyui/vue-base/ListView 的扩展 -->

<template>
  <ListView
    :action="action"
    :params="params"
    :columns="columns"
    :searchs="searchs"
    :buttons="buttons"
    :head-height="40"
    :row-height="32"
  />
</template>

<script>
import { ListView } from "@scyui/vue-base";

export default {
  components: { ListView },
  props: {
    // 查询接口
    action: String,
    // 默认查询参数，默认是get
    params: Object,
    // 列定义
    columns: Array,
    // 列表查询配置项
    searchs: Array,
    // 列表上面的按钮配置项
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
