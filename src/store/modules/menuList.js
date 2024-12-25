const menuList = {
  state: {
    //分站
    AllMenuList: [
      {
        icon: "el-icon-document-checked",
        name: "文字检查",
        path: '/text',
        children: [
          {
            path: "/text", name: "文字检查"
          },]
      },
      {
        icon: "el-icon-s-check",
        name: "图片检查",
        path: "/photo",
        children: [
          {
            path: "/photo", name: "图片检查"
          },]
      },
      {
        icon: "el-icon-s-claim",
        name: "语音检查",
        path: '/voice',
        children: [
          {
            path: "/voice", name: "语音检查"
          },]
      },
      {
        icon: "el-icon-s-claim",
        name: "视频检查",
        path: '/video',
        children: [
          {
            path: "/video", name: "视频检查"
          },]
      },
    ],
  },
  mutations: {},
  actions: {}
}

export default menuList
