<!-- 用户可见的商品详情页面 -->
<template>

    <div class="wrap">
        

        <div class="shopName">
            <h1 class="title">{{ goods.shopname }}</h1>
        </div>

        <div class="infoBuy">
            <!-- TODO: 图片美化（每个商品的img数量不定，建议做成点击放大的缩略图）-->
            <div class="image">
                <ShowPics :goodsAvatar="goods.goodsAvatar" />

            </div>
            
            <div class="info">
                <div class="basicInfo">
                    <p>商品名：{{ goods.goodsname }}</p>
                    <p>商品类别：{{ goods.goodsCategory }}</p>
                    <p>商品简介：{{ goods.introduction }}</p>
                    <div class="left">
                        <p>商品价格：￥{{ goods.goodsPrice }}</p>
                        <p>商品库存：{{ goods.goodsStock }}</p>
                    </div>
                </div>
                
                <div class="buyInfo">
                    <p>收藏量：{{ goods.favorites }}</p>

                    <!-- TODO: 累计销量-->
                    <p>购买量：0</p>
                </div>
                
                <div class="buy">
                    <div class="add">
                        <el-input-number v-model="num" :min="1" @change="handleChange" :value-on-clear="1" />
                        <p>总价：{{ this.totalPrice }}</p>
                    </div>
                    <div class="buyBtn">
                        <el-button class="btn" @click="addToCart"> 加入购物车 </el-button>
                        <el-button class="btn"> 购买 </el-button>
                        <!-- TODO: 收藏美化 -->
                        <el-button class="btn" v-if="favorited" @click="Unfavorite"> 取消收藏 </el-button>
                        <el-button class="btn" v-else @click="addToFavorites"> 收藏 </el-button>
                    </div>
                    
                </div>

            </div>
        </div>

        <div class="detail">
            <el-tabs type="border-card">
                <el-tab-pane label="商品详情">预留</el-tab-pane>
                <el-tab-pane label="评价">预留</el-tab-pane>
                <el-tab-pane label="专享服务">预留</el-tab-pane>
            </el-tabs>
        </div>

    </div>
</template>
 
<script>
import "../../constant";
import ShowPics from "../../components/Public/Upload/ShowPics.vue"
export default {
    props: {
        goodsId:{
            type: String,
            required: true
        }
    },
    components: {
        ShowPics
    },
    data() {
        return {
            goods: {
                goodsAvatar: [],
                goodsname: "商品名",
                goodsCategory: [],
                introduction: "intro",
                goodsStock: 23,
                goodsPrice: 23,
                favorites: 0,
                shopname: ""
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
        this.getGoodsInfoByGoodsId();
    },
    methods:{
        getGoodsInfoByGoodsId() {
            this.$axios.post('/getGoodsInfoByGoodsId', {
                goodsId: this.goodsId
            })
            .then(res=>{
                const goods = res.data.data;
                this.goods.goodsAvatar = goods.goodsAvatar;
                this.goods.goodsname = goods.goodsname;
                this.goods.goodsCategory = goods.goodsCategory;
                this.goods.introduction = goods.introduction;
                this.goods.goodsStock = goods.goodsStock;
                this.goods.favorites = goods.favorites;
                this.goods.goodsPrice = parseFloat(goods.goodsPrice);
                this.totalPrice = this.goods.goodsPrice;
                this.goods.shopname = goods.shopname;
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
            this.$axios.post('/isFavorite',{
                goodsId: parseInt(this.goodsId),
                username: localStorage.getItem("username")
            }).then(res => {
                console.log("isfa")
                console.log(res.data)
                if(res.data.state == window.SUCCESS) {
                    this.favorited = true;
                }
            })
        },
        addToCart() {
            this.$axios.post('/addToCart',{
                goodsId: parseInt(this.goodsId),
                username: localStorage.getItem("username"),
                num: this.num
            }).then(res => {
                console.log(res.data)
                if(res.data.state == window.SUCCESS) {
                    this.$message.success("已成功添加至购物车！");
                } else {
                    this.$message.error("出现异常，请重试！");
                }})
        },
        addToFavorites() {
            this.$axios.post('/addToFavorites',{
                goodsId: parseInt(this.goodsId),
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
                goodsId: parseInt(this.goodsId),
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

.wrap{
    display: grid;
    gap:30px;
    height:auto;
    margin:30px auto 50px;
}
.wrap .title{
    width:40%;
    margin: 10px auto;
    padding: 10px 0;
    text-align: center;
    color:#81A18B;
    font-size: 45px;
    font-family: "Brush Script MT", cursive;
    border: 2px solid #ebeef5;
    border-radius: 10px;
}

.wrap .infoBuy{
    padding: 40px 40px;
    margin: 0px auto;
    border: 2px solid #ebeef5;
    border-radius: 5px;
    width: 90%;
    display: grid;
    grid-template-columns: 40% 60%;
    column-gap: 30px;
}

.wrap .detail{
    margin:0 auto;
    width: 90%;
}

.image img{
    width:90%;
    height: 90%;
    background-color:rgba(128,128,128,0.3);
    margin-bottom: 10px;
}

.info{
    display: flex;
    flex-direction: column;
    gap:10px;
    font-family: "Brush Script MT", cursive;
    font-size: 20px;
}
.info .basicInfo{
    line-height: 30px;
    background-color: rgba(129,161,139,0.1);
    padding: 20px;
    width:fit-content;
}

.info .left{
    display: flex;
    gap:20px;
}

.info .buyInfo{
    margin:20px 0;
    padding: 20px;
    width:fit-content;
    display: flex;
    gap:20px;
    background:#FFF2E8;
    
}
.buy{
    display: flex;
    flex-direction: column;
    gap:20px;
}

.buy .add{
    display: flex;
    gap:20px;
}
.buy .buyBtn{
    margin-top:20px;
    display: flex;
    gap:10px; 
}

.btn{
    width:30%
}
</style>