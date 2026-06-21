<template>
  <div>
    <el-card>
      <template #header><span style="font-weight:bold">宣传管理</span>
        <el-button type="primary" size="small" style="float:right" @click="openDialog()">新增文章</el-button>
      </template>
      <el-table :data="tableData" border stripe>
        <el-table-column prop="articleId" label="ID" width="60"/>
        <el-table-column prop="title" label="标题" min-width="200"/>
        <el-table-column prop="authorName" label="作者" width="100"/>
        <el-table-column prop="deptName" label="院系" width="140"/>
        <el-table-column prop="publishTime" label="发布时间" width="170"/>
        <el-table-column label="操作" width="160" fixed="right">
          <template #default="{row}"><el-button size="small" @click="openDialog(row)">编辑</el-button><el-button size="small" type="danger" @click="del(row.articleId)">删除</el-button></template>
        </el-table-column>
      </el-table>
      <el-pagination style="margin-top:16px" background layout="prev,pager,next" :total="total" v-model:current-page="page" @current-change="load"/>
    </el-card>
    <el-dialog v-model="dialogVisible" :title="isEdit?'编辑文章':'新增文章'" width="700px">
      <el-form :model="form" label-width="80px">
        <el-form-item label="标题"><el-input v-model="form.title"/></el-form-item>
        <el-form-item label="作者"><el-input v-model="form.authorName"/></el-form-item>
        <el-form-item label="院系"><el-select v-model="form.deptId" placeholder="请选择"><el-option v-for="d in depts" :key="d.deptId" :label="d.deptName" :value="d.deptId"/></el-select></el-form-item>
        <el-form-item label="内容"><el-input v-model="form.content" type="textarea" :rows="6"/></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible=false">取消</el-button><el-button type="primary" @click="save">保存</el-button></template>
    </el-dialog>
  </div>
</template>
<script setup>
import { ref, onMounted } from 'vue'
import api from '../api/index.js'
import { ElMessage, ElMessageBox } from 'element-plus'
const tableData = ref([]), total = ref(0), page = ref(1), dialogVisible = ref(false), isEdit = ref(false), depts = ref([])
const form = ref({ articleId:null, title:'', content:'', authorName:'', deptId:null })
const load = async () => { const res = await api.get('/article', { params: { page: page.value, size: 20 } }); if (res.code===200) { tableData.value = res.data.records; total.value = res.data.total } }
const openDialog = (row) => { if (row) { isEdit.value = true; form.value = { ...row } } else { isEdit.value = false; form.value = { articleId:null, title:'', content:'', authorName:'', deptId:null } }; dialogVisible.value = true }
const save = async () => { if (isEdit.value) await api.put('/article', form.value); else await api.post('/article', form.value); ElMessage.success('保存成功'); dialogVisible.value = false; load() }
const del = async (id) => { await ElMessageBox.confirm('确定删除？'); await api.delete('/article/'+id); ElMessage.success('删除成功'); load() }
onMounted(async () => { load(); const r = await api.get('/department', { params: { page:1, size:999 } }); if (r.code===200) depts.value = r.data.records })
</script>
