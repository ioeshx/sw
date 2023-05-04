<!--用户头像组件-->
<template>
  <a @click="deleteUserAvatar"> 
    <el-icon><Delete /></el-icon> 
  </a>

  <div>
    <el-upload
      class="avatar-uploader"
      action="''"
      :show-file-list="false"
      :on-success="handleAvatarSuccess"
      :before-upload="beforeAvatarUpload"
    >
      <img v-if="this.base64Data" :src="'data:image/jpeg;base64,' + this.base64Data">
      <el-icon v-else><Plus /></el-icon>
    </el-upload>

  </div>
</template>

<script>
import "../../../constant"
export default {
  data() {
    return {
      isUploaded: false,
      uploadFormat: ['jpg', 'jpeg', 'png', 'bmp'],
      uploadSize: 5 * 1024 * 1024,
      base64Data: ""
    };
  },
  created() {
    this.getUserAvatar();
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
      var avatar = new FormData();
      avatar.append('image', file);
      avatar.append('username', localStorage.getItem("username"));
      this.$axios.post('/setUserAvatar', avatar, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }).then(res => {
        if(res.data.state == window.SUCCESS) {
          this.$message.success("上传成功");
          this.getUserAvatar();
        }
        else {
          this.$message.error(res.data.message);
        }
      })
      return false;
    },
    getUserAvatar: function() {
      var localStorage = window.localStorage;
      this.$axios.post('/getUserAvatar', {
        username: localStorage.getItem("username")
      })
      .then(res => {
        if(res.data.state==window.SUCCESS) {
          // 已有头像
          console.log("success");
          this.base64Data =  res.data.data;
        }
        else {
          console.log("failure");
        }
      })
    },
    deleteUserAvatar() {
      var localStorage = window.localStorage;
      this.$axios.post('/deleteUserAvatar', {
        username: localStorage.getItem("username")
      })
      .then(res => {
        if(res.data.state==window.SUCCESS) {
          this.$message.success("头像已删除");
          this.base64Data = "";
        }
      })
    }
  }
};
</script>

<style>
.avatar-uploader{
  display: flex;
  margin: 0 auto;
}

</style>
