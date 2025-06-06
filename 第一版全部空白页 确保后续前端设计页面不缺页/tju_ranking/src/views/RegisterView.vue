<template>
  <div class="register-container">
    <div class="register-card">
      <header class="card-header">
        <!-- 建议在这里添加一个Logo图片 -->
        <!-- <img src="@/assets/logo-small.png" alt="天大点评" class="logo-small"> -->
        <h2>创建您的 天大点评 账户</h2>
        <p>加入我们，分享和发现美食！</p>
      </header>

      <form @submit.prevent="handleRegister" class="register-form">
        <div class="form-group">
          <label for="username">用户名</label>
          <input type="text" id="username" v-model="username" placeholder="请输入用户名" required>
        </div>
        <div class="form-group">
          <label for="email">邮箱</label>
          <input type="email" id="email" v-model="email" placeholder="请输入邮箱地址" required>
        </div>
        <div class="form-group">
          <label for="password">密码</label>
          <input type="password" id="password" v-model="password" placeholder="请输入密码 (至少6位)" required>
        </div>
        <div class="form-group">
          <label for="confirmPassword">确认密码</label>
          <input type="password" id="confirmPassword" v-model="confirmPassword" placeholder="请再次输入密码" required>
        </div>

        <!-- 可以在这里添加验证码输入框 -->
        <!-- <div class="form-group">
          <label for="captcha">验证码</label>
          <div class="captcha-wrapper">
            <input type="text" id="captcha" v-model="captcha" placeholder="请输入验证码" required>
            <img src="/api/captcha" alt="验证码" @click="refreshCaptcha" class="captcha-img">
          </div>
        </div> -->

        <div class="form-actions">
          <button type="submit" class="btn btn-primary">立即注册</button>
        </div>
      </form>

      <footer class="card-footer">
        <p>已有账户? <router-link to="/login">点此登录</router-link></p>
        <router-link to="/" class="back-home-link">返回首页</router-link>
      </footer>
    </div>
    <!-- 建议在页面背景添加一张与美食或校园相关的图片，并通过CSS设置 -->
  </div>
</template>

<script>
export default {
  name: 'RegisterView',
  data() {
    return {
      username: '',
      email: '',
      password: '',
      confirmPassword: '',
      // captcha: ''
    };
  },
  methods: {
    handleRegister() {
      if (this.password !== this.confirmPassword) {
        alert('两次输入的密码不一致！'); // 实际项目中建议使用更友好的提示组件
        return;
      }
      if (this.password.length < 6) {
        alert('密码长度至少为6位！');
        return;
      }
      // 在这里处理注册逻辑，例如发送API请求
      console.log('注册信息:', {
        username: this.username,
        email: this.email,
        password: this.password,
      });
      // 注册成功后跳转，例如跳转到登录页或首页
      // this.$router.push('/login');
      alert('注册请求已发送（模拟）');
    },
    // refreshCaptcha() {
    //   // 刷新验证码图片的逻辑
    //   const captchaImg = document.querySelector('.captcha-img');
    //   if (captchaImg) {
    //     captchaImg.src = '/api/captcha?' + new Date().getTime();
    //   }
    // }
  }
}
</script>

<style scoped>
.register-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background-color: #eef1f5; /* 淡雅的背景色 */
  /* 如果要添加背景图片，可以在这里设置:
  background-image: url('~@/assets/background.jpg');
  background-size: cover;
  background-position: center;
  */
  padding: 20px;
  box-sizing: border-box;
  font-family: 'Helvetica Neue', Arial, sans-serif;
}

.register-card {
  background-color: #ffffff;
  padding: 30px 40px;
  border-radius: 12px;
  box-shadow: 0 8px 25px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-width: 450px; /* 控制卡片最大宽度 */
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

.register-form .form-group {
  margin-bottom: 20px;
}

.register-form label {
  display: block;
  font-weight: bold;
  margin-bottom: 8px;
  color: #555;
  font-size: 0.95em;
}

.register-form input[type="text"],
.register-form input[type="email"],
.register-form input[type="password"] {
  width: 100%;
  padding: 12px 15px;
  border: 1px solid #ccc;
  border-radius: 6px;
  box-sizing: border-box;
  font-size: 1em;
  transition: border-color 0.3s ease;
}

.register-form input[type="text"]:focus,
.register-form input[type="email"]:focus,
.register-form input[type="password"]:focus {
  border-color: #3498db; /* 主题色 */
  outline: none;
  box-shadow: 0 0 0 2px rgba(52, 152, 219, 0.2);
}

/* .captcha-wrapper {
  display: flex;
  align-items: center;
}

.captcha-wrapper input {
  flex-grow: 1;
  margin-right: 10px;
}

.captcha-img {
  height: 40px;
  border-radius: 4px;
  cursor: pointer;
  border: 1px solid #ccc;
} */

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
  .register-card {
    padding: 25px 20px;
  }
  .card-header h2 {
    font-size: 1.6em;
  }
}
</style>
