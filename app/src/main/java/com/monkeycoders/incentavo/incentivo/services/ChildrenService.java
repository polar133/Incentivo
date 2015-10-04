package com.monkeycoders.incentavo.incentivo.services;

import android.content.Context;
import com.monkeycoders.incentavo.incentivo.network.ApiManager;
import java.util.HashMap;
import retrofit.RetrofitError;


public class ChildrenService {

    private static ChildrenService instance;

    private ChildrenService() {
    }

    public static ChildrenService getInstance() {
        if (instance == null) {
            instance = new ChildrenService();
        }
        return instance;
    }

    public HashMap<String, Object> loadChildren(Context context, int id) {
        HashMap<String, Object> response = new HashMap<String, Object>();
        try {
            response = ApiManager.geApiClient().getChildren(String.valueOf(id));
            return response;

        } catch (RetrofitError e) {
            System.out.println(e);
            response.put("Error", "Error de conexión. Intente de nuevo.");
            return response;
        }
    }
}
