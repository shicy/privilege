<!-- 登录视图 -->

<template>
  <div class="v-login-box">
    <div class="title">登录</div>
    <div class="login-form">
      <div class="form-item name">
        <input v-model="username" placeholder="用户名" />
      </div>
      <div class="form-item pwd">
        <input v-model="password" placeholder="密码" />
      </div>
      <div class="form-item code">
        <input v-model="validcode" placeholder="验证码" />
        <div class="code-img">
          <img alt="验证码" :src="codeImage" @click="onCodeImageHandler" />
        </div>
      </div>
    </div>
    <Button
      class="login-btn"
      type="primary"
      :loading="loginFlag"
      @click="onLoginBtnHandler"
    >
      {{ loginBtnLabel }}
    </Button>
  </div>
</template>

<script>
import { $get, $post, trimToEmpty, Message } from "@scyui/vue-base";
import { api, setUser } from "@/framework/Context";

export default {
  data() {
    return {
      username: "",
      password: "",
      validcode: "",
      codeId: "",
      codeImage: "",
      loginFlag: false
    };
  },

  mounted() {
    this.loadCodeImage();
  },

  computed: {
    loginBtnLabel() {
      return this.loginFlag ? "正在登录..." : "登录";
    }
  },

  methods: {
    onLoginBtnHandler() {
      if (!this.loginFlag) {
        let data = this.getValidData();
        if (data) {
          // console.log("--->", data);
          this.loginFlag = true;
          $post(api("/account/login"), data, (err, user) => {
            console.log("--->", err, user);
            if (!err) {
              setUser(user);
              this.$emit("logined");
            } else {
              this.loginFlag = false;
            }
          });
        }
      }
    },

    onCodeImageHandler() {
      this.loadCodeImage();
    },

    getValidData() {
      let params = {};
      params.username = trimToEmpty(this.username);
      if (!params.username) {
        return Message.error("请输入用户名称");
      }
      params.password = trimToEmpty(this.password);
      if (!params.password) {
        return Message.error("请输入登录密码");
      }
      params.validCode = trimToEmpty(this.validcode);
      if (!params.validCode) {
        return Message.error("请输入验证码");
      }
      params.validCodeId = this.codeId;
      return params;
    },

    loadCodeImage() {
      $get(api("/valid/code")).then(({ data }) => {
        if (data) {
          this.codeId = data.codeId;
          this.codeImage = data.imageUrl;
        }
      });
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
    width: 100%;
    height: 44px;
    cursor: pointer;
  }
}
</style>
