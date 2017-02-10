package support.matchers;


import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.openqa.selenium.WebElement;

public class IsAboveElementMatcher extends TypeSafeDiagnosingMatcher<WebElement> {

    private final WebElement target;
    private WebElement elementUnderTest;

    public IsAboveElementMatcher(WebElement element) {
        this.target = element;
    }

    public static IsAboveElementMatcher isAbove(WebElement element) {
        return new IsAboveElementMatcher(element);
    }

    @Override
    public void describeTo(Description description) {
        String message = "#" +
                this.elementUnderTest.getAttribute("id") +
                " to be above #" +
                this.target.getAttribute("id");
        description.appendText(message);
    }

    @Override
    protected boolean matchesSafely(WebElement item, Description mismatchDescription) {
        this.elementUnderTest = item;

        if (!LayoutAssertions.areInVerticalOrder(this.elementUnderTest, target)) {
            mismatchDescription.appendText("no :(");
            return false;
        }
        return true;
    }
}
