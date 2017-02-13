var expect = require('chai').expect;
var jsdom = require("jsdom");
var login = require('../../main/resources/login');

describe('login', function() {

    it('turns feedback message into red', function() {
        var document = jsdom.jsdom('<label id="login-feedback"></label>');
        login(document);
        var element = document.getElementById('login-feedback');

        expect(element.className).to.equal('red');
    });
});