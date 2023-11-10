<script setup>
// import { ref } from 'vue';

// const peopleNearby = ref([
//     { name: '역삼역 카리나', age: '25세' },
//     { name: '평냉먹는 권은비', age: '29세' },
//     { name: '0.1초 한소희', age: '32세' },
//     { name: '보급형 송강', age: '23세' },

// ]);
import { ref, onMounted } from 'vue';

const peopleNearby = ref([]);

// 백엔드에서 데이터를 로드하는 함수
const loadPeopleData = async () => {
  try {
    const response = await fetch('https://a52a-222-106-187-35.ngrok-free.app/api/members',
    {
      headers:{
        "ngrok-skip-browser-warning": "69420",
      }
    });
    if (!response.ok) {
      throw new Error('Network response was not ok');
    }
    const data = await response.json();
    peopleNearby.value = data.map(person => ({
      id: person.id,
      name: person.name,
      gender: person.gender,
      age: person.age,
      phoneNumber: person.phoneNumber,
      thumbnail: person.thumbnail
    }));
  } catch (error) {
    console.error('There was a problem with the fetch operation:', error);
  }
};

// 컴포넌트가 마운트되었을 때 데이터 로드
onMounted(loadPeopleData);

const myProfile = { name: '강동원 호소인', gender: '남', age: '25세' };

const isEntered = ref(false);

const enterMeetingSquare = () => {
    if (!peopleNearby.value.find(person => person.name === myProfile.name)) {
        peopleNearby.value.push(myProfile);
        isEntered.value = true;
    }
};

const leaveMeetingSquare = () => {
    const index = peopleNearby.value.findIndex(person => person.name === myProfile.name);
    if (index !== -1) {
        peopleNearby.value.splice(index, 1);
        isEntered.value = false;
    }
};

const showProfileModal = ref(false);
const showMessageModal = ref(false);

const openProfileModal =async () => {
  await fetch(
    "https://a52a-222-106-187-35.ngrok-free.app/api/member/1",
    {
      method: "GET",
      mode: 'cors',
      headers:{
        "ngrok-skip-browser-warning": "69420",
      }
    }
  )

    showProfileModal.value = true;
};


let websocket = null;

// WebSocket 연결을 설정하는 함수
const setupWebSocket = () => {
  websocket = new WebSocket('wss://a52a-222-106-187-35.ngrok-free.app/api/ws/playground');

  websocket.onopen = () => {
    console.log('WebSocket connected');
  };

  websocket.onerror = (error) => {
    console.error('WebSocket Error:', error);
  };

  websocket.onmessage = (event) => {
    // 서버로부터 메시지 수신 시 처리 로직
    console.log('Message from server:', event.data);
  };

  websocket.onclose = () => {
    console.log('WebSocket disconnected');
    // 필요한 경우 재연결 로직 추가
  };
};

onMounted(setupWebSocket);

const requestChat = (userId) => {
  if (websocket && websocket.readyState === WebSocket.OPEN) {
    websocket.send(JSON.stringify({ action: 'requestChat', userId: userId }));
  } else {
    console.error('WebSocket is not connected');
  }
};

const showChatRequestPopup = (request) => {
  // 예시: 단순한 알림으로 구현
  if (confirm(`${request.senderName}님이 채팅을 요청했습니다. 수락하시겠습니까?`)) {
    acceptChatRequest(request.requestId);
  }
};

const acceptChatRequest = (requestId) => {
  if (websocket && websocket.readyState === WebSocket.OPEN) {
    websocket.send(JSON.stringify({ action: 'acceptChatRequest', requestId: requestId }));
    openChatWindow(requestId);
  } else {
    console.error('WebSocket is not connected');
  }
};

const openChatWindow = (requestId) => {
  // 채팅창 열기 로직
  // 예: 새로운 페이지나 모달로 채팅창 표시
  console.log('채팅창 열기:', requestId);
};

// const requestChat = async (userId) => {
//   try {
//     const response = new WebSocket('ws://a52a-222-106-187-35.ngrok-free.app/api/ws/playground')
      
//   } catch (error) {
//     console.error('Error sending chat request:', error);
//   }
// };



</script>

<template>
  <div class="total"> 
  <div class="profile">
    <div>이름: {{ myProfile.name }}</div>
    <div>성별: {{ myProfile.gender }}</div>
    <div>나이: {{ myProfile.age }}</div>

  </div>

  <div class="nearby-people">
      <h1>만남의 광장에 있는 사람들</h1>
      <ul>
          <li v-for="person in peopleNearby" :key="person.id">
              <div v-if="person.name !== myProfile.name" class='icon-button' @click="openProfileModal">👤</div>
              {{ person.name }} ({{person.gender}}) ({{ person.age }})
          </li>
      </ul>
        <div>
      님들이 대화중입니다
    </div>
  </div>
  
  <div v-if="showProfileModal" class="modal">
    프로필 정보<br>
    <br>
    <br>
    <div class="modal-footer">
      <div class="modal-button left">
        <span class="modal-text" @click="requestChat(person.id)">대화 요청하기</span>
      </div>
      <div class="modal-button right">
      <span class="modal-text" @click="showProfileModal=false">닫기</span>
    </div>
      </div>
    </div>

  <div v-if="showMessageModal" class="modal">
    상대방이 수락하면 대화가 시작됩니다<br>
    <br>
    <br>
    <br>
    <div class="close-button"><span class="close-text" @click="showMessageModal=false">닫기</span></div>
  </div>
  </div>

  <div v-if='!isEntered' class="enter" @click="enterMeetingSquare">
    <div>만남의 광장 입장하기</div>
  </div>

  <div v-else class='exit' @click="leaveMeetingSquare" >
    <div>퇴장하기</div>
  </div>
  
</template>

<style>
body {
    background-image: url('C:\Users\User\Desktop\hack\client\bundda\src\views\background.jpg'); 
    background-size: cover;
    background-position: center;
    background-repeat: no-repeat;
    background-attachment: fixed;
}

.total{
  background-color: rgba(255, 255, 255, 0.9);
  border-radius: 10px;
  border: 1px solid #ccc;
  box-shadow: 0 4px 8px rgba(0,0,0,0.1); 
 

}

.nearby-people {
    text-align: top ;
    padding: 20px;
}

ul {
    list-style-type: none;
    padding: 0;
}

li {
    margin: 10px 0;
    font-size: 1.2em;
    background-color:aliceblue;
    border-radius: 10px;
    box-shadow: 0 4px 8px rgba(0,0,0,0.1); 

}

.enter {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    padding: 20px; 
    background-color: slateblue;
    font-family: 'Arial', sans-serif;
    border: 1px solid #ccc;
    border-radius: 10px; 
    box-shadow: 0 4px 8px rgba(0,0,0,0.1);
    margin: auto; 
    text-align: center;
    color: white;
}

.enter:hover {
    background-color: darkblue;
    cursor: pointer;
}

.exit {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    padding: 20px; 
    background-color: LightCoral;
    font-family: 'Arial', sans-serif;
    border: 1px solid #ccc;
    border-radius: 10px; 
    box-shadow: 0 4px 8px rgba(0,0,0,0.1);
    margin: auto; 
    text-align: center; 
    color: white;
}

.exit:hover {
    background-color: darkred;
    cursor: pointer;
}

.profile {
    position: fixed;
    top: 20px;
    right: 20px;
    background-color: white;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 5px;
    box-shadow: 0 4px 8px rgba(0,0,0,0.1);
}

.modal {
    position: fixed;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    width: 300px;
    padding: 20px;
    background-color: white;
    border: 1px solid #ccc;
    border-radius: 5px;
    box-shadow: 0 4px 8px rgba(0,0,0,0.3);
    z-index: 1000;
    display: flex;
  flex-direction: column;
  justify-content: space-between; /* Aligns children (content and footer) */
}

.modal-footer {
  display: flex;
  justify-content: space-between; /* Positions buttons at each end */
  padding: 10px;
}

.modal-button {
  /* Style for the modal buttons */
  margin-top: 10px; /* Adds space above the button */
}

.modal-button.left {
color: slateblue;
font-weight: bolder;
}

.modal-button.right {
  /* Additional styles for right button if needed */
}

.modal-text {
  cursor: pointer;
  /* Add other styling as needed */
}

.icon-button {
  display: inline-block;
  cursor: pointer;
  margin-right: 10px;
}

.close-button {
  position: absolute;
  right: 30px;
  bottom: 30px;

}

.close-text {
  cursor: pointer;
}

</style>
