<!--封装三种用户的拦截器-->

<script setup>
import UserNav from './UserNav.vue';
import ShopkeeperNav from './ShopkeeperNav.vue';
import ManagerNav from './ManagerNav.vue';
</script>

<template>
    <ManagerNav v-if="userType==0"/>
    <ShopkeeperNav v-if="userType==1" />
    <UserNav v-if="userType==2" />
</template>

<script>
export default {
  data() {
    return {
      userType: null
    };
  },
  created() {
    this.getUserType();
  },
  methods: {
    getUserType() {
        this.userType = localStorage.getItem("usertype")
        console.log(this.userType)
    }
  }
};
</script>