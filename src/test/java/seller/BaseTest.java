package seller;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utilities.appium_server.AppiumServer;
import utilities.assert_customize.AssertCustomize;
import utilities.recording.ScreenRecording;
import utilities.utils.PropertiesUtil;

public class BaseTest {
    public WebDriver driver;
    public String language;

    @BeforeSuite
    @Parameters({"environment", "language"})
    public void getConfig(@Optional("STAG") String environment,
                          @Optional("VIE") String language) {
        this.language = language;
        // set environment, language for Properties
        PropertiesUtil.setEnvironment(environment);
        PropertiesUtil.setDBLanguage(language);
        PropertiesUtil.setSFLanguage(language);

        // Start server
        AppiumServer.startServer();
    }

    @BeforeMethod
    void startTest() {
        // Start recording
        ScreenRecording.startRecording(driver);
    }

    @AfterMethod
    public void writeResult(ITestResult result) {
        // Clear assert count false
        AssertCustomize.setCountFalse(0);

        // Stop recording
        ScreenRecording.stopRecording(driver, result);
    }

    @AfterSuite
    void tearDown() {
        // Clear driver
        if (driver != null) driver.quit();

        // Start server
        AppiumServer.stopServer();
    }
}
