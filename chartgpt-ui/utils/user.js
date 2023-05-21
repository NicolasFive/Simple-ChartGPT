export const setUser = (info) => {
    const user = useUser();
    user.value = info;
};

export const fetchUser = async (forwardTo) => {
    const {error, data} = await get('curUser');
    if (error.value) {
        console.log(error)
        return navigateTo({
            path: '/login',
            query: {
                forwardTo: forwardTo
            }
        })
    } else {
        setUser(data.value.data)
    }
};

export const login = (user) => {
    setAuthorization(null)
    return postJson('login', user, {
        onResponse({request, response, options}) {
            if (response.status == 200) {
                setAuthorization(response._data.data)
            }
        }
    });
};


export const logout = () => {
    const user = useUser()
    user.value = null
    setAuthorization(null)
    return navigateTo('/login');
}
