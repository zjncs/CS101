<template>
  <div class="login-container">
    <div class="login-card">
      <header class="card-header">
        <!-- 建议在这里添加一个Logo图片 -->
        <!-- <img src="@/assets/logo-small.png" alt="天大点评" class="logo-small"> -->
        <h2>登录 天大点评</h2>
        <p>继续您的美食探索之旅！</p>
      </header>

      <form @submit.prevent="handleLogin" class="login-form">
        <div class="form-group">
          <label for="account">账户</label> <!-- 可以是用户名或邮箱 -->
          <input type="text" id="account" v-model="account" placeholder="请输入用户名或邮箱" required>
        </div>
        <div class="form-group">
          <label for="password">密码</label>
          <input type="password" id="password" v-model="password" placeholder="请输入密码" required>
        </div>

        <div class="form-options">
          <label class="remember-me">
            <input type="checkbox" v-model="rememberMe"> 记住我
          </label>
          <a href="/forgot-password" class="forgot-password-link">忘记密码?</a>
        </div>

        <div class="form-actions">
          <button type="submit" class="btn btn-primary">登 录</button>
        </div>
      </form>

      <footer class="card-footer">
        <p>还没有账户? <router-link to="/register">立即注册</router-link></p>
        <router-link to="/" class="back-home-link">返回首页</router-link>
      </footer>
    </div>
    <!-- 建议在页面背景添加一张与美食或校园相关的图片，并通过CSS设置 -->
  </div>
</template>

<script>
export default {
  name: 'LoginView',
  data() {
    return {
      account: '',
      password: '',
      rememberMe: false
    };
  },
  methods: {
    handleLogin() {
      // 在这里处理登录逻辑，例如发送API请求
      console.log('登录信息:', {
        account: this.account,
        password: this.password,
        rememberMe: this.rememberMe
      });
      // 登录成功后跳转，例如跳转到主页面
      // this.$router.push('/main');
      alert('登录请求已发送（模拟）');
    }
  }
}
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background-color: #eef1f5; /* 与注册页一致的背景色 */
  /* 如果要添加背景图片，可以在这里设置:
  background-image: url('~@/assets/background-login.jpg');
  background-size: cover;
  background-position: center;
  */
  padding: 20px;
  box-sizing: border-box;
  font-family: 'Helvetica Neue', Arial, sans-serif;
}

.login-card {
  background-color: #ffffff;
  padding: 30px 40px;
  border-radius: 12px;
  box-shadow: 0 8px 25px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-width: 420px; /* 控制卡片最大宽度 */
}

.card-header {
  text-align: center;
  margin-bottom: 30px;
}

.logo-small {
  max-width: 80px;
  margin-bottom: 10px;
}

.card-header h2 {
  font-size: 1.8em;
  color: #333;
  margin-bottom: 8px;
}

.card-header p {
  font-size: 1em;
  color: #666;
}

.login-form .form-group {
  margin-bottom: 20px;
}

.login-form label {
  display: block;
  font-weight: bold;
  margin-bottom: 8px;
  color: #555;
  font-size: 0.95em;
}

.login-form input[type="text"],
.login-form input[type="password"] {
  width: 100%;
  padding: 12px 15px;
  border: 1px solid #ccc;
  border-radius: 6px;
  box-sizing: border-box;
  font-size: 1em;
  transition: border-color 0.3s ease;
}

.login-form input[type="text"]:focus,
.login-form input[type="password"]:focus {
  border-color: #3498db; /* 主题色 */
  outline: none;
  box-shadow: 0 0 0 2px rgba(52, 152, 219, 0.2);
}

.form-options {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 25px;
  font-size: 0.9em;
}

.remember-me {
  display: flex;
  align-items: center;
  color: #555;
  cursor: pointer;
}

.remember-me input[type="checkbox"] {
  margin-right: 6px;
  accent-color: #3498db; /* 使复选框颜色与主题色一致 */
}

.forgot-password-link {
  color: #3498db;
  text-decoration: none;
}

.forgot-password-link:hover {
  text-decoration: underline;
}

.form-actions {
  margin-top: 30px;
}

.btn {
  padding: 12px 20px;
  border: none;
  border-radius: 6px;
  font-size: 1.1em;
  font-weight: bold;
  cursor: pointer;
  transition: background-color 0.3s ease, transform 0.2s ease;
  width: 100%;
}

.btn-primary {
  background-color: #3498db; /* 主题蓝色 */
  color: white;
}

.btn-primary:hover {
  background-color: #2980b9;
  transform: translateY(-1px);
}

.card-footer {
  text-align: center;
  margin-top: 25px;
  font-size: 0.9em;
}

.card-footer p {
  margin-bottom: 10px;
  color: #555;
}

.card-footer a {
  color: #3498db;
  text-decoration: none;
  font-weight: bold;
}

.card-footer a:hover {
  text-decoration: underline;
}

.back-home-link {
  display: inline-block;
  margin-top: 10px;
  color: #777;
}

/* 响应式调整 */
@media (max-width: 480px) {
  .login-card {
    padding: 25px 20px;
  }
  .card-header h2 {
    font-size: 1.6em;
  }
  .form-options {
    flex-direction: column;
    align-items: flex-start;
    gap: 8px; /* 在堆叠时增加一些间距 */
  }
}
</style>
