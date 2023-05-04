<script setup>
import Nav from '../../components/Public/Nav/Nav.vue'
import ShopDetail from '../../components/ShopGoodsPage/ShopDetail.vue'
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
import { interceptor, userInterceptor,userorshopInterceptor } from "../../interceptor";
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
        interceptor(this);
        userorshopInterceptor(this);
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