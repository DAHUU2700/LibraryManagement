import Vue from 'vue'
import App from './App.vue'
import router from './router'
// 导入src下的css文件
import '@/assets/global.css'

//导入ElemetUI
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

//  导入滑块验证
import SlideVerify from 'vue-monoplasty-slide-verify';

Vue.config.productionTip = false
Vue.use(ElementUI);
Vue.use(SlideVerify);

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
