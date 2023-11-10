<script>
export default {
    data() {
    return {
        name: 'Vue.js'
    }
    },
    methods: {
    greet(event) {
        // `this`는 메서드가 활성화된 현재 인스턴스를 가리킵니다.x
        alert(`안녕 ${this.name}!`)
        // 'event'는 네이티브 DOM 이벤트 객체입니다.
        if (event) {
        alert(event.target.tagName)
        }
    }
    }
}
/*
methods: {
  warn(message, event) {
    // 이제 네이티브 이벤트 객체에 접근할 수 있습니다.
    if (event) {
      event.preventDefault()
    }
    alert(message)
  }
}

}
</script>
*/

<template>

 <button @click="count++">1 추가</button>
  <p>숫자 값은: {{ count }}</p>

  <button @click="greet">환영하기</button>

  <!-- 특수한 키워드인 $event 사용 -->
  <button @click="warn('아직 양식을 제출할 수 없습니다.', $event)">
    제출하기
  </button>

  <!-- 인라인 화살표 함수 사용 -->
  <button @click="(event) => warn('아직 양식을 제출할 수 없습니다.', event)">
    제출하기
  </button>

  <!-- `key`가 `Enter`일 때만 `submit`을 호출합니다 -->
  <input @keyup.enter="submit" placeholder="이름을 입력하세요">

  <input v-model="Name" placeholder="이름을 입력하세요">

</template>


<template>
  <div>
    <button @click="click()">클릭</button>	
    // 버튼 클릭 시 'click' 함수의 내용을 동작
  </div>
</template>

<script>
export default {
  name: 'App',
  methods:{
    click(){
      alert("버튼을 클릭 했습니다.")
    }
  }
}
</script>