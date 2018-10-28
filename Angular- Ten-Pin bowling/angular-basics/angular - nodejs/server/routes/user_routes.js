class JsonData {
    constructor(status, user) {
        this.status = status;
        this.user = user;
    }
}

class CreateUser {
    constructor(userName, password) {
        this.userName = userName;
        this.password = password;
    }
}

module.exports = function(app, db) {
    app.post('/api/user', (req, res) => {
        // console.log('req: ' + req.body.userName);
        // console.log('req: ' + req.body.password);
        createUser = new CreateUser();
        createUser.userName = req.body.userName;
        createUser.password = req.body.password;
        console.log('CreateUser: ' + createUser.userName + ' ' + createUser.password);


        const js1 = new JsonData();
        js1.status = "Created";
        js1.user = createUser.userName;

        res.status(201).send(js1);
    });
};

