<template>

</template>

<script>
import { interceptor,shopkeeperInterceptor } from "../../../interceptor";
export default {
    data (){
        this.$router.push({name:'ShopDashboard'});
    },
    created(){
        interceptor(this);
        shopkeeperInterceptor(this);
    }
}
</script>