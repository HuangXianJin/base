const getters = {
  sidebar: state => state.app.sidebar,
  device: state => state.app.device,
  token: state => state.user.token,
  avatar: state => state.user.avatar,
  name: state => state.user.name,
  userId: state => state.user.userId,
  permission_routes: state => state.permission.routes,
  keep: state => state.app.keep,
  noKeep: state => state.app.noKeep
}
export default getters
