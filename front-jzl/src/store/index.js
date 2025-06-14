import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    token: null, // 存储token
    username: null //存储用户名  
  },
  getters: {
    token: state => state.token,
    username: state => state.username,
    isLoggedIn: state => state.token!== null && state.username!== null
  },
  mutations: {
    setToken(state, {token, username}) {
      state.token = token
      state.username = username
    },
    removeToken(state) {
      state.token = null
      state.username = null
    },
  },
  actions: {
    login({ commit }, token, username) {
      commit('setToken', token, username);
    },
    logout({ commit }) {
      commit('removeToken');
    }
  },
  modules: {
  }
})
