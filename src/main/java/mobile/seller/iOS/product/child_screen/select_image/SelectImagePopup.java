package mobile.seller.iOS.product.child_screen.select_image;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import utilities.assert_customize.AssertCustomize;
import utilities.commons.UICommonIOS;

import java.util.stream.IntStream;

public class SelectImagePopup extends SelectImageElement{
    WebDriver driver;
    AssertCustomize assertCustomize;
    UICommonIOS commonIOS;
    Logger logger = LogManager.getLogger();
    public SelectImagePopup(WebDriver driver) {
        // Get driver
        this.driver = driver;

        // Init assert class
        assertCustomize = new AssertCustomize(driver);

        // Init commons class
        commonIOS = new UICommonIOS(driver);
    }

    public void selectImages() {
        // Select images
        IntStream.range(0, 2).forEach(imageIndex -> commonIOS.tap(loc_lstImages, imageIndex));

        // Save changes
        commonIOS.tap(loc_btnSave);

        // Log
        logger.info("Select product images");
    }
}
