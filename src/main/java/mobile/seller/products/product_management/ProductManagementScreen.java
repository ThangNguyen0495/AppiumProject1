package mobile.seller.products.product_management;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import utilities.assert_customize.AssertCustomize;
import utilities.commons.UICommonMobile;

import static utilities.environment.goSELLEREnvironment.*;

public class ProductManagementScreen extends ProductManagementElement {
    WebDriver driver;
    AssertCustomize assertCustomize;
    UICommonMobile commonMobile;
    Logger logger = LogManager.getLogger();

    public ProductManagementScreen(WebDriver driver) {
        // Get driver
        this.driver = driver;

        // Init assert class
        assertCustomize = new AssertCustomize(driver);

        // Init commons class
        commonMobile = new UICommonMobile(driver);
    }

    public ProductManagementScreen navigateToProductManagementScreen() {
        // Navigate to product management screen
        commonMobile.navigateToScreen(goSELLERBundleId, goSELLERProductManagementActivity);

        // Log
        logger.info("Navigate to product management screen.");

        return this;
    }

    public void navigateToProductDetailScreen(String productName) {
        // Search product by name
        commonMobile.sendKeys(rsId_txtSearchBox, productName);

        // Log
        logger.info("Search product by name: {}", productName);

        // Navigate to product detail screen
        By resultXpath = By.xpath(str_lblProductName.formatted(productName));
        if (commonMobile.isShown(resultXpath)) {
            // Click into first result
            commonMobile.click(resultXpath);

            // Wait screen loaded
            commonMobile.waitUntilScreenLoaded(goSELLERProductDetailActivity);

        } else throw new NoSuchElementException("No result with keyword: %s".formatted(productName));
    }
}
