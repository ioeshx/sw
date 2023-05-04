<!--商家上架申请记录中的商品展示组件-->

<template>
  <div class="goods">
    <a href="#" class="goodsLink">

      <img v-if="goods.goodsAvatar.length==0" class="logo" src="@/assets/shop.png" alt="点击进入店铺详情页面"/>
      <img v-else :src="'data:image/jpeg;base64,'+goods.goodsAvatar[0]" class="logo">

      <p class="goodsName">商品名：{{ goods.goodsname }}</p>
      <el-button class="btnReg"  @click="showGoodsDetail">
        <p>点击查看详细信息</p>
      </el-button>
    
    </a>
  </div>
</template>

<script>
import "../../../constant"
import GoodsInfo4Shopkeeper from "./GoodsInfo4Shopkeeper.vue"
export default {
    props: {
        goods: {
            type: Object,
            required: true
        }
    },
    components: { 
      GoodsInfo4Shopkeeper
    },
    methods: {
      showGoodsDetail() {
        this.$emit('showGoodsDetail', {goods:this.goods});
      }
    }
}
</script>

<style>
.goodsLink{
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
    text-decoration: none;
}
.goodsLink .logo{
    grid-row: 1/3;
    grid-column: 1/2;
    width: 80%;
    height: 80%;
}
.goodsLink p{
  align-items: left;
  padding-top: 5px;
  margin-left: -10px;
  line-height: 25px;
}
.goodsLink .goodsName{
  padding-top: 100px;
}


.goodsLink .btnReg{
    grid-row: 3/4;
    grid-column: 1/3;
}



.goodsLink .information p{
  font-family:"Lucida Console", "Courier New", monospace;
  font-size:20px;
  margin:0px 0px 0px 50px;
  line-height: 40px;
}
.goodsLink .information .bottom{
  font-family:"Lucida Console", "Courier New", monospace;
  font-size:20px;
  margin:0px 0px -70px 50px;
}

.dialog-footer{
  margin-right: 10px;
}


</style>