package seller;

import mobile.seller.home.HomeElement;
import mobile.seller.home.HomeScreen;
import mobile.seller.login.LoginScreen;
import mobile.seller.products.product_management.ProductManagementScreen;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.driver.InitAppiumDriver;
import utilities.model.sellerApp.login.LoginInformation;

import static utilities.account.AccountTest.ADMIN_ACCOUNT_THANG;
import static utilities.account.AccountTest.ADMIN_PASSWORD_THANG;

public class ProductManagementTest extends BaseTest {
    LoginInformation loginInformation;
    ProductManagementScreen productManagementScreen;

    @BeforeClass
    void setup() {
        // init WebDriver
        String uuid = "192.168.168.103:5555";//PropertiesUtil.getEnvironmentData("uuidAndroidThang");
        driver = new InitAppiumDriver().getSellerDriver(uuid);

        // init login information
        loginInformation = new LoginInformation(ADMIN_ACCOUNT_THANG, ADMIN_PASSWORD_THANG);

        // login to dashboard with login information
        new LoginScreen(driver).performLogin(loginInformation);
        new HomeScreen(driver).navigate(HomeElement.ManagementActions.products);

        // init product page POM
        productManagementScreen = new ProductManagementScreen(driver);
    }

    @Test
    void MN_PRODUCT_01_SortProductByRecentUpdated() {
        productManagementScreen.navigateToProductManagementScreen()
                .checkSortByRecentUpdated();
    }

    @Test
    void MN_PRODUCT_02_SortProductByStockHighToLow() {
        productManagementScreen.navigateToProductManagementScreen()
                .checkSortByStockHighToLow();
    }

    @Test
    void MN_PRODUCT_03_SortProductByStockLowToHigh() {
        productManagementScreen.navigateToProductManagementScreen()
                .checkSortByStockLowToHigh();
    }

    @Test
    void MN_PRODUCT_04_SortProductByPriorityHighToLow() {
        productManagementScreen.navigateToProductManagementScreen()
                .checkSortByPriorityHighToLow();
    }

    @Test
    void MN_PRODUCT_05_SortProductByPriorityLowToHigh() {
        productManagementScreen.navigateToProductManagementScreen()
                .checkSortByPriorityLowToHigh();
    }
}
