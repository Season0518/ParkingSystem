<template>
  <div class="login_bg"
      v-loading="loading"
      element-loading-text="正在登录中,请稍等！"
      element-loading-spinner="el-icon-loading"
      element-loading-background="rgba(0, 0, 0, 0.8)">
    <div class="block" v-if="!registerShow">
      <el-carousel height="500px">
        <!-- <el-carousel-item v-for="item in imgs" :key="item">
          <img :src="'@/aseets'+item" alt="">
        </el-carousel-item> -->
        <el-carousel-item>
          <img src="@/assets/img/1.jpg" alt="">
        </el-carousel-item>
         <el-carousel-item>
          <img src="@/assets/img/2.jpg" alt="">
        </el-carousel-item>
        <el-carousel-item>
          <img src="@/assets/img/3.jpg" alt="">
        </el-carousel-item>
         <el-carousel-item>
          <img src="@/assets/img/4.jpg" alt="">
        </el-carousel-item>
      </el-carousel>
    </div>
    <div class="login_box" v-if="!registerShow">
      <div class="login-title">
        <h2>登录</h2>
      </div>
      <!-- 登录表单 -->
      <el-form
        class="login_form"
        :model="ruleForm"
        :rules="rules"
        ref="rules"
        label-width="0"
      >
        <el-form-item prop="username">
          <el-input v-model="ruleForm.username" placeholder="请输入用户名">
            <i slot="prefix" class="fa fa-user"></i>
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input type="password" v-model="ruleForm.password" placeholder="请输入密码">
            <i slot="prefix" class="fa fa-lock"></i>
          </el-input>
        </el-form-item>
        <el-button type="primary" @click="login">登录</el-button>
        <!-- <el-button type="info">重置</el-button> -->
      </el-form>
      <span class="register" @click="toRegister">立即注册</span>
    </div>
    <div class="register-box" v-if="registerShow">
        <h2>用户注册</h2>
       <el-form :model="registerUser" status-icon :rules="registerRules" ref="registerRules" label-width="100px" class="demo-ruleForm">
          <el-form-item label="用户名" prop="username">
            <el-input v-model="registerUser.username" placeholder="请输入用户名" style="width: 300px;"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input v-model="registerUser.password" placeholder="请输入密码" show-password style="width: 300px;"></el-input>
          </el-form-item>
          <el-form-item label="确认密码" prop="check" class="is-required">
            <el-input v-model="registerUser.check" placeholder="确认密码" show-password style="width: 300px;"></el-input>
          </el-form-item>
          <el-form-item label="性别" prop="sex" required>
            <el-radio v-model="registerUser.sex" label="男">男</el-radio>
            <el-radio v-model="registerUser.sex" label="女">女</el-radio>
          </el-form-item>
          <el-form-item label="年龄" prop="age" class="is-required">
            <el-input v-model.number="registerUser.age" style="width: 300px;"></el-input>
          </el-form-item>
          <el-form-item label="车牌号"  prop="card" class="is-required">
            <el-input v-model="registerUser.card" placeholder="请输入车牌号" style="width: 300px;"></el-input>
          </el-form-item>
          <el-form-item label="联系电话"  prop="phone" required>
            <el-input v-model="registerUser.phone" placeholder="请输入联系电话" style="width: 300px;"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button class="register-btn" type="primary" @click="submitForm('registerRules')">注册</el-button>
            <el-button @click="resetForm('registerRules')">重置</el-button>
          </el-form-item>
        </el-form>
        <span class="to-login" @click="toLogin">>>返回登录</span>
    </div>
  </div>
</template>
<script>
import axios from 'axios'
export default {
  data() {
    var checkUsername = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('用户名不能为空'));
        }
        setTimeout(() => {
          axios.get('/api/user/checkUsername?username='+value).then(res=>{
            if(!res.data.data){
              callback(new Error('用户名已存在'));
            }else{
              callback();
            }
          })
        }, 10);
      };
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
        } else if (value !== this.registerUser.password) {
          callback(new Error('两次输入密码不一致!'));
        } else {
          callback();
        }
      };
    return {
      registerShow: false,
      loading: false,
      imgs:[
        "/img/1.jpg",
        "/img/2.jpg",
        "/img/3.jpg",
        "/img/4.jpg",
      ],
      ruleForm: {
        username: '',
        password: '',
      },
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          {
            min: 3,
            max: 10,
            message: '长度在 3 到 10 个字符',
            trigger: 'blur',
          },
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          {
            min: 6,
            max: 15,
            message: '长度在 6 到 15 个字符',
            trigger: 'blur',
          },
        ],
      },
      registerUser: {
        username: '',
        password: '',
        check: '',
        sex: '男',
        phone: '',
        age: '',
        card: '',
      },
      registerRules:{
        username: [
          { validator: checkUsername, trigger: 'blur' }
        ],
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
      },
    }
  },
  methods: {
    login(){
      this.loading = true;
      axios.post('/api/user/login',this.ruleForm).then(res=>{
          this.loading = false;
          if(res.data.data.state){
            this.$message.success('登陆成功！');
            //  token只应在当前网站打开期间生效，所以将token保存在sessionStorage中
            window.sessionStorage.setItem('token', res.data.data.uid);
            if(this.ruleForm.username=='admin'){
              this.$router.push('/mane');
            }else{
              this.$router.push('/conmen');
            }
            //  this.$router.push('/home');
          }else{
             this.$message.error('登录失败,'+res.data.data.msg+"!");
          }
      })
    },
    toRegister(){
      this.registerShow = true;
    },
    toLogin(){
      this.registerShow = false;
      this.$nextTick(()=>{
        this.$refs.rules.resetFields();
      });
    },
    submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.loading = true;
            axios.post('/api/user/register',this.registerUser).then(res=>{
              this.loading = false;
              if(res.data.data){
                this.$message.success('注册成功！');
                this.registerShow = false;
              }else{
                this.$message.error('登录失败,请重新尝试！');
              }
            })
          } else {
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      },
      
  },
}
</script>
<style lang="less" scoped>
.login_bg {
  height: 100%;
  background-image: url('../../public/img/bg1.jpg');
  background-repeat: no-repeat;
  background-size: cover;
}
.block{
  width: 350px;
  height: 500px;
  position: absolute;
  left: 50%;
  top: 50%;
  background: #fff;
  transform: translate(-90%,-50%);
   img{
    width: 100%;
    height: 100%;
  }
}
.register{
  display: inline-block;
  height: 32px;
  line-height: 32px;
  position: absolute;
  bottom: 20px;
  right: 20px;
  color: rgb(172, 169, 169);
  cursor: pointer;
}
.register:hover{
  color: red;
}
.register-box{
  height: 700px;
  width: 600px;
  background: #fff;
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-300px, -350px);
}
.register-box h2{
  text-align: center;
}

.login_box {
  width: 350px;
  height: 500px;
  background-color: rgb(248, 246, 246);
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(10%, -50%);
  // border-left: 1px solid rgb(25, 186, 235);
}
.login-title{
  position: absolute;
  top: 10%;
  width: 70%;
  left: 50%;
  transform: translate(-50%,20%);
}
.login-title h2{
  text-align: center;
}
.login_form {
  position: absolute;
  width: 70%;
  left: 50%;
  transform: translate(-50%, 100%);
}
.login_form > .el-input {
  width: 100%;
  padding: 20px;
}
.login_form > .el-button {
  width: 100%;
  margin-left: 50%;
  transform: translate(-50%);
}
.fa {
  /* margin:0 15px; */
  margin-left: 7px;
}
.register-btn{
  width: 150px;

}
.to-login{
  position: absolute;
  top: 20px;
  left: 20px;
  color: rgb(146, 146, 146);
  cursor: pointer;
  font-size: 14px;
}
.to-login:hover{
  color: red;
}
</style>