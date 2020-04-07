<template>
  <div class="navbar">
    <hamburger :is-active="sidebar.opened" class="hamburger-container" @toggleClick="toggleSideBar" />

    <breadcrumb class="breadcrumb-container" />

    <div class="right-menu">

      <!-- <span class="my-header-menu" @click="fullScreen">可视化平台</span> -->
      <el-dropdown class="avatar-container" trigger="click">
        <div class="avatar-wrapper">
          <!-- <img :src="avatar+'?imageView2/1/w/80/h/80'" class="user-avatar"> -->
          <el-button type="text">
            {{name}}<i class="el-icon-arrow-down el-icon--right"></i>
          </el-button>
          <!-- <i class="el-icon-caret-bottom" /> -->

        </div>
        <el-dropdown-menu slot="dropdown" class="user-dropdown">
          <router-link to="/">
            <el-dropdown-item>
              主页
            </el-dropdown-item>
          </router-link>
          <el-dropdown-item divided>
            <span style="display:block;" @click="handleModifyPassword()">修改密码</span>
          </el-dropdown-item>
          <!-- <a target="_blank" href="https://github.com/PanJiaChen/vue-admin-template/">
            <el-dropdown-item>Github</el-dropdown-item>
          </a>
          <a target="_blank" href="https://panjiachen.github.io/vue-element-admin-site/#/">
            <el-dropdown-item>Docs</el-dropdown-item>
          </a> -->
          <el-dropdown-item divided>
            <span style="display:block;" @click="logout">退出账号</span>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>

    </div>
    <div>
      <el-dialog :visible.sync="modifyPasswordDialogFormVisible" :close-on-click-modal="false" title="修改密码" width="600px">
      <div class="my-from-container">
        <el-form ref="modifyPasswordForm" :rules="rules" :model="temp" label-position="right" label-width="80px">
          <el-form-item label="密码" prop="password">
            <el-input v-model="temp.password" type="text" placeholder="密码" @input="inputChange(temp.password)"/>
            <span style="color:red" v-if="isShow">密码长度不能小于6</span>
          </el-form-item>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="modifyPassword()">确定</el-button>
        <el-button @click="modifyPasswordDialogFormVisible = false">取消</el-button>
      </div>
    </el-dialog>

    </div>

  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import Breadcrumb from '@/components/Breadcrumb'
import Hamburger from '@/components/Hamburger'
import User from '@/api/system/sys_user'

export default {
  components: {
    Breadcrumb,
    Hamburger
  },
  data(){
    return{
      refs: undefined,
      modifyPasswordDialogFormVisible:false,
      temp:{},
      isShow:false,
      rules:{
        password: [
          {
            required: true,
            message: '不能为空',
            trigger: 'blur'
          }
        ],
      }
    }
  },
  computed: {
    ...mapGetters([
      'sidebar',
      'avatar',
      'name',
      'userId'
    ])
  },
  mounted(){
    this.refs = this.$refs
  },
  methods: {
    toggleSideBar() {
      this.$store.dispatch('app/toggleSideBar')
    },
    async logout() {
      await this.$store.dispatch('user/logout')
      this.$router.push(`/login?redirect=${this.$route.fullPath}`)
    },

    fullScreen() {
      this.$router.push('/fullScreen')
    },
    handleModifyPassword() {
      this.temp = { id: this.userId }
      this.modifyPasswordDialogFormVisible = true
      this.$nextTick(() => {
        this.refs.modifyPasswordForm.clearValidate()
      })
    },
    inputChange(str){
      if(str.length>=6){
        this.isShow = false
      }else if(str.length>=1 && str.length<6){
        this.isShow = true
      }else{
        this.isShow = false
      }
    },
    modifyPassword() {
      this.refs.modifyPasswordForm.validate((valid) => {
        if (valid) {
          User.modifyPassword(this.temp).then(async ret => {
            this.modifyPasswordDialogFormVisible = false
            this.$message({ message: ret.message, type: 'success' })
            if(ret.code===200 && ret.success===true){
              await this.$store.dispatch('user/logout')
            this.$router.push(`/login?redirect=${this.$route.fullPath}`)
            }
          })
        }
      })
    },

  }
}
</script>

<style lang="scss" scoped>
.navbar {
  height: 50px;
  overflow: hidden;
  position: relative;
  background: #fff;
  box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);

  .hamburger-container {
    line-height: 46px;
    height: 100%;
    float: left;
    cursor: pointer;
    transition: background 0.3s;
    -webkit-tap-highlight-color: transparent;

    &:hover {
      background: rgba(0, 0, 0, 0.025);
    }
  }

  .breadcrumb-container {
    float: left;
  }

  .right-menu {
    float: right;
    height: 100%;
    line-height: 50px;

    &:focus {
      outline: none;
    }

    .right-menu-item {
      display: inline-block;
      padding: 0 8px;
      height: 100%;
      font-size: 18px;
      color: #5a5e66;
      vertical-align: text-bottom;

      &.hover-effect {
        cursor: pointer;
        transition: background 0.3s;

        &:hover {
          background: rgba(0, 0, 0, 0.025);
        }
      }
    }

    .avatar-container {
      margin-right: 30px;

      .avatar-wrapper {
        margin-top: 5px;
        position: relative;

        .user-avatar {
          cursor: pointer;
          width: 40px;
          height: 40px;
          border-radius: 10px;
        }

        .el-icon-caret-bottom {
          cursor: pointer;
          position: absolute;
          right: -20px;
          top: 25px;
          font-size: 12px;
        }
      }
    }
  }
}
</style>

<style scoped>
.my-header-menu {
  vertical-align: top;
  display: inline-block;
  font-size: 14px;
  font-weight: bold;
  margin-right: 10px;
  text-decoration: underline;
  color: #409eff;
  cursor: pointer;
}
</style>
