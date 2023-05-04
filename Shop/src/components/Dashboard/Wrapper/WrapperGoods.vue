<!--此组件为将所有向已注册成功的店铺信息打包，并排版为用户友好的UI界面-->
<script setup>
import GoodsShow from '../Goods/GoodsShow.vue'
import RegisteringGoods from '../Goods/RegisteringGoods.vue'
</script>

<template>
    <div class="wrap" >
        <el-button class="regBtn" type="primary">点击注册新商品</el-button>
        <div class="ge"></div>
        <h1>已注册</h1>
        <h1>您还没有注册任何商品</h1>
        <div class="goods">
            <!--div v-for="goods in registeringGoodsList" :key="goods.goodsId">
                <GoodsShow :shop="shop"/>
            </div-->
            <GoodsShow :goods="this.goods"/>
            <!--GoodsShow />
            <GoodsShow />
            <GoodsShow />
            <GoodsShow />
            <GoodsShow /-->
        </div>
    </div>
</template>

<script>
import "../../../constant"
export default {
    props: ['shopname'],
    data() {
        return {
            // state表示“商品信息”页面对应的显示内容——注册新商品。
            state: 0,
            registeringGoodsList: [],
            goods:{
                goodsname: "goodsname",
                goodsId: "id",
                status: 1,
                router: this.$router,
                toGoodsRegister: this.toGoodsRegister
            }
        }
    },
    created (){
        //this.getRegisteringGoods() 
    },
    methods: {
        getRegisteringGoods() {
            this.goods.shopname="shopname"
        },
        toGoodsRegister() {
            console.log("跳转至详情页面");
            var localStorage = window.localStorage;
            localStorage.setItem("goodsId",this.goods.goodsId);
            console.log(localStorage.getItem("goodsId"));
            var msg = this.$router.push({name:'GoodsRegister'})
            .catch(err => {
                console.log(err) 
            })
            console.log(msg)
        //console.log(this.$router.push({name:'GoodsRegister'}))
    }
    }
}
</script>


<style scoped>
.wrap {
    width: 100%;
    height: 100%;
    padding: 25px 25px 25px 40px;

    line-height: 100px;
    align-items: center;
    justify-items: center;

    background-color: #fff;
    border-radius: 15px;
    color: #303133;
    border: 2px solid #ebeef5;
    box-shadow: 2px 2px 12px 2px rgb(0 0 0 / 10%);
    transition: .3s;
    margin-top: 50px;
    margin-bottom: 50px;
}

h1{
    text-align: center;
    font-weight: bolder;
    font-size: 30px;
}
.goods{
    display:flex;
    overflow: auto;
    flex-wrap: wrap;
}

.ge{
    width: 100%;
    height:1px;
    margin-top: 30px;
    margin-bottom: 10px;
    border-top:2px solid #ccc;
}

.regBtn{
  margin-bottom: 25px;
  margin-right: 20px;
  margin-top: 0px;
  border-color:#81A18B;
  background-color: #81A18B;
  width :100%;
  height: 40px;
  font-size: 16px;
}
</style>