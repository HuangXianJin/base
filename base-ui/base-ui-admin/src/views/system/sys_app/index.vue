<!-- 客户端应用 -->
<template>
  <div class="app-container my-app-container my-index-sys-app">

    <!--条件Demo开始-->
    <div class="my-filter-container">
      <div class="my-filter-list">

        <span class="my-filter-item my-filter-item-search">
          <span class="search-input">
            <el-input v-model="listQuery.filter" placeholder="关键字" clearable @change="handleFilter" @keyup.enter.native="handleFilter" />
          </span>
          <span class="search-btn">
            <el-button type="primary" icon="el-icon-search" @click="handleFilter">
              搜索
            </el-button>
          </span>
        </span>

      </div>

      <div class="my-filter-btn">
        <el-button type="primary" icon="el-icon-plus" @click="handleCreate">
          新增
        </el-button>
      </div>

    </div>
    <!--条件Demo结束-->

    <!--主体Demo开始-->
    <div class="my-layout-height my-layout-height-filter-table">
      <el-scrollbar class="my-scroll my-scroll-column">
        <div class="my-scroll-padding-right">
          <!--表格Demo开始-->
          <div class="my-table-container">

            <el-table :key="tableKey" v-loading="listLoading" :data="list" stripe fit highlight-current-row @sort-change="sortChange">

              <el-table-column label="序号" prop="id" fixed align="center" width="50">
                <template slot-scope="scope">
                  <span>{{ scope.$index + (listQuery.current - 1) * listQuery.size + 1 }}</span>
                </template>
              </el-table-column>

              <el-table-column label="租户" align="center" sortable="custom">
                <template slot-scope="scope">
                  <span>{{ scope.row.tenantCode }}</span>
                </template>
              </el-table-column>

              <el-table-column label="API访问key" align="center" prop="api_key" sortable="custom">
                <template slot-scope="scope">
                  <span>{{ scope.row.apiKey }}</span>
                </template>
              </el-table-column>

              <el-table-column label="API访问密钥" align="center" prop="secret_key" sortable="custom">
                <template slot-scope="scope">
                  <span>{{ scope.row.secretKey }}</span>
                </template>
              </el-table-column>

              <el-table-column label="应用名称" align="center" prop="app_name" sortable="custom">
                <template slot-scope="scope">
                  <span>{{ scope.row.appName }}</span>
                </template>
              </el-table-column>

              <el-table-column label="应用类型" align="center" prop="app_type" sortable="custom">
                <template slot-scope="scope">
                  <span>{{ appType[scope.row.appType] }}</span>
                </template>
              </el-table-column>

              <el-table-column label="应用描述" align="center" prop="app_desc" sortable="custom">
                <template slot-scope="scope">
                  <span>{{ scope.row.appDesc }}</span>
                </template>
              </el-table-column>

              <el-table-column label="状态" align="center" prop="status" sortable="custom">
                <template slot-scope="scope">
                  <span :class="scope.row.status === 1?'text-color-main':'text-color-danger'">
                    {{ status[scope.row.status] }}
                  </span>
                </template>
              </el-table-column>

              <el-table-column label="操作" align="center" fixed="right" width="90">
                <template slot-scope="scope">
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
        <el-form ref="createForm" :rules="rules" :model="temp" label-position="right" label-width="100px">

          <el-form-item label="租户" prop="tenantCode">
            <el-input v-model="temp.tenantCode" type="text" placeholder="租户" />
          </el-form-item>

          <el-form-item label="应用名称" prop="appName">
            <el-input v-model="temp.appName" type="text" placeholder="应用名称" />
          </el-form-item>

          <el-form-item label="应用类型" prop="appType">
            <el-radio-group v-model="temp.appType">
              <el-radio-button v-for="(value, key) in appType" :key="key" :label="key">
                {{ value }}
              </el-radio-button>
            </el-radio-group>
          </el-form-item>

          <el-form-item label="应用描述" prop="appDesc">
            <el-input v-model="temp.appDesc" type="textarea" placeholder="应用描述" />
          </el-form-item>

          <el-form-item label="状态" prop="status">
            <el-radio-group v-model="temp.status">
              <el-radio-button v-for="(value, key) in status" :key="key" :label="key">
                {{ value }}
              </el-radio-button>
            </el-radio-group>
          </el-form-item>

        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="createData()">
          确定
        </el-button>
        <el-button @click="createDialogFormVisible = false">
          取消
        </el-button>
      </div>
    </el-dialog>
    <!--新增Demo结束-->

    <!--编辑Demo开始-->
    <el-dialog :visible.sync="updateDialogFormVisible" :close-on-click-modal="false" title="编辑" width="600px">
      <div class="my-from-container">
        <el-form ref="updateForm" :rules="rules" :model="temp" label-position="right" label-width="100px">
          <el-form-item label="租户" prop="tenantCode">
            <el-input v-model="temp.tenantCode" type="text" placeholder="租户" />
          </el-form-item>

          <el-form-item label="应用名称" prop="appName">
            <el-input v-model="temp.appName" type="text" placeholder="应用名称" />
          </el-form-item>

          <el-form-item label="应用类型" prop="appType">
            <el-radio-group v-model="temp.appType">
              <el-radio-button v-for="(value, key) in appType" :key="key" :label="key">
                {{ value }}
              </el-radio-button>
            </el-radio-group>
          </el-form-item>

          <el-form-item label="应用描述" prop="appDesc">
            <el-input v-model="temp.appDesc" type="textarea" placeholder="应用描述" />
          </el-form-item>

          <el-form-item label="状态" prop="status">
            <el-radio-group v-model="temp.status">
              <el-radio-button v-for="(value, key) in status" :key="key" :label="key">
                {{ value }}
              </el-radio-button>
            </el-radio-group>
          </el-form-item>

        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="updateData()">
          确定
        </el-button>
        <el-button @click="updateDialogFormVisible = false">
          取消
        </el-button>
      </div>
    </el-dialog>
    <!--编辑Demo结束-->

  </div>
</template>

<script>

import App from '@/api/system/sys_app'

export default {
  components: {
    Pagination: () => import('@/components/Pagination')
  },
  data() {
    return {
      refs: undefined,
      tableKey: 0,
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
      createDialogFormVisible: false,
      updateDialogFormVisible: false,
      appType: { server: '服务应用', app: '手机应用', web: '网页应用' },
      status: { 1: '启用', 0: '禁用' },
      rules: {
        tenantCode: [
          {
            required: true,
            message: '不能为空',
            trigger: 'blur'
          }
        ],
        appName: [
          {
            required: true,
            message: '不能为空',
            trigger: 'blur'
          }
        ],
        appType: [
          {
            required: true,
            message: '请选择',
            trigger: 'blur'
          }
        ],
        appDesc: [
          {
            required: true,
            message: '不能为空',
            trigger: 'blur'
          }
        ],
        status: [
          {
            required: true,
            message: '请选择',
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

    },
    getList() {
      App.page(this.listQuery).then(response => {
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
          App.create(this.temp).then(ret => {
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
          App.update(this.temp).then(ret => {
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
      App.remove(row.id).then((ret) => {
        this.$message({
          type: 'success',
          message: ret.message
        })
        if (this.list.length === 1) {
          this.listQuery.current -= 1
        }
        this.getList()
      })
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
</style>

<style>
</style>
