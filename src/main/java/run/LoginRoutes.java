package run;

import file.FileContent;
import http.HttpResponse;
import http.routing.HttpRequestMatcher;
import http.services.*;

import java.util.HashMap;

import static http.routing.PathEndingWith.pathEndingWith;
import static http.routing.PathEqualTo.pathEqualTo;

public class LoginRoutes extends HashMap<HttpRequestMatcher, Endpoint> {

    private static final String WEBROOT = "build/resources/main/";

    public LoginRoutes() {
        put( pathEndingWith( ".css" ), new CssProvider(WEBROOT) );
        put( pathEndingWith( ".js" ), new JsProvider(WEBROOT) );
        put( pathEndingWith( ".html" ), new HtmlProvider(WEBROOT) );
        put( pathEqualTo("/login"), new Login() );
        put( pathEqualTo("/"), request -> {
            HttpResponse response = new HttpResponse();
            response.code = 200;
            response.headers.put( "content-type", "text/html" );
            response.body = FileContent.of( WEBROOT + "/index.html" );

            return response;
        });
    }

}