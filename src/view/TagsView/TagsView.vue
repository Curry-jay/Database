<template>
  <div class="tags-view-container">
    <scroll-pane ref="scrollPane" class="tags-view-wrapper">
      <router-link
        v-for="tag in Array.from(visitedViews)"
        ref="tag"
        :key="tag.path"
        :class="isActive(tag)?'active':''"
        :to="tag"
        class="tags-view-item"
        @contextmenu.prevent.native="openMenu(tag,$event)"
      >
        {{ tag.title }}
        <span v-if="tag.title !== '首页'" class="el-icon-close" @click.prevent.stop="closeSelectedTag(tag)"/>
      </router-link>
    </scroll-pane>
    <!--    https://blog.csdn.net/weixin_42343966/article/details/127258274?ops_request_misc=%257B%2522request%255Fid%2522%253A%2522168852653616800226530500%2522%252C%2522scm%2522%253A%252220140713.130102334.pc%255Fall.%2522%257D&request_id=168852653616800226530500&biz_id=0&utm_medium=distribute.pc_search_result.none-task-blog-2~all~first_rank_ecpm_v1~rank_v31_ecpm-1-127258274-null-null.142^v88^insert_down38v5,239^v2^insert_chatgpt&utm_term=vue2%20%E5%86%99tagview&spm=1018.2226.3001.4187-->
    <ul v-show="visible" class="contextmenu" :style="{left:left+'px',top:top+'px'}">
      <li @click="closeSelectedTag(selectedTag)"><i class="el-icon-close"></i> 关闭当前</li>
      <li @click="closeOthersTags"><i class="el-icon-circle-close"></i> 关闭其他</li>
      <li @click="closeAllTags"><i class="el-icon-circle-close"></i> 全部关闭</li>
    </ul>
  </div>
</template>

<script>
import ScrollPane from './ScrollPane.vue'

export default {
  name: 'TagsView',
  components: {ScrollPane},
  data() {
    return {
      visible: false,
      top: 0,
      left: 0,
      selectedTag: {}
    }
  },
  computed: {
    visitedViews() {
      return this.$store.state.tagsView.visitedViews
    }
  },
  watch: {
    $route() {
      if (this.$route.path !== '/error'){
        this.addViewTags()
      }
    },
    visible(value) {
      if (value) {
        document.body.addEventListener('click', this.closeMenu)
      } else {
        document.body.removeEventListener('click', this.closeMenu)
      }
    }
  },
  mounted() {
    if (this.$route.path !== '/error'){
      this.addViewTags()
    }else {
      this.$router.back();
      
    }
  },
  methods: {
    generateRoute() {
      if (this.$route.name) {
        return this.$route
      }
      return false
    },
    isActive(route) {
    
      return route.path === this.$route.path
    },
    //增加一个
    addViewTags() {
      let route = this.generateRoute()
      if (!route) {
        return false
      }
   
      this.$store.dispatch('addVisitedViews', {
        fullPath: "/charts",
        hash: "",
        meta: {
          close: false,
          title: "首页"
        },
        name: "charts",
        path: "/charts",
      })
      this.$store.dispatch('addVisitedViews', route)
    },
    //关闭一个
    closeSelectedTag(view) {
      this.$store.dispatch('delVisitedViews', view).then((views) => {
        if (this.isActive(view)) {
          const latestView = views.slice(-1)[0]
          if (latestView) {
            this.$router.push(latestView)
          } else {
            this.$router.push('/')
          }
        }
      })
    },
    //关闭其他的
    closeOthersTags() {
      this.$router.push(this.selectedTag)
      this.$store.dispatch('delOthersViews', this.selectedTag).then(() => {

        this.addViewTags()
      })
    },
    //关闭所有的
    closeAllTags() {
      this.$store.dispatch('delAllViews')
      this.$router.push('/mainPage')
      this.addViewTags()

    },
    //打开下拉单
    openMenu(tag, e) {

      this.visible = true
      this.selectedTag = tag
      let offsetLeft = this.$el.getBoundingClientRect().left // container margin left
      let offsetTop = this.$el.getBoundingClientRect().top // container margin left
      this.left = e.clientX-offsetLeft  // 15: margin right
      this.top = e.clientY-offsetTop
    },
    closeMenu() {
      this.visible = false
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.tags-view-container {
  position: relative;
  z-index: 2;
  .tags-view-wrapper {
    background: #fff;
    height: 34px;
    border-bottom: 1px solid #d8dce5;
    box-shadow: 0 1px 3px 0 rgba(0, 0, 0, .12), 0 0 3px 0 rgba(0, 0, 0, .04);

    .tags-view-item {
      display: inline-block;
      position: relative;
      height: 26px;
      line-height: 26px;
      border: 1px solid #d8dce5;
      color: #495060;
      background: #fff;
      padding: 0 8px;
      font-size: 12px;
      margin-left: 5px;
      margin-top: 4px;

      &:first-of-type {
        margin-left: 15px;
      }

      &.active {
        background-color: #42b983;
        color: #fff;
        border-color: #42b983;

        &::before {
          content: '';
          background: #fff;
          display: inline-block;
          width: 8px;
          height: 8px;
          border-radius: 50%;
          position: relative;
          margin-right: 2px;
        }
      }
    }
  }

  .contextmenu {
    margin: 0;
    background: #fff;
    z-index: 100;
    position: absolute;
    list-style-type: none;
    padding: 5px 0;
    border-radius: 4px;
    font-size: 12px;
    font-weight: 400;
    color: #333;
    box-shadow: 2px 2px 3px 0 rgba(0, 0, 0, .3);

    li {
      margin: 0;
      padding: 7px 16px;
      cursor: pointer;

      &:hover {
        background: #eee;
      }
    }
  }
}
</style>

<style rel="stylesheet/scss" lang="scss">
//reset element css of el-icon-close
.tags-view-wrapper {
  .tags-view-item {
    .el-icon-close {
      width: 16px;
      height: 16px;
      vertical-align: 2px;
      border-radius: 50%;
      text-align: center;
      transition: all .3s cubic-bezier(.645, .045, .355, 1);
      transform-origin: 100% 50%;

      &:before {
        transform: scale(.6);
        display: inline-block;
        vertical-align: -3px;
      }

      &:hover {
        background-color: #b4bccc;
        color: #fff;
      }
    }
  }
}
</style>
