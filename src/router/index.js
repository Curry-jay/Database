import Vue from 'vue'
import Router from 'vue-router'
import login from '../view/Login.vue'
import mainPage from '../view/MainPage.vue'
import charts from '../components/Charts.vue'
Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'login',
      component: login
    },
   
    {
      path: '/mainPage',
      name: 'mainPage',
      component: mainPage,
      meta: {
        title: '首页',
        close: false,
      },

      children: [
        {
          //地址为空时跳转home页面
          path: '/mainPage',
          redirect: '/charts'
        },
        {
          path: '/charts',
          name: 'charts',
          component: charts,
          meta: {title: '首页', keepAlive: true}
        },
        {
          path: '/text',
          name: 'text',
          component: ()=>import("../view/text/Text.vue"),
          meta: {title: '文字检查', keepAlive: true}
        },
        {
          path: '/error',
          name: 'error',
          component: ()=>import("../components/Error.vue"),
          meta: {title: '服务器异常', keepAlive: true}
        },

        {
          path: '/photo',
          name: 'photo',
          component: ()=>import("../view/photo/Photo.vue"),
          meta: {title: '图片检查', keepAlive: true}
        },
        {
          path: '/video',
          name: 'video',
          component: ()=>import("../view/video/video.vue"),
          meta: {title: '视频检查', keepAlive: true}
        },
        {
          path: '/voice',
          name: 'voice',
          component: ()=>import("../view/voice/Voice.vue"),
          meta: {title: '语音检查', keepAlive: true}
        },
      ]

      
    }
  ]
})

// 重复点击一个路由会报错
/* 由于Vue-router在3.1之后把$router.push()方法改为了Promise。所以假如没有回调函数，错误信息就会交给全局的路由错误处理。
vue-router先报了一个Uncaught(in promise)的错误(因为push没加回调) ，然后再点击路由的时候才会触发NavigationDuplicated的错误(路由出现的错误，全局错误处理打印了出来)。*/
// 禁止全局路由错误处理打印
const originalPush = Router.prototype.push;
Router.prototype.push = function push(location, onResolve, onReject) {
  if (onResolve || onReject)
    return originalPush.call(this, location, onResolve, onReject)
  return originalPush.call(this, location).catch(err => err)
}

