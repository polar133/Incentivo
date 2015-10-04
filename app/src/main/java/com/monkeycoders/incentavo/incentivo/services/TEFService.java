package com.monkeycoders.incentavo.incentivo.services;

import android.content.Context;

import com.monkeycoders.incentavo.incentivo.network.ApiManager;

import java.util.HashMap;

import retrofit.RetrofitError;

public class TEFService {

    private static TEFService instance;

    private TEFService() {
    }

    public static TEFService getInstance() {
        if (instance == null) {
            instance = new TEFService();
        }
        return instance;
    }

    public HashMap<String, Object> loadTEF(Context context, String rut, String idProductoOrigin, int monto, String mensaje, HashMap<String, Object> datosDestino) {
        HashMap<String, Object> response = new HashMap<String, Object>();
        try {
            HashMap<String, Object> credentials = new HashMap<String, Object>();
            credentials.put("rut", rut);
            credentials.put("apiID", ApiManager.apiID);
            credentials.put("monto", monto);
            credentials.put("mensaje", mensaje);
            credentials.put("datosDestino", datosDestino);
            response = ApiManager.geApiBank().postTEF(credentials);
            return response;

        } catch (RetrofitError e) {
            response.put("Error", "Error de conexión. Intente de nuevo.");
            return response;
        }
    }

}
