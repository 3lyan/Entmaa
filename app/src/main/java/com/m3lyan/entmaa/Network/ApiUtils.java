package com.m3lyan.entmaa.Network;

/**
 * Created by mahmoud on 5/15/2018.
 */

public class ApiUtils {

        public static final String BASE_URL = "https://e3gaz.net/entmaa/public/api/v1/";

        public static APIService getSOService() {
            return RetrofitClient.getClient(BASE_URL).create(APIService.class);
        }
}
