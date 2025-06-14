const { defineConfig } = require('@vue/cli-service');

module.exports = defineConfig({
  transpileDependencies: true,

  devServer: {
    proxy: {
      // 当请求路径以 /api 开头时，触发此代理
      '/api': {
        target: 'http://localhost:8090', // 您的后端服务器地址
        changeOrigin: true, // 必须设置为true，以解决跨域问题

        // 新增路径重写(pathRewrite)规则
        // 它的作用是：在将请求转发给后端之前，把请求路径中的'/api'部分去掉。
        // 例如：前端请求 /api/users/login -> 代理转发到 http://localhost:8090/users/login
        // 如果你的后端@RequestMapping已经包含了/api，就不需要这个重写。
        // 但根据你的Controller代码，你的后端路径是/api/verifications，所以我们需要保持这个配置。
        // 为了保险起见，我们通常会加上这个。
        // pathRewrite: {
        //   '^/api': '' // 如果后端路径不带/api，则取消此行注释
        // }
      }
    }
  }
});
