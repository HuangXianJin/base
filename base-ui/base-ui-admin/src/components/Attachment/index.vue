
<template>
  <div :class="onlyPreview?'onlyPreview uploadmyfile':'uploadmyfile'">
    <!--start-->
    <div v-show="onlyPreview && photoNothing && total < 1" class="nothing">
      <div class="iconfont my-icon-nothing nothing_img" />
      <div class="nothing_name">暂无数据</div>
    </div>
    <!--end-->
    <!--start-->
    <div v-show="onlyPreview && !photoNothing && total < 1" class="nothing_photo">
      <div class="iconfont my-icon-nothingPhoto nothing_photo_name" />
    </div>
    <!--end-->
    <div v-if="!onlyPreview && tip" slot="tip" class="el-upload__tip tips">【仅{{ tip }}】</div>
    <el-upload
      v-if="!onlyPreview || (onlyPreview && total >0)"
      :data="data"
      :multiple="multiple"
      :on-preview="handlePreview"
      :before-remove="handleBeforeRemove"
      :before-upload="handleBeforeUpload"
      :on-success="handleSuccess"
      :on-remove="handleRemove"
      :file-list="fileList"
      :http-request="upload"
      :action="action"
      :headers="header"
      :list-type="listType"
      :disabled="onlyPreview"
      :limit="limit"
    >
      <div v-if="!onlyPreview" style="width:100%;">
        <i v-if="fileType === 'IMG'" class="el-icon-plus" />
        <div v-else class="videobtn">
          <i class="el-icon-plus" />
        </div>
      </div>
    </el-upload>

    <el-dialog :close-on-click-modal="false" :visible.sync="dialogVisible" append-to-body>
      <img :src="dialogImageUrl" width="100%" alt >
    </el-dialog>
  </div>
</template>

<script>
import File from '@/api/system/sys_file'
import ajax from '@/utils/ajax'

export default {
  name: 'Attachment',
  props: {
    photoNothing: { type: Boolean, default: false },
    code: { type: String, required: true },
    fileType: { type: String, required: true },
    onlyPreview: { type: Boolean, default: false },
    desc: { type: String, default: '附件' },
    multiple: { type: Boolean, default: true }, // 多选
    limit: { type: Number, default: undefined }, // 最大上传数
    isShowSingle: { type: Boolean, default: false }, // 是否显示单个
    isReturnTotal: { type: Boolean, default: false } // 是否返回总数,可以辅助必传场景的校验，如需要，则添加@returnTotal事件接收总数
  },
  data() {
    return {
      action: '',
      tip: undefined,
      listType: 'text',
      fileList: [],
      dialogImageUrl: '',
      dialogVisible: false,
      total: 0,
      httpRequest: ajax,
      header: {}
    }
  },
  computed: {
    data: {
      get() {
        return { attachmentCode: this.code,
          sysFileType: this.fileType,
          desc: this.desc }
      },
      set(val) {
        this.$emit('input', val)
      }
    }
  },
  watch: {
    code: {
      handler(newValue, oldValue) {
        this.getAttachments()
      },
      deep: true
    },
    fileType: {
      handler(newValue, oldValue) {
        this.changeTip()
        this.getAttachments()
      },
      deep: true
    },
    total: {
      handler(newValue, oldValue) {
        if (this.isReturnTotal) {
          this.$emit('returnTotal', newValue)
        }
      },
      deep: true
    }
  },
  created() {
    this.action = process.env.VUE_APP_BASE_API + '/system/file/uploadAttachment'
    // this.header.Token = this.$store.getters.token
    this.getAttachments()
    this.changeTip()
  },
  methods: {
    changeTip() {
      this.tip = ''
      this.listType = 'text'
      if (this.fileType === 'IMG') {
        this.tip = '支持jpg/jpeg/png/bmp/gif文件'
        this.listType = 'picture-card'
      }
      if (this.fileType === 'VIDEO') {
        this.tip = '支持rmvb/avi/mov/mpeg/mp4/mkv/wmv文件'
      }
    },
    getAttachments() {
      File.getAttachmentsByAttachmentCode(this.data).then((response) => {
        // 只显示一张图片
        if (this.isShowSingle && response.data.length > 0) {
          this.fileList[0] = response.data[0]
          this.total = this.fileList.length
          this.fileList[0].url = this.fileList[0].sysUrl
          this.fileList[0].name = this.fileList[0].originName
          return
        }
        this.fileList = response.data
        this.total = response.data.length
        this.fileList.forEach(v => {
          v.url = v.sysUrl
          v.name = v.originName
        })
      })
    },
    handleBeforeRemove(file, fileList) {
      var _self = this
      if (file.id) {
        return new Promise((resolve, reject) => {
          this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            File.deleteAttachment(file.id).then(response => {
              _self.getAttachments()
              resolve(response.data)
            }).catch(error => { reject(error) })
          }).catch(() => {
            reject()
          })
        })
      }
    },
    handleBeforeUpload(file) {
      const fileName = file.name.toLowerCase()
      if (!this.fileType) {
        return true
      }
      var regex
      if (this.fileType === 'IMG') {
        regex = /(.jpg|.jpeg|.png|.bmp|.gif)$/
      }

      if (this.fileType === 'VIDEO') {
        regex = /(.rmvb|.avi|.mpeg|.mov|.mp4|.mkv|.wmv)$/
      }
      if (regex && !regex.test(fileName.toLowerCase())) {
        this.$message.error('请上传格式支持的文件')
        return false
      }
    },
    handleSuccess(response, file, fileList) {
      file.id = response.data.id
      this.total = fileList.length
      this.$emit('success', file, fileList)
    },
    handleRemove(file, fileList) {
      this.$emit('remove', file, fileList)
    },
    handlePreview(file) {
      const fileName = file.name.toLowerCase()
      var regex = /(.jpg|.jpeg|.png|.bmp|.gif)$/
      if (regex.test(fileName.toLowerCase())) {
        this.dialogImageUrl = file.url
        this.dialogVisible = true
      } else {
        window.open(file.url)
      }
    },
    upload(options) {
      var vm = this
      const fileName = options.file.name.toLowerCase()
      var regex = /(.jpg|.jpeg|.png|.bmp)$/
      if (regex && regex.test(fileName.toLowerCase())) {
        lrz(options.file).then(function (rst) {// eslint-disable-line
          rst.file.name = fileName
          options.file = rst.file
          vm.httpRequest(options)
        }).catch(function(err) {
          console.log(err)
          vm.httpRequest(options)
        }).always(function() {
          // 不管是成功失败，都会执行
        })
      } else {
        vm.httpRequest(options)
      }
    }
  }
}
</script>

<style>
.nothing_photo .nothing_photo_name {
  font-size: 50px;
}
.onlyPreview .el-upload--picture-card {
  visibility: hidden;
}

.uploadmyfile .el-upload-list--picture .el-upload-list__item-thumbnail {
  display: none;
}

.uploadmyfile .el-upload-list--text {
  width: 100%;
}

.uploadmyfile .el-upload--text {
  width: 100%;
}

.uploadmyfile .el-upload-list--picture .el-upload-list__item {
  padding: 10px;
}

.uploadmyfile
  .el-upload-list--picture
  .el-upload-list__item.is-success
  .el-upload-list__item-name {
  line-height: 30px;
}

.uploadmyfile .el-upload-list--picture .el-upload-list__item {
  height: 50px;
}

.uploadmyfile .tips {
  color: red;
  margin: 0 0 5px 0;
  font-size: 14px;
}

.uploadmyfile .el-upload-list--picture-card .el-upload-list__item {
  width: 100px;
  height: 100px;
}

.uploadmyfile .el-upload--picture-card {
  width: 98px;
  height: 98px;
  line-height: 98px;
}

.uploadmyfile .el-upload-list__item-name {
  color: #333;
}

.uploadmyfile .videobtn {
  display: inline-block;
  width: 100%;
  height: 50px;
  line-height: 50px;
  border-radius: 6px;
  color: #8c939d;
  background: #fbfdff;
  border: 1px dashed #c0ccda;
}

.uploadmyfile .videobtn i {
  font-size: 25px;
  color: #8c939d;
  margin-top: 10px;
}
</style>
