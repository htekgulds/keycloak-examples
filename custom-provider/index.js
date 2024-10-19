const express = require('express')
const app = express()
const port = process.env.PORT || 4000

app.get('/auth/action', (req, res) => {
  const token = decodeURI(req.query.token)
  const replacedToken = token.replace('{APP_TOKEN}', 'mytoken')
  console.log('token', token)
  res.redirect(replacedToken)
})

app.get('/auth/rest', (req, res) => {
  const code = req.query.code
  console.log('code', code)
  if (code !== '123456') return res.status(401).send('no user')
  res.json({ username: 'hasan' })
})

app.listen(port, () => {
  console.log('Listening on port: ' + port)
})