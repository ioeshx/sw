<script setup>
    import GoodsShow from '../Goods/GoodsShow.vue'
    import GoodsShow4Shopkeeper from '../Goods/GoodsShow4Shopkeeper.vue';
    import GoodsInfo4Shopkeeper from '../Goods/GoodsInfo4Shopkeeper.vue';
</script>

<template>
    <div class="wrap">
        <el-tabs tab-position="left">
            <el-tab-pane label="在售商品">
                <div class="show" v-if="!isShowGoodsDetail">
                    <h1>在售商品表</h1>
                    <div class="goods">
                        <div class="goods" v-for="goods in validGoodsList" :key="goods.goodsId">
                            <GoodsShow4Shopkeeper @showGoodsDetail="showGoodsDetail" :goods="goods"/>

                        </div>
                    </div>
                    <div class="pages">
                        <el-button class="pageBtn">上一页</el-button>
                        <span>1/10</span>
                        <el-button class="pageBtn">下一页</el-button>
                    </div>
                </div>

                <div v-else>
                    <GoodsInfo4Shopkeeper :goods="showGoods" @cancelShowGoodsDetail="cancelShowGoodsDetail" />
                </div>
            </el-tab-pane>

            <el-tab-pane label="已下架">
                <div class="show" v-if="!isShowGoodsDetail2">
                    <h1>已下架商品表</h1>
                    <div class="goods">
                        <div class="goods" v-for="goods in invalidGoodsList" :key="goods.goodsId">
                            <GoodsShow4Shopkeeper @showGoodsDetail="showGoodsDetail2" :goods="goods"/>
                        </div>
                    </div>
                    <div class="pages">
                        <el-button class="pageBtn">上一页</el-button>
                        <span>1/10</span>
                        <el-button class="pageBtn">下一页</el-button>
                    </div>
                </div>

                <div v-else>
                    <GoodsInfo4Shopkeeper :goods="showGoods" @cancelShowGoodsDetail="cancelShowGoodsDetail2" />
                </div>
            </el-tab-pane>
        </el-tabs>
    </div>

</template>
<script>
import '../../../constant'
export default {
    data() {
        return {
            validGoodsList: [],
            invalidGoodsList: [],
            goods:{
                goodsname: "goodsname",
                goodsId: "id",
                status: 0,
                goodsPrice:100,
                goodsStock:20,
                introduction:"intro",
                goodsCategory: ['电脑数码', '家用电器'],
                favorites:0,
                goodsAvatar: []
            },
            isShowGoodsDetail: false,
            showGoods: {
                goodsname: "",
                goodsId: "",
                registerStatus: 0,
                modifyStatus: 0,
                status: 0,
                goodsPrice:0,
                goodsStock:0,
                introduction:"",
                goodsCategory: [],
                favorites:0,
                goodsAvatar: []
            },
            isShowGoodsDetail2: false,
            showGoods2: {
                goodsname: "",
                goodsId: "",
                registerStatus: 0,
                modifyStatus: 0,
                status: 0,
                goodsPrice:0,
                goodsStock:0,
                introduction:"",
                goodsCategory: [],
                favorites:0,
                goodsAvatar: []
            }
        }
    },
    created (){
        this.getValidGoods();
        this.getInvalidGoods();
    },
    methods: {
        getValidGoods() {
            var localStorage = window.localStorage;
            console.log("getValidGoods")
            this.$axios.post("/getValidGoodsByShopname", {
                shopname: localStorage.getItem("shopname")
            }).then(res => {
                this.validGoodsList = res.data.data;
                console.log(this.validGoodsList)
            })
        },
        getInvalidGoods() {
            var localStorage = window.localStorage;
            this.$axios.post("/getInvalidGoodsByShopname", {
                shopname: localStorage.getItem("shopname")
            }).then(res => {
                this.invalidGoodsList = res.data.data;
            })
        },
        showGoodsDetail(arg) {
            console.log("showGoodsDetail")
            console.log(arg.goods)
            this.showGoods.goodsname = arg.goods.goodsname
            this.showGoods.goodsId = arg.goods.goodsId
            this.showGoods.registerStatus = arg.goods.registerStatus
            this.showGoods.modifyStatus = arg.goods.modifyStatus
            this.showGoods.status = arg.goods.status
            this.showGoods.goodsPrice = arg.goods.goodsPrice
            this.showGoods.goodsStock = arg.goods.goodsStock
            this.showGoods.introduction = arg.goods.introduction
            this.showGoods.goodsCategory = arg.goods.goodsCategory
            this.showGoods.favorites = arg.goods.favorites
            this.showGoods.goodsAvatar = arg.goods.goodsAvatar
            console.log(this.showGoods)
            this.isShowGoodsDetail = true;
        },
        cancelShowGoodsDetail() {
            this.isShowGoodsDetail = false
        },
        showGoodsDetail2(arg) {
            console.log(arg.goods)
            this.showGoods.goodsname = arg.goods.goodsname
            this.showGoods.goodsId = arg.goods.goodsId
            this.showGoods.registerStatus = arg.goods.registerStatus
            this.showGoods.modifyStatus = arg.goods.modifyStatus
            this.showGoods.status = arg.goods.status
            this.showGoods.goodsPrice = arg.goods.goodsPrice
            this.showGoods.goodsStock = arg.goods.goodsStock
            this.showGoods.introduction = arg.goods.introduction
            this.showGoods.goodsCategory = arg.goods.goodsCategory
            this.showGoods.favorites = arg.goods.favorites
            this.showGoods.goodsAvatar = arg.goods.goodsAvatar
            console.log(this.showGoods)
            this.isShowGoodsDetail2 = true;
        },
        cancelShowGoodsDetail2() {
            this.isShowGoodsDetail2 = false
        }
    }
}
</script>

<style scoped>

.show{
    margin:10px 30px 50px 30px;
    padding: 30px;
    background-color: #fff;
    border-radius: 15px;
    color: #303133;
    border: 3px solid #ebeef5;
    transition: .3s;

}
.show h1{
  text-align: left;
  font-family:"Lucida Console", "Courier New", monospace;
  font-size: 30px;
  margin-bottom:40px;
  font-weight: bolder;
  color:#81A18B;
}

.goods{
    display: flex;
    flex-wrap: wrap;
}

/*翻页按钮*/
.pages{
    display: flex;
    margin:50px auto 0px;
}
.pages span{
    align-content:end;
    color:#81A18B;
    font-weight: bolder;
}
.pageBtn{
    display: flex;
    margin:0 auto;
}
</style>