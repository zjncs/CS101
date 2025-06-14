import Vue from 'vue'
import Vuex from 'vuex'
import getters from './getters' // 我们将 getters 分离到独立文件

Vue.use(Vuex)

// https://webpack.js.org/guides/dependency-management/#require-context
// 这段代码会自动导入所有在 ./modules 文件夹下的 .js 文件作为 vuex 模块
const modulesFiles = require.context('./modules', true, /\.js$/)

const modules = modulesFiles.keys().reduce((modules, modulePath) => {
  const moduleName = modulePath.replace(/^\.\/(.*)\.\w+$/, '$1')
  const value = modulesFiles(modulePath)
  modules[moduleName] = value.default
  return modules
}, {})

const store = new Vuex.Store({
  modules,
  getters
})

export default store
