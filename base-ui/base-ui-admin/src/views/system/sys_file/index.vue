<template>
  <div class="app-container  my-app-container my-index-sys-sysFile">

    <!--条件Demo开始-->
    <div class="my-filter-container">
      <div class="my-filter-list">

        <span class="my-filter-item" style="width:300px;">
          <date-picker v-model="dateValue" format="yyyy-MM-dd" clearable />
        </span>

        <span class="my-filter-item my-filter-item-search">
          <span class="search-input">
            <el-input v-model="listQuery.originName" clearable placeholder="文件名称" @keyup.enter.native="handleFilter" @clear="handleFilter" @change="handleFilter" />
          </span>
          <span class="search-btn">
            <el-button type="primary" icon="el-icon-search" @click="handleFilter">
              搜索
            </el-button>
          </span>
        </span>

      </div>

      <div class="my-filter-btn">
        <el-button type="primary" @click="clear">
          一键清理
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

            <el-table :key="tableKey" v-loading="listLoading" :data="list" stripe fit highlight-current-row :default-sort="{prop: 'operate_time', order: 'descending'}" @sort-change="sortChange">

              <el-table-column label="序号" prop="id" fixed align="center" width="60">
                <template slot-scope="scope">
                  <span>{{ scope.$index + (listQuery.current - 1) * listQuery.size + 1 }}</span>
                </template>
              </el-table-column>

              <el-table-column label="文件名称" align="center" sortable="custom" prop="origin_name">
                <template slot-scope="scope">
                  <span>{{ scope.row.originName }}</span>
                </template>
              </el-table-column>

              <el-table-column label="文件类型" align="center" prop="file_size" sortable="custom" width="120">
                <template slot-scope="scope">
                  <span v-if="dictionaryMap.file_type">{{ dictionaryMap.file_type[scope.row.fileType] }}</span>
                </template>
              </el-table-column>

              <el-table-column label="大小(KB)" align="center" prop="file_size" sortable="custom" width="120">
                <template slot-scope="scope">
                  <span>{{ scope.row.fileSize }}</span>
                </template>
              </el-table-column>

              <el-table-column label="上传时间" align="center" prop="operate_time" sortable="custom" width="150">
                <template slot-scope="scope">
                  <span>{{ scope.row.operateTime }}</span>
                </template>
              </el-table-column>

              <el-table-column label="存储路径" align="center" prop="url" sortable="custom">
                <template slot-scope="scope">
                  <span>
                    <a :href="scope.row.url" target="view_window">
                      {{ scope.row.sysUrl }}
                    </a>
                  </span>
                </template>
              </el-table-column>

              <el-table-column label="文件描述" align="center" prop="description" sortable="custom" width="150">
                <template slot-scope="scope">
                  <span>{{ scope.row.description }}</span>
                </template>
              </el-table-column>

              <el-table-column label="操作" align="center" fixed="right" width="50">
                <template slot-scope="scope">
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

  </div>
</template>

<script>
import SysFile from '@/api/system/sys_file'

import SysCommon from '@/api/system/sys_common'

import {
  parseTime,
  transToMap,
  getLastMonthDate
} from '@/utils'
import Pagination from '@/components/Pagination'
import DatePicker from '@/components/DatePicker/index'
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
        originName: undefined,
        ascs: undefined,
        descs: undefined
      },
      dictionaryCode: {
        FILE_TYPE: 'FILE_TYPE'
      },
      dictionaryMap: { file_type: undefined },
      dictionarys: { file_type: undefined },
      downloadLoading: false,
      dateValue: []
    }
  },
  watch: {
    dateValue() {
      this.listQuery.startDate = this.dateValue[0] ? this.formatTime(this.dateValue[0], '{y}-{m}-{d}') : undefined
      this.listQuery.endDate = this.dateValue[1] ? this.formatTime(this.dateValue[1], '{y}-{m}-{d}') : undefined
      this.getList()
    }
  },
  created() {
    SysCommon.getDictionaryByCode({ code: this.dictionaryCode.FILE_TYPE }).then(response => {
      this.dictionarys.file_type = response.data
      this.dictionaryMap.file_type = transToMap(this.dictionarys.file_type, 'itemValue', 'itemName')
    })
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      this.listQuery.startDate = this.dateValue[0]
      this.listQuery.endDate = this.dateValue[1]
      SysFile.page(this.listQuery).then(response => {
        this.list = response.data.records
        this.total = response.data.total
        this.listLoading = false
      })
      this.listLoading = false
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
      SysFile.remove(row.id).then((ret) => {
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
    lastMonthDate(time) {
      return getLastMonthDate(time)
    },
    clear() {
      if (!(this.listQuery.startDate && this.listQuery.endDate)) {
        return
      }
      SysFile.deleteByDate(this.listQuery).then((ret) => {
        this.$message({
          type: 'success',
          message: ret.message
        })
        this.getList()
      })
    },
    formatTime(time, cFormat) {
      return parseTime(time, cFormat)
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
</style>

<style>
</style>
