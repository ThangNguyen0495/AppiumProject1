package mobile.seller.products.create;

import static utilities.environment.EnvironmentInformation.goSELLERBundleId;

public class CreateProductElement {
    String rsId_btnSelectImage = "%s:id/rlSelectImages".formatted(goSELLERBundleId);
    String rsId_txtProductName = "%s:id/edtProductName".formatted(goSELLERBundleId);
    String rsId_btnProductDescription = "%s:id/tvProductDescription".formatted(goSELLERBundleId);
    String rsId_txtWithoutVariationListingPrice = "%s:id/edtProductOrgPrice".formatted(goSELLERBundleId);
    String rsId_txtWithoutVariationSellingPrice = "%s:id/edtProductNewPrice".formatted(goSELLERBundleId);
    String rsId_txtWithoutVariationCostPrice = "%s:id/edtProductCostPrice".formatted(goSELLERBundleId);
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
