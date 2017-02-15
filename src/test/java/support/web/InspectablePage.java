package support.web;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class InspectablePage {

    protected AppDriver driver;

    public InspectablePage(AppDriver driver) {
        this.driver = driver;
    }

    public WebElement element(String selector) {
        return driver.browser.findElement(By.cssSelector(selector));
    }

    public void type(String value, WebElement element) {
        element.sendKeys(value);
    }

    public void type(String value, String selector) {
        type(value, element(selector));
    }

    public void click(WebElement element) {
        element.click();
    }

    public void click(String selector) {
        click(element(selector));
    }

    public String title() {
        return driver.browser.getTitle();
    }
}
