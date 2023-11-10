
<template>
  <div class="form-container">

    <!-- @keyup.enter로 엔터 바로 확인 -->
    <input type="search" @keyup.enter="doSearch" placeholder="닉네임을 입력하세요." v-model="searchText">
    <button @click="doSearch">중복조회</button>

    <div class='gender-radio'>
      <input type="radio" v-model="gender" value="남자">남자
      <input type="radio" v-model="gender" value="여자">여자
    </div>

    <input type="text" @keyup="getPhoneMask(contact)" placeholder="전화번호를 입력하세요." v-model="contact" maxlength="13">

    <input type="number" v-model="age" min="15" max="100" placeholder="나이를 입력하세요.">

    <button @click="click()">확인</button>
  </div>
</template>

<script>
export default {
  data() {
    return {
      searchText: '',
      gender: '',
      contact:null,
      age: null,
    }
  },
  methods: {
    doSearch() {
      console.log(this.searchText)
    },
    checkEnter(evt) {
      console.log(evt.key)
      // key가 enter면 doSearch 실행
      if (evt.key === 'Enter') {
        // method도 this 사용
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

    click(){
      if(confirm(`닉네임 ${this.searchText}, 성별 ${this.gender}, 전화번호 ${this.contact}, 나이 ${this.age}이(가) 맞습니까?`)){
        //데이터 전송?
      }
      else{
        return;
      }
    }
  }
}
</script>


<style scoped>
.form-container {
  max-width: 400px;
  margin: 0 auto;
  padding: 20px;
  background-color: #fff;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
  text-align: center;
}

input {
  margin: 10px 0;
  padding: 8px;
  width: 100%;
  box-sizing: border-box;
}

button {
  padding: 10px;
  background-color: #4CAF50;
  color: #fff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.gender-radio {
  display: flex;
  justify-content: space-between;
  margin: 10px 0;
}
</style>