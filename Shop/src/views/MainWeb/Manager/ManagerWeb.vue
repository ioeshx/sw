<!--管理员主页面，会显示所有店铺注册请求-->
<script setup>
import WrapperManager from '../../../components/MainWeb/Wrapper/WrapperManager.vue'
import SearchM from '../../../components/MainWeb/Components/SearchM.vue'
import Nav from '../../../components/Public/Nav/ManagerNav.vue'
import { RouterLink, RouterView } from 'vue-router'
</script>

<template>
        
    <Nav></Nav>

    <section>
        <SearchM/>
    </section>
        
    <section class="show">
        <WrapperManager />
    </section>


</template>

<script>
import { interceptor, managerInterceptor } from "../../../interceptor";
export default {
    created(){
        interceptor(this);
        managerInterceptor(this);
    }
}
</script>



<style scoped>
/*展示栏*/
.show{
    margin: 20px 50px 0px 50px;
}
</style>