<template>
  <div class="container" v-cloak>
    <div class="row">
      <div class="col-md-12">
        <h3>채팅방 리스트</h3>
      </div>
    </div>
    <div class="input-group">
      방제목
      <input
        type="text"
        v-model="room_name"
        @keyup.enter="createRoom"
      />
      <button class="btn btn-primary" type="button" @click="createRoom">
        채팅방 개설
      </button>
    </div>
    <ul class="list-group">
      <li
        class="list-group-item list-group-item-action"
        v-for="item in chatrooms"
        v-bind:key="item.roomId"
        v-on:click="enterRoom(item.roomId)"
      >
        {{ item.name }}
      </li>
    </ul>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: 'Room',
  data () {
    return {
      room_name: '',
      chatrooms: []
    }
  },
  created () {
    this.findAllRoom()
  },
  methods: {
    findAllRoom: function () {
      axios.get('http://localhost:8081/chat/rooms').then(response => {
        console.log(response)
        this.chatrooms = response.data
      })
    },
    createRoom: function () {
      if ('' === this.room_name) {
        alert('방 제목을 입력해 주십시요.')
        return
      } else {
        var params = new URLSearchParams()
        params.append('name', this.room_name)
        axios
          .post('http://localhost:8081/chat/room', params)
          .then(response => {
            console.log(response)
            alert(response.data.name + '방 개설에 성공하였습니다.')
            this.room_name = ''
            this.findAllRoom()
          })
          .catch(response => {
            console.log(response)
            alert('채팅방 개설에 실패하였습니다.')
          })
      }
    },
    enterRoom: function (roomId) {
      var sender = prompt('대화명을 입력해 주세요.')
      localStorage.setItem('wschat.sender', sender)
      localStorage.setItem('wschat.roomId', roomId)
      this.$router.push('/roomdetail')
    }
  }
}
</script>

<style>
[v-cloak] {
  display: none;
}
</style>
