package mobile.seller.products.product_management;

import static utilities.environment.goSELLEREnvironment.goSELLERBundleId;

public class ProductManagementElement {
    String rsId_txtSearchBox = "%s:id/edtProductSearch".formatted(goSELLERBundleId);
    String str_lblProductName = "//android.widget.TextView[@* = '%s']";
}
