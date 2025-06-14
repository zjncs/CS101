<template>
  <div class="app-container">
    <el-card>
      <div slot="header">
        <span>在线帮助文档</span>
        <el-button v-if="canEdit" style="float: right; padding: 3px 0" type="text" @click="editing = !editing">{{ editing ? '完成' : '编辑' }}</el-button>
      </div>
      <div v-if="!editing" v-html="content" class="doc-content"></div>
      <el-input v-else type="textarea" :rows="20" v-model="content"></el-input>
    </el-card>
  </div>
</template>
<script>
export default { name: 'HelpDoc', data(){ return { editing: false, content: '<h1>帮助文档标题</h1><p>这里是帮助内容...</p>' } }, computed: { canEdit(){ return this.$store.getters.roles.includes('系统管理员') } } }
</script>
