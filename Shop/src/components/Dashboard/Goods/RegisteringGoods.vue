<!--商家上架申请记录中的商品展示组件-->

<template>
  <div class="goods">
    <a href="#" class="goodsLink">
      <img v-if="goods.goodsAvatar.length==0" class="logo" src="@/assets/shop.png" alt="点击进入店铺详情页面"/>
      <img v-else :src="'data:image/jpeg;base64,'+goods.goodsAvatar[0]">
      <p class="goodsName">商品名：{{ goods.goodsname }}</p>
      <el-button class="btnReg">
        <RouterLink :to="{ name: 'GoodsRegister'}" @click.prevent="toGoodsRegister">点击查看详细信息</RouterLink>
      </el-button>
      
    </a>
  </div>
</template>

<script>
import "../../../constant"
export default {
  props: {
    goods: {
      type: Object,
      required: true
    }
  },
  methods: {
    toGoodsRegister() {
        console.log("跳转至详情页面");
        var localStorage = window.localStorage;
        localStorage.setItem("goodsId",this.goods.goodsId);
        console.log(localStorage.getItem("goodsId"));
        console.log(this.goods.router);
        //var msg = this.goods.router.push({name:'GoodsRegister'})
        //.then(res => {
        //    console.log('路由跳转成功');
        //    console.log(res);
        //    console.log(this.$router.history);
            //this.$router.go(-2);
        //}).catch(res => {
        //    console.log('路由跳转失败');
        //});

        //console.log(msg);
        //console.log(this.$router.push({name:'GoodsRegister'}))
        //this.goods.toGoodsRegister();
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
    transition: .3s;
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
  margin-left: -20px;
  line-height: 30px;
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