<!-- 组件 @scyui/vue-base/ListView 的扩展 -->

<template>
  <ListView
    ref="listView"
    :action="action"
    :params="params"
    :columns="columns"
    :searchs="searchs"
    :buttons="buttons"
    :head-height="40"
    :show-stripe="true"
    page-style=""
    @load-before="onLoadBeforeHandler"
    @loaded="onLoadResultHandler"
    @btnclick="onButtonHandler"
    @oper="onOperHandler"
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
  },

  methods: {
    refresh() {
      this.$refs.listView.refresh();
    },

    reload() {
      this.$refs.listView.reload();
    },

    onLoadBeforeHandler(params) {
      this.$emit("load-before", params);
    },

    onLoadResultHandler(result) {
      this.$emit("loaded", result);
    },

    onButtonHandler(name) {
      this.$emit("btnclick", name);
      this.$emit(`btn-${name}`);
    },

    onOperHandler(name, data) {
      this.$emit("oper", name, data);
      this.$emit(`oper-${name}`, data);
    }
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
    .ivu-table {
      th {
        height: 40px;
      }

      td {
        border-bottom: 0px;
      }
    }

    .ivu-table-body {
      color: #686868;
    }
  }
}
</style>
