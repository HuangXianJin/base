<!-- $!{table.comment} -->
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
      <div class="my-filter-btn">
        <el-button type="primary" icon="el-icon-plus" @click="handleCreate">新增</el-button>
      </div>
    </div>
    <!--条件结束-->

    <!--表格开始-->
    <el-table  key="0" v-loading="listLoading" :data="list" border fit highlight-current-row class="sametable" @sort-change="sortChange">
      <el-table-column label="序号" prop="id" align="center" width="60">
        <template slot-scope="scope">
          <span>{{ scope.$index + (listQuery.current - 1) * listQuery.size + 1 }}</span>
        </template>
      </el-table-column>

      #foreach($field in ${table.fields})
      #if(!${field.keyFlag} && "$!field.propertyName" != "createId" && "$!field.propertyName" != "createTime" && "$!field.propertyName" != "modifyTime" && "$!field.propertyName" != "modifyId" && "$!field.propertyName" != "isPersist")
      <el-table-column label="$!{field.comment}" align="center" prop="$!{field.name}" sortable="custom">
        <template slot-scope="scope">
          <span>{{ scope.row.${field.propertyName} }}</span>
        </template>
      </el-table-column>
      #end
      #end

      <el-table-column label="操作" align="center" width="100">
        <template slot-scope="scope">
          <el-button type="primary" icon="iconfont my-icon-editor" circle alt="编辑" title="编辑" @click="handleUpdate(scope.row)" />
          <el-button type="danger" icon="iconfont my-icon-del" circle alt="删除" title="删除" @click="handleRemove(scope.row)" />
        </template>
      </el-table-column>
    </el-table>
    <pagination v-show="total > 0" :total="total" :page.sync="listQuery.current" :limit.sync="listQuery.size" @pagination="getList" />
    <!--表格结束-->
    <!--新增开始-->
    <el-dialog :visible.sync="createDialogFormVisible" title="新增" width="450px">
      <el-form ref="createForm" :rules="rules" :model="temp" label-position="right" label-width="100px">
        <div class="sameform">
          #foreach($field in ${table.fields})
          #if(!${field.keyFlag} && "$!field.propertyName" != "createId" && "$!field.propertyName" != "createTime" && "$!field.propertyName" != "modifyTime" && "$!field.propertyName" != "modifyId" && "$!field.propertyName" != "isPersist")
          <el-form-item label="$!{field.comment}：" prop="${field.propertyName}">
            <el-input v-model="temp.${field.propertyName}" type="text" placeholder="请输入$!{field.comment}" />
          </el-form-item>
          #end
          #end
        </div>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="createData()">确定</el-button>
        <el-button @click="createDialogFormVisible = false">取消</el-button>
      </div>
    </el-dialog>
    <!--新增结束-->
    <!--编辑开始-->
    <el-dialog :visible.sync="updateDialogFormVisible" title="编辑" width="450px">
      <el-form ref="updateForm" :rules="rules" :model="temp" label-position="right" label-width="100px">
        <div class="sameform">
          #foreach($field in ${table.fields})
          #if(!${field.keyFlag} && "$!field.propertyName" != "createId" && "$!field.propertyName" != "createTime" && "$!field.propertyName" != "modifyTime" && "$!field.propertyName" != "modifyId" && "$!field.propertyName" != "isPersist")
          <el-form-item label="$!{field.comment}：" prop="${field.propertyName}">
            <el-input v-model="temp.${field.propertyName}" type="text" placeholder="请输入$!{field.comment}" />
          </el-form-item>
          #end
          #end
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

import ${entity} from '@/api/${package.ModuleName}/${table.name}'

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
#foreach($field in ${table.fields})
#if(!${field.keyFlag} && "$!field.propertyName" != "createId" && "$!field.propertyName" != "createTime" && "$!field.propertyName" != "modifyTime" && "$!field.propertyName" != "modifyId" && "$!field.propertyName" != "isPersist")
        ${field.propertyName}: [{ required: true, message: '${field.comment}不能为空', trigger: 'blur' }],
#end
#end
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
      ${entity}.list(this.listQuery).then(response => {
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
          ${entity}.create(this.temp).then(ret => {
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
          ${entity}.update(this.temp).then(ret => {
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
      ${entity}.remove(row.id).then((ret) => {
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

