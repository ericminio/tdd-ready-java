package support.web;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;

public class AppDriver {

    private static AppDriver instance;
    public WebDriver browser;

    public AppDriver(WebDriver browser) {
        this.browser = browser;
    }

    public static InspectablePage open(String url, Device device) {
        WebDriver browser = Browsers.firefox();
        browser.manage().window().setSize(new Dimension(device.getWindowSize().width, device.getWindowSize().height));
        browser.get(url);
        AppDriver.instance = new AppDriver(browser);
        return new InspectablePage(AppDriver.instance);
    }

    public static void close() {
        AppDriver.instance.browser.quit();
    }
}
