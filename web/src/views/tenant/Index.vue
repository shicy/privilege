<!-- 租户管理 -->

<template>
  <div class="m-tenant">
    <MainFrame fix>
      <ListView :columns="columns" :searchs="searchs" :buttons="buttons" />
    </MainFrame>
  </div>
</template>

<script>
import MainFrame from "@/framework/main/MainFrame.vue";
import ListView from "@/components/MyListView.vue";

const tableColumns = [
  { key: "name", title: "租户名称" },
  { key: "mobile", title: "手机号码" },
  { key: "email", title: "邮箱" },
  { key: "code", title: "编码" },
  { key: "secret", title: "密钥" },
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
    return {
      columns: tableColumns,
      searchs: searchItems,
      buttons: topButtons
    };
  }
};
</script>

<style lang="scss">
.m-tenant {
  height: 100%;
}
</style>
