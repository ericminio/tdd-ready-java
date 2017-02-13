package support.diagnose;

import com.sun.net.httpserver.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import support.web.Browsers;

import java.io.IOException;
import java.net.InetSocketAddress;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class SeleniumReadyTest {

    private HttpServer server;
    private WebDriver browser;

    @Before
    public void startServer() throws IOException {
        server = HttpServer.create( new InetSocketAddress( 8000 ), 0 );
        server.createContext( "/", exchange -> {
            String body = "<html>"
                    + "<head>"
                    + "<script>"
                    + " var change = function() { "
                    + "     document.getElementById('greetings').innerHTML = 'hello world';"
                    + " }"
                    + "</script>"
                    + "</head>"
                    + "<body>"
                    + "<label id=\"greetings\">hello</label>"
                    + "<br/>"
                    + "<button id=\"go\" onclick=\"change();\">Go</button>"
                    + "</body>"
                    + "</html>";
            exchange.getResponseHeaders().add( "content-type", "text/html" );
            exchange.sendResponseHeaders( 200, body.length() );
            exchange.getResponseBody().write( body.getBytes() );
            exchange.close();
        } );
        server.start();
    }

    @After
    public void closeClientAndStopServer() {
        browser.quit();
        server.stop( 0 );
    }

    @Test
    public void headlessCanFindElementOnAPage() {
        browser = Browsers.headless();
        browser.navigate().to( "http://localhost:8000/" );
        WebElement element = browser.findElement(By.id("greetings"));

        assertThat(element.getText(), equalTo("hello"));
    }

    @Test
    public void headlessCanExecuteJavascript() {
        browser = Browsers.headless();
        browser.navigate().to( "http://localhost:8000/" );
        WebElement element = browser.findElement(By.id("greetings"));
        WebElement button = browser.findElement(By.id("go"));
        button.click();

        assertThat(element.getText(), equalTo("hello world"));
    }

    @Test
    public void firefoxDriverCanExecuteJavascript() {
        browser = Browsers.firefox();
        browser.navigate().to( "http://localhost:8000/" );
        WebElement element = browser.findElement(By.id("greetings"));
        WebElement button = browser.findElement(By.id("go"));
        button.click();

        assertThat(element.getText(), equalTo("hello world"));
    }
}
