<!--商城中间账户余额显示+充值-->
<template>
    <p class="charge">中间账户余额: {{ this.intermediaryAccount }}</p>
    <div class="chargeBtn">
        <el-input class="input" v-model='credit'></el-input>
        <el-button class="btn" @click="recharge">充值</el-button>
    </div>
</template>

<script>
import "../../../constant"
export default{
    data() {
        return {
            intermediaryAccount: 0,
            credit: 0
        }
    },
    created (){
        this.getIntermediaryAccount();
    },
    methods: {
        getIntermediaryAccount() {
            this.$axios.get('/getIntermediaryAccount')
            .then(res => {
                console.log(res.data.data)
                this.intermediaryAccount = res.data.data;
            })
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
                        this.$message.success("充值金额：" + this.credit + "（系统默认保留两位小数）！");
                        this.intermediaryAccount = res.data.data;
                        this.credit = 0;
                        console.log(res.data.data);
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

.charge{
    margin-left:40px;
}

.chargeBtn{
    margin-left:40px;
    display: flex;
    gap:20px;
}

.input{
    width:20%;
}


</style>
