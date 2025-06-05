<template>
  <div>
    <el-row type="flex" justify="center">
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>用户注册</span>
          <router-link to="/" style="float: right; padding: 3px 0">
            <el-button type="info" circle>返回</el-button>
          </router-link>
        </div>
        <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
          <el-form-item label="名称" prop="username">
            <el-input v-model="ruleForm.username"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="pass">
            <el-input type="password" v-model="ruleForm.pass" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="确认密码" prop="checkPass">
            <el-input type="password" v-model="ruleForm.checkPass" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
            <el-button @click="resetForm('ruleForm')">重置</el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </el-row>
  </div>
</template>

<script>
import services from "@/services/AccountServices.js";
export default {
  data() {
    return {
      /**表单数据 */
      ruleForm: {
        pass: "",
        checkPass: "",
        username: "",
      },
      /**校验规则 */
      rules: {
        pass: [
          { required: true, message: "不能为空", trigger: "blur" },
          { validator: this.validatePass, trigger: "blur" }
        ],
        checkPass: [
          { required: true, message: "不能为空", trigger: "blur" },
          { validator: this.validatePass2, trigger: "blur" }
        ],
        username: [
          { required: true, message: "名称不能为空", trigger: "blur" },
          { validator: this.checkusername, trigger: "blur" }
        ],
      },
    };
  },
  computed: {
    model() { return { name: this.ruleForm.username, password: this.ruleForm.pass } }
  },
  methods: {
    /**表单函数 */
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          services.Register(this.model).then(response => {
            console.log('response', response)
            if (response.isSuccess) {
              console.log("注册成功")
              this.$notify.info({
                title: '消息',
                message: '注册成功'
              });
              this.$router.push('/')
            } else {
              console.log(`注册失败{response.message}`)
            }
          })
        } else {
          console.log("error submit!!")
          return false
        }
      })
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },

    /**校验函数 */
    validatePass(rule, value, callback) {
      if (value === "") {
        callback(new Error("请输入密码"));
      } else {
        if (this.ruleForm.checkPass !== "") {
          this.$refs.ruleForm.validateField("checkPass");
        }
        callback();
      }
    },
    validatePass2(rule, value, callback) {
      if (value === "") {
        callback(new Error("请再次输入密码"));
      } else if (value !== this.ruleForm.pass) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    },
    checkusername(rule, value, callback) {
      services.IsNameUsed(value).then(response => {
        if (response.data) {
          callback(new Error('用户名已存在'));
        } else {
          callback();
        }
      })
        .catch(() => {
          callback(new Error('验证失败，请稍后再试'));
        })
    },
  },
};
</script>

<style scoped>
.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both;
}

.box-card {
  width: 480px;
}
</style>