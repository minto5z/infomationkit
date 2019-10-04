package com.mintosoft.rubelgroup.api;

/**
 * Created by fariz ramadhan.
 * website : www.farizdotid.com
 * github : https://github.com/farizdotid
 */
public class UtilsApi {

    // 10.0.2.2 ini adalah localhost.
    //public static final String BASE_URL_API = "http://nishutiapi.bemantech.com/api/";
    public static final String BASE_URL_API = "http://apidemo.rubelgroup.com.bd/api/";
    //public static final String BASE_URL_API = "http://amsapi.bemantech.com/api/";

    // Mendeklarasikan Interface BaseApiService
    public static BaseApiService getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }
}
