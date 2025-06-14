<template>
  <div style="padding: 0 15px;" @click="toggleClick">
    <i :class="isActive ? 'el-icon-s-fold' : 'el-icon-s-unfold'" style="font-size: 20px; vertical-align: middle;"></i>
  </div>
</template>
<script>
export default {
  name: 'Hamburger',
  props: {
    isActive: { type: Boolean, default: false }
  },
  methods: {
    toggleClick() { this.$emit('toggleClick') }
  }
}
</script>
