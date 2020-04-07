<!-- 部门权限表 -->
<template>
  <div class="app-container">
    <!--条件开始-->
    <div class="my-filter-container">
      <div class="my-filter-list">
        <span class="my-filter-item my-filter-item-search" />
      </div>
    </div>
    <!--条件结束-->

    <el-row :gutter="20">
      <!--主体Demo开始-->
      <el-col :span="8">
        <div class="my-layout">
          <div class="my-layout-height my-layout-height-border-right">
            <el-scrollbar class="my-scroll my-scroll-column">
              <div class="my-scroll-padding-right">
                <!--组织架构树Demo开始-->
                <el-tree
                  ref="departmentTree"
                  :data="list"
                  :expand-on-click-node="false"
                  node-key="id"
                  highlight-current
                  default-expand-all
                  class="filter-tree"
                  @node-click="handleUpdate"
                >
                  <span slot-scope="{ node, data }" class="custom-tree-node">
                    <span>{{ data.departmentName }}</span>
                  </span>
                </el-tree>
                <!--组织架构树Demo结束-->
              </div>
            </el-scrollbar>
          </div>

        </div>
      </el-col>
      <!--主体Demo结束-->
      <el-col :span="16">
        <!--编辑开始-->
        <div class="my-from-container" style="width:600px;">
          <el-form ref="updateForm" :rules="rules" :model="temp" label-position="right" label-width="100px">
            <el-form-item label="机构权限：" prop="departmentPermissions">
              <el-tree
                ref="permissionsTree"
                :data="list"
                :props="treeProps"
                node-key="departmentCode"
                show-checkbox
                :default-expanded-keys="temp.departmentPermissions"
                class="my-from-item-full"
                @check="handlePermissionsCheck"
              />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="updateData()">确定</el-button>
            </el-form-item>
          </el-form>
        </div>
        <!--编辑结束-->
      </el-col>
    </el-row>

  </div>
</template>

<script>
import Department from '@/api/system/sys_department'
import DepartmentPermission from '@/api/system/sys_department_permission'

export default {
  name: 'DepartmentPermission',
  components: {
  },
  data() {
    return {
      refs: undefined,
      list: null,
      temp: {},
      currentDepartmentId: undefined, // 当前选择的部门id
      currentDepartmentCode: undefined, // 当前选择的部门编码
      treeProps: {
        label: 'departmentName',
        disabled: this.setDisabled
      },
      rules: {
        departmentPermissions: [{ required: true, message: '机构权限不能为空', trigger: 'blur' }]
      }
    }
  },
  mounted() {
    this.refs = this.$refs
    this.initData()
  },
  methods: {
    initData() {
      Department.list().then(response => {
        this.list = response.data
      })
    },
    handleUpdate(row) {
      this.currentDepartmentCode = row.departmentCode
      // 初始化禁用
      this.treeProps.disabled = false
      DepartmentPermission.list({ departmentId: (this.currentDepartmentId = row.id) }).then(response => {
        this.temp = response.data[0]
        this.temp.departmentPermissions = this.temp.departmentPermissions
          ? this.temp.departmentPermissions.split(',') : []
        this.refs.permissionsTree.setCheckedKeys(this.temp.departmentPermissions)
        this.$nextTick(() => {
          // 在数据准备好后，启用禁用
          this.treeProps.disabled = this.setDisabled
          this.refs.updateForm.clearValidate()
        })
      })
    },
    updateData(row) {
      if (!this.currentDepartmentId) {
        this.$message({
          message: '请先选择机构再进行操作',
          type: 'warning'
        })
        return
      }
      this.refs.updateForm.validate((valid) => {
        if (valid) {
          var submit = Object.assign({}, this.temp)
          DepartmentPermission.update(this.setSubmit(submit)).then(ret => {
            this.$message({
              message: ret.message,
              type: 'success'
            })
            this.handleUpdate({ id: this.currentDepartmentId })
          })
        }
      })
    },
    setDisabled(data) { // 自己部门不能更改
      return data.departmentCode.startsWith(this.currentDepartmentCode)
    },
    handlePermissionsCheck(data, checkedStatusObj) { // 权限树选择处理
      this.temp.departmentPermissions = checkedStatusObj.checkedKeys
    },
    setSubmit(data) { // 设置提交数据
      data.departmentPermissions = data.departmentPermissions.toString()
      return data
    }
  }
}
</script>

