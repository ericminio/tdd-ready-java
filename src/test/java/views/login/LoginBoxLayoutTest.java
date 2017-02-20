package views.login;

import http.routing.Router;
import run.LoginRoutes;
import run.SunServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import support.web.AppDriver;
import support.web.Device;
import support.web.InspectablePage;
import support.web.With;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static support.web.AppDriver.open;
import static support.matchers.ContainsElementMatcher.contains;
import static support.matchers.IsAboveElementMatcher.isAbove;
import static support.matchers.IsLeftAlignedWithElementMatcher.isLeftAlignedWith;
import static support.matchers.IsLeftToElementMatcher.isLeftTo;
import static support.matchers.IsTopAlignedWithElementMatcher.isTopAlignedWith;

public class LoginBoxLayoutTest {

    private SunServer server;
    private WebElement greetings;
    private WebElement userField;
    private WebElement userLabel;
    private WebElement passwordField;
    private WebElement passwordLabel;
    private WebElement loginBox;
    private WebElement loginButton;
    private WebElement feedback;

    @Before
    public void startServer() throws IOException {
        server = new SunServer(8000);
        server.useRouter( Router.routing( new LoginRoutes() ));
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
    public void hasTheExpectedLayoutOnIPhone() {
        openPage(With.iPhone);

        assertThat(greetings, isAbove(loginBox));

        assertThat(userLabel, isAbove(userField));
        assertThat(userField, isAbove(passwordLabel));
        assertThat(passwordLabel, isAbove(passwordField));
        assertThat(userLabel, isLeftAlignedWith(userField));
        assertThat(userLabel, isLeftAlignedWith(passwordLabel));
        assertThat(userLabel, isLeftAlignedWith(passwordField));
        assertThat(userLabel, isLeftAlignedWith(loginButton));
        assertThat(userLabel, isLeftAlignedWith(feedback));

        assertThat(loginBox, contains(userLabel));
        assertThat(loginBox, contains(feedback));
    }

    @Test
    public void hasTheExpectedLayoutOnLaptop() {
        openPage(With.laptop);

        assertThat(greetings, isAbove(loginBox));

        assertThat(userLabel, isAbove(passwordLabel));
        assertThat(userLabel, isLeftTo(userField));
        assertThat(passwordLabel, isLeftTo(passwordField));
        assertThat(userLabel, isLeftAlignedWith(passwordLabel));
        assertThat(userField, isLeftAlignedWith(passwordField));
        assertThat(userLabel, isTopAlignedWith(userField));
        assertThat(passwordLabel, isTopAlignedWith(passwordField));
        assertThat(userLabel, isLeftAlignedWith(loginButton));
        assertThat(userLabel, isLeftAlignedWith(feedback));

        assertThat(loginBox, contains(userLabel));
        assertThat(loginBox, contains(feedback));
    }

    private void openPage(Device device) {
        InspectablePage page = open("http://localhost:8000", device);

        greetings = page.element("#greetings");
        loginBox = page.element("#login-box");
        userField = page.element("#user-field");
        userLabel = page.element("#user-label");
        passwordField = page.element("#password-field");
        passwordLabel = page.element("#password-label");
        loginButton = page.element("#login-button");
        feedback = page.element("#login-feedback");
    }
}