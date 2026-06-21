<template>
  <div>
    <el-card>
      <template #header><span style="font-weight:bold">比赛项目管理</span>
        <el-button type="primary" size="small" style="float:right" @click="openDialog()">新增项目</el-button>
      </template>
      <el-table :data="tableData" border stripe>
        <el-table-column prop="eventId" label="ID" width="60"/>
        <el-table-column prop="eventName" label="项目名称" width="120"/>
        <el-table-column prop="eventType" label="类型" width="80"/>
        <el-table-column prop="genderGroup" label="性别组" width="90"/>
        <el-table-column prop="unit" label="单位" width="70"/>
        <el-table-column prop="record" label="校纪录" width="100"/>
        <el-table-column label="操作" width="160" fixed="right">
          <template #default="{row}"><el-button size="small" @click="openDialog(row)">编辑</el-button><el-button size="small" type="danger" @click="del(row.eventId)">删除</el-button></template>
        </el-table-column>
      </el-table>
      <el-pagination style="margin-top:16px" background layout="prev,pager,next" :total="total" v-model:current-page="page" @current-change="load"/>
    </el-card>
    <el-dialog v-model="dialogVisible" :title="isEdit?'编辑项目':'新增项目'" width="500px">
      <el-form :model="form" label-width="80px">
        <el-form-item label="名称"><el-input v-model="form.eventName"/></el-form-item>
        <el-form-item label="类型"><el-select v-model="form.eventType"><el-option label="径赛" value="径赛"/><el-option label="田赛" value="田赛"/></el-select></el-form-item>
        <el-form-item label="性别组"><el-select v-model="form.genderGroup"><el-option label="男子组" value="男子组"/><el-option label="女子组" value="女子组"/></el-select></el-form-item>
        <el-form-item label="单位"><el-input v-model="form.unit"/></el-form-item>
        <el-form-item label="校纪录"><el-input-number v-model="form.record" :precision="2"/></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible=false">取消</el-button><el-button type="primary" @click="save">保存</el-button></template>
    </el-dialog>
  </div>
</template>
<script setup>
import { ref, onMounted } from 'vue'
import api from '../api/index.js'
import { ElMessage, ElMessageBox } from 'element-plus'
const tableData = ref([]), total = ref(0), page = ref(1), dialogVisible = ref(false), isEdit = ref(false)
const form = ref({ eventId:null, eventName:'', eventType:'径赛', genderGroup:'男子组', unit:'秒', record:null })
const load = async () => { const res = await api.get('/event', { params: { page: page.value, size: 20 } }); if (res.code===200) { tableData.value = res.data.records; total.value = res.data.total } }
const openDialog = (row) => { if (row) { isEdit.value = true; form.value = { ...row } } else { isEdit.value = false; form.value = { eventId:null, eventName:'', eventType:'径赛', genderGroup:'男子组', unit:'秒', record:null } }; dialogVisible.value = true }
const save = async () => { if (isEdit.value) await api.put('/event', form.value); else await api.post('/event', form.value); ElMessage.success('保存成功'); dialogVisible.value = false; load() }
const del = async (id) => { await ElMessageBox.confirm('确定删除？'); await api.delete('/event/'+id); ElMessage.success('删除成功'); load() }
onMounted(load)
</script>
