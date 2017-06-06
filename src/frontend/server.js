const gzippo = require ('gzippo'),
      express = require('express')
//      ,morgan = require('morgan')  // windows only??

let app = express()

// app.use(morgan('combined')) // windows only??
app.use(gzippo.staticGzip(`${__dirname}/dist`))

app.get('/cms-status', (req, res) =>
  res.sendStatus(200)
)

app.get('*', (req, res) =>
  res.sendfile(`${__dirname}/dist/index.html`)
)

app.listen(9000)
