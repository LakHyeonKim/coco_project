<template>
  <div class="container" v-cloak>
    <div>
      <h2>{{ room.name }}</h2>
    </div>
    <div class="input-group">
      <div class="input-group-prepend">
        <label class="input-group-text">내용</label>
      </div>
      <input
        type="text"
        class="form-control"
        v-model="message"
        @keyup.enter="sendMessage"
      />
      <div class="input-group-append">
        <button class="btn btn-primary" type="button" @click="sendMessage">
          보내기
        </button>
      </div>
    </div>
    <ul class="list-group">
      <li class="list-group-item" v-for="message in messages" :key="message">
        {{ message.sender }} - {{ message.message }}
      </li>
    </ul>
    <div></div>
  </div>
</template>

<script>
import sock from 'sockjs-client'
import ws from 'webstomp-client'
import axios from "axios";

export default {
  name: 'RoomDetail',
  data () {
    return {
      roomId: '',
      room: {},
      sender: '',
      message: '',
      messages: []
    }
  },
  created () {
    this.roomId = localStorage.getItem('wschat.roomId')
    this.sender = localStorage.getItem('wschat.sender')
    this.findRoom()
    this.connect()
  },
  methods: {
    findRoom: function () {
      axios.get('http://localhost:8081/chat/room/' + this.roomId).then(response => {
        this.room = response.data
      })
    },
    sendMessage: function () {
      this.stompClient.send(
        '/app/chat/message',
        JSON.stringify({
          type: 'TALK',
          roomId: this.roomId,
          sender: this.sender,
          message: this.message
        },{})
      )
      this.message = ''
    },
    recvMessage: function (recv) {
      this.messages.unshift({
        type: recv.type,
        sender: recv.type == 'ENTER' ? '[알림]' : recv.sender,
        message: recv.message
      })
    },
    connect: function () {
      this.socket = new sock("http://localhost:8081/gs-guide-websocket");
      this.stompClient = ws.over(this.socket);
      this.stompClient.connect(
        {},
        frame => {
          console.log(frame);
          this.stompClient.subscribe('/sub/chat/room/' + this.$data.roomId, message => {
            var recv = JSON.parse(message.body)
            this.recvMessage(recv)
          })
          this.stompClient.send(
            '/app/chat/message',
            JSON.stringify({
              type: 'ENTER',
              roomId: this.$data.roomId,
              sender: this.$data.sender
            },{})
          )
        },
        error => {
          alert('error ' + error)
        }
      )
    }
  }
}
</script>

<style>
[v-cloak] {
  display: none;
}
</style>
