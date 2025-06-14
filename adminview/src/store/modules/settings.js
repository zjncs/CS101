// 这是一个标准的 JavaScript 模块

const state = {
    showSettings: false, // 控制设置面板的显示
    fixedHeader: true,   // 控制头部导航是否固定
    sidebarLogo: true    // 控制侧边栏Logo是否显示
}

const mutations = {
    CHANGE_SETTING: (state, { key, value }) => {
        // 确保 key 存在于 state 中
        if (Object.prototype.hasOwnProperty.call(state, key)) {
            state[key] = value
        }
    }
}

const actions = {
    changeSetting({ commit }, data) {
        commit('CHANGE_SETTING', data)
    }
}

export default {
    namespaced: true,
    state,
    mutations,
    actions
}
