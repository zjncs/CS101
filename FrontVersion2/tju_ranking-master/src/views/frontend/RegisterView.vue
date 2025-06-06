<template>
  <div class="auth-view-wrapper">
    <div class="auth-container-split">
      <!-- 左侧图片和Logo区域 -->
      <div class="auth-image-section">
        <div class="image-overlay"></div>
        <div class="logo-container">
          <img src="https://placehold.co/150x150/FFFFFF/28a745?text=Join-Us" alt="天大点评 Logo" class="brand-logo">
          <h2 class="brand-title">加入我们</h2>
          <p class="brand-tagline">成为天大点评的一员，开启您的美食之旅</p>
        </div>
      </div>

      <!-- 右侧表单区域 -->
      <div class="auth-form-section">
        <div class="auth-card">
          <h1 class="auth-title">创建新账户</h1>
          <p class="auth-subtitle">分享与发现，从这里开始！</p>

          <form @submit.prevent="handleRegister" autocomplete="off">
            <div class="form-group">
              <label for="username">用户名:</label>
              <input type="text" id="username" v-model="form.username" required placeholder="设置您的用户名">
            </div>

            <div class="form-group">
              <label for="email">邮箱:</label>
              <input type="email" id="email" v-model="form.email" required placeholder="请输入有效的邮箱地址">
            </div>

            <div class="form-group">
              <label for="phone">手机号:</label>
              <input type="tel" id="phone" v-model="form.phone" required placeholder="用于接收短信验证码">
            </div>

            <div class="form-group">
              <label for="password">密码:</label>
              <input type="password" id="password" v-model="form.password" required placeholder="至少6位字符">
            </div>

            <div class="form-group">
              <label for="confirmPassword">确认密码:</label>
              <input type="password" id="confirmPassword" v-model="form.confirmPassword" required placeholder="请再次输入密码">
            </div>

            <!-- 图形验证码 -->
            <div class="form-group captcha-group">
              <label for="graphicalCaptcha">图形验证码:</label>
              <div class="input-with-image">
                <input
                    type="text"
                    id="graphicalCaptcha"
                    v-model="form.graphicalCaptchaInput"
                    :disabled="!isPhoneValid"
                    placeholder="不区分大小写"
                >
                <canvas ref="captchaCanvas" @click="refreshGraphicalCaptcha" title="点击刷新"></canvas>
              </div>
            </div>

            <!-- 短信验证码 -->
            <div class="form-group sms-group">
              <label for="smsCode">短信验证码:</label>
              <div class="input-with-button">
                <input type="text" id="smsCode" v-model="form.smsCode" placeholder="请输入6位短信验证码">
                <button
                    type="button"
                    @click="sendSmsCode"
                    class="btn-send-sms"
                    :disabled="!canSendSms || countdown > 0"
                >
                  {{ countdown > 0 ? `${countdown}秒后重发` : '发送验证码' }}
                </button>
              </div>
            </div>

            <div v-if="errorMessage" class="error-message">
              {{ errorMessage }}
            </div>

            <button type="submit" class="btn-submit" :disabled="loading">
              <i class="fas fa-spinner fa-spin" v-if="loading"></i>
              <span v-else>立即注册</span>
            </button>
          </form>

          <div class="auth-footer">
            <p>已有账户? <router-link :to="{ name: 'Login' }">直接登录</router-link></p>
            <p><router-link :to="{ name: 'Home' }">返回首页</router-link></p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
// 模拟API接口
const mockRegisterApi = {
  checkGraphicalCaptcha(code, expectedCode) {
    return new Promise(resolve => {
      setTimeout(() => resolve(code.toLowerCase() === expectedCode.toLowerCase()), 300);
    });
  },
  sendSms(phone) {
    return new Promise(resolve => {
      console.log(`[API MOCK] Sending SMS to ${phone}... The verification code is 123456.`);
      setTimeout(() => resolve({ success: true, message: '短信已发送，请注意查收！' }), 1000);
    });
  },
  registerUser(userData) {
    return new Promise(resolve => {
      console.log('[API MOCK] Registering user:', userData);
      setTimeout(() => resolve({ success: true, message: '注册成功！' }), 1500);
    });
  }
};

export default {
  name: 'RegisterView',
  data() {
    return {
      form: {
        username: '',
        email: '',
        phone: '',
        password: '',
        confirmPassword: '',
        graphicalCaptchaInput: '',
        smsCode: '',
      },
      graphicalCaptchaCode: '', // 正确的图形验证码
      countdown: 0,
      smsSent: false,
      errorMessage: '',
      loading: false,
    };
  },
  computed: {
    isPhoneValid() {
      // 简单验证手机号格式（11位数字）
      return /^1\d{10}$/.test(this.form.phone);
    },
    canSendSms() {
      // 必须手机号有效，且图形验证码输入了内容
      return this.isPhoneValid && this.form.graphicalCaptchaInput.length > 0;
    }
  },
  methods: {
    // 生成并绘制图形验证码
    generateGraphicalCaptcha() {
      const canvas = this.$refs.captchaCanvas;
      if (!canvas) return;
      const ctx = canvas.getContext('2d');
      const chars = 'ABCDEFGHJKMNPQRSTUVWXYZabcdefghjkmnpqrstuvwxyz23456789'; // 移除了易混淆的字符
      let captcha = '';
      const canvasWidth = 100;
      const canvasHeight = 44; // 与输入框高度匹配
      canvas.width = canvasWidth;
      canvas.height = canvasHeight;

      ctx.fillStyle = `rgb(${Math.floor(Math.random()*55 + 200)}, ${Math.floor(Math.random()*55 + 200)}, ${Math.floor(Math.random()*55 + 200)})`;
      ctx.fillRect(0, 0, canvasWidth, canvasHeight);

      for (let i = 0; i < 4; i++) {
        const char = chars.charAt(Math.floor(Math.random() * chars.length));
        captcha += char;
        ctx.font = `${Math.floor(Math.random() * 10 + 24)}px Arial`;
        ctx.fillStyle = `rgb(${Math.floor(Math.random()*150)}, ${Math.floor(Math.random()*150)}, ${Math.floor(Math.random()*150)})`;
        ctx.save();
        ctx.translate(20 + i * 20, 30);
        ctx.rotate((Math.random() - 0.5) * 0.4); // 随机旋转角度
        ctx.fillText(char, 0, 0);
        ctx.restore();
      }
      this.graphicalCaptchaCode = captcha;
      console.log(`[CAPTCHA] New graphical captcha generated: ${captcha}`);
    },
    refreshGraphicalCaptcha() {
      this.generateGraphicalCaptcha();
    },
    async sendSmsCode() {
      this.errorMessage = '';
      if (!this.canSendSms) return;

      // 步骤1：验证图形验证码
      const isGraphicalCaptchaCorrect = await mockRegisterApi.checkGraphicalCaptcha(this.form.graphicalCaptchaInput, this.graphicalCaptchaCode);

      if (!isGraphicalCaptchaCorrect) {
        this.errorMessage = '图形验证码不正确。';
        this.refreshGraphicalCaptcha(); // 刷新图形码
        return;
      }

      // 步骤2：发送短信
      try {
        const response = await mockRegisterApi.sendSms(this.form.phone);
        alert(response.message); // "短信已发送..."
        this.smsSent = true;
        this.countdown = 60; // 开始60秒倒计时
        const timer = setInterval(() => {
          this.countdown--;
          if (this.countdown <= 0) {
            clearInterval(timer);
          }
        }, 1000);
      } catch (error) {
        this.errorMessage = '短信发送失败，请稍后重试。';
      }
    },
    async handleRegister() {
      this.errorMessage = '';
      // 基本校验
      if (this.form.password !== this.form.confirmPassword) {
        this.errorMessage = '两次输入的密码不一致。';
        return;
      }
      if (this.form.password.length < 6) {
        this.errorMessage = '密码长度至少为6位。';
        return;
      }
      if (!this.smsSent) {
        this.errorMessage = '请先发送并填写短信验证码。';
        return;
      }
      // 模拟校验短信验证码
      if (this.form.smsCode !== '123456') {
        this.errorMessage = '短信验证码不正确。';
        return;
      }

      this.loading = true;
      const userData = { ...this.form };
      delete userData.confirmPassword;
      delete userData.graphicalCaptchaInput;

      try {
        const response = await mockRegisterApi.registerUser(userData);
        alert(response.message); // "注册成功！"
        this.$router.push({ name: 'Login' }); // 跳转到登录页
      } catch (error) {
        this.errorMessage = '注册失败，请稍后重试。';
      } finally {
        this.loading = false;
      }
    }
  },
  mounted() {
    this.generateGraphicalCaptcha();
  }
}
</script>

<style scoped>
/* 与登录页共用大部分样式，提取为.auth-xxx */
.auth-view-wrapper { width: 100%; height: 100vh; display: flex; justify-content: center; align-items: center; background-color: #f0f2f5; }
.auth-container-split { width: 100%; max-width: 1200px; height: 100%; max-height: 750px; display: flex; background-color: #ffffff; border-radius: 12px; box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1); overflow: hidden; }

.auth-image-section { flex-basis: 55%; background-image: url('https://images.unsplash.com/photo-1555939594-58d7cb561ad1?q=80&w=1974&auto=format&fit=crop'); background-size: cover; background-position: center; position: relative; display: flex; justify-content: center; align-items: center; color: white; }
.image-overlay { position: absolute; top: 0; left: 0; width: 100%; height: 100%; background-color: rgba(40, 167, 69, 0.4); } /* 绿色半透明遮罩 */
.logo-container { text-align: center; z-index: 1; padding: 20px; }
.brand-logo { width: 120px; height: 120px; margin-bottom: 20px; border-radius: 50%; border: 4px solid rgba(255,255,255,0.8); }
.brand-title { font-size: 2.5em; font-weight: bold; text-shadow: 2px 2px 4px rgba(0,0,0,0.3); margin-bottom: 10px; }
.brand-tagline { font-size: 1.1em; opacity: 0.9; }

.auth-form-section { flex-basis: 45%; display: flex; justify-content: center; align-items: center; padding: 20px 40px; overflow-y: auto; }
.auth-card { width: 100%; max-width: 420px; padding: 0; box-shadow: none; background-color: transparent; text-align: center; }

.auth-title { font-size: 2.2em; color: #333; margin-bottom: 10px; }
.auth-subtitle { font-size: 1em; color: #666; margin-bottom: 25px; }
.form-group { margin-bottom: 15px; text-align: left; }
.form-group label { display: block; font-weight: bold; margin-bottom: 8px; color: #555; font-size: 0.9em; }
input[type="text"], input[type="email"], input[type="password"], input[type="tel"] { width: 100%; height: 44px; padding: 12px 15px; border: 1px solid #ced4da; border-radius: 5px; box-sizing: border-box; font-size: 1em; }
input:focus { border-color: #28a745; outline: none; box-shadow: 0 0 0 2px rgba(40, 167, 69, 0.2); }
input:disabled { background-color: #e9ecef; cursor: not-allowed; }

.input-with-image, .input-with-button { display: flex; gap: 10px; }
.input-with-image input, .input-with-button input { flex-grow: 1; }
.captcha-group canvas { width: 100px; height: 44px; border-radius: 5px; border: 1px solid #ced4da; cursor: pointer; flex-shrink: 0; }
.btn-send-sms { height:44px; padding: 0 15px; flex-shrink: 0; white-space: nowrap; font-size: 0.9em; background-color: #007bff; color: white; border: none; border-radius: 5px; cursor: pointer; transition: background-color 0.2s; }
.btn-send-sms:disabled { background-color: #a0cfff; cursor: not-allowed; }
.btn-send-sms:not(:disabled):hover { background-color: #0056b3; }

.error-message { background-color: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; padding: 10px; border-radius: 5px; margin-bottom: 15px; font-size: 0.9em; }
.btn-submit { width: 100%; padding: 12px; background-color: #28a745; color: white; border: none; border-radius: 5px; font-size: 1.1em; font-weight: bold; cursor: pointer; transition: background-color 0.2s; display: flex; align-items: center; justify-content: center; }
.btn-submit:disabled { background-color: #28a745aa; cursor: not-allowed; }
.btn-submit:not(:disabled):hover { background-color: #218838; }
.btn-submit i { margin-right: 8px; }

.auth-footer { margin-top: 25px; font-size: 0.9em; }
.auth-footer p { margin-bottom: 8px; color: #666; }
.auth-footer a { color: #007bff; text-decoration: none; font-weight: 500; }
.auth-footer a:hover { text-decoration: underline; }

/* 响应式设计 */
@media (max-width: 992px) { .auth-image-section { display: none; } .auth-form-section { flex-basis: 100%; padding: 40px; } }
@media (max-height: 700px) { .auth-form-section { align-items: flex-start; } }
</style>
