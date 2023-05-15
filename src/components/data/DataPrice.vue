<template>
  <div>
      <div v-if="viewShow" id="myChart" :style="{width: '1200px', height: '600px'}"></div>
      <div v-else>
          暂时没有数据.......
      </div>

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
      dataBottom:[],
      dataColor:[],
      dataList:[],
      dataItem:[],
      colorSum:[],
      viewShow:true,
    }
  },
  methods: {
      drawLine () {
        var echarts = require('echarts');
        var myChart = echarts.init(document.getElementById('myChart'));
       var option =  {
    color: ['#80FFA5', '#00DDFF', '#37A2FF', '#FF0087', '#FFBF00'],
    title: {
        text: '商品销量图'
    },
    tooltip: {
        trigger: 'axis',
        axisPointer: {
            type: 'cross',
            label: {
                backgroundColor: '#6a7985'
            }
        }
    },
    legend: {
        data: this.dataName
    },
    toolbox: {
        feature: {
            saveAsImage: {}
        }
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    xAxis: [
        {
            type: 'category',
            boundaryGap: false,
           data: this.dataBottom
        } 
    ],
    yAxis: [
        {
            type: 'value'
        }
    ],
    series: [ {
            name: this.dataName[0],
            type: 'line',
            stack: '总量',
            smooth: true,
            lineStyle: {
                width: 0
            },
            showSymbol: false,
            areaStyle: {
                opacity: 0.8,
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                    offset: 0,
                    color: this.dataColor[0].color1
                }, {
                    offset: 1,
                    color: this.dataColor[0].color2
                }])
            },
            emphasis: {
                focus: 'series'
            },
            data: this.dataList
        }]
};
        myChart.setOption(option);
      }
      
  },
  mounted(){
       var echarts = require('echarts');
     this.dataName=['销售金额[单位：元]'];
     this.dataColor=[
         {color1:'rgba(128, 255, 165)',color2:'rgba(1, 191, 236)'},
         {color1:'rgba(0, 221, 255)',color2:'rgba(77, 119, 255)'},
         {color1: 'rgba(55, 162, 255)',color2: 'rgba(116, 21, 219)'},
         {color1:'rgba(255, 0, 135)',color2:'rgba(135, 0, 157)'},
         {color1: 'rgba(255, 191, 0)',color2:'rgba(224, 62, 76)'},
         ];

        axios.get('/api/ssm/orders/getOrderDataPrice').then(res=>{
                if(res.status===200){
                    for(let i in res.data){
                        let date = new Date(res.data[i].date);
                        let time = date.getFullYear()+'-'+(date.getMonth()+1)+'-'+date.getDate();
                        this.dataBottom.unshift(time);
                        this.dataList.unshift(res.data[i].price);
                        this.drawLine();
                    }
                }else{
                    this.viewShow = false;
                }
        })
  },
}
</script>
<style scoped>
 #myChart{
   margin-top: 20px;
 }
</style>
