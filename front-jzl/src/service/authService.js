import axios from 'axios';

const apiClient = axios.create({
    baseURL: '/api',
    timeout: 10000,
});

/**
 * 【新增】获取图形验证码
 */
export const getCaptchaImage = () => {
    return apiClient.get('/verifications/captcha');
};

/**
 * 【修改】封装发送邮箱验证码的API
 * @param {object} sendData - 包含email, type, captchaKey, captchaCode的对象
 */
export const sendEmailCode = (sendData) => {
    return apiClient.post('/verifications/code', sendData);
};

/**
 * 封装用户注册的API
 * @param {object} registerData - 注册表单数据
 */
export const register = (registerData) => {
    return apiClient.post('/users/register', registerData);
};
