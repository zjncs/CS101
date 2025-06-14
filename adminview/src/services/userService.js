/**
 * @description 用户相关的业务逻辑服务 (前端模拟版)
 * 在没有后端的情况下，这个文件将模拟API的返回，让您能正常进入系统。
 */
// import * as userApi from '@/api/user'; // 暂时注释掉真实的API调用
import { setToken, removeToken } from '@/utils/auth';

class UserService {
    /**
     * 模拟用户登录业务
     * @param {object} loginData - 包含 username 和 password
     * @returns {Promise<string>} 返回一个假的 token
     */
    async login(loginData) {
        console.log('正在执行模拟登录...', loginData);

        // 模拟后端延时
        await new Promise(resolve => setTimeout(resolve, 500));

        // ★ 核心修改：不再调用API，直接伪造一个成功的登录
        if (loginData.username === 'admin' && loginData.password === 'admin123') {
            const fakeToken = 'a-fake-super-admin-token';
            setToken(fakeToken); // 将假token存入Cookie，以便后续流程使用
            return Promise.resolve(fakeToken); // 返回一个成功的Promise
        } else {
            return Promise.reject(new Error('用户名或密码错误'));
        }
    }

    /**
     * 模拟获取当前登录管理员的信息
     * @returns {Promise<object>} 返回一个假的、拥有所有权限的用户信息
     */
    async getUserInfo() {
        console.log('正在执行模拟获取用户信息...');

        // 模拟后端延时
        await new Promise(resolve => setTimeout(resolve, 500));

        // ★ 核心修改：不再调用API，直接伪造一个超级管理员的用户信息
        // 这将确保所有菜单和路由都对您可见，方便开发。
        const mockUserInfo = {
            username: 'admin (开发模式)',
            icon: 'https://i.imgur.com/kYoN8mC.png', // 一个默认头像
            roles: ['系统管理员'] // 关键：赋予系统管理员角色
        };

        return Promise.resolve(mockUserInfo);
    }

    /**
     * 处理用户登出业务
     */
    async logout() {
        // 登出操作主要是清除前端的状态，可以保持不变
        // await userApi.logout();
        removeToken();
        return Promise.resolve();
    }

    /**
     * 模拟用户注册业务
     * @param {object} registerData - 注册信息
     */
    async register(registerData) {
        console.log('模拟注册:', registerData);
        return Promise.resolve();
    }
}

// 导出 UserService 的单例
export default new UserService();
