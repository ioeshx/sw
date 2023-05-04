<!--普通用户首页-->
<script setup>
import WrapperUser from '../../../components/MainWeb/Wrapper/WrapperUser.vue'
import SearchTop from '../../../components/MainWeb/Components/SearchU.vue'
import Nav from '../../../components/Public/Nav/UserNav.vue'
import { RouterLink, RouterView } from 'vue-router'
import { ref } from 'vue'
import { Search } from '@element-plus/icons-vue'
const input = ref('')
const select = ref('')
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
import { interceptor, userInterceptor } from "../../../interceptor";
export default {
    created(){
        interceptor(this);
        userInterceptor(this);
    }
}
</script>


<style scoped>


/*展示栏*/
.show{
    margin: -10px 50px 0px 50px;
}
.show .title{
    margin-top:10px;
    margin-bottom: 10px;
    text-align: center;
    color:#81A18B;
    text-shadow: 2px 2px 2px #195844;
    font-size: 45px;
    font-family: "Lucida Console", "Courier New", monospace;
}

.margin{
    margin-bottom: 100px;
}
</style>