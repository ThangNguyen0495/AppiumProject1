package mobile.seller.home;

import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.commons.UICommonMobile;

import java.time.Duration;

import static mobile.seller.home.HomeElement.QuickActions.getAllActions;

public class HomeScreen extends HomeElement {

    final static Logger logger = LogManager.getLogger(HomeScreen.class);

    WebDriver driver;
    WebDriverWait wait;
    UICommonMobile commonAction;

    public HomeScreen(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        commonAction = new UICommonMobile(driver);
    }




    public boolean isAccountTabDisplayed() {
        boolean isDisplayed = commonAction.isDisplayed(loc_tabAccount);
        logger.info("Is Account Tab displayed: {}", isDisplayed);
        return isDisplayed;
    }

    public void clickAccountTab() {
        commonAction.click(loc_tabAccount);
        logger.info("Click on Account tab");
    }

    public void clickLogoutBtn() {
        commonAction.click(loc_btnLogout);
        logger.info("Click on Logout button");
    }

    public void clickLogoutOKBtn() {
        commonAction.click(loc_btnOK);
        logger.info("Click on OK button to confirm logout");
    }

    public HomeScreen clickLogoutAbortBtn() {
        commonAction.click(loc_btnLogoutAbort);
        logger.info("Click on Cancel button to abort logout");
        return this;
    }

    @SneakyThrows
    public void navigate(QuickActions actions) {
        // move to quick access section
        commonAction.getElement(str_resourceIdQuickAccess);

        // actions
        commonAction.click(loc_btnQuickAccessActions, getAllActions().indexOf(actions));
    }
}
