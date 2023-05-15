<template>
  <!-- 头部区域 -->
  <el-container class="home-container">
    <el-header>
      <div class="left-logo">
        <!-- <img src="../assets/drag.jpg" alt="商标Logo" /> -->
        <span>欢迎{{userInfo.username}}
          <!-- <i v-if="userInfo.sex=='女'" class="el-icon-female" style="color:red;"></i>
          <i v-else class="el-icon-male" style="color:blue;"></i> -->
        </span>
      </div>
      <el-button type="danger" @click="logout">退出</el-button>
    </el-header>
    <!-- 页面主体区域 -->
    <el-container>
      <!-- 侧边栏 -->
      <el-aside :width="isCollapse ? '64px' : '200px'">
        <div class="toggle-button" @click="toggleCollapse">|||</div>
        <el-menu
          background-color="#333744"
          text-color="#fff"
          active-text-color="#409EFF"
          :unique-opened="true"
          :collapse="isCollapse"
          :collapse-transition="false"
          router
          :default-active="activePath"
        >
          <!-- 一级菜单 -->
          <el-submenu
            :index="item.id + ''"
            v-for="item in menuList"
            :key="item.id"
          >
            <template slot="title">
              <i :class="icons[item.id]"></i>
              <span>{{ item.name }}</span>
            </template>
            <!-- 二级菜单 -->
            <el-menu-item
              :index="'/' + subItem.path"
              v-for="subItem in item.children"
              :key="subItem.id"
              @click="savePath('/' + subItem.path)"
            >
              <template slot="title">
                <i class="el-icon-menu"></i>
                <span>{{ subItem.name }}</span>
              </template>
            </el-menu-item>
          </el-submenu>
        </el-menu>
      </el-aside>
      <!-- 右侧内容主体 -->
      <el-main>
      <!-- 路由占位符 -->
      <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>
<script>
import axios from 'axios';
export default {
  data() {
    return {
      menuList: [
        {
          name: '信息管理',
          id: 11,
          children: [
            {
              name: '个人信息',
              id: 111,
              path: 'userInfo',
            }],
        },
        {
          name: '车位管理',
          id: 13,
          children: [
            {
              name: '车位信息',
              id: 131,
              path:'carInfo'
            }],
        },
        {
          name: '费用管理',
          id: 14,
          children: [
            {
              name: '收费信息',
              id: 141,
              path:'cost'
            }
          ],
        },
      ],
      icons:{
        '11':'fa fa-user-secret',
        // '12':'fa fa-taxi',
        '13':'fa fa-taxi',
        '14':'fa fa-ticket',
      },
      isCollapse:false,
      userInfo:{},
      // isTransition:true
      activePath: '/users'
    }
  },
  created() {
    this.activePath = window.sessionStorage.getItem('savePath')
  },
  methods: {
    getUserInfo(){
      const uid = window.sessionStorage.getItem('token');
      axios.get('/api/user/getUserByUid?uid='+uid).then(res=>{
        this.userInfo = res.data.data;
      })
    },
    logout() {
      window.sessionStorage.clear()
      this.$router.push('/login')
      this.$message('您已退出登录，请重新登录！')
    },
    // 点击按钮菜单的折叠与展开
    toggleCollapse(){
      this.isCollapse = !this.isCollapse
    },
    savePath(savePath){
      window.sessionStorage.setItem('savePath',savePath)
    }
  },
  mounted(){
    this.getUserInfo();
  }
}
</script>
<style scoped>
.home-container {
  height: 100%;
}
.el-header {
  background-color: #373d41;
  display: flex;
  justify-content: space-between;
  padding-left: 0;
  align-items: center;
  color: #ffffff;
  font-size: 20px;
}
.left-logo {
  height: 100%;
  display: flex;
  align-items: center;
}
.left-logo > span {
  margin-left: 15px;
}
.left-logo > img {
  height: 100%;
  border-radius: 50%;
}
.el-aside {
  background-color: #333744;
}
.el-main {
  background-color: #eaedf1;
}
.el-menu{
  border-right: none;
}
.fa{
  margin-right: 10px;
}
.toggle-button{
  background-color: #4A5064;
  font-size: 10px;
  line-height: 24px;
  color: #fff;
  text-align: center;
  letter-spacing: 0.2em;
  cursor: pointer;
}
</style>