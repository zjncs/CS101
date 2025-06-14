<template>
  <div class="register-container">
    <el-card class="register-card">
      <div slot="header" class="clearfix">
        <h2>TjuFood 用户注册</h2>
      </div>
      <el-form :model="registerForm" :rules="rules" ref="registerForm" label-width="100px">
        <!-- 基础信息 -->
        <el-form-item label="昵称" prop="nickname">
          <el-input v-model="registerForm.nickname" placeholder="请输入您的昵称"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="registerForm.email" placeholder="用于登录和接收验证码"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="registerForm.password" type="password" placeholder="请输入至少6位的密码"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input v-model="registerForm.confirmPassword" type="password" placeholder="请再次输入密码"></el-input>
        </el-form-item>

        <!-- 图形验证码 -->
        <el-form-item label="图形验证码" prop="captchaCode">
          <el-input v-model="registerForm.captchaCode" placeholder="请输入右侧图形验证码" style="width: 60%; float: left;"></el-input>
          <img :src="captchaImg" @click="getCaptcha" class="captcha-img" title="点击刷新">
        </el-form-item>

        <!-- 邮箱验证码 -->
        <el-form-item label="邮箱验证码" prop="code">
          <el-input v-model="registerForm.code" placeholder="请输入邮箱收到的6位验证码">
            <el-button slot="append" @click="handleSendCode" :disabled="isSendingCode">
              {{ codeButtonText }}
            </el-button>
          </el-input>
        </el-form-item>

        <!-- 其他信息 -->
        <el-form-item label="性别" prop="gender">
          <el-radio-group v-model="registerForm.gender">
            <el-radio :label="1">男</el-radio>
            <el-radio :label="0">女</el-radio>
            <el-radio :label="2">保密</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="校区" prop="campus">
          <el-select v-model="registerForm.campus" placeholder="请选择校区" style="width: 100%;">
            <el-option label="北洋园校区" value="北洋园校区"></el-option>
            <el-option label="卫津路校区" value="卫津路校区"></el-option>
          </el-select>
        </el-form-item>

        <!-- 操作按钮 -->
        <el-form-item>
          <el-button type="primary" @click="submitForm('registerForm')" style="width: 100%;">立即注册</el-button>
        </el-form-item>
      </el-form>
      <div class="links">
        <router-link to="/login">已有账号？去登录</router-link>
      </div>
    </el-card>
  </div>
</template>

<script>
// 引入API服务文件
import { sendEmailCode, register, getCaptchaImage } from '@/service/authService';

export default {
  name: 'RegisterView',
  data() {
    // 自定义密码验证规则
    const validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.registerForm.password) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    return {
      registerForm: {
        email: '',
        password: '',
        confirmPassword: '',
        code: '',
        nickname: '',
        gender: 2, // 默认为保密
        campus: '',
        captchaCode: '',
        captchaKey: ''
      },
      rules: {
        email: [
          { required: true, message: '请输入邮箱地址', trigger: 'blur' },
          { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, message: '密码长度不能小于6位', trigger: 'blur' }
        ],
        confirmPassword: [
          { required: true, validator: validatePass2, trigger: 'blur' }
        ],
        nickname: [
          { required: true, message: '请输入昵称', trigger: 'blur' }
        ],
        campus: [
          { required: true, message: '请选择校区', trigger: 'change' }
        ],
        captchaCode: [
          { required: true, message: '请输入图形验证码', trigger: 'blur' }
        ],
        code: [
          { required: true, message: '请输入邮箱验证码', trigger: 'blur' },
          { len: 6, message: '验证码必须为6位', trigger: 'blur' }
        ]
      },
      captchaImg: '', // 用于显示图形验证码图片
      isSendingCode: false,
      codeButtonText: '发送验证码',
      countdown: 60,
    };
  },
  created() {
    // 组件创建时，自动获取一次图形验证码
    this.getCaptcha();
  },
  methods: {
    // 获取图形验证码的方法
    async getCaptcha() {
      try {
        const response = await getCaptchaImage();
        if (response.data.success) {
          this.captchaImg = response.data.data.image;
          this.registerForm.captchaKey = response.data.data.key;
        } else {
          this.$message.error(response.data.errorMsg || '获取图形验证码失败');
        }
      } catch (error) {
        this.$message.error('网络错误，无法获取图形验证码');
      }
    },

    // 【已重构】处理发送邮箱验证码的逻辑，使用 async/await 增强健壮性
    async handleSendCode() {
      // 1. 先对需要验证的字段进行校验
      try {
        await this.$refs.registerForm.validateField(['email', 'captchaCode']);
      } catch (error) {
        // 如果校验失败，validateField 会 reject 一个 promise，直接在这里返回即可
        return;
      }

      // 2. 如果校验通过，立即设置按钮为禁用状态
      this.isSendingCode = true;

      // 3. 准备请求数据
      const sendData = {
        email: this.registerForm.email,
        type: 0, // 注册类型
        captchaKey: this.registerForm.captchaKey,
        captchaCode: this.registerForm.captchaCode
      };

      // 4. 使用 try/catch 来确保无论成功或失败，状态都能被正确管理
      try {
        const response = await sendEmailCode(sendData); // 使用 await 等待异步结果

        if (response.data.success) {
          this.$message.success('验证码已发送，请注意查收！');
          this.startCountdown(); // 成功后开始倒计时
        } else {
          this.$message.error(response.data.errorMsg || '发送失败');
          this.isSendingCode = false; // 发送失败，立刻恢复按钮
          this.getCaptcha(); // 刷新图形验证码
        }
      } catch (error) {
        this.$message.error('发送验证码失败：' + (error.response?.data?.errorMsg || '网络错误'));
        this.isSendingCode = false; // 网络或服务器错误，立刻恢复按钮
        this.getCaptcha();
      }
    },

    // 开始倒计时的方法
    startCountdown() {
      // isSendingCode 状态已在 handleSendCode 中设置为 true
      const timer = setInterval(() => {
        if (this.countdown > 1) {
          this.countdown--;
          this.codeButtonText = `${this.countdown}秒后重试`;
        } else {
          clearInterval(timer);
          this.codeButtonText = '发送验证码';
          this.isSendingCode = false;
          this.countdown = 60; // 重置倒计时
        }
      }, 1000);
    },

    // 提交注册表单的方法
    async submitForm(formName) {
      this.$refs[formName].validate(async (valid) => {
        if (valid) {
          try {
            // 调用API进行注册
            const response = await register(this.registerForm);
            if (response.data.success) {
              this.$message.success('注册成功！即将跳转到登录页...');
              // 注册成功后，延时1.5秒跳转到登录页
              setTimeout(() => {
                this.$router.push('/login');
              }, 1500);
            } else {
              this.$message.error(response.data.errorMsg || '注册失败');
              // 注册失败时也刷新一下图形验证码
              this.getCaptcha();
            }
          } catch (error) {
            this.$message.error('注册失败：' + (error.response?.data?.errorMsg || '未知错误'));
            this.getCaptcha();
          }
        } else {
          this.$message.error('请检查表单填写是否完整且正确！');
          return false;
        }
      });
    },
  }
}
</script>

<style scoped>
.register-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background-color: #f5f7fa;
}
.register-card {
  width: 500px;
  padding: 1rem;
}
.captcha-img {
  width: 38%;
  height: 40px;
  float: right;
  cursor: pointer;
  border-radius: 4px;
  border: 1px solid #DCDFE6;
}
.links {
  margin-top: 20px;
  text-align: center;
}
.links a {
  margin: 0 10px;
  color: #409EFF;
  text-decoration: none;
}
</style>