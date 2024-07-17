package mobile.seller.iOS.products.child_screen.product_description;

import org.openqa.selenium.By;

import static utilities.environment.goSELLEREnvironment.goSELLERBundleId;

public class ProductDescriptionElement {
    By loc_txtContent = By.xpath("//XCUIElementTypeTextField");
    By loc_btnSave = By.xpath("//XCUIElementTypeButton[@name=\"Item\"]");
}
