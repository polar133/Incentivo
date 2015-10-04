package com.monkeycoders.incentavo.incentivo.services;

import android.content.Context;

import com.monkeycoders.incentavo.incentivo.network.ApiManager;

import java.util.HashMap;

import retrofit.RetrofitError;

public class SaldoService {

    private static SaldoService instance;

    private SaldoService() {
    }

    public static SaldoService getInstance() {
        if (instance == null) {
            instance = new SaldoService();
        }
        return instance;
    }

    public HashMap<String, Object> loadSaldo(Context context, String rut) {
        HashMap<String, Object> response = new HashMap<String, Object>();
        try {
            HashMap<String, Object> credentials = new HashMap<String, Object>();
            credentials.put("rut", rut);
            credentials.put("apiID", ApiManager.apiID);
            response = ApiManager.geApiBank().postSaldo(credentials);
            return response;

        } catch (RetrofitError e) {
            System.out.println(e);
            response.put("Error", "Error de conexión. Intente de nuevo.");
            return response;
        }
    }

}
