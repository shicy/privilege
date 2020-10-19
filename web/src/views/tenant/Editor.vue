<!-- 编辑页面 -->

<template>
  <div class="tenant-editor">
    <div class="title">{{ editorTitle }}</div>
    <div class="form">
      <Form
        ref="form"
        :model="formData"
        :rules="formRules"
        :label-width="90"
        label-position="left"
      >
        <FormItem label="租户名称" prop="name">
          <Row>
            <Col span="16">
              <Input v-model="formData.name" placeholder="请输入" />
            </Col>
            <Col v-if="isNameValid" span="4">
              <i class="ic-valid"></i>
            </Col>
          </Row>
        </FormItem>
        <FormItem label="手机号码" prop="mobile">
          <Row>
            <Col span="16">
              <Input v-model="formData.mobile" placeholder="请输入" />
            </Col>
            <Col v-if="isMobileValid" span="4">
              <i class="ic-valid"></i>
            </Col>
          </Row>
        </FormItem>
        <FormItem label="电子邮箱" prop="email">
          <Row>
            <Col span="16">
              <Input v-model="formData.email" placeholder="请输入" />
            </Col>
            <Col v-if="isEmailValid" span="4">
              <i class="ic-valid"></i>
            </Col>
          </Row>
        </FormItem>
        <FormItem label="备注信息" prop="remark">
          <Input
            v-model="formData.remark"
            type="textarea"
            placeholder="请输入"
            :autosize="{ minRows: 3, maxRows: 3 }"
          />
        </FormItem>
        <FormItem class="pwd" label="登录密码" prop="password">
          <Row>
            <Col span="16">
              <Input
                ref="passwordInput"
                v-model="formData.password"
                placeholder="请输入"
                :readonly="!editPassword"
              />
            </Col>
            <Col v-if="!editPassword" span="5">
              <a class="edtbtn" @click="onPasswordEditHandler">修改</a>
            </Col>
          </Row>
        </FormItem>
        <FormItem class="code" label="编码" prop="code">
          <Row>
            <Col span="16">
              <Input
                v-model="formData.code"
                placeholder="自动生成"
                :readonly="true"
              />
            </Col>
          </Row>
        </FormItem>
        <FormItem class="secret" label="密钥" prop="secret">
          <Row>
            <Col span="16">
              <Input
                v-model="formData.secret"
                placeholder="自动生成"
                :readonly="true"
              />
            </Col>
            <Col v-if="!isCreate" span="8">
              <a
                v-if="showSecret"
                class="edtbtn"
                @click="onSecretUpdateHandler"
              >
                重新生成
              </a>
              <a v-else class="edtbtn" @click="onSecretShowHandler">查看</a>
            </Col>
          </Row>
        </FormItem>
        <FormItem>
          <Button
            type="primary"
            :loading="loadingFlag"
            @click="onSubmitHandler"
          >
            确定
          </Button>
          <Button style="margin-left: 10px;" @click="onCancelBtnHandler">
            取消
          </Button>
        </FormItem>
      </Form>
    </div>
  </div>
</template>

<script>
import { $get, $post, trimToEmpty, Message } from "@scyui/vue-base";
import { api } from "@/framework/Context";

export default {
  props: {
    model: Object
  },

  data() {
    return {
      // 初始值使用空白符，否则第一个字符不触发change事件
      formData: {
        name: " ",
        mobile: " ",
        email: " ",
        remark: " ",
        password: " ",
        code: " ",
        secret: " "
      },
      formRules: {
        name: [{ asyncValidator: this.checkNameInput, trigger: "change" }],
        mobile: [{ asyncValidator: this.checkMobileInput, trigger: "change" }],
        email: [{ asyncValidator: this.checkEmailInput, trigger: "change" }]
      },
      editPassword: false,
      isNameValid: false,
      isMobileValid: false,
      isEmailValid: false,
      showSecret: false,
      loadingFlag: false
    };
  },

  watch: {
    model: {
      handler() {
        this.reset();
        this.initData();
      }
    }
  },

  computed: {
    isCreate() {
      return !(this.model && this.model.id);
    },

    editorTitle() {
      return this.isCreate ? "新建租户" : "修改租户";
    }
  },

  methods: {
    initData() {
      let data = this.model || {};
      // console.log("==>", data);
      this.formData.name = data.name || "";
      this.formData.mobile = data.mobile || "";
      this.formData.email = data.email || "";
      this.formData.remark = data.remark || "";
      this.formData.password = this.isCreate ? "" : "******";
      this.formData.code = data.code || "";
      this.formData.secret = this.isCreate ? "" : "****************";

      this.editPassword = this.isCreate;
      this.isNameValid = !this.isCreate;
      this.isMobileValid = !this.isCreate;
      this.isEmailValid = !this.isCreate;
      this.showSecret = false;
      this.loadingFlag = false;
    },

    reset() {
      let form = this.$refs.form;
      form && form.resetFields();
    },

    checkNameInput(rule, value) {
      this.isNameValid = false;
      value = trimToEmpty(value);
      if (value) {
        return this.checkInput({ name: value })
          .then(() => (this.isNameValid = true))
          .catch(() => Promise.reject("名称已存在！"));
      } else {
        return Promise.reject("名称不能为空！");
      }
    },

    checkMobileInput(rule, value) {
      this.isMobileValid = false;
      value = trimToEmpty(value);
      if (value) {
        if (!/^1\d{10}$/.test(value)) {
          return Promise.reject("手机号码格式不正确！");
        }
        return this.checkInput({ mobile: value })
          .then(() => (this.isMobileValid = true))
          .catch(() => Promise.reject("手机号码已存在！"));
      } else {
        return Promise.resolve();
      }
    },

    checkEmailInput(rule, value) {
      this.isEmailValid = false;
      value = trimToEmpty(value);
      if (value) {
        if (!/^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/.test(value)) {
          return Promise.reject("邮箱格式不正确！");
        }
        return this.checkInput({ email: value })
          .then(() => (this.isEmailValid = true))
          .catch(() => Promise.reject("邮箱已存在！"));
      } else {
        return Promise.resolve();
      }
    },

    checkInput(data) {
      return new Promise((resolve, reject) => {
        $get(api("/account/list"), data, (err, ret) => {
          if (!err && ret && ret.length > 0) {
            if (this.isCreate || this.model.id != ret[0].id) {
              reject("已存在");
            } else {
              resolve();
            }
          } else {
            resolve();
          }
          return false;
        });
      });
    },

    onPasswordEditHandler() {
      this.editPassword = true;
      this.formData.password = "";
      this.$refs.passwordInput.focus();
    },

    onSecretShowHandler() {
      this.showSecret = true;
      $get(api(`/account/info/${this.model.id}`)).then(({ data }) => {
        this.formData.secret = data.secret;
      });
    },

    onSecretUpdateHandler() {
      let _api = api(`/account/set/secret/${this.model.id}`);
      Message.confirm("", "是否确认重新生成？", _api)
        .then(({ data }) => {
          this.formData.secret = data;
          Message.success("密钥更新成功");
        })
        .catch(() => {
          // .
        });
    },

    onSubmitHandler() {
      if (this.loadingFlag) {
        return;
      }
      let data = this.getSaveData();
      if (data) {
        // console.log("save:", data);
        this.loadingFlag = true;
        let _api = api(data.id ? "/account/update" : "/account/add");
        $post(_api, data, err => {
          this.loadingFlag = false;
          if (!err) {
            Message.success("保存成功");
            this.$emit("submit");
          }
        });
      }
    },

    onCancelBtnHandler() {
      this.$emit("cancel");
    },

    getSaveData() {
      let data = {};

      data.id = (this.model && this.model.id) || undefined;
      data.name = trimToEmpty(this.formData.name);
      if (!data.name) {
        return Message.error("请输入租户名称");
      } else if (!this.isNameValid) {
        return Message.error("租户名称已存在");
      }

      data.mobile = trimToEmpty(this.formData.mobile);
      if (data.mobile && !this.isMobileValid) {
        return Message.error("手机号码不符");
      }

      data.email = trimToEmpty(this.formData.email);
      if (data.email && !this.isEmailValid) {
        return Message.error("邮箱不符");
      }

      data.remark = trimToEmpty(this.formData.remark);

      if (this.editPassword) {
        data.password = trimToEmpty(this.formData.password);
        if (!data.password) {
          return Message.error("请输入登录密码");
        }
      }

      return data;
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

  .form {
    margin-top: 30px;

    input[readonly] {
      background-color: #fafafa;
    }
  }

  .ic-valid {
    display: inline-block;
    width: 12px;
    height: 32px;
    vertical-align: top;
    margin-left: 10px;
    background-image: url(../../assets/c001.png);
    background-size: 12px;
    background-repeat: no-repeat;
    background-position: center;
  }

  .edtbtn {
    display: inline-block;
    margin-left: 10px;
    font-size: 12px;
    line-height: 20px;
  }
}
</style>
