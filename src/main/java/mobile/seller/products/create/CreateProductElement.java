package mobile.seller.products.create;

import org.openqa.selenium.By;

import static utilities.environment.EnvironmentInformation.goSELLERBundleId;

public class CreateProductElement {
    String rsId_btnSelectImage = "%s:id/rlSelectImages".formatted(goSELLERBundleId);
    By loc_dlgSelectImage_lstImages = By.xpath("//*[@*='%s:id/tvSelectIndex']".formatted(goSELLERBundleId));
    String rsId_dlgSelectImage_btnSave = "%s:id/fragment_choose_photo_dialog_btn_choose".formatted(goSELLERBundleId);
    String rsId_txtProductName = "%s:id/edtProductName".formatted(goSELLERBundleId);
    String rsId_btnProductDescription = "%s:id/tvProductDescription".formatted(goSELLERBundleId);
    String rsId_dlgProductDescription_txtContent = "editor";
    String rsId_dlgProductDescription_btnSave = "com.mediastep.GoSellForSeller.STG:id/ivActionBarIconRight";
    String rsId_sctPrice = "%s:id/clProductPriceContainer".formatted(goSELLERBundleId);
    By loc_txtWithoutVariationListingPrice = By.xpath("//*[@*= '%s:id/edtProductOrgPrice']//*[@* = '%s:id/edtPriceCustom']".formatted(goSELLERBundleId, goSELLERBundleId));
    By loc_txtWithoutVariationSellingPrice = By.xpath("//*[@*= '%s:id/edtProductNewPrice']//*[@* = '%s:id/edtPriceCustom']".formatted(goSELLERBundleId, goSELLERBundleId));
    By loc_txtWithoutVariationCostPrice = By.xpath("//*[@*= '%s:id/edtProductCostPrice']//*[@* = '%s:id/edtPriceCustom']".formatted(goSELLERBundleId, goSELLERBundleId));
    String rsId_ddvSelectedVAT = "%s:id/tvVAT".formatted(goSELLERBundleId);
    String rsId_txtWithoutVariationSKU = "%s:id/edtSKU".formatted(goSELLERBundleId);
    String rsId_txtWithoutVariationBarcode = "%s:id/edtProductBarcode".formatted(goSELLERBundleId);
    String rsId_chkHideRemainingStock = "%s:id/ivHideStockOnOnlineStore".formatted(goSELLERBundleId);
    String rsId_chkShowOutOfStock ="%s:id/ivDisplayIfOutOfStock".formatted(goSELLERBundleId);
    String rsId_chkShowListingPrice = "%s:id/ivListingProductCheckBox".formatted(goSELLERBundleId);
    String rsId_ddvSelectedManageType = "%s:id/btnSwitchManageInventoryType".formatted(goSELLERBundleId);
    String rsId_chkManageByLot = "%s:id/ivManageStockByLotDate".formatted(goSELLERBundleId);
    String rsId_chkExcludeExpiredStock = "%s:id/ivExcludeExpireQuantity".formatted(goSELLERBundleId);
}
