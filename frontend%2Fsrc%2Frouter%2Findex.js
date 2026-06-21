import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  { path: '/', name: 'Dashboard', component: () => import('../views/Dashboard.vue') },
  { path: '/athlete', name: 'Athlete', component: () => import('../views/Athlete.vue') },
  { path: '/event', name: 'Event', component: () => import('../views/Event.vue') },
  { path: '/match', name: 'Match', component: () => import('../views/Match.vue') },
  { path: '/referee', name: 'Referee', component: () => import('../views/Referee.vue') },
  { path: '/logistics', name: 'Logistics', component: () => import('../views/Logistics.vue') },
  { path: '/article', name: 'Article', component: () => import('../views/Article.vue') },
  { path: '/ranking', name: 'Ranking', component: () => import('../views/Ranking.vue') },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

export default router
