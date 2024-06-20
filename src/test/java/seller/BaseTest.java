package seller;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utilities.assert_customize.AssertCustomize;
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
    }

    @AfterMethod
    public void writeResult(ITestResult result) {
        AssertCustomize.setCountFalse(0);
    }

    @AfterSuite
    void tearDown() {
        if (driver != null) driver.quit();
    }
}