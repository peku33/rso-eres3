const gzippo = require ('gzippo'),
      express = require('express'),
      morgan = require('morgan')

let app = express()

app.use(morgan('combined'))
app.use(gzippo.staticGzip(`${__dirname}/dist`))

app.get('/cms-status', (req, res) =>
  res.sendStatus(200)
)

app.get('*', (req, res) =>
  res.sendfile(`${__dirname}/dist/index.html`)
)

app.use(express.static('public')).listen(9000, "0.0.0.0")
