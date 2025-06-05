<template>
  <div class="auth-page">
    <div class="auth-container">
      <div class="auth-card">
        <div class="auth-header">
          <h1 class="auth-title">注册账号</h1>
          <p class="auth-subtitle">加入天津大学美食社区</p>
        </div>

        <form @submit.prevent="handleRegister" class="auth-form">
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
            <label for="email" class="form-label">邮箱</label>
            <div class="code-row">
              <input
                  type="email"
                  id="email"
                  v-model="email"
                  class="form-input"
                  placeholder="请输入邮箱"
                  required
              />
              <button
                  type="button"
                  class="code-btn"
                  @click="sendCode"
              >{{ codeButtonText }}</button>
            </div>
          </div>

          <div class="form-group">
            <label for="code" class="form-label">邮箱验证码</label>
            <input
                type="text"
                id="code"
                v-model="code"
                class="form-input"
                placeholder="请输入验证码"
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

          <div class="form-group">
            <label for="confirmPassword" class="form-label">确认密码</label>
            <input
                type="password"
                id="confirmPassword"
                v-model="confirmPassword"
                class="form-input"
                placeholder="请再次输入密码"
                required
            />
          </div>

          <div class="form-group">
            <label for="campus" class="form-label">所在校区</label>
            <select
                id="campus"
                v-model="campus"
                class="form-input"
                required
            >
              <option value="">请选择校区</option>
              <option value="北洋园校区">北洋园校区</option>
              <option value="卫津路校区">卫津路校区</option>
            </select>
          </div>

          <div class="form-agreement">
            <input type="checkbox" id="agreement" v-model="agreement" required />
            <label for="agreement">
              我已阅读并同意
              <a href="#" class="agreement-link">用户协议</a>
              和
              <a href="#" class="agreement-link">隐私政策</a>
            </label>
          </div>

          <div v-if="errorMessage" class="error-message">
            {{ errorMessage }}
          </div>

          <button type="submit" class="btn btn-primary auth-button">
            注册
          </button>
        </form>

        <div class="auth-footer">
          <p>已有账号? <router-link to="/login" class="auth-link">立即登录</router-link></p>
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
const email = ref('')
const code = ref('')
const password = ref('')
const confirmPassword = ref('')
const campus = ref('')
const agreement = ref(false)
const errorMessage = ref('')
const codeButtonText = ref('发送验证码')
let countdown = 0

function updateCountdown() {
  if (countdown > 0) {
    codeButtonText.value = `${countdown}s`
    setTimeout(() => {
      countdown--
      updateCountdown()
    }, 1000)
  } else {
    codeButtonText.value = '发送验证码'
  }
}

async function sendCode() {
  if (countdown > 0) return
  if (!email.value) {
    errorMessage.value = '请先填写邮箱'
    return
  }
  const ok = await authStore.sendRegisterEmail(email.value)
  if (ok) {
    countdown = 60
    updateCountdown()
  }
}

async function handleRegister() {
  // Reset error message
  errorMessage.value = ''

  // Validate inputs
  if (!username.value || !email.value || !password.value || !confirmPassword.value || !campus.value) {
    errorMessage.value = '请填写所有必填项'
    return
  }

  if (password.value !== confirmPassword.value) {
    errorMessage.value = '两次输入的密码不一致'
    return
  }

  if (!agreement.value) {
    errorMessage.value = '请阅读并同意用户协议和隐私政策'
    return
  }

  // Attempt registration
  const success = await authStore.register({
    username: username.value,
    email: email.value,
    password: password.value,
    code: code.value
  })

  if (success) {
    router.push('/login')
  } else {
    errorMessage.value = '注册失败，请稍后重试'
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

select.form-input {
  appearance: none;
  background-image: url("data:image/svg+xml,%3csvg xmlns='http://www.w3.org/2000/svg' fill='none' viewBox='0 0 20 20'%3e%3cpath stroke='%236b7280' stroke-linecap='round' stroke-linejoin='round' stroke-width='1.5' d='M6 8l4 4 4-4'/%3e%3c/svg%3e");
  background-position: right 0.5rem center;
  background-repeat: no-repeat;
  background-size: 1.5em 1.5em;
  padding-right: 2.5rem;
}

.form-agreement {
  display: flex;
  align-items: flex-start;
  gap: var(--space-2);
  margin-bottom: var(--space-4);
  font-size: 0.875rem;
}

.form-agreement input[type="checkbox"] {
  margin-top: 0.25rem;
}

.agreement-link {
  color: var(--color-primary-600);
}

.agreement-link:hover {
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

.code-row {
  display: flex;
  gap: var(--space-2);
}

.code-btn {
  white-space: nowrap;
  padding: var(--space-2) var(--space-3);
  font-size: 0.875rem;
}
</style>