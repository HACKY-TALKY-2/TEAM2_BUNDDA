<script setup>
import TheWelcome from '../components/TheWelcome.vue'
</script>

<template>
  <main>
    <TheWelcome />
    <button @click="testWebSocket">Connect</button>
    <button @click="testSendMessage">Send Message</button>
  </main>
</template>

<script>
export default {

  data() {
    return {
      websocket: null
    }
  },
  methods: {
    testWebSocket: async function() {
      // return await axios.get("ws://localhost:8080/api/ws/playground");
      this.websocket = new WebSocket("ws://localhost:8080/api/ws/playground");
      this.websocket.onmessage = this.onMessage;

    },

    testSendMessage: async function() {
      this.websocket.send(`helloworld:i'm test Message:${JSON.stringify(this.websocket)}`);
    },

    onMessage: function (msg) {
      const data = msg.data;
      const arr = data.split(":");
      for(let i=0; i<arr.length; i++){
        console.log('arr[' + i + ']: ' + arr[i]);
      }

    }
  }
}
</script>
