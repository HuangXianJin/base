/* Layout */
import Layout from '@/layout'

export var basicMap = [
  {
    path: '/basic',
    component: Layout,
    name: 'Basic',
    redirect: 'noredirect',
    meta: {
      title: '基础信息',
      icon: '',
      code: '/admin/basic'
    },
    children: [
      {
        path: 'basicReporter',
        name: 'BasicReporter',
        component: () => import('@/views/basic/basic_reporter'),
        meta: {
          title: '登填人信息',
          icon: '',
          code: ''
        }
      }
    ]
  }
]
