<!--普通用户首页-->
<script setup>
import WrapperUser from '../../../components/MainWeb/Wrapper/WrapperUser.vue'
import SearchTop from '../../../components/MainWeb/Components/SearchU.vue'
import Nav from '../../../components/Public/Nav/UserNav.vue'
import { RouterLink, RouterView } from 'vue-router'
import { ref } from 'vue'
import { Search } from '@element-plus/icons-vue'
const input = ref('')
const select = ref('')
</script>

<template>
  <Nav></Nav>

  <section>
    <SearchTop />
  </section>

  <section class="show">
    <div class="title-container">
      <h1 class="title">Let's have fun!</h1>
      <div v-if="state ==1"><router-link to="/SaleDetails" class="sale-link">大促销进行中！！！点击查看详情</router-link></div>
    </div>
    <br>
    <WrapperUser class="margin"/>
  </section>
</template>

<script>
import { interceptor, userInterceptor } from "../../../interceptor";
import axios from "axios";
export default {
    data() {
      return{
        state:0
      }
    },
    mounted() {
      axios.post('/getPromotions',{type : 1})
          .then(response => {
                if (response.data.state == 0) {
                  if(response.data.data!=null) this.state = 1;
                }
              })
    },
  created(){
        interceptor(this);
        userInterceptor(this);
    }
}
</script>


<style scoped>
/*展示栏*/
.show{
  margin: -10px 50px 0px 50px;
}
.show .title-container {
  display: flex;
  justify-content: center;
  align-items: center;
}
.show .title{
  margin-top:10px;
  margin-bottom: 10px;
  text-align: center;
  color:#81A18B;
  text-shadow: 2px 2px 2px #195844;
  font-size: 45px;
  font-family: "Lucida Console", "Courier New", monospace;
}
.show .sale-link {
  margin-left: 20px;
  color: #81A18B;
  text-decoration: underline;
  cursor: pointer;
}
.margin{
  margin-bottom: 100px;
}
</style>