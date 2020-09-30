<!-- 登录页面 -->

<template>
  <div class="m-login">
    <div class="login-container">
      <div class="app-title"></div>
      <div class="login-box">
        <LoginBox @logined="onLoginHandler" />
      </div>
    </div>
    <div class="copyright">{{ copyright }}</div>
  </div>
</template>

<script>
import LoginBox from "./LoginBox.vue";

export default {
  components: {
    LoginBox
  },

  computed: {
    copyright() {
      let year = new Date().getFullYear();
      return `©${year} 阳光夜雨梦 shicy85@163.com`;
    }
  },

  methods: {
    onLoginHandler() {
      this.$router.push({ path: "/" });
    }
  }
};
</script>

<style lang="scss">
.m-login {
  height: 100%;
  background-color: #f39048;

  .app-title {
    position: absolute;
    width: 100%;
    height: 50px;
    top: -80px;
    background-image: url(../../assets/app_title.png);
    background-size: 320px;
    background-repeat: no-repeat;
    background-position: center;
  }

  .login-container {
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
  }

  .copyright {
    position: absolute;
    left: 0px;
    right: 0px;
    bottom: 20px;
    color: rgba(255, 255, 255, 0.75);
    font-size: 12px;
    text-align: center;
  }
}
</style>
