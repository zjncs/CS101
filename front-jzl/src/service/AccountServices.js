import http from '../service/Request'
const base = '/api/Account/'
export default {
    /**检查指定名称是否使用 */
    IsNameUsed(name) {
        return http.get(`${base}isNameUsed/${name}`)
    },
    /** 注册*/
    Register(model){
        return http.post(`${base}Register`,model)
    },
    /** 登录 */
    Login(model){
        return http.post(`${base}Login`,model)
    }

}