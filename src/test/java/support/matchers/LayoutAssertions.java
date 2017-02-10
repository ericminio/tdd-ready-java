package support.matchers;

import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;

public class LayoutAssertions {

    public static boolean areInVerticalOrder(WebElement one, WebElement two) {
        Rectangle oneBox = one.getRect();
        Rectangle twoBox = two.getRect();

        return twoBox.y >= (oneBox.y + oneBox.height);
    }

    public static boolean areInHorizontalOrder(WebElement one, WebElement two) {
        Rectangle oneBox = one.getRect();
        Rectangle twoBox = two.getRect();

        return twoBox.x >= (oneBox.x + oneBox.width);
    }

    public static boolean areInNestingOrder(WebElement container, WebElement nested) {
        Rectangle containerBox = container.getRect();
        Rectangle nestedBox = nested.getRect();

        return nestedBox.getX() > containerBox.getX()
                && nestedBox.getY() > containerBox.getY()
                && (nestedBox.getX() + nestedBox.getWidth() < containerBox.getX() + containerBox.getWidth())
                && (nestedBox.getY() + nestedBox.getHeight() < containerBox.getY() + containerBox.getHeight());
    }

    public static boolean haveSameX(WebElement one, WebElement two) {
        return one.getLocation().getX() == two.getLocation().getX();
    }

    public static boolean haveSameY(WebElement one, WebElement two) {
        return one.getLocation().getY() == two.getLocation().getY();
    }
}
