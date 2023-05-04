<!--此组件将店铺信息打包，用于向用户展示店铺信息-->
<template>
  <div class="shop">
    

    <a href="#" class="shop_link">
      <img v-if="shop.avatar" :src="'data:image/jpeg;base64,' + shop.avatar">
      <img v-else class="logo" src="@/assets/shop.png"/>
        <p>{{shop.shopname}}</p>
        <p>{{shop.goodstype}} </p>
        <router-link class="intro" :to="{ name: 'Shop4User', params: { shopname: this.shop.shopname } }">
          进入店铺详情页面
        </router-link>
    </a>
  </div>
    
</template>

<script>
export default {
  props: {
    shop: {
      type: Object,
      required: true
    }
  }
}
</script>

<style scoped>
.shop_link{
    width: 320px;
    height: 250px;
    padding: 20px 30px 10px 30px;
    margin: 10px 20px 20px 20px;
    line-height: 100px;
    align-items: center;

    display: grid;
    grid-template-columns: 60% 40%;
    grid-template-rows: 20% 50% 30%;
    grid-column-gap:20px;

    background-color: #fff;
    border-radius: 15px;
    color: #303133;
    border: 2px solid #ebeef5;
    transition: .3s;
    text-decoration: none;
}

.shop_link .logo{
    grid-row: 1/3;
    grid-column: 1/2;
    width: 80%;
    height: 80%;
}
.shop_link p{
  align-items: left;
  padding-top: 10px;
  margin-left: -20px;
  line-height: 30px;

}

.shop_link .intro{
    grid-row: 3/4;
    grid-column: 1/3;
    text-align: center;
}


</style>