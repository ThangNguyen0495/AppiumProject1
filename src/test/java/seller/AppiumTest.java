package seller;

import io.appium.java_client.AppiumDriver;
import mobile.seller.home.HomeElement;
import mobile.seller.home.HomeScreen;
import mobile.seller.login.LoginScreen;
import mobile.seller.products.create_product.CreateProductScreen;
import org.openqa.selenium.By;
import org.testng.annotations.Test;
import utilities.commons.UICommonMobile;
import utilities.driver.InitAppiumDriver;
import utilities.model.sellerApp.login.LoginInformation;

public class AppiumTest extends BaseTest {

    String feature_id = "tm-product-service-default-layout";
    By xpath = By.xpath("//android.view.View[@resource-id=\"tm-product-service-default-layout\"]/android.view.View");

    @Test()
    void t() throws Exception {
        AppiumDriver driver = new InitAppiumDriver().getSellerDriver("192.168.168.102:5555");
        new LoginScreen(driver).performLogin(new LoginInformation("stgaboned@nbobd.com", "Abc@12345"));
        new HomeScreen(driver).navigate(HomeElement.QuickActions.addProduct);
        new CreateProductScreen(driver).getManageByIMEI(false)
                .navigateToCreateProductScreen()
                .createProductWithVariation(5);
//        driver.quit();
    }

    @Test()
    void t2() throws Exception {
//        AppiumDriver driver = getBuyerDriver();
        UICommonMobile commonMobile = new UICommonMobile(driver);
        commonMobile.getElement(feature_id);
        commonMobile.click(xpath, 7);

//        driver.quit();
    }

}



