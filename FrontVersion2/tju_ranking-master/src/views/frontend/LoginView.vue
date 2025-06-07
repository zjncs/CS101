<template>
  <div class="login-view-wrapper">
    <div class="login-container-split">
      <!-- 左侧图片和Logo区域 -->
      <div class="login-image-section">
        <div class="image-overlay"></div>
        <div class="logo-container">
          <!-- 在此放置您的Logo图片 -->
          <img src="https://placehold.co/150x150/FFFFFF/007BFF?text=TJU-DP" alt="天大点评 Logo" class="brand-logo">
          <h2 class="brand-title">天大点评</h2>
          <p class="brand-tagline">发现、分享、品味你的天大校园生活</p>
        </div>
      </div>

      <!-- 右侧表单区域 -->
      <div class="login-form-section">
        <div class="login-card">
          <h1 class="login-title">欢迎回来！</h1>
          <p class="login-subtitle">请选择您的身份并登录</p>

          <form @submit.prevent="handleLogin" autocomplete="off">
            <div class="form-group identity-selector">
              <label>选择身份:</label>
              <div class="radio-group">
                <label>
                  <input type="radio" v-model="selectedRole" value="user" name="role"> 用户
                </label>
                <label>
                  <input type="radio" v-model="selectedRole" value="admin" name="role"> 后台管理员
                </label>
                <label>
                  <input type="radio" v-model="selectedRole" value="boss" name="role"> 平台管理员
                </label>
              </div>
            </div>

            <div class="form-group">
              <label for="account">账号:</label>
              <input type="text" id="account" v-model="account" required placeholder="请输入您的账号" autocomplete="off">
            </div>

            <div class="form-group">
              <label for="password">密码:</label>
              <input type="password" id="password" v-model="password" required placeholder="请输入您的密码" autocomplete="new-password">
            </div>

            <div v-if="errorMessage" class="error-message">
              {{ errorMessage }}
            </div>

            <button type="submit" class="btn-login" :disabled="loading">
              <i class="fas fa-spinner fa-spin" v-if="loading"></i>
              <span v-else>登 录</span>
            </button>
          </form>

          <div class="login-footer">
            <router-link :to="{ name: 'ForgotPassword' }" class="forgot-password-link">忘记密码?</router-link>
            <p>还没有账户? <router-link :to="{ name: 'Register' }">立即注册</router-link></p>
            <p><router-link :to="{ name: 'Home' }">返回首页</router-link></p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
// 模拟后端用户数据库和API接口
const mockApi = {
  userDatabase: {
    // 初始化一个用户和后台管理员
    user: {
      'user123': { password: '123456', displayName: '初始用户' }
    },
    admin: {
      'manager': { password: '123456', displayName: '后台管理员' }
    },
    boss: {
      // 平台管理员账号，不再是admin/admin，需要从这里验证
      'bossadmin': { password: 'admin123', displayName: '平台管理员' }
    }
  },
  // 模拟验证函数，返回Promise
  verifyCredentials(role, account, password) {
    console.log(`[API MOCK] Verifying: role=${role}, account=${account}`);
    return new Promise((resolve, reject) => {
      setTimeout(() => {
        if (this.userDatabase[role] && this.userDatabase[role][account]) {
          const userRecord = this.userDatabase[role][account];
          if (userRecord.password === password) {
            resolve({ success: true, displayName: userRecord.displayName });
          } else {
            reject(new Error('密码不正确。'));
          }
        } else {
          reject(new Error('账号不存在或角色选择错误。'));
        }
      }, 1000); // 模拟网络延迟
    });
  }
};


export default {
  name: 'LoginView',
  data() {
    return {
      selectedRole: 'user', // 默认选择用户
      account: '',
      password: '',
      errorMessage: '',
      loading: false,
    };
  },
  methods: {
    handleLogin() {
      this.errorMessage = '';
      this.loading = true;

      // 调用模拟API进行验证
      mockApi.verifyCredentials(this.selectedRole, this.account, this.password)
          .then(response => {
            // 登录成功
            this.loading = false;
            const usernameToStore = response.displayName || this.account;
            this.$auth.login(this.selectedRole, usernameToStore);

            // 根据角色跳转
            if (this.selectedRole === 'user') {
              const redirectPath = this.$route.query.redirect || { name: 'Home' };
              this.$router.push(redirectPath);
            } else { // admin 和 boss 都跳转到后台
              this.$router.push({ name: 'AdminDashboard' });
            }
          })
          .catch(error => {
            // 登录失败
            this.loading = false;
            this.errorMessage = error.message;
          });
    }
  }
}
</script>

<style scoped>
.login-view-wrapper {
  width: 100%;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #f0f2f5;
}

.login-container-split {
  width: 100%;
  max-width: 1200px; /* 控制整体最大宽度 */
  height: 100%;
  max-height: 750px; /* 控制整体最大高度 */
  display: flex;
  background-color: #ffffff;
  border-radius: 12px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

.login-image-section {
  flex-basis: 55%; /* 左侧占55% */
  background-image: url('https://images.unsplash.com/photo-1504674900247-0877df9cc836?q=80&w=2070&auto=format&fit=crop');
  background-size: cover;
  background-position: center;
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
  color: white;
}

.image-overlay {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 91, 255, 0.4); /* 蓝色半透明遮罩 */
}

.logo-container {
  text-align: center;
  z-index: 1;
  padding: 20px;
}
.brand-logo {
  width: 120px;
  height: 120px;
  margin-bottom: 20px;
  border-radius: 50%;
  border: 4px solid rgba(255,255,255,0.8);
}
.brand-title {
  font-size: 2.5em;
  font-weight: bold;
  text-shadow: 2px 2px 4px rgba(0,0,0,0.3);
  margin-bottom: 10px;
}
.brand-tagline {
  font-size: 1.1em;
  opacity: 0.9;
}

.login-form-section {
  flex-basis: 45%; /* 右侧占45% */
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 40px;
}

.login-card {
  width: 100%;
  max-width: 400px;
  padding: 0; /* 在新布局中移除内边距 */
  box-shadow: none; /* 移除卡片阴影，因为父容器已有 */
  background-color: transparent;
  text-align: center;
}

.login-title { font-size: 2.2em; color: #333; margin-bottom: 10px; }
.login-subtitle { font-size: 1em; color: #666; margin-bottom: 30px; }
.form-group { margin-bottom: 20px; text-align: left; }
.form-group label { display: block; font-weight: bold; margin-bottom: 8px; color: #555; font-size: 0.9em; }
.identity-selector label { display: block; margin-bottom: 10px; }
.radio-group { display: flex; justify-content: space-around; align-items: center; padding: 10px 0; border: 1px solid #eee; border-radius: 5px; }
.radio-group label { display: flex; align-items: center; cursor: pointer; font-size: 0.95em; color: #444; margin-bottom: 0; }
.radio-group input[type="radio"] { margin-right: 6px; accent-color: #007bff; }
input[type="text"], input[type="password"] { width: 100%; padding: 12px 15px; border: 1px solid #ced4da; border-radius: 5px; box-sizing: border-box; font-size: 1em; }
input[type="text"]:focus, input[type="password"]:focus { border-color: #007bff; outline: none; box-shadow: 0 0 0 2px rgba(0, 123, 255, 0.2); }
.error-message { background-color: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; padding: 10px; border-radius: 5px; margin-bottom: 15px; font-size: 0.9em; }
.btn-login { width: 100%; padding: 12px; background-color: #007bff; color: white; border: none; border-radius: 5px; font-size: 1.1em; font-weight: bold; cursor: pointer; transition: background-color 0.2s; display: flex; align-items: center; justify-content: center; }
.btn-login:disabled { background-color: #007bffaa; cursor: not-allowed; }
.btn-login:not(:disabled):hover { background-color: #0056b3; }
.btn-login i { margin-right: 8px; }

.login-footer { margin-top: 25px; font-size: 0.9em; display: flex; flex-direction: column; align-items: center; gap: 8px; }
.login-footer .forgot-password-link { margin-bottom: 8px; align-self: flex-end; }
.login-footer p { margin: 0; color: #666; }
.login-footer a { color: #007bff; text-decoration: none; font-weight: 500; }
.login-footer a:hover { text-decoration: underline; }

/* 响应式设计 */
@media (max-width: 992px) {
  .login-image-section {
    display: none; /* 在平板及以下尺寸隐藏左侧图片区域 */
  }
  .login-form-section {
    flex-basis: 100%;
  }
}
@media (max-height: 500px) { /* 针对高度过小的屏幕 */
  .login-view-wrapper {
    height: auto;
    padding: 40px 0;
  }
  .login-container-split {
    height: auto;
  }
}

</style>
