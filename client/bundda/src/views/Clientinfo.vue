
<template>
  <body>
  <div class="form-container">
    <div class="nickname-container">
      <!-- @keyup.enter로 엔터 바로 확인 -->
      <input type="search" @keyup.enter="doSearch" placeholder="닉네임을 입력하세요." v-model="searchText">
      <button @click="doSearch" class="search-button">중복검사</button>
    </div>
    <div class='gender-radio'>
      <div class="radio-box">
        <input type="radio" v-model="gender" value="male"><div class='gender-text'>남자</div>
      </div>
      <div class="radio-box">
        <input type="radio" v-model="gender" value="female"><div class='gender-text'>여자</div>
      </div>
    </div>

    <input type="text" @keyup="getPhoneMask(contact)" placeholder="전화번호를 입력하세요." v-model="contact" maxlength="13">

    <input type="number" v-model="age" min="15" max="100" placeholder="나이를 입력하세요.">
    

    <div class="file-input-container">
      <label for="file-upload" class="custom-file-upload">
        프로필 사진 선택
      </label>
      <input id="file-upload" type="file" accept="image/*" @change="fileChange">
    </div>
    <button @click="click()" class="confirm-button">확인</button>
  </div>
</body>
</template>

<script>

export default {
  data() {
    return {
      websocket: null,
      searchText: '',
      gender: '',
      contact:null,
      age: null,
      file: null,
      state: false,
    }
  },
  methods: {
    async CheckName() {
      const response = await fetch(
        `https://a52a-222-106-187-35.ngrok-free.app/api/member/check?name=${this.searchText}`,
        {
          method: "GET",
          mode: "cors",
          headers:{
              "ngrok-skip-browser-warning": "69420"
          }
        }
      );
      const result = await response.json();
      if(result.ok === true){
        alert(`사용 가능합니다!`)
        this.state=true
      }
      else if(result.ok === false){
        alert(`중복 되었습니다.`)
        this.searchText = ''
      }
      console.log(await response.json());
    },
    async PostInfo()  {
      var data = new FormData();
      data.append('age', this.age);
      data.append('gender', this.gender);
      data.append('name', this.searchText);
      data.append('phoneNumber', this.contact);
      data.append('thumbnail', this.file[0]);

      const response = await fetch(
      
        "https://a52a-222-106-187-35.ngrok-free.app/api/member",
        {
            method: "POST",
            mode: "cors",
            body: data,
            headers:{
              "ngrok-skip-browser-warning": "69420"
          }
        }
      )
      const result = await response.json();
      if(result.ok == true){
        localStorage.setItem("me", result.message);
        this.websocket = new WebSocket(
          "ws://a52a-222-106-187-35.ngrok-free.app/api/ws/playground"
        );
        await this.websocket.addEventListener('open', (event) => {
          console.log("WebSocket 연결", event);
          this.websocket.send(`connect:${result.message}:-1`)
        });
        window.location.href = '/juneon1';
      } else{
        console.error('응답 오류:', response.status)
      }
},

    doSearch() {
      console.log(this.searchText)
      this.CheckName();
    },
    checkEnter(evt) {
      console.log(evt.key)
      // key가 enter면 doSearch 실행
      if (evt.key === 'Enter') {
        this.doSearch()
      }
    },


    getPhoneMask(val){
      let res = this.getMask(val)
      this.contact = res
      //서버 전송은 숫자만
      this.model.contact = this.contact.replace(/[^0-9]/g,'')
    },

    getMask(phoneNumber){
      if(!phoneNumber) return phoneNumber
      phoneNumber = phoneNumber.replace(/[^0-9]/g,'')

      let res = ''
      if(phoneNumber.length < 3){
        res = phoneNumber
      }
      else{
        if(phoneNumber.substr(0, 2) =='02'){
          if(phoneNumber.length <= 5){
            res = phoneNumber.substr(0, 2) + '-' + phoneNumber.substr(2, 3)
          }
          else if(phoneNumber.length > 5 && phoneNumber <= 9){
            res = phoneNumber.substr(0, 2) + '-' + phoneNumber.substr(2, 4) + '-' + phoneNumber.substr(6)
          }
        }
        else{
          if(phoneNumber.length < 8){
            res = phoneNumber
          }
          else if(phoneNumber.length == 8){
            res = phoneNumber.substr(0, 4) + '-' + phoneNumber.substr(4)
          }
          else if(phoneNumber.length == 9){
            res = phoneNumber.substr(0, 3) + '-' + phoneNumber.substr(3, 3) + '-' + phoneNumber.substr(6)
          }
          else if (phoneNumber.length == 10){
            res = phoneNumber.substr(0, 3) + '-' + phoneNumber.substr(3, 3) + '-' + phoneNumber.substr(6)
          }
          else if(phoneNumber.length > 10){
            res = phoneNumber.substr(0, 3) + '-' + phoneNumber.substr(3, 4) + '-' + phoneNumber.substr(7)
          }
        }
      }
      return res
    },

    fileChange: function(e){
      const file = e.target.files;
      let validation = true;
      let message = '';

      if (file[0].type.indexOf('image') < 0){
        validation = false;
        message = `${message}, 이미지 파일만 업로드 가능합니다.`;
      }
      if(validation){
        this.file = file
      }
      else{
        this.file = '';
        alert(message);
      }
    },

    click(){
      if(this.searchText == ''){
        alert(`닉네임을 입력하세요.`)
        return;
      }
      if(this.gender == ''){
        alert(`성별을 선택해 주세요.`)
        return;
      }
      if(this.contact == null){
        alert(`전화번호를 입력해 주세요.`)
        return;
      }
      if(this.age == null){
        alert(`나이를 입력해 주세요.`)
        return;
      }
      if(this.state == false){
        alert(`중복 검사를 해주세요.`)
        return;
      }

      if(confirm(`닉네임 ${this.searchText}, 성별 ${this.gender}, 전화번호 ${this.contact}, 나이 ${this.age}이(가) 맞습니까?`)){
        this.PostInfo();
      }
      else{
        return;
      }
    },
  }
}
</script>


<style scoped>

body {
  background-image: url('.\background.jpg'); 
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  background-attachment: fixed;
}
.radio-box {
  display: flex;
  align-items: center;
}

.form-container {
  max-width: 400px;
  margin: 0 auto;
  padding: 20px;
  background-color: #fff;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
  text-align: center;
}

.nickname-container {
  display: flex;
  gap: 10px;
  margin-bottom: 10px;
}

input {
  margin: 10px 0;
  padding: 8px;
  width: 100%;
  box-sizing: border-box;
  border-radius: 7px;
}

.search-button{
  padding: 10px 20px;
  background-color: slateblue;
  color: #fff;
  cursor: pointer;
  border-radius: 15px;
  font-size: 13px;
  border: none;
}
.confirm-button {
  padding: 12px;
  background-color: slateblue;
  color: #fff;
  cursor: pointer;
  border-radius: 15px;
  font-size: 15px;
  border: none;
  margin: 13px 0;
}

.gender-radio {
  display: flex;
  justify-content: space-between;
  margin: 10px 0;
  width: 50%;
}
[type="radio"] {
  vertical-align: middle;
  border: max(2px, 0.1em) solid gray;
  border-radius: 50%;
  width: 1.25em;
  height: 1.25em;
}
.gender-text{
  padding: 10px;
}

.file-input-container {
  margin-top: 10px;
  display: flex;
  align-items: center;
  font-size: 13px;
  border: none;

}

.custom-file-upload {
  cursor: pointer;
  padding: 3px 10px;
  background-color: slateblue;
  color: #fff;
  border-radius: 15px;
  display: inline-block;
}



</style>