import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    token: '',
    username: ''
  },
  mutations: {
    setToken(state, { token, username }) {
      state.token = token
      state.username = username
    }
  },
  actions: {},
  modules: {}
})
