package http;

import http.services.Endpoint;
import http.services.NotFound;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class NotFoundTest {

    Endpoint notFound = new NotFound();
    private HttpResponse response;

    @Before
    public void thisEndpoint() throws Exception {
        response = notFound.handle( new HttpRequest() );
    }

    @Test
    public void returns404() {
        assertThat( response.code, equalTo( 404 ) );
    }

    @Test
    public void returnsEmptyBody() {
        assertThat( response.body, equalTo( "" ) );
    }
}
