<!-- 页面加载进度 -->

<script>
import Vue from "vue";

const instance = new Vue();
let isLoading = false;
let lastLoadingTimerId = 0;

export default {
  start(force) {
    if (!isLoading || force) {
      instance.$Loading.start();
      isLoading = true;

      if (lastLoadingTimerId) {
        clearTimeout(lastLoadingTimerId);
      }

      lastLoadingTimerId = setTimeout(() => {
        lastLoadingTimerId = 0;
        instance.$Loading.finish();
        isLoading = false;
      }, 5000);
    }
  },

  finish() {
    if (isLoading) {
      instance.$Loading.finish();
      isLoading = false;
    }

    if (lastLoadingTimerId) {
      clearTimeout(lastLoadingTimerId);
      lastLoadingTimerId = 0;
    }
  },

  error() {
    if (isLoading) {
      instance.$Loading.error();
      isLoading = false;
    }

    if (lastLoadingTimerId) {
      clearTimeout(lastLoadingTimerId);
      lastLoadingTimerId = 0;
    }
  },

  percent(value) {
    instance.$Loading.update(value);
  }
};
</script>
