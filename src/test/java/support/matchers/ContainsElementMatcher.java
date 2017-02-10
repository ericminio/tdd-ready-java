package support.matchers;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.openqa.selenium.WebElement;

public class ContainsElementMatcher extends TypeSafeDiagnosingMatcher<WebElement> {

    private final WebElement target;
    private WebElement elementUnderTest;

    public ContainsElementMatcher(WebElement element) {
        this.target = element;
    }

    public static ContainsElementMatcher contains(WebElement element) {
        return new ContainsElementMatcher(element);
    }

    @Override
    public void describeTo(Description description) {
        String message = "#" +
                this.elementUnderTest.getAttribute("id") +
                " to contain #" +
                this.target.getAttribute("id");
        description.appendText(message);
    }

    @Override
    protected boolean matchesSafely(WebElement item, Description mismatchDescription) {
        this.elementUnderTest = item;

        if (!LayoutAssertions.areInNestingOrder(this.elementUnderTest, target)) {
            mismatchDescription.appendText("no :(");
            return false;
        }
        return true;
    }
}
