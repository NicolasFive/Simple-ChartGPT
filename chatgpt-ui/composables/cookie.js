import Cookies from 'js-cookie'

export const getCookie=(key)=>{
    return Cookies.get(key)
}

export const setCookie=(key,val)=>{
    return Cookies.set(key,val)
}

export const getAuthorization=()=>{
    return Cookies.get('Authorization')
}

export const setAuthorization=(val)=>{
    return Cookies.set('Authorization',val)
}
