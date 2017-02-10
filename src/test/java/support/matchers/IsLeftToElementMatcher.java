package support.matchers;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.openqa.selenium.WebElement;

public class IsLeftToElementMatcher extends TypeSafeDiagnosingMatcher<WebElement> {

    private final WebElement target;
    private WebElement elementUnderTest;

    public IsLeftToElementMatcher(WebElement element) {
        this.target = element;
    }

    public static IsLeftToElementMatcher isLeftTo(WebElement element) {
        return new IsLeftToElementMatcher(element);
    }

    @Override
    public void describeTo(Description description) {
        String message = "#" +
                this.elementUnderTest.getAttribute("id") +
                " to be left to #" +
                this.target.getAttribute("id");
        description.appendText(message);
    }

    @Override
    protected boolean matchesSafely(WebElement item, Description mismatchDescription) {
        this.elementUnderTest = item;

        if (!LayoutAssertions.areInHorizontalOrder(this.elementUnderTest, target)) {
            mismatchDescription.appendText("no :(");
            return false;
        }
        return true;
    }
}
