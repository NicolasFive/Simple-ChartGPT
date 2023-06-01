import {createProxyMiddleware} from 'http-proxy-middleware'

export default defineEventHandler(async (event) => {
    console.log(process.env)
    await new Promise((resolve, reject) => {
        createProxyMiddleware('/server/', {
            target: `http://${process.env.SERVER_IP||'localhost'}:${process.env.SERVER_PORT||'8000'}`,
            pathRewrite:{
                '^/server/' : '/'
            }
        })(event.node.req, event.node.res, (err) => {
            if (err)
                reject(err)
            else
                resolve(true)
        })
    })
})
