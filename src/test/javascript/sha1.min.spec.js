var expect = require('chai').expect;
var sha1 = require('../../main/resources/sha1.min');

describe('sha1', function() {

    it('exposes sha1 hash function', function() {
        expect(sha1('Doe')).to.equal('c947ad320e66fc64998e86a55c0da210c8c1d81a');
    });
    it('hashes tecuser as expected', function() {
        expect(sha1('tecuser')).to.equal('e276f2f92e1a5a85c6695895d51943530bd0edd6');
    });
    it('hashes user as expected', function() {
        expect(sha1('user')).to.equal('12dea96fec20593566ab75692c9949596833adc9');
    });
});