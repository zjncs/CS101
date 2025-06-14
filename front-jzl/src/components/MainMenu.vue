<template>
  <el-menu mode="horizontal" :router="true" class="main-menu">
    <el-menu-item index="/">
      <img src="../assets/logotju.png" alt="Logo" class="logo">
    </el-menu-item>
    <el-menu-item index="/">主页</el-menu-item>
    <el-menu-item index="/about">关于</el-menu-item>
    <el-menu-item index="/canteens">食堂</el-menu-item>
    <el-menu-item index="/windows">窗口</el-menu-item>
    <el-menu-item index="/dishes">菜品</el-menu-item>
    <el-menu-item index="/user/profile">个人账户</el-menu-item>
  </el-menu>
</template>

<style scoped>
.main-menu {
  display: flex;
  align-items: center;
}
.logo {
  height: 40px;
  margin-right: 10px;
}
</style>
