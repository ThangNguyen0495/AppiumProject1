package mobile.seller.products.product_management;

import org.openqa.selenium.By;

import java.util.Arrays;
import java.util.List;

import static utilities.environment.goSELLEREnvironment.goSELLERBundleId;

public class ProductManagementElement {
    String rsId_txtSearchBox = "%s:id/edtProductSearch".formatted(goSELLERBundleId);

    enum SortOption {
        recentUpdated, stockHighToLow, stockLowToHigh, priorityHighToLow, priorityLowToHigh;

        public static List<SortOption> getAllSortOptions() {
            return Arrays.asList(SortOption.values());
        }
    }

    String rsId_btnSort = "%s:id/ivSortType".formatted(goSELLERBundleId);
    By loc_lstSortOptions = By.xpath("//*[@* ='%s:id/tvStatus']".formatted(goSELLERBundleId));
    String rsId_btnFilter = "%s:id/btnFilterProduct".formatted(goSELLERBundleId);
    String str_lblProductName = "//android.widget.TextView[@* = '%s']".formatted(goSELLERBundleId);
    By loc_lblProductName = By.xpath("//*[@* ='%s:id/tvProductName']".formatted(goSELLERBundleId));

}
