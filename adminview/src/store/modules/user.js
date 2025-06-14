import userService from '@/services/userService';
import { getToken, setToken, removeToken } from '@/utils/auth';
import { resetRouter } from '@/router';

const state = {
    token: getToken(),
    name: '',
    avatar: 'https://i.imgur.com/kYoN8mC.png', // 提供一个默认头像
    roles: []
};

const mutations = {
    SET_TOKEN: (state, token) => {
        state.token = token;
    },
    SET_NAME: (state, name) => {
        state.name = name;
    },
    SET_AVATAR: (state, avatar) => {
        state.avatar = avatar;
    },
    SET_ROLES: (state, roles) => {
        state.roles = roles;
    }
};

const actions = {
    // 用户登录
    login({ commit }, loginData) {
        return new Promise((resolve, reject) => {
            userService.login(loginData).then(token => {
                commit('SET_TOKEN', token);
                setToken(token); // 存入 Cookie
                resolve();
            }).catch(error => {
                reject(error);
            });
        });
    },

    // 获取用户信息
    getInfo({ commit, state }) {
        return new Promise((resolve, reject) => {
            userService.getUserInfo().then(userInfo => {
                // 假设后端返回的数据结构为 { roles: [...], username: '...', icon: '...' }
                const { roles, username, icon } = userInfo;

                if (!roles || roles.length <= 0) {
                    reject('getInfo: roles must be a non-null array!')
                }

                commit('SET_ROLES', roles);
                commit('SET_NAME', username);
                commit('SET_AVATAR', icon || state.avatar); // 如果后端提供了头像则使用，否则用默认
                resolve({ roles });
            }).catch(error => {
                reject(error);
            });
        });
    },

    // 用户登出
    logout({ commit }) {
        return new Promise((resolve) => {
            userService.logout().finally(() => {
                commit('SET_TOKEN', '');
                commit('SET_ROLES', []);
                removeToken(); // 从 Cookie 中移除 token
                resetRouter();
                resolve();
            });
        });
    },

    // 用户注册 (移除未使用的 commit, 使用 context 占位)
    register(context, registerData) {
        return new Promise((resolve, reject) => {
            userService.register(registerData).then(() => {
                resolve();
            }).catch(error => {
                reject(error);
            });
        });
    },

    // 重置 Token
    resetToken({ commit }) {
        return new Promise(resolve => {
            commit('SET_TOKEN', '');
            commit('SET_ROLES', []);
            removeToken();
            resolve();
        });
    }
};

export default {
    namespaced: true,
    state,
    mutations,
    actions
};
