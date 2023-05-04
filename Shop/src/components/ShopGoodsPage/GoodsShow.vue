<!-- 用户可见的商品简要信息展示 -->
<template>
    <div class="all">
        <div class="image">
            <img v-if="goods.goodsAvatar.length == 0" class="logo" src="@/assets/shop.png" alt="点击进入店铺详情页面"/>
            <img v-else :src="'data:image/jpeg;base64,'+goods.goodsAvatar[0]">
        </div>
        
        <div class="info">
            <p>商品名: {{ goods.goodsname }}</p>
            <p>收藏量：{{ goods.favorites }}</p>
            <p>单价：{{ goods.goodsPrice}}</p>
        </div>
        
        <div class="buy">
            <el-input-number class="num" v-model="num" :min="1" :value-on-clear="1" />
        </div>
        <div class="btn">
            <el-button class="b" @click="addToCart">加入购物车</el-button>
            <el-button class="b">
                <router-link :to="{ name:'GoodsDetail', params:{goodsId: this.goods.goodsId}}" target="_blank" class="rt">
                <!--router-link :to="{ name: 'Goods4User'}" class="rt"-->
                点击查看详细信息
                </router-link>
            </el-button>
        </div>
    </div>
  </template>
  
  <script>
import "../../constant"
export default {
    props: {
        goods: {
            type: Object,
            required: true
        }
    },
    data() {
        return {
            num: 1
        }
    },
    methods: {
        addToCart() {
            this.$axios.post('/addToCart',{
                goodsId: parseInt(this.goods.goodsId),
                username: localStorage.getItem("username"),
                num: this.num
            }).then(res => {
                if(res.data.state == window.SUCCESS) {
                    this.$message.success("已成功添加至购物车！");
                } else {
                    this.$message.error("出现异常，请重试！");
                }})
        },
    }
  }
  </script>

<style scoped>
.image{
    grid-column: 1/2;
}
.image img{
    width:100%;
    height: 100%;
}
.info{
    line-height: 50px;
}
.buy .num{
    margin-left: -100px;
}
.btn{
    display: flex;
    flex-direction: column;
    gap:20px;
}
.b{
    width:100%;
    margin-left: 0;
}
.rt{
    text-decoration: none;
}
</style>