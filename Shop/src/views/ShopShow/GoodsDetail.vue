<script setup>
import Nav from '../../components/Public/Nav/Nav.vue';
import GoodsDetail from '../../components/ShopGoodsPage/GoodsDetail.vue';
</script>

<template>
    <Nav></Nav>
    <GoodsDetail :goodsId="this.goodsId" />
</template>

<script>
import { interceptor } from "../../interceptor";
export default {
    data(){
        return {
            goodsId: ""
        }
    },
    components: {
        GoodsDetail
    },
    created() {
        interceptor(this);
        console.log("detail")
        this.goodsId = this.$route.params.goodsId;
        console.log(this.goodsId);
    }
}
</script>