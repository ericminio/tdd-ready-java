package support.matchers;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.openqa.selenium.WebElement;

public class IsRightToElementMatcher extends TypeSafeDiagnosingMatcher<WebElement> {

    private final WebElement target;
    private WebElement elementUnderTest;

    public IsRightToElementMatcher(WebElement element) {
        this.target = element;
    }

    public static IsRightToElementMatcher isRightTo(WebElement element) {
        return new IsRightToElementMatcher(element);
    }

    @Override
    public void describeTo(Description description) {
        String message = "#" +
                this.elementUnderTest.getAttribute("id") +
                " to be right to #" +
                this.target.getAttribute("id");
        description.appendText(message);
    }

    @Override
    protected boolean matchesSafely(WebElement item, Description mismatchDescription) {
        this.elementUnderTest = item;

        if (!LayoutAssertions.areInHorizontalOrder(target, this.elementUnderTest)) {
            mismatchDescription.appendText("no :(");
            return false;
        }
        return true;
    }
}
