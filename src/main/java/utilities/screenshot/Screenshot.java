package utilities.screenshot;

import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import utilities.data.DataGenerator;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Screenshot {
    public static String generateDateTime(String dateFormat) {
        return DateTimeFormatter.ofPattern(dateFormat).format(LocalDateTime.now());
    }

    @SneakyThrows
    public void takeScreenshot(WebDriver driver) {
        String path = new DataGenerator().getFolderPath("debug") + "/%s_%s.png".formatted("debug",
                generateDateTime("yyyy_MM_dd-HH_mm_ss")).replace("/", File.separator);
        FileUtils.copyFile(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE), new File(path));
    }

    @SneakyThrows
    public void takeScreenShot(WebDriver driver, WebElement element) {
        // Get entire page screenshot
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        BufferedImage fullImg = ImageIO.read(screenshot);

        // Get the location of element on the page
        Point point = element.getLocation();

        // Get width and height of the element
        int eleWidth = element.getSize().getWidth();
        int eleHeight = element.getSize().getHeight();

        // Crop the entire page screenshot to get only element screenshot
        BufferedImage elementScreenShoot = fullImg.getSubimage(point.getX(), point.getY(), eleWidth, eleHeight);
        ImageIO.write(elementScreenShoot, "png", screenshot);

        // Copy the element screenshot to disk
        File screenshotLocation = new File(new DataGenerator().getFolderPath("element_image") + File.separator + "el_image.png");
        FileUtils.copyFile(screenshot, screenshotLocation);
    }
}
