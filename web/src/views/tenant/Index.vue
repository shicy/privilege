<!-- 租户管理 -->

<template>
  <div class="m-tenant">
    <MainFrame fix>
      <ListView
        :action="apiName"
        :columns="columns"
        :searchs="searchs"
        :buttons="buttons"
        @btn-create="onCreateBtnHandler"
        @oper-detail="onDetailOperHandler"
        @oper-delete="onDeleteOperHandler"
      />
    </MainFrame>
  </div>
</template>

<script>
import { api } from "@/framework/Context";
import MainFrame from "@/framework/main/MainFrame.vue";
import ListView from "@/components/MyListView.vue";

const tableColumns = [
  { key: "name", title: "租户名称" },
  { key: "mobile", title: "手机号码" },
  { key: "email", title: "邮箱" },
  { key: "code", title: "编码" },
  { key: "ops", title: "操作" }
];

const searchItems = [
  { name: "keyword", placeholder: "输入名称、手机号码或邮箱查询", width: 320 }
];

const topButtons = [
  {
    name: "create",
    label: "新建",
    type: "primary",
    icon: require("../../assets/001b.png")
  }
];

export default {
  components: { MainFrame, ListView },

  data() {
    tableColumns[tableColumns.length - 1].opers = this.getOpers;
    return {
      columns: tableColumns,
      searchs: searchItems,
      buttons: topButtons
    };
  },

  computed: {
    apiName() {
      return api("/account/list");
    }
  },

  methods: {
    getOpers() {
      let buttons = [];
      buttons.push({ name: "detail", label: "详情" });
      buttons.push({ name: "delete", label: "删除" });
      return buttons;
    },

    onCreateBtnHandler() {
      console.log("create");
    },

    onDetailOperHandler(data) {
      console.log("detail:", data);
    },

    onDeleteOperHandler(data) {
      console.log("delete:", data);
    }
  }
};
</script>

<style lang="scss">
.m-tenant {
  height: 100%;
}
</style>
