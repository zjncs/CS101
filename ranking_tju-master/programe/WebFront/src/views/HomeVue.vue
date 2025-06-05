<template>
    <div class="home">
      <img alt="Vue logo" src="../assets/logotju.png" />
      <HelloWorld msg="Welcome to Your Vue.js App" />
      <el-divider></el-divider>
      <el-row id="btns">
        <router-link to="/login" class="link">
          <el-button type="primary" round>登录</el-button>
        </router-link>
        <router-link to="/register" class="link">
          <el-button type="info" round>注册</el-button>
        </router-link>
      </el-row>
    </div>
  </template>
  
  <script>
  // @ is an alias to /src
  import HelloWorld from "@/components/HelloWorld.vue";
  
  export default {
    name: "HomeView",
    components: {
      HelloWorld,
    },
  };
  </script>
  <style scoped>
  #btns {
    margin-top: 300px;
  }
  .link{
    margin-left: 2em;
  }
  </style>
  