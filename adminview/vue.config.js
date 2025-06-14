const { defineConfig } = require('@vue/cli-service')
const path = require('path')

module.exports = defineConfig({
  transpileDependencies: true,
  configureWebpack: {
    resolve: {
      fallback: {
        "path": require.resolve("path-browserify")
      }
    }
  },
  // 可选：配置路径别名
  chainWebpack: config => {
    config.resolve.alias
        .set('@', path.join(__dirname, 'src'))
  }
})