<template>
  <div class="app-container  my-app-container my-index-system-log">

    <!--条件Demo开始-->
    <div class="my-filter-container my-filter-container-padding-right">
      <div class="my-filter-list">

        <span class="my-filter-item my-filter-item-big">
          <date-picker v-model="dateValue" format="yyyy-MM-dd" clearable @pick="handleFilter" />
        </span>

        <span class="my-filter-item my-filter-item-search">
          <span class="search-input">
            <el-input v-model="listQuery.searchStr" placeholder="日志类型 / 日志内容" clearable @change="handleFilter" @keyup.enter.native="handleFilter" />
          </span>
          <span class="search-btn">
            <el-button type="primary" icon="el-icon-search" @click="handleFilter">
              搜索
            </el-button>
          </span>
        </span>

      </div>

      <div class="my-filter-btn">
        <el-button type="primary" icon="iconfont my-icon-floor" @click="handleClean">
          清理日志
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
            <el-table :key="tableKey" v-loading="listLoading" :data="list" :default-sort="{prop: 'operate_time', order: 'descending'}" @sort-change="sortChange">
              <el-table-column label="序号" prop="id" fixed align="center" width="60">
                <template slot-scope="scope">
                  <span>{{ scope.$index+(listQuery.current - 1) * listQuery.size + 1 }}</span>
                </template>
              </el-table-column>

              <el-table-column label="日志类型" align="left" width="160" prop="module" sortable="custom">
                <template slot-scope="scope">
                  <span>{{ scope.row.module }}</span>
                </template>
              </el-table-column>

              <el-table-column label="日志内容" align="left" width="160" prop="operation" sortable="custom">
                <template slot-scope="scope">
                  <span>{{ scope.row.operation }}</span>
                </template>
              </el-table-column>

              <el-table-column label="操作账号" align="center" width="100" prop="operator" sortable="custom">
                <template slot-scope="scope">
                  <span>{{ scope.row.operator }}</span>
                </template>
              </el-table-column>

              <el-table-column label="操作时间" align="center" width="160" prop="operate_time" sortable="custom">
                <template slot-scope="scope">
                  <span>{{ scope.row.operateTime }}</span>
                </template>
              </el-table-column>

              <el-table-column label="请求方法" align="left" width="">
                <template slot-scope="scope">
                  <span>{{ scope.row.classMethod }}</span>
                </template>
              </el-table-column>

              <el-table-column label="请求内容" align="left" width="">
                <template slot-scope="scope">
                  <span>{{ scope.row.args }}</span>
                </template>
              </el-table-column>

              <el-table-column label="请求结果" align="left" width="">
                <template slot-scope="scope">
                  <span>{{ scope.row.response }}</span>
                </template>
              </el-table-column>

            </el-table>
          </div>
          <pagination v-show="total>0" :total="total" :page.sync="listQuery.current" :limit.sync="listQuery.size" @pagination="getList" />
          <!--表格Demo结束-->
        </div>
      </el-scrollbar>
    </div>
    <!--主体Demo结束-->

    <!--新增编辑Demo开始-->
    <el-dialog :close-on-click-modal="false" :visible.sync="dialogFormVisible" title="清理日志信息" width="600px">
      <div class="my-from-container">
        <el-form ref="createForm" :model="temp" :rules="rules" label-position="right" label-width="200px">
          <el-form-item label="清理指定日期之前日志：" prop="operateTime">
            <el-date-picker v-model="temp.operateTime" type="date" value-format="yyyy-MM-dd" placeholder="选择日期" class="my-from-item-full" clearable />
          </el-form-item>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="cleanLog()">确定</el-button>
        <el-button @click="dialogFormVisible = false">取消</el-button>
      </div>
    </el-dialog>
    <!--新增编辑Demo结束-->

  </div>
</template>

<script>
import SysLog from '@/api/system/sys_log'
import Pagination from '@/components/Pagination'
import DatePicker from '@/components/DatePicker'
export default {
  components: {
    Pagination,
    DatePicker
  },
  data() {
    return {
      tableKey: 0,
      list: null,
      total: 0,
      listLoading: false,
      listQuery: {
        current: 1,
        size: 10,
        operation: undefined,
        branchId: undefined,
        ascs: undefined,
        descs: undefined
      },
      temp: {},
      dialogFormVisible: false,
      rules: {
        operateTime: [{
          required: true,
          message: '不能为空',
          trigger: 'change'
        }]
      },
      dateValue: [],
      downloadLoading: false
    }
  },
  created() {
    this.initData()
    this.getList()
  },
  methods: {
    initData() {},
    getList() {
      this.listLoading = true
      this.listQuery.startDate = this.dateValue[0]
      this.listQuery.endDate = this.dateValue[1]
      SysLog.queryLog(this.listQuery).then(response => {
        this.list = response.data.records
        this.total = response.data.total
        this.listLoading = false
      })
      this.listLoading = false
    },
    sortChange(data) {
      const {
        prop,
        order
      } = data
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
    handleClean() {
      this.temp = {}
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['createForm'].clearValidate()
      })
    },
    cleanLog() {
      this.$refs['createForm'].validate((valid) => {
        if (valid) {
          SysLog.cleanLog(this.temp).then(ret => {
            this.dialogFormVisible = false
            this.$message({
              message: ret.message,
              type: 'success'
            })
            this.getList()
          })
        }
      })
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
</style>
