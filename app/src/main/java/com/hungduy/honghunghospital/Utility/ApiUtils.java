package com.hungduy.honghunghospital.Utility;

public class ApiUtils {
    private ApiUtils() {}
   // public static final String BASE_URL = "https://api.nguyentri.xyz/api/app/";
    public static final String BASE_URL = "https://app.honghunghospital.com.vn/api/app/";
    //public static final String BASE_URL = "https://a.hungduy.vn/api/qlcongviecVer2/";

    public static APIService getAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
