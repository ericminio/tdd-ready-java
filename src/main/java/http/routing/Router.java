package http.routing;

import http.services.Endpoint;
import http.HttpRequest;
import http.services.NotFound;

import java.util.Map;

public class Router {

    public static Router routing(Map<HttpRequestMatcher, Endpoint> routes) {
        return new Router( routes );
    }

    private Map<HttpRequestMatcher, Endpoint> routes;

    public Router(Map<HttpRequestMatcher, Endpoint> routes) {
        this.routes = routes;
    }

    public Endpoint firstEndpointMatching(HttpRequest request) {
        if (routes.entrySet().stream()
                .filter(r -> r.getKey().matches(request)).count() == 0) {
            return new NotFound();
        }
        return routes.entrySet().stream()
                .filter(r -> r.getKey().matches(request)).findFirst().get().getValue();
    }
}
