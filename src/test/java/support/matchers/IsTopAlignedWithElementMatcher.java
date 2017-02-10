package support.matchers;


import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.openqa.selenium.WebElement;

public class IsTopAlignedWithElementMatcher extends TypeSafeDiagnosingMatcher<WebElement> {

    private final WebElement target;
    private WebElement elementUnderTest;

    public IsTopAlignedWithElementMatcher(WebElement element) {
        this.target = element;
    }

    public static IsTopAlignedWithElementMatcher isTopAlignedWith(WebElement element) {
        return new IsTopAlignedWithElementMatcher(element);
    }

    @Override
    public void describeTo(Description description) {
        String message = "#" +
                this.elementUnderTest.getAttribute("id") +
                " to have same Y as #" +
                this.target.getAttribute("id");
        description.appendText(message);
    }

    @Override
    protected boolean matchesSafely(WebElement item, Description mismatchDescription) {
        this.elementUnderTest = item;

        if (!LayoutAssertions.haveSameY(this.elementUnderTest, target)) {
            mismatchDescription.appendText("no :(");
            return false;
        }
        return true;
    }
}
