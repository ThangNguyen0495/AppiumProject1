package mobile.seller.products.child_screen.product_variation;

import mobile.seller.products.child_screen.inventory.InventoryScreen;
import mobile.seller.products.child_screen.product_description.ProductDescriptionScreen;
import mobile.seller.products.child_screen.select_image_popup.SelectImagePopup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import utilities.assert_customize.AssertCustomize;
import utilities.commons.UICommonMobile;
import utilities.data.DataGenerator;
import utilities.model.dashboard.products.productInfomation.ProductInfo;
import utilities.model.dashboard.setting.branchInformation.BranchInfo;

import java.time.Instant;
import java.util.List;

import static org.apache.commons.lang.math.JVMRandom.nextLong;
import static org.apache.commons.lang.math.RandomUtils.nextBoolean;
import static utilities.character_limit.CharacterLimit.MAX_PRICE;
import static utilities.environment.goSELLEREnvironment.goSELLERProductDetailActivity;

public class ProductVariationScreen extends ProductVariationElement {
    WebDriver driver;
    AssertCustomize assertCustomize;
    UICommonMobile commonMobile;
    Logger logger = LogManager.getLogger();
    String defaultLanguage;
    boolean hasDiscount;
    boolean hasCostPrice;
    ProductInfo productInfo;
    int variationIndex;
    String variationValue;
    BranchInfo branchInfo;

    public ProductVariationScreen(WebDriver driver) {
        // Get driver
        this.driver = driver;

        // Init assert class
        assertCustomize = new AssertCustomize(driver);

        // Init commons class
        commonMobile = new UICommonMobile(driver);
    }

    private static long getCurrentEpoch() {
        return Instant.now().toEpochMilli();
    }

    public ProductVariationScreen getVariationInformation(String defaultLanguage, BranchInfo branchInfo, boolean hasDiscount, boolean hasCostPrice, int variationIndex, ProductInfo productInfo) {
        // Get default language
        this.defaultLanguage = defaultLanguage;

        // Get branch information
        this.branchInfo = branchInfo;

        // Get price condition
        this.hasDiscount = hasDiscount;

        // Get cost price condition
        this.hasCostPrice = hasCostPrice;

        // Get product information
        this.productInfo = productInfo;

        // Get variation index
        this.variationIndex = variationIndex;

        // Get variation value
        this.variationValue = productInfo.getVariationValuesMap().get(defaultLanguage).get(this.variationIndex);

        // Log
        logger.info("Update information of '{}' variation", variationValue);

        return this;
    }


    void selectVariationImages() {
        // Get list images
        List<String> imageFileNames = new DataGenerator().getAllFileNamesInFolder("images");

        // Sent list images to mobile device
        imageFileNames.forEach(fileName -> commonMobile.pushFileToMobileDevices(fileName));

        // Open select image popup
        commonMobile.click(rsId_btnSelectImage);

        // Select images
        new SelectImagePopup(driver).selectImages(imageFileNames);

        // Log
        logger.info("Select variation images.");
    }

    void updateVariationName() {
        // Input product name
        String name = "[%s][%s] Variation name %s".formatted(defaultLanguage, variationValue, getCurrentEpoch());
        commonMobile.sendKeys(rsId_txtVariationName, name);

        // Log
        logger.info("Input variation name: {}", name);
    }

    void updateVariationDescription() {
        // Get reuse description checkbox status
        boolean reuseParentDescription = nextBoolean();

        if (reuseParentDescription) {
            // Log
            logger.info("Reuse parent description");
        } else {
            // Get current reuse description checkbox status
            boolean status = commonMobile.isChecked( commonMobile.getElement(rsId_chkReuseProductDescription));

            // Uncheck reuse description checkbox
            if (status) commonMobile.click(rsId_chkReuseProductDescription);

            // Open description popup
            commonMobile.click(rsId_btnVariationDescription);

            // Input product description
            String description = "[%s][%s] Variation description %s".formatted(defaultLanguage, variationValue, getCurrentEpoch());
            new ProductDescriptionScreen(driver).inputDescription(description);

            // Log
            logger.info("Input variation description: {}", description);
        }

    }

    void updateVariationPrice() {
        // Input listing price
        long listingPrice = nextLong(MAX_PRICE);
        commonMobile.sendKeys(rsId_sctPrice, loc_txtVariationListingPrice, String.valueOf(listingPrice));
        logger.info("Input variation listing price: %,d".formatted(listingPrice));

        // Input selling price
        long sellingPrice = hasDiscount ? nextLong(Math.max(listingPrice, 1)) : listingPrice;
        commonMobile.sendKeys(rsId_sctPrice, loc_txtVariationSellingPrice, String.valueOf(sellingPrice));
        logger.info("Input variation selling price: %,d".formatted(sellingPrice));

        // Input cost price
        long costPrice = hasCostPrice ? nextLong(Math.max(sellingPrice, 1)) : 0;
        commonMobile.sendKeys(rsId_sctPrice, loc_txtVariationCostPrice, String.valueOf(costPrice));
        logger.info("Input variation cost price: %,d".formatted(costPrice));
    }

    void updateVariationSKU() {
        // Input variation SKU
        String sku = "SKU%s".formatted(getCurrentEpoch());
        commonMobile.sendKeys(rsId_txtVariationSKU, sku);

        // Log
        logger.info("Input variation SKU: {}", sku);
    }

    void updateVariationBarcode() {
        // Input variation barcode
        String barcode = "Barcode%s".formatted(getCurrentEpoch());
        commonMobile.sendKeys(rsId_txtVariationBarcode, barcode);

        // Log
        logger.info("Input variation barcode: {}", barcode);
    }

    void updateVariationStock(int... branchStock) {
        // Check product is managed by lot or not
        if (!productInfo.getLotAvailable() || productInfo.getManageInventoryByIMEI()) {
            // Navigate to inventory screen
            commonMobile.click(rsId_btnInventory);

            // Add variation stock
            new InventoryScreen(driver).updateStock(productInfo.getManageInventoryByIMEI(), branchInfo, variationValue, branchStock);
        } else logger.info("Product is managed by lot, requiring stock updates in the lot screen.");
    }

    void updateVariationStatus() {
        // Get new variation status
        String newStatus = nextBoolean() ? "ACTIVE" : "DEACTIVE";

        // Get current variation status
        String currentStatus = productInfo.getVariationStatus().get(variationIndex);

        // Update variation status
        if (!currentStatus.equals(newStatus)) {
            commonMobile.click(rsId_btnDeactivate);
        }

        // Log
        logger.info("New variation's status: {}", newStatus);
    }

    void completeUpdateVariation() {
        // Save all product information
        commonMobile.click(rsId_btnSave);

        // Wait product management screen loaded
        commonMobile.waitInvisible(rsId_prgLoading);

        // Verify that product management screen is shown
        assertCustomize.assertEquals(commonMobile.getCurrentActivity(),
                goSELLERProductDetailActivity,
                "Can not update variation");

        // Assert
        AssertCustomize.verifyTest();
    }

    public void updateVariationInformation(int... branchStock) {
        selectVariationImages();
        updateVariationName();
        updateVariationDescription();
        updateVariationPrice();
//        updateVariationSKU();
        updateVariationBarcode();
        updateVariationStock(branchStock);
        updateVariationStatus();
        completeUpdateVariation();
    }
}
