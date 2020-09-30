<!-- 列表页面 -->

<template>
  <div class="sui-vue-listview">
    <MyTable :columns="columns" />
  </div>
</template>

<script>
import { MyTable } from "@scyui/vue-base";

const tableColumns = [
  { key: "name", title: "租户名称" },
  { key: "mobile", title: "手机号码" },
  { key: "email", title: "邮箱" },
  { key: "code", title: "编码" },
  { key: "secret", title: "密钥" },
  { key: "ops", title: "操作" }
];

export default {
  components: { MyTable },

  data() {
    return {
      columns: tableColumns
    };
  }
};
</script>

<style lang="scss">
.sui-vue-listview {
  height: 100%;
}
</style>
