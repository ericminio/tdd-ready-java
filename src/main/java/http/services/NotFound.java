package http.services;

import http.HttpRequest;
import http.HttpResponse;
import http.services.Endpoint;

public class NotFound implements Endpoint {

    @Override
    public HttpResponse handle(HttpRequest request) {
        HttpResponse response = new HttpResponse();
        response.code = 404;
        response.body = "";
        return response;
    }
}