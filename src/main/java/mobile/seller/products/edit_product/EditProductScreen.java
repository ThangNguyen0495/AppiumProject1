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
import mobile.seller.products.child_screen.product_variation.ProductVariationScreen;
import mobile.seller.products.child_screen.select_image_popup.SelectImagePopup;
import mobile.seller.products.product_management.ProductManagementScreen;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import utilities.assert_customize.AssertCustomize;
import utilities.commons.UICommonMobile;
import utilities.data.DataGenerator;
import utilities.model.dashboard.products.productInfomation.ProductInfo;
import utilities.model.dashboard.setting.branchInformation.BranchInfo;
import utilities.model.dashboard.setting.storeInformation.StoreInfo;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    private ProductInfo productInfo;
    ProductManagementScreen productManagementScreen;

    public EditProductScreen(WebDriver driver) {
        // Get driver
        this.driver = driver;

        // Init assert class
        assertCustomize = new AssertCustomize(driver);

        // Init commons class
        commonMobile = new UICommonMobile(driver);

        // Init product management POM
        productManagementScreen = new ProductManagementScreen(driver);

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

    private boolean hasLot;
    private APIProductDetail apiProductDetail;

    public EditProductScreen navigateToProductDetailScreen(int productId) {
        // Get product information
        apiProductDetail = new APIProductDetail(LoginScreen.getLoginInformation());
        this.productInfo = apiProductDetail.getInfo(productId);

        // Get lot manage status
        this.hasLot = productInfo.getLotAvailable();

        // Get product name
        String productName = productInfo.getMainProductNameMap().get(defaultLanguage);

        // get inventory manage type
        manageByIMEI = productInfo.getManageInventoryByIMEI();

        // Navigate to product detail screen
        productManagementScreen.navigateToProductManagementScreen()
                .navigateToProductDetailScreen(productName);

        // Log
        logger.info("Navigate to product detail screen");

        return this;
    }

    void removeOldVariations() {
        // Get product name
        String productName = productInfo.getMainProductNameMap().get(defaultLanguage);

        // If product has model, remove model and saves changes.
        if (productInfo.isHasModel() && !productInfo.getLotAvailable()) {

            // remove variation
            removeVariation();

            // Navigate to product detail screen
            productManagementScreen.navigateToProductManagementScreen()
                    .navigateToProductDetailScreen(productName);

            // log
            logger.info("Remove old variation and navigate to product detail again");
        }
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
        String name = "[%s][%s] Product name %s".formatted(defaultLanguage, manageByIMEI ? "IMEI" : "NORMAL", getCurrentEpoch());
        commonMobile.sendKeys(rsId_txtProductName, name);

        // Get new product name
        Map<String, String> mainNameMap = new HashMap<>(productInfo.getMainProductNameMap());
        mainNameMap.put(defaultLanguage, name);
        productInfo.setMainProductNameMap(mainNameMap);

        // Log
        logger.info("Input product name: {}", name);
    }

    void inputProductDescription() {
        // Open description popup
        commonMobile.click(rsId_btnProductDescription);

        // Input product description
        String description = "[%s] Product description %s".formatted(defaultLanguage, getCurrentEpoch());
        new ProductDescriptionScreen(driver).inputDescription(description);

        // Get new product description
        Map<String, String> mainDescriptionMap = new HashMap<>(productInfo.getMainProductDescriptionMap());
        mainDescriptionMap.put(defaultLanguage, description);
        productInfo.setMainProductDescriptionMap(mainDescriptionMap);

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

        // Get new product price
        productInfo.setProductListingPrice(List.of(listingPrice));
        productInfo.setProductSellingPrice(List.of(sellingPrice));
        productInfo.setProductCostPrice(List.of(costPrice));
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

        // Get new barcode
        productInfo.setBarcodeList(List.of(barcode));
    }

    @SneakyThrows
    void hideRemainingStockOnOnlineStore() {
        // Get current checkbox status
        boolean status = commonMobile.isChecked(commonMobile.getElement(rsId_chkHideRemainingStock));

        // Hide remaining stock on online store config
        if (!Objects.equals(hideRemainingStock, status)) commonMobile.click(rsId_chkHideRemainingStock);

        // Log
        logger.info("Hide remaining stock on online store config: {}", hideRemainingStock);

        // Get new hide remaining stock config
        productInfo.setHideStock(hideRemainingStock);
    }

    @SneakyThrows
    void displayIfOutOfStock() {
        // Get current checkbox status
        boolean status = commonMobile.isChecked(commonMobile.getElement(rsId_chkShowOutOfStock));

        // Add display out of stock config
        if (!Objects.equals(showOutOfStock, status)) commonMobile.click(rsId_chkShowOutOfStock);

        // Log
        logger.info("Display out of stock config: {}", showOutOfStock);

        // Get new show out of stock config
        productInfo.setShowOutOfStock(showOutOfStock);
    }

    void manageProductByLot() {
        if (!manageByIMEI) {
            // Get current manage by lot checkbox status
            boolean status = !commonMobile.isEnabled(rsId_chkManageByLot);

            // Manage product by lot
            if (manageByLot && !status) commonMobile.click(rsId_chkManageByLot);

            // Log
            logger.info("Manage product by lot date: {}", manageByLot || status);

            // Get new lot available
            productInfo.setLotAvailable(manageByLot || status);
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

        // Get new stock quantity
        List<Integer> stockQuantity = IntStream.range(0, branchInfo.getBranchID().size())
                .mapToObj(branchIndex -> productInfo.getLotAvailable() ? 0 : ((branchIndex >= branchStock.length) ? 0 : branchStock[branchIndex]))
                .toList();
        productInfo.setProductStockQuantityMap(Map.of(String.valueOf(productInfo.getProductId()), stockQuantity));
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

        // Get new platform config
        productInfo.setOnApp(showOnApp);
        productInfo.setOnWeb(showOnWeb);
        productInfo.setInGoSocial(showInGoSocial);
        productInfo.setInStore(showInStore);
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
        // If product is managed by Lot, that is not allow to remove variation
        if (this.hasLot) {
            logger.info("Product that is managed by Lot, do not allow add variation");
        } else {
            // Else navigate to Add/Edit variation screen to add new variation
            commonMobile.click(rsId_swVariations);
            commonMobile.click(rsId_btnAddVariation);

            // Add/Edit variation
            new CRUDVariationScreen(driver).addVariation(defaultLanguage);

            // Get variation map
            Map<String, List<String>> variationMap = CRUDVariationScreen.getVariationMap();
            String variationGroupName = variationMap.keySet().toString().replaceAll("[\\[\\]\\s]", "").replaceAll(",", "|");
            List<String> variationValueList = new DataGenerator().getVariationList(variationMap);

            // Get store information
            StoreInfo storeInfo = new StoreInformation(LoginScreen.getLoginInformation()).getInfo();

            // Init variation group map
            Map<String, String> groupMap = new HashMap<>();

            // Init variation value map
            Map<String, List<String>> valueMap = new HashMap<>();

            // Get variation group/value
            storeInfo.getStoreLanguageList()
                    .parallelStream()
                    .forEach(languageKey -> {
                        groupMap.put(languageKey, variationGroupName);
                        valueMap.put(languageKey, variationValueList);
                    });

            // Get new variation information
            productInfo.setVariationGroupNameMap(groupMap);
            productInfo.setVariationValuesMap(valueMap);
        }
    }

    void removeVariation() {
        // Move into variation section
        commonMobile.getElement(rsId_lblVariation);

        // If product is managed by Lot, that is not allow to remove variation
        if (this.hasLot) {
            logger.info("Product that is managed by Lot, do not allow remove variation");
        }
        // If product has variation, remove old variation
        else if (commonMobile.isShown(loc_imgVariation)) {
            // Navigate to Add/Edit variation
            commonMobile.click(rsId_btnAddVariation);

            // Remove all variations and save changes
            new CRUDVariationScreen(driver).removeOldVariation()
                    .saveChanges();

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
            long listingPrice = nextLong(MAX_PRICE);
            long sellingPrice = hasDiscount ? nextLong(Math.max(listingPrice, 1)) : listingPrice;
            editMultipleScreen.bulkUpdatePrice(listingPrice, sellingPrice);

            // Get new product price
            productInfo.setProductListingPrice(IntStream.range(0, totalVariations).mapToLong(varIndex -> listingPrice).boxed().toList());
            productInfo.setProductSellingPrice(IntStream.range(0, totalVariations).mapToLong(varIndex -> sellingPrice).boxed().toList());
            productInfo.setProductCostPrice(IntStream.range(0, totalVariations).mapToLong(varIndex -> 0).boxed().toList());

            // Bulk update stock
            editMultipleScreen.bulkUpdateStock(manageByIMEI, manageByLot, branchInfo, increaseNum, branchStock);

            // Get new stock quantity
            List<Integer> stockQuantity = IntStream.range(0, branchInfo.getBranchID().size())
                    .mapToObj(branchIndex -> ((branchIndex >= branchStock.length) ? 0 : branchStock[branchIndex]) + branchIndex * increaseNum)
                    .toList();
            Map<String, List<Integer>> stockMap = IntStream.range(0, totalVariations).boxed().collect(Collectors.toMap(String::valueOf, variationIndex -> stockQuantity, (a, b) -> b));
            productInfo.setProductStockQuantityMap(stockMap);

        } else {
            // Can not bulk actions when total of variations is 1
            // So we must be updated variation information at that's detail screen
            updateVariationInformation(branchStock);
        }

    }

    void completeUpdateProduct() {
        // Save all product information
        commonMobile.click(rsId_btnSave);

        // If product are managed by lot, accept when warning shows
        if (commonMobile.isShown(rsId_dlgWarningManagedByLot_btnOK)) {
            commonMobile.click(rsId_dlgWarningManagedByLot_btnOK);

            // Log
            logger.info("Confirm managed by lot");
        }

        // Wait product management screen loaded
        commonMobile.waitInvisible(rsId_prgLoading);
        commonMobile.waitUntilScreenLoaded(goSELLERProductManagementActivity);

        // If product are updated, check information after updating
        // Get current product information
        ProductInfo currentInfo = apiProductDetail.getInfo(productInfo.getProductId());

        // Check main product name
        assertCustomize.assertEquals(productInfo.getMainProductNameMap(), currentInfo.getMainProductNameMap(),
                "Main product name must be %s, but found %s".formatted(productInfo.getMainProductNameMap(), currentInfo.getMainProductNameMap()));

        // Check main product description
        assertCustomize.assertEquals(productInfo.getMainProductDescriptionMap(), currentInfo.getMainProductDescriptionMap(),
                "Main product description must be %s, but found %s".formatted(productInfo.getMainProductDescriptionMap(), currentInfo.getMainProductDescriptionMap()));

        // Check product listing price
        assertCustomize.assertEquals(productInfo.getProductListingPrice(), currentInfo.getProductListingPrice(),
                "Product listing price must be %s, but found %s".formatted(productInfo.getProductListingPrice(), currentInfo.getProductListingPrice()));

        // Check product selling price
        assertCustomize.assertEquals(productInfo.getProductSellingPrice(), currentInfo.getProductSellingPrice(),
                "Product selling price must be %s, but found %s".formatted(productInfo.getProductSellingPrice(), currentInfo.getProductSellingPrice()));

        // Check product cost price
        assertCustomize.assertEquals(productInfo.getProductCostPrice(), currentInfo.getProductCostPrice(),
                "Product cost price must be %s, but found %s".formatted(productInfo.getProductCostPrice(), currentInfo.getProductCostPrice()));

        // Check product barcode
        if (!productInfo.isHasModel() || updateVariationInformation) {
            assertCustomize.assertEquals(productInfo.getBarcodeList(), currentInfo.getBarcodeList(),
                    "Product barcode must be %s, but found %s".formatted(productInfo.getBarcodeList(), currentInfo.getBarcodeList()));
        }

        // Check online store config
        assertCustomize.assertEquals(productInfo.getShowOutOfStock(), currentInfo.getShowOutOfStock(),
                "Show when out of stock config must be %s, but found %s".formatted(productInfo.getShowOutOfStock(), currentInfo.getShowOutOfStock()));
        assertCustomize.assertEquals(productInfo.isHideStock(), currentInfo.isHideStock(),
                "Hide remaining stock config must be %s, but found %s".formatted(productInfo.isHideStock(), currentInfo.isHideStock()));

        // Check inventory
        assertCustomize.assertEquals(productInfo.getLotAvailable(), currentInfo.getLotAvailable(),
                "Manage by lot must be %s, but found %s".formatted(productInfo.getLotAvailable(), currentInfo.getLotAvailable()));

        // Check stock quantity
        assertCustomize.assertTrue(CollectionUtils.isEqualCollection(productInfo.getProductStockQuantityMap().values(), currentInfo.getProductStockQuantityMap().values()),
                "Product stock quantity must be %s, but found %s".formatted(productInfo.getProductStockQuantityMap().values(), currentInfo.getProductStockQuantityMap().values()));

        // Check selling platform
        assertCustomize.assertEquals(productInfo.getOnWeb(), currentInfo.getOnWeb(),
                "Web config must be %s, but found %s".formatted(productInfo.getOnWeb(), currentInfo.getOnWeb()));
        assertCustomize.assertEquals(productInfo.getOnApp(), currentInfo.getOnApp(),
                "App config must be %s, but found %s".formatted(productInfo.getOnApp(), currentInfo.getOnApp()));
        assertCustomize.assertEquals(productInfo.getInStore(), currentInfo.getInStore(),
                "In-store config must be %s, but found %s".formatted(productInfo.getInStore(), currentInfo.getInStore()));
        assertCustomize.assertEquals(productInfo.getInGoSocial(), currentInfo.getInGoSocial(),
                "In GoSOCIAL config must be %s, but found %s".formatted(productInfo.getInGoSocial(), currentInfo.getInGoSocial()));

        // Check variation information
        if (updateVariationInformation) {
            List<String> actualVersionNames = productInfo.getVersionNameMap().values().stream().map(map -> map.get(defaultLanguage)).toList();
            List<String> expectedVersionNames = currentInfo.getVersionNameMap().values().stream().map(map -> map.get(defaultLanguage)).toList();
            assertCustomize.assertTrue(CollectionUtils.isEqualCollection(actualVersionNames, expectedVersionNames),
                    "Variation version name must be %s, but found %s".formatted(expectedVersionNames, actualVersionNames));

            List<String> actualVersionDescriptions = productInfo.getVersionNameMap().values().stream().map(map -> map.get(defaultLanguage)).toList();
            List<String> expectedVersionDescriptions = currentInfo.getVersionNameMap().values().stream().map(map -> map.get(defaultLanguage)).toList();
            assertCustomize.assertTrue(CollectionUtils.isEqualCollection(actualVersionDescriptions, expectedVersionDescriptions),
                    "Variation version description must be %s, but found %s".formatted(expectedVersionDescriptions, actualVersionDescriptions));

            assertCustomize.assertEquals(productInfo.getVariationStatus(), currentInfo.getVariationStatus(),
                    "Variation status must be %s, but found %s".formatted(productInfo.getVariationStatus(), currentInfo.getVariationStatus()));
        }

        // Assert
        AssertCustomize.verifyTest();
    }

    public void updateProductWithoutVariation(int... branchStock) {

        removeOldVariations();
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
        removeOldVariations();
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

    public void updateEachVariationInformation(int... branchStock) {
        // Update variation information
        updateVariationInformation(branchStock);

        // Save changes
        completeUpdateProduct();
    }

    boolean updateVariationInformation = false;

    void updateVariationInformation(int... branchStock) {
        // Set update variation information flag
        updateVariationInformation = true;

        // Init variation POM
        ProductVariationScreen productVariationScreen = new ProductVariationScreen(driver);

        // Init variation information model
        List<ProductVariationScreen.VariationInfo> variationInfo = new ArrayList<>();

        // Update variation information
        IntStream.range(0, productInfo.getVariationValuesMap().get(defaultLanguage).size()).forEach(variationIndex -> {
            // Navigate to variation detail screen
            commonMobile.click(rsId_lblVariation, loc_imgVariation, variationIndex);

            // Update variation information
            productVariationScreen.getVariationInformation(defaultLanguage, branchInfo, hasDiscount, hasCostPrice, variationIndex, productInfo)
                    .updateVariationInformation(branchStock);

            variationInfo.add(ProductVariationScreen.getVariationInfo());
        });

        // Init variation information
        Map<String, Map<String, String>> versionNameMap = new HashMap<>();
        Map<String, Map<String, String>> versionDescriptionMap = new HashMap<>();
        Map<String, List<Integer>> stockQuantityMap = new HashMap<>();
        List<Long> listingPrices = new ArrayList<>();
        List<Long> sellingPrices = new ArrayList<>();
        List<Long> costPrices = new ArrayList<>();
        List<String> barcodes = new ArrayList<>();
        List<String> status = new ArrayList<>();

        // Get new variation information
        for (ProductVariationScreen.VariationInfo info : variationInfo) {
            versionNameMap.put(info.getVariation(), Map.of(defaultLanguage, info.getName()));
            versionDescriptionMap.put(info.getVariation(), Map.of(defaultLanguage, info.getDescription()));
            stockQuantityMap.put(info.getVariation(), info.getStockQuantity());
            listingPrices.add(info.getListingPrice());
            sellingPrices.add(info.getSellingPrice());
            costPrices.add(info.getCostPrice());
            barcodes.add(info.getBarcode());
            status.add(info.getStatus());
        }
        productInfo.setVersionNameMap(versionNameMap);
        productInfo.setVersionDescriptionMap(versionDescriptionMap);
        if (!hasLot) productInfo.setProductStockQuantityMap(stockQuantityMap);
        productInfo.setProductListingPrice(listingPrices);
        productInfo.setProductSellingPrice(sellingPrices);
        productInfo.setProductCostPrice(costPrices);
        productInfo.setBarcodeList(barcodes);
        productInfo.setVariationStatus(status);
    }
}
