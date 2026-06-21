<template>
  <div>
    <el-card>
      <template #header><span style="font-weight:bold">比赛场次管理</span>
        <el-button type="primary" size="small" style="float:right;margin-left:8px" @click="openSessionDialog()">新增场次</el-button>
      </template>
      <el-table :data="sessions" border stripe @row-click="selectSession" highlight-current-row>
        <el-table-column prop="sessionId" label="ID" width="60"/>
        <el-table-column prop="sessionName" label="场次名称" min-width="180"/>
        <el-table-column prop="eventName" label="比赛项目" width="120"/>
        <el-table-column prop="matchDate" label="日期" width="110"/>
        <el-table-column prop="matchTime" label="时间" width="90"/>
        <el-table-column prop="venue" label="地点" width="120"/>
        <el-table-column prop="status" label="状态" width="80">
          <template #default="{row}"><el-tag :type="row.status==='已结束'?'success':row.status==='进行中'?'warning':'info'">{{ row.status }}</el-tag></template>
        </el-table-column>
        <el-table-column label="操作" width="160" fixed="right">
          <template #default="{row}"><el-button size="small" @click.stop="openSessionDialog(row)">编辑</el-button><el-button size="small" type="danger" @click.stop="delSession(row.sessionId)">删除</el-button></template>
        </el-table-column>
      </el-table>
      <el-pagination style="margin-top:16px" background layout="prev,pager,next" :total="total" v-model:current-page="page" @current-change="loadSessions"/>
    </el-card>

    <el-card v-if="currentSession" style="margin-top:16px">
      <template #header>
        <span style="font-weight:bold">参赛选手管理 — {{ currentSession.sessionName }}</span>
        <div style="float:right">
          <el-button type="success" size="small" @click="openPartDialog()">添加参赛选手</el-button>
          <el-button type="success" size="small" style="margin-left:8px" @click="handleAutoRank()">提交成绩并排名</el-button>
        </div>
      </template>
      <el-table :data="participations" border stripe>
        <el-table-column prop="trackNo" label="赛道" width="70"/>
        <el-table-column prop="athleteName" label="运动员" width="100"/>
        <el-table-column prop="athleteGroup" label="分组" width="80"/>
        <el-table-column label="成绩" width="120"><template #default="{row}">
          <el-input-number v-model="row.result" :precision="2" size="small" controls-position="right" @change="updatePart(row)"/>
        </template></el-table-column>
        <el-table-column prop="rank" label="名次" width="70"/>
        <el-table-column prop="score" label="积分" width="70"/>
        <el-table-column label="操作" width="80"><template #default="{row}">
          <el-button size="small" type="danger" @click="delPart(row.participationId)">移除</el-button>
        </template></el-table-column>
      </el-table>
    </el-card>

    <!-- Session Dialog -->
    <el-dialog v-model="sessionDialog" :title="sessionEdit?'编辑场次':'新增场次'" width="500px">
      <el-form :model="sessionForm" label-width="80px">
        <el-form-item label="场次名称"><el-input v-model="sessionForm.sessionName"/></el-form-item>
        <el-form-item label="比赛项目"><el-select v-model="sessionForm.eventId"><el-option v-for="e in events" :key="e.eventId" :label="e.eventName+'('+e.genderGroup+')'" :value="e.eventId"/></el-select></el-form-item>
        <el-form-item label="日期"><el-date-picker v-model="sessionForm.matchDate" type="date" value-format="YYYY-MM-DD"/></el-form-item>
        <el-form-item label="时间"><el-time-picker v-model="sessionForm.matchTime" value-format="HH:mm:ss"/></el-form-item>
        <el-form-item label="地点"><el-input v-model="sessionForm.venue"/></el-form-item>
        <el-form-item label="状态"><el-select v-model="sessionForm.status"><el-option label="未开始" value="未开始"/><el-option label="进行中" value="进行中"/><el-option label="已结束" value="已结束"/></el-select></el-form-item>
      </el-form>
      <template #footer><el-button @click="sessionDialog=false">取消</el-button><el-button type="primary" @click="saveSession">保存</el-button></template>
    </el-dialog>

    <!-- Participation Dialog -->
    <el-dialog v-model="partDialog" title="添加参赛选手" width="500px">
      <el-form :model="partForm" label-width="80px">
        <el-form-item label="赛道号"><el-input-number v-model="partForm.trackNo" :min="1" :max="8"/></el-form-item>
        <el-form-item label="运动员"><el-select v-model="partForm.athleteId" filterable placeholder="搜索运动员"><el-option v-for="a in athletes" :key="a.athleteId" :label="a.name+' ('+a.className+')'" :value="a.athleteId"/></el-select></el-form-item>
      </el-form>
      <template #footer><el-button @click="partDialog=false">取消</el-button><el-button type="primary" @click="savePart">保存</el-button></template>
    </el-dialog>
  </div>
</template>
<script setup>
import { ref, onMounted } from 'vue'
import api from '../api/index.js'
import { ElMessage, ElMessageBox } from 'element-plus'
const sessions = ref([]), total = ref(0), page = ref(1),
  currentSession = ref(null), participations = ref([]),
  sessionDialog = ref(false), sessionEdit = ref(false), events = ref([]),
  sessionForm = ref({ sessionId:null, eventId:null, sessionName:'', matchDate:'', matchTime:'', venue:'', status:'未开始' }),
  partDialog = ref(false), athletes = ref([]),
  partForm = ref({ trackNo:1, athleteId:null })

const loadSessions = async () => {
  const res = await api.get('/matchSession', { params: { page: page.value, size: 20 } })
  if (res.code===200) { sessions.value = res.data.records; total.value = res.data.total }
}
const selectSession = async (row) => {
  currentSession.value = row
  const res = await api.get('/participation/session/'+row.sessionId)
  if (res.code===200) participations.value = res.data
}
const openSessionDialog = (row) => {
  if (row) { sessionEdit.value = true; sessionForm.value = { ...row } }
  else { sessionEdit.value = false; sessionForm.value = { sessionId:null, eventId:null, sessionName:'', matchDate:'', matchTime:'', venue:'', status:'未开始' } }
  sessionDialog.value = true
}
const saveSession = async () => {
  if (sessionEdit.value) await api.put('/matchSession', sessionForm.value)
  else await api.post('/matchSession', sessionForm.value)
  ElMessage.success('保存成功'); sessionDialog.value = false; loadSessions()
}
const delSession = async (id) => { await ElMessageBox.confirm('确定删除？'); await api.delete('/matchSession/'+id); ElMessage.success('删除成功'); loadSessions() }
const openPartDialog = async () => {
  partForm.value = { trackNo:1, athleteId:null }
  const r = await api.get('/athlete', { params: { page:1, size:999 } })
  if (r.code===200) athletes.value = r.data.records
  partDialog.value = true
}
const savePart = async () => {
  partForm.value.sessionId = currentSession.value.sessionId
  await api.post('/participation', partForm.value)
  ElMessage.success('添加成功'); partDialog.value = false; selectSession(currentSession.value)
}
const updatePart = async (row) => { await api.put('/participation', row) }
const delPart = async (id) => { await ElMessageBox.confirm('确定移除？'); await api.delete('/participation/'+id); ElMessage.success('移除成功'); selectSession(currentSession.value) }
const handleAutoRank = async () => {
  const session = currentSession.value
  if (!session) { ElMessage.warning('请先选择一个比赛场次'); return }
  try {
    await ElMessageBox.confirm('确定提交成绩并自动排名？此操作将根据成绩计算名次并标记场次为"已结束"。', '确认', { confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning' })
    await api.post('/matchSession/autoRank/' + session.sessionId)
    ElMessage.success('排名完成')
    await loadSessions()
    if (currentSession.value) await selectSession(currentSession.value)
  } catch (e) {
    if (e === 'cancel' || e?.toString()?.includes('cancel')) return
    ElMessage.error('排名出错，请检查后端服务是否正常运行')
  }
}
onMounted(async () => { loadSessions(); const r = await api.get('/event', { params: { page:1, size:999 } }); if (r.code===200) events.value = r.data.records })
</script>
