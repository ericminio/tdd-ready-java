package http.routing;

import http.HttpRequest;

public class PathEqualTo implements HttpRequestMatcher {

    public static HttpRequestMatcher pathEqualTo(String path) {
        return new PathEqualTo(path);
    }

    private String expected;

    public PathEqualTo(String path) {
        this.expected = path;
    }

    @Override
    public boolean matches(HttpRequest request) {
        return request.path.equals(expected);
    }
}