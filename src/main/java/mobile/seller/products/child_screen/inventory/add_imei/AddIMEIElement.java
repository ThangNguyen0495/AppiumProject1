package mobile.seller.products.child_screen.inventory.add_imei;

import static utilities.environment.EnvironmentInformation.goSELLERBundleId;

public class AddIMEIElement {
    String rsId_txtIMEI = "%s:id/edtInputImeiSerialNumberValue".formatted(goSELLERBundleId);
    String rsId_btnAdd = "%s:id/ivAddNewImeiSerialNumber".formatted(goSELLERBundleId);
    String rsId_btnSave = "%s:id/tvActionBarIconRight".formatted(goSELLERBundleId);
}
