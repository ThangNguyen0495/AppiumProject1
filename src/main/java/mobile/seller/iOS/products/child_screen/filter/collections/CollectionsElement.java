package mobile.seller.iOS.products.child_screen.filter.collections;

import org.openqa.selenium.By;

import static utilities.environment.goSELLEREnvironment.goSELLERBundleId;

public class CollectionsElement {
    By loc_btnAllCollections = By.xpath("//XCUIElementTypeStaticText[@name=\"All collections\"]");
    By loc_btnCollection(String collectionName) {
        return By.xpath("//XCUIElementTypeStaticText[@name=\"%s\"]".formatted(collectionName));
    }
}
