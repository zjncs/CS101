<template>
  <div>
    <MainMenu />
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span>首页</span>
      </div>
      <el-row :gutter="20">
        <el-col :span="8">
          <el-button type="primary" @click="$router.push('/login')">去登录</el-button>
        </el-col>
        <el-col :span="8">
          <el-button type="success" @click="$router.push('/register')">去注册</el-button>
        </el-col>
        <el-col :span="8">
          <el-button type="info" @click="$router.push('/main')">主页面</el-button>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>

<script>
import MainMenu from '../components/MainMenu.vue'

export default {
  name: 'HomeView',
  components: {
    MainMenu
  }
}
</script>

<style scoped>
.box-card {
  width: 480px;
  margin: 20px auto;
}
</style>
