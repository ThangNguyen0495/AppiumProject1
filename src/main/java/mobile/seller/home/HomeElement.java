package mobile.seller.home;

import org.openqa.selenium.By;

import java.util.Arrays;
import java.util.List;

import static utilities.environment.EnvironmentInformation.goSELLERBundleId;

public class HomeElement {
    String str_resourceIdQuickAccess = "%s:id/rlvQuickActions".formatted(goSELLERBundleId);

    public enum QuickActions {
        createNewOrder, createReservation, addProduct, addNewCustomer, scanProduct, liveStream, facebook, zaloOA;

        static List<QuickActions> getAllActions() {
            return Arrays.asList(QuickActions.values());
        }
    }

    By loc_btnQuickAccessActions = By.xpath("//*[@resource-id = '%s:id/rlvQuickActions']//*[@resource-id ='%s:id/ivIcon']".formatted(goSELLERBundleId, goSELLERBundleId));
    By loc_tabAccount = By.xpath("//*[contains(@resource-id, 'bottom_navigation_tab_account')]");
    By loc_btnLogout = By.xpath("//*[contains(@resource-id, 'llLogout')]");
    By loc_btnOK = By.xpath("//*[contains(@resource-id, 'tvRightButton')]");
    By loc_btnLogoutAbort = By.xpath("//*[contains(@resource-id, 'tvLeftButton')]");
    By loc_icnAddProduct = By.xpath("//android.widget.TextView[@text='Thêm sản phẩm']");
}
