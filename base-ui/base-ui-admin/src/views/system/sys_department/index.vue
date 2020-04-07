<!-- 部门表 -->
<template>
  <div class="app-container my-app-container my-index-xx">

    <!--条件Demo开始-->
    <div class="my-filter-container my-filter-container-padding-right">
      <div class="my-filter-btn">
        <el-button type="primary" icon="iconfont my-icon-add" @click="handleCreate()">
          新增机构
        </el-button>
      </div>
    </div>
    <!--条件Demo结束-->

    <!--主体Demo开始-->
    <div class="my-layout">
      <div class="my-layout-height" style="width:500px;height: calc(100vh - 120px);">
        <el-scrollbar class="my-scroll my-scroll-column">
          <div class="my-scroll-padding-right">
            <!--组织架构树Demo开始-->
            <el-tree ref="permissionsTree" :data="list" :expand-on-click-node="false" default-expand-all class="filter-tree">
              <span slot-scope="{ node, data }" class="custom-tree-node">
                <span>{{ data.departmentName }}</span>
                <span>
                  <el-button type="success" icon="el-icon-plus" circle alt="新增子机构" title="新增子机构" @click="handleCreate(data)" />
                  <el-button type="primary" icon="el-icon-edit" circle alt="编辑" title="编辑" @click="handleUpdate(data)" />
                  <el-button type="danger" icon="el-icon-delete" circle alt="删除" title="删除" @click="handleRemove(data)" />
                </span>
              </span>
            </el-tree>
            <!--组织架构树Demo结束-->
          </div>
        </el-scrollbar>
      </div>
    </div>
    <!--主体Demo结束-->

    <!--新增编辑Demo开始-->
    <el-dialog :visible.sync="createOrUpdateVisible" :title="temp.id?'编辑机构信息':'新增机构信息'" width="450px">
      <div class="my-from-container">
        <el-form ref="createOrUpdateForm" :rules="rules" :model="temp" label-position="right" label-width="100px">
          <el-form-item v-if="!temp.id" label="上级部门：" prop="parentDepartmentName">
            <el-input v-model="temp.parentDepartmentName" type="text" disabled placeholder="部门名称" />
          </el-form-item>
          <el-form-item label="部门名称：" prop="departmentName">
            <el-input v-model="temp.departmentName" type="text" placeholder="部门名称" />
          </el-form-item>
          <el-form-item label="描述：" prop="description">
            <el-input v-model="temp.description" type="text" placeholder="描述" />
          </el-form-item>
          <el-form-item label="排序字段：" prop="sort">
            <el-input v-model="temp.sort" type="text" placeholder="排序越小越靠前" />
          </el-form-item>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button v-if="!temp.id" type="primary" @click="createData()">确定</el-button>
        <el-button v-if="temp.id" type="primary" @click="updateData()">确定</el-button>
        <el-button @click="createOrUpdateVisible = false">取消</el-button>
      </div>
    </el-dialog>
    <!--新增编辑Demo结束-->
  </div>
</template>

<script>
import Department from '@/api/system/sys_department'

export default {
  name: 'Department',
  components: {
  },
  data() {
    return {
      refs: undefined,
      list: null,
      temp: {},
      createOrUpdateVisible: false,
      rules: {
        departmentName: [{ required: true, message: '部门名称不能为空', trigger: 'blur' }]
      }
    }
  },
  mounted() {
    this.refs = this.$refs
    this.initData()
  },
  methods: {
    initData() {
      this.getList()
    },
    getList() {
      Department.list().then(response => {
        this.list = response.data
      })
    },
    handleCreate(parent) {
      this.temp = { parentDepartmentName: '无' }
      if (parent) {
        this.temp.tenantCode = parent.tenantCode
        this.temp.parentId = parent.id
        this.temp.departmentCode = parent.departmentCode
        this.temp.parentDepartmentName = parent.departmentName
      }
      this.createOrUpdateVisible = true
      this.$nextTick(() => {
        this.refs.createOrUpdateForm.clearValidate()
      })
    },
    createData() {
      this.refs.createOrUpdateForm.validate((valid) => {
        if (valid) {
          var submit = Object.assign({}, this.temp)
          Department.create(this.setSubmit(submit)).then(ret => {
            this.createOrUpdateVisible = false
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
      this.createOrUpdateVisible = true
      this.$nextTick(() => {
        this.refs.createOrUpdateForm.clearValidate()
      })
    },
    updateData(row) {
      this.refs.createOrUpdateForm.validate((valid) => {
        if (valid) {
          var submit = Object.assign({}, this.temp)
          Department.update(this.setSubmit(submit)).then(ret => {
            this.createOrUpdateVisible = false
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
      Department.remove(row.id).then((ret) => {
        this.$message({
          type: 'success',
          message: ret.message
        })
        this.getList()
      })
    },
    setSubmit(data) { // 设置提交数据
      return data
    }
  }
}
</script>

