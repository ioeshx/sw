<!--此组件为将所有向已注册成功的店铺信息打包，并排版为用户友好的UI界面-->
<script setup>
import ShopShow from '../../ShopGoodsPage/ShopShow.vue'
</script>

<template>
    <div class="wrap" >
        <div v-if="shoplist.length === 0">
            <p>更多商家入驻中...</p>
        </div>
        <div v-else class="shops" v-for="shop in shoplist" :key="shop.id">
            <ShopShow :shop="shop"/>
        </div>
    </div>
</template>

<script>
export default {
    props: ['shop_id'],
    data() {
        return {
            shoplist: [],
            shop: {
                shopname:"shopname",
                goodstype: "",
                introduction: ""
            }
        }
    },
    created (){
        this.getShopList()
    },
    methods: {
        getShopList() {
            this.$axios.get('/getRegisteredShop')
            .then(res => {
                this.shoplist = this.shoplist.concat(res.data.data);
            })
            this.shoplist = [this.shop]
        }
    }
}
</script>


<style scoped>
.wrap {
    width: 100%;
    height: auto;
    padding: 25px 25px 25px 35px;
    line-height: 100px;
    align-items: center;
    justify-items: center;
    display:flex;
    flex-wrap: wrap;
    background-color: #fff;
    border-radius: 15px;
    color: #303133;
    border: 2px solid #ebeef5;
    box-shadow: 2px 2px 12px 2px rgb(0 0 0 / 10%);
}
</style>