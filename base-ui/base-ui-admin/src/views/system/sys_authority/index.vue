<!-- 系统权限-菜单权限、API权限 -->
<template>
  <div class="app-container my-app-container my-index-sys-authority">

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

              <el-table-column label="序号" prop="id" fixed align="center" width="60">
                <template slot-scope="scope">
                  <span>{{ scope.$index + (listQuery.current - 1) * listQuery.size + 1 }}</span>
                </template>
              </el-table-column>

              <el-table-column label="权限标识" align="center" prop="authority" sortable="custom">
                <template slot-scope="scope">
                  <span>{{ scope.row.authority }}</span>
                </template>
              </el-table-column>

              <el-table-column label="菜单资源ID" align="center" prop="menu_id" sortable="custom">
                <template slot-scope="scope">
                  <span>{{ scope.row.menuId }}</span>
                </template>
              </el-table-column>

              <el-table-column label="API资源ID" align="center" prop="api_id" sortable="custom">
                <template slot-scope="scope">
                  <span>{{ scope.row.apiId }}</span>
                </template>
              </el-table-column>

              <el-table-column label="状态" align="center" prop="status" sortable="custom">
                <template slot-scope="scope">
                  <span :class="scope.row.status === 1?'text-color-main':'text-color-danger'">
                    {{ scope.row.status }}
                  </span>
                </template>
              </el-table-column>

              <el-table-column label="操作" align="center" fixed="right" width="100">
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

          <el-form-item label="权限标识" prop="authority">
            <el-input v-model="temp.authority" type="text" placeholder="权限标识" />
          </el-form-item>

          <el-form-item label="菜单资源ID" prop="menuId">
            <el-input v-model="temp.menuId" type="text" placeholder="菜单资源ID" />
          </el-form-item>

          <el-form-item label="API资源ID" prop="apiId">
            <el-input v-model="temp.apiId" type="text" placeholder="API资源ID" />
          </el-form-item>

          <el-form-item label="状态" prop="status">
            <el-input v-model="temp.status" type="text" placeholder="状态" />
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
    <el-dialog :visible.sync="updateDialogFormVisible" :close-on-click-modal="false" title="新增" width="600px">
      <div class="my-from-container">
        <el-form ref="updateForm" :rules="rules" :model="temp" label-position="right" label-width="100px">

          <el-form-item label="权限标识" prop="authority">
            <el-input v-model="temp.authority" type="text" placeholder="权限标识" />
          </el-form-item>

          <el-form-item label="菜单资源ID" prop="menuId">
            <el-input v-model="temp.menuId" type="text" placeholder="菜单资源ID" />
          </el-form-item>

          <el-form-item label="API资源ID" prop="apiId">
            <el-input v-model="temp.apiId" type="text" placeholder="API资源ID" />
          </el-form-item>

          <el-form-item label="状态" prop="status">
            <el-input v-model="temp.status" type="text" placeholder="状态" />
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

import Authority from '@/api/system/sys_authority'

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
      rules: {
        authority: [{ required: true, message: '不能为空', trigger: 'blur' }],
        menuId: [{ required: true, message: '不能为空', trigger: 'blur' }],
        apiId: [{ required: true, message: 'API资源ID不能为空', trigger: 'blur' }],
        status: [{ required: true, message: '不能为空', trigger: 'blur' }]
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
      Authority.list(this.listQuery).then(response => {
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
          Authority.create(this.temp).then(ret => {
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
          Authority.update(this.temp).then(ret => {
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
      Authority.remove(row.id).then((ret) => {
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
