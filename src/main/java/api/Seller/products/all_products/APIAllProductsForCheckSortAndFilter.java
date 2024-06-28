package api.Seller.products.all_products;

import api.Seller.login.Login;
import api.Seller.products.all_products.APIAllProducts.ProductManagementInfo;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import utilities.api.API;
import utilities.model.dashboard.loginDashBoard.LoginDashboardInfo;
import utilities.model.sellerApp.login.LoginInformation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class APIAllProductsForCheckSortAndFilter {
    API api = new API();
    LoginDashboardInfo loginInfo;
    LoginInformation loginInformation;

    public APIAllProductsForCheckSortAndFilter(LoginInformation loginInformation) {
        this.loginInformation = loginInformation;
        loginInfo = new Login().getInfo(loginInformation);
    }

    //&sort=lastModifiedDate,desc
    String allProductListPath = "/itemservice/api/store/dashboard/%s/items-v2?page=%s&size=100&bhStatus=&itemType=BUSINESS_PRODUCT&sort=%s&branchIds=%s";

    Response getAllProductsResponse(String sortOption,int pageIndex, int... branchIds) {
        String branchId = branchIds.length == 0 ? "" : String.valueOf(branchIds[0]);
        String currentPath = allProductListPath.formatted(loginInfo.getStoreID(), pageIndex, sortOption, branchId) + (List.of("priority,desc", "priority,asc").contains(sortOption) ? "&sort=lastModifiedDate,desc" : "");
        return api.get(currentPath, loginInfo.getAccessToken())
                .then()
                .statusCode(200)
                .extract()
                .response();
    }

    public ProductManagementInfo getAllProductInformation(String sortOption, int... branchIds) {
        // Init product management info
        ProductManagementInfo info = new ProductManagementInfo();
        // get page 0 data
        List<Integer> variationNumber = new ArrayList<>();
        List<Integer> allProductIds = new ArrayList<>();
        List<String> allProductNames = new ArrayList<>();
        List<Integer> remainingStocks = new ArrayList<>();
        List<Integer> priority = new ArrayList<>();

        // get total products
        int totalOfProducts = Integer.parseInt(getAllProductsResponse(sortOption, 0, branchIds).getHeader("X-Total-Count"));

        // get number of pages
        int numberOfPages = totalOfProducts / 50;

        // get other page data
        List<JsonPath> jsonPaths = IntStream.rangeClosed(0, numberOfPages)
                .parallel()
                .mapToObj(pageIndex -> getAllProductsResponse(sortOption, pageIndex, branchIds).jsonPath())
                .toList();
        jsonPaths.forEach(jsonPath -> {
            variationNumber.addAll(jsonPath.getList("variationNumber"));
            allProductIds.addAll(jsonPath.getList("id"));
            allProductNames.addAll(jsonPath.getList("name"));
            remainingStocks.addAll(jsonPath.getList("remainingStock"));
            priority.addAll(jsonPath.getList("priority"));
        });
        info.setProductIds(allProductIds);
        info.setVariationNumber(variationNumber);
        info.setProductNames(allProductNames);
        info.setRemainingStocks(remainingStocks);
        info.setPriority(priority);

        return info;
    }

    public List<String> getListProductNameAfterSortByRecentUpdated() {
        return getAllProductInformation("lastModifiedDate,desc").getProductNames();
    }

    public List<String> getListProductNameAfterSortByStockHighToLow() {
        return getAllProductInformation("stock,desc").getProductNames();
    }

    public List<String> getListProductNameAfterSortByStockLowToHigh() {
        return getAllProductInformation("stock,asc").getProductNames();
    }

    public List<String> getListProductNameAfterSortByPriorityHighToLow() {
        return getAllProductInformation("priority,desc").getProductNames();
    }

    public List<String> getListProductNameAfterSortByPriorityLowToHigh() {
        return getAllProductInformation("priority,asc").getProductNames();
    }
}
