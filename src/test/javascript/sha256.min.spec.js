var expect = require('chai').expect;
var fs = require('fs'), path = require('path');
var body = require('fs').readFileSync(path.join(__dirname, '../../main/resources/sha256.min.js')) + 'return sha256;';
var sha = (new Function (body))();

describe('sha1', function() {

    it('exposes sha1 hash function', function() {
        expect(sha('Doe')).to.equal('fd53ef835b15485572a6e82cf470dcb41fd218ae5751ab7531c956a2a6bcd3c7');
    });
    it('hashes tecuser as expected', function() {
        expect(sha('tecuser')).to.equal('9f3da7ab6093d9d8d74c3979ccadfc7b11b37f630a932ad8d522d836edc1f11e');
    });
    it('hashes user as expected', function() {
        expect(sha('user')).to.equal('04f8996da763b7a969b1028ee3007569eaf3a635486ddab211d512c85b9df8fb');
    });
});