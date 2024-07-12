package mobile.seller.iOS.login;

import mobile.seller.iOS.home.HomeScreen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import utilities.assert_customize.AssertCustomize;
import utilities.commons.UICommonIOS;
import utilities.model.sellerApp.login.LoginInformation;

public class LoginScreen extends LoginElement {
    WebDriver driver;
    AssertCustomize assertCustomize;
    UICommonIOS commonIOS;
    Logger logger = LogManager.getLogger();

    public LoginScreen(WebDriver driver) {
        // Get driver
        this.driver = driver;

        // Init assert class
        assertCustomize = new AssertCustomize(driver);

        // Init commons class
        commonIOS = new UICommonIOS(driver);
    }

    void inputUsername(String username) {
        // Input username
        commonIOS.sendKeys(loc_txtUsername, username);

        // Log
        logger.info("Input username: {}", username);
    }

    void inputPassword(String password) {
        // Input password
        commonIOS.sendKeys(loc_txtPassword, password);

        // Log
        logger.info("Input password: ********" );
    }

    void agreeTermOfUse() {
        // Agree term of use
        commonIOS.tap(loc_chkTermOfUse);

        // Log
        logger.info("Agree term of use");
    }

    void tapLoginBtn() {
        // Tap login button
        commonIOS.tap(loc_btnLogin);

        // Log
        logger.info("Tap login button");
    }

    public void performLogin(LoginInformation loginInformation) {
        // Check if user are logged, logout and re-login with new account
        new HomeScreen(driver).logout();

        // Login with new account
        inputUsername(loginInformation.getEmail());
        inputPassword(loginInformation.getPassword());
        agreeTermOfUse();
        tapLoginBtn();
    }
}
