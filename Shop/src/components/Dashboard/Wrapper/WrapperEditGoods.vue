<script setup>
    import GoodsShow from '../Goods/GoodsShow.vue';
    import GoodsShow4Shopkeeper from '../Goods/GoodsShow4Shopkeeper.vue';
    import GoodsInfo4Shopkeeper from '../Goods/GoodsInfo4Shopkeeper.vue';
</script>

<template>
    <div class="wrap" v-if="!isShowGoodsDetail">
        <div class="edit">
            <h2>待审核</h2>
            <div class="goods">
                <div class="goods" v-for="goods in editingGoodsList" :key="goods.goodsId">
                    <GoodsShow4Shopkeeper @showGoodsDetail="showGoodsDetail" :goods="goods"/>
                </div>
            </div>
            <div class="pages">
                <el-button class="pageBtn">上一页</el-button>
                <span>1/10</span>
                <el-button class="pageBtn">下一页</el-button>
            </div>
        </div>

        <div class="edit">
            <h2>修改失败</h2>
            <div class="goods">
                <div class="goods" v-for="goods in editDeniedGoodsList" :key="goods.goodsId">
                    <GoodsShow4Shopkeeper @showGoodsDetail="showGoodsDetail" :goods="goods"/>
                </div>
            </div>
            <div class="pages">
                <el-button class="pageBtn">上一页</el-button>
                <span>1/10</span>
                <el-button class="pageBtn">下一页</el-button>
            </div>
        </div>

        <div class="edit">
            <h2>修改成功</h2>
            <div class="goods">
                <div class="goods" v-for="goods in editedGoodsList" :key="goods.goodsId">
                    <GoodsShow4Shopkeeper @showGoodsDetail="showGoodsDetail" :goods="goods"/>
                </div>
            </div>
            <div class="pages">
                <el-button class="pageBtn">上一页</el-button>
                <span>1/10</span>
                <el-button class="pageBtn">下一页</el-button>
            </div>
        </div>
     
    </div>

    <div v-else>
        <GoodsInfo4Shopkeeper :goods="showGoods" @cancelShowGoodsDetail="cancelShowGoodsDetail" />
    </div>

</template>
<script>
import '../../../constant'
export default {
    data() {
        return {
            editingGoodsList: [],
            editedGoodsList: [],
            editDeniedGoodsList: [],
            goods:{
                goodsname: "goodsname",
                goodsId: "id",
                registerStatus: 0,
                modifyStatus: 0,
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
            }
        }
    },
    created (){
        this.getEditingGoods();
        this.getEditedGoods();
        this.getEditDeniedGoods();
    },
    methods: {
        getEditingGoods() {
            var localStorage = window.localStorage;
            this.$axios.post("/getEditingGoodsByShopname", {
                shopname: localStorage.getItem("shopname")
            }).then(res => {
                this.editingGoodsList = res.data.data;
                console.log("editingGoodsList")
                console.log(this.editingGoodsList)
            })
        },
        getEditedGoods() {
            var localStorage = window.localStorage;
            this.$axios.post("/getEditedGoodsByShopname", {
                shopname: localStorage.getItem("shopname")
            }).then(res => {
                this.editedGoodsList = res.data.data;
            })
        },
        getEditDeniedGoods() {
            var localStorage = window.localStorage;
            this.$axios.post("/getEditDeniedGoodsByShopname", {
                shopname: localStorage.getItem("shopname")
            }).then(res => {
                this.editDeniedGoodsList = res.data.data;
            })
        },
        showGoodsDetail(arg) {
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
        }
    }
}
</script>

<style scoped>
/*小标题*/
.wrap h2{
  text-align: left;
  font-family:"Lucida Console", "Courier New", monospace;
  font-size: 30px;
  font-weight: bolder;
  color:#81A18B;
  margin-bottom: 30px;
}

/*包装框*/
.edit{
    padding: 30px;
    background-color: #fff;
    border-radius: 15px;
    color: #303133;
    border: 3px solid #ebeef5;
    transition: .3s;
    margin-bottom: 40px;
}

/*商品陈列*/
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