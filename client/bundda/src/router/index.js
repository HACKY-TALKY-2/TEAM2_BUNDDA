import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: () => import('../views/juneon1.vue')
    },
    {
      path: '/about',
      name: 'about',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/AboutView.vue')
    },
    {
      path: '/chat',
      name: 'chat',
      component: () => import('../views/vueChat.vue')
    },
    {
      path: '/chat2',
      name: 'chat2',
      component: () => import('../views/vueChat2.vue')
     },
    {
      path: '/juneon1',
      name: 'juneon1',
      component: () => import('../views/juneon1.vue')
    },
    {
      path: '/Clientinfo',
      name: "Clientinfo",
      component: () =>import('../views/Clientinfo.vue')
    }
  ]

})

export default router
