<template>
  <div>
    <el-tabs>
      <el-tab-pane label="个人积分排名">
        <el-table :data="athleteRank" border stripe>
          <el-table-column type="index" label="排名" width="60"/>
          <el-table-column prop="athleteName" label="姓名" width="100"/>
          <el-table-column prop="gender" label="性别" width="60"/>
          <el-table-column prop="groupName" label="分组" width="80"/>
          <el-table-column prop="className" label="班级" width="120"/>
          <el-table-column prop="deptName" label="院系" width="160"/>
          <el-table-column prop="totalScore" label="总积分" width="100">
            <template #default="{row}"><el-tag type="warning">{{ row.totalScore }}</el-tag></template>
          </el-table-column>
          <el-table-column prop="eventCount" label="参赛次数" width="90"/>
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="院系积分排名">
        <el-table :data="deptRank" border stripe>
          <el-table-column type="index" label="排名" width="60"/>
          <el-table-column prop="deptName" label="院系" width="200"/>
          <el-table-column prop="totalScore" label="总积分" width="120">
            <template #default="{row}"><el-tag type="warning" size="large">{{ row.totalScore }}</el-tag></template>
          </el-table-column>
          <el-table-column prop="athleteCount" label="参赛人数" width="100"/>
          <el-table-column prop="participationCount" label="参赛人次" width="100"/>
        </el-table>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>
<script setup>
import { ref, onMounted } from 'vue'
import api from '../api/index.js'
const athleteRank = ref([]), deptRank = ref([])
onMounted(async () => {
  const [ra, rd] = await Promise.all([
    api.get('/ranking/athlete'), api.get('/ranking/dept')
  ])
  if (ra.code===200) athleteRank.value = ra.data
  if (rd.code===200) deptRank.value = rd.data
})
</script>
