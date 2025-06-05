<template>
  <div>
    <img alt="Vue logo" src="../assets/logotju.png" />
    <el-container class="parent">
      <el-header >
        <el-row type="flex" justify="center">
          <el-row>
            <h1>标题</h1>
          </el-row>
        </el-row>
        <el-row>
          <MainMenu />
        </el-row>
      </el-header>
      <el-main class="child">
        <router-view />
      </el-main>
    </el-container>
  </div>
</template>

<script>
import MainMenu from '@/components/MainMenu.vue';
export default {
   components:{
    MainMenu
   },
   mounted(){
    this.$router.push("/main/list")
   }
}
</script>

<style scoped>
img{
  height: 10vh;
}
.parent {
  display: flex;
  flex-direction: column;
  /* 垂直方向 */
  height: 80vh;
  /* 父元素高度 */
  padding-top: 1px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04)
}

.child {
  flex: 1;
  /* 子元素将自动调整高度以填充剩余空间 */
  background-color: aliceblue;
  margin-top: 8vh;
  
}
</style>