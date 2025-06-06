<template>
  <div class="auth-view-wrapper">
    <div class="auth-container-split">
      <!-- 左侧图片和Logo区域 -->
      <div class="auth-image-section">
        <div class="image-overlay"></div>
        <div class="logo-container">
          <img src="https://placehold.co/150x150/FFFFFF/ffc107?text=Reset" alt="天大点评 Logo" class="brand-logo">
          <h2 class="brand-title">重置密码</h2>
          <p class="brand-tagline">找回您的账户，继续美食之旅</p>
        </div>
      </div>

      <!-- 右侧表单区域 -->
      <div class="auth-form-section">
        <div class="auth-card">
          <h1 class="auth-title">找回您的密码</h1>
          <p class="auth-subtitle">通过手机验证码来重置您的账户密码</p>

          <form @submit.prevent="handleResetPassword" autocomplete="off">
            <div class="form-group">
              <label>选择您的身份:</label>
              <div class="radio-group">
                <label>
                  <input type="radio" v-model="form.role" value="user" name="role"> 用户
                </label>
                <label>
                  <input type="radio" v-model="form.role" value="admin" name="role"> 后台管理员
                </label>
                <label class="disabled">
                  <input type="radio" value="boss" name="role" disabled> 平台管理员
                </label>
              </div>
              <small v-if="form.role === 'boss'">平台管理员不支持在线重置密码，请联系系统服务商。</small>
            </div>

            <div class="form-group">
              <label for="phone">手机号:</label>
              <input type="tel" id="phone" v-model="form.phone" required placeholder="请输入您注册时使用的手机号">
            </div>

            <div class="form-group sms-group">
              <label for="smsCode">短信验证码:</label>
              <div class="input-with-button">
                <input type="text" id="smsCode" v-model="form.smsCode" required placeholder="请输入6位短信验证码">
                <button
                    type="button"
                    @click="sendSmsCode"
                    class="btn-send-sms"
                    :disabled="!isPhoneValid || countdown > 0"
                >
                  {{ countdown > 0 ? `${countdown}秒后重发` : '发送验证码' }}
                </button>
              </div>
            </div>

            <div class="form-group">
              <label for="newPassword">新密码:</label>
              <input type="password" id="newPassword" v-model="form.newPassword" required placeholder="设置您的新密码 (至少6位)">
            </div>

            <div class="form-group">
              <label for="confirmNewPassword">确认新密码:</label>
              <input type="password" id="confirmNewPassword" v-model="form.confirmNewPassword" required placeholder="请再次输入新密码">
            </div>


            <div v-if="errorMessage" class="error-message">
              {{ errorMessage }}
            </div>

            <button type="submit" class="btn-submit" :disabled="loading">
              <i class="fas fa-spinner fa-spin" v-if="loading"></i>
              <span v-else>确认重置</span>
            </button>
          </form>

          <div class="auth-footer">
            <p>想起来了? <router-link :to="{ name: 'Login' }">返回登录</router-link></p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
// 模拟API接口
const mockResetApi = {
  // 假设后端能通过手机号查到用户
  checkPhoneExists(role, phone) {
    return new Promise(resolve => {
      console.log(`[API MOCK] Checking if phone ${phone} exists for role ${role}`);
      // 简单模拟，实际应查询数据库
      const phoneExists = (role === 'user' && phone === '13812345678') || (role === 'admin' && phone === '13912345678');
      setTimeout(() => resolve(phoneExists), 500);
    });
  },
  sendResetSms(phone) {
    return new Promise(resolve => {
      console.log(`[API MOCK] Sending RESET SMS to ${phone}... The verification code is 654321.`);
      setTimeout(() => resolve({ success: true, message: '重置密码的验证码已发送！' }), 1000);
    });
  },
  resetPassword(data) {
    return new Promise(resolve => {
      console.log('[API MOCK] Resetting password for:', data);
      setTimeout(() => resolve({ success: true, message: '密码重置成功！' }), 1500);
    });
  }
};

export default {
  name: 'ForgotPasswordView',
  data() {
    return {
      form: {
        role: 'user',
        phone: '',
        smsCode: '',
        newPassword: '',
        confirmNewPassword: '',
      },
      countdown: 0,
      smsSent: false,
      errorMessage: '',
      loading: false,
    };
  },
  computed: {
    isPhoneValid() {
      return /^1\d{10}$/.test(this.form.phone);
    }
  },
  methods: {
    async sendSmsCode() {
      if (!this.isPhoneValid) {
        this.errorMessage = '请输入有效的手机号码。';
        return;
      }
      this.errorMessage = '';

      // 步骤1: 检查手机号是否存在于对应角色下
      const phoneExists = await mockResetApi.checkPhoneExists(this.form.role, this.form.phone);
      if (!phoneExists) {
        this.errorMessage = '该手机号未注册或角色选择错误。';
        return;
      }

      // 步骤2: 发送短信
      try {
        const response = await mockResetApi.sendResetSms(this.form.phone);
        alert(response.message);
        this.smsSent = true;
        this.countdown = 60;
        const timer = setInterval(() => {
          this.countdown--;
          if (this.countdown <= 0) clearInterval(timer);
        }, 1000);
      } catch (error) {
        this.errorMessage = '短信发送失败，请稍后重试。';
      }
    },
    async handleResetPassword() {
      this.errorMessage = '';
      if (this.form.newPassword !== this.form.confirmNewPassword) {
        this.errorMessage = '两次输入的新密码不一致。';
        return;
      }
      if (this.form.newPassword.length < 6) {
        this.errorMessage = '新密码长度至少为6位。';
        return;
      }
      if (!this.smsSent) {
        this.errorMessage = '请先获取并填写短信验证码。';
        return;
      }
      // 模拟校验短信验证码
      if (this.form.smsCode !== '654321') {
        this.errorMessage = '短信验证码不正确。';
        return;
      }

      this.loading = true;
      try {
        const response = await mockResetApi.resetPassword(this.form);
        alert(response.message); // "密码重置成功！"
        // 自动登录并跳转
        const role = this.form.role;
        // 注意：此处账号未知，只用手机号标识，所以登录名可能需要调整
        const displayName = role === 'user' ? '重置密码用户' : '重置密码管理员';
        this.$auth.login(role, displayName);

        if (role === 'user') {
          this.$router.push({ name: 'Home' });
        } else if (role === 'admin') {
          this.$router.push({ name: 'AdminDashboard' });
        }

      } catch (error) {
        this.errorMessage = '重置失败，请稍后重试。';
      } finally {
        this.loading = false;
      }
    }
  },
  watch: {
    // 当切换角色时，禁用Boss并清空手机号等信息
    'form.role'(newRole) {
      if (newRole === 'boss') {
        this.form.role = 'user'; // 自动切换回user
        alert('平台管理员不支持在线重置密码。');
      }
      this.form.phone = '';
      this.form.smsCode = '';
      this.errorMessage = '';
      this.countdown = 0;
      this.smsSent = false;
    }
  }
}
</script>

<style scoped>
/* 与登录页和注册页共用样式 */
.auth-view-wrapper { width: 100%; height: 100vh; display: flex; justify-content: center; align-items: center; background-color: #f0f2f5; }
.auth-container-split { width: 100%; max-width: 1200px; height: 100%; max-height: 750px; display: flex; background-color: #ffffff; border-radius: 12px; box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1); overflow: hidden; }

.auth-image-section { flex-basis: 55%; background-image: url('https://images.unsplash.com/photo-1505682499293-233fb1417540?q=80&w=2070&auto=format&fit=crop'); background-size: cover; background-position: center; position: relative; display: flex; justify-content: center; align-items: center; color: white; }
.image-overlay { position: absolute; top: 0; left: 0; width: 100%; height: 100%; background-color: rgba(255, 193, 7, 0.4); } /* 黄色半透明遮罩 */
.logo-container { text-align: center; z-index: 1; padding: 20px; }
.brand-logo { width: 120px; height: 120px; margin-bottom: 20px; border-radius: 50%; border: 4px solid rgba(255,255,255,0.8); }
.brand-title { font-size: 2.5em; font-weight: bold; text-shadow: 2px 2px 4px rgba(0,0,0,0.3); margin-bottom: 10px; }
.brand-tagline { font-size: 1.1em; opacity: 0.9; }

.auth-form-section { flex-basis: 45%; display: flex; justify-content: center; align-items: center; padding: 40px; }
.auth-card { width: 100%; max-width: 420px; padding: 0; box-shadow: none; background-color: transparent; text-align: center; }

.auth-title { font-size: 2.2em; color: #333; margin-bottom: 10px; }
.auth-subtitle { font-size: 1em; color: #666; margin-bottom: 30px; }
.form-group { margin-bottom: 15px; text-align: left; }
.form-group label { display: block; font-weight: bold; margin-bottom: 8px; color: #555; font-size: 0.9em; }
.radio-group { display: flex; gap: 20px; align-items: center; padding: 10px 0; }
.radio-group label { display: flex; align-items: center; cursor: pointer; font-size: 0.95em; }
.radio-group label.disabled { color: #aaa; cursor: not-allowed; }
.radio-group input[type="radio"] { margin-right: 6px; }
.form-group small { font-size: 0.8em; color: #dc3545; }
input[type="tel"], input[type="text"], input[type="password"] { width: 100%; height:44px; padding: 12px 15px; border: 1px solid #ced4da; border-radius: 5px; box-sizing: border-box; font-size: 1em; }
.sms-group .input-with-button { display: flex; gap: 10px; }
.sms-group input { flex-grow: 1; }
.btn-send-sms { height: 44px; padding: 0 15px; flex-shrink: 0; white-space: nowrap; font-size: 0.9em; background-color: #007bff; color: white; border: none; border-radius: 5px; cursor: pointer; }
.btn-send-sms:disabled { background-color: #a0cfff; cursor: not-allowed; }
.error-message { background-color: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; padding: 10px; border-radius: 5px; margin: 15px 0; font-size: 0.9em; }
.btn-submit { width: 100%; padding: 12px; background-color: #28a745; color: white; border: none; border-radius: 5px; font-size: 1.1em; font-weight: bold; cursor: pointer; display: flex; align-items: center; justify-content: center; }
.btn-submit:disabled { background-color: #28a745aa; cursor: not-allowed; }
.auth-footer { margin-top: 25px; font-size: 0.9em; }
.auth-footer a { color: #007bff; text-decoration: none; font-weight: 500; }

@media (max-width: 992px) { .auth-image-section { display: none; } .auth-form-section { flex-basis: 100%; padding: 40px;} }
</style>
