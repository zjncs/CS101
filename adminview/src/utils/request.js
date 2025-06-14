import axios from 'axios'
import { MessageBox, Message } from 'element-ui'
import store from '@/store'
// import { getToken } from '@/utils/auth' // <-- 移除这行

const service = axios.create({
    baseURL: process.env.VUE_APP_BASE_API || '/api',
    timeout: 5000
})

service.interceptors.request.use(
    config => {
        // 直接从 store 中获取 token
        if (store.getters.token) {
            config.headers['Authorization'] = 'Bearer ' + store.getters.token
        }
        return config
    },
    error => {
        console.log(error)
        return Promise.reject(error)
    }
)

service.interceptors.response.use(
    response => {
        const res = response.data
        if (res.code !== 200) { // 请根据您的后端进行调整
            Message({
                message: res.message || 'Error',
                type: 'error',
                duration: 5 * 1000
            })
            if (res.code === 50008 || res.code === 50012 || res.code === 50014) {
                MessageBox.confirm('登录状态已过期，请重新登录', '确认登出', {
                    confirmButtonText: '重新登录',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    store.dispatch('user/resetToken').then(() => {
                        location.reload()
                    })
                })
            }
            return Promise.reject(new Error(res.message || 'Error'))
        } else {
            return res.data
        }
    },
    error => {
        console.log('err' + error)
        Message({
            message: error.message,
            type: 'error',
            duration: 5 * 1000
        })
        return Promise.reject(error)
    }
)

export default service
