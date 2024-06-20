package mobile.seller.products.child_screen.inventory;

import org.openqa.selenium.By;

import static utilities.environment.EnvironmentInformation.goSELLERBundleId;

public class InventoryElement {
    By loc_txtBranchStock = By.xpath("//*[@* = '%s:id/edtStock']".formatted(goSELLERBundleId));
    String rsId_btnSave = "%s:id/tvActionBarIconRight".formatted(goSELLERBundleId);
}
