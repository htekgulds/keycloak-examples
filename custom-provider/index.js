const express = require('express')
const app = express()
const port = process.env.PORT || 4000

app.post('/api/auth/scode', (req, res) => {
  res.send("hasan")
})

app.get('/auth/action', (req, res) => {
  const token = decodeURI(req.query.token)
  const replacedToken = token.replace('{APP_TOKEN}', 'mytoken')
  console.log('params', token)
  res.redirect(replacedToken)
})

app.listen(port, () => {
  console.log('Listening on port: ' + port)
})