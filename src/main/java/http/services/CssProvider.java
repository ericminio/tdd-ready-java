package http.services;

import file.FileContent;
import http.HttpRequest;
import http.HttpResponse;

public class CssProvider implements Endpoint {

    private String folder;

    public CssProvider(String folder) {
        this.folder = folder;
    }

    @Override
    public HttpResponse handle(HttpRequest request) {
        HttpResponse response = new HttpResponse();
        response.code = 200;
        response.headers.put( "content-type", "text/css" );
        response.body = FileContent.of( this.folder + request.path );

        return response;
    }
}
