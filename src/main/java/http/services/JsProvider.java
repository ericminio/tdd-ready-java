package http.services;

import file.FileContent;
import http.HttpRequest;
import http.HttpResponse;

public class JsProvider implements Endpoint {

    private String folder;

    public JsProvider(String folder) {
        this.folder = folder;
    }

    @Override
    public HttpResponse handle(HttpRequest request) {
        HttpResponse response = new HttpResponse();
        response.code = 200;
        response.headers.put( "content-type", "application/javascript" );
        response.body = FileContent.of( this.folder + request.path );

        return response;
    }
}
