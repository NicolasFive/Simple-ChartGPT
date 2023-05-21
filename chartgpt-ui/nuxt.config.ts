// https://nuxt.com/docs/api/configuration/nuxt-config
import { md1 } from 'vuetify/blueprints'
import dotenv from 'dotenv'
dotenv.config()
export default defineNuxtConfig({
    modules: [
        '@invictus.codes/nuxt-vuetify',
        '@dargmuesli/nuxt-cookie-control'
    ],
    vuetify: {
        vuetifyOptions: {
            // @TODO: list all vuetify options
            blueprint: md1
        },
        moduleOptions: {
            /* nuxt-vuetify module options */
            treeshaking: true
        }
    },
    app: {
        layoutTransition: { name: 'layout', mode: 'out-in' },
        pageTransition: { name: 'page', mode: 'out-in' }
    }
})
