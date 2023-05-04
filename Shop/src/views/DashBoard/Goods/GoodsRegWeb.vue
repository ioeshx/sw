<!--此页面为商家注册商品时所用的页面-->
<script setup>
import navS from '../../../components/Public/Nav/ShopkeeperNav.vue'
import goodsReg from '../../../components/Dashboard/Goods/GoodsRegister.vue'
</script>


<template>
    <navS></navS>
    <section class="registerShow">
        <goodsReg></goodsReg>
    </section>
</template>

<style scoped>
.registerShow{
    margin:40px 100px 0px 100px;
}
</style>