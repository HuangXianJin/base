/* Layout */
import Layout from '@/layout'

export var sysRouterMap = [
  {
    path: '/system',
    component: Layout,
    name: 'System',
    redirect: 'noredirect',
    meta: {
      title: '系统管理',
      icon: 'nested',
      code: '/admin/system'
    },
    children: [
      {
        path: 'api',
        name: 'Api',
        component: () => import('@/views/system/sys_api'),
        meta: {
          title: '接口管理',
          icon: '',
          code: '/admin/system/api'
        }
      },
      {
        path: 'menu',
        name: 'Menu',
        component: () => import('@/views/system/sys_menu'),
        meta: {
          title: '菜单管理',
          icon: '',
          code: '/admin/system/menu'
        }
      },
      {
        path: 'user',
        name: 'User',
        component: () => import('@/views/system/sys_user'),
        meta: {
          title: '用户管理',
          icon: '',
          code: '/admin/system/user'
        }
      },
      {
        path: 'role',
        name: 'Role',
        component: () => import('@/views/system/sys_role'),
        meta: {
          title: '角色管理',
          icon: '',
          code: '/admin/system/role'
        }
      },
      {
        path: 'tenant',
        name: 'Tenant',
        component: () => import('@/views/system/sys_tenant'),
        meta: {
          title: '租户管理',
          icon: '',
          code: '/admin/system/tenant'
        }
      },
      {
        path: 'app',
        name: 'App',
        component: () => import('@/views/system/sys_app'),
        meta: {
          title: '客户端管理',
          icon: '',
          code: '/admin/system/app'
        }
      },
      {
        path: 'dictionary',
        name: 'Dictionary',
        component: () => import('@/views/system/sys_dictionary'),
        meta: {
          title: '数据字典',
          icon: '',
          code: '/admin/system/dictionary'
        }
      },
      {
        path: 'sysFile',
        name: 'SysFile',
        component: () => import('@/views/system/sys_file/index'),
        meta: {
          title: '文件管理',
          icon: ''
        }
      },
      {
        path: 'log',
        name: 'Log',
        component: () => import('@/views/system/sys_log/index'),
        meta: {
          title: '日志管理',
          icon: ''
        }
      },
      {
        path: 'department',
        name: 'Department',
        component: () => import('@/views/system/sys_department'),
        meta: {
          title: '组织机构管理',
          icon: '',
          code: '/admin/system/department'
        }
      },
      {
        path: 'departmentPermission',
        name: 'DepartmentPermission',
        component: () => import('@/views/system/sys_department_permission'),
        meta: {
          title: '组织机构权限管理',
          icon: '',
          code: '/admin/system/departmentPermission'
        }
      }
      // {
      //   path: 'parentAccount',
      //   name: 'ParentAccount',
      //   component: () => import('@/views/system/parent_account/index'),
      //   meta: {
      //     title: '家长账户',
      //     icon: '',
      //     perms: ['pc_system_parentAccount,system_parentAccount']
      //   }
      // },

      // {
      //   path: 'dictionary',
      //   name: 'Dictionary',
      //   component: () => import('@/views/system/dictionary/index'),
      //   meta: {
      //     title: '数据字典',
      //     icon: '',
      //     perms: ['pc_system_dictionary,system_dictionary']
      //   }
      // }
    ]
  }
]
