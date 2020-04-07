<!-- 系统资源-API接口 -->
<template>
  <div class="app-container my-app-container my-index-sys-api">
    <!--条件Demo开始-->
    <div class="my-filter-container">
      <div class="my-filter-list">
        <span class="my-filter-item my-filter-item-search">
          <span class="search-input">
            <el-input v-model="listQuery.filter" placeholder="关键字" clearable @change="handleFilter" @keyup.enter.native="handleFilter"/>
          </span>
          <span class="search-btn">
            <el-button type="primary" icon="el-icon-search" @click="handleFilter">搜索</el-button>
          </span>
        </span>
      </div>

      <div class="my-filter-btn">
        <el-button type="primary" icon="el-icon-delete" @click="clearApi">清理</el-button>
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
              <el-table-column label="序号" prop="id" fixed align="center" width="60">
                <template slot-scope="scope">
                  <span>{{ scope.$index + (listQuery.current - 1) * listQuery.size + 1 }}</span>
                </template>
              </el-table-column>

              <el-table-column label="接口名称" align="left" prop="api_name" sortable="custom">
                <template slot-scope="scope">
                  <span>{{ scope.row.apiName }}</span>
                </template>
              </el-table-column>

              <el-table-column label="请求方式" align="left" prop="request_method" sortable="custom" width="100">
                <template slot-scope="scope">
                  <span>{{ scope.row.requestMethod }}</span>
                </template>
              </el-table-column>

              <el-table-column label="服务ID" align="left" prop="service_id" sortable="custom" width="100">
                <template slot-scope="scope">
                  <span>{{ scope.row.serviceId }}</span>
                </template>
              </el-table-column>

              <el-table-column label="请求路径" align="left" prop=" path" sortable="custom">
                <template slot-scope="scope">
                  <span>{{ scope.row.path }}</span>
                </template>
              </el-table-column>

              <el-table-column label="状态" align="center" prop="status" sortable="custom" width="100">
                <template slot-scope="scope">
                  <span :class="scope.row.status === 1?'text-color-main':'text-color-danger'">{{ status[scope.row.status] }}</span>
                </template>
              </el-table-column>

              <el-table-column label="身份认证" align="center" prop="is_auth" sortable="custom" width="100">
                <template slot-scope="scope">
                  <span :class="scope.row.isAuth === 1?'text-color-main':'text-color-danger'">{{ isAuth[scope.row.isAuth] }}</span>
                </template>
              </el-table-column>

              <el-table-column label="公开访问" align="center" prop="is_open" sortable="custom" width="100">
                <template slot-scope="scope">
                  <span>{{ isOpen[scope.row.isOpen] }}</span>
                </template>
              </el-table-column>

              <el-table-column label="操作" align="center" fixed="right" width="100">
                <template slot-scope="scope">
                  <el-button type="primary" icon="el-icon-edit" circle alt="编辑" title="编辑" @click="handleUpdate(scope.row)"/>
                  <el-button type="danger" icon="el-icon-delete" circle alt="删除" title="删除" @click="handleRemove(scope.row)"/>
                </template>
              </el-table-column>
            </el-table>

            <pagination v-show="total > 0" :total="total" :page.sync="listQuery.current" :limit.sync="listQuery.size" @pagination="getList"/>
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
          <el-form-item label="接口编码" prop="apiCode">
            <el-input v-model="temp.apiCode" type="text" placeholder="接口编码"/>
          </el-form-item>

          <el-form-item label="接口名称" prop="apiName">
            <el-input v-model="temp.apiName" type="text" placeholder="接口名称"/>
          </el-form-item>

          <el-form-item label="资源描述" prop="apiDesc">
            <el-input v-model="temp.apiDesc" type="textarea" placeholder="资源描述"/>
          </el-form-item>

          <el-form-item label="请求方式" prop="requestMethod">
            <el-input v-model="temp.requestMethod" type="text" placeholder="请求方式"/>
          </el-form-item>

          <el-form-item label="服务ID" prop="serviceId">
            <el-input v-model="temp.serviceId" type="text" placeholder="服务ID"/>
          </el-form-item>

          <el-form-item label="请求路径" prop="path">
            <el-input v-model="temp.path" type="text" placeholder="请求路径"/>
          </el-form-item>

          <el-form-item label="状态" prop="status">
            <el-radio-group v-model="temp.status">
              <el-radio-button v-for="(value, key) in status" :key="key" :label="key">{{ value }}</el-radio-button>
            </el-radio-group>
          </el-form-item>

          <el-form-item label="身份认证" prop="isAuth">
            <el-radio-group v-model="temp.isAuth">
              <el-radio-button v-for="(value, key) in isAuth" :key="key" :label="key">{{ value }}</el-radio-button>
            </el-radio-group>
          </el-form-item>

          <el-form-item label="公开访问" prop="isOpen">
            <el-radio-group v-model="temp.isOpen">
              <el-radio-button v-for="(value, key) in isOpen" :key="key" :label="key">{{ value }}</el-radio-button>
            </el-radio-group>
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
        <el-form ref="updateForm" :rules="rules" :model="temp" label-position="right" label-width="100px">
          <el-form-item label="接口编码" prop="apiCode">
            <el-input v-model="temp.apiCode" type="text" placeholder="接口编码"/>
          </el-form-item>

          <el-form-item label="接口名称" prop="apiName">
            <el-input v-model="temp.apiName" type="text" placeholder="接口名称"/>
          </el-form-item>

          <el-form-item label="资源描述" prop="apiDesc">
            <el-input v-model="temp.apiDesc" type="textarea" placeholder="资源描述"/>
          </el-form-item>

          <el-form-item label="请求方式" prop="requestMethod">
            <el-input v-model="temp.requestMethod" type="text" placeholder="请求方式"/>
          </el-form-item>

          <el-form-item label="服务ID" prop="serviceId">
            <el-input v-model="temp.serviceId" type="text" placeholder="服务ID"/>
          </el-form-item>

          <el-form-item label="请求路径" prop="path">
            <el-input v-model="temp.path" type="text" placeholder="请求路径"/>
          </el-form-item>

          <el-form-item label="状态" prop="status">
            <el-radio-group v-model="temp.status">
              <el-radio-button v-for="(value, key) in status" :key="key" :label="key">{{ value }}</el-radio-button>
            </el-radio-group>
          </el-form-item>

          <el-form-item label="身份认证" prop="isAuth">
            <el-radio-group v-model="temp.isAuth">
              <el-radio-button v-for="(value, key) in isAuth" :key="key" :label="key">{{ value }}</el-radio-button>
            </el-radio-group>
          </el-form-item>

          <el-form-item label="公开访问" prop="isOpen">
            <el-radio-group v-model="temp.isOpen">
              <el-radio-button v-for="(value, key) in isOpen" :key="key" :label="key">{{ value }}</el-radio-button>
            </el-radio-group>
          </el-form-item>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="updateData()">确定</el-button>
        <el-button @click="updateDialogFormVisible = false">取消</el-button>
      </div>
    </el-dialog>
    <!--编辑Demo结束-->
  </div>
</template>

<script>

import Api from '@/api/system/sys_api'

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
      status: { 1: '启用', 0: '禁用' },
      isOpen: { 0: '拒绝', 1: '允许' },
      isAuth: { 0: '关闭', 1: '开启' },
      createDialogFormVisible: false,
      updateDialogFormVisible: false,
      rules: {
        apiCode: [{ required: true, message: '不能为空', trigger: 'blur' }],
        apiName: [{ required: true, message: '不能为空', trigger: 'blur' }],
        requestMethod: [{ required: true, message: '不能为空', trigger: 'blur' }],
        serviceId: [{ required: true, message: '不能为空', trigger: 'blur' }],
        path: [{ required: true, message: '不能为空', trigger: 'blur' }],
        priority: [{ required: true, message: '不能为空', trigger: 'blur' }],
        status: [{ required: true, message: '不能为空', trigger: 'blur' }],
        isAuth: [{ required: true, message: '不能为空', trigger: 'blur' }],
        isOpen: [{ required: true, message: '不能为空', trigger: 'blur' }]
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
      this.listLoading = true
      Api.list(this.listQuery).then(response => {
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
          Api.create(this.temp).then(ret => {
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
          Api.update(this.temp).then(ret => {
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
      Api.remove(row.id).then((ret) => {
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
    clearApi() {
      this.$confirm('此操作将永久删除数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        Api.clearApi().then(() => {
          this.getList()
        })
      })
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
</style>

<style>
</style>
