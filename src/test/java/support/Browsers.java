package support;

import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

public class Browsers {

    private WebDriver browser;

    @After
    public void quit() {
        if (browser != null) {
            browser.quit();
        }
    }

    @Test
    @Ignore
    public void firefoxCanBeInstantiated() {
        browser = Browsers.firefox();

        assertThat(browser, not(equalTo(null)));
    }

    @Test
    @Ignore
    public void chromeCanBeInstantiated() {
        browser = Browsers.chrome();

        assertThat(browser, not(equalTo(null)));
    }

    @Test
    public void htmlunitCanBeInstantiated() {
        assertThat(Browsers.headless(), not(equalTo(null)));
    }

    public static HtmlUnitDriver headless() {
        System.setProperty("webdriver.chrome.driver", BrowserDriverPaths.chrome());
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability("marionette", true);
        capabilities.setJavascriptEnabled(true);

        return new HtmlUnitDriver(capabilities);
    }

    public static WebDriver chrome() {
        System.setProperty("webdriver.chrome.driver", BrowserDriverPaths.chrome());
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability("marionette", true);

        return new ChromeDriver(capabilities);
    }

    public static WebDriver firefox() {
        System.setProperty("webdriver.gecko.driver", BrowserDriverPaths.firefox());
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        capabilities.setCapability("marionette", true);

        return new FirefoxDriver(capabilities);
    }
}
