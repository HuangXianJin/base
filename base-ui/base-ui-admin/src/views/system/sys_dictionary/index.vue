<!-- 系统资源-菜单信息 -->
<template>
  <div class="app-container my-app-container my-index-sys-Dictionary">
    <el-row :gutter="20">
      <el-col :span="8">
        <div class="my-layout-height my-layout-height-border-right">
          <!--主体Demo开始-->
          <el-scrollbar class="my-scroll my-scroll-column">
            <div class="my-scroll-padding-right">
              <!--菜单树Demo开始-->
              <el-input v-model="filterText" placeholder="关键字" class="my-tree-container-input" />
              <el-tree ref="permissionsTree" :data="tree" :expand-on-click-node="false" :filter-node-method="filterNode" node-key="id" class="my-tree-container" @node-click="handleUpdate">
                <span slot-scope="{ node, data }" class="custom-tree-node">
                  <span class="my-by-one">{{ data.itemName }}</span>
                  <span>
                    <el-button type="success" icon="el-icon-plus" circle alt="新增" title="新增" @click="handleCreate(data)" />
                    <el-button type="danger" icon="el-icon-delete" circle alt="删除" title="删除" @click="handleRemove(data)" />
                  </span>
                </span>
              </el-tree>
              <!--菜单树Demo结束-->
            </div>
          </el-scrollbar>
        </div>
      </el-col>
      <el-col :span="16">
        <div class="my-layout-height">
          <!--条件Demo开始-->
          <div class="my-filter-container">
            <div class="my-filter-btn">
              <el-button type="primary" icon="el-icon-plus" @click="handleCreate">添加根节点</el-button>
            </div>
          </div>
          <!--条件Demo结束-->
          <!--主体Demo开始-->
          <div class="my-layout-height my-layout-height-left-right-filter">
            <el-scrollbar class="my-scroll my-scroll-column">
              <div class="my-scroll-padding-right">
                <!--编辑Demo开始-->
                <div class="my-from-container" style="width:600px;">
                  <el-form ref="updateForm" :rules="rules" :model="updateForm" label-position="right" label-width="120px">
                    <el-form-item label="根节点编码：" prop="itemCode">
                      <el-input v-model="updateForm.indexCode" type="text" placeholder="请输入编码" disabled />
                    </el-form-item>
                    <el-form-item label="编码：" prop="itemCode">
                      <el-input v-model="updateForm.itemCode" type="text" placeholder="请输入编码" disabled />
                    </el-form-item>
                    <el-form-item label="名称：" prop="itemName">
                      <el-input v-model="updateForm.itemName" type="text" placeholder="请输入名称" />
                    </el-form-item>
                    <el-form-item label="取值：" prop="itemValue">
                      <el-input v-model="updateForm.itemValue" type="text" placeholder="请输入取值" />
                    </el-form-item>
                    <el-form-item label="描述：" prop="description">
                      <el-input v-model="updateForm.description" type="text" placeholder="请输入描述" />
                    </el-form-item>
                    <el-form-item label="优先级：" prop="sort">
                      <el-input v-model="updateForm.sort" type="text" placeholder="优先级 越小越靠前" />
                    </el-form-item>

                    <el-form-item label="状态" prop="status">
                      <el-radio-group v-model="updateForm.status">
                        <el-radio-button label="1">启用</el-radio-button>
                        <el-radio-button label="0">禁用</el-radio-button>
                      </el-radio-group>
                    </el-form-item>

                    <el-form-item>
                      <el-button type="primary" @click="updateData()">确定</el-button>
                    </el-form-item>
                  </el-form>
                </div>
                <!--编辑Demo结束-->
              </div>
            </el-scrollbar>
          </div>
          <!--主体Demo结束-->
        </div>
      </el-col>
    </el-row>

    <!--新增Demo开始-->
    <el-dialog :visible.sync="createDialogFormVisible" :close-on-click-modal="false" title="新增" width="600px">
      <div class="my-form-container">
        <el-form ref="createForm" :rules="rules" :model="createForm" label-position="right" label-width="100px">
          <el-form-item label="编码：" prop="itemCode">
            <el-input v-model="createForm.itemCode" type="text" placeholder="请输入编码" />
          </el-form-item>
          <el-form-item label="名称：" prop="itemName">
            <el-input v-model="createForm.itemName" type="text" placeholder="请输入名称" />
          </el-form-item>
          <el-form-item label="取值：" prop="itemValue">
            <el-input v-model="createForm.itemValue" type="text" placeholder="请输入取值" />
          </el-form-item>
          <el-form-item label="描述：" prop="description">
            <el-input v-model="createForm.description" type="text" placeholder="请输入描述" />
          </el-form-item>
          <el-form-item label="优先级：" prop="sort">
            <el-input v-model="createForm.sort" type="text" placeholder="优先级 越小越靠前" />
          </el-form-item>

          <el-form-item label="状态" prop="status">
            <el-radio-group v-model="createForm.status">
              <el-radio-button label="1">启用</el-radio-button>
              <el-radio-button label="0">禁用</el-radio-button>
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
  </div>
</template>

<script>

import Dictionary from '@/api/system/sys_dictionary'
import svgIcons from '@/icons/svg-icons'

export default {
  components: {

  },
  data() {
    return {
      svgIcons,
      refs: undefined,
      filterText: '',
      tableKey: 0,
      list: null,
      tree: null,
      total: 0,
      listLoading: false,
      listQuery: {
        current: 1,
        size: 10,
        filter: undefined,
        ascs: undefined,
        descs: undefined
      },
      apiFilter: '',
      apis: [],
      filterApis: [],
      grantedApis: [],
      createForm: {},
      updateForm: {},
      createDialogFormVisible: false,
      updateDialogFormVisible: false,
      grantDialogFormVisible: false,

      rules: {
        itemCode: [{ required: true, message: '不能为空', trigger: 'blur' }],
        itemName: [{ required: true, message: '不能为空', trigger: 'blur' }],
        itemValue: [{ required: true, message: '不能为空', trigger: 'blur' }],
        status: [{ required: true, message: '不能为空', trigger: 'blur' }]
      }
    }
  },
  watch: {
    filterText(val) {
      this.$refs.permissionsTree.filter(val)
    }
  },
  mounted() {
    this.refs = this.$refs
    this.initData()
    this.treeData()
  },
  methods: {
    initData() {

    },
    treeData() {
      Dictionary.tree().then(response => {
        this.tree = response.data
      })
    },

    filterNode(value, data) {
      if (!value) return true
      if (data.menuName) {
        return data.menuName.indexOf(value) !== -1
      }
    },
    handleCreate(parent) {
      this.createForm = {}
      if (parent) {
        this.createForm.parentId = parent.id
        this.createForm.indexCode = parent.indexCode
      }
      this.createDialogFormVisible = true
      this.$nextTick(() => {
        this.refs.createForm.clearValidate()
      })
    },
    createData() {
      this.refs.createForm.validate((valid) => {
        if (valid) {
          if (!this.createForm.indexCode) {
            this.createForm.indexCode = this.createForm.itemCode
          }
          Dictionary.create(this.createForm).then(ret => {
            this.createDialogFormVisible = false
            this.treeData()
          })
        }
      })
    },
    handleUpdate(row) {
      this.updateForm = Object.assign({}, row) // copy obj
      this.grantedApis = []
      this.$nextTick(() => {
        this.refs.updateForm.clearValidate()
      })
    },
    updateData(row) {
      this.refs.updateForm.validate((valid) => {
        if (valid) {
          Dictionary.update(this.updateForm).then(ret => {
            this.updateDialogFormVisible = false
            this.treeData()
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
      Dictionary.remove(row.id).then((ret) => {
        this.$message({
          type: 'success',
          message: ret.message
        })
        this.treeData()
      })
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
</style>

<style>
</style>

