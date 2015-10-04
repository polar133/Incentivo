package com.monkeycoders.incentavo.incentivo.services;

import android.content.Context;
import com.monkeycoders.incentavo.incentivo.network.ApiManager;
import java.util.HashMap;
import retrofit.RetrofitError;

public class CatalogService {
    private static CatalogService instance;

    private CatalogService() {
    }

    public static CatalogService getInstance() {
        if (instance == null) {
            instance = new CatalogService();
        }
        return instance;
    }

    public HashMap<String, Object> loadCatalog(Context context) {
        HashMap<String, Object> response = new HashMap<String, Object>();
        try {
            response = ApiManager.geApiClient().getCatalog();
            return response;

        } catch (RetrofitError e) {
            response.put("Error", "Error de conexión. Intente de nuevo.");
            return response;
        }
    }
}
