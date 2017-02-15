package http.routing;

import http.HttpRequest;

public interface HttpRequestMatcher {

    boolean matches(HttpRequest request);
}
