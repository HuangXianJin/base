import request from '@/utils/request'

/**
 * 全屏接口 api
 *
 * @author XXX
 * @date 2020-02-19
 */

/**
 * 获取镇街数据
 */
const streetSummary = (params) => {
  return request({
    url: '/questionnaire/questionnaireFamily/familyStatistics',
    method: 'get',
    params
  })
}

/**
 * 获取公司数据
 */
const companySummary = (params) => {
  return request({
    url: '/company/companyInfo/companyStatistics',
    method: 'get',
    params
  })
}

export default {
  streetSummary,
  companySummary
}
