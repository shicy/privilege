<!-- 主页面 -->

<template>
  <div class="m-index"></div>
</template>

<script>
export default {
  mounted() {
    if (this.$route.name == "Index") {
      this.$router.push("/tenant");
    }
  }
};
</script>
