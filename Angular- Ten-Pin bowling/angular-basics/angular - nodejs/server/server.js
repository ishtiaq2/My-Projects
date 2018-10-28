const express = require('express');
const app = express();
const path = require('path');
const port = 8000;
const formidable = require('formidable');

const bodyParser = require('body-parser');

app.use(bodyParser.urlencoded({
    extended: true
}));

app.use(bodyParser.json());
const dir = app.use('/', express.static(path.join(__dirname, '../fileUpload/dist/fileUpload')));

require('./routes')(app, {});
app.listen(port, () => console.log(`Server listening on port ${port}! ` + __dirname));