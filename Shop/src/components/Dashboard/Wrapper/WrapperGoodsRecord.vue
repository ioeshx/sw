<!--已申请未批复+已申请被拒绝-->
<script setup>
    import WrapperRegisterGoods from './WrapperRegisterGoods.vue';
    import WrapperEditGoods from './WrapperEditGoods.vue';
</script>

<template>
    <el-tabs tab-position="left">
        <el-tab-pane label="注册申请">
            <WrapperRegisterGoods class="cont"/>
        </el-tab-pane>
        <el-tab-pane label="修改申请">
            <WrapperEditGoods class="cont"/>
        </el-tab-pane>
    </el-tabs>
    
    
</template>
<script>
export default {
    components: {
        WrapperRegisterGoods,
        WrapperEditGoods
    }
}
</script>

<style scoped>
.cont{
    width: 90%;
    margin:10px auto 50px;
}
</style>