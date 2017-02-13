package features;

import http.Server;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import support.web.AppDriver;
import support.web.InspectablePage;
import support.web.With;

import java.io.IOException;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static support.web.AppDriver.open;

public class _01_PassLoginGateTest {

    private Server server;
    private InspectablePage page;

    @Before
    public void startServer() throws IOException {
        server = new Server(8000);
        server.start();
    }
    @After
    public void stopServer() {
        server.stop();
    }
    @After
    public void closeBrowser() {
        AppDriver.close();
    }

    @Test
    public void requiresValidCredentials() {
        page = open("http://localhost:8000", With.iPhone);
        page.type("user", "#user-field");
        page.type("unknown", "#password-field");
        page.click("#login-button");

        assertThat(page.element("#login-feedback").getCssValue("color"), equalTo("rgba(255, 0, 0, 1)"));
    }
}
