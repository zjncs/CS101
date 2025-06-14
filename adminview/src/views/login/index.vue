<template>
  <div class="login-container">
    <div class="login-left">
      <div class="login-left-logo">
        <img src="@/assets/imgs/logo.png" alt="logo" />
        <span>TjuFood 后台管理</span>
      </div>
      <div class="login-left-content">
        <img src="@/assets/svgs/login-box-bg.svg" alt="welcome" class="welcome-img" />
        <div class="welcome-title">欢迎使用</div>
        <div class="welcome-message">一个高效、便捷的校园美食管理平台</div>
      </div>
    </div>

    <div class="login-right">
      <div class="login-form-wrapper">
        <h2 class="form-title">管理员登录</h2>
        <el-form
            ref="loginForm"
            :model="loginData"
            :rules="loginRules"
            class="login-form-body"
            label-position="top"
            size="large"
        >
          <el-form-item prop="username">
            <el-input
                v-model="loginData.username"
                placeholder="管理员账号"
                prefix-icon="el-icon-user"
                @keyup.enter.native="handleLogin"
            />
          </el-form-item>

          <el-form-item prop="password">
            <el-input
                v-model="loginData.password"
                type="password"
                placeholder="密码"
                prefix-icon="el-icon-lock"
                show-password
                @keyup.enter.native="handleLogin"
            />
          </el-form-item>

          <el-form-item>
            <el-button
                :loading="loading"
                type="primary"
                style="width: 100%;"
                @click.native.prevent="handleLogin"
            >
              登 录
            </el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Login',
  data() {
    return {
      loading: false,
      loginData: {
        username: '',
        password: '',
      },
      loginRules: {
        username: [{ required: true, message: '管理员账号不能为空', trigger: 'blur' }],
        password: [{ required: true, message: '密码不能为空', trigger: 'blur' }]
      },
      redirect: undefined
    };
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect;
      },
      immediate: true
    }
  },
  methods: {
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true;
          this.$store.dispatch('user/login', this.loginData)
              .then(() => {
                this.$router.push({ path: this.redirect || '/' });
                this.loading = false;
              })
              .catch(() => {
                this.loading = false;
              });
        }
      });
    }
  }
};
</script>

<style scoped>
/* 样式与之前类似，但移除了不再需要的组件引用 */
.login-container {
  display: flex;
  width: 100%;
  height: 100vh;
  overflow: hidden;
}

.login-left {
  flex: 1;
  background-color: #304156;
  padding: 30px;
  position: relative;
  display: flex;
  flex-direction: column;
  background-image: url('~@/assets/svgs/login-bg.svg');
  background-position: center;
  background-repeat: no-repeat;
  background-size: cover;
  color: white;
}

.login-left-logo { display: flex; align-items: center; }
.login-left-logo img { width: 48px; height: 48px; margin-right: 10px; }
.login-left-logo span { font-size: 20px; font-weight: bold; }
.login-left-content { flex: 1; display: flex; flex-direction: column; justify-content: center; align-items: center; text-align: center; }
.welcome-img { width: 350px; margin-bottom: 20px; }
.welcome-title { font-size: 32px; font-weight: bold; }
.welcome-message { margin-top: 10px; font-size: 14px; }

.login-right {
  flex: 1;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #fff;
}
.login-form-wrapper { width: 350px; }
.form-title { font-size: 24px; font-weight: bold; text-align: center; margin-bottom: 24px; }
</style>

