package com.monkeycoders.incentavo.incentivo.services;

import android.content.Context;

import com.monkeycoders.incentavo.incentivo.network.ApiManager;

import java.util.HashMap;

import retrofit.RetrofitError;

public class ProductDetailService {
    private static ProductDetailService instance;

    private ProductDetailService() {
    }

    public static ProductDetailService getInstance() {
        if (instance == null) {
            instance = new ProductDetailService();
        }
        return instance;
    }

    public HashMap<String, Object> loadProductDetail(Context context, String id) {
        HashMap<String, Object> response = new HashMap<String, Object>();
        try {
            response = ApiManager.geApiClient().getProduct(id);
            return response;

        } catch (RetrofitError e) {
            response.put("Error", "Error de conexión. Intente de nuevo.");
            return response;
        }
    }
}
