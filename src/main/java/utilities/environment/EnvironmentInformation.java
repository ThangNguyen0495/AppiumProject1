package utilities.environment;

import utilities.utils.PropertiesUtil;

public class EnvironmentInformation {
    public final static String goSELLERBundleId = PropertiesUtil.getEnvironmentData("goSELLERBundleId");
    public final static String goSELLERLoginActivity = "com.mediastep.gosellseller.modules.credentials.login.LoginActivity";
    public final static String goSELLERCreateProductActivity = "com.mediastep.gosellseller.modules.upload_product.CreateProductActivity";
    public final static String goSELLERProductManagementActivity = "com.mediastep.gosellseller.modules.product_management.ProductManagementActivity";
    public final static String goBUYERBundleId = PropertiesUtil.getEnvironmentData("goBUYERBundleId_shopThang");
    public final static String goBUYERSplashActivity = "%s.ui.modules.splash.SplashScreenActivity".formatted(goBUYERBundleId);
}
