<template>
  <div class="not-found-container">
    <h1>404</h1>
    <p>抱歉，您访问的页面不存在。</p>
    <el-button type="primary" @click="goHome">返回首页</el-button>
  </div>
</template>

<script>
export default {
  name: 'NotFound',
  methods: {
    goHome() {
      this.$router.push('/');
    }
  }
};
</script>

<style scoped>
.not-found-container {
  text-align: center;
  padding-top: 100px;
}
h1 {
  font-size: 60px;
}
</style>
