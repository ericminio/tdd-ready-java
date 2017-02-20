package http.routing;

import http.HttpRequest;

public class PathEndingWith implements HttpRequestMatcher {

    public static HttpRequestMatcher pathEndingWith(String path) {
        return new PathEndingWith(path);
    }

    private String expected;

    public PathEndingWith(String path) {
        this.expected = path;
    }

    @Override
    public boolean matches(HttpRequest request) {
        return request.path.endsWith( expected );
    }
}
