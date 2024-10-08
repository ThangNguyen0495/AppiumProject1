package mobile.seller.android.products.child_screen.filter.branch;

import org.openqa.selenium.By;

import static utilities.environment.goSELLEREnvironment.goSELLERBundleId;

public class BranchElement {
    By loc_btnAllBranches = By.xpath("(//*[@* = '%s:id/htvFullBranches'] // *[@* = '%s:id/tag_container'])[1]".formatted(goSELLERBundleId, goSELLERBundleId));
    By loc_btnBranch(String branchName) {
        return By.xpath("//*[@text = '%s']".formatted(branchName));
    }
}
