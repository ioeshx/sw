<!-- 商铺流水详情 -->

<template>
    TODO：transaction
</template>

<script>
import "../../../constant"
export default{
    data() {
        return {
            transactions: [],
            transaction: {
                counterpart: "username",
                amount: "",
                
            }
        }
    },
    created (){
       // this.getShopTransactions();
    },
    methods: {
        getShopTransactions() {
            this.transactions = [this.goods];
            console.log(this.registeringGoodsList);
            var localStorage = window.localStorage;
            this.$axios.post("/getRegisteringGoodsByShopname", {
                shopname: localStorage.getItem("shopname")
            }).then(res => {
                this.registeringGoodsList = res.data.data;
            })
        }
    }
}
</script>

<style scoped>

.cont{
    width: 90%;
    margin:10px auto 50px;
}

</style>
