package seller;

import io.appium.java_client.ios.IOSDriver;
import mobile.seller.iOS.login.LoginScreen;
import mobile.seller.iOS.products.create_product.CreateProductScreen;
import org.testng.annotations.Test;
import utilities.driver.InitIOSDriver;
import utilities.model.sellerApp.login.LoginInformation;


public class AppiumTest {

    String udid = "00008110-000A2928116A201E";

    @Test()
    void t() throws Exception {
        IOSDriver driver = new InitIOSDriver().getSellerDriver(udid);

        new LoginScreen(driver).performLogin(new LoginInformation("stgauto@nbobd.com", "Abc@12345"));

        new CreateProductScreen(driver).navigateToCreateProductScreen().createProductWithoutVariation();
    }

}



