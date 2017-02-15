package features;

import data.ConnectionProvider;
import data.InMemoryDatabase;
import data.UserRepository;
import domain.User;
import http.Server;
import http.routing.Router;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import run.Routes;
import run.SunServer;
import support.web.AppDriver;
import support.web.InspectablePage;
import support.web.With;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static support.web.AppDriver.open;

public class _01_PassingLoginGateTest {

    private Server server;
    private InspectablePage page;

    @Before
    public void startServer() throws Exception {
        server = new SunServer(8000);
        server.useRouter( Router.routing( new Routes() ));
        server.start();
    }
    @After
    public void stopServer() throws Exception {
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

        assertThat(page.element("#login-feedback").getCssValue("color"),
                equalTo("rgba(255, 0, 0, 1)"));
        assertThat(page.title(), equalTo("Login"));
    }

    @Test
    public void isDoneWithValidCredentials() {
        ConnectionProvider connectionProvider = new InMemoryDatabase();
        UserRepository users = new UserRepository(connectionProvider);
        users.save(new User("known", "user"));
        server.useDatabase(connectionProvider);
        page = open("http://localhost:8000", With.iPhone);
        page.type("known", "#user-field");
        page.type("user", "#password-field");
        page.click("#login-button");

        assertThat(page.title(), equalTo("Main gate"));
    }
}
