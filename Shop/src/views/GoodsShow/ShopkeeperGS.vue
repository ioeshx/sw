<script setup>
import Nav from '../../components/Public/Nav/ShopkeeperNav.vue'
import GoodsDetail from '../../components/ShopGoodsPage/GoodsDetail.vue'
import { RouterLink, RouterView } from 'vue-router'
</script>

<template>
    <body>
        <Nav></Nav>
        <section class="back">
            <GoodsDetail/>
        </section>
    </body>
</template>

<style scoped>

.back{
    padding-bottom: 30px;
    margin:20px auto;
}
</style>