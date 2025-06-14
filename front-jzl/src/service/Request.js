import axios from 'axios';
import store from '@/store'
// 创建axios实例
const service = axios.create({
    baseURL: process.env.VUE_APP_BASE_API, // api的base_url
    timeout: 5000 // 请求超时时间
});

// 请求拦截器
service.interceptors.request.use(
    config => {
        // 可以在这里添加请求头等信息
        // 如果token存在，则添加到请求头中  
        if (store.getters.token) {
            config.headers['Authorization'] = `Bearer ${store.getters.token}`;
        }
        return config;
    },
    error => {
        // 请求错误处理
        // 对请求错误做些什么  
        console.error('请求错误：', error); // for debug  
        Promise.reject(error);
    }
);

// 响应拦截器
service.interceptors.response.use(
    response => {
        // 对响应数据做处理，例如只返回data部分
        const res = response.data;
        // 根据返回的状态码做相应处理，例如401未授权等
        return res;
    },
    error => {
        // 响应错误处理
        console.log('err' + error); // for debug
        return Promise.reject(error);
    }
);

export default service;