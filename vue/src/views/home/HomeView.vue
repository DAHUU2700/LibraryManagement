<template>
  <div>
    <div style="margin:30px 0px 30px 20px ">
      <!--  从后台加载最新的数据 -->
      <el-select class="input" v-model="timeRange" placeholder="请选择" @change="load">
        <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value">
        </el-option>
      </el-select>
    </div>
    <el-card>
      <div id="line" style="width: 100%; height: 400px"></div>
    </el-card>
  </div>
</template>

<script>
import Cookies from 'js-cookie'
import request from "@/utils/request";
import * as echarts from 'echarts'

const option = {
  title: {
    text: '图书借还统计'
  },
  tooltip: {
    trigger: 'axis'
  },
  legend: {
    data: ['借书数量', '还书数量']
  },
  grid: {
    left: '3%',
    right: '4%',
    bottom: '3%',
    containLabel: true
  },
  toolbox: {
    feature: {
      saveAsImage: {}
    }
  },
  xAxis: {
    type: 'category',
    boundaryGap: false,
    data: []  // 从后台动态获取
  },
  yAxis: {
    type: 'value'
  },
  series: [
    {
      name: '借书数量',
      type: 'line',
      stack: 'Total',
      smooth: true,
      data: []     // 从后台动态获取
    },
    {
      name: '还书数量',
      type: 'line',
      stack: 'Total',
      smooth: true,
      data: []     // 从后台动态获取
    }
  ]
}

export default {
  data() {
    return {
      admin: Cookies.get('admin') ? JSON.parse(Cookies.get('admin')) : {},
      lineBox: null,
      timeRange: 'week',
      options: [
        {label: '最近一周', value: 'week'},
        {label: '最近一个月', value: 'month'},
        {label: '最近两个月', value: 'month2'},
        {label: '最近三个月', value: 'month3'},
      ]
    }
  },
  mounted() {  // 等页面元素全部初始化好
    this.load()
  },
  methods: {
    load() {
      if (!this.lineBox) {
        this.lineBox = echarts.init(document.getElementById('line'))  // 初始化echarts容器
      }
      // 从后台获取数据
      request.get('/restore/lineCharts/' + this.timeRange).then(res => {
        option.xAxis.data = res.data.date
        option.series[0].data = res.data.borrow
        option.series[1].data = res.data.retur
        // 设置容器的属性值，当你的数据发生变化的时候，一定要重新setOption
        this.lineBox.setOption(option)
      })
    }
  }
}
</script>

<style>
.input {
  width: 300px;
}
</style>
