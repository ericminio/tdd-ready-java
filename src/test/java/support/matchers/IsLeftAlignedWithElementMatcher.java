package support.matchers;


import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.openqa.selenium.WebElement;

public class IsLeftAlignedWithElementMatcher extends TypeSafeDiagnosingMatcher<WebElement> {

    private final WebElement target;
    private WebElement elementUnderTest;

    public IsLeftAlignedWithElementMatcher(WebElement element) {
        this.target = element;
    }

    public static IsLeftAlignedWithElementMatcher isLeftAlignedWith(WebElement element) {
        return new IsLeftAlignedWithElementMatcher(element);
    }

    @Override
    public void describeTo(Description description) {
        String message = "#" +
                this.elementUnderTest.getAttribute("id") +
                " to have same X as #" +
                this.target.getAttribute("id");
        description.appendText(message);
    }

    @Override
    protected boolean matchesSafely(WebElement item, Description mismatchDescription) {
        this.elementUnderTest = item;

        if (!LayoutAssertions.haveSameX(this.elementUnderTest, target)) {
            mismatchDescription.appendText("no :(");
            return false;
        }
        return true;
    }
}
