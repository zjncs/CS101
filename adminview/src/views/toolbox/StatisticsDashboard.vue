<template>
  <div class="app-container">
    <el-row :gutter="20" class="panel-group">
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel"><div class="card-panel-icon-wrapper icon-people"><i class="el-icon-user-solid card-panel-icon"></i></div><div class="card-panel-description"><div class="card-panel-text">总用户数</div><span class="card-panel-num">102,400</span></div></div>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel"><div class="card-panel-icon-wrapper icon-message"><i class="el-icon-s-comment card-panel-icon"></i></div><div class="card-panel-description"><div class="card-panel-text">日均评论</div><span class="card-panel-num">5,820</span></div></div>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel"><div class="card-panel-icon-wrapper icon-money"><i class="el-icon-food card-panel-icon"></i></div><div class="card-panel-description"><div class="card-panel-text">菜品总数</div><span class="card-panel-num">9,280</span></div></div>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel"><div class="card-panel-icon-wrapper icon-shopping"><i class="el-icon-s-shop card-panel-icon"></i></div><div class="card-panel-description"><div class="card-panel-text">食堂总数</div><span class="card-panel-num">13</span></div></div>
      </el-col>
    </el-row>
    <el-row style="background:#fff;padding:16px 16px 0;margin-bottom:20px;"><div ref="lineChart" style="height: 400px; width: 100%;"></div></el-row>
    <el-row :gutter="20"><el-col :span="10"><el-card><div slot="header"><span>热门菜品排行榜 TOP 10</span></div><el-table :data="dishRank" height="450"><el-table-column prop="rank" label="排名" width="60" align="center"></el-table-column><el-table-column prop="name" label="菜品名称"></el-table-column><el-table-column prop="canteen" label="所属食堂"></el-table-column><el-table-column prop="score" label="评分" width="80" align="center"></el-table-column></el-table></el-card></el-col><el-col :span="14"><el-card><div ref="pieChart" style="height: 492px;"></div></el-card></el-col></el-row>
  </div>
</template>
<script>
import * as echarts from 'echarts';
export default {
  name: 'StatisticsDashboard',
  data() { return { lineChart: null, pieChart: null, dishRank: [ { rank: 1, name: '油泼面', canteen: '学一食堂', score: 4.9 }, { rank: 2, name: '自选香锅', canteen: '学一食堂', score: 4.6 }, { rank: 3, name: '黄焖鸡米饭', canteen: '学二食堂', score: 4.5 }, { rank: 4, name: '牛肉拉面', canteen: '清真食堂', score: 4.5 }, { rank: 5, name: '猪脚饭', canteen: '留学生食堂', score: 4.4 } ] } },
  mounted() { this.$nextTick(() => { this.initLineChart(); this.initPieChart(); }) },
  beforeDestroy() { if (this.lineChart) { this.lineChart.dispose(); this.lineChart = null; } if (this.pieChart) { this.pieChart.dispose(); this.pieChart = null; } },
  methods: { initLineChart() { this.lineChart = echarts.init(this.$refs.lineChart); this.lineChart.setOption({ title: { text: '近30日用户发帖/评论趋势' }, tooltip: { trigger: 'axis' }, legend: { data: ['发帖数', '评论数'] }, grid: { left: '3%', right: '4%', bottom: '3%', containLabel: true }, xAxis: { type: 'category', boundaryGap: false, data: Array.from({length: 30}, (v, i) => `5-${i+1}`) }, yAxis: { type: 'value' }, series: [ { name: '发帖数', type: 'line', smooth: true, data: Array.from({length: 30}, () => Math.floor(Math.random()*200 + 50)) }, { name: '评论数', type: 'line', smooth: true, data: Array.from({length: 30}, () => Math.floor(Math.random()*1000 + 300)) } ] }); }, initPieChart() { this.pieChart = echarts.init(this.$refs.pieChart); this.pieChart.setOption({ title: { text: '各食堂评价数占比', left: 'center' }, tooltip: { trigger: 'item', formatter: '{a} <br/>{b} : {c} ({d}%)' }, legend: { orient: 'vertical', left: 'left' }, series: [{ name: '评价数', type: 'pie', radius: '65%', center: ['50%', '60%'], data: [ { value: 1048, name: '学一食堂' }, { value: 735, name: '学二食堂' }, { value: 950, name: '留学生食堂' } ], emphasis: { itemStyle: { shadowBlur: 10, shadowOffsetX: 0, shadowColor: 'rgba(0, 0, 0, 0.5)' } } }] }); } }
}
</script>
<style lang="scss" scoped>
.app-container { padding: 20px; background-color: #f0f2f5; } .panel-group { .card-panel-col { margin-bottom: 20px; } .card-panel { height: 108px; cursor: pointer; position: relative; overflow: hidden; color: #666; background: #fff; box-shadow: 4px 4px 40px rgba(0, 0, 0, .05); border-color: rgba(0, 0, 0, .05); .card-panel-icon-wrapper { float: left; margin: 14px 0 0 14px; padding: 16px; transition: all 0.38s ease-out; border-radius: 6px; } .card-panel-icon { float: left; font-size: 48px; } .card-panel-description { float: right; font-weight: bold; margin: 26px; margin-left: 0px; .card-panel-text { line-height: 18px; color: rgba(0, 0, 0, 0.45); font-size: 16px; margin-bottom: 12px; } .card-panel-num { font-size: 20px; } } } }
</style>
