<script setup>
import arrowBackImage from '../assets/arrow_back_ios.svg'
import sendImage from '../assets/send.svg'
import gameImage from '../assets/stadia_controller.svg'
import axios from 'axios'

const profileImage =
  'https://hips.hearstapps.com/hmg-prod/images/cute-cat-photos-1593441022.jpg?crop=0.670xw:1.00xh;0.167xw,0&resize=640:*'

import { inject, ref } from 'vue'
import { onMessage, onOpen, onClose, onError } from 'vue3-websocket'

const socket = inject('socket')

onOpen((obj) => {
  console.log(obj)
  socket.value.send('connect:1:-1:-1')
})

onMessage((msg) => {
  console.log(msg)
})

const sendMessageSocket = () => {
  socket.value.send('send:1:2:test')
  console.log('send!')
}
</script>

<template>
  <div v-if="!chatClosed" class="screen">
    <div class="header-outer">
      <div class="header-div">
        <router-link to="/"
          ><div class="back-button" :style="{ backgroundImage: `url(${arrowBackImage})` }"></div
        ></router-link>
        <div class="header-profile" :style="{ backgroundImage: `url(${profileImage})` }"></div>
        <div class="username">역삼역</div>
      </div>
      <div class="timer">{{ timeCounter }}</div>
    </div>

    <div class="chat">
      <div class="chat-body">
        <div v-for="item in chat" :key="item">
          <div v-if="item.user === 'Others'">
            <div class="message-others">
              <div class="profile" :style="{ backgroundImage: `url(${profileImage})` }"></div>
              <div class="content">{{ item.content }}</div>
            </div>
          </div>
          <div v-else-if="item.user === 'Me'">
            <div class="message-me">
              <div class="content">{{ item.content }}</div>
            </div>
          </div>
          <div v-else-if="item.user === 'Bot'">
            <div class="message-bot">
              <div class="content">
                {{ item.content[0] }} <br />
                {{ item.content[1] }}
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="input">
      <input v-model="message" placeholder="send message.." class="input-text" />
      <div
        v-on:click="playGame"
        class="game-button"
        :style="{ backgroundImage: `url(${gameImage})` }"
      ></div>
      <div
        v-on:click="sendMessageSocket"
        class="send-button"
        :style="{ backgroundImage: `url(${sendImage})` }"
      ></div>
    </div>
  </div>

  <div v-if="chatClosed">
    <div class="end-screen">
      <div class="content">연락처를 주겠습니까?</div>
      <div class="okay-button">Okay</div>
      <div class="no-button">No</div>
    </div>
  </div>
</template>

<style scoped>
@import url('https://cdn.jsdelivr.net/gh/orioncactus/pretendard@v1.3.9/dist/web/static/pretendard.min.css');

.screen {
  --chat-color: #eceeed;

  overflow: hidden;
  height: 92vh;
  display: flex;
  flex-basis: auto;
  flex-direction: column;
  gap: 20px;
}

.header-outer {
  display: flex;
  flex-grow: 1;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
}

.header-div {
  display: flex;
  flex-basis: auto;
  justify-content: flex-start;
  align-items: center;
}

.header-div .back-button {
  width: 20px;
  height: 20px;
  background-size: cover;
}

.header-div .back-button-img {
  margin-right: 5px;
}

.header-div .header-profile {
  width: 35px;
  height: 35px;
  background-color: var(--chat-color);
  border-radius: 50%;
  margin-right: 10px;
  background-size: cover;
}

.header-div .username {
  font-family: 'Pretendard medium', Pretendard, -apple-system, BlinkMacSystemFont, system-ui, Roboto,
    'Helvetica Neue', 'Segoe UI', 'Apple SD Gothic Neo', 'Noto Sans KR', 'Malgun Gothic',
    'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol', sans-serif;
  font-size: 2.5vh;
}

.timer {
}

.chat {
  padding-top: 5vh;
  overflow: auto;
  flex-basis: 500px;
  flex-grow: 8;
  border-top: 1px solid black;
}

.chat::-webkit-scrollbar {
  display: none;
}

.chat .chat-body {
  display: flex;
  flex-direction: column;
  gap: 25px;
  justify-content: flex-end;
  min-height: 100%;
}

.chat .message-others {
  display: flex;
  flex-direction: row;
  align-items: flex-end;
}

.chat .message-others .profile {
  width: 30px;
  height: 30px;
  background-color: var(--chat-color);
  border-radius: 50%;
  margin-right: 8px;
  background-size: cover;
}

.chat .message-others .content {
  font-family: 'Pretendard medium', Pretendard, -apple-system, BlinkMacSystemFont, system-ui, Roboto,
    'Helvetica Neue', 'Segoe UI', 'Apple SD Gothic Neo', 'Noto Sans KR', 'Malgun Gothic',
    'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol', sans-serif;
  max-width: 50vw;
  background-color: var(--chat-color);
  padding-block: 15px;
  padding-inline: 14px;
  border-radius: 10px;
}

.chat .message-me {
  display: flex;
  justify-content: flex-end;
}

.chat .message-me .content {
  font-family: 'Pretendard medium', Pretendard, -apple-system, BlinkMacSystemFont, system-ui, Roboto,
    'Helvetica Neue', 'Segoe UI', 'Apple SD Gothic Neo', 'Noto Sans KR', 'Malgun Gothic',
    'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol', sans-serif;
  max-width: 50vw;
  background-color: var(--chat-color);
  padding-block: 15px;
  padding-inline: 14px;
  border-radius: 10px;
}

.chat .message-bot {
  display: flex;
  justify-content: center;
}

.chat .message-bot .content {
  font-family: 'Pretendard medium', Pretendard, -apple-system, BlinkMacSystemFont, system-ui, Roboto,
    'Helvetica Neue', 'Segoe UI', 'Apple SD Gothic Neo', 'Noto Sans KR', 'Malgun Gothic',
    'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol', sans-serif;
  text-align: center;
  max-width: 50vw;
  background-color: var(--chat-color);
  padding-block: 15px;
  padding-inline: 14px;
  border-radius: 10px;
}

.input {
  display: flex;
  flex-basis: auto;
  flex-grow: 1;
  gap: 15px;
  align-items: center;
}

.input .input-text {
  flex-grow: 1;
  border-radius: 20px;
  height: 30px;
  border: 1px solid black;
  padding-left: 10px;
}

.input .game-button {
  width: 25px;
  height: 25px;
  border-radius: 50%;
  /* background-color: var(--chat-color); */
  background-size: cover;
}

.input .send-button {
  width: 25px;
  height: 25px;
  border-radius: 50%;
  /* background-color: var(--chat-color); */
  background-size: cover;
}

.end-screen {
  height: 92vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 10vh;
}

.end-screen .content {
  font-size: 30px;
  font-family: 'Pretendard semibold', Pretendard, -apple-system, BlinkMacSystemFont, system-ui,
    Roboto, 'Helvetica Neue', 'Segoe UI', 'Apple SD Gothic Neo', 'Noto Sans KR', 'Malgun Gothic',
    'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol', sans-serif;
  color: #000000;
}

.end-screen .okay-button {
  color: #000000;
  font-family: 'Pretendard semibold', Pretendard, -apple-system, BlinkMacSystemFont, system-ui,
    Roboto, 'Helvetica Neue', 'Segoe UI', 'Apple SD Gothic Neo', 'Noto Sans KR', 'Malgun Gothic',
    'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol', sans-serif;
  font-size: 30px;
  background-color: #f7a2f6;
  width: 70vw;
  text-align: center;
  padding-block: 20px;
  border-radius: 50px;
}

.end-screen .no-button {
  color: black;
  font-family: 'Pretendard semibold', Pretendard, -apple-system, BlinkMacSystemFont, system-ui,
    Roboto, 'Helvetica Neue', 'Segoe UI', 'Apple SD Gothic Neo', 'Noto Sans KR', 'Malgun Gothic',
    'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol', sans-serif;
  font-size: 30px;
  border: 1px solid black;
  width: 70vw;
  text-align: center;
  padding-block: 20px;
  border-radius: 50px;
}
</style>

<script>
const questionList = [
  '짜장 VS 짬뽕',
  '평생동안 양치 안하기 VS 평생동안 샤워 안하기',
  '지금 당장 1억 VS 10년 후 20억',
  '백수로 월 250 VS 직장인 월 1,000',
  '눈물이 다이아로 변함 VS 소변이 금으로 변함'
]

export default {
  data() {
    return {
      message: '',
      chat: [
        { content: '안녕하세요! 당신은 너무 예쁩니다.', user: 'Others' },
        { content: '감사합니다!', user: 'Me' },
        { content: '안녕하세요! 당신은 너무 예쁩니다.', user: 'Others' },
        { content: '감사합니다!', user: 'Me' },
        { content: '안녕하세요! 당신은 너무 예쁩니다.', user: 'Others' },
        { content: '감사합니다!', user: 'Me' }
      ],
      chatCount: 6,
      chatChanged: false,
      timeCounter: 180,
      intervalID: null,
      chatClosed: false,

      questionIndex: 0,
      lastQuestionTime: 1000,

      socket: null
    }
  },

  // created() {
  //   const socketUrl = 'ws://a52a-222-106-187-35.ngrok-free.app/api/ws/chat'
  //   this.socket = new WebSocket(socketUrl)
  //   this.socket.addEventListener('open', (v) => {
  //     console.log(v)
  //     console.log('연결됨')
  //     this.socket.send('connect:2:-1:-1')
  //   })
  //   this.socket.addEventListener('message', (event) => {
  //     console.log('getMessaged')
  //     console.log(event.data)
  //   })
  //   this.socket.addEventListener('close', () => {
  //     console.log('socket closed')
  //   })
  //   console.log('socket')
  //   console.log(this.socket)
  // },

  mounted: function () {
    this.intervalID = setInterval(() => {
      this.timeCounter--
      // console.log(this.timeCounter)
      if (this.timeCounter === 0) {
        this.closeChat()
      }
    }, 1000)
  },
  methods: {
    back: function () {
      window.location.href('/')
      alert('back')
    },
    sendMessage: function () {
      axios
        .post('/post', {
          message: this.message
        })
        .then(function (response) {
          console.log(response)
        })
      // alert(this.message)
      this.chat.push({
        content: this.message,
        user: 'Me'
      })
      this.chatCount += 1
      this.message = ''
    },
    playGame: function () {
      if (this.lastQuestionTime - this.timeCounter > 5) {
        this.chat.push({
          content: ['!! 밸런스 게임 !!', questionList[this.questionIndex]],
          user: 'Bot'
        })
        this.chatCount += 1
        this.questionIndex = (this.questionIndex + 1) % questionList.length
        this.lastQuestionTime = this.timeCounter
      } else {
        alert('밸런스 게임을 다시 보내려면 시간이 필요합니다.')
      }
    },
    closeChat: function () {
      clearInterval(this.intervalID)
      this.chatClosed = true
    }
    // sendMessageSocket() {
    //   this.socket.send('send:2:1:"test"')
    //   console.log('send')
    // }
  },
  updated() {
    if (this.chatChanged) {
      let chat_div = document.getElementsByClassName('chat-body')[0]
      chat_div.scrollIntoView({ behavior: 'smooth', block: 'end', inline: 'nearest' })
      this.chatChanged = false
    }
  },
  watch: {
    chatCount: function () {
      this.chatChanged = true
    }
  }
}
</script>