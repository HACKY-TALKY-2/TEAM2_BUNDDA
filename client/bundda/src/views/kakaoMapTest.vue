<script setup>
// import Vue from 'vue'
// import VueGeolocationApi from 'vue-geolocation-api'

// Vue.use(VueGeolocationApi)
</script>

<template>
  <div id="map"></div>
  <button @click="geofind">위치찾기</button>
  <p>{{ textContent }}</p>
</template>

<style>
#map {
  width: 100%;
  height: 400px;
}
</style>

<script>
export default {
  data: () => {
    return {
      latitude: '',
      longitude: '',
      textContent: '',
      map: null,
      findPos: false
    }
  },

  updated() {
    if (this.findPos) {
      if (window.kakao && window.kakao.maps) {
        this.addKakaoMapScript()
      } else {
        this.initMap()
      }
    }
  },

  methods: {
    initMap() {
      const script = document.createElement('script')
      script.src = '//dapi.kakao.com/v2/maps/sdk.js?appkey= *** *** &autoload=false'
      console.log(window.kakao)
      script.onload = () => {
        window.kakao.maps.load(this.addKakaoMapScript)
      }

      document.head.appendChild(script)
    },
    addKakaoMapScript() {
      const container = document.getElementById('map')
      const options = {
        center: new window.kakao.maps.LatLng(this.latitude, this.longitude),
        level: 3
      }

      this.map = new window.kakao.maps.Map(container, options)
    },
    geofind() {
      if (!('geolocation' in navigator)) {
        this.textContent = 'Geolocation is not available.'
        return
      }
      this.textContent = 'Locating...'

      // get position
      navigator.geolocation.getCurrentPosition(
        (pos) => {
          this.latitude = pos.coords.latitude
          this.longitude = pos.coords.longitude
          this.textContent = 'Your location data is ' + this.latitude + ', ' + this.longitude
          this.findPos = true
        },
        (err) => {
          this.textContent = err.message
        }
      )
    }
  }
}
</script>
