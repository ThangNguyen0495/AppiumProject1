package mobile.seller.products.product_management;

import api.Seller.products.all_products.APIAllProductsForCheckSortAndFilter;
import mobile.seller.login.LoginScreen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import utilities.assert_customize.AssertCustomize;
import utilities.commons.UICommonMobile;

import java.util.List;

import static mobile.seller.products.product_management.ProductManagementElement.SortOption.*;
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
        commonMobile.navigateToScreenUsingScreenActivity(goSELLERBundleId, goSELLERProductManagementActivity);

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

    void sortListProduct(SortOption sortOption) {
        // Sort by recent updated
        commonMobile.click(rsId_btnSort);

        // Select options: recent updated
        commonMobile.click(loc_lstSortOptions, getAllSortOptions().indexOf(sortOption));

        // Log
        logger.info("Sort list product by {}", sortOption);
    }

    public void checkSortByRecentUpdated() {
        // Sort list product by recent updated
        sortListProduct(recentUpdated);

        // Get list product name after sort
        List<String> firstScreenProductNames = commonMobile.getListElementTextOnFirstScreen(loc_lblProductName);

        // Get list product nam by API
        List<String> expectedProductNames = new APIAllProductsForCheckSortAndFilter(LoginScreen.getLoginInformation()).getListProductNameAfterSortByRecentUpdated();

        // Verify list product are sorted correctly
        assertCustomize.assertEquals(firstScreenProductNames, expectedProductNames.subList(0, firstScreenProductNames.size()), "List product after sorted on first screen must be %s, but found %s"
                .formatted(expectedProductNames.subList(0, firstScreenProductNames.size()), firstScreenProductNames));

        // Verify test
        AssertCustomize.verifyTest();
    }

    public void checkSortByStockHighToLow() {
        // Sort list product by stock high to low
        sortListProduct(stockHighToLow);

        // Get list product name after sort
        List<String> firstScreenProductNames = commonMobile.getListElementTextOnFirstScreen(loc_lblProductName);

        // Get list product nam by API
        List<String> expectedProductNames = new APIAllProductsForCheckSortAndFilter(LoginScreen.getLoginInformation()).getListProductNameAfterSortByStockHighToLow();

        // Verify list product are sorted correctly
        assertCustomize.assertEquals(firstScreenProductNames, expectedProductNames.subList(0, firstScreenProductNames.size()), "List product after sorted on first screen must be %s, but found %s"
                .formatted(expectedProductNames.subList(0, firstScreenProductNames.size()), firstScreenProductNames));

        // Verify test
        AssertCustomize.verifyTest();
    }

    public void checkSortByStockLowToHigh() {
        // Sort list product by stock low to high
        sortListProduct(stockLowToHigh);

        // Get list product name after sort
        List<String> firstScreenProductNames = commonMobile.getListElementTextOnFirstScreen(loc_lblProductName);

        // Get list product nam by API
        List<String> expectedProductNames = new APIAllProductsForCheckSortAndFilter(LoginScreen.getLoginInformation()).getListProductNameAfterSortByStockLowToHigh();

        // Verify list product are sorted correctly
        assertCustomize.assertEquals(firstScreenProductNames, expectedProductNames.subList(0, firstScreenProductNames.size()), "List product after sorted on first screen must be %s, but found %s"
                .formatted(expectedProductNames.subList(0, firstScreenProductNames.size()), firstScreenProductNames));

        // Verify test
        AssertCustomize.verifyTest();
    }

    public void checkSortByPriorityHighToLow() {
        // Sort list product by priority high to low
        sortListProduct(priorityHighToLow);

        // Get list product name after sort
        List<String> firstScreenProductNames = commonMobile.getListElementTextOnFirstScreen(loc_lblProductName);

        // Get list product nam by API
        List<String> expectedProductNames = new APIAllProductsForCheckSortAndFilter(LoginScreen.getLoginInformation()).getListProductNameAfterSortByPriorityHighToLow();

        // Verify list product are sorted correctly
        assertCustomize.assertEquals(firstScreenProductNames, expectedProductNames.subList(0, firstScreenProductNames.size()), "List product after sorted on first screen must be %s, but found %s"
                .formatted(expectedProductNames.subList(0, firstScreenProductNames.size()), firstScreenProductNames));

        // Verify test
        AssertCustomize.verifyTest();
    }

    public void checkSortByPriorityLowToHigh() {
        // Sort list product by priority low to high
        sortListProduct(priorityLowToHigh);

        // Get list product name after sort
        List<String> firstScreenProductNames = commonMobile.getListElementTextOnFirstScreen(loc_lblProductName);

        // Get list product nam by API
        List<String> expectedProductNames = new APIAllProductsForCheckSortAndFilter(LoginScreen.getLoginInformation()).getListProductNameAfterSortByPriorityLowToHigh();

        // Verify list product are sorted correctly
        assertCustomize.assertEquals(firstScreenProductNames, expectedProductNames.subList(0, firstScreenProductNames.size()), "List product after sorted on first screen must be %s, but found %s"
                .formatted(expectedProductNames.subList(0, firstScreenProductNames.size()), firstScreenProductNames));

        // Verify test
        AssertCustomize.verifyTest();
    }
}
