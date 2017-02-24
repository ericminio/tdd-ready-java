package features;

import data.Sokoban;
import data.UsersKeeper;
import domain.User;
import http.Server;
import http.routing.Router;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import run.LoginRoutes;
import run.SunServer;
import support.web.AppDriver;
import support.web.InspectablePage;
import support.web.With;

import java.sql.Connection;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static support.web.AppDriver.open;

public class _01_PassingLoginGateTest {

    private Server server;
    private InspectablePage page;

    @Before
    public void startServer() throws Exception {
        Sokoban.please().useInMemoryDatabase().clearTables();
        server = new SunServer(8000);
        server.useRouter( Router.routing( new LoginRoutes() ));
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
        page.type("unknown", "#user-field");
        page.type("user", "#password-field");
        page.click("#login-button");

        assertThat(page.element("#login-feedback").getCssValue("color"),
                equalTo("rgba(255, 0, 0, 1)"));
        assertThat(page.title(), equalTo("Login"));
    }

    @Test
    public void isDoneWithValidCredentials() throws Exception {
        try (Connection connection = Sokoban.please().getConnection()) {
            UsersKeeper users = new UsersKeeper(connection);
            users.save(new User("known", "04f8996da763b7a969b1028ee3007569eaf3a635486ddab211d512c85b9df8fb"));
        }
        page = open("http://localhost:8000", With.iPhone);
        page.type("known", "#user-field");
        page.type("user", "#password-field");
        page.click("#login-button");

        assertThat(page.title(), equalTo("Main gate"));
    }

    @Test
    public void canWorkAfterHavingFailed() throws Exception {
        try (Connection connection = Sokoban.please().getConnection()) {
            UsersKeeper users = new UsersKeeper(connection);
            users.save(new User("known", "04f8996da763b7a969b1028ee3007569eaf3a635486ddab211d512c85b9df8fb"));
        }
        page = open("http://localhost:8000", With.iPhone);
        page.type("unknown", "#user-field");
        page.type("user", "#password-field");
        page.click("#login-button");
        page.type("known", "#user-field");
        page.type("user", "#password-field");
        page.click("#login-button");

        assertThat(page.title(), equalTo("Main gate"));
    }
}
