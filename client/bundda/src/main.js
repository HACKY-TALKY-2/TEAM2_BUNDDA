import './assets/main.css'

import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import socket from 'vue3-websocket'

const app = createApp(App)

app.use(router)
app.use(socket, 'ws://a52a-222-106-187-35.ngrok-free.app/api/ws/chat')

app.mount('#app')
