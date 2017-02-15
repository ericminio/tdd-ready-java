package run;

import file.FileContent;
import http.*;
import http.routing.HttpRequestMatcher;
import http.services.CssProvider;
import http.services.Endpoint;
import http.services.HtmlProvider;
import http.services.JsProvider;

import java.util.HashMap;

import static http.routing.PathEndingWith.withPathEndingWith;
import static http.routing.PathEqualTo.withPathEqualTo;

public class Routes extends HashMap<HttpRequestMatcher, Endpoint> {

    private static final String WEBROOT = "build/resources/main/";

    public Routes() {
        put( withPathEndingWith( ".css" ), new CssProvider(WEBROOT) );
        put( withPathEndingWith( ".js" ), new JsProvider(WEBROOT) );
        put( withPathEndingWith( ".html" ), new HtmlProvider(WEBROOT) );
        put( withPathEqualTo("/"), request -> {
            HttpResponse response = new HttpResponse();
            response.code = 200;
            response.headers.put( "content-type", "text/html" );
            response.body = FileContent.of( WEBROOT + "/index.html" );

            return response;
        });
        put( withPathEqualTo("/login"), request -> {
            HttpResponse response = new HttpResponse();
            response.headers.put( "content-type", "application/json" );
            if (request.query.contains("unknown")) {
                response.code = 401;
            }
            else {
                response.code = 200;
            }
            response.body = "{}";

            return response;
        });
    }

}