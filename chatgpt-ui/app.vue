<template>
  <NuxtLayout>
    <NuxtLoadingIndicator />
    <NuxtErrorBoundary @error="showError">
      <NuxtPage />
      <template #error>
        <NuxtPage />
      </template>
    </NuxtErrorBoundary>
    <v-snackbar v-model="error.visibility" rounded="pill" location="top" color="red-lighten-2" :timeout="5000">
      {{ error.raw.message }}
      <template #actions>
        <v-btn color="blue" variant="text" @click="handleError">
          X
        </v-btn>
      </template>
    </v-snackbar>
  </NuxtLayout>
</template>
<script setup>
const error = reactive({ raw: ref({}), visibility: ref(false) })
const showError = (rawError) => {
  console.log(error)
  error.raw = rawError
  error.visibility = true
}
const handleError = () => {
  clearError()
  error.visibility = false
}
</script>
<style>
.page-enter-active,
.page-leave-active,
.layout-enter-active,
.layout-leave-active {
  transition: all 0.4s;
}
.page-enter-from,
.page-leave-to,
.layout-enter-from,
.layout-leave-to {
  opacity: 0;
  filter: blur(1rem);
}
</style>
