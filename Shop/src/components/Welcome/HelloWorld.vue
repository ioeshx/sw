<!--此组件为欢迎页面的欢迎语-->
<script setup>
defineProps({
  msg: {
    type: String,
    required: true
  }
})
</script>

<template>
  <div class="greetings">
    <h1 class="green">{{ msg }}</h1>
    <h3>
      Welcome to Our Online Shopping Mall!
    </h3>
  </div>
</template>

<style scoped>
h1 {
  font-family: "Brush Script MT", cursive;
  font-weight: 1000;
  font-size: 5rem;
  color:#195844;
  top: -20px;
}

h3 {
  top:0px;
  font-size: 1.6rem;
  font-family:"Brush Script MT", cursive;
  margin-top:-10px;
  color:#615d31
}

.greetings h1,
.greetings h3 {
  text-align: center;
}

@media (min-width: 1024px) {
  .greetings h1,
  .greetings h3 {
    text-align: left;
  }
}
</style>
