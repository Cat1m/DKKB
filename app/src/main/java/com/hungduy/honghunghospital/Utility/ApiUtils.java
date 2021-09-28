package com.hungduy.honghunghospital.Utility;

public class ApiUtils {
    private ApiUtils() {}
    public static final String BASE_URL = "https://api.nguyentri.tech/api/crmapi/";
   // public static final String BASE_URL = "https://apiqlcv.hungduy.vn/api/qlcongviecVer2/";

    public static APIService getAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
