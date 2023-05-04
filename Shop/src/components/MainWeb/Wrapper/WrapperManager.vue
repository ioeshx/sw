<!--此组件为将所有向管理员发送注册请求的店铺打包，并排版为管理员友好的UI界面-->
<script setup>
import ShopShow4Manager from '../Components/ShopShow4Manager.vue'
import GoodsShow4Manager from '../../Dashboard/Goods/GoodsShow4Manager.vue';
import ProfitAccount from '../../../components/Dashboard/Manager/ProfitAccount.vue'
import IntermediaryAccount from '../../../components/Dashboard/Manager/IntermediaryAccount.vue'
</script>

<template>
    <div class="wrap" >
        <el-tabs tab-position="left">
            <el-tab-pane label="店铺管理">
                <h1>待处理</h1>
                <div class="shops">
                    <div class="shop" v-for="shop in registeringShopList" :key="shop.id">
                        <ShopShow4Manager :shop="shop"/>
                    </div>
                </div>
                <div class="ge"></div>
                <h1>已处理</h1>
                <div class="shops">
                    <div class="shop" v-for="shop in registeredShopList" :key="shop.id">
                        <ShopShow4Manager :shop="shop"/>
                    </div>
                </div>
            </el-tab-pane>
            <el-tab-pane label="商品管理">
                <h1>全部申请</h1>
                <div class="goods">
                    <div class="good" v-for="goods in approvingGoodsList" :key="goods.goodsId">
                        <GoodsShow4Manager :goods="goods" v-on:reload="getApprovingGoods"/>
                    </div>
                </div>
            </el-tab-pane>
            <el-tab-pane  label="账户信息">
                <el-tabs tab-position="left">
                    <el-tab-pane label="商城利润">
                        <ProfitAccount />
                    </el-tab-pane>
                    <el-tab-pane label="中间账户信息">
                        <IntermediaryAccount />
                    </el-tab-pane>
                </el-tabs>

            </el-tab-pane>
        </el-tabs>
        
    </div>
</template>

<script>
export default {
    props: ['username'],
    data() {
        return {
            registeredShopList: [],
            registeringShopList: [],
            approvingGoodsList: [],
            profitAccount: 0,
            intermediaryAccount: 0,
            goods:{
                goodsname: "goodsname",
                goodsId: "id",
                shopname: "shopname",
                status: 0,
                goodsPrice:100,
                goodsStock:20,
                introduction:"intro",
                goodsCategory: ['电脑数码', '家用电器'],
                goodsAvatar: []
            }
        }
    },
    created (){
        this.getRegisteredShop();
        this.getRegisteringShop() ;
        this.getApprovingGoods();
    },
    methods: {
        getRegisteredShop() {
            this.$axios.get('/getRegisteredShop')
            .then(res => {
                this.registeredShopList = this.registeredShopList.concat(res.data.data);
            })
        },
        getRegisteringShop() {
            this.$axios.get('/getRegisteringShop')
            .then(res => {
                this.registeringShopList = this.registeringShopList.concat(res.data.data);
            })
        },
        getApprovingGoods() {
            this.approvingGoodsList = []
            this.$axios.get('/getApprovingGoods')
            .then(res => {
                this.approvingGoodsList = this.approvingGoodsList.concat(res.data.data);
                console.log("getApprovingGoods")
                console.log(this.approvingGoodsList)
            })
        },
        getProfitAccount() {
            this.$axios.get('/getProfitAccount')
            .then(res => {
                this.profitAccount = res.data.data.profitAccount;
            })
        },
        getIntermediaryAccount() {
            this.$axios.get('/getIntermediaryAccount')
            .then(res => {
                this.intermediaryAccount = res.data.data.intermediaryAccount;
            })
        }
    }
}
</script>

<style scoped>
.wrap {
    width: 100%;
    height: auto;
    padding: 25px 25px 25px 10px;
    margin-bottom: 40px;
    line-height: 100px;
    align-items: center;
    justify-items: center;

    background-color: #fff;
    border-radius: 15px;
    color: #303133;
    border: 2px solid #ebeef5;
    box-shadow: 2px 2px 12px 2px rgb(0 0 0 / 10%);
    transition: .3s;
}

h1{
    text-align: center;
    font-weight: bolder;
    font-size: 30px;
}
.shops{
    display:flex;
    overflow: auto;
    flex-wrap: wrap;
    column-gap:40px;
}
.shop{
    width: 300px;
}
.goods{
    display:flex;
    overflow: auto;
    flex-wrap: wrap;
    column-gap:40px;
}
.good{
    width: 300px;
}
.ge{
    width: 100%;
    height:1px;
    margin-top: 30px;
    margin-bottom: 10px;
    border-top:2px solid #ccc;
}
</style>