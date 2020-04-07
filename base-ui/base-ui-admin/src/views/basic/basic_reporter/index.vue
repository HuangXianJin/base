<!-- 上报人基础信息 -->
<template>
  <div class="app-container">
    <!--条件开始-->
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
    </div>
    <!--条件结束-->

    <!--表格开始-->
    <el-table key="0" v-loading="listLoading" :data="list" border fit highlight-current-row @sort-change="sortChange">
      <el-table-column label="序号" prop="id" align="center" width="60">
        <template slot-scope="scope">
          <span>{{ scope.$index + (listQuery.current - 1) * listQuery.size + 1 }}</span>
        </template>
      </el-table-column>
      <el-table-column label="单位" align="center" prop="unit">
        <template slot-scope="scope">
          <span>{{ scope.row.unit }}</span>
        </template>
      </el-table-column>
      <el-table-column label="单位电话" align="center" prop="work_phone">
        <template slot-scope="scope">
          <span>{{ scope.row.workPhone }}</span>
        </template>
      </el-table-column>
      <el-table-column label="登填人名" align="center" prop="name">
        <template slot-scope="scope">
          <span>{{ scope.row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column label="登填人电话" align="center" prop="phone">
        <template slot-scope="scope">
          <span>{{ scope.row.phone }}</span>
        </template>
      </el-table-column>
    </el-table>
    <pagination v-show="total > 0" :total="total" :page.sync="listQuery.current" :limit.sync="listQuery.size" @pagination="getList" />
    <!--表格结束-->
    <!--新增开始-->
    <el-dialog :visible.sync="createDialogFormVisible" title="新增" width="600px">
      <el-form ref="createForm" :rules="rules" :model="temp" label-position="right" label-width="100px">
        <div class="sameform">
          <el-form-item label="sys_user表id：" prop="sysUserId">
            <el-input v-model="temp.sysUserId" type="text" placeholder="请输入sys_user表id" />
          </el-form-item>
          <el-form-item label="单位：" prop="unit">
            <el-input v-model="temp.unit" type="text" placeholder="请输入单位" />
          </el-form-item>
          <el-form-item label="单位电话：" prop="workPhone">
            <el-input v-model="temp.workPhone" type="text" placeholder="请输入单位电话" />
          </el-form-item>
          <el-form-item label="登填人名：" prop="name">
            <el-input v-model="temp.name" type="text" placeholder="请输入登填人名" />
          </el-form-item>
          <el-form-item label="登填人电话：" prop="phone">
            <el-input v-model="temp.phone" type="text" placeholder="请输入登填人电话" />
          </el-form-item>
          <el-form-item label="数据状态,0:无效  1：有效：" prop="status">
            <el-input v-model="temp.status" type="text" placeholder="请输入数据状态,0:无效  1：有效" />
          </el-form-item>
        </div>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="createData()">确定</el-button>
        <el-button @click="createDialogFormVisible = false">取消</el-button>
      </div>
    </el-dialog>
    <!--新增结束-->
    <!--编辑开始-->
    <el-dialog :visible.sync="updateDialogFormVisible" title="编辑" width="600px">
      <el-form ref="updateForm" :rules="rules" :model="temp" label-position="right" label-width="100px">
        <div class="sameform">
          <el-form-item label="sys_user表id：" prop="sysUserId">
            <el-input v-model="temp.sysUserId" type="text" placeholder="请输入sys_user表id" />
          </el-form-item>
          <el-form-item label="单位：" prop="unit">
            <el-input v-model="temp.unit" type="text" placeholder="请输入单位" />
          </el-form-item>
          <el-form-item label="单位电话：" prop="workPhone">
            <el-input v-model="temp.workPhone" type="text" placeholder="请输入单位电话" />
          </el-form-item>
          <el-form-item label="登填人名：" prop="name">
            <el-input v-model="temp.name" type="text" placeholder="请输入登填人名" />
          </el-form-item>
          <el-form-item label="登填人电话：" prop="phone">
            <el-input v-model="temp.phone" type="text" placeholder="请输入登填人电话" />
          </el-form-item>
          <el-form-item label="数据状态,0:无效  1：有效：" prop="status">
            <el-input v-model="temp.status" type="text" placeholder="请输入数据状态,0:无效  1：有效" />
          </el-form-item>
        </div>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="updateData()">确定</el-button>
        <el-button @click="updateDialogFormVisible = false">取消</el-button>
      </div>
    </el-dialog>
    <!--编辑结束-->
  </div>
</template>
<script>

import BasicReporter from '@/api/basic/basic_reporter'

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
        unit: [{ required: true, message: '单位不能为空', trigger: 'blur' }],
        workPhone: [{ required: true, message: '单位电话不能为空', trigger: 'blur' }],
        name: [{ required: true, message: '登填人名不能为空', trigger: 'blur' }],
        phone: [{ required: true, message: '登填人电话不能为空', trigger: 'blur' }]
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
      BasicReporter.page(this.listQuery).then(response => {
        this.listQuery.current = response.data.current
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
          BasicReporter.create(this.temp).then(ret => {
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
          BasicReporter.update(this.temp).then(ret => {
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
      BasicReporter.remove(row.id).then((ret) => {
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

