var expect = require('chai').expect;
var jsdom = require("jsdom");
var login = require('../../main/resources/login');
sha1 = require('../../main/resources/sha1.min');

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
                expect(sent).to.equal('/login?user=John&password=c947ad320e66fc64998e86a55c0da210c8c1d81a');
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