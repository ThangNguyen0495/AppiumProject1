package mobile.seller.products.child_screen.product_description;

import org.openqa.selenium.By;

import static utilities.environment.EnvironmentInformation.goSELLERBundleId;

public class ProductDescriptionElement {
    By loc_txtContent = By.className("android.widget.EditText");
    String rsId_btnSave = "%s:id/ivActionBarIconRight".formatted(goSELLERBundleId);
}
