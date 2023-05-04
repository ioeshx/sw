<!-- 用户可见的商品详情页面 -->
<template>

<div class="image">
    <img :src="'data:image/jpeg;base64,' + goodsAvatar[currentPage-1]" class="showpics">
    <el-pagination
        class="pages"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-size="pageSize"
        :total="totalAvatars"
    />
</div>

</template>
 
<script>
export default {
    props: {
        goodsAvatar: []
    },
    data() {
        return {
            currentPage: 1, // 当前页码
            pageSize: 1, // 每页显示数量
            totalAvatars: 0 // 总图片数量
        };
    },
    created() {
        this.totalAvatars = this.goodsAvatar.length;
    },
    computed: {
        currentAvatars() {
            // 计算当前页需要显示的图片
            const start = (this.currentPage - 1) * this.pageSize
            const end = start + this.pageSize
            return this.goods.goodsAvatar.slice(start, end)
        }
    },
    methods: {
        handleCurrentChange(newPage) {
            this.currentPage = newPage
        }
    }
}

</script>

 
<style scoped>



.showpics{
    width:80%;
    height: 50%;
    background-color:rgba(128,128,128,0.3);
    margin-bottom: 10px;
    margin-left: 20px;
}

.pages{
    gap:0px;
    display:flex;
    flex-direction: column;
    margin:0px;
}

</style>