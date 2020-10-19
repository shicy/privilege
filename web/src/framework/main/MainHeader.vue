<!-- 顶栏 -->

<template>
  <div class="main-header">
    <div class="logo">
      <router-link to="/">&nbsp;</router-link>
    </div>
    <div class="user">
      <span class="name">{{ userName }}</span>
      <span class="exit" @click="onExitBtnHandler">退出</span>
    </div>
  </div>
</template>

<script>
import { $post } from "@scyui/vue-base";
import { api, getUser, setUser } from "@/framework/Context";

export default {
  data() {
    let user = getUser() || {};
    return {
      userName: user.name
    };
  },

  methods: {
    onExitBtnHandler() {
      $post(api("/account/logout")).then(() => {
        setUser(null);
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

    .name {
      display: inline-block;
      margin-right: 30px;
      color: #fff;
    }

    .exit {
      display: inline-block;
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
