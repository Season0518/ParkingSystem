import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../components/Login.vue'
import Home from '../views/Home.vue'
import Conmen from '../views/Conmen.vue'
import UserInfo from '../components/person/UserInfo.vue'
import CarInfo from '../components/car/CarInfo.vue'
import Cost from '../components/money/Cost.vue'
import Users from '../components/user/Users.vue'
import Mane from '../views/Mane.vue'
import CarManeger from '../components/car/CarManeger.vue'
import CarInMane from '../components/mane/CarInMane.vue'
import CarMane from '../components/mane/CarMane.vue'
import Money from '../components/mane/Money.vue'
import StallMane from '../components/mane/StallMane.vue'
import UserMane from '../components/mane/UserMane.vue'
import Shop from '../components/shop/Shop.vue'
import Din from '../components/din/Din.vue'
import Data from '../components/data/Data.vue'
import AddShop from '../components/shop/AddShop.vue'
import DataPrice from '../components/data/DataPrice.vue'

Vue.use(VueRouter)

const routes = [{
            path: '/',
            redirect: '/login'
        }, {
            path: '/login',
            component: Login
        },
        {
            path: '/conmen',
            component: Conmen,
            redirect: '/carInfo', 
            children:[
                {
                    path: '/userInfo',
                    component: UserInfo
                },
                {
                    path: '/carInfo',
                    component: CarInfo
                },
                {
                    path: '/cost',
                    component: Cost
                },
            ]
        },
        {
            path: '/mane',
            component: Mane,
            redirect: '/userMane', 
            children:[
                {
                    path: '/userMane',
                    component: UserMane
                },
                {
                    path: '/carmaneger',
                    component: CarManeger
                },
                {
                    path: '/carInMane',
                    component: CarInMane
                },
                {
                    path: '/stallMane',
                    component: StallMane
                },
                {
                    path: '/money',
                    component: Money
                },
            ]
        },
        {
            path: '/home',
            component: Home,
            redirect: '/users',
            children: [
            {
                path: '/users',
                component: Users
            }, {
                path: '/shop',
                component: Shop
            }, {
                path: '/addshop',
                component: AddShop
            }, {
                path: '/din',
                component: Din
            },  {
                path: '/data',
                component: Data
            },  {
                path: '/dataprice',
                component: DataPrice
            }]
        }

    ]
    // const routes = [{
    //         path: '/',
    //         name: 'Index',
    //         component: Index,
    //         props: true
    //     },
    //     {
    //         path: '/enter',
    //         name: 'Enter',
    //         props: true,
    //         // route level code-splitting
    //         // this generates a separate chunk (about.[hash].js) for this route
    //         // which is lazy-loaded when the route is visited.
    //         component: () =>
    //             import ( /* webpackChunkName: "Enter" */ '../views/Enter.vue')
    //     },
    //     {
    //         path: '/about',
    //         name: 'About',
    //         // route level code-splitting
    //         // this generates a separate chunk (about.[hash].js) for this route
    //         // which is lazy-loaded when the route is visited.
    //         component: () =>
    //             import ( /* webpackChunkName: "about" */ '../views/About.vue')
    //     }
    // ]

const router = new VueRouter({
    routes
})

// 挂载路由导航守卫(防止没有进行验证后直接跳转到了相关页面)
router.beforeEach((to, from, next) => {
    //to 将要访问的路径
    // from 代表从哪个路径跳转而来
    // next是一个函数，表示放行
    // next() 放行  next('/login')强制性跳转到指定路由
    if (to.path === '/login') return next();
    //获取token
    const tokenStr = window.sessionStorage.getItem('token')
    if (!tokenStr) return next('/login')
    next()

})

export default router