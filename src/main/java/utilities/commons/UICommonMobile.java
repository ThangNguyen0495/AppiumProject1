package utilities.commons;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.StartsActivity;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.data.DataGenerator;
import utilities.screenshot.Screenshot;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class UICommonMobile {

    private final static Logger logger = LogManager.getLogger(UICommonMobile.class);
    WebDriver driver;
    WebDriverWait wait;

    public UICommonMobile(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void scrollToTopOfScreen() {
        driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).scrollBackward().scrollToBeginning(10)"));
    }

    public void scrollUp() {
        driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).scrollBackward()"));
    }

    public void scrollToEndOfScreen() {
        driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).scrollForward().scrollToEnd(10)"));
    }

    public void scrollDown() {
        driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).scrollForward()"));
    }

    public WebElement getElement(String resourceId) {
        By locator = AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().resourceId(\"%s\"))".formatted(resourceId));
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public WebElement getElement(By locator) {
        // if element is not presented, scroll more to get element.
        if (driver.findElements(locator).isEmpty()) scrollDown();
        return driver.findElement(locator);
    }

    public WebElement getElement(By locator, int index) {
        // get all elements in this screen
        List<WebElement> elements = new ArrayList<>(driver.findElements(locator));

        // init current number of elements
        int currentSize = elements.size();

        // find list elements
        while (currentSize <= index) {
            // init temp arr
            List<WebElement> tempArr = new ArrayList<>(elements);

            // scroll more to get new element
            scrollDown();

            // add new element to list
            tempArr.addAll(driver.findElements(locator));

            // remove duplicate element
            elements = tempArr.stream().distinct().toList();

            // check has new element or not
            if (elements.size() == currentSize) break;

            // get current number of elements
            currentSize = elements.size();
        }

        // return element
        return elements.get(index);
    }

    public WebElement getElement(String parentResourceId, By locator) {
        // move into parent element
        getElement(parentResourceId);

        // if element is not presented, scroll more to get element.
        if (driver.findElements(locator).isEmpty()) scrollDown();
        return driver.findElement(locator);
    }

    public WebElement getElement(String parentResourceId, By locator, int index) {
        // move into parent element
        getElement(parentResourceId);

        // get all elements in this screen
        List<WebElement> elements = new ArrayList<>(driver.findElements(locator));

        // init current number of elements
        int currentSize = elements.size();

        // find list elements
        while (currentSize <= index) {
            // init temp arr
            List<WebElement> tempArr = new ArrayList<>(elements);

            // scroll more to get new element
            scrollDown();

            // add new element to list
            tempArr.addAll(driver.findElements(locator));

            // remove duplicate element
            elements = tempArr.stream().distinct().toList();

            // check has new element or not
            if (elements.size() == currentSize) break;

            // get current number of elements
            currentSize = elements.size();
        }

        // return element
        return elements.get(index);
    }

    public void click(By locator) {
        getElement(locator).click();
    }

    public void click(String resourceId) {
        getElement(resourceId).click();
    }

    public void click(String resourceId, By locator) {
        getElement(resourceId, locator).click();
    }

    public void click(By locator, int index) {
        getElement(locator, index).click();
    }

    public void click(String parentResourceId, By locator, int index) {
        getElement(parentResourceId, locator, index).click();
    }

    public void sendKeys(By locator, CharSequence content) {
        WebElement element = getElement(locator);
        element.clear();
        element.sendKeys(content);
    }

    public void sendKeys(String resourceId, CharSequence content) {
        getElement(resourceId).clear();
        getElement(resourceId).sendKeys(content);
    }

    public void sendKeys(String parentResourceId, By locator, CharSequence content) {
        WebElement element = getElement(parentResourceId, locator);
        element.clear();
        element.sendKeys(content);
    }

    public void sendKeys(By locator, int index, CharSequence content) {
        WebElement element = getElement(locator, index);
        element.clear();
        element.sendKeys(content);
    }

    public void sendKeys(String parentResourceId, By locator, int index, CharSequence content) {
        WebElement element = getElement(parentResourceId, locator, index);
        element.clear();
        element.sendKeys(content);
    }


    public String getText(By locator) {
        return getElement(locator).getText();
    }

    public String getText(By locator, int index) {
        return getElement(locator, index).getText();
    }

    public boolean isEnabled(By locator) {
        return getElement(locator).isEnabled();
    }

    public boolean isEnabled(By locator, int index) {
        return getElement(locator, index).isEnabled();
    }


    public void hidKeyboard() {
        ((AndroidDriver) driver).hideKeyboard();
        logger.debug("Hid keyboard");
    }

    public void tapByCoordinates(int x, int y) {
        // Create new PointerInput objects for start and end positions
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");

        // Create a new sequence for the tap gesture and add actions to it
        Sequence tapPosition = new Sequence(finger, 1);
        tapPosition.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, y))
                .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        ((AndroidDriver) driver).perform(List.of(tapPosition));
    }

    public void tapByCoordinatesInPercent(double x, double y) {
        // Get the size of the device screen
        Dimension size = driver.manage().window().getSize();

        tapByCoordinates((int) (size.width * x), (int) (size.height * y));
    }

    public void swipeByCoordinatesInPercent(double startX, double startY, double endX, double endY) {
        swipeByCoordinatesInPercent(startX, startY, endX, endY, 200);
    }

    public void swipeByCoordinatesInPercent(double startX, double startY, double endX, double endY, int delay) {
        // Get the size of the device screen
        Dimension size = driver.manage().window().getSize();

        // Set start and end coordinates for the swipe
        int startXCoordinate = (int) (size.width * startX);
        int startYCoordinate = (int) (size.height * startY);
        int endXCoordinate = (int) (size.width * endX);
        int endYCoordinate = (int) (size.height * endY);

        // Create new PointerInput objects for start and end positions
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");

        // Create a new sequence for the swipe gesture and add actions to it
        Sequence swipeGesture = new Sequence(finger, 0);
        swipeGesture.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), startXCoordinate, startYCoordinate));
        swipeGesture.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipeGesture.addAction(new Pause(finger, Duration.ofMillis(10)));
        swipeGesture.addAction(finger.createPointerMove(Duration.ofMillis(delay), PointerInput.Origin.viewport(), endXCoordinate, endYCoordinate));
        swipeGesture.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        // Execute the swipe gesture on the device
        String platformNameFromCapacity = ((AppiumDriver) driver).getCapabilities().getCapability("platformName").toString();
        ((AppiumDriver) driver).perform(List.of(swipeGesture));
    }

    public void waitSplashScreenLoaded() {
        wait.until((ExpectedCondition<Boolean>) driver -> {
            AndroidDriver andDriver = (AndroidDriver) driver;
            assert andDriver != null;
            return Objects.requireNonNull(andDriver.currentActivity()).contains("MainActivity") || Objects.requireNonNull(andDriver.currentActivity()).contains("Login");
        });
    }

    public List<String> getListElementText(By locator) {
        // move to top screen
        scrollToTopOfScreen();

        // move and find start point
        getElement(locator);

        // get list text element
        List<WebElement> listElement;
        List<String> listElementText = new ArrayList<>();
        do {
            // get list elementId
            listElement = driver.findElements(locator);

            // if list.size() > 0
            // add element text if not contains
            if (!listElement.isEmpty()) for (int index = 0; index < listElement.size(); index++) {
                String elementText = driver.findElements(locator).get(index).getText();
                if (!listElementText.contains(elementText)) listElementText.add(elementText);
            }
            String currentPageSource = driver.getPageSource();

            //swipe screen to get next element list
            scrollDown();

            String nextPageSource = driver.getPageSource();
            if (currentPageSource.equals(nextPageSource)) break;

            // get new element list
            listElement = driver.findElements(locator);

        } while (((listElement.isEmpty()) & (listElementText.isEmpty())) || ((!listElement.isEmpty()) & !new HashSet<>(listElementText).containsAll(IntStream.range(0, listElement.size()).mapToObj(index -> driver.findElements(locator).get(index).getText()).toList())));
        return listElementText;
    }

    public WebElement getElementByText(String elText) {
        String xpath = "//*[@text = '%s']".formatted(elText);
        return getElement(By.xpath(xpath));
    }

    public WebElement moveAndGetOverlappedElementByText(String elText, By overlapLocator) {
        WebElement element = getElementByText(elText);
        Rectangle overlapRect = driver.findElement(overlapLocator).getRect();
        Rectangle elRect = element.getRect();
        if ((elRect.getY() + elRect.getHeight()) >= overlapRect.getY()) {
            swipeByCoordinatesInPercent(0.5, 0.75, 0.5, 0.5);
        }
        return driver.findElement(By.xpath("//*[@text = '%s']".formatted(elText)));
    }

    public void waitPageLoaded() {
        String currentPageSource;
        String nextPageSource;
        do {
            currentPageSource = driver.getPageSource();

            scrollDown();

            nextPageSource = driver.getPageSource();
        } while (currentPageSource.equals(nextPageSource));
    }

    public double getElementLocationYPercent(By locator) {
        int y = getElement(locator).getLocation().getY();
        Dimension size = driver.manage().window().getSize();
        return (double) y / size.height;
    }

    public void swipeHorizontalInPercent(By locator, double startX, double endX) {
        double y = getElementLocationYPercent(locator);
        swipeByCoordinatesInPercent(startX, y, endX, y);
    }

    public void relaunchApp(String appPackage, String appActivity) {
        Activity activity = new Activity(appPackage, appActivity);
        activity.setStopApp(false);
        ((StartsActivity) driver).startActivity(activity);
        waitSplashScreenLoaded();
    }

    public boolean isChecked(By locator) {
        return getElement(locator).getAttribute("checked").equals("true");
    }

    public boolean isDisplayed(By locator) {
        return getElement(locator).isDisplayed();
    }

    public void waitPageLoaded(By locator) {
        // wait home page loaded
        boolean isLoaded;
        do {
            isLoaded = driver.getPageSource().contains(locator.toString().replaceAll("B.*?'|'.+", ""));
        } while (!isLoaded);
    }

    @SneakyThrows
    public void pushFileToMobileDevices(String fileName) {
        // Specify the file to be uploaded
        File file = new File(new DataGenerator().getFilePath(fileName));

        // Convert the file to a byte array
        byte[] fileContent = Files.readAllBytes(file.toPath());

        // Push the file to the device
        ((AndroidDriver) driver).pushFile("/sdcard/Download/%s".formatted(fileName), file);

        // Log
        logger.info("Push file to mobile device, file name: {}", fileName);
    }

    public boolean isCheckedWithImageAlgorithms(By locator) {
        // Get element screenshot
        new Screenshot().takeScreenShot(driver, getElement(locator));

        // Compare screenshot with checked sample image
        return compareImages();
    }

    public boolean isCheckedWithImageAlgorithms(By locator, int index) {
        // Get element screenshot
        new Screenshot().takeScreenShot(driver, getElement(locator, index));

        // Compare screenshot with checked sample image
        return compareImages();
    }

    public boolean isCheckedWithImageAlgorithms(String resourceId) {
        // Get element screenshot
        new Screenshot().takeScreenShot(driver, getElement(resourceId));

        // Compare screenshot with checked sample image
        return compareImages();
    }

    public boolean isCheckedWithImageAlgorithms(String resourceId, By locator, int index) {
        // Get element screenshot
        new Screenshot().takeScreenShot(driver, getElement(resourceId, locator, index));

        // Compare screenshot with checked sample image
        return compareImages();
    }

    @SneakyThrows
    boolean compareImages() {
        // Load the images
        BufferedImage img1 = ImageIO.read(new File(new DataGenerator().getFilePath("checked.png")));
        BufferedImage img2 = ImageIO.read(new File(new DataGenerator().getFilePath("el_image.png")));

        // Compare pixel by pixel
        int totalPixel = img1.getHeight() * img1.getWidth() / 4;
        int matchPixel = 0;
        for (int height = 0; height < img1.getHeight() / 2; height++) {
            for (int width = 0; width < img1.getWidth() / 2; width++) {
                if (img1.getRGB(width, height) == img2.getRGB(width, height)) {
                    matchPixel++;
                }
            }
        }

        return Math.round((float) matchPixel / totalPixel) == 1;
    }

}
