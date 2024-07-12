package mobile.seller.android.products.child_screen.filter.collections;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utilities.assert_customize.AssertCustomize;
import utilities.commons.UICommonMobile;

public class CollectionsScreen extends CollectionsElement {
    WebDriver driver;
    AssertCustomize assertCustomize;
    UICommonMobile commonMobile;
    Logger logger = LogManager.getLogger();

    public CollectionsScreen(WebDriver driver) {
        // Get driver
        this.driver = driver;

        // Init assert class
        assertCustomize = new AssertCustomize(driver);

        // Init commons class
        commonMobile = new UICommonMobile(driver);
    }

    public void selectCollection(String collectionName) {
        // Select collection
        commonMobile.click(collectionName.equals("ALL") ? loc_btnAllCollections : By.xpath(str_btnCollection.formatted(collectionName)));

        // Log
        logger.info("Select collection: {}", collectionName);
    }

}
