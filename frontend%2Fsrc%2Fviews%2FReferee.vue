<template>
  <div>
    <el-card>
      <template #header><span style="font-weight:bold">裁判管理</span>
        <el-button type="primary" size="small" style="float:right" @click="openRefereeDialog()">新增裁判</el-button>
      </template>
      <el-table :data="referees" border stripe>
        <el-table-column prop="refereeId" label="ID" width="60"/>
        <el-table-column prop="name" label="姓名" width="100"/>
        <el-table-column prop="role" label="角色" width="100"/>
        <el-table-column label="操作" width="160" fixed="right">
          <template #default="{row}"><el-button size="small" @click="openRefereeDialog(row)">编辑</el-button><el-button size="small" type="danger" @click="delReferee(row.refereeId)">删除</el-button></template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-card style="margin-top:16px">
      <template #header><span style="font-weight:bold">裁判分配</span>
        <el-button type="success" size="small" style="float:right" @click="openAssignDialog()">新增分配</el-button>
      </template>
      <el-table :data="assignments" border stripe>
        <el-table-column prop="assignmentId" label="ID" width="60"/>
        <el-table-column prop="refereeName" label="裁判" width="100"/>
        <el-table-column prop="sessionName" label="场次" width="180"/>
        <el-table-column prop="duty" label="职责" width="100"/>
        <el-table-column label="操作" width="80"><template #default="{row}">
          <el-button size="small" type="danger" @click="delAssign(row.assignmentId)">删除</el-button></template></el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="refereeDialog" :title="refereeEdit?'编辑裁判':'新增裁判'" width="400px">
      <el-form :model="refereeForm" label-width="80px">
        <el-form-item label="姓名"><el-input v-model="refereeForm.name"/></el-form-item>
        <el-form-item label="角色"><el-select v-model="refereeForm.role"><el-option label="裁判长" value="裁判长"/><el-option label="裁判员" value="裁判员"/><el-option label="计时员" value="计时员"/><el-option label="发令员" value="发令员"/></el-select></el-form-item>
      </el-form>
      <template #footer><el-button @click="refereeDialog=false">取消</el-button><el-button type="primary" @click="saveReferee">保存</el-button></template>
    </el-dialog>

    <el-dialog v-model="assignDialog" title="新增分配" width="400px">
      <el-form :model="assignForm" label-width="80px">
        <el-form-item label="裁判"><el-select v-model="assignForm.refereeId"><el-option v-for="r in referees" :key="r.refereeId" :label="r.name" :value="r.refereeId"/></el-select></el-form-item>
        <el-form-item label="场次"><el-select v-model="assignForm.sessionId"><el-option v-for="s in sessions" :key="s.sessionId" :label="s.sessionName" :value="s.sessionId"/></el-select></el-form-item>
        <el-form-item label="职责"><el-input v-model="assignForm.duty"/></el-form-item>
      </el-form>
      <template #footer><el-button @click="assignDialog=false">取消</el-button><el-button type="primary" @click="saveAssign">保存</el-button></template>
    </el-dialog>
  </div>
</template>
<script setup>
import { ref, onMounted } from 'vue'
import api from '../api/index.js'
import { ElMessage, ElMessageBox } from 'element-plus'
const referees = ref([]), assignments = ref([]), sessions = ref([]),
  refereeDialog = ref(false), refereeEdit = ref(false),
  refereeForm = ref({ refereeId:null, name:'', role:'裁判员' }),
  assignDialog = ref(false), assignForm = ref({ refereeId:null, sessionId:null, duty:'' })

const load = async () => {
  const [rr, ra, rs] = await Promise.all([
    api.get('/referee', { params: { page:1, size:999 } }),
    api.get('/refereeAssignment', { params: { page:1, size:999 } }),
    api.get('/matchSession', { params: { page:1, size:999 } })
  ])
  if (rr.code===200) referees.value = rr.data.records
  if (ra.code===200) assignments.value = ra.data.records.map(a => ({ ...a, refereeName: (rr.data.records.find(r=>r.refereeId===a.refereeId)||{}).name, sessionName: (rs.data.records.find(s=>s.sessionId===a.sessionId)||{}).sessionName }))
  if (rs.code===200) sessions.value = rs.data.records
}
const openRefereeDialog = (row) => { if (row) { refereeEdit.value = true; refereeForm.value = { ...row } } else { refereeEdit.value = false; refereeForm.value = { refereeId:null, name:'', role:'裁判员' } }; refereeDialog.value = true }
const saveReferee = async () => { if (refereeEdit.value) await api.put('/referee', refereeForm.value); else await api.post('/referee', refereeForm.value); ElMessage.success('保存成功'); refereeDialog.value = false; load() }
const delReferee = async (id) => { await ElMessageBox.confirm('确定删除？'); await api.delete('/referee/'+id); ElMessage.success('删除成功'); load() }
const openAssignDialog = () => { assignForm.value = { refereeId:null, sessionId:null, duty:'' }; assignDialog.value = true }
const saveAssign = async () => { await api.post('/refereeAssignment', assignForm.value); ElMessage.success('添加成功'); assignDialog.value = false; load() }
const delAssign = async (id) => { await ElMessageBox.confirm('确定删除？'); await api.delete('/refereeAssignment/'+id); ElMessage.success('删除成功'); load() }
onMounted(load)
</script>
