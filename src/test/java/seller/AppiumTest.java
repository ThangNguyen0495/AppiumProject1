package seller;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;
import utilities.appium_server.AppiumServer;
import utilities.driver.InitAppiumDriver;

import java.net.URL;

import static utilities.environment.goSELLEREnvironment.goSELLERBundleId;
import static utilities.environment.goSELLEREnvironment.goSELLERLoginActivity;

public class AppiumTest {

    String feature_id = "tm-product-service-default-layout";
    By xpath = By.xpath("//android.view.View[@resource-id=\"tm-product-service-default-layout\"]/android.view.View");

    @Test()
    void t() throws Exception {
        int systemPort = 0;
        int adbPort = 0;

//        CommandWindows.execute("adb -P %s connect %s".formatted(adbPort, "192.168.168.103:5555"));

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("udid", "192.168.168.103:5555");
        capabilities.setCapability("platformName", "ANDROID");
        capabilities.setCapability("appPackage", goSELLERBundleId);
        capabilities.setCapability("appActivity", goSELLERLoginActivity);
        capabilities.setCapability("newCommandTimeout", 300000);
        capabilities.setCapability("noReset", "false");
        capabilities.setCapability("fastReset", "true");
        capabilities.setCapability("resetOnSessionStartOnly", "true");
        capabilities.setCapability("autoGrantPermissions", "true");
        capabilities.setCapability("automationName", "UIAutomator2");
        capabilities.setCapability("systemPort", systemPort);
        capabilities.setCapability("adbPort", adbPort);
        // Fix startActivity issue
        capabilities.setCapability("appWaitActivity", "*");
        AppiumDriver driver = new AndroidDriver(new URL("http://127.0.0.1:4725/wd/hub"), capabilities);
//        new LoginScreen(driver).performLogin(new LoginInformation("stgaboned@nbobd.com", "Abc@12345"));
//        new HomeScreen(driver).navigate(HomeElement.QuickActions.addProduct);
//        new CreateProductScreen(driver).getManageByIMEI(false)
//                .navigateToCreateProductScreen()
//                .createProductWithVariation(5);
//        driver.quit();
    }


    @Test
    void v() {
        AppiumServer.startServer();
        new InitAppiumDriver().getSellerDriver("192.168.168.102:5555");
        new InitAppiumDriver().getSellerDriver("192.168.168.103:5555");
        AppiumServer.stopServer();
    }

}



