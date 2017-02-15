package http;

import file.FileContent;
import http.services.CssProvider;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class CssProviderTest {

    private HttpResponse response;
    private CssProvider provider;

    @Before
    public void cssRequest() throws Exception {
        provider = new CssProvider("src/test/resources");
        response = provider.handle(new HttpRequest() {{ path = "/some.css"; }});
    }

    @Test
    public void answers() throws Exception {
        assertThat( response.code, equalTo( 200 ) );
    }

    @Test
    public void answersWithCss() throws Exception {
        assertThat( response.contentType(), containsString( "text/css" ) );
    }

    @Test
    public void answersWithExpectedResponse() throws Exception {
        assertThat( response.body, equalTo( FileContent.of( "src/test/resources/some.css" ) ) );
    }
}
