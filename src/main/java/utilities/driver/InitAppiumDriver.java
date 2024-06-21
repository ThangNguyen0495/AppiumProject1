package utilities.driver;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import lombok.SneakyThrows;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

import static utilities.environment.goBUYEREnvironment.goBUYERSplashActivity;
import static utilities.environment.goSELLEREnvironment.goSELLERBundleId;
import static utilities.environment.goSELLEREnvironment.goSELLERLoginActivity;


public class InitAppiumDriver {
	private final static String url = "http://127.0.0.1:4723/wd/hub";

	/**
	 * This method returns an instance of the AppiumDriver class. It takes in the following parameters:
	 * @param udid The UDID of the device
	 * @param platformName The platform of the device (either "android" or "ios")
	 * @param appPackage The package name of the app
	 * @param appActivity The activity that needs to be started
	 * @param url The Appium server URL
	 * @return AppiumDriver returns an instance of the AppiumDriver class (AndroidDriver or IOSDriver)
	 * @throws MalformedURLException throws a MalformedURLException if the URL for the Appium server is malformed
	 * @throws IllegalArgumentException throws an IllegalArgumentException if the platform name is not recognized
	 */
    public AppiumDriver getAppiumDriver(String udid, String platformName, String appPackage, String appActivity, String url) throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("udid", udid);
        capabilities.setCapability("platformName", platformName);
        capabilities.setCapability("appPackage", appPackage);
        capabilities.setCapability("appActivity", appActivity);
		capabilities.setCapability("newCommandTimeout", 300000);
        capabilities.setCapability("noReset", "false");
		capabilities.setCapability("fastReset", "true");
		capabilities.setCapability("resetOnSessionStartOnly", "true");
		capabilities.setCapability("autoGrantPermissions","true");
		capabilities.setCapability("automationName", "UIAutomator2");
		// Fix startActivity issue
		capabilities.setCapability("appWaitActivity","*");
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

	@SneakyThrows
	public AppiumDriver getSellerDriver(String udid) {
		return getAppiumDriver(udid, "ANDROID", goSELLERBundleId, goSELLERLoginActivity, url);
	}

	@SneakyThrows
	public AppiumDriver getBuyerDriver(String udid, String goBuyerBundleId) {
		return getAppiumDriver(udid, "ANDROID", goBuyerBundleId, goBUYERSplashActivity, url);
	}

}
