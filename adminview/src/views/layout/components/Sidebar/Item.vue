<template>
  <div class="menu-item-content">
    <i v-if="icon" :class="icon" />
    <span v-if="title" slot="title">{{ title }}</span>
  </div>
</template>

<script>
export default {
  name: 'MenuItem',
  functional: true, // 作为函数式组件，性能更佳
  props: {
    icon: { type: String, default: '' },
    title: { type: String, default: '' }
  },
  render(h, context) {
    const { icon, title } = context.props
    const vnodes = []

    if (icon) {
      vnodes.push(h('i', { class: [icon, 'sub-el-icon'] }))
    }

    if (title) {
      vnodes.push(h('span', { slot: 'title' }, title))
    }
    return vnodes
  }
}
</script>

<style scoped>
.sub-el-icon {
  color: currentColor;
  width: 1em;
  height: 1em;
}
</style>