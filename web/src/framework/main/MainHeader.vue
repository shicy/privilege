<!-- 顶栏 -->

<template>
  <div class="main-header">
    <div class="logo">
      <router-link to="/">&nbsp;</router-link>
    </div>
    <div class="user">
      <div class="exit" @click="onExitBtnHandler">退出</div>
    </div>
  </div>
</template>

<script>
import { doLogout } from "@/framework/Context";

export default {
  methods: {
    onExitBtnHandler() {
      doLogout().then(() => {
        this.$router.push("/login");
      });
    }
  }
};
</script>

<style lang="scss">
.main-header {
  height: 40px;
  background-color: #f39048;

  .logo {
    width: 200px;
    height: 40px;
    background-image: url(../../assets/logo3.png);
    background-size: 100%;
    background-repeat: no-repeat;

    a {
      display: block;
      width: 100%;
      height: 100%;
      text-decoration: none;
    }
  }

  .user {
    position: absolute;
    height: 40px;
    top: 0px;
    right: 0px;

    .exit {
      padding: 0px 12px 0px 30px;
      color: #fff;
      line-height: 40px;
      background-image: url(../../assets/f04.png);
      background-size: 16px;
      background-position: 10px center;
      background-repeat: no-repeat;
      cursor: pointer;

      &:hover {
        background-color: #e28a49;
      }
    }
  }
}
</style>
