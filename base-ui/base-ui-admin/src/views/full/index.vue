<template>
  <div class="my-full-screen">
    <div class="my-full-screen-topic">韶关市武江区疫情登记可视化平台</div>

    <!--start-->
    <div class="my-full-screen-one">
      <div class="my-flex">
        <div class="my-flex-item">
          <div class="my-type-one">
            <div class="my-title">复工登记企业数量</div>
            <div class="my-name">
              <span class="my-name-mess">
                <template v-if="companySumUnit != ''">{{ companySumUnit }}</template>
                <template v-else>-</template>
              </span>家
            </div>
          </div>
        </div>
        <div class="my-flex-item">
          <div class="my-type-one">
            <div class="my-title">复工员工登记人数</div>
            <div class="my-name">
              <span class="my-name-mess">
                <template v-if="companySumPerson != ''">{{ companySumPerson }}</template>
                <template v-else>-</template>
              </span>人
            </div>
          </div>
        </div>
        <div class="my-flex-item">
          <div class="my-type-one">
            <div class="my-title">员工曾逗留疫区人数</div>
            <div class="my-name">
              <span class="my-name-mess">
                <template v-if="companySumStay != ''">{{ companySumStay }}</template>
                <template v-else>-</template>
              </span>人
            </div>
          </div>
        </div>
        <div class="my-flex-item">
          <div class="my-type-one">
            <div class="my-title">员工发热人数</div>
            <div class="my-name">
              <span class="my-name-mess">
                <template v-if="companySumFever != ''">{{ companySumFever }}</template>
                <template v-else>-</template>
              </span>人
            </div>
          </div>
        </div>
      </div>
      <div class="my-flex">
        <div class="my-flex-item">
          <div class="my-type-one">
            <div class="my-title">镇街排查户数</div>
            <div class="my-name">
              <span class="my-name-mess">
                <template v-if="streetSumUnit != ''">{{ streetSumUnit }}</template>
                <template v-else>-</template>
              </span>户
            </div>
          </div>
        </div>
        <div class="my-flex-item">
          <div class="my-type-one">
            <div class="my-title">镇街排查人数</div>
            <div class="my-name">
              <span class="my-name-mess">
                <template v-if="streetSumPerson != ''">{{ streetSumPerson }}</template>
                <template v-else>-</template>
              </span>人
            </div>
          </div>
        </div>
        <div class="my-flex-item">
          <div class="my-type-one">
            <div class="my-title">镇街曾逗留疫区人数</div>
            <div class="my-name">
              <span class="my-name-mess">
                <template v-if="streetSumStay != ''">{{ streetSumStay }}</template>
                <template v-else>-</template>
              </span>人
            </div>
          </div>
        </div>
        <div class="my-flex-item">
          <div class="my-type-one">
            <div class="my-title">镇街发热人数</div>
            <div class="my-name">
              <span class="my-name-mess">
                <template v-if="streetSumFever != ''">{{ streetSumFever }}</template>
                <template v-else>-</template>
              </span>人
            </div>
          </div>
        </div>
      </div>
    </div>
    <!--end-->

    <!--start-->
    <div class="my-full-screen-two">
      <div class="my-flex">
        <div v-for="(item,index) in streetList" :key="index" class="my-flex-item">
          <div class="my-type-two">
            <div class="my-title">{{ item.street }}</div>
            <div class="my-name">
              <div class="my-name-title">排查户数（户）</div>
              <div class="my-name-desc">{{ item.householdNumber }}</div>
            </div>
            <div class="my-name">
              <div class="my-name-title">排查人数（人）</div>
              <div class="my-name-desc">{{ item.countPerson }}</div>
            </div>
            <div class="my-name">
              <div class="my-name-title">曾逗留疫区人数（人）</div>
              <div class="my-name-desc">{{ item.hubei + item.wenzhou + item.infectedArea }}</div>
            </div>
            <div class="my-name">
              <div class="my-name-title">发热人数（人）</div>
              <div class="my-name-desc">{{ item.symptom }}</div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!--end-->
  </div>
</template>

<script>
import FullScreen from '@/api/full/full'

export default {
  components: {},
  data() {
    return {
      companySumUnit: '',
      companySumPerson: '',
      companySumStay: '',
      companySumFever: '',

      streetSumUnit: '',
      streetSumPerson: '',
      streetSumStay: '',
      streetSumFever: '',

      streetList: ''
    }
  },
  created() {
    this.getStreetData()
    this.getCompanyData()
  },
  mounted() {
    if (this.timer) {
      clearInterval(this.timer)
    } else {
      this.timer = setInterval(() => {
        this.getStreetData()
        this.getCompanyData()
      }, 60000)
    }
  },
  destroyed() {
    clearInterval(this.timer)
  },
  methods: {
    getStreetData() {
      FullScreen.streetSummary().then(response => {
        console.log('------------------------')
        console.log('镇街列表数据如下：')
        console.log(response.data)
        this.streetList = response.data
        var myData = response.data
        if (myData != '') {
          let sum1 = 0
          myData.forEach((item) => {
            sum1 += item.symptom
          })
          console.log('发热总人数是：' + sum1)
          this.streetSumFever = sum1

          let sum2 = 0
          myData.forEach((item) => {
            sum2 += item.householdNumber
          })
          console.log('镇街数量是：' + sum2)
          this.streetSumUnit = sum2

          let sum3 = 0
          myData.forEach((item) => {
            sum3 += item.countPerson
          })
          console.log('人数数量是：' + sum3)
          this.streetSumPerson = sum3

          let sum4 = 0
          myData.forEach((item) => {
            sum4 += item.hubei
          })
          console.log('逗留湖北是：' + sum4)

          let sum5 = 0
          myData.forEach((item) => {
            sum5 += item.wenzhou
          })
          console.log('逗留温州是：' + sum5)

          let sum6 = 0
          myData.forEach((item) => {
            sum6 += item.infectedArea
          })
          console.log('逗留其他疫区域是：' + sum6)

          this.streetSumStay = sum4 + sum5 + sum6
        }
      })
    },
    getCompanyData() {
      FullScreen.companySummary().then(response => {
        console.log('------------------------')
        console.log('公司列表数据如下：')
        console.log(response.data)
        var myData = response.data
        if (myData != '') {
          let sum1 = 0
          myData.forEach((item) => {
            sum1 += item.symptom
          })
          console.log('发热总人数是：' + sum1)
          this.companySumFever = sum1

          let sum2 = 0
          myData.forEach((item) => {
            sum2 += item.companyNumber
          })
          console.log('企业数量是：' + sum2)
          this.companySumUnit = sum2

          let sum3 = 0
          myData.forEach((item) => {
            sum3 += item.countPerson
          })
          console.log('人数数量是：' + sum3)
          this.companySumPerson = sum3

          let sum4 = 0
          myData.forEach((item) => {
            sum4 += item.hubei
          })
          console.log('逗留湖北是：' + sum4)

          let sum5 = 0
          myData.forEach((item) => {
            sum5 += item.wenzhou
          })
          console.log('逗留温州是：' + sum5)

          let sum6 = 0
          myData.forEach((item) => {
            sum6 += item.infectedArea
          })
          console.log('逗留其他疫区域是：' + sum6)

          this.companySumStay = sum4 + sum5 + sum6
        }
      })
    }
  }
}
</script>

<style>
.my-flex {
  display: -webkit-box;
  display: -webkit-flex;
  display: flex;
}
.my-flex-item {
  -webkit-box-flex: 1;
  -webkit-flex: 1;
  flex: 1;
}
.my-full-screen-topic {
  height: 100px;
  font-size: 40px;
  line-height: 100px;
  color: #b5f0fc;
  text-align: center;
}

.my-full-screen {
  width: 100%;
  height: 100%;
  background: url("/static/images/my-full-bg.jpg") no-repeat;
  background-size: cover;
  background-attachment: fixed;
}
.my-type-one {
  text-align: center;
  border: 1px solid #416caa;
  background: #15356d;
  margin: 20px 30px;
  padding: 20px 10px;
  border-radius: 4px;
}
.my-type-one .my-title {
  font-size: 20px;
  color: #4adefe;
  margin-bottom: 10px;
}
.my-type-one .my-name {
  font-size: 18px;
  color: #4adefe;
}
.my-type-one .my-name-mess {
  font-size: 32px;
  color: #f3db5c;
  margin-right: 5px;
  font-weight: bold;
}
.my-full-screen-two {
  text-align: center;
  border: 1px solid #416caa;
  background: #15356d;
  margin: 20px 30px 0 30px;
  padding: 20px 10px;
  border-radius: 4px;
}
.my-full-screen-two .my-flex-item {
  border-right: 1px solid #416caa;
}
.my-full-screen-two .my-flex-item:last-child {
  border-right: none;
}
.my-type-two {
  padding: 20px;
}
.my-type-two .my-title {
  font-size: 22px;
  margin: 10px 0 30px 0;
  color: #fff;
  text-align: left;
}
.my-type-two .my-name {
  font-size: 14px;
  color: #4adefe;
  margin-bottom: 20px;
}

.my-type-two .my-name-title {
  font-size: 16px;
  margin-bottom: 10px;
  text-align: left;
}
.my-type-two .my-name-desc {
  font-size: 20px;
  color: #f3db5c;
  text-align: right;
}
</style>
