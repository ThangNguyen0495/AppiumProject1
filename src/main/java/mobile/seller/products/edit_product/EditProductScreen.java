package mobile.seller.products.edit_product;

import api.Seller.products.all_products.APIProductDetail;
import api.Seller.setting.BranchManagement;
import api.Seller.setting.StoreInformation;
import lombok.SneakyThrows;
import mobile.seller.login.LoginScreen;
import mobile.seller.products.child_screen.crud_variations.CRUDVariationScreen;
import mobile.seller.products.child_screen.edit_multiple.EditMultipleScreen;
import mobile.seller.products.child_screen.inventory.InventoryScreen;
import mobile.seller.products.child_screen.product_description.ProductDescriptionScreen;
import mobile.seller.products.child_screen.select_image_popup.SelectImagePopup;
import mobile.seller.products.product_management.ProductManagementScreen;
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
import java.util.Objects;

import static org.apache.commons.lang.math.JVMRandom.nextLong;
import static org.apache.commons.lang.math.RandomUtils.nextInt;
import static utilities.character_limit.CharacterLimit.MAX_PRICE;
import static utilities.environment.goSELLEREnvironment.goSELLERProductManagementActivity;

public class EditProductScreen extends EditProductElement {
    WebDriver driver;
    AssertCustomize assertCustomize;
    UICommonMobile commonMobile;
    Logger logger = LogManager.getLogger();
    private static String defaultLanguage;
    private static BranchInfo branchInfo;

    public EditProductScreen(WebDriver driver) {
        // Get driver
        this.driver = driver;

        // Init assert class
        assertCustomize = new AssertCustomize(driver);

        // Init commons class
        commonMobile = new UICommonMobile(driver);

        // Get store default language
        defaultLanguage = new StoreInformation(LoginScreen.getLoginInformation())
                .getInfo()
                .getDefaultLanguage();

        // Get branch information
        branchInfo = new BranchManagement(LoginScreen.getLoginInformation()).getInfo();
    }

    private boolean hideRemainingStock = false;
    private boolean showOutOfStock = true;
    private boolean manageByIMEI;
    private boolean manageByLot = false;
    private boolean hasDiscount = true;
    private boolean hasCostPrice = true;
    private boolean hasDimension = false;
    private boolean showOnWeb = true;
    private boolean showOnApp = true;
    private boolean showInStore = true;
    private boolean showInGoSocial = true;
    private boolean hasPriority = false;

    public EditProductScreen getHideRemainingStock(boolean hideRemainingStock) {
        this.hideRemainingStock = hideRemainingStock;
        return this;
    }

    public EditProductScreen getShowOutOfStock(boolean showOutOfStock) {
        this.showOutOfStock = showOutOfStock;
        return this;
    }

    public EditProductScreen getManageByLotDate(boolean manageByLot) {
        this.manageByLot = manageByLot;
        return this;
    }

    public EditProductScreen getHasDiscount(boolean hasDiscount) {
        this.hasDiscount = hasDiscount;
        return this;
    }

    public EditProductScreen getHasCostPrice(boolean hasCostPrice) {
        this.hasCostPrice = hasCostPrice;
        return this;
    }

    public EditProductScreen getHasDimension(boolean hasDimension) {
        this.hasDimension = hasDimension;
        return this;
    }

    public EditProductScreen getProductSellingPlatform(boolean showOnWeb, boolean showOnApp, boolean showInStore, boolean showInGoSocial) {
        this.showOnWeb = showOnWeb;
        this.showOnApp = showOnApp;
        this.showInStore = showInStore;
        this.showInGoSocial = showInGoSocial;
        return this;
    }

    public EditProductScreen getHasPriority(boolean hasPriority) {
        this.hasPriority = hasPriority;
        return this;
    }

    private static long getCurrentEpoch() {
        return Instant.now().toEpochMilli();
    }

    public EditProductScreen navigateToProductDetailScreen(int productId) {
        // Get product information
        ProductInfo productInfo = new APIProductDetail(LoginScreen.getLoginInformation()).getInfo(productId);

        // Get product name
        String productName = productInfo.getMainProductNameMap().get(defaultLanguage);

        // get inventory manage type
        manageByIMEI = productInfo.getManageInventoryByIMEI();

        // Init product management POM
        ProductManagementScreen productManagementScreen = new ProductManagementScreen(driver);

        // Navigate to product detail screen
        productManagementScreen.navigateToProductManagementScreen()
                .navigateToProductDetailScreen(productName);

        // Log
        logger.info("Navigate to product detail screen");

        // remove variation
        removeVariation();

        // Navigate to product detail screen
        productManagementScreen.navigateToProductManagementScreen()
                .navigateToProductDetailScreen(productName);

        // log
        logger.info("Navigate to product detail screen again");

        return this;
    }

    void clearOldData() {
//        // Remove product collections
//        while (commonMobile.isShown(rsId_btnAddCollection, loc_icnRemoveCollections)) {
//            commonMobile.click(rsId_btnAddCollection, loc_icnRemoveCollections);
//        }
//        logger.info("Remove old product collections");

        // Remove variation
        removeVariation();

//        // Log
//        logger.info("Remove old variations");
//
//        // Save changes
//        commonMobile.click(rsId_btnSave);
    }

    void selectProductImages() {
        // Remove product images
        while (commonMobile.isShown(loc_icnDeleteImages)) {
            commonMobile.click(loc_icnDeleteImages);
        }
        logger.info("Remove old product images");

        // Get list images
        List<String> imageFileNames = new DataGenerator().getAllFileNamesInFolder("images");

        // Sent list images to mobile device
        imageFileNames.forEach(fileName -> commonMobile.pushFileToMobileDevices(fileName));

        // Open select image popup
        commonMobile.click(rsId_btnSelectImage);

        // Select images
        new SelectImagePopup(driver).selectImages(imageFileNames);

        // Log
        logger.info("Select product images.");
    }

    void inputProductName() {
        // Input product name
        String name = "[%s] Product name %s".formatted(defaultLanguage, getCurrentEpoch());
        commonMobile.sendKeys(rsId_txtProductName, name);

        // Log
        logger.info("Input product name: {}", name);
    }

    void inputProductDescription() {
        // Open description popup
        commonMobile.click(rsId_btnProductDescription);

        // Input product description
        String description = "[%s] Product description %s".formatted(defaultLanguage, getCurrentEpoch());
        new ProductDescriptionScreen(driver).inputDescription(description);

        // Log
        logger.info("Input product description: {}", description);

    }

    void inputWithoutVariationPrice() {
        // Input listing price
        long listingPrice = nextLong(MAX_PRICE);
        commonMobile.sendKeys(rsId_sctPrice, loc_txtWithoutVariationListingPrice, String.valueOf(listingPrice));
        logger.info("Input without variation listing price: %,d".formatted(listingPrice));

        // Input selling price
        long sellingPrice = hasDiscount ? nextLong(Math.max(listingPrice, 1)) : listingPrice;
        commonMobile.sendKeys(rsId_sctPrice, loc_txtWithoutVariationSellingPrice, String.valueOf(sellingPrice));
        logger.info("Input without variation selling price: %,d".formatted(sellingPrice));

        // Input cost price
        long costPrice = hasCostPrice ? nextLong(Math.max(sellingPrice, 1)) : 0;
        commonMobile.sendKeys(rsId_sctPrice, loc_txtWithoutVariationCostPrice, String.valueOf(costPrice));
        logger.info("Input without variation cost price: %,d".formatted(costPrice));
    }

    void inputWithoutVariationSKU() {
        // Input without variation SKU
        String sku = "SKU%s".formatted(getCurrentEpoch());
        commonMobile.sendKeys(rsId_txtWithoutVariationSKU, sku);

        // Log
        logger.info("Input without variation SKU: {}", sku);
    }

    void inputWithoutVariationBarcode() {
        // Input without variation barcode
        String barcode = "Barcode%s".formatted(getCurrentEpoch());
        commonMobile.sendKeys(rsId_txtWithoutVariationBarcode, barcode);

        // Log
        logger.info("Input without variation barcode: {}", barcode);
    }

    @SneakyThrows
    void hideRemainingStockOnOnlineStore() {
        // Get current checkbox status
        boolean status = commonMobile.isChecked(commonMobile.getElement(rsId_chkHideRemainingStock));

        // Hide remaining stock on online store config
        if (!Objects.equals(hideRemainingStock, status)) commonMobile.click(rsId_chkHideRemainingStock);

        // Log
        logger.info("Hide remaining stock on online store config: {}", hideRemainingStock);
    }

    @SneakyThrows
    void displayIfOutOfStock() {
        // Get current checkbox status
        boolean status = commonMobile.isChecked(commonMobile.getElement(rsId_chkShowOutOfStock));

        // Add display out of stock config
        if (!Objects.equals(showOutOfStock, status)) commonMobile.click(rsId_chkShowOutOfStock);

        // Log
        logger.info("Display out of stock config: {}", showOutOfStock);
    }

    void manageProductByLot() {
        if (!manageByIMEI) {
            // Get current manage by lot checkbox status
            boolean status = commonMobile.isChecked(commonMobile.getElement(rsId_chkManageByLot));

            // Manage product by lot
            if (manageByLot && !status) commonMobile.click(rsId_chkManageByLot);

            // Log
            logger.info("Manage product by lot date: {}", manageByLot);
        } else logger.info("Lot only support for the product has inventory managed by product");
    }

    void addWithoutVariationStock(int... branchStock) {
        // Check product is managed by lot or not
        if (!manageByLot || manageByIMEI) {
            // Navigate to inventory screen
            commonMobile.click(rsId_btnInventory);

            // Add without variation stock
            new InventoryScreen(driver).updateStock(manageByIMEI, branchInfo, "", branchStock);
        } else logger.info("Product is managed by lot, requiring stock updates in the lot screen.");
    }

    void modifyShippingInformation() {
        // Get current shipping config status
        boolean status = commonMobile.isChecked(commonMobile.getElement(rsId_swShipping));

        // Update shipping status
        if (!Objects.equals(hasDimension, status)) commonMobile.click(rsId_swShipping);

        // If product has dimension, add shipping configuration
        // Add product weight
        if (hasDimension) {
            commonMobile.sendKeys(rsId_txtWeight, "10");
            logger.info("Add product weight: 10g");

            // Add product length
            commonMobile.sendKeys(rsId_txtLength, "10");
            logger.info("Add product length: 10cm");

            // Add product width
            commonMobile.sendKeys(rsId_txtWidth, "10");
            logger.info("Add product width: 10cm");

            // Add product height
            commonMobile.sendKeys(rsId_txtHeight, "10");
            logger.info("Add product height: 10cm");
        } else logger.info("Product do not have shipping information.");
    }

    void modifyProductSellingPlatform() {
        /* WEB PLATFORM */
        // Get current show on web status
        boolean webStatus = commonMobile.isChecked(commonMobile.getElement(rsId_swWebPlatform));

        // Modify show on web config
        if (!Objects.equals(showOnWeb, webStatus)) commonMobile.click(rsId_swWebPlatform);

        // Log
        logger.info("On web configure: {}", showOnWeb);

        /* APP PLATFORM */
        // Get current show on app status
        boolean appStatus = commonMobile.isChecked(commonMobile.getElement(rsId_swAppPlatform));

        // Modify show on app config
        if (!Objects.equals(showOnApp, appStatus)) commonMobile.click(rsId_swAppPlatform);

        // Log
        logger.info("On app configure: {}", showOnApp);

        /* IN-STORE PLATFORM */
        // Get current show in-store status
        boolean inStoreStatus = commonMobile.isChecked(commonMobile.getElement(rsId_swInStorePlatform));

        // Modify show in-store config
        if (!Objects.equals(showInStore, inStoreStatus)) commonMobile.click(rsId_swInStorePlatform);

        // Log
        logger.info("In store configure: {}", showInStore);

        /* GO SOCIAL PLATFORM */
        // Get current show in goSocial status
        boolean goSocialStatus = commonMobile.isChecked(commonMobile.getElement(rsId_swGoSocialPlatform));

        // Modify show in goSocial config
        if (!Objects.equals(showInGoSocial, goSocialStatus)) commonMobile.click(rsId_swGoSocialPlatform);

        // Log
        logger.info("In goSOCIAL configure: {}", showInGoSocial);
    }

    void modifyPriority() {
        // Get current priority config status
        boolean status = commonMobile.isChecked(commonMobile.getElement(rsId_swPriority));

        // Update priority config
        if (!Objects.equals(hasPriority, status)) commonMobile.click(rsId_swPriority);

        // If product has priority, add priority
        if (hasPriority) {
            // Input priority
            int priority = nextInt(100);
            commonMobile.sendKeys(rsId_txtPriority, String.valueOf(priority));

            // Log
            logger.info("Product priority: {}", priority);
        } else logger.info("Product do not have priority configure");
    }

    void addVariations() {
        // Navigate to Add/Edit variation
        commonMobile.click(rsId_swVariations);
        commonMobile.click(rsId_btnAddVariation);

        // Add/Edit variation
        new CRUDVariationScreen(driver).addVariation(defaultLanguage);
    }

    void removeVariation() {
        // If product has variation, remove old variation
        if (commonMobile.isShown(rsId_btnAddVariation)) {
            // Navigate to Add/Edit variation
            commonMobile.click(rsId_btnAddVariation);

            // Remove all variations and save changes
            new CRUDVariationScreen(driver).removeOldVariation()
                    .saveChanges();

            // Log
            logger.info("Remove old variations");

            // Save changes
            commonMobile.click(rsId_btnSave);
        }
    }

    void bulkUpdateVariations(int increaseNum, int... branchStock) {
        // Get total variations
        int totalVariations = CRUDVariationScreen.getVariationMap().values().stream().mapToInt(List::size).reduce(1, (a, b) -> a * b);

        // Navigate to edit multiple screen
        if (totalVariations > 1) {
            commonMobile.click(rsId_btnEditMultiple);

            // Init edit multiple model
            EditMultipleScreen editMultipleScreen = new EditMultipleScreen(driver);

            // Bulk update price
            editMultipleScreen.bulkUpdatePrice(hasDiscount);

            // Bulk update stock
            editMultipleScreen.bulkUpdateStock(manageByIMEI, manageByLot, branchInfo, increaseNum, branchStock);

            // Save changes

        } else {
            // When total variations = 1, Edit multiple button is hidden
            logger.info("Can not bulk actions when total of variations is 1.");
        }

    }

    void completeUpdateProduct() {
        // Save all product information
        commonMobile.click(rsId_btnSave);

        // Wait product management screen loaded
        commonMobile.waitInvisible(rsId_prgLoading);

        // Verify that product management screen is shown
        assertCustomize.assertEquals(commonMobile.getCurrentActivity(),
                goSELLERProductManagementActivity,
                "Can not update product");

        // Assert
        AssertCustomize.verifyTest();
    }

    public void updateProductWithoutVariation(int... branchStock) {
        selectProductImages();
        inputProductName();
        inputProductDescription();
        inputWithoutVariationPrice();
//        inputWithoutVariationSKU();
        inputWithoutVariationBarcode();
        hideRemainingStockOnOnlineStore();
        displayIfOutOfStock();
        manageProductByLot();
        addWithoutVariationStock(branchStock);
        modifyShippingInformation();
        modifyProductSellingPlatform();
        modifyPriority();
        completeUpdateProduct();
    }

    public void updateProductWithVariation(int increaseNum, int... branchStock) {
        selectProductImages();
        inputProductName();
        inputProductDescription();
        hideRemainingStockOnOnlineStore();
        displayIfOutOfStock();
        manageProductByLot();
        modifyShippingInformation();
        modifyProductSellingPlatform();
        modifyPriority();
        addVariations();
        bulkUpdateVariations(increaseNum, branchStock);
        completeUpdateProduct();
    }
}
