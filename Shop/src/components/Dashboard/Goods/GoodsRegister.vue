<!-- 添加商品的组件页面-->

<template>
    <div class="wrap">
        <h1 v-if="isModified">商品信息修改</h1>
        <h1 v-else>商品注册表</h1>
        <el-form :model="addForm" :rules="addFormRules" ref="addForm" label-width="100px" label-position="top">
            <!-- TODO: icon添加 -->
            <el-row>
                <el-col :span="24" >
                    <el-form-item class="elItem" label="商品名称" prop="goodsname"> 
                        <el-input 
                            v-model="addForm.goodsname"
                        ></el-input>
                    </el-form-item>
                </el-col>
            
            </el-row>

            <el-row>
                <el-col :span="12">
                    <el-form-item class="elItem" label="商品价格" prop="goodsPrice"> 
                        <el-input 
                            v-model="addForm.goodsPrice" 
                        ></el-input>
                        <span class="hint">商品价格不能小于0，且保留2位小数 </span>
                    </el-form-item>
                </el-col>

                <el-col :span="12">
                    <el-form-item class="elItem" label="初始库存" prop="goodsStock"> 
                        <el-input 
                            v-model="addForm.goodsStock" 
                        ></el-input>
                        <span class="hint">初始库存为不小于0的整数 </span>
                    </el-form-item>
                </el-col>
            </el-row>

            <!--TODO：重新分配类型，做成级联选择器-->
            <!--如果前面的可用 请删除这部分-->
            <el-form-item label="商品类别" prop="goodsCategory">
                <el-checkbox-group v-model="addForm.goodsCategory">
                    <el-checkbox label="电脑数码" />
                    <el-checkbox label="家用电器" />
                    <el-checkbox label="运动户外" />
                    <el-checkbox label="服饰鞋包" />
                    <el-checkbox label="个护化妆" />
                    <el-checkbox label="母婴用品" />
                    <el-checkbox label="日用百货" />
                    <el-checkbox label="食品生鲜" />
                    <el-checkbox label="配饰腕表" />
                    <el-checkbox label="图书音像" />
                    <el-checkbox label="玩模乐器" />
                    <el-checkbox label="办公设备" />
                    <el-checkbox label="家居家装" />
                    <el-checkbox label="汽车消费" />
                    <el-checkbox label="艺术收藏" />
                    <el-checkbox label="其他" />
                </el-checkbox-group>
            </el-form-item>

            <el-form-item label="商品简介" prop="introduction" >
                <el-input
                    type="textarea"
                    :rows="3"
                    placeholder="商品简介（不超过128个字）" 
                    v-model="addForm.introduction" 
                    autocomplete="off"
                    Clearable
                ></el-input>
            </el-form-item>

            <el-form-item label="商品图片（可添加多张）">
                <el-upload
                    v-model:file-list="addForm.fileList"
                    auto-upload:false
                    list-type="picture-card"
                    :on-remove="handleChange"
                    ref="upload"
                    :action="''"
                    :auto-upload="false"
                    :on-change="handleChange"
                >
                    <el-icon><Plus /></el-icon>
                </el-upload>
            </el-form-item>

            <el-row>
              <el-col :span="12">
                <el-form-item class="btn">
                  <el-button v-if="isModified" type="primary" @click="setGoodsInfo('addForm')">提&nbsp;&nbsp;交</el-button>
                  <el-button v-else type="primary" @click="goodsRegister('addForm')">注&nbsp;&nbsp;册</el-button>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item class="btn">
                  <el-button type="primary" @click="resetForm">重&nbsp;&nbsp;置</el-button>
                </el-form-item>
              </el-col>
            </el-row>

        </el-form>
    </div>
</template>
 
<script>
import "../../../constant";
import { interceptor, shopkeeperInterceptor } from "../../../interceptor"
export default {
    props: {
        goods: {
            type: Object,
            required: false,
            default: ""
        }
    },
    data(){
        return {
            isModified: false,
            addForm: {
                goodsname:'',
                goodsPrice: 0,
                goodsStock: 0,
                goodsCategory: [],
                fileList: [],
                introduction: "",
                goodsId: ""
            },
            addFormRules: {
                goodsname:[
                    {required:true, message:'请输入商品名称', trigger: 'blur'},
                    {required:true, message:'请输入商品名称', trigger: 'change'},
                    { required: true, max: 20, message: '商品名称不能超过20个字', trigger: 'blur' },
                    { required: true, max: 20, message: '商品名称不能超过20个字', trigger: 'change' }
                ],
                goodsPrice:[
                    { required:true, validator: this.goodsPriceValidator, trigger: 'blur' }
                ],
                goodsStock:[
                    { required:true, validator: this.goodsStockValidator, trigger: 'blur' }
                ],
                introduction:[
                    { required: true, message: '店铺简介不能为空', trigger: 'blur' },
                    { required: true, message: '店铺简介不能为空', trigger: 'change' },
                    { required: true, max: 128, message: '店铺简介不能超过128个字', trigger: 'blur' },
                    { required: true, max: 128, message: '店铺简介不能超过128个字', trigger: 'change' }
                ],
                goodsCategory:[
                    { required:true, validator: this.goodsCategoryValidator, trigger: 'blur' },
                    { required:true, validator: this.goodsCategoryValidator, trigger: 'change' }
                ]
            } 
        }
    },
    created(){
        interceptor(this);
        shopkeeperInterceptor(this);
        this.getGoodsInfo();
    },
    methods:{
        getGoodsInfo() {
            console.log("getGoodsInfo")
            console.log(Object.keys(this.goods).length)
            // 商品修改中
            if(Object.keys(this.goods).length != 0) {
                this.isModified = true;
                // 情况1:注册中/修改中的商品再次修改信息
                if(this.goods.status == 0||this.goods.status == 2) {
                    this.$axios.post('/getEditingGoodsInfo',{
                        goodsId: this.goods.goodsId
                    }).then(res => {
                        console.log("getGoodsInfo")
                        console.log(res.data)
                        if(res.data.state == window.SUCCESS) {
                            // 显示的是提交但未审批的信息
                            const goods = res.data.data;
                            this.addForm.goodsname = goods.goodsname;
                            this.addForm.goodsPrice = goods.goodsPrice;
                            this.addForm.goodsStock = goods.goodsStock;
                            this.addForm.goodsCategory = goods.goodsCategory;
                            this.addForm.introduction = goods.introduction;
                            // this.addForm.fileList = goods.goodsAvatar;
                            /*const defaultFileList = [];
                            goods.goodsAvatar.forEach((data, index) => {
                                const file = this.convertBase64ToFile(data, `file${index}.png`);
                                defaultFileList.push({ name: file.name, url: URL.createObjectURL(file) });
                                this.addForm.fileList.push(file);
                            });
                            this.defaultFileList = defaultFileList;
                            console.log("avatar")
                            console.log(goods.goodsAvatar)*/
                        }
                    })
                } else {
                    this.addForm.goodsname = this.goods.goodsname;
                    this.addForm.goodsPrice = this.goods.goodsPrice;
                    this.addForm.goodsStock = this.goods.goodsStock;
                    this.addForm.goodsCategory = this.goods.goodsCategory;
                    this.addForm.introduction = this.goods.introduction;
                    //this.addForm.fileList = this.goods.goodsAvatar;
                }
                console.log(this.goods);
            }
        },
        goodsPriceValidator(rule, value, callback) {
            if (isNaN(Number(value))) {
                this.addForm.goodsPrice = 0;
                return callback(new Error("请输入正确的浮点数"));
            }
            else {
                const input = String(value);
                // 使用正则表达式将其转换为保留两位小数的字符串
                const regex = /^\d+(\.\d{0,2})?/;
                const match = input.match(regex);
                const formattedValue = match ? match[0] : '0';
                // 将转换后的值赋给 this.addForm.goodsPrice
                this.addForm.goodsPrice = parseFloat(formattedValue);
            }
            callback();
        },
        goodsStockValidator(rule, value, callback) {
            if (isNaN(Number(value)) || Number(value)<0) {
                this.addForm.goodsStock = 0;
                return callback(new Error("请输入正确的库存数"));
            }
            else {
                this.addForm.goodsStock = parseInt(value);
            }
            callback();
        },
        goodsCategoryValidator(rule, value, callback) {
            if(value.length==0) {
                return callback(new Error("请至少选择一项"));
            }
            callback();
        },
        fileListValidator(rule, value, callback) {
            if(value.length==0) {
                return callback(new Error("至少上传一张图片"));
            }
            callback();
        },
        handleChange(file, fileList) {
            const format = file.name.split('.').pop().toLowerCase()
            const uploadFormat = ['jpg', 'jpeg', 'png', 'bmp']
            const isFormatValid = uploadFormat.indexOf(format) !== -1
            if (!isFormatValid) {
                this.$message.error(`上传图片格式不支持，支持的格式为 ${uploadFormat.join(', ')}`);
                fileList.splice(-1, 1);
                console.log(fileList);
                return false;
            }
            if (fileList.length > 5) {
                fileList.splice(5);
                this.$message.error("最多只能上传5张图片");
                return false;
            }
            this.addForm.fileList = fileList;
            console.log(this.addForm.fileList.length);
        },
        setGoodsInfo: function (addForm) {
            this.$refs[addForm].validate((valid) => {
            if (valid) {
                if(this.addForm.fileList.length==0) {
                    console.log(this.addForm)
                    this.$message.error("至少上传一张图片")
                    return
                }
                // 先传输普通数据
                var localStorage = window.localStorage;
                this.$axios.post('/setGoodsInfo', {
                    goodsId: this.goods.goodsId,
                    goodsCategory: this.addForm.goodsCategory.join(';'),
                    introduction: this.addForm.introduction,
                    goodsname:this.addForm.goodsname,
                    goodsPrice: this.addForm.goodsPrice,
                    goodsStock: this.addForm.goodsStock
                }).then(res => {
                    console.log("setGoodsInfo")
                    console.log(res.data)
                    if(res.data.state == window.SUCCESS){
                        this.modifyGoodsAvatar(this.goods.goodsId);
                    }
                    else {
                        this.$message.error(res.data.message);
                    }
                }).catch(err => {
                    this.$message.error("提交失败，请重试");
                    console.log(err);
                })
            } else {
                console.log("校验失败");
                return false;
            }
          });
        },
        goodsRegister: function (addForm) {
            this.$refs[addForm].validate((valid) => {
            if (valid) {
                // 先传输普通数据
                if(this.addForm.fileList.length==0) {
                    console.log(this.addForm)
                    this.$message.error("至少上传一张图片")
                    return
                }
                var localStorage = window.localStorage;
                this.$axios.post('/goodsRegister', {
                    shopname: localStorage.getItem("shopname"),
                    goodsCategory: this.addForm.goodsCategory.join(';'),
                    introduction: this.addForm.introduction,
                    goodsname:this.addForm.goodsname,
                    goodsPrice: this.addForm.goodsPrice,
                    goodsStock: this.addForm.goodsStock
                }).then(res => {
                    if(res.data.state == window.SUCCESS){
                        this.setGoodsAvatar(res.data.data);
                    }
                    else {
                        this.$message.error(res.data.message);
                    }
                }).catch(err => {
                    console.log(err);
                })
            } else {
                console.log("校验失败");
                return false;
            }
          });
        },
        setGoodsAvatar: function(goodsId) {
            console.log("setGoodsPics")
            console.log(this.addForm.fileList.length)
            let formData = new FormData();
            formData.append("goodsId", goodsId);
            for (let i = 0; i < this.addForm.fileList.length; i++) {
                formData.append("file", this.addForm.fileList[i].raw);
            }
            console.log(formData)
            this.$axios.post("/setGoodsPicture", formData, {
                headers: { "Content-Type": "multipart/form-data" },
            })
            .then((res) => {
                console.log(res.data);
                this.$message.success("提交成功！");
                setTimeout(() => {
                    this.$router.go(0)
                }, 2000);
            })
            .catch((err) => {
                console.log(err);
                this.$message.error("提交失败，请重试");
            });
        },
        modifyGoodsAvatar: function(goodsId) {
            let formData = new FormData();
            formData.append("goodsId", goodsId);
            for (let i = 0; i < this.addForm.fileList.length; i++) {
                formData.append("file", this.addForm.fileList[i].raw);
            }
            console.log(formData)
            this.$axios.post("/modifyGoodsPicture", formData, {
                headers: { "Content-Type": "multipart/form-data" },
            }) .then((res) => {
                console.log(res.data);
                this.$message.success("提交成功！");
                setTimeout(() => {
                    this.$router.go(0)
                }, 1000);
            }) .catch((err) => {
                console.log(err);
                this.$message.error("提交失败，请重试");
            });
        },
        resetForm() {
            this.$refs.addForm.resetFields();
        },
        convertBase64ToFile(base64Data, fileName) {
            const arr = base64Data.split(',');
            const fileType = arr[0].match(/:(.*?);/)[1];
            const bstr = atob(arr[1]);
            let n = bstr.length;
            const u8arr = new Uint8Array(n);
            while (n--) {
                u8arr[n] = bstr.charCodeAt(n);
            }
            return new File([u8arr], fileName, { type: fileType });
        }
    }
}

</script>

 
<style scoped>
.wrap{
    padding: 30px;
    background-color: #fff;
    border-radius: 15px;
    color: #303133;
    border: 3px solid #ebeef5;
    transition: .3s;
}

.wrap h1{
  text-align: left;
  font-family:"Lucida Console", "Courier New", monospace;
  font-size: 30px;
  margin-bottom:40px;
  font-weight: bolder;
  color:#81A18B;
}

.elItem{
    width: 90%;
}

.hint{
    color:gray;
    font-size:12px;
    margin-left:5px
}
.btn button{
    margin-top: 20px;
    line-height: 100px;
    width: 80%;
    height: 38px;
    background-color:#81A18B;
    border-color:#81A18B;
    justify-items: center;

}

</style>