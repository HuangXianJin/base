<template>
  <div class="app-container my-app-container my-index-xx">
    <el-form ref="uploadZipForm" label-position="left" label-width="70px">
      <el-row :gutter="20">
        <el-col :span="6">
          <el-form-item label="附件编码">
            <el-input v-model="attachmentCode" />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="文件类型">
            <el-select v-model="fileType">
              <el-option v-for="item in fileTypes" :key="item[1]" :label="item[0]" :value="item[1]" />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="24">
          <attachment :code="attachmentCode" :file-type="fileType" />
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>

<script>
import Attachment from '@/components/Attachment'
import {
  uuid
} from '@/utils'
export default {
  components: {
    Attachment
  },
  data() {
    return {
      attachmentCode: undefined,
      fileType: 'IMG',
      fileTypes: ['IMG', 'VIDEO']
    }
  },
  created() {
    this.attachmentCode = uuid()
    this.attachmentCode = '0af7b72a-c37a-495a-8754-789f4d713feb'
  },
  methods: {

  }
}
</script>

<style>
</style>
