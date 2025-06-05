<template>
  <div class="auth-page">
    <div class="auth-container">
      <div class="auth-card">
        <div class="auth-header">
          <h1 class="auth-title">登录</h1>
          <p class="auth-subtitle">欢迎回到天津大学美食社区</p>
        </div>

        <form @submit.prevent="handleLogin" class="auth-form">
          <div class="form-group">
            <label for="username" class="form-label">用户名</label>
            <input
                type="text"
                id="username"
                v-model="username"
                class="form-input"
                placeholder="请输入用户名"
                required
            />
          </div>

          <div class="form-group">
            <label for="password" class="form-label">密码</label>
            <input
                type="password"
                id="password"
                v-model="password"
                class="form-input"
                placeholder="请输入密码"
                required
            />
          </div>

          <div class="form-options">
            <div class="remember-me">
              <input type="checkbox" id="remember" v-model="remember" />
              <label for="remember">记住我</label>
            </div>
            <a href="#" class="forgot-password">忘记密码?</a>
          </div>

          <div v-if="errorMessage" class="error-message">
            {{ errorMessage }}
          </div>

          <button type="submit" class="btn btn-primary auth-button">
            登录
          </button>
        </form>

        <div class="auth-footer">
          <p>还没有账号? <router-link to="/register" class="auth-link">立即注册</router-link></p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '../../stores/auth'

const router = useRouter()
const authStore = useAuthStore()

const username = ref('')
const password = ref('')
const remember = ref(false)
const errorMessage = ref('')

async function handleLogin() {
  errorMessage.value = ''
  if (!username.value || !password.value) {
    errorMessage.value = '请输入用户名和密码'
    return
  }
  const success = await authStore.login(
    username.value,
    password.value,
    remember.value
  )
  if (success) {
    router.push('/')
  } else {
    errorMessage.value = '用户名或密码错误'
  }
}
</script>

<style scoped>
.auth-page {
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: calc(100vh - 60px);
  padding: var(--space-4);
}

.auth-container {
  width: 100%;
  max-width: 400px;
}

.auth-card {
  background-color: white;
  border-radius: var(--radius-lg);
  box-shadow: var(--shadow-lg);
  padding: var(--space-6);
}

.auth-header {
  text-align: center;
  margin-bottom: var(--space-6);
}

.auth-title {
  font-size: 1.5rem;
  font-weight: 700;
  color: var(--color-gray-800);
  margin-bottom: var(--space-2);
}

.auth-subtitle {
  color: var(--color-gray-600);
}

.auth-form {
  margin-bottom: var(--space-4);
}

.form-group {
  margin-bottom: var(--space-4);
}

.form-label {
  display: block;
  margin-bottom: var(--space-2);
  font-weight: 500;
  color: var(--color-gray-700);
}

.form-input {
  width: 100%;
  padding: var(--space-3);
  border: 1px solid var(--color-gray-300);
  border-radius: var(--radius-md);
  outline: none;
  transition: all var(--transition-fast);
}

.form-input:focus {
  border-color: var(--color-primary-400);
  box-shadow: 0 0 0 2px rgba(59, 130, 246, 0.2);
}

.form-options {
  display: flex;
  justify-content: space-between;
  margin-bottom: var(--space-4);
  font-size: 0.875rem;
}

.remember-me {
  display: flex;
  align-items: center;
}

.remember-me input {
  margin-right: var(--space-2);
}

.forgot-password {
  color: var(--color-primary-600);
}

.forgot-password:hover {
  text-decoration: underline;
}

.auth-button {
  width: 100%;
  padding: var(--space-3);
  font-size: 1rem;
}

.auth-footer {
  text-align: center;
  font-size: 0.875rem;
  color: var(--color-gray-600);
}

.auth-link {
  color: var(--color-primary-600);
  font-weight: 500;
}

.auth-link:hover {
  text-decoration: underline;
}

.error-message {
  color: var(--color-error-500);
  font-size: 0.875rem;
  margin-bottom: var(--space-4);
  padding: var(--space-2);
  background-color: rgba(239, 68, 68, 0.1);
  border-radius: var(--radius-md);
  text-align: center;
}
</style>