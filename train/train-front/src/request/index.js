import axios from 'axios'
import qs from 'querystring'
import {message} from "antd";

const req = axios.create({
  baseURL: 'http://skyemperor.top:6066/train',
  // baseURL: 'http://127.0.0.1:6067/train',
  timeout: 5000
})

const null_function = () => {
}

export const req_get = (url, data, callback = null_function) => {
  req.get(url,
    {
      params: data,
      headers: {token: window.sessionStorage.getItem('TOKEN')}
    }
  ).then(r => {
    r = r.data
    if (r.code === 0) {
      callback(r.data)
      return r.data
    } else {
      message.warn(r.message)
    }
  })
}

export const req_post_json = (url, data, callback = null_function) => {
  req.post(url, data,
    {
      headers: {token: window.sessionStorage.getItem('TOKEN')}
    }).then(r => {
    r = r.data
    if (r.code === 0) {
      callback(r.data)
      return r.data
    } else {
      message.warn(r.message)
    }
  })
}

export const req_post_form = (url, data, callback = null_function) => {
  req.post(url, qs.stringify(data),
    {
      headers: {token: window.sessionStorage.getItem('TOKEN')}
    }).then(r => {
    r = r.data
    if (r.code === 0) {
      callback(r.data)
      return r.data
    } else {
      message.warn(r.message)
    }
  })
}


