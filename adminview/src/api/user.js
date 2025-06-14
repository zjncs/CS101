import request from '@/utils/request'

export function login(data) {
    // 注意：这里的 URL '/admin/login' 是示例
    // 请根据您的 Spring Boot 后端接口进行修改
    return request({
        url: '/admin/login',
        method: 'post',
        data
    })
}

export function getInfo(token) {
    // 注意：这里的 URL '/admin/info' 是示例
    return request({
        url: '/admin/info',
        method: 'get',
        params: { token }
    })
}

export function logout() {
    // 注意：这里的 URL '/admin/logout' 是示例
    return request({
        url: '/admin/logout',
        method: 'post'
    })
}

export function register(data) {
    // 注意：这里的 URL '/admin/register' 是示例
    return request({
        url: '/admin/register',
        method: 'post',
        data
    })
}
