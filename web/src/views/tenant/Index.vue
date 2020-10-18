<!-- 租户管理 -->

<template>
  <div class="m-tenant">
    <MainFrame fix>
      <ListView
        ref="ListView"
        :action="apiName"
        :columns="columns"
        :searchs="searchs"
        :buttons="buttons"
        @loaded="onLoadResultHandler"
        @btn-create="onCreateBtnHandler"
        @oper-detail="onDetailOperHandler"
        @oper-delete="onDeleteOperHandler"
      />
      <Drawer
        v-model="showEditor"
        class="tenant-edit-drawer"
        width="550"
        :mask-closable="false"
      >
        <Editor
          :model="currentModel"
          @submit="onSubmitHandler"
          @cancel="onCancelHandler"
        />
      </Drawer>
    </MainFrame>
  </div>
</template>

<script>
import { toDateString } from "@scyui/vue-base";
import { api } from "@/framework/Context";
import MainFrame from "@/framework/main/MainFrame.vue";
import ListView from "@/components/MyListView.vue";
import Editor from "./Editor.vue";

const tableColumns = [
  { key: "name", title: "租户名称" },
  { key: "mobile", title: "手机号码" },
  { key: "email", title: "邮箱" },
  { key: "code", title: "编码" },
  { key: "createDate", title: "创建日期" },
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
  components: { MainFrame, ListView, Editor },

  data() {
    tableColumns[tableColumns.length - 1].opers = this.getOpers;
    return {
      columns: tableColumns,
      searchs: searchItems,
      buttons: topButtons,

      showEditor: false,
      currentModel: null
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

    onLoadResultHandler(result) {
      if (result && result.datas) {
        result.datas.forEach(data => {
          data.createDate = toDateString(data.createTime, "yyyy-MM-dd");
        });
      }
    },

    onCreateBtnHandler() {
      this.currentModel = {};
      this.showEditor = true;
    },

    onDetailOperHandler(data) {
      this.currentModel = Object.assign({}, data);
      this.showEditor = true;
    },

    onDeleteOperHandler(data) {
      console.log("delete:", data);
    },

    onSubmitHandler() {
      this.showEditor = false;
      this.$refs.listView.reload();
    },

    onCancelHandler() {
      this.showEditor = false;
    }
  }
};
</script>

<style lang="scss">
.m-tenant {
  height: 100%;
}
</style>
