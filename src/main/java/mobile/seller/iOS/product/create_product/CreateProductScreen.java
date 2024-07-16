package mobile.seller.iOS.product.create_product;

import mobile.seller.iOS.product.child_screen.select_image.SelectImagePopup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import utilities.assert_customize.AssertCustomize;
import utilities.commons.UICommonIOS;

public class CreateProductScreen extends CreateProductElement {
    WebDriver driver;
    AssertCustomize assertCustomize;
    UICommonIOS commonIOS;
    Logger logger = LogManager.getLogger();
    public CreateProductScreen(WebDriver driver) {
        // Get driver
        this.driver = driver;

        // Init assert class
        assertCustomize = new AssertCustomize(driver);

        // Init commons class
        commonIOS = new UICommonIOS(driver);
    }

    public void selectImages() {
        // Open select image popup
        commonIOS.tap(loc_icnProductImage);

        // Allow access all photo library
        commonIOS.allowPermission("Allow Full Access");

        // Select images
        new SelectImagePopup(driver).selectImages();
    }
}
