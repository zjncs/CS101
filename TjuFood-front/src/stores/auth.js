import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useAuthStore = defineStore('auth', () => {
    const user = ref(null)
    const isAuthenticated = ref(false)

    // Mock user data for demo purposes
    const mockUsers = [
        {
            id: 1,
            username: 'user1',
            password: 'password',
            nickname: '美食达人',
            avatar: 'https://images.pexels.com/photos/220453/pexels-photo-220453.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2',
            credits: 520,
            level: 3,
            campus: '北洋园校区'
        }
    ]

    function login(username, password) {
        const foundUser = mockUsers.find(u => u.username === username && u.password === password)

        if (foundUser) {
            // Remove password from user object
            const { password, ...userData } = foundUser
            user.value = userData
            isAuthenticated.value = true
            return true
        }

        return false
    }

    function logout() {
        user.value = null
        isAuthenticated.value = false
    }

    function register(userData) {
        // In a real app, this would send a request to the backend
        // For demo purposes, just simulate success
        return true
    }

    return {
        user,
        isAuthenticated,
        login,
        logout,
        register
    }
})