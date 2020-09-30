<!-- 主页面框架 -->

<template>
  <div id="main-body" :fixed="fix">
    <div class="head">
      <MainHeader />
    </div>
    <div class="body">
      <div class="menu">
        <MainMenu />
      </div>
      <div class="content">
        <div class="main-container">
          <slot></slot>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import MainHeader from "./MainHeader.vue";
import MainMenu from "./MainMenu.vue";

export default {
  props: ["fix"],

  components: {
    MainHeader,
    MainMenu
  }
};
</script>

<style lang="scss">
#main-body {
  height: 100%;
  padding-top: 40px;

  > .head {
    position: fixed;
    height: 40px;
    left: 0px;
    right: 0px;
    top: 0px;
    z-index: 10;
  }

  > .body {
    height: 100%;
    padding-left: 120px;

    > .menu {
      position: absolute;
      width: 120px;
      left: 0px;
      top: 0px;
      bottom: 0px;
      overflow: auto;
      background-color: #f0f0f0;
    }

    > .content {
      width: 100%;
      height: 100%;
      overflow: auto;
      padding: 20px 30px;
    }
  }
}
#main-body[fixed] {
  .main-container {
    height: 100%;
  }
}
</style>
