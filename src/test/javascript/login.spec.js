var expect = require('chai').expect;
var jsdom = require("jsdom");
login = (new Function (require('fs').readFileSync('src/main/resources/login.js') + 'return login;'))();
sha256 = (new Function (require('fs').readFileSync('src/main/resources/sha256.min.js') + 'return sha256;'))();

describe('login', function() {

    var page = '<html>'
        + '<label id="login-feedback"></label>'
        + '<input id="user-field" value="John" />'
        + '<input id="password-field" value="Doe" />'
        + '<html>';

    var server;
    var sent;
    var serverAnswer;

    beforeEach(function(done) {
        server = require('http').createServer(function(request, response) {
            sent = request.url;
            serverAnswer(response);
        }).listen(5000, done);
    });
    afterEach(function() {
        server.close();
    });

    it('sends user and hashed password', function(exit) {
        serverAnswer = function(response) {
            response.setHeader('Content-Type', 'text/plain');
            response.setHeader('Access-Control-Allow-Origin', '*');
            response.end(JSON.stringify({ any:'thing' }));
        };
        var document = jsdom.jsdom(page);
        login(document.defaultView, function(data) {
            try {
                expect(sent).to.equal('/login?user=John&password=fd53ef835b15485572a6e82cf470dcb41fd218ae5751ab7531c956a2a6bcd3c7');
                exit();
            }
            catch (error) { exit(error); }
        });
    });

    describe('when 401', function() {

        var document;
        var answer;

        beforeEach(function(done) {
            serverAnswer = function(response) {
                response.setHeader('Content-Type', 'text/plain');
                response.setHeader('Access-Control-Allow-Origin', '*');
                response.statusCode = 401;
                response.end();
            };
            document = jsdom.jsdom(page);
            login(document.defaultView, function(data) {
                answer = data;
                done();
            });
        });

        it('turns feedback message into red', function() {
            var element = document.getElementById('login-feedback');
            expect(element.className).to.equal('red');
        });
        it('informs enjoy not to proceed', function() {
            expect(answer.enjoy).to.equal(false);
        });
    });

    describe('when 200', function() {

        var document;
        var answer;

        beforeEach(function(done) {
            serverAnswer = function(response) {
                response.setHeader('Content-Type', 'text/plain');
                response.setHeader('Access-Control-Allow-Origin', '*');
                response.end(JSON.stringify({ any:'thing' }));
            };
            document = jsdom.jsdom(page);
            login(document.defaultView, function(data) {
                answer = data;
                done();
            });
        });

        it('does not modify feedback message', function() {
            var element = document.getElementById('login-feedback');
            expect(element.className).to.equal('');
        });
        it('informs enjoy to proceed', function() {
            expect(answer.enjoy).to.equal(true);
        });
        it('forwards data to enjoy', function() {
            expect(answer.data).to.deep.equal({ any:'thing' });
        });
   });
});