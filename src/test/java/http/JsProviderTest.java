package http;

import file.FileContent;
import http.services.JsProvider;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class JsProviderTest {

    private HttpResponse response;
    private JsProvider provider;

    @Before
    public void cssRequest() throws Exception {
        provider = new JsProvider("src/test/resources");
        response = provider.handle(new HttpRequest() {{ path = "/some.js"; }});
    }

    @Test
    public void answers() throws Exception {
        assertThat( response.code, equalTo( 200 ) );
    }

    @Test
    public void answersWithJs() throws Exception {
        assertThat( response.contentType(), containsString( "application/javascript" ) );
    }

    @Test
    public void answersWithExpectedResponse() throws Exception {
        assertThat( response.body, equalTo( FileContent.of( "src/test/resources/some.js" ) ) );
    }
}
