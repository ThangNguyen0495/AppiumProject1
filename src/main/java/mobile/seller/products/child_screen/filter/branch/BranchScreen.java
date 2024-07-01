package mobile.seller.products.child_screen.filter.branch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utilities.assert_customize.AssertCustomize;
import utilities.commons.UICommonMobile;

public class BranchScreen extends BranchElement{
    WebDriver driver;
    AssertCustomize assertCustomize;
    UICommonMobile commonMobile;
    Logger logger = LogManager.getLogger();
    public BranchScreen(WebDriver driver) {
        // Get driver
        this.driver = driver;

        // Init assert class
        assertCustomize = new AssertCustomize(driver);

        // Init commons class
        commonMobile = new UICommonMobile(driver);
    }

    public void selectBranch(String branchName) {
        // Select branch
        commonMobile.click(branchName.equals("ALL") ? loc_btnAllBranches : By.xpath(str_btnBranch.formatted(branchName)));

        // Log
        logger.info("Select branch: {}", branchName);
    }
}
