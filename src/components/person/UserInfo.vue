<template>
  <div>
     <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item>信息管理</el-breadcrumb-item>
      <el-breadcrumb-item>个人信息</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 卡片视图区域 -->
    <el-card>
      <el-row :gutter="20">
        <el-col :span="16">
          <div class="grid-content bg-purple">
            <h2>个人基本信息</h2>
          </div>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="4"><div class="grid-content bg-purple">用户名：{{userInfo.username}}</div></el-col>
        <el-col :span="5"><div class="grid-content bg-purple">联系电话：{{userInfo.phone}}</div></el-col>
        <el-col :span="4"><div class="grid-content bg-purple">年龄：{{userInfo.age}}岁</div></el-col>
      </el-row>
      <br>
      <el-row :gutter="20">
        <el-col :span="4"><div class="grid-content bg-purple">性别：{{userInfo.sex}}</div></el-col>
        <el-col :span="5"><div class="grid-content bg-purple">车牌号：{{userInfo.card}}</div></el-col>
        <el-col :span="4"><div class="grid-content bg-purple">
          <el-button type="primary" @click="updateUser">修改信息</el-button></div></el-col>
      </el-row>
      <br>
      <el-row :gutter="20">
        <el-col :span="10">
          <div v-if="!dataTypeShow" class="data-type">
            <h2 class="h2-text">停车类型记录</h2>
            <div class="data-text">
              没有相关记录
            </div>
          </div>
          <div v-show="dataTypeShow" class="grid-content bg-purple" id='myChart'>
          </div>
        </el-col>
        <el-col :span="10">
          <div class="grid-content bg-purple" id='chart2'>
          </div>
          
        </el-col>
      </el-row>
    </el-card>
    <el-dialog title="修改信息" :visible.sync="editShow" width="50%" center @close="editClose" >
      <!-- 内容主题区域 -->
      <el-form :model="editUser" :rules="editRules" ref="editRules" label-width="80px">
        <!-- <el-form-item label="姓名" prop="uname">
          <el-input v-model="editUser.uname"></el-input>
        </el-form-item> -->
        <el-form-item label="密码" prop="password">
          <el-input v-model="editUser.password"></el-input>
        </el-form-item>
        <el-form-item label="车牌号" prop="card">
          <el-input v-model="editUser.card"></el-input>
        </el-form-item>
        <el-form-item label="联系电话" prop="phone">
          <el-input v-model="editUser.phone"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <template>
            <el-radio v-model="editUser.sex" label="男">男</el-radio>
            <el-radio v-model="editUser.sex" label="女">女</el-radio>
          </template>
        </el-form-item>
      </el-form>

      <!-- 底部区域 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="editClose()">取 消</el-button>
        <el-button type="primary" @click="editCommit('editRules')">确认修改</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import axios from 'axios'

export default {
  data(){
    var checkAge = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('年龄不能为空'));
        }
        setTimeout(() => {
          if (!Number.isInteger(value)) {
            callback(new Error('请输入数字值'));
          } else {
            if (value < 18) {
              callback(new Error('必须年满18岁'));
            } else {
              callback();
            }
          }
        }, 1000);
      };
     var validatePass2 = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请再次输入密码'));
        } else if (value !== this.editUser.password) {
          callback(new Error('两次输入密码不一致!'));
        } else {
          callback();
        }
      };
    return{
      userInfo:{},
      editUser:{
        password:'',
        age:'',
        sex:'',
        phone:'',
        card:'',      },
      editShow:false,
      dataTypes:[],
      dataMoneyName:[],
      dataMoneyValue:[],
      dataTypeShow: false,
      editRules:{
         password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          {
            min: 6,
            max: 15,
            message: '长度在 6 到 15 个字符',
            trigger: 'blur',
          },
        ],
        check: [
          { validator: validatePass2, trigger: 'blur' }
        ],
        age: [
          { validator: checkAge, trigger: 'blur' }
        ],
        phone: [
           { required: true, message: '请输入联系电话', trigger: 'blur' },
        ],
        card: [
           { required: true, message: '请输入车牌号', trigger: 'blur' },
        ]
      }
    }
  },
  methods:{
    getUserInfo(uid){
      axios.get('/api/user/getUserByUid?uid='+uid).then(res=>{
        this.userInfo = res.data.data
        this.getUserDataType(this.userInfo.username);
        this.getUserDataMoney(this.userInfo.username);
      })
    },
    getUserDataType(person){
      axios.get('/api/user/dataType?person='+person).then(res=>{
        this.dataTypes = res.data.data
        if(this.dataTypes.length>0){
          this.dataTypeShow = true;
          this.draw1();
        }else{
          this.dataTypeShow = false;
        } 
      })
    },
    getUserDataMoney(person){
      axios.get('/api/user/dataMoney?person='+person).then(res=>{
        this.dataMoneyName = [];
        this.dataMoneyValue = [];
        if(res.data.data.length>0){
          for(let i=0;i<res.data.data.length;i++){
            this.dataMoneyName.push(this.dateFormat(res.data.data[i].name,2));
            this.dataMoneyValue.push(res.data.data[i].value);
          }
          this.draw2();
        }
      })
    },
    updateUser(){
      this.editUser.phone = this.userInfo.phone;
      this.editUser.age = this.userInfo.age;
      this.editUser.sex = this.userInfo.sex;
      this.editUser.card = this.userInfo.card;
      this.editUser.password = this.userInfo.password;
      this.editShow =true;
    },
    editClose(){
      this.editShow = false;
    },
    draw1(){
      var echarts = require('echarts');
      var myChart = echarts.init(document.getElementById('myChart'));
      var option = {
          title: {
            text: '停车类型记录',
            left: 'center'
          },
          tooltip: {
            trigger: 'item'
          },
          legend: {
            orient: 'vertical',
            left: 'left'
          },
          series: [
            {
              name: '次数',
              type: 'pie',
              radius: '50%',
              data: this.dataTypes,
              emphasis: {
                itemStyle: {
                  shadowBlur: 10,
                  shadowOffsetX: 0,
                  shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
              }
            }
          ]
        };

      option && myChart.setOption(option);
    },
      draw2(){
      var echarts = require('echarts');
      var myChart = echarts.init(document.getElementById('chart2'));
      var option = {
            title: {
              text: '近五天缴费记录',
              left: 'center'
            },
            toolbox: {
              show: true,
              orient: 'vertical',
              left: 'right',
              top: 'center',
              feature: {
                mark: { show: true },
                dataView: { show: true, readOnly: false },
                magicType: { show: true, type: ['line', 'bar', 'stack'] },
                restore: { show: true },
                saveAsImage: { show: true }
              }
            },
            xAxis: {
              type: 'category',
              axisLabel:{ 
                  rotate : 45
              },
              data: this.dataMoneyName
            },
            yAxis: {
              type: 'value'
            },
            series: [
              {
                data: this.dataMoneyValue,
                type: 'bar',
                showBackground: true,
                backgroundStyle: {
                  color: 'rgba(180, 180, 180, 0.2)'
                }
              }
            ]
            };

      option && myChart.setOption(option);
    },
    editCommit(){
      this.editUser.uid = this.userInfo.uid;
      axios.post('/api/user/updateByUser',this.editUser).then(res=>{
        if(res.data.data){
          this.$message.success('修改成功！');
          this.getUserInfo(this.userInfo.uid);
        }else{
          this.$message.error('修改失败！');
        }
        this.editShow = false;
      })
    },
    //时间转换函数
    dateFormat(val,timeType){
        //将字符串或长整型转为时间类型
        let date = new Date(val);
        //默认格式为'年月日 时:分:秒'
        let resultFormat = 'Y年M月D日 h:m:s'
        switch (timeType) {
            case 1: resultFormat = 'Y年M月D日';break;
            case 2: resultFormat = 'Y-M-D';break;
            case 3: resultFormat = 'Y-M-D h:m:s';break;
        }
        let result = formatTime(resultFormat);

        // 格式化日期，如月、日、时、分、秒保证为2位数
        function formatNumber (n) {
            n = n.toString()
            return n[1] ? n : '0' + n;
        }
        // 参数number为毫秒时间戳，format为需要转换成的日期格式
        function formatTime (format) {
            let newArr = []
            let formatArr = ['Y', 'M', 'D', 'h', 'm', 's']
            newArr.push(date.getFullYear())
            newArr.push(formatNumber(date.getMonth() + 1))
            newArr.push(formatNumber(date.getDate()))

            newArr.push(formatNumber(date.getHours()))
            newArr.push(formatNumber(date.getMinutes()))
            newArr.push(formatNumber(date.getSeconds()))

            for (let i in newArr) {
                format = format.replace(formatArr[i], newArr[i])
            }
            return format;
        }

        return result;
      },
  },
  mounted(){
    const uid = window.sessionStorage.getItem('token');
    this.getUserInfo(uid);
    // this.draw1();
    // this.draw2();
  },
}
</script>
<style scoped>
#myChart{
  width: 500px;
  height: 450px;
}
#chart2{
  width: 500px;
  height: 450px;
}
.h2-text{
  color: rgb(70, 70, 70);
  font-size: 18px;
  text-align: center;
}
.data-text{
  width: 100%;
  height: 150px;
  line-height: 150px;
  text-align: center;
  color: rgb(203, 199, 199);
  font-size: 24px;
}
</style>