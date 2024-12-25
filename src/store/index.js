import Vue from "vue";
import Vuex from 'vuex'
import tagsView from './modules/tagsView'
import menuList from "./modules/menuList";
import { name } from "file-loader";
Vue.use(Vuex);

const actions = {

}

const mutations = {

}

const state = {

  isLogin: false,

  admin: {
    id: 6,
    userName: 'admin',
    passWord: '',
    url: '',
    department: {
      id: 4,
      name: '111',
      type: 7
    }
  },

  textList: [
    {
      id: 0,
      text: '123123',
      time: '2024-12-5',
      identify: '65%'
    },
    {
      id: 1,
      text: '阿斯达克里面哪里开始慢慢离开',
      time: '2024-12-5',
      identify: '65%'
    },
    {
      id: 2,
      text: '人才是科技创新的源泉，是推动社会进步的核心动力。无论是在基础设施建设、工程技术还是航天领域，科技创新的背后都离不开一大批高素质的人才。例如，嫦娥六号任务的成功实施，离不开科研人员的无私奉献与专业能力。这些科研人才不仅具备扎实的专业知识，还具备强烈的创新意识和探索精神。他们攻克了多个世界级技术难题，创造了多个突破性的技术记录，为我国航天事业的发展做出了不可磨灭的贡献。',
      time: '2024-12-5',
      identify: null
    },
    {
      id: 3,
      text: '在教育领域，科技应用为学生带来了全新的学习体验，尤其是在线教育平台的崛起。通过互联网技术，优质教育资源不再受地域限制，学生们可以随时随地通过智能设备参与课堂学习。与此同时，虚拟现实（VR）和增强现实（AR）等技术的引入，使得学习过程变得更加生动与互动，学生们能够沉浸在更加真实的学习场景中，大大提升了学习的效果和趣味性。在教育领域，科技应用为学生带来了全新的学习体验，尤其是在线教育平台的崛起。通过互联网技术，优质教育资源不再受地域限制，学生们可以随时随地通过智能设备参与课堂学习。与此同时，虚拟现实（VR）和增强现实（AR）等技术的引入，使得学习过程变得更加生动与互动，学生们能够沉浸在更加真实的学习场景中，大大提升了学习的效果和趣味性。在教育领域，科技应用为学生带来了全新的学习体验，尤其是在线教育平台的崛起。通过互联网技术，优质教育资源不再受地域限制，学生们可以随时随地通过智能设备参与课堂学习。与此同时，虚拟现实（VR）和增强现实（AR）等技术的引入，使得学习过程变得更加生动与互动，学生们能够沉浸在更加真实的学习场景中，大大提升了学习的效果和趣味性。',
      time: '2024-12-5',
      identify: null
    },

  ],
  videoList: [
    {
      id: 0,
      title: '123123',
      time: '2024-12-5',
      identify: '65%',
      url: '1.mp4'
    },
    {
      id: 1,
      title: '阿斯达克里面哪里开始慢慢离开',
      time: '2024-12-5',
      identify: '65%',
      url: '2.mp4'
    },
    {
      id: 2,
      title: '为我国航天事业的发展做出了不可磨灭的贡献。',
      time: '2024-12-5',
      identify: null,
      url: '3.mp4'
    },
    {
      id: 3,
      title: '大大提升了学习的效果和趣味性。',
      time: '2024-12-5',
      identify: null,
      url:'4.mp4'
    },
    {
      id: 4,
      title: '测试修改样例。',
      time: '2024-12-5',
      identify: null,
      url:'5.mp4'
    },
  ],
  voiceList: [
    {
      id: 0,
      title: '我不难过',
      time: '2024-12-5',
      identify: '65%',
      url: '李荣浩我不难过.mp3'
    },
    {
      id: 1,
      title: '孤独患者',
      time: '2024-12-5',
      identify: '65%',
      url: '陈奕迅孤独患者.mp3'
    },
    {
      id: 2,
      title: '葡萄成熟时',
      time: '2024-12-5',
      identify: null,
      url: '陈奕迅 - 葡萄成熟时.mp3'
    },
    {
      id: 3,
      title: '陈奕迅 - 富士山下',
      time: '2024-12-5',
      identify: null,
      url:'陈奕迅 - 富士山下.mp3'
    },
    {
      id: 4,
      title: '退后',
      time: '2024-12-5',
      identify: null,
      url:'5.mp4'
    },
  ],
  photoList: [
    {
      id: 0,
      title: '李荣浩',
      time: '2024-12-5',
      identify: '65%',
      url: '1.jpg'
    },
    {
      id: 1,
      title: '李荣浩',
      time: '2024-12-5',
      identify: '65%',
      url: '2.jpg'
    },
    {
      id: 2,
      title: 'curry',
      time: '2024-12-5',
      identify: null,
      url: '3.jpg'
    },
    {
      id: 3,
      title: 'jay',
      time: '2024-12-5',
      identify: null,
      url: '4.jpg'
    },
    {
      id: 4,
      title: '医生',
      time: '2024-12-5',
      identify: null,
      url: '5.jpg'
    },
    {
      id: 5,
      title: '错误图片测试',
      time: '2024-12-5',
      identify: null,
      url: '123.jpg'
    },
  ]
}

export default new Vuex.Store({
  actions,
  mutations,
  state,
  modules: {
    tagsView,
    menuList
  },
})
