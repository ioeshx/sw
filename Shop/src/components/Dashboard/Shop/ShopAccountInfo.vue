<!--商店余额显示+充值-->
<template>
    <p class="text">商店账户余额:{{ this.shopAccount }}</p>
    <div class="charge">
        <el-input v-model='credit' class="chargeInput"></el-input>
        <el-button @click="recharge">充值</el-button>
    </div>
</template>

<script>
import "../../../constant"
export default{
    data() {
        return {
            shopAccount: 0,
            credit: 0
        }
    },
    created (){
        this.getShopAccount();
    },
    methods: {
        getShopAccount() {
            var localStorage = window.localStorage;
            this.$axios.post("/getShopAccount", {
                username: localStorage.getItem("username")
            }).then(res => {
                console.log(res.data.data);
                this.shopAccount = res.data.data;
            }).catch((err) => {
                console.log(err);
                this.$message.error("获取账户信息失败，请重试");
            });
        },
        recharge: function() {
            var localStorage = window.localStorage;
            console.log(this.credit);
            if (isNaN(Number(this.credit))||this.credit<=0){
                this.$message.error("充值失败，请输入符合要求的正浮点数");
            } else {
                const credit = String(this.credit);
                const regex = /^\d+(\.\d{0,2})?/;
                const match = credit.match(regex);
                const formattedValue = match ? match[0] : '0';
                this.credit = parseFloat(formattedValue);
                this.$axios.post('/recharge', {
                    username: localStorage.getItem("username"),
                    // accountType为2表示为商店账户充值
                    accountType: 2,
                    credit: String(this.credit)
                })
                .then(res => {
                    if(res.data.state==window.SUCCESS) {
                        console.log(res.data.data);
                        this.$message.success("充值金额：" + this.credit + "（系统默认保留两位小数）！");
                        this.getShopAccount();
                        this.credit = 0;
                    }
                    else {
                        console.log("failure");
                    }
                })
            }
        }
    }
}
</script>

<style scoped>
.text{
    margin:10px;
}
.chargeInput{
    width:40%;
    margin:10px
}  

</style>
