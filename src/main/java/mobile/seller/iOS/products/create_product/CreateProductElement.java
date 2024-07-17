package mobile.seller.iOS.products.create_product;

import org.openqa.selenium.By;

public class CreateProductElement {
    By loc_icnProductImage = By.xpath("//XCUIElementTypeImage[@name=\"icon_selected_image_default\"]");
    By loc_txtProductName = By.xpath("//XCUIElementTypeTextField[@value=\"Input product name\"]");
    By loc_txtProductDescription = By.xpath("//XCUIElementTypeStaticText[@name=\"Input product description\"]");
    By loc_txtWithoutVariationListingPrice = By.xpath("//XCUIElementTypeTextField[@value=\"Input listing price\"]");
    By loc_txtWithoutVariationSellingPrice = By.xpath("//XCUIElementTypeTextField[@value=\"Input selling price\"]");
    By loc_txtWithoutVariationCostPrice = By.xpath("//XCUIElementTypeTextField[@value=\"Input cost price\"]");
    By loc_icnShowMoreVAT = By.xpath("name == \"ic_arrow_drop_down_gray\"");

    By loc_ddvVAT(String vatName) {
        return By.xpath("//XCUIElementTypeStaticText[@name=\"%s\"]".formatted(vatName));
    }

    By loc_txtWithoutVariationSKU = By.xpath("//XCUIElementTypeTextField[@value=\"Input product SKU\"]");
    By loc_txtWithoutVariationBarcode = By.xpath("//XCUIElementTypeStaticText[@name=\"Barcode\"]//following-sibling::XCUIElementTypeTextField");
    public static By loc_chkDisplayIfOutOfStock = By.xpath("//XCUIElementTypeStaticText[@name=\"Display if out of stock\"]//preceding-sibling::XCUIElementTypeOther");
    By loc_chkShowAsListingProduct = By.xpath("//XCUIElementTypeStaticText[@name=\"Show as listing product on store front\"]//preceding-sibling::XCUIElementTypeOther");
    By loc_chkHideRemainingStock = By.xpath("//XCUIElementTypeStaticText[@name=\"Hide remaining stock on online store\"]//preceding-sibling::XCUIElementTypeOther");
    By loc_ddvSelectedManageInventoryType = By.xpath("//XCUIElementTypeStaticText[@name=\"Manage Inventory\"]/following-sibling::XCUIElementTypeButton");
    By loc_ddvManageInventoryByIMEI = By.xpath("//XCUIElementTypeStaticText[@name=\"Manage inventory by IMEI/Serial number\"]");
    By loc_chkManageStockByLotDate = By.xpath("//XCUIElementTypeStaticText[@name=\"Manage stock by Lot-date\"]//preceding-sibling::XCUIElementTypeOther");
    By loc_btnInventory = By.xpath("//XCUIElementTypeImage[@name=\"icon_inventory\"]//parent::*");
    By loc_swShipping = By.xpath("//XCUIElementTypeImage[@name=\"icon_truck\"]//following-sibling::XCUIElementTypeSwitch");
    By loc_txtWeight = By.xpath("//XCUIElementTypeStaticText[@name=\"Weight\"]//following-sibling::XCUIElementTypeTextField");
    By loc_txtLength = By.xpath("//XCUIElementTypeStaticText[@name=\"Length\"]//following-sibling::XCUIElementTypeTextField");
    By loc_txtWidth = By.xpath("//XCUIElementTypeStaticText[@name=\"Width\"]//following-sibling::XCUIElementTypeTextField");
    By loc_txtHeight = By.xpath("//XCUIElementTypeStaticText[@name=\"Height\"]//following-sibling::XCUIElementTypeTextField");
    By loc_swPriority = By.xpath("//XCUIElementTypeStaticText[@name=\"Priority\"]//following-sibling::XCUIElementTypeSwitch");
    By loc_txtPriority = By.xpath("//XCUIElementTypeTextField[@value=\"Please input a number\"]");
    By loc_swWeb = By.xpath("//XCUIElementTypeStaticText[@name=\"Web\"]//following-sibling::XCUIElementTypeSwitch");
    By loc_swApp = By.xpath("//XCUIElementTypeStaticText[@name=\"App\"]//following-sibling::XCUIElementTypeSwitch");
    By loc_swInStore = By.xpath("//XCUIElementTypeStaticText[@name=\"In-store\"]//following-sibling::XCUIElementTypeSwitch");
    By loc_swGoSocial = By.xpath("//XCUIElementTypeStaticText[@name=\"GoSocial\"]//following-sibling::XCUIElementTypeSwitch");
    By loc_swVariation = By.xpath("//XCUIElementTypeStaticText[@name=\"Variations\"]//following-sibling::XCUIElementTypeSwitch");
    By loc_btnAddVariation = By.xpath("//XCUIElementTypeStaticText[@name=\"Add Variation\"]");
    By loc_btnEditMultiple = By.xpath("//XCUIElementTypeStaticText[@name=\"Edit multiple\"]");
    By loc_lstVariations = By.xpath("//XCUIElementTypeStaticText[contains(@name, \"available\")]");
    By loc_btnSave = By.xpath("//XCUIElementTypeButton[@name=\"icon checked white\"]");
}
