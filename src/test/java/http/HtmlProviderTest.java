package http;

import file.FileContent;
import http.services.HtmlProvider;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class HtmlProviderTest {

    private HttpResponse response;
    private HtmlProvider provider;

    @Before
    public void cssRequest() throws Exception {
        provider = new HtmlProvider("src/test/resources");
        response = provider.handle(new HttpRequest() {{ path = "/some.html"; }});
    }

    @Test
    public void answers() throws Exception {
        assertThat( response.code, equalTo( 200 ) );
    }

    @Test
    public void answersWithHtml() throws Exception {
        assertThat( response.contentType(), containsString( "text/html" ) );
    }

    @Test
    public void answersWithExpectedResponse() throws Exception {
        assertThat( response.body, equalTo( FileContent.of( "src/test/resources/some.html" ) ) );
    }
}
