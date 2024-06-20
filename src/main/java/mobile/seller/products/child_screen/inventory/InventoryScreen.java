package mobile.seller.products.child_screen.inventory;

import mobile.seller.products.child_screen.inventory.add_imei.AddIMEIScreen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import utilities.assert_customize.AssertCustomize;
import utilities.commons.UICommonMobile;
import utilities.model.dashboard.setting.branchInformation.BranchInfo;

import java.util.stream.IntStream;

public class InventoryScreen extends InventoryElement {
    WebDriver driver;
    AssertCustomize assertCustomize;
    UICommonMobile commonMobile;
    Logger logger = LogManager.getLogger();

    public InventoryScreen(WebDriver driver) {
        this.driver = driver;
        assertCustomize = new AssertCustomize(driver);
        commonMobile = new UICommonMobile(driver);
    }

    public void addStock(boolean manageByIMEI, BranchInfo branchInfo, String variation, int... branchStock) {
        // Add stock for each branch
        IntStream.range(0, branchInfo.getBranchName().size()).forEach(branchIndex -> {
            // Get current branch
            String branchName = branchInfo.getBranchName().get(branchIndex);

            // Get branch quantity
            int branchQuantity = (branchIndex >= branchStock.length) ? 0 : branchStock[branchIndex];

            // Add branch stock
            if (manageByIMEI) {
                // Navigate to add imei screen
                commonMobile.click(loc_txtBranchStock, branchIndex);

                // Add imei
                new AddIMEIScreen(driver).addIMEI(branchQuantity, branchName, variation);
            } else {
                // Input branch stock
                commonMobile.sendKeys(loc_txtBranchStock, branchIndex, String.valueOf(branchQuantity));
            }

            // Log
            logger.info("Add stock for branch '{}', quantity: {}", branchName,branchQuantity);
        });

        // Save changes
        commonMobile.click(rsId_btnSave);
    }
}
