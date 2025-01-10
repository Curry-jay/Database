import Vue from 'vue'
import App from './App'
import router from './router'
import store from "./store";

// //����elementUI
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
// //VUEʹ��ElementUI
Vue.use(ElementUI)

// //���붯�����
import animated from 'animate.css'

Vue.use(animated);
//����dialog�϶��¼�
import '../src/assets/js/dialog-directive'
// // axios���ص�ȫ�֣�����axios
import axios from "axios";

import * as echarts from "echarts";//���鰴�赼�룬����ռ����Ŀ̫����Դ
Vue.prototype.$echarts = echarts//����ȫ��ʵ��

//���û���·�ɣ�axios����Ļ���·��
// ���ص�ԭ�Ͷ������ϣ�  $http ���Լ��������
Vue.prototype.$http = axios

axios.defaults.baseURL = 'http://127.0.0.1:8888/'
import VideoPlayer from 'vue-video-player'
import 'vue-video-player/src/custom-theme.css'
import 'video.js/dist/video-js.css'

const SERVER_URL =  'http://127.0.0.1:8888/';

// ���ص� Vue ԭ���ϣ��������������ͨ�� this.$serverUrl ����
Vue.prototype.$serverUrl = SERVER_URL;

Vue.use(VideoPlayer)





new Vue({
  el: '#app',
  router, store,
  components: { App },
  template: '<App/>'
})
