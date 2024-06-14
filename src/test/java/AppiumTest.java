import io.appium.java_client.AppiumDriver;
import mobile.seller.home.HomeScreen;
import mobile.seller.login.LoginScreen;
import org.openqa.selenium.By;
import org.testng.annotations.Test;
import utilities.commons.UICommonMobile;
import utilities.driver.InitAppiumDriver;

import java.net.MalformedURLException;

import static mobile.seller.home.HomeElement.QuickActions.addProduct;
import static utilities.environment.EnvironmentInformation.*;

public class AppiumTest {
    private final static String udid = "192.168.168.101:5555";
    private final static String url = "http://127.0.0.1:4723/wd/hub";

    private AppiumDriver getSellerDriver() throws MalformedURLException {
        return new InitAppiumDriver().getAppiumDriver(udid, "ANDROID", goSELLERBundleId, goSELLERActivity, url);
    }

    private AppiumDriver getBuyerDriver() throws MalformedURLException {
        return new InitAppiumDriver().getAppiumDriver(udid, "ANDROID", goBuyerBundleId, goBuyerActivity, url);
    }

    String feature_id = "tm-product-service-default-layout";
    By xpath = By.xpath("//android.view.View[@resource-id=\"tm-product-service-default-layout\"]/android.view.View");

    @Test()
    void t() throws Exception {
        AppiumDriver driver = getSellerDriver();
        new LoginScreen(driver).performLogin("stgaboned@nbobd.com", "Abc@12345");
        new HomeScreen(driver).navigate(addProduct);
//        driver.quit();
    }

    @Test()
    void t2() throws Exception {
        AppiumDriver driver = getBuyerDriver();
        UICommonMobile commonMobile = new UICommonMobile(driver);
        commonMobile.getElement(feature_id);
        commonMobile.click(xpath, 7);

//        driver.quit();
    }

}



