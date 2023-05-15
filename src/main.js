import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import './assets/css/global.css'
import './assets/css/font-awesome.min.css'
import ElementUi from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css';

Vue.use(ElementUi)
Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
