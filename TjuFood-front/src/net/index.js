import axios from 'axios'

function post(url, data) {
  return axios
    .post(url, data, {
      headers: {
        'Content-Type': 'application/json'
      },
      withCredentials: true
    })
    .then(({ data }) => {
      if (data.success) return data.message
      throw new Error(data.message)
    })
}

function postForm(url, data) {
  const params = new URLSearchParams()
  Object.keys(data).forEach((k) => params.append(k, data[k]))
  return axios
    .post(url, params, { withCredentials: true })
    .then(({ data }) => {
      if (data.success) return data.message
      throw new Error(data.message)
    })
}

function get(url) {
  return axios
    .get(url, { withCredentials: true })
    .then(({ data }) => {
      if (data.success) return data.message
      throw new Error(data.message)
    })
}

export { get, post, postForm }
