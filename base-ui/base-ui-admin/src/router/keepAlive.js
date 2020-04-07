
/**
 * 要缓存的组件，路由和组件都需要填加name属性，并且要相同
 * 从 'KeepAliveDemo' 跳转到 'DatePickerDemo'会被缓存，其他情况不缓存
 */
export var keep = [
  {
    name: 'QuestionnaireRentalHouse',
    to: ['Renter']
  },
  {
    name: 'OwnerInfo',
    to: ['QuestionnaireRentalHouse']
  }
]
