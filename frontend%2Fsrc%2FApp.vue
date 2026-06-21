<template>
  <el-container style="min-height:100vh">
    <el-aside width="220px" style="background:#1d1e1f;color:#fff">
      <div style="padding:20px;text-align:center;font-size:18px;font-weight:bold;border-bottom:1px solid #333">
        <el-icon><Trophy /></el-icon> 校运动会管理系统
      </div>
      <el-menu :default-active="activeMenu" router background-color="#1d1e1f" text-color="#ccc" active-text-color="#409EFF" style="border:none">
        <el-menu-item index="/"><el-icon><DataAnalysis /></el-icon> 仪表盘</el-menu-item>
        <el-menu-item index="/athlete"><el-icon><User /></el-icon> 运动员管理</el-menu-item>
        <el-menu-item index="/event"><el-icon><List /></el-icon> 比赛项目</el-menu-item>
        <el-menu-item index="/match"><el-icon><Timer /></el-icon> 比赛场次</el-menu-item>
        <el-menu-item index="/referee"><el-icon><Avatar /></el-icon> 裁判管理</el-menu-item>
        <el-menu-item index="/logistics"><el-icon><Box /></el-icon> 后勤管理</el-menu-item>
        <el-menu-item index="/article"><el-icon><Document /></el-icon> 宣传管理</el-menu-item>
        <el-menu-item index="/ranking"><el-icon><Medal /></el-icon> 积分排名</el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header style="background:#fff;border-bottom:1px solid #e8e8e8;display:flex;align-items:center;padding:0 24px;font-size:16px;font-weight:bold">
        {{ pageTitle }}
      </el-header>
      <el-main style="background:#f0f2f5;padding:20px">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()
const activeMenu = computed(() => route.path)

const titles = {
  '/': '仪表盘',
  '/athlete': '运动员管理',
  '/event': '比赛项目管理',
  '/match': '比赛场次管理',
  '/referee': '裁判管理',
  '/logistics': '后勤管理',
  '/article': '宣传管理',
  '/ranking': '积分排名',
}
const pageTitle = computed(() => titles[route.path] || '校运动会管理系统')
</script>
