<!--作为GoodsRegister.vue时的组件
可以在点击注册时发送两个axios请求，或者先发送文本信息得到goodsid，再发纯图片的信息
-->
<template>
    <div>
    <!--TODO：可以使用elementplus的其他组件美化-->
      <el-upload
        class="upload-demo"
        ref="upload"
        :action="''"
        :auto-upload="false"
        :file-list="fileList"
        :on-change="handleChange"
      >
        <el-button size="small" type="primary">选取文件</el-button>
      </el-upload>
      <el-button
          size="small"
          type="success"
          :disabled="fileList.length === 0"
          @click="handleUpload"
        >
          上传到服务器
        </el-button>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        fileList: [],
        goodsId: ""
      };
    },
    methods: {
      handleChange(file, fileList) {
        this.fileList = fileList;
      },
      handleUpload() {
        console.log("here");
        this.$refs.form.validate((valid) => {
          if (valid) {
            let formData = new FormData();
            formData.append("goodsId", this.form.goodsId);
            for (let i = 0; i < this.fileList.length; i++) {
              formData.append("file", this.fileList[i].raw);
            }
            this.$axios
              .post("/setGoodsPicture", formData, {
                headers: { "Content-Type": "multipart/form-data" },
              })
              .then((res) => {
                console.log(res.data);
                this.$message.success("图片上传成功");
              })
              .catch((err) => {
                console.log(err);
                this.$message.error("图片上传失败");
              });
          } else {
            console.log("校验失败");
            return false;
          }
        });
      },
    },
  };
  </script>
  