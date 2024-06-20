package mobile.seller.products.create_product;

import api.Seller.setting.BranchManagement;
import api.Seller.setting.StoreInformation;
import io.appium.java_client.android.AndroidDriver;
import lombok.SneakyThrows;
import mobile.seller.login.LoginScreen;
import mobile.seller.products.child_screen.crud_variations.CRUDVariationScreen;
import mobile.seller.products.child_screen.edit_multiple.EditMultipleScreen;
import mobile.seller.products.child_screen.inventory.InventoryScreen;
import mobile.seller.products.child_screen.product_description.ProductDescriptionScreen;
import mobile.seller.products.child_screen.select_image_popup.SelectImagePopup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import utilities.assert_customize.AssertCustomize;
import utilities.commons.UICommonMobile;
import utilities.data.DataGenerator;
import utilities.model.dashboard.setting.branchInformation.BranchInfo;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

import static org.apache.commons.lang.math.JVMRandom.nextLong;
import static org.apache.commons.lang.math.RandomUtils.nextInt;
import static utilities.character_limit.CharacterLimit.MAX_PRICE;
import static utilities.environment.EnvironmentInformation.*;

public class CreateProductScreen extends CreateProductElement {
    WebDriver driver;
    AssertCustomize assertCustomize;
    UICommonMobile commonMobile;
    Logger logger = LogManager.getLogger();
    private static String defaultLanguage;
    private static BranchInfo branchInfo;

    public CreateProductScreen(WebDriver driver) {
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
    private boolean manageByIMEI = false;
    private boolean manageByLot = false;
    private boolean hasDiscount = true;
    private boolean hasCostPrice = true;
    private boolean hasDimension = true;
    private boolean showOnWeb = true;
    private boolean showOnApp = true;
    private boolean showInStore = true;
    private boolean showInGoSocial = true;
    private boolean hasPriority = false;

    public CreateProductScreen getHideRemainingStock(boolean hideRemainingStock) {
        this.hideRemainingStock = hideRemainingStock;
        return this;
    }

    public CreateProductScreen getShowOutOfStock(boolean showOutOfStock) {
        this.showOutOfStock = showOutOfStock;
        return this;
    }

    public CreateProductScreen getManageByIMEI(boolean manageByIMEI) {
        this.manageByIMEI = manageByIMEI;
        return this;
    }

    public CreateProductScreen getManageByLotDate(boolean manageByLot) {
        this.manageByLot = manageByLot;
        return this;
    }

    public CreateProductScreen getHasDiscount(boolean hasDiscount) {
        this.hasDiscount = hasDiscount;
        return this;
    }

    public CreateProductScreen getHasCostPrice(boolean hasCostPrice) {
        this.hasCostPrice = hasCostPrice;
        return this;
    }

    public CreateProductScreen getHasDimension(boolean hasDimension) {
        this.hasDimension = hasDimension;
        return this;
    }

    public CreateProductScreen getProductSellingPlatform(boolean showOnWeb, boolean showOnApp, boolean showInStore, boolean showInGoSocial) {
        this.showOnWeb = showOnWeb;
        this.showOnApp = showOnApp;
        this.showInStore = showInStore;
        this.showInGoSocial = showInGoSocial;
        return this;
    }

    public CreateProductScreen getHasPriority(boolean hasPriority) {
        this.hasPriority = hasPriority;
        return this;
    }

    private static long getCurrentEpoch() {
        return Instant.now().toEpochMilli();
    }

    public CreateProductScreen navigateToCreateProductScreen() {
        // Navigate to create product screen
        if (!((AndroidDriver) driver).currentActivity().equals(goSELLERCreateProductActivity)) {
            commonMobile.relaunchApp(goSELLERBundleId, goSELLERCreateProductActivity);
        }

        // Log
        logger.info("Navigate to create product screen.");

        return this;
    }

    void selectProductImages() {
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

    void selectManageInventory() {
        // Open manage inventory dropdown
        commonMobile.click(rsId_ddvSelectedManageType);

        // Select manage inventory type
        commonMobile.click(manageByIMEI ? rsId_ddvManagedByIMEI : rsId_ddvManagedByProduct);

        // Log
        logger.info("Manage inventory by: {}", manageByIMEI ? "IMEI/Serial number" : "Product");
    }

    void manageProductByLot() {
        // Get current manage by lot checkbox status
        boolean status = commonMobile.isChecked(commonMobile.getElement(rsId_chkManageByLot));

        // Manage product by lot
        if (manageByLot && !status && !manageByIMEI) commonMobile.click(rsId_chkManageByLot);

        // Log
        logger.info("Manage product by lot date: {}", manageByLot && !manageByIMEI || status);
    }

    void addWithoutVariationStock(int... branchStock) {
        // Check product is managed by lot or not
        if (!manageByLot || manageByIMEI) {
            // Navigate to inventory screen
            commonMobile.click(rsId_btnInventory);

            // Add without variation stock
            new InventoryScreen(driver).addStock(manageByIMEI, branchInfo, "", branchStock);
        } else logger.info("Product is managed by lot-date, requiring stock updates in the lot-date screen.");
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
        } else logger.info("Product do not have priority");
    }

    void addVariations() {
        // Navigate to Add/Edit variation
        commonMobile.click(rsId_swVariations);
        commonMobile.click(rsId_btnAddVariation);

        // Add/Edit variation
        new CRUDVariationScreen(driver).addVariation(defaultLanguage);
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

    void completeCreateProduct() {
        // Save all product information
        commonMobile.click(rsId_btnSave);

        // Wait product management screen loaded
        commonMobile.waitUntilScreenLoaded(goSELLERProductManagementActivity);
    }

    public void createProductWithoutVariation(int... branchStock) {
        selectProductImages();
        inputProductName();
        inputProductDescription();
        inputWithoutVariationPrice();
        inputWithoutVariationSKU();
        inputWithoutVariationBarcode();
        hideRemainingStockOnOnlineStore();
        displayIfOutOfStock();
        selectManageInventory();
        manageProductByLot();
        addWithoutVariationStock(branchStock);
        modifyShippingInformation();
        modifyProductSellingPlatform();
        modifyPriority();
        completeCreateProduct();
    }

    public void createProductWithVariation(int increaseNum, int... branchStock) {
        selectProductImages();
        inputProductName();
        inputProductDescription();
        hideRemainingStockOnOnlineStore();
        displayIfOutOfStock();
        selectManageInventory();
        manageProductByLot();
        modifyShippingInformation();
        modifyProductSellingPlatform();
        modifyPriority();
        addVariations();
        bulkUpdateVariations(increaseNum, branchStock);
        completeCreateProduct();
    }

    @AfterMethod
    void teardown() {
        new UICommonMobile(driver).relaunchApp(goSELLERBundleId, "");
    }
}
