<!--商店余额显示+充值-->
<template>
    <p class="profit">利润:{{ this.profitAccount }}</p>
</template>

<script>
export default{
    data() {
        return {
            profitAccount: 0
        }
    },
    created (){
        this.getProfitAccount();
    },
    methods: {
        getProfitAccount() {
            this.$axios.get('/getProfitAccount')
            .then(res => {
                this.profitAccount = res.data.data;
            }).catch((err) => {
                console.log(err);
                this.$message.error("获取利润失败，请重试");
            });
        }
    }
}
</script>

<style scoped>

.profit{
    margin-left:40px;
}

</style>
