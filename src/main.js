import Vue from 'vue'
import App from './App'
import router from './router'
import store from "./store";

// //引入elementUI
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
// //VUE使用ElementUI
Vue.use(ElementUI)

// //引入动画组件
import animated from 'animate.css'

Vue.use(animated);
//引入dialog拖动事件
import '../src/assets/js/dialog-directive'
// // axios挂载到全局，引入axios
import axios from "axios";

import * as echarts from "echarts";//建议按需导入，不会占用项目太多资源
Vue.prototype.$echarts = echarts//构造全局实例

//设置基本路由，axios发射的基础路径
// 挂载到原型对象身上，  $http 是自己起的名字
Vue.prototype.$http = axios

axios.defaults.baseURL = 'http://127.0.0.1:8888/'
import VideoPlayer from 'vue-video-player'
import 'vue-video-player/src/custom-theme.css'
import 'video.js/dist/video-js.css'

const SERVER_URL =  'http://127.0.0.1:8888/';

// 挂载到 Vue 原型上，所有组件都可以通过 this.$serverUrl 访问
Vue.prototype.$serverUrl = SERVER_URL;

Vue.use(VideoPlayer)





new Vue({
  el: '#app',
  router, store,
  components: { App },
  template: '<App/>'
})
