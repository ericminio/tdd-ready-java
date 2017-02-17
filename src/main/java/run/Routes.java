package run;

import file.FileContent;
import http.HttpResponse;
import http.routing.HttpRequestMatcher;
import http.services.*;

import java.util.HashMap;

import static http.routing.PathEndingWith.withPathEndingWith;
import static http.routing.PathEqualTo.withPathEqualTo;

public class Routes extends HashMap<HttpRequestMatcher, Endpoint> {

    private static final String WEBROOT = "build/resources/main/";

    public Routes() {
        put( withPathEndingWith( ".css" ), new CssProvider(WEBROOT) );
        put( withPathEndingWith( ".js" ), new JsProvider(WEBROOT) );
        put( withPathEndingWith( ".html" ), new HtmlProvider(WEBROOT) );
        put( withPathEqualTo("/login"), new Login() );
        put( withPathEqualTo("/"), request -> {
            HttpResponse response = new HttpResponse();
            response.code = 200;
            response.headers.put( "content-type", "text/html" );
            response.body = FileContent.of( WEBROOT + "/index.html" );

            return response;
        });
    }

}