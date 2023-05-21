export default defineNuxtRouteMiddleware((to, from) => {
    const user = useUser()
    const loginPath = '/login'
    const registerPath = '/register'
    if (!user.value && to.path !== loginPath&& to.path !== registerPath) {
        fetchUser(to.fullPath)
    }
})
