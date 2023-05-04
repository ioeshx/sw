<!--商家修改信息主页面-->
<script setup>
import Nav from '../../components/Public/Nav/ShopkeeperNav.vue'
import UserInfo  from '../../components/Dashboard/User/SetUserInfo.vue';
import { RouterLink, RouterView } from 'vue-router'
</script>

<template>
        
    <Nav></Nav>
    
    <section>
        <h1>个人信息修改</h1>
        <UserInfo class="wrap"></UserInfo>
    </section>

</template>

<style scoped>
section h1{
  text-align: center;
  font-family:"Lucida Console", "Courier New", monospace;
  font-size: 45px;
  margin:40px;
  font-weight: bolder;
  color:#81A18B;
}
.wrap{
    padding: 20px 30px 10px 30px;
    margin: 20px 200px;
    background-color: #fff;
    border-radius: 15px;
    color: #303133;
    border: 2px solid #ebeef5;
    gap:20px;
}
</style>