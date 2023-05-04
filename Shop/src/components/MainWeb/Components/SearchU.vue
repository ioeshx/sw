<!--此组件为用户的店铺搜索引擎及相关组件-->
<script setup>
import { ref } from 'vue'
import { Search } from '@element-plus/icons-vue'
const input = ref('')
const select = ref('')
</script>


<template>
    <div class="top">
        <img class="logo" src="@/assets/logo.png" />
        <div class="search">
            <el-input
            v-model="input"
            placeholder="请输入搜索内容"
            class="input-with-select"
            >
                <template #prepend>
                    <el-select v-model="select" placeholder="请选择" style="width: 115px">
                        <el-option label="宝贝" value="1" />
                        <el-option label="店铺" value="2" />
                    </el-select>
                </template>
                <template #append>
                    <el-button :icon="Search" />
                </template>
            </el-input>
        </div>
        <p>Contact Us</p>
    </div>
</template>


<style scoped>

.top {
    display: grid;
    grid-template-columns: 15% 75% 10%;
    grid-template-rows: 150px;
    margin-top:30px;
    margin-left: 40px;
    margin-right:150px;
    grid-column-gap:50px;
    align-items: center;
    align-content:stretch;
}
.top img{
    grid-column: 1/2;
    width: 100%;
    height: 100%
}
.top .search{
    grid-column: 2/3; 
    text-align: center;
}
.search .el-input-group__prepend {
  background-color: var(--el-fill-color-blank);
}
.top p{
    grid-column: 3/4;
    left: 30px;
    text-align: left;
    color: #81A18B;
    text-decoration: underline;
    font-size: 18px;
    font-weight: bold;
}


</style>
