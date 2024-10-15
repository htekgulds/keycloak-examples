const express = require('express')
const app = express()
const port = process.env.PORT || 4000

app.post('/api/auth/scode', (req, res) => {
  res.send("hasan")
})

app.listen(port, () => {
  console.log('Listening on port: ' + port)
})