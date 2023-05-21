<template>
  <v-dialog v-model="visibility" persistent width="500" activator="parent">
    <v-card>
      <v-card-text>
        <v-form>
          <v-text-field v-model="settings.model" :counter="50" label="Select A Model."/>
          <div class="form-label">Max Tokens</div>
          <v-slider
              v-model="settings.max_tokens"
              max="1000"
              min="100"
              step="10"
              thumb-label="always"></v-slider>
          <div class="form-label">Temperature</div>
          <v-slider
              v-model="settings.temperature"
              max="2"
              min="0"
              step="0.1"
              thumb-label="always"></v-slider>
          <div class="form-label">top_p</div>
          <v-slider
              v-model="settings.top_p"
              max="1"
              min="0"
              step="0.1"
              thumb-label="always"></v-slider>
          <div class="form-label">n</div>
          <v-slider
              v-model="settings.n"
              max="2"
              min="1"
              step="1"
              thumb-label="always"></v-slider>
          <div class="form-label">presence_penalty</div>
          <v-slider
              v-model="settings.presence_penalty"
              max="2"
              min="-2"
              step="0.1"
              thumb-label="always"></v-slider>
          <div class="form-label">frequency_penalty</div>
          <v-slider
              v-model="settings.frequency_penalty"
              max="2"
              min="-2"
              step="0.1"
              thumb-label="always"></v-slider>
        </v-form>
      </v-card-text>
      <v-card-actions>
        <v-btn color="primary" block @click="visibility = false">Close</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>
<script setup>
const settings=useSettings()
watch(settings,()=>{
  saveSettings()
},{deep:true})
let visibility = ref(false)
</script>
<style scoped>
.form-label{
  font-size: 0.5rem;
}
</style>
