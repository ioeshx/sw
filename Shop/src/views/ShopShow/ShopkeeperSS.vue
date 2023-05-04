<script setup>
import Nav from '../../components/Public/Nav/ShopkeeperNav.vue'
import ShopDetail from '../../components/ShopGoodsPage/ShopDetail.vue'
import { RouterLink, RouterView } from 'vue-router'
</script>

<template>
    <body>
        <Nav></Nav>
        <section class="back">
            <ShopDetail :shopname="shopname"/>
        </section>
    </body>
</template>

<script>
export default {
    data(){
        return {
            shopname: ""
        }
    },
    components: {
        ShopDetail
    },
    created() {
        console.log("SS")
        this.shopname = this.$route.params.shopname;
        console.log(this.shopname);
    }
}
</script>

<style scoped>

.back{
    margin-top: 20px;
    padding-bottom: 30px;
    display: flex;
}
</style>