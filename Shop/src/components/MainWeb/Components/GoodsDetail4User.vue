<!-- 用户可见的商品详情页面 -->
<template>
    <div>

        <!-- TODO: 图片美化（每个商品的img数量不定，建议做成点击放大的缩略图）-->
        <div v-for="avatar in goods.goodsAvatar">
            <img :src="'data:image/jpeg;base64,' + avatar">
        </div>

        <p>商品类型：{{ goods.goodsname }}</p>
        <p>商品类别：{{ goods.goodsCategory }}</p>
        <p>商品简介：{{ goods.introduction }}</p>
        <p>商品库存：{{ goods.goodsStock }}</p>
        <p>商品价格：{{ goods.goodsPrice }}</p>
            
        <!-- TODO: 累计销量-->
        <!--p>累计销量：{{ goods.sales }}</p-->
        <p>收藏量：{{ goods.favorites }}</p>

        <el-input-number v-model="num" :min="1" @change="handleChange" :value-on-clear="1" />
        <el-button @click="addToCart"> 加入购物车 </el-button>

        <!-- TODO: 收藏美化 -->
        <el-button v-if="favorited" @click="Unfavorite"> 取消收藏 </el-button>
        <el-button v-else @click="addToFavorites"> 收藏 </el-button>

        <p>总价：{{ this.totalPrice }}</p>
    </div>
</template>
 
<script>
import "../../../constant";
import { interceptor,userorshopInterceptor } from "../../../interceptor";
export default {
    data() {
        return {
            goods: {
                goodsAvatar: [],
                goodsname: "",
                goodsCategory: [],
                introduction: "",
                goodsStock: "",
                goodsPrice: 0,
            },
            favorited: false,
            num: 1,
            totalPrice: 0
        };
    },
    watch: {
        num(newValue, oldValue) {
            console.log(newValue);
            if (!newValue) {
                this.$message.error("请输入正整数！");
                this.num = 1
            }
    },
  },
    created() {
        interceptor(this);
        userorshopInterceptor(this);
        this.getGoodsInfoByGoodsId();
    },
    methods:{
        getGoodsInfoByGoodsId() {
            this.$axios.post('/getGoodsInfoByGoodsId', {
                goodsId: this.$route.params.goodsId
            })
            .then(res=>{
                const goods = res.data.data;
                this.goods.goodsAvatar = goods.goodsAvatar;
                this.goods.goodsname = goods.goodsname;
                this.goods.goodsCategory = goods.goodsCategory;
                this.goods.introduction = goods.introduction;
                this.goods.goodsStock = goods.goodsStock;
                this.goods.goodsPrice = parseFloat(goods.goodsPrice);
                this.isFavorited();
            })
        },
        handleChange() {
            console.log(this.num)
            if(this.num){
                this.totalPrice = this.num * this.goods.goodsPrice
            } else{
                this.totalPrice = this.goods.goodsPrice
                this.$message.error("请输入正整数！")
            }
        },
        isFavorited() {
            this.$axios.post('/isFavorited',{
                goodsId: this.goods.goodsId,
                username: localStorage.getItem("username")
            }).then(res => {
                if(res.data.state == window.SUCCESS) {
                    this.favorited = true;
                }
            })
        },
        addToCart() {
            console.log(this.num)
            this.$axios.post('/addToCart',{
                goodsId: this.goods.goodsId,
                username: localStorage.getItem("username"),
                num: this.num
            }).then(res => {
                if(res.data.state == window.SUCCESS) {
                    this.$message.success("已成功添加至购物车！");
                } else {
                    this.$message.error("出现异常，请重试！");
                }})
        },
        addToFavorites() {
            this.$axios.post('/addToFavorites',{
                goodsId: this.goods.goodsId,
                username: localStorage.getItem("username")
            }).then(res => {
                if(res.data.state == window.SUCCESS) {
                    this.$message.success("已成功收藏！");
                    this.isFavorited = true;
                } else {
                    this.$message.error("出现异常，请重试！");
                }})
        },
        Unfavorite() {
            this.$axios.post('/Unfavorite',{
                goodsId: this.goods.goodsId,
                username: localStorage.getItem("username")
            }).then(res => {
                if(res.data.state == window.SUCCESS) {
                    this.$message.success("已取消收藏！");
                    this.isFavorited = false;
                } else {
                    this.$message.error("出现异常，请重试！");
                }})
        }
    }
}

</script>

 
<style scoped>
 .el-steps{
  margin: 15px 0;
}
.el-step__title{
  font-size: 13px;
}

</style>