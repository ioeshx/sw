<script setup>
import Nav from '../../components/Public/Nav/UserNav.vue'
import ShoppingList from '../../components/ShoppingList/ShoppingList.vue'
import { RouterLink, RouterView } from 'vue-router'
</script>

<template>
    <body>
        <Nav></Nav>
        <section class="back">
            <ShoppingList />
        </section>
    </body>
</template>

<script>
import { interceptor, userInterceptor } from "../../interceptor";
export default{
    created(){
        interceptor(this);
        userInterceptor(this);
    }
}
</script>

<style scoped>
body{
    background-color: rgba(128,128,128,0.1);
}
.back{
    margin-top: 20px;
    padding-bottom: 30px;
    display: flex;
}
</style>