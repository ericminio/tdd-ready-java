package run;

import file.FileContent;
import http.services.Endpoint;
import http.HttpRequest;
import http.HttpResponse;
import http.routing.Router;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class LoginRoutesTest {

    @Test
    public void hasARouteForHome() throws Exception {
        Router router = Router.routing(new LoginRoutes());
        HttpRequest request = new HttpRequest() {{ path="/"; }};
        Endpoint endpoint = router.firstEndpointMatching(request);
        HttpResponse response = endpoint.handle(request);

        assertThat(response.body, equalTo(FileContent.of("src/main/resources/index.html")));
    }
}
