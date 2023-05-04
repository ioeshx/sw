<template>
    <section>

        <div class="profile">
            <div class="infoShow">
                
                <img v-if="form.avatar==''" src="../../assets/shop.png" alt="头像">
                <img v-else :src="form.avatar">
                <div class="info">
                    <p>姓名: {{this.form.username}}</p>
                    <p>电话: {{this.form.phone}}</p>
                    
                </div>
            </div>
            <p>身份证号: {{this.form.idnum}}</p>
            <p>邮箱: {{this.form.email}}</p>
            <p>个人账户余额: <num class="account">{{ this.form.account }}</num></p>
            <div class="charge">
                <el-input class="chargeInput" v-model='credit'></el-input>
                <el-button @click="recharge">充值</el-button>
            </div>
        </div>


    </section> 
</template>
<script>
import '../../constant';
export default {
    data() {
      return {
        form: {
          username: "",
          phone: "",
          idnum: "",
          email: "",
          avatar: "",
          account:0
        },
        credit: 0,
      }
    },
    created() {
        this.getUserInfo();
        this.getUserAvatar();
    },
    methods: {
      getUserInfo() {
        var localStorage = window.localStorage;
        console.log("created!");
        this.$axios.post('/getUserInfo', {
            username: localStorage.getItem("username")
        })
        .then(res => {
            const user = res.data.data;
            this.form.username = user.username;
            this.form.phone = user.phone;
            this.form.idnum = user.idnum;
            this.form.email = user.email;
            this.form.password = user.password;
            this.form.account = user.privateAccount;
        })
      },
      getUserAvatar: function() {
        var localStorage = window.localStorage;

        this.$axios.post('/getUserAvatar', {
            username: localStorage.getItem("username")
        })
        .then(res => {
            if(res.data.state==window.SUCCESS) {
                console.log("success");
                this.form.avatar = "data:image/jpeg;base64," + res.data.data;
            }
            else {
                console.log("failure");
            }
        })
      },
      recharge: function() {
        var localStorage = window.localStorage;
        console.log(this.credit);
        if (isNaN(Number(this.credit))||this.credit<=0){
            this.$message.error("充值失败，请输入符合要求的正浮点数");
        } else {
            // 转换为两位小数
            const credit = String(this.credit);
            const regex = /^\d+(\.\d{0,2})?/;
            const match = credit.match(regex);
            const formattedValue = match ? match[0] : '0';
            this.credit = parseFloat(formattedValue);
            this.$axios.post('/recharge', {
                username: localStorage.getItem("username"),
                // accountType为1表示为个人账户充值
                accountType: 1,
                credit: String(this.credit)
            })
            .then(res => {
                if(res.data.state==window.SUCCESS) {
                    this.$message.success("充值金额：" + this.credit + "（系统默认保留两位小数）！");
                    this.form.account = res.data.data;
                }
                else {
                    console.log("failure");
                }
            })
        }
        
      },
      creditValidator(rule, value, callback) {
            if (isNaN(Number(value))) {
                this.credit = 0;
                return callback(new Error("请输入正确的浮点数"));
            }
            else {
                const input = String(value);
                // 使用正则表达式将其转换为保留两位小数的字符串
                const regex = /^\d+(\.\d{0,2})?/;
                const match = input.match(regex);
                const formattedValue = match ? match[0] : '0';
                // 将转换后的值赋给 this.addForm.goodsPrice
                this.credit = parseFloat(formattedValue);
            }
            callback();
        },
  }
}
</script>
<style scoped>
section{
    margin: 0;
}

/*个人信息展示与导航*/
.profile {
    display: flex;
    flex-direction: column;
    grid-column: 1/2;
    padding: 20px;
    margin-left: 30px;
    margin-bottom: 0;
    width: 100%;
    height: auto;
    gap:20px;
    
}

.infoShow{
    display: flex;
}
.profile img {
    width: 100px;
    height: 100px;
    border-radius: 50%;
    margin-right: 20px;
}
.profile .info {
    font-size: 16px;
}
.profile .info p {
    margin: 10px 0;
}

.profile > p{
    letter-spacing:0;
    line-height: 10px;
    margin-left: 10px;
}
.profile .chargeInput{
    width:60%;
    margin-right:20px
}  
</style>