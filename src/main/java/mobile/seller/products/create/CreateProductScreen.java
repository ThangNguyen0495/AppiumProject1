package mobile.seller.products.create;

import api.Seller.setting.StoreInformation;
import lombok.SneakyThrows;
import mobile.seller.login.LoginScreen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import utilities.assert_customize.AssertCustomize;
import utilities.commons.UICommonMobile;
import utilities.data.DataGenerator;

import java.time.Instant;
import java.util.List;
import java.util.stream.IntStream;

import static org.apache.commons.lang.math.JVMRandom.nextLong;
import static utilities.character_limit.CharacterLimit.MAX_PRICE;

public class CreateProductScreen extends CreateProductElement {
    WebDriver driver;
    AssertCustomize assertCustomize;
    UICommonMobile commonMobile;
    Logger logger = LogManager.getLogger();
    private static String defaultLanguage;

    public CreateProductScreen(WebDriver driver) {
        this.driver = driver;
        assertCustomize = new AssertCustomize(driver);
        commonMobile = new UICommonMobile(driver);
        defaultLanguage = new StoreInformation(LoginScreen.getLoginInformation())
                .getInfo()
                .getDefaultLanguage();
    }

    long currentEpoch = Instant.now().toEpochMilli();
    private boolean hideRemainingStock = false;
    private boolean displayOutOfStock = true;
    private boolean hasDiscount = true;
    private boolean hasCostPrice = true;
    private boolean hasDimension = true;

    public CreateProductScreen getHideRemainingStock(boolean hideRemainingStock) {
        this.hideRemainingStock = hideRemainingStock;
        return this;
    }

    public CreateProductScreen getDisplayOutOfStock(boolean displayOutOfStock) {
        this.displayOutOfStock = displayOutOfStock;
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

    void selectProductImages() {
        // Get list images
        List<String> imageFileNames = new DataGenerator().getAllFileNamesInFolder("images");

        // Sent list images to mobile device
        imageFileNames.forEach(fileName -> commonMobile.pushFileToMobileDevices(fileName));

        // Open select image popup
        commonMobile.click(rsId_btnSelectImage);

        // Select images
        IntStream.range(0, imageFileNames.size()).forEach(imageIndex -> commonMobile.click(loc_dlgSelectImage_lstImages, imageIndex));

        // Save changes
        commonMobile.click(rsId_dlgSelectImage_btnSave);

        // Log
        logger.info("Select product images.");
    }

    void inputProductName() {
        // Input product name
        String name = "[%s] Product name %s".formatted(defaultLanguage, currentEpoch);
        commonMobile.sendKeys(rsId_txtProductName, name);

        // Log
        logger.info("Input product name: {}", name);
    }

    void inputProductDescription() {
        // Open description popup
        commonMobile.click(rsId_btnProductDescription);

        // Input product description
        String description = "[%s] Product description %s".formatted(defaultLanguage, currentEpoch);
        commonMobile.sendKeys(rsId_dlgProductDescription_txtContent, description);

        // Save changes
        commonMobile.click(rsId_dlgProductDescription_btnSave);

        // Log
        logger.info("Input product description: {}", description);

    }

    void inputWithoutVariationPrice() {
        // Input listing price
        long listingPrice = nextLong(MAX_PRICE);
        commonMobile.sendKeys(rsId_sctPrice, loc_txtWithoutVariationListingPrice, String.valueOf(listingPrice));
        logger.info("Input listing price: %,d".formatted(listingPrice));

        // Input selling price
        long sellingPrice = nextLong(Math.max(listingPrice, 1));
        commonMobile.sendKeys(rsId_sctPrice, loc_txtWithoutVariationSellingPrice, String.valueOf(sellingPrice));
        logger.info("Input selling price: %,d".formatted(sellingPrice));

        // Input cost price
        long costPrice = nextLong(Math.max(sellingPrice, 1));
        commonMobile.sendKeys(rsId_sctPrice, loc_txtWithoutVariationCostPrice, String.valueOf(costPrice));
        logger.info("Input cost price: %,d".formatted(costPrice));
    }

    void inputWithoutVariationSKU() {
        // Input without variation SKU
        String sku = "SKU%s".formatted(currentEpoch);
        commonMobile.sendKeys(rsId_txtWithoutVariationSKU, sku);

        // Log
        logger.info("Input without variation SKU: {}", sku);
    }

    void inputWithoutVariationBarcode() {
        // Input without variation barcode
        String barcode = "Barcode%s".formatted(currentEpoch);
        commonMobile.sendKeys(rsId_txtWithoutVariationBarcode, barcode);

        // Log
        logger.info("Input without variation barcode: {}", barcode);
    }

    @SneakyThrows
    void hideRemainingStockOnOnlineStore() {
        System.out.println(commonMobile.isCheckedWithImageAlgorithms(rsId_chkHideRemainingStock));
    }

    @SneakyThrows
    void displayIfOutOfStock() {
        System.out.println(commonMobile.isCheckedWithImageAlgorithms(rsId_chkShowOutOfStock));

    }
    public void createProduct() {
        selectProductImages();
        inputProductName();
        inputProductDescription();
        inputWithoutVariationPrice();
        inputWithoutVariationSKU();
        inputWithoutVariationBarcode();
        hideRemainingStockOnOnlineStore();
        displayIfOutOfStock();
        logger.info("Check log.");
    }
}
