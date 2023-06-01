<template>
    <v-row class="message-line">
      <v-col cols="1">
        <v-icon class="role-icon" :icon="RoleIcon[data.role]"/>
      </v-col>
      <v-col cols="10">
        <div v-html="contentHtml"></div>
      </v-col>
      <v-col cols="1">
        <v-btn density="compact" variant="text" icon="mdi-close-circle-outline" size="small" @click="delMessage(props.data)"></v-btn>
        <v-btn density="compact" variant="text" icon="mdi-content-copy" size="small" @click="onCopy"></v-btn>
      </v-col>
      <v-snackbar v-model="success.visibility" rounded="pill" location="top" color="success" timeout="3000">
        {{ success.message }}
      </v-snackbar>
    </v-row>
</template>
<script setup>
import MarkdownIt from 'markdown-it'
import copy from 'copy-to-clipboard'
import mathjax3 from 'markdown-it-mathjax3'
const props = defineProps({
  data: {
    type: Object,
    required: true
  }
})

const success=reactive({visibility:ref(false),message:ref("")})

const onCopy=()=>{
  copy(props.data.content)
  success.message="Copy succeed."
  success.visibility=true
}
const contentHtml=ref('')
const md = new MarkdownIt({
  linkify: true,
})
md.use(mathjax3)
contentHtml.value=md.render(props.data.content)

</script>
<style scoped lang="less">
.role-icon{
  font-size: 2rem;
  &.mdi-robot-excited-outline{
    color: #025cee;
  }
  &.mdi-account{
    color: #bb00ff;
  }
  &.mdi-head-question-outline{
    color: #ff94a1;
  }
}
.message-line{
  border-bottom: 1px solid #cbcbcb;
  padding: 2rem 3rem;
}
</style>
