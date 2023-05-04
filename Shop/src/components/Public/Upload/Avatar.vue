<!--用户/商铺：注册/修改信息的头像组件-->
<template>
  <div class="upload">
      <a class="element" @click="deleteAvatar"> 
        <el-icon><Delete /></el-icon> 
      </a>

      <div class="element">
        <el-upload
          action="''"
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload"
        >
          <img class="img" v-if="this.base64Data" :src="this.base64Data">
          <el-icon class="imgU" v-else><Plus /></el-icon>
        </el-upload>

      </div>
  </div>
</template>

<script>
import "../../../constant";
var avatar = new FormData();
export default {
  props: {
    type: {
      // 1:商店信息修改，2:用户信息修改
      type: Number,
      required: true
    }
  },
  data() {
    return {
      isUploaded: false,
      uploadFormat: ['jpg', 'jpeg', 'png', 'bmp'],
      uploadSize: 5 * 1024 * 1024,
      base64Data: ""
    };
  },
  created() {
    this.getAvatar();
  },
  methods: {
    beforeAvatarUpload(file) {
      const format = file.name.split('.').pop().toLowerCase()
      const isFormatValid = this.uploadFormat.indexOf(format) !== -1
      const isSizeValid = file.size <= this.uploadSize
      if (!isFormatValid) {
        this.$message.error(`上传图片格式不支持，支持的格式为 ${this.uploadFormat.join(', ')}`)
        return false
      }
      if (!isSizeValid) {
        this.$message.error(`上传图片大小超出限制，大小不能超过 ${this.uploadSize / 1024 / 1024}MB`)
        return false
      }
      avatar.append('image', file);
      if(this.type == window.USER) {
        avatar.append('username', localStorage.getItem("username"));
        this.$axios.post('/setUserAvatar', avatar, {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        }).then(res => {
          if(res.data.state == window.SUCCESS) {
            this.$message.success("上传成功");
            this.getAvatar();
          }
          else {
            this.$message.error(res.data.message);
          }
        })
      } else {
        if(localStorage.getItem("shopname")) {
          avatar.append('shopname', localStorage.getItem("shopname"));
          this.$axios.post('/setShopAvatar', avatar, {
            headers: {
              'Content-Type': 'multipart/form-data'
            }
          }).then(res => {
            if(res.data.state == window.SUCCESS) {
              this.$message.success("店铺头像上传成功");
              const reader = new FileReader();
              reader.readAsDataURL(file);
              reader.onload = () => {
                this.base64Data = reader.result;
              };
              reader.onerror = error => {
                console.log('Error: ', error);
              };
            } else {
                this.$message.error(res.data.message);
              }
          })
          } else {
            const reader = new FileReader();
            reader.readAsDataURL(file);
            reader.onload = () => {
              this.base64Data = reader.result;
            };
            reader.onerror = error => {
              console.log('Error: ', error);
            };
          }
         
      }
      return false;
    },
    getAvatar: function() {
      var localStorage = window.localStorage;
      if(this.type == window.USER) {
        this.$axios.post('/getUserAvatar', {
          username: localStorage.getItem("username")
        })
        .then(res => {
          if(res.data.state==window.SUCCESS) {
            // 已有头像
            console.log("success");
            this.base64Data = "data:image/jpeg;base64," + res.data.data;
            console.log(this.type)
          }
          else {
            console.log("failure");
          }
        })
      } else {
        this.$axios.post('/getShopAvatarByShopname', {
          shopname: localStorage.getItem("shopname")
        })
        .then(res => {
          if(res.data.state==window.SUCCESS) {
            // 已有头像
            console.log("success");
            this.base64Data = "data:image/jpeg;base64," + res.data.data;
            console.log(this.type)
          }
          else {
            console.log("failure");
          }
        })
      }
      
    },
    deleteAvatar() {
      var localStorage = window.localStorage;
      if(this.type == window.USER) {
        this.$axios.post('/deleteUserAvatar', {
          username: localStorage.getItem("username")
        })
        .then(res => {
          if(res.data.state==window.SUCCESS) {
            this.$message.success("头像已删除");
            this.base64Data = "";
          }
        })
      } else {
        if(localStorage.getItem("shopname")) {
          this.$axios.post('/deleteShopAvatar', {
            shopname: localStorage.getItem("shopname")
          })
          .then(res => {
            if(res.data.state==window.SUCCESS) {
              this.$message.success("头像已删除");
              this.base64Data = "";
            }
          })
        } else {
          this.base64Data = ""
        }
      }
    },
    setShopAvatar() {
        avatar.append('shopname', localStorage.getItem("shopname"));
        this.$axios.post('/setShopAvatar', avatar, {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        }).then(res => {
          if(res.data.state == window.SUCCESS) {
            this.$message.success("上传成功");
            const reader = new FileReader();
            reader.readAsDataURL(file);
            reader.onload = () => {
              this.base64Data = reader.result;
              console.log(this.base64Data)
            };
            reader.onerror = error => {
              console.log('Error: ', error);
            };
          }
          else {
            this.$message.error(res.data.message);
          }
        })
    }
  }
};
</script>

<style>
.upload{
  display: grid;
  grid-template-columns: 5% 90% 5%;
  margin:20px;
}
.element{
  margin:  auto;
}
.imgU{
  width:100px;
  height: 100px;
  margin: auto;
  display: flex;
  border: 3px dashed gray; /* 3像素宽的黑色虚线边框 */
  background-color: rgba(128, 128, 128, 0.2); /* 透明灰色背景 */
}
.img{
  padding: 20px;
  width:50%;
  height:50%;
}
</style>
