package com.monkeycoders.incentavo.incentivo.services;

import android.content.Context;
import com.monkeycoders.incentavo.incentivo.network.ApiManager;
import java.util.HashMap;
import retrofit.RetrofitError;


public class UserService {

    private static UserService instance;

    private UserService() {
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public HashMap<String, Object> cargarLogin(Context context, String login, String password) {
        HashMap<String, Object> respuesta = new HashMap<String, Object>();
        try {

            HashMap<String, Object> credentials = new HashMap<String, Object>();
            credentials.put("rut", login);
            credentials.put("password", password);
            respuesta = ApiManager.geApiClient().postLogin(credentials);
            return respuesta;

        } catch (RetrofitError e) {
            respuesta.put("Error", "Error de conexión. Intente de nuevo.");
            return respuesta;
        }
    }



}
