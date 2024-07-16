package utilities.commons;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.ios.IOSDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;

public class UICommonIOS {
    private final static Logger logger = LogManager.getLogger();
    WebDriver driver;
    WebDriverWait wait;

    public UICommonIOS(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void allowPermission(String optionText) {
        try {
            HashMap<String, Object> args = new HashMap<>();
            args.put("action", "accept");
            args.put("buttonLabel", optionText);
            ((IOSDriver)driver).executeScript("mobile: alert", args);
        } catch (NoAlertPresentException ignored) {
        }
    }

    void hidKeyboard() {
        // Check if keyboard shows, hid this
        if (((IOSDriver) driver).isKeyboardShown()) {
            // Hid keyboard
            tap(By.xpath("//XCUIElementTypeButton[@name=\"Done\"]"));
        }
    }

    public WebDriverWait customWait(int milSeconds) {
        return new WebDriverWait(driver, Duration.ofMillis(milSeconds));
    }

    public List<WebElement> getListElements(By locator) {
        try {
            customWait(3000).until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (TimeoutException ignored) {
        }

        return driver.findElements(locator).isEmpty()
                ? List.of()
                : wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    public WebElement getElement(String predicateString) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.iOSNsPredicateString(predicateString)));
    }

    public WebElement getElement(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public WebElement getElement(By locator, int index) {
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator)).get(index);
    }

    public void tapByCoordinates(int x, int y) {
        // Create new PointerInput objects for start and end positions
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");

        // Create a new sequence for the tap gesture and add actions to it
        Sequence tapPosition = new Sequence(finger, 1);
        tapPosition.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, y))
                .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        ((IOSDriver) driver).perform(List.of(tapPosition));
    }

    public void tapByCoordinatesInPercent(double x, double y) {
        // Get the size of the device screen
        Dimension size = driver.manage().window().getSize();

        tapByCoordinates((int) (size.width * x), (int) (size.height * y));
    }

    void tap(WebElement element) {
        // Get the center coordinates of the element
        int centerX = element.getLocation().getX() + element.getSize().getWidth() / 2;
        int centerY = element.getLocation().getY() + element.getSize().getHeight() / 2;

        // Perform tap action using PointerInput
        tapByCoordinates(centerX, centerY);
    }

    public void tap(By locator) {
        tap(getElement(locator));
    }

    public void tap(By locator, int index) {
        tap(getElement(locator, index));
    }

    public void sendKeys(By locator, CharSequence content) {
        getElement(locator).clear();
        getElement(locator).sendKeys(content);
        hidKeyboard();
    }

    public void sendKeys(By locator, int index, CharSequence content) {
        getElement(locator, index).clear();
        getElement(locator, index).sendKeys(content);
        hidKeyboard();
    }

    public String getText(By locator) {
        return getElement(locator).getText();
    }

    public String getText(By locator, int index) {
        return getElement(locator, index).getText();
    }

}
