import ajax from '@/utils/ajax'
import { getToken } from '@/utils/auth'
import { Message } from 'element-ui'
export default function upload(data) {
  console.log('upload(data)', data)
  var file = data.data.file
  delete data.data.file
  var fileName = file.name
  var url = process.env.VUE_APP_BASE_API + data.url
  console.log('upload url', url)
  var token = getToken()

  return new Promise((resolve, reject) => {
    const options = {
      headers: { Authorization: 'Bearer ' + token },
      withCredentials: false,
      data: data.data,
      file: file,
      filename: 'file',
      action: url,
      onProgress: e => {
      },
      onSuccess: res => {
        if (res.success) {
          resolve(res)
        } else {
          Message({
            message: res.message,
            type: 'error',
            duration: 5 * 1000
          })
        }
      },
      onError: e => {
        reject(e)
      }
    }
    var regex = /(.jpg|.jpeg|.png|.bmp)$/
    if (regex && regex.test(fileName.toLowerCase())) {
      lrz(file, { width: 1080 }).then(function (rst) {// eslint-disable-line
        rst.file.name = file.name
        options.file = rst.file
        ajax(options)
      }).catch(function(err) {
        console.log(err)
        ajax(options)
      }).always(function() {
        // 不管是成功失败，都会执行
      })
    } else {
      ajax(options)
    }
  })
}
