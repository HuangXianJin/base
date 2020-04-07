<!-- 用户信息 -->
<template>
  <div class="app-container my-app-container my-index-sys-user">
    <!--条件Demo开始-->
    <div class="my-filter-container">
      <div class="my-filter-list">
        <span class="my-filter-item my-filter-item-search">
          <span class="search-input">
            <el-input v-model="listQuery.filter" placeholder="关键字" clearable @change="handleFilter" @keyup.enter.native="handleFilter" />
          </span>
          <span class="search-btn">
            <el-button type="primary" icon="el-icon-search" @click="handleFilter">搜索</el-button>
          </span>
        </span>
      </div>

      <div class="my-filter-btn">
        <el-button type="primary" icon="el-icon-plus" @click="handleCreate">新增</el-button>
        <el-button type="primary" icon="el-icon-plus" @click="uploadDialogFormVisible = true">批量导入</el-button>
      </div>
    </div>
    <!--条件Demo结束-->

    <!--主体Demo开始-->
    <div class="my-layout-height my-layout-height-filter-table">
      <el-scrollbar class="my-scroll my-scroll-column">
        <div class="my-scroll-padding-right">
          <!--表格Demo开始-->
          <div class="my-table-container">
            <el-table key="0" v-loading="listLoading" :data="list" stripe fit highlight-current-row @sort-change="sortChange">
              <el-table-column label="序号" prop="id" fixed align="center" width="60">
                <template slot-scope="scope">
                  <span>{{ scope.$index + (listQuery.current - 1) * listQuery.size + 1 }}</span>
                </template>
              </el-table-column>

              <el-table-column label="所属租户" align="center" prop="tenant_code" sortable="custom">
                <template slot-scope="scope">
                  <span>{{ scope.row.tenantName }}</span>
                </template>
              </el-table-column>

              <el-table-column label="账号" align="center" prop="user_name" sortable="custom">
                <template slot-scope="scope">
                  <span>{{ scope.row.userName }}</span>
                </template>
              </el-table-column>

              <el-table-column label="姓名" align="center" prop="nick_name" sortable="custom">
                <template slot-scope="scope">
                  <span>{{ scope.row.nickName }}</span>
                </template>
              </el-table-column>

              <el-table-column label="邮箱" align="center" prop="email" sortable="custom">
                <template slot-scope="scope">
                  <span>{{ scope.row.email }}</span>
                </template>
              </el-table-column>

              <el-table-column label="手机号" align="center" prop="mobile" sortable="custom">
                <template slot-scope="scope">
                  <span>{{ scope.row.mobile }}</span>
                </template>
              </el-table-column>

              <el-table-column label="状态" align="center" prop="status" sortable="custom">
                <template slot-scope="scope">
                  <span v-if="scope.row.status === 2" class="text-color-danger">锁定</span>
                  <span v-if="scope.row.status === 1" class="text-color-main">启用</span>
                  <span v-if="scope.row.status === 0" class="text-color-danger">禁用</span>
                </template>
              </el-table-column>

              <el-table-column label="操作" align="center" fixed="right" width="150">
                <template slot-scope="scope">
                  <el-button type="success" icon="el-icon-bell" circle alt="修改密码" title="修改密码" @click="handleModifyPassword(scope.row)" />
                  <el-button type="primary" icon="el-icon-edit" circle alt="编辑" title="编辑" @click="handleUpdate(scope.row)" />
                  <el-button type="danger" icon="el-icon-delete" circle alt="删除" title="删除" @click="handleRemove(scope.row)" />
                </template>
              </el-table-column>
            </el-table>

            <pagination v-show="total > 0" :total="total" :page.sync="listQuery.current" :limit.sync="listQuery.size" @pagination="getList" />
          </div>
          <!--表格Demo结束-->
        </div>
      </el-scrollbar>
    </div>
    <!--主体Demo结束-->

    <!--新增Demo开始-->
    <el-dialog :visible.sync="createDialogFormVisible" :close-on-click-modal="false" title="新增" width="600px">
      <div class="my-from-container">
        <el-form ref="createForm" :rules="rules" :model="temp" label-position="right" label-width="80px">
          <el-form-item label="账号" prop="userName">
            <el-input v-model="temp.userName" type="text" placeholder="账号" />
          </el-form-item>

          <el-form-item label="姓名" prop="nickName">
            <el-input v-model="temp.nickName" type="text" placeholder="姓名" />
          </el-form-item>

          <el-form-item label="密码" prop="password">
            <el-input v-model="temp.password" type="text" placeholder="密码" />
          </el-form-item>

          <el-form-item label="邮箱" prop="email">
            <el-input v-model="temp.email" type="text" placeholder="邮箱" />
          </el-form-item>

          <el-form-item label="手机号" prop="mobile">
            <el-input v-model="temp.mobile" type="text" placeholder="手机号" />
          </el-form-item>

          <el-form-item label="状态" prop="status">
            <el-radio-group v-model="temp.status">
              <el-radio-button label="1">启用</el-radio-button>
              <el-radio-button label="0">禁用</el-radio-button>
              <el-radio-button label="2">锁定</el-radio-button>
            </el-radio-group>
          </el-form-item>

          <el-form-item label="描述" prop="userDesc">
            <el-input v-model="temp.userDesc" type="textarea" placeholder="描述" />
          </el-form-item>

          <el-form-item label="角色">
            <el-select v-model="temp.roleIds" multiple placeholder="请选择" class="my-from-item-full">
              <el-option v-for="item in roles" :key="item.id" :label="item.roleName" :value="item.id" />
            </el-select>
          </el-form-item>

          <el-form-item label="所在机构：" prop="departmentId">
            <el-cascader v-model="temp.departmentId" :options="departments" :props="cascaderProps" clearable />
          </el-form-item>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="createData()">确定</el-button>
        <el-button @click="createDialogFormVisible = false">取消</el-button>
      </div>
    </el-dialog>
    <!--新增Demo结束-->

    <!--编辑Demo开始-->
    <el-dialog :visible.sync="updateDialogFormVisible" :close-on-click-modal="false" title="编辑" width="600px">
      <div class="my-from-container">
        <el-form ref="updateForm" :rules="rules" :model="temp" label-position="right" label-width="80px">
          <el-form-item label="账号" prop="userName">
            <el-input v-model="temp.userName" type="text" placeholder="账号" />
          </el-form-item>

          <el-form-item label="姓名" prop="nickName">
            <el-input v-model="temp.nickName" type="text" placeholder="姓名" />
          </el-form-item>

          <el-form-item label="邮箱" prop="email">
            <el-input v-model="temp.email" type="text" placeholder="邮箱" />
          </el-form-item>

          <el-form-item label="手机号" prop="mobile">
            <el-input v-model="temp.mobile" type="text" placeholder="手机号" />
          </el-form-item>

          <el-form-item label="状态" prop="status">
            <el-radio-group v-model="temp.status">
              <el-radio-button label="1">启用</el-radio-button>
              <el-radio-button label="0">禁用</el-radio-button>
              <el-radio-button label="2">锁定</el-radio-button>
            </el-radio-group>
          </el-form-item>

          <el-form-item label="描述" prop="userDesc">
            <el-input v-model="temp.userDesc" type="textarea" placeholder="描述" />
          </el-form-item>

          <el-form-item label="角色">
            <el-select v-model="temp.roleIds" multiple placeholder="请选择" class="my-from-item-full">
              <el-option v-for="item in roles" :key="item.id" :label="item.roleName" :value="item.id" />
            </el-select>
          </el-form-item>

          <el-form-item label="所在机构：" prop="departmentId">
            <el-cascader v-model="temp.departmentId" :options="departments" :props="cascaderProps" clearable />
          </el-form-item>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="updateData()">确定</el-button>
        <el-button @click="updateDialogFormVisible = false">取消</el-button>
      </div>
    </el-dialog>
    <!--编辑Demo结束-->

    <!--密码Demo开始-->
    <el-dialog :visible.sync="modifyPasswordDialogFormVisible" :close-on-click-modal="false" title="修改密码" width="600px">
      <div class="my-from-container">
        <el-form ref="modifyPasswordForm" :rules="rules" :model="temp" label-position="right" label-width="80px">
          <el-form-item label="密码" prop="password">
            <el-input v-model="temp.password" type="text" placeholder="密码" />
          </el-form-item>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="modifyPassword()">确定</el-button>
        <el-button @click="modifyPasswordDialogFormVisible = false">取消</el-button>
      </div>
    </el-dialog>
    <!--密码Demo结束-->

    <!--导入开始-->
    <el-dialog :close-on-click-modal="false" :visible.sync="uploadDialogFormVisible" title="导入用户" width="400px">
      <div class="my-from-container">
        <el-form ref="uploadDataForm" :rules="rules" :model="temp" label-position="right" label-width="0">
          <div class="my-from-item-full my-from-item-upload">
            <el-upload ref="upload" :multiple="false" :limit="1" :http-request="upload" :auto-upload="false" drag action>
              <i class="el-icon-upload" />
              <div class="el-upload__text">
                将文件到此处，或
                <em>点击上传</em>
              </div>
              <div slot="tip" class="el-upload__tip" />
            </el-upload>
          </div>
          <div class="text-align-right">
            <el-button type="primary" icon="iconfont my-icon-downloadTem" @click="handleDownload('user')">下载模板</el-button>
          </div>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitUpload">确定</el-button>
        <el-button @click="cancelUpload">取消</el-button>
      </div>
    </el-dialog>
    <!---导入结束-->
  </div>
</template>

<script>
import User from '@/api/system/sys_user'
import Role from '@/api/system/sys_role'
import Common from '@/api/common/common'
import Department from '@/api/system/sys_department'

export default {
  components: {
    Pagination: () => import('@/components/Pagination')
  },
  data() {
    return {
      refs: undefined,
      list: null,
      total: 0,
      listLoading: false,
      listQuery: {
        current: 1,
        size: 10,
        filter: undefined,
        ascs: undefined,
        descs: undefined
      },
      temp: {},
      roles: [],
      departments: [], // 机构List
      selectRoles: undefined,
      createDialogFormVisible: false,
      updateDialogFormVisible: false,
      modifyPasswordDialogFormVisible: false,
      uploadDialogFormVisible: false,
      cascaderProps: { // 机构选择配置
        value: 'id',
        label: 'departmentName',
        emitPath: false,
        checkStrictly: true
      },
      rules: {
        email: [
          {
            message: '请输入正确的邮箱',
            trigger: 'change',
            pattern: /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/
          }
        ],
        mobile: [
          {
            message: '请输入正确的手机号',
            trigger: 'change',
            pattern: /^1[3456789]\d{9}$/
          }
        ],
        tenantCode: [
          {
            required: true,
            message: '不能为空',
            trigger: 'blur'
          }
        ],
        userName: [
          {
            required: true,
            message: '不能为空',
            trigger: 'blur'
          }
        ],
        nickName: [
          {
            required: true,
            message: '不能为空',
            trigger: 'blur'
          }
        ],
        password: [
          {
            required: true,
            message: '不能为空',
            trigger: 'blur'
          }
        ],
        status: [
          {
            required: true,
            message: '不能为空',
            trigger: 'blur'
          }
        ]
      }
    }
  },
  mounted() {
    this.refs = this.$refs
    this.initData()
    this.handleFilter()
  },
  methods: {
    initData() {
      Role.list().then(response => {
        this.roles = response.data
      })
      Department.list().then(response => {
        this.departments = response.data
      })
    },
    getList() {
      this.listLoading = true
      User.page(this.listQuery).then(response => {
        this.list = response.data.records
        this.total = response.data.total
        this.listLoading = false
      })
    },
    sortChange(data) {
      const { prop, order } = data
      if (order === 'ascending') {
        this.listQuery.ascs = prop
        this.listQuery.descs = undefined
      } else {
        this.listQuery.ascs = undefined
        this.listQuery.descs = prop
      }
      this.handleFilter()
    },
    handleFilter() {
      this.listQuery.current = 1
      this.getList()
    },
    handleCreate() {
      this.temp = {}
      this.createDialogFormVisible = true
      this.$nextTick(() => {
        this.refs.createForm.clearValidate()
      })
    },
    createData() {
      this.refs.createForm.validate((valid) => {
        if (valid) {
          User.create(this.temp).then(ret => {
            this.createDialogFormVisible = false
            this.$message({
              message: ret.message,
              type: 'success'
            })
            this.getList()
          })
        }
      })
    },
    handleUpdate(row) {
      this.temp = Object.assign({}, row) // copy obj
      this.updateDialogFormVisible = true
      this.$nextTick(() => {
        this.refs.updateForm.clearValidate()
      })
    },
    updateData(row) {
      this.refs.updateForm.validate((valid) => {
        if (valid) {
          var submit = Object.assign({}, this.temp)
          User.update(submit).then(ret => {
            this.updateDialogFormVisible = false
            this.$message({
              message: ret.message,
              type: 'success'
            })
            this.getList()
          })
        }
      })
    },
    handleModifyPassword(row) {
      this.temp = { id: row.id }
      this.modifyPasswordDialogFormVisible = true
      this.$nextTick(() => {
        this.refs.modifyPasswordForm.clearValidate()
      })
    },
    modifyPassword(row) {
      this.refs.modifyPasswordForm.validate((valid) => {
        if (valid) {
          User.modifyPassword(this.temp).then(ret => {
            this.modifyPasswordDialogFormVisible = false
            this.$message({ message: ret.message, type: 'success' })
            this.getList()
          })
        }
      })
    },
    handleRemove(row) {
      this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteData(row)
      })
    },
    deleteData(row) {
      User.remove(row.id).then((ret) => {
        this.$message({
          type: 'success',
          message: ret.message
        })
        if (this.list.length === 1) {
          this.listQuery.current -= 1
        }
        this.getList()
      })
    },
    handleDownload(filename) {
      const params = {
        name: filename
      }
      Common.loadExcel(params).then(res => {
        this.content = res
        this.filename = filename + '.xlsx'
        const blob = new Blob([this.content])
        if (window.navigator.msSaveOrOpenBlob) {
          // 兼容IE10
          navigator.msSaveBlob(blob, this.filename)
        } else {
          //  chrome/firefox
          const aTag = document.createElement('a')
          aTag.download = this.filename
          aTag.href = URL.createObjectURL(blob)
          aTag.click()
          URL.revokeObjectURL(aTag.href)
        }
      })
    },
    submitUpload() {
      this.$refs.upload.submit()
    },
    cancelUpload() {
      this.$refs.upload.clearFiles()
      this.uploadDialogFormVisible = false
    },
    upload(obj) {
      this.$refs['uploadDataForm'].validate(valid => {
        if (valid) {
          const data = {} // 创建form对象
          data.sysFile = obj.sysFile// 通过append向form对象添加数据
          User.upload(data).then(response => {
            this.$refs.upload.clearFiles()
            this.uploadDialogFormVisible = false
            this.$message({
              message: response.message,
              type: 'success'
            })
            this.getList()
            // this.handleFilter()
          })
        }
      })
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
</style>

<style>
</style>
