<template>
  <div>
    <el-row :gutter="16">
      <el-col :span="6" v-for="s in stats" :key="s.label">
        <el-card shadow="hover" style="text-align:center">
          <div style="font-size:14px;color:#999">{{ s.label }}</div>
          <div style="font-size:28px;font-weight:bold;margin:10px 0">{{ s.value }}</div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>
<script setup>
import { ref, onMounted } from 'vue'
import api from '../api/index.js'
const stats = ref([
  { label: '运动员总数', value: 0 },{ label: '比赛项目', value: 0 },{ label: '比赛场次', value: 0 },{ label: '已完成场次', value: 0 },{ label: '宣传文章', value: 0 }
])
onMounted(async () => {
  const res = await api.get('/dashboard')
  if (res.code===200) {
    stats.value[0].value = res.data.athleteCount
    stats.value[1].value = res.data.eventCount
    stats.value[2].value = res.data.sessionCount
    stats.value[3].value = res.data.finishedCount
    stats.value[4].value = res.data.articleCount
  }
})
</script>
