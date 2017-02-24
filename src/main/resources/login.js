var login = function(window, done) {
    var document = window.document;
    var user = document.getElementById('user-field').value;
    var password = document.getElementById('password-field').value;

    var port = window.location.port || 5000;
    var hostname = window.location.hostname || 'localhost';
    var url = "http://"+ hostname +":"+ port +"/login";
    url += '?user=' + user;
    url += '&password=' + sha256(password);

    var xhr = new window.XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == xhr.DONE) {
            if (xhr.status == 200) {
                done({ enjoy:true, data:JSON.parse(xhr.responseText) });
            }
            else {
                var feedback = document.getElementById('login-feedback');
                feedback.className = 'red';
                done({ enjoy:false });
            }
        }
    };
    xhr.open("GET", url, true);
    xhr.send();
};

var module = module || {}
module.exports = login;