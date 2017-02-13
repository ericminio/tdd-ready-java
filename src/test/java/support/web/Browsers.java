package support.web;

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
    public void firefoxCanBeInstantiated() {
        browser = Browsers.firefox();

        assertThat(browser, not(equalTo(null)));
    }

    @Test
    public void headlessFirefoxCanBeInstantiated() {
        browser = Browsers.headless();

        assertThat(browser, not(equalTo(null)));
    }

    public static HtmlUnitDriver headless() {
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        capabilities.setCapability("marionette", false);
        capabilities.setJavascriptEnabled(true);

        return new HtmlUnitDriver(capabilities);
    }

    public static WebDriver firefox() {
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        capabilities.setCapability("marionette", false);
        capabilities.setJavascriptEnabled(true);

        return new FirefoxDriver(capabilities);
    }
}
