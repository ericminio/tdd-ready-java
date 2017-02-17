package http;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class HttpRequestTest {

    @Test
    public void offersAWayToExtractParameters() {
        HttpRequest request = new HttpRequest() {{
            query = "user=John&password=Doe";
        }};

        assertThat(request.getValueOf("user"), equalTo("John"));
        assertThat(request.getValueOf("password"), equalTo("Doe"));
    }
}
