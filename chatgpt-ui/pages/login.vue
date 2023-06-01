<template>
  <v-container>
    <v-row justify="center">
      <v-card class="card-body">
        <v-card-title class="card-title">
          Login
        </v-card-title>
        <v-card-text>
          <v-form ref="form" @submit.prevent="onSubmit">
            <v-text-field v-show="type == 'phone'" v-model="phone.value.value" :counter="50"
                          label="Phone Number." :error-messages="phone.errorMessage.value" append-icon="mdi-email"
                          @click:append="type = 'email'"/>
            <v-text-field v-show="type == 'email'" v-model="email.value.value" :counter="100"
                          label="Email Address." :error-messages="email.errorMessage.value" append-icon="mdi-phone"
                          @click:append="type = 'phone'"/>
            <v-text-field v-model="password.value.value" :append-icon="showPwd ? 'mdi-eye' : 'mdi-eye-off'"
                          :type="showPwd ? 'text' : 'password'" label="Your Password." :counter="50"
                          @click:append="showPwd = !showPwd" :error-messages="password.errorMessage.value"/>

            <v-checkbox v-model="rememberMe" label="Remember Me.">
              <template #append>
                <NuxtLink to="/register">
                  <v-btn variant="text" size="small" :ripple="false" :active="false">register
                    now
                  </v-btn>
                </NuxtLink>
              </template>
            </v-checkbox>
            <v-btn color="success" block type="submit">
              SignIn
            </v-btn>
          </v-form>
        </v-card-text>
      </v-card>
    </v-row>
  </v-container>
</template>
<script setup>
import {useField, useForm} from 'vee-validate'

const form = ref(null)
const type = ref('phone')//phone,email
const {handleSubmit, handleReset} = useForm({
  validationSchema: {
    phone(value) {
      if (type.value != 'phone' || (value?.length > 0)) return true
      return 'Must input phone number.'
    },
    email(value) {
      if (type.value != 'email' || (/^[a-z.-]+@[a-z.-]+\.[a-z]+$/i.test(value))||(/^admin$/i.test(value))) return true
      return 'Must be a valid e-mail.'
    },
    password(value) {
      if (value?.length >= 6) return true
      return 'Password needs to be at least 6 digits.'
    },
  },
})
const phone = useField('phone')
const email = useField('email')
const password = useField('password')
const showPwd = ref(false)
const rememberMe = ref(false)
const onSubmit = handleSubmit (async values => {
  const {error}= await login(values)
  if (error.value){
    throw new Error("Login Failed.")
  }else{
    const router = useRouter()
    const route = useRoute()
    const forwardTo = route.query.forwardTo
    router.push({path: forwardTo ? forwardTo : '/'})
  }
})
onMounted(()=>{
  watch(type,()=>{
    form.value.reset()
  })
})
const cookieControl = useCookieControl()
// console.log(cookieControl.cookiesEnabledIds.value)
</script>
<style scoped>
.card-body {
  width: 400px;
  margin-top: 20%;
}

.card-title {
  text-align: center;
}
</style>
