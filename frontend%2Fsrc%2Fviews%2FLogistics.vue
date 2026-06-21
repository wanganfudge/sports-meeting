<template>
  <div>
    <el-tabs>
      <el-tab-pane label="物品管理">
        <el-button type="primary" size="small" style="margin-bottom:12px" @click="openSupplyDialog()">新增物品</el-button>
        <el-table :data="supplies" border stripe>
          <el-table-column prop="supplyId" label="ID" width="60"/>
          <el-table-column prop="name" label="物品名称" width="150"/>
          <el-table-column prop="quantity" label="数量" width="100"/>
          <el-table-column prop="unit" label="单位" width="80"/>
          <el-table-column label="操作"><template #default="{row}"><el-button size="small" @click="openSupplyDialog(row)">编辑</el-button><el-button size="small" type="danger" @click="delSupply(row.supplyId)">删除</el-button></template></el-table-column>
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="人员管理">
        <el-button type="primary" size="small" style="margin-bottom:12px" @click="openStaffDialog()">新增人员</el-button>
        <el-table :data="staffs" border stripe>
          <el-table-column prop="staffId" label="ID" width="60"/>
          <el-table-column prop="name" label="姓名" width="120"/>
          <el-table-column prop="phone" label="电话" width="150"/>
          <el-table-column label="操作"><template #default="{row}"><el-button size="small" @click="openStaffDialog(row)">编辑</el-button><el-button size="small" type="danger" @click="delStaff(row.staffId)">删除</el-button></template></el-table-column>
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="值班管理">
        <el-button type="primary" size="small" style="margin-bottom:12px" @click="openDutyDialog()">新增值班</el-button>
        <el-table :data="duties" border stripe>
          <el-table-column prop="dutyId" label="ID" width="60"/>
          <el-table-column prop="staffName" label="人员" width="100"/>
          <el-table-column prop="dutyDate" label="日期" width="110"/>
          <el-table-column prop="startTime" label="开始" width="90"/>
          <el-table-column prop="endTime" label="结束" width="90"/>
          <el-table-column prop="location" label="地点" width="120"/>
          <el-table-column label="操作"><template #default="{row}"><el-button size="small" @click="openDutyDialog(row)">编辑</el-button><el-button size="small" type="danger" @click="delDuty(row.dutyId)">删除</el-button></template></el-table-column>
        </el-table>
      </el-tab-pane>
    </el-tabs>

    <el-dialog v-model="supplyDialog" :title="supplyEdit?'编辑物品':'新增物品'" width="400px">
      <el-form :model="supplyForm" label-width="80px">
        <el-form-item label="名称"><el-input v-model="supplyForm.name"/></el-form-item>
        <el-form-item label="数量"><el-input-number v-model="supplyForm.quantity"/></el-form-item>
        <el-form-item label="单位"><el-input v-model="supplyForm.unit"/></el-form-item>
      </el-form>
      <template #footer><el-button @click="supplyDialog=false">取消</el-button><el-button type="primary" @click="saveSupply">保存</el-button></template>
    </el-dialog>

    <el-dialog v-model="staffDialog" :title="staffEdit?'编辑人员':'新增人员'" width="400px">
      <el-form :model="staffForm" label-width="80px">
        <el-form-item label="姓名"><el-input v-model="staffForm.name"/></el-form-item>
        <el-form-item label="电话"><el-input v-model="staffForm.phone"/></el-form-item>
      </el-form>
      <template #footer><el-button @click="staffDialog=false">取消</el-button><el-button type="primary" @click="saveStaff">保存</el-button></template>
    </el-dialog>

    <el-dialog v-model="dutyDialog" :title="dutyEdit?'编辑值班':'新增值班'" width="400px">
      <el-form :model="dutyForm" label-width="80px">
        <el-form-item label="人员"><el-select v-model="dutyForm.staffId"><el-option v-for="s in staffs" :key="s.staffId" :label="s.name" :value="s.staffId"/></el-select></el-form-item>
        <el-form-item label="日期"><el-date-picker v-model="dutyForm.dutyDate" type="date" value-format="YYYY-MM-DD"/></el-form-item>
        <el-form-item label="开始"><el-time-picker v-model="dutyForm.startTime" value-format="HH:mm:ss"/></el-form-item>
        <el-form-item label="结束"><el-time-picker v-model="dutyForm.endTime" value-format="HH:mm:ss"/></el-form-item>
        <el-form-item label="地点"><el-input v-model="dutyForm.location"/></el-form-item>
      </el-form>
      <template #footer><el-button @click="dutyDialog=false">取消</el-button><el-button type="primary" @click="saveDuty">保存</el-button></template>
    </el-dialog>
  </div>
</template>
<script setup>
import { ref, onMounted } from 'vue'
import api from '../api/index.js'
import { ElMessage, ElMessageBox } from 'element-plus'
const supplies = ref([]), staffs = ref([]), duties = ref([]),
  supplyDialog = ref(false), supplyEdit = ref(false), supplyForm = ref({ supplyId:null, name:'', quantity:0, unit:'个' }),
  staffDialog = ref(false), staffEdit = ref(false), staffForm = ref({ staffId:null, name:'', phone:'' }),
  dutyDialog = ref(false), dutyEdit = ref(false), dutyForm = ref({ dutyId:null, staffId:null, dutyDate:'', startTime:'', endTime:'', location:'' })

const load = async () => {
  const [rs, rp, rd] = await Promise.all([
    api.get('/supply', { params: { page:1, size:999 } }),
    api.get('/staff', { params: { page:1, size:999 } }),
    api.get('/dutySchedule', { params: { page:1, size:999 } })
  ])
  if (rs.code===200) supplies.value = rs.data.records
  if (rp.code===200) staffs.value = rp.data.records
  if (rd.code===200) duties.value = rd.data.records.map(d => ({ ...d, staffName: (rp.data.records.find(s=>s.staffId===d.staffId)||{}).name }))
}
const openSupplyDialog = (row) => { if (row) { supplyEdit.value = true; supplyForm.value = { ...row } } else { supplyEdit.value = false; supplyForm.value = { supplyId:null, name:'', quantity:0, unit:'个' } }; supplyDialog.value = true }
const saveSupply = async () => { if (supplyEdit.value) await api.put('/supply', supplyForm.value); else await api.post('/supply', supplyForm.value); ElMessage.success('保存成功'); supplyDialog.value = false; load() }
const delSupply = async (id) => { await ElMessageBox.confirm('确定删除？'); await api.delete('/supply/'+id); ElMessage.success('删除成功'); load() }
const openStaffDialog = (row) => { if (row) { staffEdit.value = true; staffForm.value = { ...row } } else { staffEdit.value = false; staffForm.value = { staffId:null, name:'', phone:'' } }; staffDialog.value = true }
const saveStaff = async () => { if (staffEdit.value) await api.put('/staff', staffForm.value); else await api.post('/staff', staffForm.value); ElMessage.success('保存成功'); staffDialog.value = false; load() }
const delStaff = async (id) => { await ElMessageBox.confirm('确定删除？'); await api.delete('/staff/'+id); ElMessage.success('删除成功'); load() }
const openDutyDialog = (row) => { if (row) { dutyEdit.value = true; dutyForm.value = { ...row } } else { dutyEdit.value = false; dutyForm.value = { dutyId:null, staffId:null, dutyDate:'', startTime:'', endTime:'', location:'' } }; dutyDialog.value = true }
const saveDuty = async () => { if (dutyEdit.value) await api.put('/dutySchedule', dutyForm.value); else await api.post('/dutySchedule', dutyForm.value); ElMessage.success('保存成功'); dutyDialog.value = false; load() }
const delDuty = async (id) => { await ElMessageBox.confirm('确定删除？'); await api.delete('/dutySchedule/'+id); ElMessage.success('删除成功'); load() }
onMounted(load)
</script>
