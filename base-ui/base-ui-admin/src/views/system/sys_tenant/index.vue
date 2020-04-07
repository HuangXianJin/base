<!-- 租户信息 -->
<template>
  <div class="app-container my-app-container my-index-sys-tenant">

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

              <el-table-column label="租户编号" align="center" sortable="tenant_code">
                <template slot-scope="scope">
                  <span>{{ scope.row.tenantCode }}</span>
                </template>
              </el-table-column>

              <el-table-column label="租户名称" align="center" prop="tenant_name" sortable="custom">
                <template slot-scope="scope">
                  <span>{{ scope.row.tenantName }}</span>
                </template>
              </el-table-column>

              <el-table-column label="联系人" align="center" prop="linkman" sortable="custom">
                <template slot-scope="scope">
                  <span>{{ scope.row.linkman }}</span>
                </template>
              </el-table-column>

              <el-table-column label="手机号" align="center" prop="contact_number" sortable="custom">
                <template slot-scope="scope">
                  <span>{{ scope.row.contactNumber }}</span>
                </template>
              </el-table-column>

              <el-table-column label="联系地址" align="center" prop="address" sortable="custom">
                <template slot-scope="scope">
                  <span>{{ scope.row.address }}</span>
                </template>
              </el-table-column>

              <el-table-column label="失效日期" align="center" prop="address" sortable="custom">
                <template slot-scope="scope">
                  <span>{{ scope.row.expireTime }}</span>
                </template>
              </el-table-column>

              <el-table-column label="状态" align="center" prop="status" sortable="custom">
                <template slot-scope="scope">
                  <span v-if="scope.row.status === 1" class="text-color-main">
                    启用</span>
                  <span v-if="scope.row.status === 0" class="text-color-danger">
                    禁用
                  </span>
                </template>
              </el-table-column>

              <el-table-column label="操作" align="center" fixed="right" width="150">
                <template slot-scope="scope">
                  <el-button type="success" icon="el-icon-menu" circle alt="分配权限" title="分配权限" @click="handleAuth(scope.row)" />
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

          <el-form-item label="租户编号" prop="tenantCode">
            <el-input v-model="temp.tenantCode" type="text" placeholder="租户编号" />
          </el-form-item>

          <el-form-item label="租户名称" prop="tenantName">
            <el-input v-model="temp.tenantName" type="text" placeholder="租户名称" />
          </el-form-item>

          <el-form-item label="联系人" prop="linkman">
            <el-input v-model="temp.linkman" type="text" placeholder="联系人" />
          </el-form-item>

          <el-form-item label="手机号" prop="contactNumber">
            <el-input v-model="temp.contactNumber" type="text" placeholder="手机号" />
          </el-form-item>

          <el-form-item label="联系地址" prop="address">
            <el-input v-model="temp.address" type="textarea" placeholder="联系地址" />
          </el-form-item>

          <el-form-item label="失效日期" prop="expireTime">
            <el-date-picker v-model="temp.expireTime" type="date" value-format="yyyy-MM-dd" placeholder="请选择日期" class="my-from-item-full" />
          </el-form-item>

          <el-form-item label="状态" prop="status">
            <el-radio-group v-model="temp.status">
              <el-radio-button label="1">
                启用
              </el-radio-button>
              <el-radio-button label="0">
                禁用
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
    <el-dialog :visible.sync="updateDialogFormVisible" :close-on-click-modal="false" title="新增" width="600px">
      <div class="my-from-container">
        <el-form ref="updateForm" :rules="rules" :model="temp" label-position="right" label-width="100px">

          <el-form-item label="租户编号" prop="tenantCode">
            <el-input v-model="temp.tenantCode" type="text" placeholder="租户编号" />
          </el-form-item>

          <el-form-item label="租户名称" prop="tenantName">
            <el-input v-model="temp.tenantName" type="text" placeholder="租户名称" />
          </el-form-item>

          <el-form-item label="联系人" prop="linkman">
            <el-input v-model="temp.linkman" type="text" placeholder="联系人" />
          </el-form-item>

          <el-form-item label="手机号" prop="contactNumber">
            <el-input v-model="temp.contactNumber" type="text" placeholder="手机号" />
          </el-form-item>

          <el-form-item label="联系地址" prop="address">
            <el-input v-model="temp.address" type="textarea" placeholder="联系地址" />
          </el-form-item>

          <el-form-item label="失效日期" prop="expireTime">
            <el-date-picker v-model="temp.expireTime" type="date" value-format="yyyy-MM-dd" placeholder="请选择日期" class="my-from-item-full" />
          </el-form-item>

          <el-form-item label="状态" prop="status">
            <el-radio-group v-model="temp.status">
              <el-radio-button label="1">
                启用
              </el-radio-button>
              <el-radio-button label="0">
                禁用
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

    <!--分配权限开始-->
    <el-dialog :visible.sync="authDialogFormVisible" :close-on-click-modal="false" title="分配权限" width="800px">
      <div class="my-from-container" style="height:600px;">
        <el-tabs v-model="activeTab">
          <el-tab-pane label="菜单权限" name="menu">
            <!--菜单树Demo开始-->
            <el-input v-model="filterText" placeholder="关键字" class="my-tree-container-input" />
            <el-tree ref="menusTree" :data="menus" :expand-on-click-node="false" :filter-node-method="filterNode" node-key="authority" show-checkbox accordion class="my-tree-container">
              <span slot-scope="{ node, data }" class="custom-tree-node">
                <span>{{ data.menuName }}</span>
              </span>
            </el-tree>
            <!--菜单树Demo结束-->
          </el-tab-pane>
          <el-tab-pane label="接口权限" name="api">
            <!--授权Demo开始-->
            <div class="my-form-container-transfer">
              <el-transfer v-model="grantedApis" :titles="['未授权', '已授权']" :props="{ key: 'authority',label: 'label'}" :data="apis" filterable />
            </div>
            <!--授权Demo结束-->
          </el-tab-pane>
        </el-tabs>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="grantAuth()">
          确定
        </el-button>
        <el-button @click="authDialogFormVisible = false">
          取消
        </el-button>
      </div>
    </el-dialog>
    <!--分配权限结束-->
  </div>
</template>

<script>
import Tenant from '@/api/system/sys_tenant'
import Authority from '@/api/system/sys_authority'
import { uuid } from '@/utils'

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
      filterText: '',
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
      authDialogFormVisible: false,
      activeTab: 'menu',
      rules: {
        tenantCode: [
          {
            required: true,
            message: '不能为空',
            trigger: 'blur'
          }
        ],
        tenantName: [
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
        ],
        contactNumber: [
          {
            message: '请输入正确的手机号',
            trigger: 'change',
            pattern: /^1[3456789]\d{9}$/
          }
        ]
      },
      apis: [],
      grantedApis: [],
      menus: [],
      grantedMenus: [],
      root: { authorityId: 0, menuCode: 'root', menuName: '根菜单' }
    }
  },
  watch: {
    filterText(val) {
      this.$refs.menusTree.filter(val)
      this.$refs.apiTree.filter(val)
    }
  },
  mounted() {
    this.refs = this.$refs
    this.initData()
    this.handleFilter()
  },
  methods: {
    initData() {
      Authority.api().then(response => {
        this.apis = response.data
        this.apis.forEach(option => {
          option.label =
            '/' + option.serviceId + option.path + '-' + option.apiName
        })
      })
      Authority.menu().then(response => {
        this.menus = response.data
        this.root.children = this.menus
      })
    },
    getList() {
      Tenant.page(this.listQuery).then(response => {
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
    handleAuth(row) {
      this.temp = Object.assign({}, row)
      this.grantedApis = []
      this.grantedMenus = []
      this.authDialogFormVisible = true
      this.activeTab = 'menu'
      Authority.tenant(this.temp.tenantCode).then(ret => {
        ret.data.forEach(item => {
          if (item.authority.indexOf('API_') !== -1 && !this.grantedApis.includes(item.authority)) {
            this.grantedApis.push(item.authority)
          }
          if (item.authority.indexOf('MENU_') !== -1 && !this.grantedMenus.includes(item.authority)) {
            if (this.isLeaf(item.authority, this.root)) {
              this.grantedMenus.push(item.authority)
            }
          }
        })
        this.$refs.menusTree.setCheckedKeys(this.grantedMenus)
      }
      )
    },
    isLeaf(key, node) {
      var ret = false
      if (node.children) {
        node.children.forEach(e => {
          if (this.isLeaf(key, e)) {
            ret = true
          }
        })
      } else if (node.authority === key) {
        ret = true
      }
      return ret
    },
    filterNode(value, data) {
      if (!value) return true
      if (data.menuName) {
        return data.menuName.indexOf(value) !== -1
      }
      if (data.label) {
        return data.label.indexOf(value) !== -1
      }
    },
    grantAuth() {
      this.grantedMenus = this.$refs.menusTree.getCheckedKeys().concat(this.$refs.menusTree.getHalfCheckedKeys())
      let authoritys = []
      authoritys = authoritys.concat(this.grantedApis)
      authoritys = authoritys.concat(this.grantedMenus)
      const data = {
        tenantCode: this.temp.tenantCode,
        authoritys: authoritys
      }
      Authority.grant(data).then(ret => {
        this.authDialogFormVisible = false
      })
    },
    handleCreate() {
      this.temp = { tenantCode: '' }
      this.temp.tenantCode = uuid().toString()
      this.temp.status = 1
      this.createDialogFormVisible = true
      this.$nextTick(() => {
        this.refs.createForm.clearValidate()
      })
    },
    createData() {
      this.refs.createForm.validate(valid => {
        if (valid) {
          Tenant.create(this.temp).then(ret => {
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
      this.refs.updateForm.validate(valid => {
        if (valid) {
          Tenant.update(this.temp).then(ret => {
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
      Tenant.remove(row.id).then(ret => {
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
