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
 const dir = app.use('/', express.static(path.join(__dirname, '../local-app-frontend/dist')));

let counter = 1;
/**node stuff to uplodad file */

/** var http = require('http'); */


/**app.get('/', function(req, res){                                        /**send index page */
   /**res.sendFile('index2.html');
});*/


app.get('/api/name/:fname/:lname', function(req, res) {             /** receive parameterized get req */
    const firstName = req.params['fname'];
    const lastName = req.params['lname'];
    console.log(firstName + ' ' + lastName);
    res.send([{ name: firstName }, { name: lastName }]);
})

app.get('/api/login', function(req, res) {                              /** get req for login -> send login page */
   // res.sendFile(path.join(__dirname, '/_public', 'login.html'));
});

app.post('/api/login', function(req, res) {                             /** receive login req */
    const uName = req.body.user.name;
    const pass = req.body.user.password;
    console.log('login: ' + uName + ' ' + pass);
    if (uName === userName && pass === password) {
        res.status(201).send(userName + " Logged in");
    } else {
        res.status(401).send(userName + " not found");                  /** TODO: look for the correct status code */
    }

});

app.get('/api/update', function(req, res) {
   res.sendFile(path.join(__dirname, '/_public', 'update.html'));
});

app.put('/api/update', function(req, res) {
    console.log('old credentials: ' + userName + ' ' + password );
    userName = req.body.user.name;
    password = req.body.user.password;
    console.log('new credentials: ' + userName + ' ' + password );
    res.status(200).send(userName + ' updated');
});

app.post('/api/uploadFile', function (req, res){
    let fileName = '';

    var form = new formidable.IncomingForm();
    form.encoding = 'utf-8';

    form.parse(req);

    form.on('fileBegin', function (name, file){
        file.path = __dirname + '/_public/' + file.name + '-' + counter;
        fileName = file.name + '-' + counter;
        console.log(fileName);
    });

    /**form.on('field', function(name, value) {
        console.log(name + ' ' + value);
    });
     */
    form.on('end', function() {
        const fs = require('fs');
        fs.readFile(__dirname + '/_public/' + fileName, 'utf-8', (err, data) => {
            if (err) console.log('error in fs.readFile(): ' + err);
            try {
                const jsonData = JSON.parse(data);
                for (let d of jsonData) {
                    console.log(d.name);
                }
            } catch(e) {
                console.log('Can not show values, File not properly formated into JSON');
            }
            // console.log(jsonData[0].name);
            // console.log(jsonData[1].name);
        });

        counter++;


        let post_data = new FileUpload_Response();
        post_data.status = "created";
        post_data.fileName = fileName;
        res.status(201).send(post_data);
    });

    form.on('error', function(err) {
        consoloe.log('error getting file');
        let post_data = new FileUpload_Response();
        post_data.status = "can not create";
        post_data.file = fileName;
        res.status(201).send(post_data);
    });

    /**

     form.on('file', function (name, file){
        console.log('Uploaded ' + file.name);
    });

    form.on('aborted', function() {
    });

     */



});

app.listen(port, () => console.log(`Server listening on port ${port}!`))


/** Response objects that are easy to map to client side objects */

class FileUpload_Response {
    constructor(status, file) {
        this.status = status;
        this.file = file;
    }
}

