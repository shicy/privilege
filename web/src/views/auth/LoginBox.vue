<!-- 登录视图 -->

<template>
  <div class="v-login-box">
    <div class="title">登录</div>
    <div class="login-form">
      <div class="form-item name">
        <input placeholder="用户名" />
      </div>
      <div class="form-item pwd">
        <input placeholder="密码" />
      </div>
      <div class="form-item code">
        <input placeholder="验证码" />
        <div class="code-img">
          <img alt="验证码" src="" />
        </div>
      </div>
    </div>
    <div class="login-btn" @click="onLoginBtnHandler">登录</div>
  </div>
</template>

<script>
import * as Context from "@/framework/Context";

export default {
  methods: {
    onLoginBtnHandler() {
      Context.setUser({ id: 1, name: "张飞" });
      this.$emit("logined");
    }
  }
};
</script>

<style lang="scss">
.v-login-box {
  width: 320px;
  padding: 20px 30px 30px;
  border-radius: 10px;
  background-color: #fff;

  .title {
    margin-bottom: 20px;
    font-size: 20px;
    text-align: center;
    line-height: 30px;
  }

  .form-item {
    margin-bottom: 20px;

    input {
      width: 100%;
      height: 40px;
      padding: 0px 10px 0px 43px;
      color: #fff;
      border: 0px;
      border-radius: 3px;
      background-color: #f39048;
    }

    input:-ms-input-placeholder {
      color: rgba(255, 255, 255, 0.65);
    }

    input::-webkit-input-placeholder {
      color: rgba(255, 255, 255, 0.65);
    }
  }

  .form-item:before {
    content: "";
    position: absolute;
    width: 16px;
    height: 16px;
    left: 12px;
    top: 50%;
    margin-top: -8px;
    z-index: 1;
    background-size: 16px;
    background-position: center;
    background-repeat: no-repeat;
  }

  .form-item.name {
    &:before {
      background-image: url(../../assets/f01.png);
    }
  }

  .form-item.pwd {
    &:before {
      background-image: url(../../assets/f02.png);
    }
  }

  .form-item.code {
    &:before {
      background-image: url(../../assets/f03.png);
    }

    input {
      width: 150px;
    }

    .code-img {
      position: absolute;
      width: 100px;
      height: 100%;
      top: 0px;
      right: 0px;
      padding: 1px;
      background-color: #ddd;

      img {
        display: block;
        width: 100%;
        height: 100%;
        cursor: pointer;
      }
    }
  }

  .login-btn {
    height: 44px;
    color: #fff;
    text-align: center;
    line-height: 44px;
    border-radius: 3px;
    background-color: #f39048;
    cursor: pointer;
  }
}
</style>
