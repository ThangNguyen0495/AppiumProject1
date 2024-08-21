package seller;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import mobile.seller.iOS.login.LoginScreen;
import mobile.seller.iOS.products.create_product.CreateProductScreen;
import org.testng.annotations.Test;
import utilities.driver.InitAndroidDriver;
import utilities.driver.InitIOSDriver;
import utilities.model.sellerApp.login.LoginInformation;
import utilities.utils.PropertiesUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class AppiumTest {

    String udid = "00008110-000A2928116A201E";

    @Test()
    void t() throws Exception {
        System.out.println(Optional.ofNullable(System.getProperty("udid")).orElse(PropertiesUtil.getEnvironmentData("udidIOSThang")));
//        AndroidDriver driver = new InitAndroidDriver().getAndroidDriver("PVIVHIR47LOR6DRK");
//        Map map = (Map) driver.executeScript("mobile: getNotifications");
//        List<Map> notifications = ((List<Map>)map.get("statusBarNotifications")).stream().distinct().toList();
//        List<String> notiList = new ArrayList();
//        notifications.stream().map(notification -> (Map) notification.get("notification")).forEach(innerNotification -> {
//            if (innerNotification.get("bigText") != null) {
//                notiList.add(innerNotification.get("bigText").toString());
//            } else if (innerNotification.get("text") != null) {
//                notiList.add(innerNotification.get("text").toString());
//            }
//        });
//        System.out.println(notiList);


    }

}



