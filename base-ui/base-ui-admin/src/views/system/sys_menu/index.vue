<!-- 系统资源-菜单信息 -->
<template>
  <div class="app-container my-app-container my-index-sys-menu">
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
                  <span class="my-by-one">{{ data.menuName }}</span>
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
                  <el-form ref="updateForm" :rules="rules" :model="updateForm" label-position="right" label-width="100px">
                    <!-- <el-form-item
              label="父级菜单"
              prop="parentId"
            >
              <el-input
                v-model="updateForm.parentId"
                type="text"
                placeholder="父级菜单"
              />
                    </el-form-item>-->

                    <el-form-item label="菜单编码" prop="menuCode">
                      <el-input v-model="updateForm.menuCode" type="text" placeholder="菜单编码" />
                    </el-form-item>

                    <el-form-item label="菜单名称" prop="menuName">
                      <el-input v-model="updateForm.menuName" type="text" placeholder="菜单名称" />
                    </el-form-item>

                    <el-form-item label="描述" prop="menuDesc">
                      <el-input v-model="updateForm.menuDesc" type="textarea" placeholder="描述" />
                    </el-form-item>

                    <el-form-item label="请求路径" prop="path">
                      <el-input v-model="updateForm.path" type="text" placeholder="请求路径" />
                    </el-form-item>

                    <el-form-item label="菜单图标" prop="icon">
                      <div class="icon-item">
                        <el-select v-model="updateForm.icon" placeholder="请选择菜单图标" clearable>
                          <el-option v-for="item in svgIcons" :key="item" :label="item" :value="item">
                            <svg-icon style="float: left" :icon-class="item" class-name="disabled" />
                            <span style="float: right; color: #8492a6; font-size: 13px">{{ item }}</span>
                          </el-option>
                        </el-select>
                        <svg-icon v-if="updateForm.icon" :icon-class="updateForm.icon" class-name="disabled" />
                      </div>
                    </el-form-item>

                    <el-form-item label="打开方式" prop="target">
                      <el-input v-model="updateForm.target" type="text" placeholder="打开方式:_self窗口内,_blank新窗口" />
                    </el-form-item>

                    <el-form-item label="优先级" prop="priority">
                      <el-input v-model="updateForm.priority" type="text" placeholder="优先级 越小越靠前" />
                    </el-form-item>

                    <el-form-item label="状态" prop="status">
                      <el-radio-group v-model="updateForm.status">
                        <el-radio-button label="1">启用</el-radio-button>
                        <el-radio-button label="0">禁用</el-radio-button>
                      </el-radio-group>
                    </el-form-item>

                    <el-form-item label="api" prop="status">
                      <el-button type="primary" @click="handleGrant()">授权api</el-button>
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
          <el-form-item v-if="createForm.parentId" label="父级菜单" prop="parentId">{{ createForm.parentName }}</el-form-item>

          <el-form-item label="菜单编码" prop="menuCode">
            <el-input v-model="createForm.menuCode" type="text" placeholder="菜单编码" />
          </el-form-item>

          <el-form-item label="菜单名称" prop="menuName">
            <el-input v-model="createForm.menuName" type="text" placeholder="菜单名称" />
          </el-form-item>

          <el-form-item label="描述" prop="menuDesc">
            <el-input v-model="createForm.menuDesc" type="textarea" placeholder="描述" />
          </el-form-item>

          <el-form-item label="请求路径" prop="path">
            <el-input v-model="createForm.path" type="text" placeholder="请求路径" />
          </el-form-item>

          <el-form-item label="菜单图标" prop="icon">
            <div class="icon-item">
              <el-select v-model="createForm.icon" placeholder="请选择菜单图标" clearable>
                <el-option v-for="item in svgIcons" :key="item" :label="item" :value="item">
                  <svg-icon style="float: left" :icon-class="item" class-name="disabled" />
                  <span style="float: right; color: #8492a6; font-size: 13px">{{ item }}</span>
                </el-option>
              </el-select>
              <svg-icon v-if="createForm.icon" :icon-class="createForm.icon" class-name="disabled" />
            </div>
          </el-form-item>

          <!-- <el-form-item
            label="打开方式:_self窗口内,_blank新窗口"
            prop="target"
          >
            <el-input
              v-model="createForm.target"
              type="text"
              placeholder="打开方式:_self窗口内,_blank新窗口"
            />
          </el-form-item>-->

          <el-form-item label="优先级" prop="priority">
            <el-input v-model="createForm.priority" type="text" placeholder="优先级 越小越靠前" />
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

    <!--授权Demo开始-->
    <el-dialog :visible.sync="grantDialogFormVisible" :close-on-click-modal="false" title="授权api" width="800px">
      <!-- <div class="my-from-container" style="height:600px;">
        <el-tabs v-model="activeTab">
          <el-tab-pane label="菜单权限" name="menu">
            <el-input v-model="filterText" placeholder="关键字" class="my-tree-container-input" />
            <el-tree ref="permissionsTree" :data="apiTree" :expand-on-click-node="false" :filter-node-method="filterApiTree" node-key="authority" show-checkbox accordion class="my-tree-container">
              <span slot-scope="{ node, data }" class="custom-tree-node">
                <span>{{ data.apiName}}</span>
              </span>
            </el-tree>
          </el-tab-pane>
          <el-tab-pane label="接口权限" name="api">
            <div class="my-form-container-transfer">
              <el-transfer v-model="grantedApis" class="grant" :titles="['未授权', '已授权']" :props="{ key: 'authority',label: 'label'}" :data="apis" filterable />
            </div>
          </el-tab-pane>
        </el-tabs>
      </div>-->
      <div class="my-form-container">
        <el-input v-model="filterText" placeholder="关键字" class="my-tree-container-input" />
        <el-tree ref="apiTree" :data="apiTree" :expand-on-click-node="false" :filter-node-method="filterApiTree" node-key="authority" show-checkbox accordion class="my-tree-container">
          <span slot-scope="{ node, data }" class="custom-tree-node">
            <span>{{ data.apiName }}{{ '--['+data.apiCode+']' }}</span>
          </span>
        </el-tree>
      </div>
      <!-- <div class="my-form-container">
        <div class="my-form-container-transfer">
          <el-transfer v-model="grantedApis" class="grant" :titles="['未授权', '已授权']" :props="{ key: 'authority',label: 'label'}" :data="apis" filterable />
        </div>
      </div>-->

      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="grantAuth()">确定</el-button>
        <el-button @click="grantDialogFormVisible = false">取消</el-button>
      </div>
    </el-dialog>
    <!--授权Demo结束-->
  </div>
</template>

<script>

import Menu from '@/api/system/sys_menu'
import Authority from '@/api/system/sys_authority'
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
      apiTree: [],
      filterApis: [],
      grantedApis: [],
      createForm: {},
      updateForm: {},
      createDialogFormVisible: false,
      updateDialogFormVisible: false,
      grantDialogFormVisible: false,
      rules: {
        menuCode: [{ required: true, message: '不能为空', trigger: 'blur' }],
        menuName: [{ required: true, message: '不能为空', trigger: 'blur' }],
        path: [{ required: true, message: '不能为空', trigger: 'blur' }],
        priority: [{ required: true, message: '不能为空', trigger: 'blur' }],
        status: [{ required: true, message: '不能为空', trigger: 'blur' }]
      }
    }
  },
  watch: {
    filterText(val) {
      this.$refs.apiTree.filter(val)
    }
  },
  mounted() {
    this.refs = this.$refs
    this.initData()
    this.treeData()
  },
  methods: {
    initData() {
      Authority.api().then(response => {
        this.apis = response.data
        this.apis.forEach(option => {
          option.label = '/' + option.serviceId + option.path + '-' + option.apiName
        })
      })
      Authority.apiTree().then(response => {
        this.apiTree = response.data
        // this.apis.forEach(option => {
        //   option.label = '/' + option.serviceId + option.path + '-' + option.apiName
        // })
      })
    },
    treeData() {
      Menu.tree().then(response => {
        this.tree = response.data
      })
    },

    filterNode(value, data) {
      if (!value) return true
      if (data.menuName) {
        return data.menuName.indexOf(value) !== -1
      }
    },
    filterApiTree(value, data) {
      if (!value) return true
      if (data.apiName) {
        return data.apiName.indexOf(value) !== -1 || data.apiCode.indexOf(value) !== -1
      }
    },

    handleCreate(parent) {
      this.createForm = {}
      if (parent) {
        this.createForm.parentId = parent.id
        this.createForm.parentName = parent.menuName
      }
      this.createDialogFormVisible = true
      this.$nextTick(() => {
        this.refs.createForm.clearValidate()
      })
    },
    createData() {
      this.refs.createForm.validate((valid) => {
        if (valid) {
          Menu.create(this.createForm).then(ret => {
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
          Menu.update(this.updateForm).then(ret => {
            this.updateDialogFormVisible = false
            this.treeData()
          })
        }
      })
    },
    handleGrant() {
      if (!this.updateForm.id) {
        return
      }
      this.grantedApis = []
      this.grantDialogFormVisible = true
      this.$nextTick(() => {
        this.$refs.apiTree.setCheckedKeys(this.grantedApis)
      })
      Authority.menuAuthority(this.updateForm.id).then(ret => {
        ret.data.forEach(item => {
          if (item.authority.indexOf('API_') !== -1 && !this.grantedApis.includes(item.authority)) {
            this.grantedApis.push(item.authority)
          }
        })
        this.$nextTick(() => {
          this.$refs.apiTree.setCheckedKeys(this.grantedApis)
        })
      }
      )
    },
    grantAuth() {
      const authoritys = this.$refs.apiTree.getCheckedKeys()
      // authoritys = authoritys.concat(this.grantedApis)
      const data = { menuId: this.updateForm.id, authoritys: authoritys }
      Authority.grant(data).then(ret => {
        this.grantDialogFormVisible = false
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
      Menu.remove(row.id).then((ret) => {
        this.$message({
          type: 'success',
          message: ret.message
        })
        if (this.list.length === 1) {
          this.listQuery.current -= 1
        }
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

