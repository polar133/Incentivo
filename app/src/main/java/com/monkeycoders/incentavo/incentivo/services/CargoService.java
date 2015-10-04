package com.monkeycoders.incentavo.incentivo.services;

import android.content.Context;
import com.monkeycoders.incentavo.incentivo.network.ApiManager;
import java.util.HashMap;
import retrofit.RetrofitError;

public class CargoService {
    private static CargoService instance;

    private CargoService() {
    }

    public static CargoService getInstance() {
        if (instance == null) {
            instance = new CargoService();
        }
        return instance;
    }

    public HashMap<String, Object> loadCargo(Context context, String rut, int monto, String desc, String idProducto) {
        HashMap<String, Object> response = new HashMap<String, Object>();
        try {
            HashMap<String, Object> credentials = new HashMap<String, Object>();
            credentials.put("rut", rut);
            credentials.put("apiID", ApiManager.apiID);
            credentials.put("idProducto", idProducto);
            credentials.put("monto", monto);
            credentials.put("descripcion", desc);

            response = ApiManager.geApiBank().postSaldo(credentials);
            return response;

        } catch (RetrofitError e) {
            response.put("Error", "Error de conexión. Intente de nuevo.");
            return response;
        }
    }
}
