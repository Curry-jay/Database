const tagsView = {
  state: {
    visitedViews: [],
    cachedViews: [],
    //当前页面是什么
    nowViews: [],
  },
  mutations: {
    ADD_VISITED_VIEWS: (state, view) => {
      //如果不是首页那么就判断有没有，没有就加入
      if (view.meta.title !== '首页') {
        if (state.nowViews.length > 0) {
          for (let i = 0; i < state.nowViews.length; i++) {
            state.nowViews.splice(i, 1);
          }
        }
        state.nowViews.push(Object.assign({}, view, {
          title: view.meta.title || 'no-name'
        }))
      }else {
        //如果是首页也要判断
        if (state.visitedViews.some(v => v.meta.title === '首页')) return
      }
    
      if (state.visitedViews.some(v => v.path === view.path)) return
      state.visitedViews.push(Object.assign({}, view, {
        title: view.meta.title || 'no-name'
      }))
      if (!view.meta.noCache) {
        state.cachedViews.push(view.name)
      }
    },



    DEL_VISITED_VIEWS: (state, view) => {
      for (const [i, v] of state.visitedViews.entries()) {
        if (v.path === view.path) {
          state.visitedViews.splice(i, 1)
          break
        }
      }
      for (const i of state.cachedViews) {
        if (i === view.name) {
          const index = state.cachedViews.indexOf(i)
          state.cachedViews.splice(index, 1)
          break
        }
      }
    },
    DEL_OTHERS_VIEWS: (state,) => {

      //全删了
      for (const [i, v] of state.visitedViews.entries()) {
        state.visitedViews = state.visitedViews.slice(i, i + 1)
      }
      for (let i of state.cachedViews) {
        state.cachedViews = state.cachedViews.slice(state.cachedViews.indexOf(i), i + 1)
      }

    },
    DEL_ALL_VIEWS: (state) => {
      //全删了
      for (const [i, v] of state.visitedViews.entries()) {
        state.visitedViews = state.visitedViews.slice(i, i + 1)
      }
      for (let i of state.cachedViews) {
        state.cachedViews = state.cachedViews.slice(state.cachedViews.indexOf(i), i + 1)
      }
    }
  },
  actions: {
    addVisitedViews({commit}, view) {
      commit('ADD_VISITED_VIEWS', view)
    },
    delVisitedViews({commit, state}, view) {
      return new Promise((resolve) => {
        commit('DEL_VISITED_VIEWS', view)
        resolve([...state.visitedViews])
      })
    },
    delOthersViews({commit, state}, view) {
      return new Promise((resolve) => {
        commit('DEL_OTHERS_VIEWS', view)
        resolve([...state.visitedViews])
      })
    },
    delAllViews({commit, state}) {
      return new Promise((resolve) => {
        commit('DEL_ALL_VIEWS')
        resolve([...state.visitedViews])
      })
    }
  }
}

export default tagsView
