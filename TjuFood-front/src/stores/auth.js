import { defineStore } from 'pinia'
import { ref } from 'vue'
import { postForm, get } from '../net'

export const useAuthStore = defineStore('auth', () => {
  const user = ref(null)
  const isAuthenticated = ref(false)

  async function login(username, password, remember = false) {
    try {
      await postForm('/api/auth/login', { username, password, remember })
      const info = await get('/api/user/me')
      user.value = info
      isAuthenticated.value = true
      return true
    } catch (e) {
      return false
    }
  }

  function logout() {
    postForm('/api/auth/logout', {})
    user.value = null
    isAuthenticated.value = false
  }

  async function register({ username, email, password, code }) {
    try {
      await postForm('/api/auth/register', {
        username,
        email,
        password,
        code
      })
      return true
    } catch (e) {
      return false
    }
  }

  async function sendRegisterEmail(email) {
    try {
      await postForm('/api/auth/valid-register-email', { email })
      return true
    } catch (e) {
      return false
    }
  }

  return { user, isAuthenticated, login, logout, register, sendRegisterEmail }
})
