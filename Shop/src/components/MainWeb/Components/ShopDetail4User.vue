<!-- 用户可见的店铺详情 -->
<template>
    <router-link to="/UserWeb"> 返回 </router-link>
    <div class="wrap">
        <div class="sideInfo">
            <div class="image">
                <img v-if="!shop.avatar" class="logo" src="@/assets/shop.png" alt="点击进入店铺详情页面"/>
                <img v-else :src="'data:image/jpeg;base64,'+shop.avatar">
            </div>
            <div class="info">
                <p> 店铺名{{ this.shop.shopname }} </p>
            </div>
        </div>
        <!-- 商品展示 -->
        <div class="goods">
            <div v-for="goods in shop.goodslist" :key="goods.goodsname">
                <!--GoodsShow4User :goods="goods"/-->
            </div>
        </div>
    </div>
    
</template>
  
<script>
import GoodsShow4User from "./GoodsShow4User.vue"
import { interceptor,userorshopInterceptor } from "../../../interceptor"
export default {
    data(){
        return {
            shop: {
                shopname: "",
                avatar: "",
                goodslist: []
            }
        }
    },
    components: {
        GoodsShow4User
    },
    created() {
        interceptor(this);
        userorshopInterceptor(this);
        this.getShopInfo();
    },
    methods: {
        getValidGoods() {
            this.$axios.post("/getValidGoodsByShopname", {
                shopname: this.shop.shopname
            }).then(res => {
                this.shop.goodslist = res.data.data;
            })
        },
        getShopInfo() {
            // this.goods.goodsAvatar = []
            // this.shop.goodslist = [this.goods]
            // console.log(this.shop.goodslist)
            this.$axios.post("/getShopInfoByShopname", {
                shopname: this.$route.params.shopname
            })
            .then((res) => {
                if(res.data.state == window.SUCCESS){
                    const shop = res.data.data;
                    this.shop.shopname = shop.shopname;
                    this.shop.avatar = shop.avatar;
                    this.getValidGoods();
                }
                else {
                    this.$message.error("加载失败，请重试");
                }
            })
            .catch((err) => {
                console.log(err);
                this.$message.error("加载失败，请重试");
            });
        }
    }
}
</script>
 
<style scoped>
.wrap{
    display: grid;
    grid-template: 20% 80%;
    margin:0 auto;
    background-color: rgba(128,128,128,0.1);
    width:80%;
}
</style>