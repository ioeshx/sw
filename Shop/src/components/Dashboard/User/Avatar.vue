<template>
    <el-upload
      v-model:file-list="fileList"
      action="''"
      list-type="picture-card"
      :on-preview="handlePictureCardPreview"
      :on-remove="handleRemove"
      :auto-upload="false"
      :show-file-list="fileList.length > 0" 
      v-if="fileList.length < 1"
    >
      <el-icon v-if="fileList.length==0"><Plus /></el-icon>
    </el-upload>
  
    <el-dialog v-model="dialogVisible">
      <img w-full :src="dialogImageUrl" alt="Preview Image" />
    </el-dialog>
  </template>
  
  <script lang="ts" setup>
  import { ref } from 'vue'
  import { Plus } from '@element-plus/icons-vue'
  
  import type { UploadProps, UploadUserFile } from 'element-plus'
  
  const fileList = ref<UploadUserFile[]>([])
  
  const dialogImageUrl = ref('')
  const dialogVisible = ref(false)
  
  const handleRemove: UploadProps['onRemove'] = (uploadFile, uploadFiles) => {
    console.log(uploadFile, uploadFiles)
  }
  
  const handlePictureCardPreview: UploadProps['onPreview'] = (uploadFile) => {
    dialogImageUrl.value = uploadFile.url!
    dialogVisible.value = true
  }
  </script>
  