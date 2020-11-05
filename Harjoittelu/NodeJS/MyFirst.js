var http = require('http');
var date = require('./DateModule');
var fs = require('fs');

http.createServer(function (req, res) {

    if (req.url == "/date") {
    res.writeHead(200, {'Content-Type': 'text/html'});
    res.write("The date and time currently is " + date.myDateTime());
    }

    res.end();
}).listen(8080);