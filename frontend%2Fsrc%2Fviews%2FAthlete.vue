<template>
  <div>
    <el-card>
      <template #header><span style="font-weight:bold">运动员管理</span>
        <el-button type="primary" size="small" style="float:right" @click="openDialog()">新增运动员</el-button>
      </template>
      <el-table :data="tableData" border stripe>
        <el-table-column prop="athleteId" label="ID" width="60"/>
        <el-table-column prop="name" label="姓名" width="100"/>
        <el-table-column prop="gender" label="性别" width="60"/>
        <el-table-column prop="className" label="班级" width="120"/>
        <el-table-column prop="deptName" label="院系" width="140"/>
        <el-table-column prop="groupName" label="分组" width="80"/>
        <el-table-column prop="phone" label="电话" width="130"/>
        <el-table-column label="操作" width="160" fixed="right">
          <template #default="{row}">
            <el-button size="small" @click="openDialog(row)">编辑</el-button>
            <el-button size="small" type="danger" @click="del(row.athleteId)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination style="margin-top:16px" background layout="prev,pager,next" :total="total" v-model:current-page="page" @current-change="load"/>
    </el-card>

    <el-dialog v-model="dialogVisible" :title="isEdit?'编辑运动员':'新增运动员'" width="500px">
      <el-form :model="form" label-width="80px">
        <el-form-item label="姓名"><el-input v-model="form.name"/></el-form-item>
        <el-form-item label="性别"><el-select v-model="form.gender"><el-option label="男" value="M"/><el-option label="女" value="F"/></el-select></el-form-item>
        <el-form-item label="班级"><el-select v-model="form.classId" value-key="classId" placeholder="请选择班级"><el-option v-for="c in classes" :key="c.classId" :label="c.className+' ('+c.deptName+')'" :value="c.classId"/></el-select></el-form-item>
        <el-form-item label="分组"><el-select v-model="form.groupName"><el-option label="甲组" value="甲组"/><el-option label="乙组" value="乙组"/><el-option label="丙组" value="丙组"/></el-select></el-form-item>
        <el-form-item label="电话"><el-input v-model="form.phone"/></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible=false">取消</el-button><el-button type="primary" @click="save">保存</el-button></template>
    </el-dialog>
  </div>
</template>
<script setup>
import { ref, onMounted } from 'vue'
import api from '../api/index.js'
import { ElMessage, ElMessageBox } from 'element-plus'

const tableData = ref([]), total = ref(0), page = ref(1), dialogVisible = ref(false), isEdit = ref(false), classes = ref([])
const form = ref({ athleteId:null, name:'', gender:'M', classId:null, groupName:'甲组', phone:'' })

const load = async () => {
  const res = await api.get('/athlete', { params: { page: page.value, size: 20 } })
  if (res.code===200) { tableData.value = res.data.records; total.value = res.data.total }
}
const loadClasses = async () => {
  const res = await api.get('/class', { params: { page:1, size:999 } })
  if (res.code===200) { classes.value = res.data.records }
}
const openDialog = (row) => {
  if (row) { isEdit.value = true; form.value = { ...row } } else { isEdit.value = false; form.value = { athleteId:null, name:'', gender:'M', classId:null, groupName:'甲组', phone:'' } }
  dialogVisible.value = true
}
const save = async () => {
  if (isEdit.value) await api.put('/athlete', form.value)
  else await api.post('/athlete', form.value)
  ElMessage.success('保存成功'); dialogVisible.value = false; load()
}
const del = async (id) => {
  await ElMessageBox.confirm('确定删除？')
  await api.delete('/athlete/'+id); ElMessage.success('删除成功'); load()
}
onMounted(() => { load(); loadClasses() })
</script>
