<!--商家首页，和普通用户基本相同，添加了‘我的店铺’链接-->
<script setup>
import WrapperUser from '../../../components/MainWeb/Wrapper/WrapperUser.vue'
import SearchTop from '../../../components/MainWeb/Components/SearchU.vue'
import Nav from '../../../components/Public/Nav/ShopkeeperNav.vue'
</script>

<template>
    <Nav></Nav>

    <section>
        <SearchTop />
    </section>
        
    <section class="show">
        <h1 class="title">Let's have fun!</h1>
        <br>
        <WrapperUser class="margin"/>
    </section>

</template>

<script>
import { interceptor, shopkeeperInterceptor } from "../../../interceptor";
export default {
    created(){
        interceptor(this);
        shopkeeperInterceptor(this);
    }
}
</script>

<style scoped>


/*商家注册信息栏*/
.register_show{
    margin:100px;
}

/*商铺展示*/
.show{
    margin: 10px 50px 0px 50px;
}
.show .title{
    margin-bottom: 10px;
    text-align: center;
    color:#81A18B;
    text-shadow: 2px 2px 2px #195844;
    font-size: 45px;
    font-family: "Lucida Console", "Courier New", monospace;
}

.margin{
    height: auto;
    margin-bottom: 100px;
}
</style>