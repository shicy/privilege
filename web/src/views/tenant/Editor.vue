<!-- 编辑页面 -->

<template>
  <div class="tenant-editor">
    <div class="title">{{ editorTitle }}</div>
  </div>
</template>

<script>
export default {
  props: {
    model: Object
  },

  data() {
    return {};
  },

  computed: {
    editorTitle() {
      return this.model && this.model.id ? "修改租户" : "新建租户";
    }
  }
};
</script>

<style lang="scss">
.tenant-editor {
  .title {
    font-weight: bold;
    line-height: 24px;
  }
}
</style>
