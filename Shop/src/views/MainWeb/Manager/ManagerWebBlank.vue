<!--此页面用于管理员页面跳转过渡-->
<template>

</template>

<script>
import { interceptor, managerInterceptor } from "../../../interceptor";
export default {
    created(){
        interceptor(this);
        managerInterceptor(this);
    },
    data (){
        this.$router.push({name:'ManagerWeb'});
    }
}
</script>