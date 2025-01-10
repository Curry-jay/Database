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
      content: '123123',
      saveTime: '2024-12-5',
      recognitionPercentage: '65%'
    },
    {
      id: 1,
      content: '阿斯达克里面哪里开始慢慢离开',
      saveTime: '2024-12-5',
      recognitionPercentage: '65%'
    },
    {
      id: 2,
      content: '人才是科技创新的源泉，是推动社会进步的核心动力。无论是在基础设施建设、工程技术还是航天领域，科技创新的背后都离不开一大批高素质的人才。例如，嫦娥六号任务的成功实施，离不开科研人员的无私奉献与专业能力。这些科研人才不仅具备扎实的专业知识，还具备强烈的创新意识和探索精神。他们攻克了多个世界级技术难题，创造了多个突破性的技术记录，为我国航天事业的发展做出了不可磨灭的贡献。',
      saveTime: '2024-12-5',
      recognitionPercentage: null
    },
    {
      id: 3,
      content: '在教育领域，科技应用为学生带来了全新的学习体验，尤其是在线教育平台的崛起。通过互联网技术，优质教育资源不再受地域限制，学生们可以随时随地通过智能设备参与课堂学习。与此同时，虚拟现实（VR）和增强现实（AR）等技术的引入，使得学习过程变得更加生动与互动，学生们能够沉浸在更加真实的学习场景中，大大提升了学习的效果和趣味性。在教育领域，科技应用为学生带来了全新的学习体验，尤其是在线教育平台的崛起。通过互联网技术，优质教育资源不再受地域限制，学生们可以随时随地通过智能设备参与课堂学习。与此同时，虚拟现实（VR）和增强现实（AR）等技术的引入，使得学习过程变得更加生动与互动，学生们能够沉浸在更加真实的学习场景中，大大提升了学习的效果和趣味性。在教育领域，科技应用为学生带来了全新的学习体验，尤其是在线教育平台的崛起。通过互联网技术，优质教育资源不再受地域限制，学生们可以随时随地通过智能设备参与课堂学习。与此同时，虚拟现实（VR）和增强现实（AR）等技术的引入，使得学习过程变得更加生动与互动，学生们能够沉浸在更加真实的学习场景中，大大提升了学习的效果和趣味性。',
      saveTime: '2024-12-5',
      recognitionPercentage: null
    },

  ],
  videoList: [
    {
      id: 0,
      headline: '李荣浩',
      saveTime: '2024-12-5',
      recognitionPercentage: '65%',
      path: '1.mp4'
    },
    {
      id: 1,
      headline: '李荣浩',
      saveTime: '2024-12-5',
      recognitionPercentage: '65%',
      path: '2.mp4'
    },
    {
      id: 2,
      headline: '李荣浩',
      saveTime: '2024-12-5',
      recognitionPercentage: '65%',
      path: '3.mp4'
    },
    {
      id: 3,
      headline: '李荣浩',
      saveTime: '2024-12-5',
      recognitionPercentage: '65%',
      path:'4.mp4'
    },
    {
      id: 4,
      headline: '李荣浩',
      saveTime: '2024-12-5',
      recognitionPercentage: '65%',
      path:'5.mp4'
    },
  ],
  voiceList: [
    {
      id: 0,
      headline: '李荣浩',
      saveTime: '2024-12-5',
      recognitionPercentage: '65%',
      path: '李荣浩我不难过.mp3'
    },
    {
      id: 1,
      headline: '李荣浩',
      saveTime: '2024-12-5',
      recognitionPercentage: '65%',
      path: '陈奕迅孤独患者.mp3'
    },
    {
      id: 2,
      headline: '李荣浩',
      saveTime: '2024-12-5',
      recognitionPercentage: '65%',
      path: '陈奕迅 - 葡萄成熟时.mp3'
    },
    {
      id: 3,
      headline: '李荣浩',
      saveTime: '2024-12-5',
      recognitionPercentage: '65%',
      path:'陈奕迅 - 富士山下.mp3'
    },
    {
      id: 4,
      headline: '李荣浩',
      saveTime: '2024-12-5',
      recognitionPercentage: '65%',
      path:'5.mp4'
    },
  ],
  photoList: [
    {
      id: 0,
      headline: '李荣浩',
      saveTime: '2024-12-5',
      recognitionPercentage: '65%',
      path: '1.jpg'
    },
    {
      id: 1,
      headline: '李荣浩',
      saveTime: '2024-12-5',
      recognitionPercentage: '65%',
      path: '2.jpg'
    },
    {
      id: 2,
      headline: '李荣浩',
      saveTime: '2024-12-5',
      recognitionPercentage: '65%',
      path: '3.jpg'
    },
    {
      id: 4,
      headline: '李荣浩',
      saveTime: '2024-12-5',
      recognitionPercentage: '65%',
      path: '4.jpg'
    },
    {
      id: 5,
      headline: '李荣浩',
      saveTime: '2024-12-5',
      recognitionPercentage: '65%',
      path: '5.jpg'
    },
    {
      id: 6,
      headline: '李荣浩',
      saveTime: '2024-12-5',
      recognitionPercentage: '65%',
      path: '6.jpg'
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
