package seller;

import org.openqa.selenium.By;
import org.testng.annotations.Test;
import utilities.appium_server.AppiumServer;
import utilities.driver.InitAppiumDriver;

public class AppiumTest {

    String feature_id = "tm-product-service-default-layout";
    By xpath = By.xpath("//android.view.View[@resource-id=\"tm-product-service-default-layout\"]/android.view.View");

    @Test()
    void t() throws Exception {
    }


    @Test
    void v() {
        AppiumServer.startServer();
        new InitAppiumDriver().getSellerDriver("192.168.168.102:5555");
        new InitAppiumDriver().getSellerDriver("192.168.168.103:5555");
        AppiumServer.stopServer();
    }

}



