package mobile.seller.products.child_screen.edit_multiple;

import org.openqa.selenium.By;

import static utilities.environment.EnvironmentInformation.goSELLERBundleId;

public class EditMultipleElement {
    String rsId_ddvSelectedBranch = "%s:id/tvFilterBranches".formatted(goSELLERBundleId);
    String xpath_ddvBranch = "//*[@text = '%s']";
    By loc_lblActions = By.xpath("//android.widget.TextView[@* = '%s:id/tvAction']".formatted(goSELLERBundleId));
    By loc_lblUpdatePriceActions = By.xpath("(//*[@* = '%s:id/title'])[1]".formatted(goSELLERBundleId));
    By loc_lblUpdateStockActions = By.xpath("(//*[@* = '%s:id/title'])[2]".formatted(goSELLERBundleId));
}
