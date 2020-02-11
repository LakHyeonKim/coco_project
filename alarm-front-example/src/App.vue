<template>
  <div id="app">
    <room></room>
  </div>
</template>

<script>
import Room from "@/components/Room.vue"


export default {
  name: 'App',
  componets:{
    Room
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
