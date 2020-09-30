<!-- 左边菜单栏 -->

<template>
  <div class="main-menu">
    <div class="menus">
      <div v-for="item in menus" :key="item.name" class="menu">
        <router-link :to="item.router">{{ item.label }}</router-link>
      </div>
    </div>
  </div>
</template>

<script>
import Menus from "@/framework/menus";

export default {
  data() {
    return {
      menus: []
    };
  },

  mounted() {
    this.menus = JSON.parse(JSON.stringify(Menus.menus));
  }
};
</script>

<style lang="scss">
.main-menu {
  padding-top: 20px;

  .menu a {
    display: block;
    padding: 3px 10px 3px 20px;
    color: #ababab;
    line-height: 24px;

    &:hover {
      background-color: #ddd;
    }

    &.router-link-active {
      color: #f39048;
      font-weight: bold;

      &:hover {
        background-color: transparent;
      }

      &:after {
        content: "";
        position: absolute;
        width: 2px;
        top: 3px;
        bottom: 3px;
        right: 0px;
        background-color: #f39048;
      }
    }
  }
}
</style>
