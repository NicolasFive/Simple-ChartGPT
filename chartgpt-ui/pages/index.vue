<template>
  <v-layout>
    <v-navigation-drawer color="grey-darken-2" permanent>
      <v-container>
        <v-row>
          <v-btn variant="flat" prepend-icon="mdi-plus" block rounded color="white" @click="addChart">
            New Chart
          </v-btn>
        </v-row>
        <v-row v-for="it in charts" :key="it.id">
          <v-card class="card-body" prepend-icon="mdi-file" hover rounded>
            <template v-slot:title>
              <div class="card-text">
                {{ it.messages.length>0?it.messages[0].content:"A New Chart" }}
                <v-btn-toggle divided class="card-btn">
                  <v-btn density="compact" icon="mdi-pencil" size="small" :active="false"
                         @click="selectChart(it)"></v-btn>
                  <v-btn density="compact" icon="mdi-delete" size="small" @click="delChart(it)"></v-btn>
                </v-btn-toggle>
              </div>
            </template>
          </v-card>
        </v-row>
        <v-row class="user-config">
          <v-menu>
            <template v-slot:activator="{ props }">
              <v-btn variant="flat" append-icon="mdi-account-cog-outline" block rounded color="white" v-bind="props">
                <div v-if="user">{{ user.email }}</div>
              </v-btn>
            </template>
            <v-list>
              <v-list-item @click="">
                <Setting/>
                <template #prepend>
                  <v-icon icon="mdi-cog"/>
                </template>
                Setting
              </v-list-item>
              <v-list-item @click="logout">
                <template #prepend>
                  <v-icon icon="mdi-logout"/>
                </template>
                Logout
              </v-list-item>
            </v-list>
          </v-menu>
        </v-row>
      </v-container>
    </v-navigation-drawer>
    <v-app-bar elevation="0" :order="0" height="40" flat></v-app-bar>
    <v-main>
      <v-container class="messages" v-mutate.child="onMutate">
        <template v-if="curChart">
          <Message v-for="it in curChart.messages" :data="it" :key="it.content"></Message>
        </template>
      </v-container>
    </v-main>
    <v-app-bar location="bottom" :order="0" height="200" flat>
      <v-container>
        <v-row no-gutters>
          <v-col align-self="center" cols="10">
            <v-textarea :loading="send.loading" density="compact" variant="solo" type="text"
                          label="Please input message"
                          rows="1"
                          auto-grow
                          max-rows="5"
                          hint="Send message what you want to ask ChartGPT,for example 'Show me a maze code of Java.'"
                          clearable v-model="send.content"
                          :append-inner-icon="send.loading ? 'mdi-send-lock' : 'mdi-send'"
                          @keydown.enter.prevent="onSend($event,$vuetify)"
                          @click:append-inner.prevent="onSend($event,$vuetify)">
              <template #prepend>
                <v-select
                    class="role-selection"
                    v-model="send.role"
                    :prepend-inner-icon="RoleIcon[send.role]"
                    variant="solo"
                    density="compact"
                    rounded
                    :items="['user','system']">
                  <template #selection="{ item, index }">
                  </template>
                </v-select>
              </template>
            </v-textarea>
          </v-col>
        </v-row>
      </v-container>
    </v-app-bar>
  </v-layout>
</template>
<script setup>
definePageMeta({
  middleware: ['auth']
})
const send = reactive({loading: ref(false), content: ref(''), role: ref('user')})
var charts = ref([])
var curChart = ref({messages: []})
var cur = ref(-1)
var user = ref({})


const onSend = async (event) => {
  if (event.ctrlKey || event.shiftKey || event.altKey) {
    send.content += '\n';
    return
  }
  if (!send.loading) {
    send.loading = true
    try {
      const msg = {
        content: send.content,
        role: send.role
      }
      addMessage(msg)
      send.content=""
      element.scrollIntoView({behavior: "smooth"});
      return
      const {data, error} = await sendMessage(Object.assign(useSettings().value,{
        "messages": JSON.parse(JSON.stringify(curChart.value.messages))
      }))
      if (error.value) {
        throw new Error("Something Failed.")
      } else {
        try {
          const {choices} = data.value.data
          choices.forEach(o => addMessage(o.message))
        } catch (e) {
          throw new Error(data.value.data.error.message)
        }
      }
    } finally {
      send.loading = false
    }
  }
}

const onMutate= (records)=>{
  if (records[0].addedNodes[0])
  records[0].addedNodes[0].scrollIntoView({behavior: "smooth"});
}

onMounted(() => {
  charts = getCharts()
  cur = getCur()
  curChart = computed(() => charts.value[cur.value])
  user = useUser()
  watch([charts, cur], () => {
    saveCharts()
  }, {deep: true})
})
</script>
<style scoped lang="less">
.card-body {
  margin: 0.5rem;
  width: 100%;
}

.card-btn {
  position: absolute;
  right: 0.5rem;
  top: 0.5rem;
}

.card-text {
  font-size: 1rem;
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
  padding-right: 3rem;
}

.role-selection {
  width: 80px;
  margin: 0 10px;
}

.messages {
  padding: 0;

  .message-line:nth-child(odd) {
    background-color: #ddd;
  }

  .message-line:nth-child(even) {
    background-color: #ffffff;
  }
}
.user-config{
  position: absolute;
  width: 100%;
  bottom: 1rem;
  padding: 0.5rem 0.5rem 0.5rem 0rem;
}
</style>
