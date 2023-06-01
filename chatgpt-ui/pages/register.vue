<template>
  <v-container>
    <v-row justify="center">
      <v-card class="card-body">
        <v-card-title class="card-title">
          Create Your Account
        </v-card-title>
        <v-card-text>
          <v-form ref="form" @submit.prevent="onSubmit">
            <v-text-field v-show="type == 'phone'" v-model="phone.value.value" :counter="50"
                          label="Phone Number." :error-messages="phone.errorMessage.value" append-icon="mdi-email"
                          @click:append="type = 'email'"/>
            <v-text-field v-show="type == 'email'" v-model="email.value.value" :counter="100"
                          label="Email Address." :error-messages="email.errorMessage.value" append-icon="mdi-phone"
                          @click:append="type = 'phone'"/>
            <v-text-field v-model="newPassword.value.value"
                          :append-icon="showNewPwd ? 'mdi-eye' : 'mdi-eye-off'" :type="showNewPwd ? 'text' : 'password'"
                          label="Your Password." :counter="50" @click:append="showNewPwd = !showNewPwd"
                          :error-messages="newPassword.errorMessage.value"/>
            <v-text-field v-model="retypePwd.value.value"
                          :append-icon="showRetypePwd ? 'mdi-eye' : 'mdi-eye-off'"
                          :type="showRetypePwd ? 'text' : 'password'" label="Retype Password." :counter="50"
                          @click:append="showRetypePwd = !showRetypePwd"
                          :error-messages="retypePwd.errorMessage.value"/>
            <div class="to-login">
              <NuxtLink to="/login">
                <v-btn variant="text" size="small" :ripple="false" :active="false">SignIn
                  now
                </v-btn>
              </NuxtLink>
            </div>
            <v-btn color="success" block type="submit">
              Join Us
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
      if (type.value != 'email' || (/^[a-z.-]+@[a-z.-]+\.[a-z]+$/i.test(value))) return true
      return 'Must be a valid e-mail.'
    },
    newPassword(value) {
      if (value?.length >= 6) return true
      return 'Password needs to be at least 6 digits.'
    },
    retypePwd(value) {
      if (value?.length >= 6&&value==newPassword.value.value) return true
      return 'The retype password and the new password do not match!'
    },
  },
})
const phone = useField('phone')
const email = useField('email')
const newPassword = useField('newPassword')
const retypePwd = useField('retypePwd')
const showNewPwd = ref(false)
const showRetypePwd = ref(false)
const onSubmit = handleSubmit(values => {
  alert(JSON.stringify(values, null, 2))
})
</script>
<style scoped>
.card-body {
  width: 400px;
  margin-top: 20%;
}

.card-title {
  text-align: center;
}

.to-login {
  text-align: right;
  margin: 0 0 1rem 0;
}
</style>
