<template>
  <div class="container">
    <div class="player">
      <video-player
        class="video-player vjs-custom-skin"
        ref="videoPlayer"
        :playsinline="true"
        :options="playerOptions"
        @error="handleVideoError"
      >
      </video-player>
    </div>
  </div>
</template>

<script>
import { videoPlayer } from "vue-video-player";

export default {
  props: {
    // 接收来自父组件的 videoSrc 和是否自动播放的设置
    videoSrc: {
      type: String,
      default: "",
    },
    autoplay: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      playerOptions: {
        playbackRates: [0.7, 1.0, 1.5, 2.0], //播放速度
        autoplay: this.autoplay, // 是否自动播放
        muted: false, // 默认情况下将会消除任何音频
        loop: false, // 导致视频一结束就重新开始
        preload: "auto", // 建议浏览器在<video>加载元素后是否应该开始下载视频数据
        language: "zh-CN",
        fluid: true, // 开启自适应大小
        // aspectRatio: 'auto', // 将播放器置于流畅模式，并在计算播放器的动态大小时使用该值
        fluid: true, // 当true时，Video.js player将拥有流体大小
        sources: [
          {
            type: "video/mp4", // MP4 视频类型
            src: this.videoSrc || "", // 使用传递的 videoSrc
          },
        ],
        // poster: "poster.jpg", // 你的封面地址
        width: document.documentElement.clientWidth,
        notSupportedMessage: "此视频暂无法播放，请稍后再试",
        controlBar: {
          timeDivider: true,
          durationDisplay: true,
          remainingTimeDisplay: false,
          fullscreenToggle: true, //全屏按钮
        },
      },
    };
  },
  components: {
    videoPlayer,
  },
  methods: {
    handleVideoError() {
      // 通知父组件视频加载失败
      this.$emit("video-error");
      this.playerOptions.sources.src = "";
    },
  },
  computed: {
    player() {
      return this.$refs.videoPlayer.player;
    },
  },
  watch: {
    // 当 videoSrc 改变时，更新播放器的源
    videoSrc(newSrc) {
      if (newSrc === "" || newSrc === null) return;
      this.playerOptions.sources[0].src = newSrc;
      this.$nextTick(() => {
        this.player.load(); // 更新播放器并重新加载视频
      });
    },
  },
  mounted() {
    // 覆盖 console.error，避免在控制台输出错误信息
    const originalConsoleError = console.error;
    console.error = function () {}; // 关闭所有 console.error 输出

    // 在视频播放器上监听 error 事件
    this.player.on("error", this.handleVideoError);
  },
  beforeDestroy() {
    // 组件销毁前，移除事件监听
    this.player.off("error", this.handleVideoError);
  },
};
</script>

<style scoped>
.container {
  background-color: #efefef;
  min-height: 100%;
}
</style>
