<template>
  <div>
    商品销量前十统计图
    <div id="myChart" :style="{width: '1200px', height: '600px'}"></div>
  </div>
</template>
<script>
import axios from 'axios'
import echarts from 'echarts'
export default {
   data() {
    return {
      dataName:[],
      dataCount:[],
      dataList:[]
    }
  },
  methods: {
      drawLine () {
        var echarts = require('echarts');
        var myChart = echarts.init(document.getElementById('myChart'));
        var option = option = {
            tooltip: {
                trigger: 'axis',
                axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                    type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            grid: {
                left: '10%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis: [
                {
                    type: 'category',
                    data: this.dataName,
                    axisTick: {
                        alignWithLabel: true
                    },
                    axisLabel: {
                      rotate: 10,
                      textStyle: {
                          color:'blue'
                      }
                    }
                }
            ],
            yAxis: [
                {
                    type: 'value'
                }
            ],
            series: [
                {
                    name: '商品销量',
                    type: 'bar',
                    barWidth: '60%',
                    data: this.dataList
                }
            ]
          };
        myChart.setOption(option);
      }
      
  },
  mounted(){
     axios.get("/api/ssm/orders/getOrderGoodData").then(res=>{
           for(let i in res.data){
             this.dataName.push(res.data[i].gname);
             this.dataList.push(res.data[i].num);
           }
             this.drawLine();
          })
   
  },
}
</script>
<style scoped>
 #myChart{
   margin-top: 20px;
 }
</style>