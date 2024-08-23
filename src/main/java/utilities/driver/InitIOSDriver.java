package utilities.driver;

import io.appium.java_client.ios.IOSDriver;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

import static utilities.environment.goSELLEREnvironment.goSELLERBundleId;

public class InitIOSDriver {
    Logger logger = LogManager.getLogger();
    private final static String url = "http://127.0.0.1:4723/wd/hub";

    public IOSDriver getIOSDriver(String udid) throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("udid", udid);
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("newCommandTimeout", 300000);
        capabilities.setCapability("wdaStartupRetries", 5);
        capabilities.setCapability("automationName", "XCUITest");
        return new IOSDriver(new URL(url), capabilities);
    }


    public IOSDriver getSellerDriver(String udid) {
        try {
            // Init driver
            IOSDriver driver = getIOSDriver(udid);

            // Open GoSeller app
            driver.installApp(System.getProperty("user.dir") + "/src/main/resources/app/GoSeller STG.zip");
            driver.terminateApp(goSELLERBundleId);
            driver.activateApp(goSELLERBundleId);

            // Return driver
            return driver;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    public IOSDriver getBuyerDriver(String udid, String goBuyerBundleId) {
        try {
            IOSDriver driver = getIOSDriver(udid);
            driver.activateApp(goBuyerBundleId);
            return driver;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
