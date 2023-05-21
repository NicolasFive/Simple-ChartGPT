const fetch = (url,options) => {
    return useFetch(`/server/${url}`, Object.assign({
        onRequest({ request, options }) {
            // Set the request headers
            const auth=getAuthorization()
            if (auth!='null'){
                options.headers = options.headers || {}
                options.headers.Authorization = auth
            }
        },
    },options));
}

export const postJson = (url,body,options) => {
    return fetch(url,Object.assign({
        method: 'post',
        headers: {'Content-Type': 'application/json'},
        body: body,
    },options))
}

export const get = (url,query,options) => {
    return fetch(url,Object.assign({
        method: 'get',
        query: query,
    },options))
}
