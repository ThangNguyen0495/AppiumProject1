package utilities.driver;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
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

    public AppiumDriver getAppiumDriver(String udid, String appPackage, String url) throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("udid", udid);
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("bundleId", appPackage);
        capabilities.setCapability("newCommandTimeout", 300000);
        capabilities.setCapability("automationName", "XCUITest");
        return getAppiumDriver(capabilities, url);
    }

    public AppiumDriver getAppiumDriver(DesiredCapabilities capabilities, String url) throws MalformedURLException {
        String platformNameFromCapacity = capabilities.getCapability("platformName").toString();
        if (platformNameFromCapacity.equalsIgnoreCase("android")) {
            return new AndroidDriver(new URL(url), capabilities);
        } else if(platformNameFromCapacity.equalsIgnoreCase("ios")) {
            return new IOSDriver(new URL(url), capabilities);
        } else {
            throw new IllegalArgumentException("Unknown platform: " + platformNameFromCapacity);
        }
    }


    public AppiumDriver getSellerDriver(String udid) {
        try {
            return getAppiumDriver(udid, goSELLERBundleId, url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    public AppiumDriver getBuyerDriver(String udid, String goBuyerBundleId) {
        return getAppiumDriver(udid, goBuyerBundleId, url);
    }
}
