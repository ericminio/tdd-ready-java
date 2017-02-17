package http.services;

import http.HttpRequest;
import http.HttpResponse;

public interface Endpoint {

    HttpResponse handle(HttpRequest request) throws Exception;
}
